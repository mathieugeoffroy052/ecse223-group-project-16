package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.application.CarShopApplication.AccountType;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;

public class LogInView extends JPanel {

	// Variables declaration
		private JPanel thisPanel = this;
		private JButton buttonLogin; 			//login button
		private JLabel labelWindow;				
		private JLabel labelUsername;			//username label
		private JLabel labelPassword;			//password label
		@SuppressWarnings("unused")
		private JPanel panelName;				
		private JPasswordField textPassword;	//password text box
		private JTextField textUsername;		//username text box
		private JLabel errorMessage;			//error popup label
		private String error;
		private OwnerView ownerView;			//the owner view
		private CustomerView customerView;		//the customer view
		private TechnicianView technicianView;	//the technician view
		private JButton buttonSignup;
		private JLabel labelApplication;
		private JLabel labelInstructions1;
		private JLabel labelInstructions2;
		@SuppressWarnings("unused")
		private JLabel labelError;
		
		
		public LogInView() {
			initComponents();
			
			buttonLogin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					attemptLogin();
				}
			});

			// Sign up button - will automatically log in a new customer if no errors are raised
			buttonSignup.addActionListener(new ActionListener() {
				@SuppressWarnings({ "deprecation", "static-access" })
				@Override
				public void actionPerformed(ActionEvent evt) {
					//TODO 
					String username = textUsername.getText();
					String password = textPassword.getText();
					error = null;
					try 
					{
						CarShopController.signUpUser(username, password, CarShopApplication.AccountType.Customer);
					} 
					catch (InvalidInputException e) 
					{
						error = e.getMessage();
					}
					if(error != null) {
						errorMessage.setText(error);
					}else {
						error = null;
						try {
							CarShopController.login(username, password);
						} catch (InvalidInputException e) {
							error = e.getMessage();
						}
						if(error!=null) {
							errorMessage.setText(error);
						}else {
							if (CarShopApplication.getCurrentUser() != null && CarShopApplication.getAccountType().equals(CarShopApplication.accountType.Customer)) {
								System.out.println("Logging in as customer...");

								// init customer view
								CarShopPage frame = (CarShopPage) SwingUtilities.windowForComponent(thisPanel);
								frame.initComponentsCustomerView();
							}
						}	
					}
				}
			});

			textPassword.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// Auto-generated method stub

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						attemptLogin();
					}
				}
			});
		}
		
		
		private void initComponents() {

			//initialize Jthings
			panelName = new JPanel();
			labelWindow = new JLabel();
			labelUsername = new JLabel();
			labelPassword = new JLabel();
			buttonLogin = new JButton();
			buttonSignup = new JButton();
			textUsername = new JTextField();
			textPassword = new JPasswordField();
			errorMessage = new JLabel();
			labelApplication = new JLabel();
			labelInstructions1 = new JLabel();
			labelInstructions2 = new JLabel();
			labelError = new JLabel();



			//layout
			labelWindow.setText("CarShop Login");

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setSize(screenSize.width, screenSize.height);

//			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screenSize.width)/2;
			int y = (screenSize.height)/2;

			setLayout(null);

			labelUsername = new JLabel("Username");
			labelUsername.setBounds(x-175, y+75, 77, 16);
			add(labelUsername);

			labelPassword = new JLabel("Password");
			labelPassword.setBounds(x-175, y+100, 77, 16);
			add(labelPassword);

			textUsername = new JTextField();
			textUsername.setBounds(x-110, y+70, 184, 26);
			add(textUsername);
			textUsername.setColumns(10);

			textPassword = new JPasswordField();
			textPassword.setBounds(x-110, y+95, 184, 26);
			add(textPassword);
			textPassword.setColumns(10);

			buttonSignup = new JButton("Sign up");
			buttonSignup.setBounds(x-175, y+175, 117, 29);
			add(buttonSignup);

			buttonLogin = new JButton("Log in");
			buttonLogin.setBounds(x-60, y+175, 117, 29);
			add(buttonLogin);
	        
	        labelApplication = new JLabel("CarShop Application");
	        labelApplication.setFont(new Font("Arial", Font.BOLD, 22));
	        labelApplication.setBounds(x-175, y-20, 250, 25);
	        add(labelApplication);
	        
	        labelInstructions1 = new JLabel("Enter your username and password");
	        labelInstructions1.setBounds(x-175, y+5, 273, 16);
	        add(labelInstructions1);
	        
	        labelInstructions2 = new JLabel("To register a new account, select \"Sign up\"");
	        labelInstructions2.setBounds(x-175, y+30, 273, 16);
	        add(labelInstructions2);
	        
	        //error label - shown as a test for now. remove this later!
//	        labelError = new JLabel("error-area-remove-later-just-for-show----");
//	        labelError.setBounds(x-175, y+125, 273, 16);
//	        add(labelError);
	        errorMessage.setBounds(x-175, y+125, 273, 16);
	        errorMessage.setForeground(Color.RED);
	        add(errorMessage);

	    }
		
		private void attemptLogin() {
			CarShopPage frame = (CarShopPage) SwingUtilities.windowForComponent(thisPanel);
			String username = textUsername.getText();
			@SuppressWarnings("deprecation")
			String password = textPassword.getText();
			error = null;
			try {
				CarShopController.login(username, password);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
			if(error != null) {
				errorMessage.setText(error);
			}

			//login or create a new account using the username and password 
			//inputted in the textUsername and textPassword text fields
			//if user is logged in correctly, transition to a new window 
			//(either customer or owner perspective)  

			if (CarShopApplication.getCurrentUser() != null && CarShopApplication.getAccountType().equals(AccountType.Owner)) {
				System.out.println("logging in as owner...");
				//    		CarShopApplication.logIn(username, password);

				// init owner view
				
				frame.initComponentsOwnerView();

			}

			if (CarShopApplication.getCurrentUser() != null && CarShopApplication.getAccountType().equals(AccountType.Customer)) {
				System.out.println("Logging in as customer...");
				//    		CarShopApplication.logIn(username, password);

				// init customer view
				frame.initComponentsCustomerView();
			}

			if (CarShopApplication.getCurrentUser() != null && CarShopApplication.getUser().getUsername().toLowerCase().contains("technician")) {
				System.out.println("Logging in as technician...");
				// init customer view
				frame.initComponentsTechnicianView();
			}

		}
	
}
