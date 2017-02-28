import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by saradion on 26/10/16.
 */
class TicketServImpl extends UnicastRemoteObject implements TicketServ{
    private int nextTicket;

    TicketServImpl() throws RemoteException {
        nextTicket = 0;
    }

    @Override
    public synchronized int requestTicket() throws RemoteException {
        nextTicket++;
        return nextTicket;
    }
}
