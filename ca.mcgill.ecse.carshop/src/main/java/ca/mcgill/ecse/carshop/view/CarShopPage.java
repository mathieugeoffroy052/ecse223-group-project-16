package ca.mcgill.ecse.carshop.view;


import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;



public class CarShopPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	// Variables declaration
	private OwnerView ownerView;			//the owner view
	private CustomerView customerView;		//the customer view
	private LogInView login;				//the login view
	private TechnicianView technicianView;	//the technician view
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
