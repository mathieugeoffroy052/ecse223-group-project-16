package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.FocusEvent;
//import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse.carshop.controller.CarShopController;


@SuppressWarnings("serial")
public class OwnerViewProfile extends JPanel{
	// UI elements
	private JLabel titleJLabel;
	
	private JLabel usernameJLabel;
	private JLabel passwordLabel;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	
	private JButton editPasswordButton;
	private JLabel errorLabel;
	private JLabel prompt;
	
	private String error;
	
	public OwnerViewProfile() {
		// Initializing UI elements
		this.setLayout(new GridBagLayout());
		titleJLabel = new JLabel("Profile");
		titleJLabel.setFont(new Font("Arial", Font.BOLD, 22));
		
		errorLabel = new JLabel(error);
		errorLabel.setForeground(Color.RED);
		prompt = new JLabel();
		prompt.setForeground(Color.BLUE);
		prompt.setVisible(false);
		
		usernameJLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		
		String username = CarShopController.getCurrentUser();
		String password = CarShopController.getUser().getPassword();
		
		usernameTextField = new JTextField(username);
		passwordTextField = new JTextField(password);
		usernameTextField.setEditable(false);
		passwordTextField.setEditable(false);
		
		editPasswordButton = new JButton("Edit password");
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
				
		// horizontal group
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(titleJLabel)
				.addComponent(errorLabel)
				.addComponent(usernameJLabel)
				.addComponent(usernameTextField)
				.addComponent(passwordLabel)
				.addComponent(prompt)
				.addGroup(layout.createSequentialGroup()
						.addComponent(passwordTextField)
						.addComponent(editPasswordButton)));
		
		// vertical group
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(titleJLabel)
				.addComponent(errorLabel)
				.addComponent(usernameJLabel)
				.addComponent(usernameTextField)
				.addComponent(passwordLabel)
				.addComponent(prompt)
				.addGroup(layout.createParallelGroup()
						.addComponent(passwordTextField)
						.addComponent(editPasswordButton)));
		
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {usernameJLabel, usernameTextField, passwordLabel, passwordTextField, prompt});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {usernameJLabel, usernameTextField, passwordLabel, passwordTextField, editPasswordButton, prompt});
				
		editPasswordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editPasswordActionPerformed(e);
				prompt.setText("Press enter to confirm");
			}
		});
		
		passwordTextField.addKeyListener(new KeyListener() {
			
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
					confirmPasswordActionPerformed(e);
				}
			}
		});
		
	}
	
	// Methods tp update informations 
	private void editPasswordActionPerformed(ActionEvent event) {
		prompt.setVisible(true);
		passwordTextField.setEditable(true);
	}
	
	private void confirmPasswordActionPerformed(KeyEvent event) {
		error = "";
		
		try {
			String newPassword = passwordTextField.getText();
			CarShopController.updateUser(usernameTextField.getText(), newPassword);
		} catch (Exception e) {
			error = e.getMessage();
		}
		refreshProfile();
		
		
	}
	
	private void refreshProfile() {
		errorLabel.setText(error);
		
		if (error == null || error.length() == 0) {
			String password = CarShopController.getUser().getPassword();
			passwordTextField.setText(password);
		}
		passwordTextField.setEditable(false);
		prompt.setVisible(false);
		
		revalidate();
		repaint();
	}
	
}
