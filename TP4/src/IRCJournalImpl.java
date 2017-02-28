import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by saradion on 10/10/16.
 */
public class IRCJournalImpl extends UnicastRemoteObject implements IRCJournal {
    private String home;
    private FileWriter out;
    private Connection connection;

    private IRCJournalImpl() throws RemoteException {
        home = System.getProperty("user.home");
    }

    @Override
    public void record() throws IOException, JMSException {
        out = new FileWriter(home + "/journal.log");

        ConnectionFactory connection_factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        connection = connection_factory.createConnection();
        connection.start();
        Session new_session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = new_session.createTopic("Nouveau sujet");
        MessageConsumer new_consumer = new_session.createConsumer(destination);
        new_consumer.setMessageListener(message -> {
            try {
                Stanza stanza = (Stanza) ((ObjectMessage) message).getObject();
                if (stanza.header().equals("M")) {
                    System.out.println(stanza.body());
                    out.write(stanza.sender() + " : " + stanza.body()+"\n");
                    out.flush();
                }
            } catch (JMSException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public BufferedReader recover() throws IOException, JMSException {
        try {
            connection.close();
            out.close();
        } catch (NullPointerException e) {
            System.out.println("Backlogger not connected yet");
        }
        InputStream fis = new FileInputStream(home + "/journal.log");
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader in = new BufferedReader(isr);
        return in;
    }

    public static void main(String[] args) throws RemoteException, UnknownHostException, MalformedURLException {
        String hostname = InetAddress.getLocalHost().getCanonicalHostName();
        try {
            LocateRegistry.createRegistry(Integer.parseInt(args[0]));
        } catch (ExportException e) {
            e.printStackTrace();
        }

        IRCJournal journal = new IRCJournalImpl();
        Naming.rebind("//" + hostname + ":" + args[0] + "/IRCjournal", journal);
    }
}
