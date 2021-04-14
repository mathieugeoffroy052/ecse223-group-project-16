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


import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;



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
    //TODO
    //need to add the error message to the display, and send a confirmation that the user is logged in if it's successful.
    


    public CarShopPage() {
        initComponents();
        
        setTitle("Car Shop Login Form");
        
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	//TODO 
            	String username = textUsername.getText();
            	String password = textPassword.getText();
            	error = null;
            	try {
					CarShopController.login(username, password);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					error = e.getMessage();
				}
            	if(error != null) {
    				errorMessage.setText(error);
            	}

            	//login or create a new account using the username and password 
            	//inputted in the textUsername and textPassword text fields
            	//if user is logged in correctly, transition to a new window 
            	//(either customer or owner perspective)  
            	
            	if (username.equals("owner")) {
					System.out.println("logging in as owner...");
					
					// init owner view
					initComponentsOwnerView();
					
				}
            	
            	if (username.equals("c")) {
            		System.out.println("Logging in as customer...");
            		
            		// init customer view
            		initComponentsCustomerView();
            	}
            	
            	if (username.toLowerCase().contains("technician")) {
            		System.out.println("Logging in as technician...");
            		
            		// init customer view
            		initComponentsTechnicianView();
            	}
            }
        });
        
        signUpButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		System.out.println("Directing to sign up page...");
        		
        		//TODO 
            	String username = textUsername.getText();
            	String password = textPassword.getText();
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
        textUsername = new JTextField();
        textPassword = new JPasswordField();
        errorMessage = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        
        //layout
        labelWindow.setText("Car Shop Login");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panelName);
        panelName.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(labelWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(180, 180, 180))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(labelWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );

        labelUsername.setText("Username");
        labelPassword.setText("Password");
        buttonLogin.setText("Login");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelUsername)
                            .addComponent(labelPassword))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textUsername)
                            .addComponent(textPassword)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsername)
                    .addComponent(textUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPassword)
                    .addComponent(textPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(buttonLogin)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        
		signUpButton = new JButton("Sign up");
		signUpButton.setBounds(526, 490, 117, 29);	// TODO
		add(signUpButton);
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
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
  
    
    //initialize business info
    private void initComponentsOwnerViewBusinessInfo() {
		
	}
    
    //initialize services
    private void initComponentsOwnerViewServices() {
		
	}
    
    //initialize appointments
    private void initComponentsOwnerViewAppointments() {
		
	}


}
