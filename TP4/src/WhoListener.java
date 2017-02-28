import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by saradion on 10/10/16.
 */
class WhoListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
        try {
            Irc.print(Irc.users.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
