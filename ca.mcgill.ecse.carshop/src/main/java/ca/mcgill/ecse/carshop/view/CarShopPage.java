package ca.mcgill.ecse.carshop.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.application.CarShopApplication.AccountType;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;



public class CarShopPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	// Variables declaration
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
	private LogInView login;				//the login view
	private TechnicianView technicianView;	//the technician view
	private JButton buttonSignup;
	private JLabel labelApplication;
	private JLabel labelInstructions1;
	private JLabel labelInstructions2;
	@SuppressWarnings("unused")
	private JLabel labelError;
	//need to add the error message to the display, and send a confirmation that the user is logged in if it's successful.



	public CarShopPage() {
		setTitle("CarShop Application");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		returnToLogInPanel();
	}
    
    //initialize the owner panel
    public void initComponentsOwnerView() {
    	//initialize the owner view

		ownerView = new OwnerView();

		//set the content pane to owner view
		setContentPane(ownerView);

		revalidate();
		repaint();
	}

	//initialize the customer panel
	public void initComponentsCustomerView() {
		//initialize the customer view
		customerView = new CustomerView();

		//set the content pane to customer view
		setContentPane(customerView);

		revalidate();
		repaint();
	}

	public void initComponentsTechnicianView() {
		// initialize the technician view
		technicianView = new TechnicianView();

		// set the content pane to technician view
		setContentPane(technicianView);

		revalidate();
		repaint();    
	}
	
	public void returnToLogInPanel(){
		
		login = new LogInView();
		
		setContentPane(login);
		
		revalidate();
		repaint();    
	}
}
