package ca.mcgill.ecse.carshop.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.model.CarShop;



public class CarShopPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	// Variables declaration
	private JButton buttonLogin; 			//login button
	private JButton signUpButton;			//sign up button
	private JLabel labelWindow;				
	private JLabel labelUsername;			//username label
	private JLabel labelPassword;			//password label
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
	private JLabel labelError;
	//TODO
	//need to add the error message to the display, and send a confirmation that the user is logged in if it's successful.



	public CarShopPage() {
		initComponents();

		setTitle("CarShop Application");

		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) { 
				attemptLogin();
			}
		});

		// Sign up button - will automatically log in a new customer if no errors are raised
		buttonSignup.addActionListener(new ActionListener() {
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
							initComponentsCustomerView();
						}
					}	
				}
			}
		});

		textPassword.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					attemptLogin();
				}
			}
		});
	}




	protected void attemptLogin() {
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

		if (CarShopApplication.getCurrentUser() != null && CarShopApplication.getAccountType().equals(CarShopApplication.accountType.Owner)) {
			System.out.println("logging in as owner...");
			//    		CarShopApplication.logIn(username, password);

			// init owner view
			initComponentsOwnerView();

		}

		if (CarShopApplication.getCurrentUser() != null && CarShopApplication.getAccountType().equals(CarShopApplication.accountType.Customer)) {
			System.out.println("Logging in as customer...");
			//    		CarShopApplication.logIn(username, password);

			// init customer view
			initComponentsCustomerView();
		}

		if (CarShopApplication.getCurrentUser() != null && CarShopApplication.getUser().getUsername().toLowerCase().contains("technician")) {
			System.out.println("Logging in as technician...");
			// init customer view
			initComponentsTechnicianView();
		}

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

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


		//layout
		labelWindow.setText("Car Shop Login");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
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

		labelApplication = new JLabel("Car shop application");
		labelApplication.setBounds(x-175, y-20, 142, 16);
		add(labelApplication);

		labelInstructions1 = new JLabel("Enter your username and password");
		labelInstructions1.setBounds(x-175, y+5, 273, 16);
		add(labelInstructions1);

		labelInstructions2 = new JLabel("To register a new account, select \"Sign up\"");
		labelInstructions2.setBounds(x-175, y+30, 273, 16);
		add(labelInstructions2);

		//error label - shown as a test for now. remove this later!
		//        labelError = new JLabel("error-area-remove-later-just-for-show----");
		//        labelError.setBounds(x-175, y+125, 273, 16);
		//        add(labelError);
		errorMessage.setBounds(x-175, y+125, 273, 16);
		add(errorMessage);

	}

	//initialize the owner panel
	private void initComponentsOwnerView() {
		//initialize the owner view
		ownerView = new OwnerView();

		//set the content pane to owner view
		setContentPane(ownerView);

		revalidate();
		repaint();
	}

	//initialize the customer panel
	private void initComponentsCustomerView() {
		//initialize the customer view
		customerView = new CustomerView();

		//set the content pane to customer view
		setContentPane(customerView);

		revalidate();
		repaint();
	}

	private void initComponentsTechnicianView() {
		// initialize the technician view
		technicianView = new TechnicianView();

		// set the content pane to technician view
		setContentPane(technicianView);

		revalidate();
		repaint();    
	}




}
