import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by saradion on 26/10/16.
 */
interface TicketServ extends Remote {
    public int requestTicket() throws RemoteException ;
}
