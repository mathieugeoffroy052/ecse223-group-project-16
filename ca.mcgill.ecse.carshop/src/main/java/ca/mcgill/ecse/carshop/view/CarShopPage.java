//package ca.mcgill.ecse.carshop.view;
//
//import java.awt.Color;
//
//import javax.swing.*;
//
//public class CarShopPage extends JFrame{
//	
//	private static final long serialVersionUID = -4426310869335015542L;
//
//		
//	//UI elements
//	private JLabel errorMessage;
//	private JLabel username;
//	private JLabel password;
//	private JTextField usernameBox;
//	private JPasswordField passwordBox;
//	private JButton loginButton;
//	
//	
//	private void initComponents() {
//		
//		//LOGIN WINDOW
//		errorMessage = new JLabel();
//		errorMessage.setForeground(Color.RED);
//		
//		username = new JLabel("Username");
//		password = new JLabel("Password");
//		
//		usernameBox = new JTextField();
//		passwordBox = new JPasswordField();
//		
//		loginButton = new JButton("Login");
//		
//		//button listeners
//		loginButton.addActionListener(new java.awt.event.ActionListener(){
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				loginButtonActionPerformed(evt);
//			}
//		});
//		
//		
//		//layout
//		//for some reason I get an exception, even if I feel
//		//I followed what Percy did, but on a dumbed down scale
//		GroupLayout layout = new GroupLayout(getContentPane());
//		getContentPane().setLayout(layout);
//		layout.setAutoCreateGaps(true);
//		layout.setAutoCreateContainerGaps(true);
//		layout.setHorizontalGroup(
//				layout.createSequentialGroup()
//				.addGroup(layout.createParallelGroup())
////					.addComponent(errorMessage)
//					.addComponent(username)
//					.addComponent(password)
//					.addComponent(usernameBox)
//					.addComponent(passwordBox)
//					.addComponent(loginButton)
//				);
//		
//		layout.setHorizontalGroup(
//				layout.createParallelGroup()
//				.addGroup(layout.createSequentialGroup()
//						.addComponent(errorMessage)
//						)
//				);
//		pack();
//	}
//	
//	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
//		//TODO
//	}
//	
//	private void refreshData() {
//		//TODO
//	}
//	
//	
//	//initializes the display (gets called in the Application)
//	public CarShopPage() {
//		initComponents();
//		refreshData();
//	}
//
//}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcgill.ecse.carshop.view;

import java.awt.event.*;
import javax.swing.*;

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;



public class CarShopPage extends JFrame {
	
	private static final long serialVersionUID = -4426310869335015542L;

	
    // Variables declaration
    private JButton buttonLogin; 			//login button
    private JLabel labelWindow;				
    private JLabel labelUsername;			//username label
    private JLabel labelPassword;			//password label
    private JPanel panelName;				
    private JPasswordField textPassword;	//password text box
    private JTextField textUsername;		//username text box
    private JLabel errorMessage;			//error popup label
    private String error;
    //TODO
    //need to add the error message to the display, and send a confirmation that the user is logged in if it's successful.
    


    public CarShopPage() {
        initComponents();
     
        //found this code on the internet, but don't see a use for it...?
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(CarShopPage.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(CarShopPage.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(CarShopPage.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(CarShopPage.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
        
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
        pack();
    }
    
//    public static void main(String args[]) {
//
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(CarShopPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(CarShopPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(CarShopPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(CarShopPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new CarShopPage().setVisible(true);
//            }
//        });
//    }


}