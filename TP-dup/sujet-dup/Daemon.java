import java.util.HashMap;
import java.util.Map;

public class Daemon extends Thread {

    private Map<Integer, Update> updateBuffer = new HashMap<>();
	
	public void run() {
		while (true) {
            Update u = ClientImpl.group.receiveUpdate();
            updateBuffer.put(u.ticket, u);
            flush();
            if (Appli.nextTicket == Appli.currentTicket) {
                synchronized (Appli.lock) {
                    Appli.lock.notifyAll();
                }
            }
        }
	}

	private void flush() {
        Update u;
        while((u = updateBuffer.get(Appli.nextTicket)) != null) {
            synchronized (ClientImpl.data) {
                if (u.op == Update.ADD) ClientImpl.data[u.index] += u.val;
                if (u.op == Update.MUL) ClientImpl.data[u.index] += u.val;
            }
            System.out.println("Updating n°" + Appli.nextTicket);
            Appli.nextTicket++;
        }
    }
}
