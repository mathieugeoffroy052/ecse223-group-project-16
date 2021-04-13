package ca.mcgill.ecse.carshop.view;

import java.awt.Color;

import javax.swing.*;

public class CarShopPage extends JFrame{
	
	private static final long serialVersionUID = -4426310869335015542L;

		
	//UI elements
	private JLabel errorMessage;
	private JLabel username;
	private JLabel password;
	private JTextField usernameBox;
	private JPasswordField passwordBox;
	private JButton loginButton;
	
	
	private void initComponents() {
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		username = new JLabel("Username");
		password = new JLabel("Password");
		
		usernameBox = new JTextField();
		passwordBox = new JPasswordField();
		
		loginButton = new JButton("Login");
		
		//button listeners
		loginButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginButtonActionPerformed(evt);
			}
		});
		
		
		//layout
		//for some reason I get an exception, even if I feel
		//I followed what Percy did, but on a dumbed down scale
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup())
					.addComponent(errorMessage)
					.addComponent(username)
					.addComponent(password)
					.addComponent(usernameBox)
					.addComponent(passwordBox)
					.addComponent(loginButton)
				);
		pack();
	}
	
	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//TODO
	}
	
	private void refreshData() {
		//TODO
	}
	
	
	//initializes the display (gets called in the Application)
	public CarShopPage() {
		initComponents();
		refreshData();
	}

}
