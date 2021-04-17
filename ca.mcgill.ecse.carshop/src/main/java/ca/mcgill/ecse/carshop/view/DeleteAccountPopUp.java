package ca.mcgill.ecse.carshop.view;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DeleteAccountPopUp {
	
	private JFrame ourFrame = new JFrame("Subscribe");
	
	DeleteAccountPopUp(){
		
		ourFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ourFrame.setBounds(200, 100, 400, 200);
		
		Container container = ourFrame.getContentPane();
		container.setLayout(null);
		
		JLabel logo = new JLabel("Are you sure you want to delete your account?");
		logo.setBounds(60,5,250,30);
		
		JLabel username_label = new JLabel("Username :");
		username_label.setBounds(20,30,250,30);
		
		JLabel password_label = new JLabel("Password :");
		password_label.setBounds(20,60,250,30);
		
		JTextField nameText = new JTextField();
		nameText.setBounds(65,30,250,30);
		
		JTextField emailText = new JTextField();
		emailText.setBounds(65, 60, 250, 30);
		
		JButton subBtn  = new JButton("Confirm");
		subBtn.setBounds(150,90,100,30);
		
		container.add(logo);
		container.add(username_label);
		container.add(password_label);
		container.add(nameText);
		container.add(emailText);
		container.add(subBtn);
		ourFrame.setVisible(true);
		
	}

}
