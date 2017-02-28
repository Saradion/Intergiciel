/* ------------------------------------------------------- 
		Les packages Java qui doivent etre importes.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;



/* ------------------------------------------------------- 
		Implementation de l'application
*/

public class Saisie extends JApplet {
	private static final long serialVersionUID = 1;
	private TextField nom, email;
	private Choice carnets;
	private Label message;
    private String hostname;

	public void init() {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();
        }
        setSize(300,200);
		setLayout(new GridLayout(6,2));
		add(new Label("  Nom : "));
		nom = new TextField(30);
		add(nom);
		add(new Label("  Email : "));
		email = new TextField(30);
		add(email);
		add(new Label("  Carnet : "));
		carnets = new Choice();
		carnets.addItem("Carnet1");
		carnets.addItem("Carnet2");
		add(carnets);
		add(new Label(""));
		add(new Label(""));
		Button Abutton = new Button("Ajouter");
		Abutton.addActionListener(new AButtonAction());
		add(Abutton);
		Button Cbutton = new Button("Consulter");
		Cbutton.addActionListener(new CButtonAction());
		add(Cbutton);
		message = new Label();
		add(message);
	}

	// La reaction au bouton Consulter
	private class CButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String n, c;
			n = nom.getText();
			c = carnets.getSelectedItem();
			message.setText("Consulter("+n+","+c+")        ");
			try {
				Carnet carnet = (Carnet) Naming.lookup("//" + hostname + ":3000/" + c);
                RFiche fiche = carnet.Consulter(n, true);
                System.out.println("[" + fiche.getNom() + " : " + fiche.getEmail() + "]");
			} catch (NotBoundException | MalformedURLException | RemoteException | UnknownHostException e) {
				e.printStackTrace();
			}
        }
	}
	// La reaction au bouton Ajouter
	private class AButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String n, e, c;
			n = nom.getText();
			e = email.getText();
			c = carnets.getSelectedItem();
			message.setText("Ajouter("+n+","+e+","+c+")        ");
            try {
                Carnet carnet = (Carnet) Naming.lookup("//" + hostname + ":3000/" + c);
                SFiche new_fiche = new SFicheImpl(n, e);
                carnet.Ajouter(new_fiche);
                System.out.println("Added [" + n + " : " + c + "]");
            } catch (NotBoundException | MalformedURLException | RemoteException e1) {
                e1.printStackTrace();
            }
        }
	}
	
	public static void main(String args[]) {
		Saisie a = new Saisie();
		a.init();
		a.start();
		JFrame frame = new JFrame("Applet");
		frame.setSize(400,200);
  		frame.getContentPane().add(a);
  		frame.setVisible(true);
	}
	
}


