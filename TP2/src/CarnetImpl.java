import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implements a notebook server.
 * <p>
 * Created by saradion on 05/10/16.
 */
public class CarnetImpl extends UnicastRemoteObject implements Carnet {
    private Map<String, RFiche> fiches;
    private String name;
    private ArrayList<String> registered;

    protected CarnetImpl(String new_name) throws RemoteException {
        fiches = new HashMap<>();
        name = new_name;
        registered = new ArrayList<>();
    }

    @Override
    public void Ajouter(SFiche sf) throws RemoteException {
        RFiche new_fiche = new RFicheImpl(sf.getNom(), sf.getEmail());
        fiches.put(new_fiche.getNom(), new_fiche);
        System.out.println("Added new entry : " + "{" + new_fiche.getNom() + " , " + new_fiche.getEmail() + "}");
    }

    @Override
    public RFiche Consulter(String n, boolean forward) throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
        System.out.println("Accessing entry : " + n);
        RFiche result = fiches.get(n);
        if ((result==null) && forward) {
            System.out.println("Forwarding the request...");
            for (String register : registered) {
                result = ((Carnet) Naming.lookup("//" +InetAddress.getLocalHost().getHostName()+ ":3000/"+register)).Consulter(n, false);
                if (result != null) { System.out.println("Got it.");break; }
            }
        }
        return result;
    }

    @Override
    public void update() throws RemoteException {
        String[] registers = java.rmi.registry.LocateRegistry.getRegistry(3000).list();
        if (registers != null) {
            for (String register : registers) {
                if (!Objects.equals(register, name)) {
                    registered.add(register);
                }
            }
        }
    }

    public static void main(String[] args) throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {
        Carnet new_carnet = new CarnetImpl(args[0]);
        String hostname = InetAddress.getLocalHost().getHostName();

        try {
            LocateRegistry.createRegistry(Integer.parseInt(args[1]));
        } catch (ExportException e) {
            e.printStackTrace();
        }

        Naming.rebind("//" + hostname + ":" + args[1] + "/" + args[0], new_carnet);
        for (String register : LocateRegistry.getRegistry(Integer.parseInt(args[1])).list()) {
            ((Carnet) Naming.lookup("//" + hostname + ":" + args[1] + "/" + register)).update();
        }
        System.out.println("Binded this notebook to host " + hostname + " on port " + args[1] + " with name " + args[0]);
    }
}
