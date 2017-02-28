import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by saradion on 10/10/16.
 */
class WriteListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
        try {
            if (Irc.isConnected) {
                Stanza stanza = new Stanza("M", "ALL", Irc.myName, Irc.data.getText());
                Irc.producer.send(Irc.session.createObjectMessage(stanza));
                Irc.data.setText("");
            } else {
                Irc.print("You are not connected!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
