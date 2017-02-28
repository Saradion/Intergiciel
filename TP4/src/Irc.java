
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.awt.*;
import java.util.ArrayList;

public class Irc {
    public static TextArea text;
    public static TextField data;
    public static Frame frame;

    public static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static String subject = "MyQueue";

    public static ArrayList<String> users = new ArrayList<>();
    static String myName;

    public static ConnectionFactory connectionFactory;
    public static Connection connection;
    public static Session session;
    public static MessageConsumer consumer;
    public static MessageProducer producer;
    public static Topic topic;
    static boolean isConnected = false;

    public static void main(String argv[]) {

        if (argv.length != 1) {
            System.out.println("java Irc <name>");
            return;
        }
        myName = argv[0];
        connectionFactory = new ActiveMQConnectionFactory(url);

        // creation of the GUI
        frame = new Frame();
        frame.setLayout(new FlowLayout());

        text = new TextArea(10, 55);
        text.setEditable(false);
        text.setForeground(Color.red);
        frame.add(text);

        data = new TextField(55);
        frame.add(data);

        Button write_button = new Button("write");
        write_button.addActionListener(new WriteListener());
        frame.add(write_button);

        Button connect_button = new Button("connect");
        connect_button.addActionListener(new ConnectListener());
        frame.add(connect_button);

        Button who_button = new Button("who");
        who_button.addActionListener(new WhoListener());
        frame.add(who_button);

        Button leave_button = new Button("leave");
        leave_button.addActionListener(new LeaveListener());
        frame.add(leave_button);

        frame.setSize(470, 300);
        text.setBackground(Color.black);
        frame.setVisible(true);
    }

    /* allow to print something in the window */
    public static void print(String msg) {
        try {
            System.out.println(msg);
            text.append(msg + "\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

