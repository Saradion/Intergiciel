import java.net.*;
import java.rmi.*;

public interface Carnet extends Remote {
	public void Ajouter(SFiche sf) throws RemoteException, MalformedURLException;
	public RFiche Consulter(String n, boolean forward) throws RemoteException, java.net.UnknownHostException, MalformedURLException, NotBoundException;
	public void update() throws RemoteException;
}
