import javax.jms.*;

/**
 * Created by saradion on 10/10/16.
 */
class IRCListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            Stanza stanza = (Stanza) ((ObjectMessage) message).getObject();

            if (stanza.header().equals("C")) {
                Irc.print("Connected : " + stanza.sender());
                Irc.users.add(stanza.sender());
                Stanza new_stanza = new Stanza("P", "ALL", Irc.myName, null);
                Irc.producer.send(Irc.session.createObjectMessage(new_stanza));
            } else if (stanza.header().equals("DC")) {
                Irc.print("Disconnected : " + stanza.sender());
                Irc.users.remove(stanza.sender());
            } else if (stanza.header().equals("P")) {
                if (!Irc.users.contains(stanza.sender())) Irc.users.add(stanza.sender());
            } else if (stanza.header().equals("M")) {
                Irc.print(stanza.sender() + " : " + stanza.body());
            } else {
                System.out.println("Not a correct stanza!!!");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
