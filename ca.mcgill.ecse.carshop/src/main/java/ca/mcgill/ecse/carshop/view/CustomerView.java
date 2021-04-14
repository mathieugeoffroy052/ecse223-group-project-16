package ca.mcgill.ecse.carshop.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.TOAppointment;
import ca.mcgill.ecse.carshop.controller.TOBookableService;
import ca.mcgill.ecse.carshop.model.CarShop;

public class CustomerView extends JPanel {

	private static final long serialVersionUID = -7261632807094119148L;
	
	private JTextField createApptEnterStartDateTextField;
	private JTextField createApptEnterStartTimeTextField;
	private JTextField updateApptEnterNewDateTextField;
	private JTextField updateApptEnterNewTimeTextField;
	private JTextField newUsernameTextField;
	private JTextField newPasswordTextField;
	
	private String error;
	
	public CustomerView() {
		initialize();
	}

	private void initialize() {

		
		JLabel bookanApptText = new JLabel("Book an Appointment");
		bookanApptText.setBounds(16, 16, 136, 18);
		add(bookanApptText);
		
		JLabel chooseServiceText = new JLabel("Choose a service/service combo:");
		chooseServiceText.setBounds(16, 45, 208, 18);
		add(chooseServiceText);
		
		JComboBox<TOBookableService> createApptChooseServiceComboBox = new JComboBox<TOBookableService>();
		createApptChooseServiceComboBox.setBounds(13, 64, 216, 38);
		add(createApptChooseServiceComboBox);
		
		JLabel createApptEnterStartTimeText = new JLabel("Enter a start time: (hh:mm)");
		createApptEnterStartTimeText.setBounds(274, 73, 208, 18);
		add(createApptEnterStartTimeText);
		
		createApptEnterStartDateTextField = new JTextField();
		createApptEnterStartDateTextField.setBounds(513, 41, 130, 26);
		add(createApptEnterStartDateTextField);
		createApptEnterStartDateTextField.setColumns(10);
		
		JLabel createApptEnterStartDateText = new JLabel("Enter start date: (yyyy-mm-dd)");
		createApptEnterStartDateText.setBounds(274, 45, 227, 18);
		add(createApptEnterStartDateText);
		
		createApptEnterStartTimeTextField = new JTextField();
		createApptEnterStartTimeTextField.setBounds(513, 69, 130, 26);
		add(createApptEnterStartTimeTextField);
		createApptEnterStartTimeTextField.setColumns(10);
		
		JLabel createApptSelectOptServicesText = new JLabel("Select optional services: (leave empty if just a service)");
		createApptSelectOptServicesText.setBounds(16, 114, 339, 18);
		add(createApptSelectOptServicesText);
		
		JList<String> createApptSelectOptServicesList = new JList<String>();
		createApptSelectOptServicesList.setBounds(18, 144, 208, 70);
		add(createApptSelectOptServicesList);
		
		JButton createApptConfirmButton = new JButton("Confirm");
		createApptConfirmButton.setBounds(526, 185, 117, 29);
		add(createApptConfirmButton);
		
		JButton logOutButton = new JButton("Log out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		logOutButton.setBounds(1051, 250, 117, 29);		
		add(logOutButton);
		
		JLabel updateAppointmentText = new JLabel("Update an Appointment");
		updateAppointmentText.setBounds(16, 285, 149, 16);
		add(updateAppointmentText);
		
		JLabel updateAppointmentAndServicesText = new JLabel("Enter appointment and new service:");
		updateAppointmentAndServicesText.setBounds(16, 313, 239, 18);
		add(updateAppointmentAndServicesText);
		
		JComboBox<String> updateApptUpdateOptServicesList = new JComboBox<String>();
		updateApptUpdateOptServicesList.setBounds(13, 332, 216, 38);
		add(updateApptUpdateOptServicesList);
		
		JLabel updateApptEnterNewDateText = new JLabel("Enter new date: (yyyy-mm-dd)");
		updateApptEnterNewDateText.setBounds(274, 336, 249, 18);
		add(updateApptEnterNewDateText);
		
		updateApptEnterNewDateTextField = new JTextField();
		updateApptEnterNewDateTextField.setColumns(10);
		updateApptEnterNewDateTextField.setBounds(513, 332, 130, 26);
		add(updateApptEnterNewDateTextField);
		
		JLabel updateApptEnterNewTimeText = new JLabel("Enter new time: (hh:mm)");
		updateApptEnterNewTimeText.setBounds(274, 364, 208, 18);
		add(updateApptEnterNewTimeText);
		
		updateApptEnterNewTimeTextField = new JTextField();
		updateApptEnterNewTimeTextField.setColumns(10);
		updateApptEnterNewTimeTextField.setBounds(513, 360, 130, 26);
		add(updateApptEnterNewTimeTextField);
		
		JLabel updateApptEnterNewOptServicesText = new JLabel("Select optional services: (leave empty if just a service)");
		updateApptEnterNewOptServicesText.setBounds(16, 413, 339, 18);
		add(updateApptEnterNewOptServicesText);
		
		JList<String> updateApptEnterNewOptServicesList = new JList<String>();
		updateApptEnterNewOptServicesList.setBounds(18, 443, 208, 70);
		add(updateApptEnterNewOptServicesList);
		
		JButton updateApptButton = new JButton("Update");
		updateApptButton.setBounds(526, 484, 117, 29);
		add(updateApptButton);
		
		JComboBox<String> updateApptNewServiceSelected = new JComboBox<String>();
		updateApptNewServiceSelected.setBounds(13, 361, 216, 38);
		add(updateApptNewServiceSelected);
		
		
		GroupLayout layout = new GroupLayout(this);	// sets a layout
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel lblCancelAnAppointment = new JLabel("Cancel an Appointment");
		lblCancelAnAppointment.setBounds(16, 573, 149, 16);
		add(lblCancelAnAppointment);
		
		JComboBox<String> cancelApptSelectApptBox = new JComboBox<String>();
		cancelApptSelectApptBox.setBounds(13, 590, 216, 38);
		add(cancelApptSelectApptBox);
		
		JButton confirmCancelApptButton = new JButton("Confirm");
		confirmCancelApptButton.setBounds(241, 594, 117, 29);
		add(confirmCancelApptButton);
		
		JLabel updateAccountInfoText = new JLabel("Update Account Information");
		updateAccountInfoText.setBounds(923, 114, 178, 18);
		add(updateAccountInfoText);
		
		newUsernameTextField = new JTextField();
		newUsernameTextField.setColumns(10);
		newUsernameTextField.setBounds(1036, 156, 130, 26);
		add(newUsernameTextField);
		
		newPasswordTextField = new JTextField();
		newPasswordTextField.setColumns(10);
		newPasswordTextField.setBounds(1036, 194, 130, 26);
		add(newPasswordTextField);
		
		JLabel newUsernameText = new JLabel("New Username:");
		newUsernameText.setBounds(923, 159, 103, 18);
		add(newUsernameText);
		
		JLabel newPasswordText = new JLabel("New Password:");
		newPasswordText.setBounds(923, 199, 103, 18);
		add(newPasswordText);
		
		JButton deleteAccountButton = new JButton("Delete Account");
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		deleteAccountButton.setBounds(916, 250, 136, 29);
		add(deleteAccountButton);

		// action listeners
		
		// listeners for driver
		createApptConfirmButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createApptConfirmButtonActionPerformed(evt);
			}

			private void createApptConfirmButtonActionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				error = null;
				
				// call the controller
//				try {
//					
////					CarShopController.CreateAppointmentWithOptServices(CarShopApplication.getCurrentUser(),);
//				} catch (InvalidInputException e) {
//					error = e.getMessage();
//				}
				
				// update visuals
				refreshData();
			}
		});
	}
	
	private void refreshData() {
	}

}
