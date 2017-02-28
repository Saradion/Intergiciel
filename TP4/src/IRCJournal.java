import javax.jms.JMSException;
import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.Remote;

/**
 * Created by saradion on 10/10/16.
 */
interface IRCJournal extends Remote {
    void record() throws IOException, JMSException;
    BufferedReader recover() throws IOException, JMSException;
}
