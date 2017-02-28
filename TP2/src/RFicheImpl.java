import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/** Implementation of a remotely accessible data slate.
 *
 * Created by saradion on 05/10/16.
 */
public class RFicheImpl extends UnicastRemoteObject implements RFiche {
    private String name;
    private String mail;

    public RFicheImpl(String new_name, String new_mail) throws RemoteException {
        super();
        name = new_name;
        mail = new_mail;
    }

    @Override
    public String getNom() throws RemoteException {
        return name;
    }

    @Override
    public String getEmail() throws RemoteException {
        return mail;
    }
}
