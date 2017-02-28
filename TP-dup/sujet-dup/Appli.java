import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;


public class Appli extends Thread {

	private Random rand = new Random();
    public static int currentTicket;
    public static int nextTicket = 1;
    public static final Object lock = new Object();

	public void run() {
		for (int i = 0;i<200;i++) {
            try {
                TicketServ serv = (TicketServ) Naming.lookup("//localhost:5000/TicketServ");
                currentTicket = serv.requestTicket();
                while (currentTicket != nextTicket) {
                    synchronized (lock) {
                        lock.wait();
                    }
                }

                int index = rand.nextInt(ClientImpl.nbData);
                int op = rand.nextInt(2);
                int val = rand.nextInt(3) + 1;
                synchronized (ClientImpl.data) {
                    if (op == Update.ADD) ClientImpl.data[index] += val;
                    if (op == Update.MUL) ClientImpl.data[index] *= val;
                }
                ClientImpl.group.sendUpdate(new Update(index, op, val, currentTicket));
                System.out.println("Applyied and sent update n°" + currentTicket);
                nextTicket++;
            } catch (NotBoundException | MalformedURLException | RemoteException | InterruptedException e) {
                e.printStackTrace();
            }
        }
		System.out.println("Application completed");
	}

}
