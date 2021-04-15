package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.TOAppointment;
import ca.mcgill.ecse.carshop.controller.TOComboItem;

import org.jdatepicker.impl.JDatePanelImpl;

import org.jdatepicker.impl.JDatePickerImpl;

import org.jdatepicker.impl.SqlDateModel;

public class CustomerView extends JPanel {

	private static final long serialVersionUID = -7261632807094119148L;
	
	private JTextField createApptEnterStartDateTextField;
	private JTextField createApptEnterStartTimeTextField;
	private JTextField updateApptEnterNewDateTextField;
	private JTextField updateApptEnterNewTimeTextField;
	private JTextField newUsernameTextField;
	private JTextField newPasswordTextField;
	private JLabel errorMessage;
	
	private String error = "";
	
	
	private JLabel bookanApptText;
	private JLabel chooseServiceText;
	private JComboBox<String> createApptChooseServiceComboBox;
	private JLabel createApptEnterStartTimeText;
	private JLabel createApptEnterStartDateText;
	private JLabel createApptSelectOptServicesText;
	private JList<String> createApptSelectOptServicesList;
	private JButton createApptConfirmButton;
	private JButton logOutButton;
	private JLabel updateAppointmentText;
	private JLabel updateAppointmentAndServicesText;
	private JComboBox<String> updateApptUpdateOptServicesList;
	private JLabel updateApptEnterNewDateText;
	private JLabel updateApptEnterNewTimeText;
	private JLabel updateApptEnterNewOptServicesText;
	private JList<String> updateApptEnterNewOptServicesList;
	private JButton updateApptButton;
	private JComboBox<String> updateApptNewServiceSelected;
	private JLabel lblCancelAnAppointment;
	private JComboBox<String> cancelApptSelectApptBox;
	private JButton confirmCancelApptButton;
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

	private JButton plusCreateAppt;
	private JButton plusUpdateAppt;
	private JButton plusUpdateServiceOfAppt;

	
	// lists
	private HashMap<Integer, String> appointments;
		
	public CustomerView() {
		initialize();
		refreshData();

	}

	private void initialize() {

		// *** Buttons *** //
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(6, 648, 97, 16);
		add(errorMessage);
		
		bookanApptText = new JLabel("Book an Appointment");
		bookanApptText.setBounds(16, 16, 136, 18);
		add(bookanApptText);
		
		chooseServiceText = new JLabel("Choose a service/service combo:");
		chooseServiceText.setBounds(16, 45, 208, 18);
		add(chooseServiceText);
		
		createApptChooseServiceComboBox = new JComboBox<String>(new String[0]);
		createApptChooseServiceComboBox.setBounds(13, 64, 216, 38);
		add(createApptChooseServiceComboBox);
		
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
		
		createApptSelectOptServicesList = new JList<String>(new String[0]);
		createApptSelectOptServicesList.setBounds(18, 144, 208, 70);
		add(createApptSelectOptServicesList);
		
		createApptConfirmButton = new JButton("Confirm");
		createApptConfirmButton.setBounds(526, 185, 117, 29);
		add(createApptConfirmButton);
		
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
		
		updateApptUpdateOptServicesList = new JComboBox<String>(new String[0]);
		updateApptUpdateOptServicesList.setBounds(13, 332, 216, 38);
		add(updateApptUpdateOptServicesList);
		
		updateApptEnterNewDateText = new JLabel("Enter new date: (yyyy-mm-dd)");
		updateApptEnterNewDateText.setBounds(274, 336, 249, 18);
		add(updateApptEnterNewDateText);
		
		updateApptEnterNewTimeText = new JLabel("Enter new time: (hh:mm)");
		updateApptEnterNewTimeText.setBounds(274, 364, 208, 18);
		add(updateApptEnterNewTimeText);
		
		updateApptEnterNewTimeTextField = new JTextField();
		updateApptEnterNewTimeTextField.setColumns(10);
		updateApptEnterNewTimeTextField.setBounds(513, 360, 130, 26);
		add(updateApptEnterNewTimeTextField);
		
		updateApptEnterNewOptServicesText = new JLabel("Select optional services: (leave empty if just a service)");
		updateApptEnterNewOptServicesText.setBounds(16, 413, 339, 18);
		add(updateApptEnterNewOptServicesText);
		
		updateApptEnterNewOptServicesList = new JList<String>(new String[0]);
		updateApptEnterNewOptServicesList.setBounds(18, 443, 208, 70);
		add(updateApptEnterNewOptServicesList);
		
		updateApptButton = new JButton("Update");
		updateApptButton.setBounds(526, 490, 117, 29);
		add(updateApptButton);
		
		updateApptNewServiceSelected = new JComboBox<String>(new String[0]);
		updateApptNewServiceSelected.setBounds(13, 361, 216, 38);
		add(updateApptNewServiceSelected);
		
		GroupLayout layout = new GroupLayout(this);	// sets a layout
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		lblCancelAnAppointment = new JLabel("Cancel an Appointment");
		lblCancelAnAppointment.setBounds(16, 573, 149, 16);
		add(lblCancelAnAppointment);
		
		cancelApptSelectApptBox = new JComboBox<String>(new String[0]);
		cancelApptSelectApptBox.setBounds(13, 590, 216, 38);
		add(cancelApptSelectApptBox);
		
		confirmCancelApptButton = new JButton("Confirm");
		confirmCancelApptButton.setBounds(241, 594, 117, 29);
		add(confirmCancelApptButton);
		
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
		
		plusCreateAppt = new JButton("+");
		plusCreateAppt.setBounds(221, 68, 50, 29);
		add(plusCreateAppt);
		
		plusUpdateAppt = new JButton("+");
		plusUpdateAppt.setBounds(221, 336, 50, 29);
		add(plusUpdateAppt);
		
		plusUpdateServiceOfAppt = new JButton("+");
		plusUpdateServiceOfAppt.setBounds(221, 365, 50, 29);
		add(plusUpdateServiceOfAppt);
		
		
		// table - by Matthew
		JTable table = new JTable();
		//make the table non editable (for the user. the table will still get updated):
		DefaultTableModel modelTable = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelTable.addColumn("Service Booking");
		modelTable.addColumn("Start Date");
		modelTable.addColumn("Start Time");
		modelTable.addColumn("End Date");
		modelTable.addColumn("End Time");
		table.setModel(modelTable);
		
		for(int i = 0; i< 20;i++) {
			//TODO
			//here is where we would get the TO information about the appointments 
			//and insert them into the table
			Vector<String> r = new Vector<String>();
			r.addElement("---");		//service booking
			r.addElement("---");		//start date
			r.addElement("---");		//start time
			r.addElement("---");		//end date
			r.addElement("---");		//end time
			modelTable.addRow(r);
		}
		JScrollPane scrollTable = new JScrollPane(table);
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


		// *** Action Listeners *** //
		
		// create an appointment via the "+" button
		plusCreateAppt.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { plusCreateApptActionPerformed(evt); 
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// update an appointment via the "+" button
		plusUpdateAppt.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { plusUpdateApptActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// update the optional services via the "+" button
		plusUpdateServiceOfAppt.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { plusUpdateServiceOfApptActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// listeners for create appointment button
		createApptConfirmButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { createApptConfirmButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});
		
		
		
		// listeners for create appointment button
		updateApptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try { updateApptButtonActionPerformed(evt);
				} catch (Exception e) { error = e.getMessage(); }
			}
		});

	}
	
	
	
	private void refreshData() {
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			// text fields
			createApptEnterStartTimeTextField.setText("");
			updateApptEnterNewTimeTextField.setText("");
			// username
			newUsernameTextField.setText("");
			newPasswordTextField.setText("");

			// add appointments to the appointment box
			appointments = new HashMap<Integer, String>();
			createApptChooseServiceComboBox.removeAllItems();
			cancelApptSelectApptBox.removeAllItems();
			updateApptNewServiceSelected.removeAllItems();
			Integer index = 0;
			System.out.println(CarShopApplication.getCurrentUser());
//			for (TOAppointment appt : CarShopController.getCustomerAppointments(CarShopApplication.getCurrentUser())) {
//				appointments.put(index, appt.getServiceName());
//				createApptChooseServiceComboBox.addItem(appt.getServiceName());
//				updateApptNewServiceSelected.addItem(appt.getServiceName());	// not sure if this one is the right box
//				cancelApptSelectApptBox.addItem(appt.getServiceName());
//				index++;
//			};
//			createApptChooseServiceComboBox.setSelectedIndex(-1);
//			updateApptNewServiceSelected.setSelectedIndex(-1);
//			cancelApptSelectApptBox.setSelectedIndex(-1);
			
			

			
			// toggle repair status
//			buses = new HashMap<Integer, String>();
//			busToggleList.removeAllItems();
//			index = 0;
//			for (TOBusVehicle bus : BtmsController.getBuses()) {
//				buses.put(index, bus.getLicencePlate());
//				busToggleList.addItem(bus.getLicencePlate());
//				index++;
//			};
//			busToggleList.setSelectedIndex(-1);
//		
//			// bus assignment - bus
//			availableBuses = new HashMap<Integer, String>();
//			busList.removeAllItems();
//			index = 0;
//			for (TOBusVehicle bus : BtmsController.getAvailableBuses()) {
//				availableBuses.put(index, bus.getLicencePlate());
//				busList.addItem(bus.getLicencePlate());
//				index++;
//			};
//			busList.setSelectedIndex(-1);
//			// bus assignment - route (also combo box for bus route visualization)
//			routes = new HashMap<Integer, TORoute>();
//			routeList.removeAllItems();
//			routeList2.removeAllItems();
//			routeList2.addItem("no Route selected");
//			index = 0;
//			for (TORoute route : BtmsController.getRoutes()) {
//				routes.put(index, route);
//				routeList.addItem("#" + route.getNumber());
//				routeList2.addItem("#" + route.getNumber());
//				index++;
//			};
//			routeList.setSelectedIndex(-1);
//			// bus assignment - date
//			assignmentDatePicker.getModel().setValue(null);
//			
//			// schedule driver - driver
//			availableDrivers = new HashMap<Integer, Integer>();
//			driverList.removeAllItems();
//			index = 0;
//			for (TODriver driver : BtmsController.getAvailableDrivers()) {
//				availableDrivers.put(index, driver.getId());
//				driverList.addItem("#" + driver.getId() + " " + driver.getName());
//				index++;
//			};
//			driverList.setSelectedIndex(-1);
//			// schedule driver - assignment
//			assignments = new HashMap<Integer, TORouteAssignment>();
//			assignmentList.removeAllItems();
//			index = 0;
//			for (TORouteAssignment assignment : BtmsController.getAssignments()) {
//				assignments.put(index, assignment);
//				assignmentList.addItem(assignment.getDate() + ": Route #" + assignment.getNumber() + ", Bus " + assignment.getLicencePlate());
//				index++;
//			};
//			assignmentList.setSelectedIndex(-1);
//			// schedule driver - shift
//			shifts = new HashMap<Integer, String>();
//			shiftList.removeAllItems();
//			index = 0;
//			for (String shift : BtmsController.getShiftValues()) {
//				shifts.put(index, shift);
//				shiftList.addItem(shift);
//				index++;
//			};
			// selectedIndex of shiftList defaults to 0 - does not need to be set
		}
//		
		// daily overview
//		refreshDailyOverview();

		// this is needed because the size of the window changes depending on whether an error message is shown or not
		
	}
	
	
	
	private void plusCreateApptActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		if(createApptChooseServiceComboBox.getSelectedIndex()!=-1) {
			String apptName = createApptChooseServiceComboBox.getItemAt(createApptChooseServiceComboBox.getSelectedIndex());
			
			try {	// if it's an instance of servicecombo
				DefaultListModel<String> model = new DefaultListModel<>();
				JList<String> list = new JList<>(model);
				List<TOComboItem> list1 = CarShopController.getOptServicesWithName(apptName);
				for(int i = 0; i < list1.size(); i++ ) {
					  model.addElement(list1.get(0).getName());
					}
				createApptSelectOptServicesList = list;
			} catch(Exception e) {	// otherwise, the optional services list is empty (it's a service)
				DefaultListModel<String> model = new DefaultListModel<>();
				JList<String> list = new JList<>(model);
				updateApptEnterNewOptServicesList = list;
			}	
		}
		
		// update visuals
		refreshData();
	}
	
	
	
	private void plusUpdateApptActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		if(updateApptNewServiceSelected.getSelectedIndex()!=-1) {
			String apptName = updateApptNewServiceSelected.getItemAt(updateApptNewServiceSelected.getSelectedIndex());
			
			try {	// if it's an instance of servicecombo
				DefaultListModel<String> model = new DefaultListModel<>();
				JList<String> list = new JList<>(model);
				List<TOComboItem> list1 = CarShopController.getOptServicesWithName(apptName);
				for(int i = 0; i < list1.size(); i++ ) {
					  model.addElement(list1.get(0).getName());
					}
				updateApptEnterNewOptServicesList = list;
			} catch(Exception e) {	// otherwise, the list is empty (it's a service)
				DefaultListModel<String> model = new DefaultListModel<>();
				JList<String> list = new JList<>(model);
				updateApptEnterNewOptServicesList = list;
			}
		}
		
		// update visuals
		refreshData();
	}
	
	
	
	private void plusUpdateServiceOfApptActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		if(updateApptUpdateOptServicesList.getSelectedIndex()!=-1) {
			String apptName = updateApptUpdateOptServicesList.getItemAt(updateApptUpdateOptServicesList.getSelectedIndex());
			
			try {	// if it's an instance of servicecombo
				DefaultListModel<String> model = new DefaultListModel<>();
				JList<String> list = new JList<>(model);
				List<TOComboItem> list1 = CarShopController.getOptServicesWithName(apptName);
				for(int i = 0; i < list1.size(); i++ ) {
					  model.addElement(list1.get(0).getName());
					}
				updateApptEnterNewOptServicesList = list;
			} catch(Exception e) {	// otherwise, the list is empty (it's a service)
				DefaultListModel<String> model = new DefaultListModel<>();
				JList<String> list = new JList<>(model);
				updateApptEnterNewOptServicesList = list;
			}
		}
		
		// update visuals
		refreshData();
	}
	
	
	
	private void createApptConfirmButtonActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		
		// call the controller
		try {
			// TODO replace error
			List<String> list = createApptSelectOptServicesList.getSelectedValuesList();
			String optServices = "";
			for(String s : list) {
				optServices += s+",";
			}
			CarShopController.CreateAppointmentWithOptServices(CarShopApplication.getCurrentUser(),
					createApptChooseServiceComboBox.getItemAt(createApptChooseServiceComboBox.getSelectedIndex()), 
					createApptEnterStartDateTextField.getText(), updateApptDateText.toString(), 
					CarShopApplication.getCarShop(), optServices, true);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// update visuals
		refreshData();
	}
	
	
	
	private void updateApptButtonActionPerformed(ActionEvent evt) throws Exception {
		error = "";
		// call the controller
		try {
			// TODO replace error
			List<String> list = createApptSelectOptServicesList.getSelectedValuesList();
			String optServices = "";
			for(String s : list) {
				optServices += s+",";
			}
			// TODO
			CarShopController.CreateAppointmentWithOptServices(CarShopApplication.getCurrentUser(),
					createApptChooseServiceComboBox.getItemAt(createApptChooseServiceComboBox.getSelectedIndex()), 
					createApptEnterStartDateTextField.getText(),updateApptDateText.toString(), 
					CarShopApplication.getCarShop(), optServices, true);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// update visuals
		refreshData();
	}
}
