import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.rmi.Naming;

/**
 * Created by saradion on 10/10/16.
 */
class LeaveListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
        try {
            Stanza stanza = new Stanza("DC", "ALL", Irc.myName, null);
            Irc.producer.send(Irc.session.createObjectMessage(stanza));
            Irc.isConnected = false;
            Irc.users.clear();
            ((IRCJournal) Naming.lookup("//" + InetAddress.getLocalHost().getHostName() + ":19666/IRCjournal")).record();
            Irc.connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
