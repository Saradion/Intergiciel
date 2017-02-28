import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Control extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton gobutton;
	private JButton printbutton;
	
	public Control() {
		super();
		this.setSize(300, 200);
        this.setLayout(new GridLayout(2, 2));
		this.gobutton=new JButton("Go");
		this.gobutton.addActionListener(this);
		this.printbutton=new JButton("Print");
		this.printbutton.addActionListener(this);
        this.getContentPane().add(this.gobutton);
		this.getContentPane().add(this.printbutton);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		//
		//System.out.println(arg0.getActionCommand());
		String cmd = arg0.getActionCommand();
		if (cmd.equals("Go")) LauncherImpl.go();
		if (cmd.equals("Print")) LauncherImpl.print();
		//this.dispose();
	}
	
	public static void main(String args[]) {
		new Control();
	}
}
