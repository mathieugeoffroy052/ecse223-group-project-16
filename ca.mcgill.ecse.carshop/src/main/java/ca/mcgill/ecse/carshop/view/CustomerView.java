package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.TOAppointment;
import ca.mcgill.ecse.carshop.controller.TOBookableService;
import ca.mcgill.ecse.carshop.controller.TOComboItem;
import ca.mcgill.ecse.carshop.model.Customer;

import org.jdatepicker.impl.JDatePanelImpl;

import org.jdatepicker.impl.JDatePickerImpl;

import org.jdatepicker.impl.SqlDateModel;

public class CustomerView extends JPanel {

	private static final long serialVersionUID = -7261632807094119148L;
	
	private JTextField createApptEnterStartTimeTextField;
	private JTextField updateApptEnterNewTimeTextField;
	private JTextField newUsernameTextField;
	private JTextField newPasswordTextField;
	private JLabel errorMessage;
	
	private String error = "";
	
	
	private JLabel bookanApptText;
	private JLabel chooseServiceText;
	private JComboBox<String> name1;
	private JLabel createApptEnterStartTimeText;
	private JLabel createApptEnterStartDateText;
	private JLabel createApptSelectOptServicesText;
	
	private JList<String> list1;
	private DefaultListModel<String> model1;
	private JButton confirmTimeButton1;
	
	private JButton confirmButton;
	private JButton logOutButton;
	private JLabel updateAppointmentText;
	private JLabel updateAppointmentAndServicesText;
	private JComboBox<String> name2;	// swap
	private JLabel updateApptEnterNewDateText;
	private JLabel updateApptEnterNewTimeText;
	private JLabel updateApptEnterNewOptServicesText;
	
	private JList<String> list2_3;
	private DefaultListModel<String> model2_3;
	private JLabel statusUpdate;
	private JButton confirmTimeButton2;
	private JLabel statusMake;
	
	private JButton updateButton;
	private JComboBox<String> name3;	// swap
	private JLabel lblCancelAnAppointment;
	private JComboBox<String> name4;
	private JButton cancelButton;
	private JLabel updateAccountInfoText;
	private JLabel newUsernameText;
	private JLabel newPasswordText;
	private JButton deleteAccountButton;
	
	private JDatePickerImpl overviewDatePickerCreateAppt;

	private JDatePickerImpl overviewDatePickerUpdateAppt;
	
	// create appointment calendar
	private SqlDateModel createApptDateText;
	private Properties createApptDateDisplayed;

	// update appointment calendar
	private SqlDateModel updateApptDateText;
	private Properties updateApptDateDisplayed;

	private JButton plus1;
	private JButton plus2;
	private JButton plus3;

	// Matthew's table
	private JTable table;
	private DefaultTableModel modelTable;
	private JScrollPane scrollTable;
	
	// lists
//	private HashMap<Integer, String> appointments;
	private List<TOAppointment> TOAppointments;

	// for the update scenarios
	private int updateStatusCode;
	
	// for the pop-up
	private JLabel smallErrorLabel;

	// update username, password
	private JButton updateUsernameButton;
	private JButton updatePasswordButton;

		
	public CustomerView() {
		initialize();
		refreshInit();

	}



	private void initialize() {

		// *** Buttons *** //
		
		errorMessage = new JLabel("");
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(16, 648, 1000, 16);
		add(errorMessage);
		
		bookanApptText = new JLabel("Book an Appointment");
		bookanApptText.setBounds(16, 16, 136, 18);
		add(bookanApptText);
		
		chooseServiceText = new JLabel("Choose a service/service combo:");
		chooseServiceText.setBounds(16, 45, 208, 18);
		add(chooseServiceText);
		
		name1 = new JComboBox<String>(new String[0]);
		name1.setBounds(13, 64, 216, 38);
		add(name1);
		
		createApptEnterStartTimeText = new JLabel("Enter a start time: (hh:mm)");
		createApptEnterStartTimeText.setBounds(274, 73, 208, 18);
		add(createApptEnterStartTimeText);
		
		createApptEnterStartDateText = new JLabel("Enter start date: (yyyy-mm-dd)");
		createApptEnterStartDateText.setBounds(274, 45, 227, 18);
		add(createApptEnterStartDateText);
		
		createApptEnterStartTimeTextField = new JTextField();
		createApptEnterStartTimeTextField.setBounds(513, 69, 130, 26);
		add(createApptEnterStartTimeTextField);
		createApptEnterStartTimeTextField.setColumns(10);
		
		createApptSelectOptServicesText = new JLabel("Select optional services: (leave empty if just a service)");
		createApptSelectOptServicesText.setBounds(16, 114, 339, 18);
		add(createApptSelectOptServicesText);
		
		model1 = new DefaultListModel<>();
		list1 = new JList<String>(model1);
		list1.setBounds(18, 144, 208, 70);
		add(list1);
		
		confirmButton = new JButton("Confirm");
		confirmButton.setBounds(526, 185, 117, 29);
		add(confirmButton);
		
		logOutButton = new JButton("Log out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		logOutButton.setBounds(1051, 250, 117, 29);		
		add(logOutButton);
		
		updateAppointmentText = new JLabel("Update an Appointment");
		updateAppointmentText.setBounds(16, 285, 149, 16);
		add(updateAppointmentText);
		
		updateAppointmentAndServicesText = new JLabel("Enter appointment and new service:");
		updateAppointmentAndServicesText.setBounds(16, 313, 239, 18);
		add(updateAppointmentAndServicesText);
		
		name2 = new JComboBox<String>(new String[0]);
		name2.setBounds(13, 332, 216, 38);
		add(name2);
		
		updateApptEnterNewDateText = new JLabel("Enter new date: (yyyy-mm-dd)");
		updateApptEnterNewDateText.setBounds(274, 336, 249, 18);
		add(updateApptEnterNewDateText);
		
		updateApptEnterNewTimeText = new JLabel("Enter new time: (hh:mm)");
		updateApptEnterNewTimeText.setBounds(274, 364, 208, 18);
		add(updateApptEnterNewTimeText);
		
		updateApptEnterNewTimeTextField = new JTextField();
		updateApptEnterNewTimeTextField.setColumns(10);
		updateApptEnterNewTimeTextField.setBounds(513, 361, 130, 26);
		add(updateApptEnterNewTimeTextField);
		
		updateApptEnterNewOptServicesText = new JLabel("Select optional services: (leave empty if just a service)");
		updateApptEnterNewOptServicesText.setBounds(16, 413, 339, 18);
		add(updateApptEnterNewOptServicesText);
		
		model2_3 = new DefaultListModel<>();
		list2_3 = new JList<String>(model2_3);
		list2_3.setBounds(18, 443, 208, 70);
		add(list2_3);
		
		updateButton = new JButton("Update");
		updateButton.setBounds(526, 490, 117, 29);
		add(updateButton);
		
		name3 = new JComboBox<String>(new String[0]);
		name3.setBounds(13, 361, 216, 38);
		add(name3);
		
		GroupLayout layout = new GroupLayout(this);	// sets a layout
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		lblCancelAnAppointment = new JLabel("Cancel an Appointment");
		lblCancelAnAppointment.setBounds(16, 573, 149, 16);
		add(lblCancelAnAppointment);
		
		name4 = new JComboBox<String>(new String[0]);
		name4.setBounds(13, 590, 216, 38);
		add(name4);
		
		cancelButton = new JButton("Confirm");
		cancelButton.setBounds(241, 594, 117, 29);
		add(cancelButton);
		
		updateAccountInfoText = new JLabel("Update Account Information");
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
		
		newUsernameText = new JLabel("New Username:");
		newUsernameText.setBounds(923, 159, 103, 18);
		add(newUsernameText);
		
		newPasswordText = new JLabel("New Password:");
		newPasswordText.setBounds(923, 199, 103, 18);
		add(newPasswordText);
		
		deleteAccountButton = new JButton("Delete Account");
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		deleteAccountButton.setBounds(916, 250, 136, 29);
		add(deleteAccountButton);
		
		plus1 = new JButton("Log");
		plus1.setBounds(221, 68, 50, 29);
		add(plus1);
		
		plus2 = new JButton("Log");
		plus2.setBounds(221, 336, 50, 29);
		add(plus2);
		
		plus3 = new JButton("Log");
		plus3.setBounds(221, 365, 50, 29);
		add(plus3);
		
		
		// table - by Matthew
		table = new JTable();
		//make the table non editable (for the user. the table will still get updated):
		modelTable = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelTable.addColumn("ServiceBooking");
		modelTable.addColumn("Start Date");
		modelTable.addColumn("Start Time");
		modelTable.addColumn("Status");
		table.setModel(modelTable);
		
		scrollTable = new JScrollPane(table);
		scrollTable.setBounds(700, 336 ,464, 268);
		scrollTable.setVisible(true);
		add(scrollTable);
		// end of Table - by Matthew
		
		
		LocalDate now = LocalDate.now();
		
		// create appointment calendar
		createApptDateText = new SqlDateModel();
		createApptDateText.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		createApptDateText.setSelected(true);
		createApptDateDisplayed = new Properties();
		createApptDateDisplayed.put("text.today", "Today");
		createApptDateDisplayed.put("text.month", "Month");
		createApptDateDisplayed.put("text.year", "Year");
		JDatePanelImpl overviewDatePanel = new JDatePanelImpl(createApptDateText, createApptDateDisplayed);
		overviewDatePickerCreateAppt = new JDatePickerImpl(overviewDatePanel, new DateLabelFormatter());
		overviewDatePickerCreateAppt.setBounds(513, 41, 145, 26);//513, 41, 130, 26
		add(overviewDatePickerCreateAppt);
		
		
		// update appointment calendar
		updateApptDateText = new SqlDateModel();
		updateApptDateText.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		updateApptDateText.setSelected(true);
		updateApptDateDisplayed = new Properties();
		updateApptDateDisplayed.put("text.today", "Today");
		updateApptDateDisplayed.put("text.month", "Month");
		updateApptDateDisplayed.put("text.year", "Year");
		JDatePanelImpl overviewDatePanel1 = new JDatePanelImpl(updateApptDateText, updateApptDateDisplayed);
		overviewDatePickerUpdateAppt = new JDatePickerImpl(overviewDatePanel1, new DateLabelFormatter());
		overviewDatePickerUpdateAppt.setBounds(513, 332, 145, 26);//513, 332, 130, 26
		add(overviewDatePickerUpdateAppt);

		
		statusUpdate = new JLabel("Current Status: Nothing changed");
		statusUpdate.setForeground(new Color(60, 179, 113));
		statusUpdate.setBounds(16, 525, 627, 16);
		add(statusUpdate);
		
		confirmTimeButton2 = new JButton("Confirm Time");
		confirmTimeButton2.setBounds(513, 390, 130, 29);
		add(confirmTimeButton2);
		
		confirmTimeButton1 = new JButton("Confirm Time");
		confirmTimeButton1.setBounds(513, 96, 130, 29);
		add(confirmTimeButton1);
		
		statusMake= new JLabel("Current Status: Nothing added");
		statusMake.setForeground(new Color(60, 179, 113));
		statusMake.setBounds(16, 226, 627, 16);
		add(statusMake);
		
		updateUsernameButton = new JButton("Update username");
		updateUsernameButton.setBounds(1170, 156, 153, 29);
		add(updateUsernameButton);

		updatePasswordButton = new JButton("Update password");
		updatePasswordButton.setBounds(1170, 194, 153, 29);
		add(updatePasswordButton);
		
		JLabel lblNewLabel_3 = new JLabel("Welcome to the Appointment System! As a customer, you can make an appointment, update and cancel an appointment. To keep track of anything you choose in the Combo Box, press \"log\" once you've selected an option.");
		lblNewLabel_3.setBounds(13, 677, 1500, 16);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Updating each appointment contains three scenarios: updating the time of a single appointment (in which only the service and new date be entered), updating the appointment's servicebooking to a new servicebooking,");
		lblNewLabel_4.setBounds(13, 705, 1390, 16);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("(in which only the two combo boxes need be changed), and adding optional services. Once you have selected these options, click \"Update\" and your table will be successfully updated.");
		lblNewLabel_4_1.setBounds(13, 733, 1500, 16);
		add(lblNewLabel_4_1);
		

		// *** Action Listeners *** //

		// create an appointment via the "+" button
		plus1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { plus1ActionPerformed(evt); 
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// update an appointment via the "+" button
		plus2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { plus2ActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// update the optional services via the "+" button
		plus3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { plus3ActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		// update the time 1
		confirmTimeButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { confirmTimeButton1ActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		// update the time 2
		confirmTimeButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { confirmTimeButton2ActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// listeners for create appointment button
		confirmButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { confirmButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// listeners for create appointment button
		updateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { updateButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});

		// listeners for cancel appointment button
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { cancelButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		// *** Account Information *** //
		deleteAccountButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { deleteAccountButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		logOutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { logOutButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		updateUsernameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { updateUsernameButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		updatePasswordButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { updatePasswordButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
	}
	
	
	
	private void refreshInit() {
		
//		appointments = new HashMap<Integer, String>();
		if(name1.getSelectedIndex()==-1) {
			name1.removeAllItems();
		}
//		Integer index = 0;
		name2.addItem("Select...");
		name4.addItem("Select...");
		TOAppointments = new ArrayList<>();
		for (TOAppointment appt : CarShopController.getCustomerAppointments(CarShopApplication.getCurrentUser())) {
//			appointments.put(index, appt.getServiceName());
			TOAppointments.add(appt);
			name2.addItem(appt.getServiceName());
			name4.addItem(appt.getServiceName());
//			index++;
		};
		
		// get all the services in the carshop's services
		name1.addItem("Select...");
		name3.addItem("Select...");
		for(TOBookableService sb : CarShopController.getCarShopBookableServices()) {
			name1.addItem(sb.getName());
			name3.addItem(sb.getName());	// not sure if this one is the right box

		}
		

		for(TOAppointment TOApp: TOAppointments) {
			
			Vector<String> r = new Vector<String>();
			r.addElement(TOApp.getServiceName());
			r.addElement(TOApp.getDate().toString());
			r.addElement(TOApp.getStartTime().toString());
			r.addElement(TOApp.getStatus());
			
			modelTable.addRow(r);
		}
	}
	
	
	
	private void refreshData() {
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			
			createApptEnterStartTimeTextField.setText("");
			updateApptEnterNewTimeTextField.setText("");

			name1.removeAllItems();
			name2.removeAllItems();
			name3.removeAllItems();
			name4.removeAllItems();
			
			name1.addItem("Select...");
			name2.addItem("Select...");
			name3.addItem("Select...");
			name4.addItem("Select...");
			
//			Integer index = 0;
//			appointments = new HashMap<Integer, String>();
			if(TOAppointments!=null) {
				for(int i = TOAppointments.size()-1; i >= 0; i--) {
					TOAppointments.remove(i);
				}
//				TOAppointments.removeAll(TOAppointments);
				for (TOAppointment appt : CarShopController.getCustomerAppointments(CarShopApplication.getCurrentUser())) {
//					if(appointments.put(index, appt.getServiceName())==null) {
						name2.addItem(appt.getServiceName());
						name4.addItem(appt.getServiceName());
						TOAppointments.add(appt);
//					}
//					index++;
				};
			}
			
			name1.setSelectedIndex(0);
			name2.setSelectedIndex(0);
			name3.setSelectedIndex(0);
			name4.setSelectedIndex(0);

			updateStatusCode = 0;
			confirmTimeButton1.setForeground(Color.black);

			list2_3.removeAll();
			statusMake.setText("Current Status: Nothing added");
			statusUpdate.setText("Current Status: Nothing changed");
			
			for(TOBookableService sb : CarShopController.getCarShopBookableServices()) {
				name1.addItem(sb.getName());
				name3.addItem(sb.getName());	// not sure if this one is the right box

			}
			
			for(int i = 0; i < modelTable.getRowCount(); i++) {
				modelTable.removeRow(i);
			}
			
			for(TOAppointment TOApp: TOAppointments) {
				
				Vector<String> r = new Vector<String>();
				r.addElement(TOApp.getServiceName());
				r.addElement(TOApp.getDate().toString());
				r.addElement(TOApp.getStartTime().toString());
				r.addElement(TOApp.getStatus());
				modelTable.addRow(r);
			}		
		}
	}
	


	private void plus1ActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		String apptName = name1.getItemAt(name1.getSelectedIndex());
		statusMake.setText("Current Status: Nothing added");
		confirmTimeButton1.setForeground(Color.red);
		if(!apptName.equals("Select...")) {
			try {	// if it's an instance of servicecombo
				List<TOComboItem> list1 = CarShopController.getOptServicesWithName(apptName);
				model1.removeAllElements();
				for(int i = 0; i < list1.size(); i++ ) {
				  model1.addElement(list1.get(i).getName());
				}
			} catch(Exception e) {	// otherwise, the optional services list is empty (it's a service)
				model1.removeAllElements();
			}	
		}
		
	}
	
	
	
	private void plus2ActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		String apptName = name2.getItemAt(name2.getSelectedIndex());
		if(!apptName.equals("Select...")) {
			
			try {	// if it's an instance of servicecombo
				name3.setSelectedIndex(0);
				statusUpdate.setText("Current Status(3): Add optional services for " + apptName + " (select above)");
				updateStatusCode = 3;
				List<TOComboItem> list1 = CarShopController.getOptServicesWithName(apptName);
				model2_3.removeAllElements();
				for(int i = 0; i < list1.size(); i++ ) {
				  model2_3.addElement(list1.get(i).getName());
				}
			} catch(Exception e) {	// otherwise, the list is empty (it's a service)
				model2_3.removeAllElements();
			}
		}
		
	}
	
	
	
	private void plus3ActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		String apptName = name3.getItemAt(name3.getSelectedIndex());
		if(!apptName.equals("Select...")) {			
			try {	// if it's an instance of servicecombo
				if(!name2.getItemAt(name2.getSelectedIndex()).equals("Select...")) {
					statusUpdate.setText("Current Status(1): Update "+name2.getItemAt(name2.getSelectedIndex())+" to "+apptName);
					updateStatusCode = 1;
				}
				updateApptEnterNewTimeTextField.setText("");
				List<TOComboItem> list1 = CarShopController.getOptServicesWithName(apptName);
				model2_3.removeAllElements();
				for(int i = 0; i < list1.size(); i++ ) {
				  model2_3.addElement(list1.get(i).getName());
				}
			} catch(Exception e) {	// otherwise, the list is empty (it's a service)
				model2_3.removeAllElements();
			}
		}
		
	}
	

	
	private void confirmTimeButton1ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		confirmTimeButton1.setForeground(Color.black);
		String monthString = String.valueOf(createApptDateText.getMonth()+1);
		if((createApptDateText.getMonth()+1)<10) monthString = "0"+(createApptDateText.getMonth()+1);
		String dayString = String.valueOf(createApptDateText.getDay());
		if(createApptDateText.getDay()<10) dayString = "0"+createApptDateText.getDay();
		String dateString = createApptDateText.getYear()+"-"+monthString+"-"+dayString;
		String time = createApptEnterStartTimeTextField.getText();
		if(createApptEnterStartTimeTextField.getText().charAt(1)==':') {
			time = "0"+createApptEnterStartTimeTextField.getText();
		}
		statusMake.setText("Current Status: Create an appointment for "+name1.getItemAt(name1.getSelectedIndex()) + " at "+ time + " on " + dateString);
	}
	
	
	
	private void confirmTimeButton2ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(!name2.getItemAt(name2.getSelectedIndex()).equals("Select...") && !updateApptEnterNewTimeTextField.getText().equals("")) {
			String monthString = String.valueOf(updateApptDateText.getMonth()+1);
			if((updateApptDateText.getMonth()+1)<10) monthString = "0"+(updateApptDateText.getMonth()+1);
			String dayString = String.valueOf(updateApptDateText.getDay());
			if(updateApptDateText.getDay()<10) dayString = "0"+updateApptDateText.getDay();
			String dateString = updateApptDateText.getYear()+"-"+monthString+"-"+dayString;
			String time = updateApptEnterNewTimeTextField.getText();
			if(updateApptEnterNewTimeTextField.getText().charAt(1)==':') {
				time = "0"+updateApptEnterNewTimeTextField.getText();
			}
			statusUpdate.setText("Current Status(2): Update " + name2.getItemAt(name2.getSelectedIndex()) + " to " + time + " at " + dateString);
			updateStatusCode = 2;
		}
		name3.setSelectedIndex(0);
	}
	
	
	// Assumption: services selected optionally will occur 10 minutes after one another.
	private void confirmButtonActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		

		List<String> list = list1.getSelectedValuesList();
		String optServices = "";
		for(String s : list) {
			optServices += s+",";
		}
		String customer = CarShopApplication.getCurrentUser();
		String sbName = name1.getItemAt(name1.getSelectedIndex());
		String currentDateAndTime = CarShopApplication.getSystemDateTime();
		String monthString = String.valueOf(createApptDateText.getMonth()+1);
		if((createApptDateText.getMonth()+1)<10) monthString = "0"+(createApptDateText.getMonth()+1);
		String dayString = String.valueOf(createApptDateText.getDay());
		if(createApptDateText.getDay()<10) dayString = "0"+createApptDateText.getDay();
		String dateString = createApptDateText.getYear()+"-"+monthString+"-"+dayString;
		String time = createApptEnterStartTimeTextField.getText();
		if(createApptEnterStartTimeTextField.getText().charAt(1)==':') {
			time = "0"+createApptEnterStartTimeTextField.getText();
		}
		try {
		CarShopController.createAppointmentAt(customer, sbName, optServices, dateString, time, currentDateAndTime);
		} catch(Exception e) {
			error = e.getMessage();
			errorMessage.setText(error);
		}
			
		// update visuals
		refreshData();
	}
	

	
	/*
	 * There are three cases here:
	 * @when the customer changes the BookableService for their appointment to another BookableService:			updateStatusCode = 1;	// serv1 -> serv2
	 *  - only need name2 and name3 # done
	 * @When the customer changes the date and time to another date and time:									updateStatusCode = 2;	// time1 -> time2
	 *  - only need name2 and date,time fields # done - TODO bug with date picker
	 * @When the customer adds optService(s) to a service combo with start time specific to that optService:	updateStatusCode = 3;	// addOptServices
	 *  - only need list2_3 and name2 # 
	 * Assumption: services selected optionally will occur 10 minutes after one another.
	 */
	private void updateButtonActionPerformed(ActionEvent evt) {
		error = "";
		
		try {
			// depends on 3 states
			if(updateStatusCode == 0) {
				return;
			}
			else if(updateStatusCode == 1) {	// serv1 -> serv2
				String timeOfChange = CarShopApplication.getSystemDateTime();
				String serviceName = name3.getItemAt(name3.getSelectedIndex());
				String prevServName = name2.getItemAt(name2.getSelectedIndex());
				String username = CarShopApplication.getCurrentUser();
				TOAppointment prevTOAppt = null;
				for(TOAppointment toa : TOAppointments) {
					if(toa.getServiceName().equals(prevServName)) {
						prevTOAppt = toa;
					}
				}
				CarShopController.updateAppointmentCase1(username, prevTOAppt, serviceName, timeOfChange);
				System.out.println("\n--------------------------\n\n\n");
				for(int i = 0; i < TOAppointments.size(); i++) {
					System.out.println(TOAppointments.get(i));

				}
			}
			else if(updateStatusCode == 2) {	// time1 -> time2
				String timeOfChange = CarShopApplication.getSystemDateTime();
				String prevServName = name2.getItemAt(name2.getSelectedIndex());
				String username = CarShopApplication.getCurrentUser();
				TOAppointment prevTOAppt = null;
				for(TOAppointment toa : TOAppointments) {
					if(toa.getServiceName().equals(prevServName)) {
						prevTOAppt = toa;
					}
				}
				String monthString = String.valueOf(updateApptDateText.getMonth()+1);
				if((updateApptDateText.getMonth()+1)<10) monthString = "0"+(updateApptDateText.getMonth()+1);
				String dayString = String.valueOf(updateApptDateText.getDay());
				if(updateApptDateText.getDay()<10) dayString = "0"+updateApptDateText.getDay();
				String dateString = updateApptDateText.getYear()+"-"+monthString+"-"+dayString;
				String time = updateApptEnterNewTimeTextField.getText();
				if(updateApptEnterNewTimeTextField.getText().charAt(1)==':') {
					time = "0"+updateApptEnterNewTimeTextField.getText();
				}
				CarShopController.updateAppointmentCase2(username, prevTOAppt, dateString, time, timeOfChange);
				System.out.println("\\n--------------------------\\n\\n\\n");
				for(int i = 0; i < TOAppointments.size(); i++) {
					System.out.println(TOAppointments.get(i));

				}
			} 
			else if(updateStatusCode == 3) {	// addOptServices
				String timeOfChange = CarShopApplication.getSystemDateTime();
				String username = CarShopApplication.getCurrentUser();
				String servName = name2.getItemAt(name2.getSelectedIndex());
				List<String> list = list2_3.getSelectedValuesList();
				String optServices = "";
				for(String s : list) {
					optServices += s+",";
				}
				String time = updateApptEnterNewTimeTextField.getText();
				if(updateApptEnterNewTimeTextField.getText().charAt(1)==':') {
					time = "0"+updateApptEnterNewTimeTextField.getText();
				}
				TOAppointment prevTOAppt = null;
				for(TOAppointment toa : TOAppointments) {
					if(toa.getServiceName().equals(servName)) {
						prevTOAppt = toa;
					}
				}
				CarShopController.updateAppointmentCase3(username, prevTOAppt, optServices, time, timeOfChange);
				System.out.println("\\n--------------------------\\n\\n\\n");
				for(int i = 0; i < TOAppointments.size(); i++) {
					System.out.println(TOAppointments.get(i));

				}
			}
							
		} catch (Exception e) {
			error += e.getMessage();
			errorMessage.setText(error);

		}
		
		refreshData();
	}
	
	

	private void cancelButtonActionPerformed(ActionEvent evt) {
		error = "";
		String username = CarShopApplication.getCurrentUser();

		try {
			String currentDateAndTime = CarShopApplication.getSystemDateTime();
			String servName = name4.getItemAt(name4.getSelectedIndex());
			TOAppointment prevTOAppt = null;
			for(TOAppointment toa : TOAppointments) {
				if(toa.getServiceName().equals(servName)) {
					prevTOAppt = toa;
				}
			}
			CarShopController.cancelAppointmentCase1(prevTOAppt, username, currentDateAndTime);
		} catch (Exception e) {
			error += e.getMessage();
			errorMessage.setText(error);
		}
		
		refreshData();
	}
	
	

	private void updateUsernameButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		error = "";
		CarShopController.updateUsername(CarShopApplication.getCurrentUser(), newUsernameTextField.getText());
		error += "New username is "+newUsernameTextField.getText();
		errorMessage.setText(error);
	}
	
	

	private void updatePasswordButtonActionPerformed(ActionEvent evt) throws InterruptedException {
		// TODO Auto-generated method stub
		error = "";
		CarShopController.updatePassword(CarShopApplication.getCurrentUser(), newPasswordTextField.getText());
		error += "New password is "+newPasswordTextField.getText();
		errorMessage.setText(error);

	}
	
	
	
	private void logOutButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		error = "";
		CarShopApplication.logOut();
		error += "Now logged out. Close application and log in again to continue.";
		errorMessage.setText(error);
		for(int i = 0; i < modelTable.getRowCount(); i++) {
			modelTable.removeRow(i);
		}
		
		createApptEnterStartTimeTextField.setText("");
		updateApptEnterNewTimeTextField.setText("");

		name1.removeAllItems();
		name2.removeAllItems();
		name3.removeAllItems();
		name4.removeAllItems();
		
		name1.addItem("Select...");
		name2.addItem("Select...");
		name3.addItem("Select...");
		name4.addItem("Select...");
		
		name1.setSelectedIndex(0);
		name2.setSelectedIndex(0);
		name3.setSelectedIndex(0);
		name4.setSelectedIndex(0);

		updateStatusCode = 0;
		confirmTimeButton1.setForeground(Color.black);

		list2_3.removeAll();
		statusMake.setText("You are logged out.");
		statusUpdate.setText("You are logged out.");
		
		for(int i = 0; i < modelTable.getRowCount(); i++) {
			modelTable.removeRow(i);
		}
		
	}
	

	
	private void deleteAccountButtonActionPerformed(ActionEvent evt) {
		// TODO 
		JFrame popUpRemoveAccount = new JFrame();
		smallErrorLabel = new JLabel();
		smallErrorLabel.setForeground(Color.RED);
		smallErrorLabel.setText("Close window to cancel.");
		JLabel addTitle = new JLabel("Are you sure?");
		addTitle.setFont(new Font("Arial", Font.BOLD, 22));
		JLabel confirmationText = new JLabel("");
		JLabel userLabel = new JLabel("Username:");
		JTextField usernameField = new JTextField();
		usernameField.setSize(200, 20);
		JTextField passwordField = new JTextField();
		passwordField.setSize(200, 20);
		JLabel passLabel = new JLabel("Password:");	
		JButton confirmDeleteButton = new JButton("Delete Account");
		
		
		GroupLayout layout = new GroupLayout(popUpRemoveAccount.getContentPane());
		popUpRemoveAccount.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
					.addComponent(smallErrorLabel)
					.addComponent(addTitle)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(userLabel)
									.addComponent(passLabel))
							.addGroup(layout.createParallelGroup()
									.addComponent(confirmationText)
									.addComponent(usernameField)
									.addComponent(passwordField)))
					.addComponent(confirmDeleteButton));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(smallErrorLabel)
					.addComponent(addTitle)
					.addGroup(layout.createParallelGroup()
							.addComponent(confirmationText))
					.addGroup(layout.createParallelGroup()
							.addComponent(userLabel)
							.addComponent(usernameField))
					.addGroup(layout.createParallelGroup()
							.addComponent(passLabel)
							.addComponent(passwordField))
					.addComponent(confirmDeleteButton));
		
		popUpRemoveAccount.pack();
		centerWindow(popUpRemoveAccount);
		popUpRemoveAccount.setVisible(true);
		
		confirmDeleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				error = "";
				try {
					String newUsername = usernameField.getText();
					CarShopController.getCustomerByUsername(newUsername);
				} catch (Exception e) {
					error += e.getMessage();
					errorMessage.setText(error);
					return;
				}
				try {
					String newUsername = usernameField.getText();
					String newPassword = passwordField.getText();
					CarShopController.checkIfPasswordCorrect(newUsername, newPassword);
					confirmDeleteButtonActionPerformed(evt, popUpRemoveAccount, newUsername);
				} catch(Exception e) {
					error += e.getMessage();
					errorMessage.setText(error);
				}
			}
		});		
		
	}
	
	
	
	protected void confirmDeleteButtonActionPerformed(ActionEvent evt, JFrame frame, String newUsername) {
		error = "";
		try {
			for(TOAppointment TOAppt : TOAppointments) {
				CarShopController.cancelAppointmentCase1(TOAppt, CarShopApplication.getCurrentUser(), CarShopApplication.getSystemDateTime());
			}
			CarShopController.deleteCustomerAccount(newUsername);
			frame.setVisible(false);
		} catch (Exception e) {
		}
		error += "Account deleted. Can no longer access appointment services.";
		errorMessage.setText(error);
		for(int i = 0; i < modelTable.getRowCount(); i++) {
			modelTable.removeRow(i);
		}
		refreshData();
		frame.pack();
		
	}


	
	public static void centerWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	
	}
}
