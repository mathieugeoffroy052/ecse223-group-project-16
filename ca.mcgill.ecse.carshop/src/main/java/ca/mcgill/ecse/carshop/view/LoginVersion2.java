package ca.mcgill.ecse.carshop.view;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class LoginVersion2 extends JPanel {
	
    private JButton buttonLogin; 			//login button
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


	/**
	 * Create the panel.
	 */
	public LoginVersion2() {
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

//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        
        //layout
        labelWindow.setText("Car Shop Login");
        
		setLayout(null);
		
		labelUsername = new JLabel("Username");
		labelUsername.setBounds(761, 493, 77, 16);
		add(labelUsername);
		
		labelPassword = new JLabel("Password");
		labelPassword.setBounds(761, 521, 77, 16);
		add(labelPassword);
		
		textUsername = new JTextField();
		textUsername.setBounds(850, 488, 184, 26);
		add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(850, 516, 184, 26);
		add(textPassword);
		textPassword.setColumns(10);
		
		buttonSignup = new JButton("Sign up");
		buttonSignup.setBounds(771, 566, 117, 29);
		add(buttonSignup);
		
		buttonLogin = new JButton("Log in");
		buttonLogin.setBounds(900, 566, 117, 29);
		add(buttonLogin);

//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panelName);
//        panelName.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addGap(171, 171, 171)
//                .addComponent(labelWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(180, 180, 180))
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addGap(34, 34, 34)
//                .addComponent(labelWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGap(34, 34, 34))
//        );
//
//        labelUsername.setText("Username");
//        labelPassword.setText("Password");
//        buttonLogin.setText("Login");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(panelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
//                        .addGap(43, 43, 43)
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(labelUsername)
//                            .addComponent(labelPassword))
//                        .addGap(18, 18, 18)
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(textUsername)
//                            .addComponent(textPassword)))
//                    .addGroup(layout.createSequentialGroup()
//                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                        .addComponent(buttonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                .addGap(54, 54, 54))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addComponent(panelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(18, 18, 18)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(labelUsername)
//                    .addComponent(textUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addGap(18, 18, 18)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(labelPassword)
//                    .addComponent(textPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addGap(18, 18, 18)
//                .addComponent(buttonLogin)
//                .addContainerGap(66, Short.MAX_VALUE))
//        );
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        
        labelApplication = new JLabel("Car shop application");
        labelApplication.setBounds(761, 402, 142, 16);
        add(labelApplication);
        
        labelInstructions1 = new JLabel("Enter your username and password");
        labelInstructions1.setBounds(761, 430, 273, 16);
        add(labelInstructions1);
        
        labelInstructions2 = new JLabel("To register a new account, select \"Sign up\"");
        labelInstructions2.setBounds(761, 458, 273, 16);
        add(labelInstructions2);
        
        labelError = new JLabel("error-area-remove-later-just-for-show----");
        labelError.setBounds(761, 548, 273, 16);
        add(labelError);
	}	
}

