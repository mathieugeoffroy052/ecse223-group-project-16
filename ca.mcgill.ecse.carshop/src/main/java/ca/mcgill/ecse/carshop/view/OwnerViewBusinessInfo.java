package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.text.DateFormatter;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse.carshop.controller.TOTimeSlot;

public class OwnerViewBusinessInfo extends JPanel {
	
	private static String errorMessage = null;
	private static String smallErrorMessage = null;
	private static JLabel smallErrorLabel;
	private static JLabel errorLabel;
	private static JLabel businessInfoTitle;
	private static JLabel businessName;
    private static JLabel phoneNum;
    private static JLabel email;
    private static JLabel address;
    private static JButton updateBusinessInfo;
    //weekly business hours

    private static JLabel businessHoursTitle;
    private static JList weeklySchedule; //list to hold weekly schedule (no scrolling)
    private static TOBusinessHour[] weeklyHours = new TOBusinessHour[1]; //transfer object for business hours TODO
    private static List<TOBusinessHour> TOBusinessHoursCS;
    private static String[] stringBusinessHours;
    private static JButton addWeeklyHours;
    private static JButton updateWeeklyHours;
    //holidays
    private static JLabel holidayTitle;
    private static JList upcomingHolidays; //list to show all the upcoming holidays
    private static TOTimeSlot[] carshopHolidays = new TOTimeSlot[0]; //TO for holidays TODO
    private static List<TOTimeSlot> TOHolidaysCS;
    private static String[] stringHolidays;
    private static JScrollPane holidayScroller;
    private static JButton addHoliday;
    private static JButton updateHoliday;
    //vacations
    private static JLabel vacationTitle;
    private static JList upcomingVacations; //list to show all upcoming vacations
    private static TOTimeSlot[] carshopVacations = new TOTimeSlot[0]; //TO for vacations TODO
    private static List<TOTimeSlot> TOVacationsCS;
    private static String[] stringVacations;
    private static JScrollPane vacationScroller;
    private static JButton addVacation;
    private static JButton updateVacation;

    
    public OwnerViewBusinessInfo() {
    	
    	TOBusinessHoursCS = CarShopController.getBusinessHours();
    	stringBusinessHours = listBHToString(TOBusinessHoursCS);
    	TOHolidaysCS = CarShopController.getHolidays();
    	stringHolidays = listTSToString(TOHolidaysCS);
    	
    	
    	TOVacationsCS = CarShopController.getVacations();
    	stringVacations = listTSToString(TOVacationsCS); 	
    	
		//set up components
    	errorLabel = new JLabel(errorMessage);
    	errorLabel.setForeground(Color.RED);
    	
    	businessInfoTitle = new JLabel("Business Information");
    	businessInfoTitle.setFont(new Font("Arial", Font.BOLD, 22));
		businessName = new JLabel();
	    phoneNum = new JLabel();
	    email = new JLabel();
	    address = new JLabel();
	    updateBusinessInfo = new JButton("Update");
	    
	    businessHoursTitle = new JLabel("Weekly Business Hours");
	    businessHoursTitle.setFont(new Font("Arial", Font.BOLD, 22));
	    weeklySchedule = new JList(stringBusinessHours);
	    weeklySchedule.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    weeklySchedule.setLayoutOrientation(JList.VERTICAL);
	    weeklySchedule.setVisibleRowCount(7); //for 7 days in a week
	    addWeeklyHours = new JButton("Add");
	    updateWeeklyHours = new JButton("Update");
	    
	    holidayTitle = new JLabel("Holidays");
	    holidayTitle.setFont(new Font("Arial", Font.BOLD, 22));
	    upcomingHolidays = new JList(stringHolidays); 
	    upcomingHolidays.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    upcomingHolidays.setLayoutOrientation(JList.VERTICAL);
	    upcomingHolidays.setVisibleRowCount(10); //to change later
	    holidayScroller = new JScrollPane(upcomingHolidays);
	    holidayScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    Dimension dHoliday = upcomingHolidays.getPreferredSize();
	    holidayScroller.setPreferredSize(new Dimension(dHoliday.width, dHoliday.height));
	    addHoliday = new JButton("Add");
	    updateHoliday = new JButton("Update");
	    
	    vacationTitle = new JLabel("Vacations");
	    vacationTitle.setFont(new Font("Arial", Font.BOLD, 22));
	    upcomingVacations = new JList(stringVacations); 
	    upcomingVacations.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    upcomingVacations.setLayoutOrientation(JList.VERTICAL);
	    upcomingVacations.setVisibleRowCount(10); //to change later
	    vacationScroller = new JScrollPane(upcomingVacations);
	    vacationScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);	
	    Dimension dVacation = vacationScroller.getPreferredSize();
	    vacationScroller.setPreferredSize(new Dimension(dVacation.width, dVacation.height));
	    addVacation = new JButton("Add");
	    updateVacation = new JButton("Update");
	    
		JSeparator horizontalLineMiddle1 = new JSeparator();
		JSeparator horizontalLineMiddle2 = new JSeparator();
		JSeparator horizontalLineBottom = new JSeparator();
		JSeparator verticalLineLine = new JSeparator(SwingConstants.VERTICAL);

		refreshData();
		
	    this.add(businessInfoTitle);

	    this.add(businessName);
	    this.add(phoneNum);
	    this.add(email);
	    this.add(updateBusinessInfo);
	    
	    this.add(businessHoursTitle);
	    this.add(weeklySchedule);
	    this.add(addWeeklyHours);
	    this.add(updateWeeklyHours);
	    
	    this.add(holidayTitle);
	    this.add(upcomingHolidays);
	    this.add(holidayScroller);
	    this.add(addHoliday);
	    this.add(updateHoliday);
	    
	    this.add(vacationTitle);
	    this.add(upcomingVacations);
	    this.add(vacationScroller);
	    this.add(addVacation);
	    this.add(updateVacation);
	    
	    
	    //action listeners
	    updateBusinessInfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateBusinessInfoActionPerformed(evt);
			}
		});
	    
	    addWeeklyHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addWeeklyHoursActionPerformed(evt);
			}
		});
	    
		//layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		//horizontal Group
		layout.setHorizontalGroup(
				layout.createParallelGroup()

					.addComponent(errorLabel)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(businessInfoTitle)
									.addComponent(businessName)
									.addComponent(phoneNum)
									.addComponent(email)
									.addComponent(address)
									.addComponent(updateBusinessInfo))
							.addComponent(verticalLineLine)
							.addGroup(layout.createParallelGroup()
									.addGroup(layout.createSequentialGroup()
											.addComponent(businessHoursTitle)
											.addComponent(updateWeeklyHours)
											.addComponent(addWeeklyHours))
									.addComponent(weeklySchedule)))
					.addComponent(horizontalLineMiddle1)
					.addGroup(layout.createSequentialGroup()
							.addComponent(holidayTitle)
							.addComponent(updateHoliday)
							.addComponent(addHoliday))
					.addComponent(holidayScroller)
					.addComponent(horizontalLineMiddle2)
					.addGroup(layout.createSequentialGroup()
							.addComponent(vacationTitle)
							.addComponent(updateVacation)
							.addComponent(addVacation))
					.addComponent(vacationScroller)
					.addComponent(horizontalLineBottom));

		
		//vertical group
		layout.setVerticalGroup(
				layout.createSequentialGroup()

						.addComponent(errorLabel)
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(businessInfoTitle)
										.addComponent(businessName)
										.addComponent(phoneNum)
										.addComponent(email)
										.addComponent(address)
										.addComponent(updateBusinessInfo))
								.addComponent(verticalLineLine)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup()
												.addComponent(businessHoursTitle)
												.addComponent(updateWeeklyHours)
												.addComponent(addWeeklyHours))
										.addComponent(weeklySchedule)))
						.addComponent(horizontalLineMiddle1)
						.addGroup(layout.createParallelGroup()
								.addComponent(holidayTitle)
								.addComponent(updateHoliday)
								.addComponent(addHoliday))
						.addComponent(holidayScroller)
						.addComponent(horizontalLineMiddle2)
						.addGroup(layout.createParallelGroup()
								.addComponent(vacationTitle)
								.addComponent(updateVacation)
								.addComponent(addVacation))
						.addComponent(vacationScroller)
						.addComponent(horizontalLineBottom)
				);
		
		refreshData();
    }
    
    //helper methods
    public static void centerWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

    }
    
    public static String[] listBHToString(List<TOBusinessHour> list) {
    	String[] newStringArray = new String[list.size()];
    	int index = 0;
    	if (list.get(0) == null) return null;
    	for (TOBusinessHour bh : list) {
    		String info = bh.getDayOfWeek() 
    				+ ": " + CarShopController.timeToString(bh.getStartTime()) 
    				+ " - " + CarShopController.timeToString(bh.getEndTime());
    		newStringArray[index++] = info;
    	}		    	
    	return newStringArray;
    }

    public static String[] listTSToString(List<TOTimeSlot> list) {
    	String[] newStringArray = new String[list.size()];
    	int index = 0;
    	if (list.get(0) == null) return null;	
    	for (TOTimeSlot ts : list) {
    		String info = "From " + CarShopController.dateToString(ts.getStartDate()) 
	    		+ " at " + CarShopController.timeToString(ts.getStartTime()) 
	    		+ " to " + CarShopController.dateToString(ts.getEndDate()) 
	    		+ " at " + CarShopController.timeToString(ts.getEndTime());
    		newStringArray[index++] = info;
    	}
    	return newStringArray;

    }
    
    public static void refreshData() {
    	if (errorMessage != null) errorLabel.setText(errorMessage);
    	if (smallErrorMessage != null) smallErrorLabel.setText(smallErrorMessage);
    	businessName.setText(CarShopController.getBusinessName());
    	email.setText(CarShopController.getBusinessEmail());
    	phoneNum.setText(CarShopController.getBusinessPhone());
    	address.setText(CarShopController.getBusinessAddress());

    	TOBusinessHoursCS = CarShopController.getBusinessHours();
    	stringBusinessHours = listBHToString(TOBusinessHoursCS);
    	TOHolidaysCS = CarShopController.getHolidays();
    	stringHolidays = listTSToString(TOHolidaysCS);
    	
    	
    	TOVacationsCS = CarShopController.getVacations();
    	stringVacations = listTSToString(TOVacationsCS);

//    	weeklySchedule.setListData(stringBusinessHours);
//    	upcomingHolidays.setListData(stringHolidays);
//    	upcomingVacations.setListData(stringVacations);
    }
    
    //action methods
    private void updateBusinessInfoActionPerformed(ActionEvent evt) {
		JFrame updateBusinessInfo = new JFrame();
		smallErrorLabel = new JLabel();
		smallErrorLabel.setForeground(Color.RED);
		JLabel updateTitle = new JLabel("Update Business Information");
		updateTitle.setFont(new Font("Arial", Font.BOLD, 22));
		JLabel name = new JLabel("Name:");
		JTextField nameText = new JTextField(businessName.getText());
		JLabel address = new JLabel("Address:");
		JTextField addressText = new JTextField(this.address.getText());
		JLabel phoneNum = new JLabel("Phone Number:");
		JFormattedTextField phoneNumText = new JFormattedTextField(this.phoneNum.getText());
		JLabel email = new JLabel("Email:");
		JTextField emailText = new JTextField(this.email.getText());
		JButton updateInfo = new JButton("Update");
		
		updateBusinessInfo.add(updateTitle);
		updateBusinessInfo.add(name);
		updateBusinessInfo.add(address);
		updateBusinessInfo.add(phoneNum);
		updateBusinessInfo.add(email);
		updateBusinessInfo.add(nameText);
		updateBusinessInfo.add(addressText);
		updateBusinessInfo.add(phoneNumText);
		updateBusinessInfo.add(emailText);
		updateBusinessInfo.add(updateInfo);
		
		GroupLayout layout = new GroupLayout(updateBusinessInfo.getContentPane());
		updateBusinessInfo.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		//horizontal
		layout.setHorizontalGroup(
				layout.createParallelGroup()
					.addComponent(smallErrorLabel)
					.addComponent(updateTitle)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(name)
									.addComponent(phoneNum)
									.addComponent(email)
									.addComponent(address))
							.addGroup(layout.createParallelGroup()
									.addComponent(nameText)
									.addComponent(phoneNumText)
									.addComponent(emailText)
									.addComponent(addressText)
									))
					.addComponent(updateInfo));
		

		
		//vertical 
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(smallErrorLabel)
					.addComponent(updateTitle)
					.addGroup(layout.createParallelGroup()
							.addComponent(name)
							.addComponent(nameText))
					.addGroup(layout.createParallelGroup()
							.addComponent(phoneNum)
							.addComponent(phoneNumText))
					.addGroup(layout.createParallelGroup()
							.addComponent(email)
							.addComponent(emailText))
					.addGroup(layout.createParallelGroup()
							.addComponent(address)
							.addComponent(addressText))
					.addComponent(updateInfo));
				
		updateBusinessInfo.pack();
		centerWindow(updateBusinessInfo);
		updateBusinessInfo.setVisible(true);
		
		updateInfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String newName = nameText.getText();
				String newEmail = emailText.getText();
				String newAddress = addressText.getText();
				String newPhoneNum = phoneNumText.getText();
				updateInfoActionPerformed(evt, newName, newPhoneNum, newEmail, newAddress, updateBusinessInfo);
			}
		});
		
	}
    
    private void updateInfoActionPerformed(ActionEvent evt, String name, String phoneNum, String email, String address, JFrame frame) {
		try {
			CarShopController.updateBusinessInfo(name, address, phoneNum, email);
			smallErrorMessage = null;
			frame.setVisible(false);
		} catch (InvalidInputException e) {
			smallErrorMessage = e.getMessage();
		}
		refreshData();
		frame.pack();
	}
    
    private void addWeeklyHoursActionPerformed(ActionEvent evt) {
    	JFrame addWeeklyHours = new JFrame();
		smallErrorLabel = new JLabel();
		smallErrorLabel.setForeground(Color.RED);
		JLabel addTitle = new JLabel("Add Weekly Hours");
		addTitle.setFont(new Font("Arial", Font.BOLD, 22));
		JLabel day = new JLabel("Day:");
		String [] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		JComboBox dayPicker = new JComboBox(dayNames);
		JLabel startTime = new JLabel("Strat Time:");
		
		//START TIME
		SpinnerDateModel model = new SpinnerDateModel();
		model.setValue(CarShopApplication.getSystemTime());
		
		JSpinner startTimePicker = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(startTimePicker, "HH:mm:ss");
		DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); 
		formatter.setOverwriteMode(true);
		
		startTimePicker.setEditor(editor);
		
		JLabel endTime = new JLabel("End Time:");
		//END TIME
		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setValue(CarShopApplication.getSystemTime());
		
		JSpinner endTimePicker = new JSpinner(model2);
		JSpinner.DateEditor editor2 = new JSpinner.DateEditor(endTimePicker, "HH:mm:ss");
		DateFormatter formatter2 = (DateFormatter) editor2.getTextField().getFormatter();
		formatter2.setAllowsInvalid(false); 
		formatter2.setOverwriteMode(true);
		
		endTimePicker.setEditor(editor2);
		JButton addWeeklyHoursButton = new JButton("Add");
		
		addWeeklyHours.add(smallErrorLabel);
		addWeeklyHours.add(addTitle);
		addWeeklyHours.add(day);
		addWeeklyHours.add(dayPicker);
		addWeeklyHours.add(startTime);
		addWeeklyHours.add(startTimePicker);
		addWeeklyHours.add(endTime);
		addWeeklyHours.add(endTimePicker);
		addWeeklyHours.add(addWeeklyHoursButton);
		
		GroupLayout layout = new GroupLayout(addWeeklyHours.getContentPane());
		addWeeklyHours.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
					.addComponent(smallErrorLabel)
					.addComponent(addTitle)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(day)
									.addComponent(startTime)
									.addComponent(endTime))
							.addGroup(layout.createParallelGroup()
									.addComponent(dayPicker)
									.addComponent(startTimePicker)
									.addComponent(endTimePicker)))
					.addComponent(addWeeklyHoursButton));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(smallErrorLabel)
					.addComponent(addTitle)
					.addGroup(layout.createParallelGroup()
							.addComponent(day)
							.addComponent(dayPicker))
					.addGroup(layout.createParallelGroup()
							.addComponent(startTime)
							.addComponent(startTimePicker))
					.addGroup(layout.createParallelGroup()
							.addComponent(endTime)
							.addComponent(endTimePicker))
					.addComponent(addWeeklyHoursButton));
		
		addWeeklyHours.pack();
		centerWindow(addWeeklyHours);
		addWeeklyHours.setVisible(true);
		
		addWeeklyHoursButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String dayOfWeek = (String)(dayPicker.getSelectedItem());
				String startTime = CarShopController.dateToString( new java.sql.Date(model.getDate().getTime()));
				String endTime = CarShopController.dateToString(new java.sql.Date(model2.getDate().getTime()));
				addWeeklyHoursButtonActionPerformed(evt, dayOfWeek, startTime, endTime, addWeeklyHours);
			}
		});
	}


	protected void addWeeklyHoursButtonActionPerformed(ActionEvent evt, String dayOfWeek, String startTime,
			String endTime, JFrame frame) {
		try {
			CarShopController.createBusinessHour(dayOfWeek, startTime, endTime);
			smallErrorMessage = null;
			frame.setVisible(false);
		} catch (Exception e) {
			smallErrorMessage = e.getMessage();
		}
		refreshData();
		frame.pack();
		
	}}
