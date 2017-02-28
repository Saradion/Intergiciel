import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by saradion on 10/10/16.
 */
public class Admin {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection new_connection = connectionFactory.createConnection();
            new_connection.start();
            Session new_session = new_connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = new_session.createTopic("Nouveau sujet");


            MessageProducer producer = new_session.createProducer(destination);
            MessageConsumer consumer = new_session.createConsumer(destination);

            MessageListener listener = message -> {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Received : " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            };

            consumer.setMessageListener(listener);

            TextMessage message = new_session.createTextMessage("Hello, Welcome to this new Topic!");
            producer.send(message);
            Thread.sleep(1000);
            new_connection.close();
        } catch (InterruptedException | JMSException e) {
            e.getStackTrace();
        }
    }
}
