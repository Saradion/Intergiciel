import javax.jms.Destination;
import javax.jms.Session;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.rmi.Naming;

/**
 * Created by saradion on 10/10/16.
 */
class ConnectListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
        try {
            if (!Irc.isConnected) {
                Irc.connection = Irc.connectionFactory.createConnection();
                Irc.connection.start();
                Irc.isConnected = true;
                Irc.session = Irc.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = Irc.session.createTopic("Nouveau sujet");
                Irc.producer = Irc.session.createProducer(destination);
                Irc.consumer = Irc.session.createConsumer(destination);

                Irc.consumer.setMessageListener(new IRCListener());

                BufferedReader in = ((IRCJournal) Naming.lookup("//" + InetAddress.getLocalHost().getHostName() +":19666/IRCjournal")).recover();
                String new_line = in.readLine();
                while (new_line != null) {
                    Irc.print(new_line);
                    new_line = in.readLine();
                }
                Stanza stanza = new Stanza("C", "ALL", Irc.myName, null);
                Irc.producer.send(Irc.session.createObjectMessage(stanza));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

