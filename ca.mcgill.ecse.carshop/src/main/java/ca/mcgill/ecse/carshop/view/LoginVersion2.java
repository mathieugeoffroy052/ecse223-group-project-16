package ca.mcgill.ecse.carshop.view;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
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
    private JRadioButton radioButton;


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
        radioButton = new JRadioButton();

//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        
        //layout
        labelWindow.setText("Car Shop Login");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width)/2;
        int y = (screenSize.height)/2;
//        int x = (int) ((dimension.getWidth()-labelWindow.getWidth())/2);
//        int y = (int) ((dimension.getHeight()-labelWindow.getHeight())/2);
        
		setLayout(null);
		
		labelUsername = new JLabel("Username");
		labelUsername.setBounds(x-100, y+100, 77, 16);
		add(labelUsername);
		
		labelPassword = new JLabel("Password");
		labelPassword.setBounds(x-100, y+75, 77, 16);
		add(labelPassword);
		
		textUsername = new JTextField();
		textUsername.setBounds(x-10, y+95, 184, 26);
		add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(x-10, y+70, 184, 26);
		add(textPassword);
		textPassword.setColumns(10);
		
		buttonSignup = new JButton("Sign up");
		buttonSignup.setBounds(x-75, y+150, 117, 29);
		add(buttonSignup);
		
		buttonLogin = new JButton("Log in");
		buttonLogin.setBounds(x+40, y+150, 117, 29);
		add(buttonLogin);
        
        labelApplication = new JLabel("Car shop application");
        labelApplication.setBounds(x-100, y-20, 142, 16);
        add(labelApplication);
        
        labelInstructions1 = new JLabel("Enter your username and password");
        labelInstructions1.setBounds(x-100, y+5, 273, 16);
        add(labelInstructions1);
        
        labelInstructions2 = new JLabel("To register a new account, select \"Sign up\"");
        labelInstructions2.setBounds(x-100, y+30, 273, 16);
        add(labelInstructions2);
        
        labelError = new JLabel("error-area-remove-later-just-for-show----");
        labelError.setBounds(x-100, y+125, 273, 16);
        add(labelError);
        
        radioButton = new JRadioButton();
        radioButton.setBounds(x,y, 10, 10);
        add(radioButton);
	}	
}

