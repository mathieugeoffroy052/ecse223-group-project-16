package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultListModel;
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
//import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
//import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse.carshop.controller.TOTimeSlot;

@SuppressWarnings("serial")
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
    private static JList<String> weeklySchedule; //list to hold weekly schedule (no scrolling)
//    private static TOBusinessHour[] weeklyHours = new TOBusinessHour[1]; //transfer object for business hours TODO
    private static List<TOBusinessHour> TOBusinessHoursCS;
    private static String[] stringBusinessHours;
    private static DefaultListModel<String> bhm;
    private static JButton addWeeklyHours;
    private static JButton updateWeeklyHours;
    //holidays
    private static JLabel holidayTitle;
    private static JList<String> upcomingHolidays; //list to show all the upcoming holidays
//    private static TOTimeSlot[] carshopHolidays = new TOTimeSlot[0]; //TO for holidays TODO
    private static List<TOTimeSlot> TOHolidaysCS;
    private static String[] stringHolidays;
    private static DefaultListModel<String> hm;
    private static JScrollPane holidayScroller;
    private static JButton addHoliday;
    private static JButton updateHoliday;
    //vacations
    private static JLabel vacationTitle;
    private static JList<String> upcomingVacations; //list to show all upcoming vacations
//    private static TOTimeSlot[] carshopVacations = new TOTimeSlot[0]; //TO for vacations TODO
    private static List<TOTimeSlot> TOVacationsCS;
    private static String[] stringVacations;
    private static DefaultListModel<String> vm;
    private static JScrollPane vacationScroller;
    private static JButton addVacation;
    private static JButton updateVacation;

    
    public OwnerViewBusinessInfo() {

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
	    bhm = new DefaultListModel<String>();
	    weeklySchedule = new JList<String>(bhm);
	    weeklySchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    weeklySchedule.setLayoutOrientation(JList.VERTICAL);
	    weeklySchedule.setVisibleRowCount(7); //for 7 days in a week
	    addWeeklyHours = new JButton("Add");
	    updateWeeklyHours = new JButton("Update");
	    
	    holidayTitle = new JLabel("Holidays");
	    holidayTitle.setFont(new Font("Arial", Font.BOLD, 22));
	    hm = new DefaultListModel<String>();
	    upcomingHolidays = new JList<String>(hm); 
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
	    vm = new DefaultListModel<String>();
	    upcomingVacations = new JList<String>(vm); 
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
		
	    this.add(businessInfoTitle);

	    this.add(businessName);
	    this.add(phoneNum);
	    this.add(email);
	    this.add(updateBusinessInfo);
	    
	    this.add(businessHoursTitle);
	    this.add(addWeeklyHours);
	    this.add(updateWeeklyHours);
	    
	    this.add(holidayTitle);
	    this.add(holidayScroller);
	    this.add(addHoliday);
	    this.add(updateHoliday);
	    
	    this.add(vacationTitle);
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
	    
	    updateWeeklyHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateWeeklyHoursActionPerformed(evt);
			}
		});
	    
	    addHoliday.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addHolidayActionPerformed(evt);
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
    
    private void addHolidayActionPerformed(ActionEvent evt) {
    	JFrame addHolidayFrame = new JFrame();
    	LocalDate now = LocalDate.now();
		smallErrorLabel = new JLabel();
		smallErrorLabel.setForeground(Color.RED);
		JLabel addTitle = new JLabel("Add Holiday");
		addTitle.setFont(new Font("Arial", Font.BOLD, 22));
		JLabel startDate = new JLabel("Start Date:");
		JTextField startDateText = new JTextField(businessName.getText());
		JLabel startTime = new JLabel("Start Time:");
		JTextField startTimeText = new JTextField("08:00");
		JLabel endDate = new JLabel("End Date:");
		@SuppressWarnings("static-access")
		JFormattedTextField endDateText = new JFormattedTextField(this.phoneNum.getText());
		JLabel endTime = new JLabel("End Time:");
		JTextField endTimeText = new JTextField("09:00");
		JButton addHol = new JButton("Add");
		
		

		SqlDateModel startDateModel;
		Properties startDateDisplay;
		JDatePickerImpl startDatePicker;
		SqlDateModel endDateModel;
		Properties endDateDisplay;
		JDatePickerImpl endDatePicker;
		// create appointment calendar
		startDateModel = new SqlDateModel();
		startDateModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		startDateModel.setSelected(true);
		startDateDisplay = new Properties();
		startDateDisplay.put("text.today", "Today");
		startDateDisplay.put("text.month", "Month");
		startDateDisplay.put("text.year", "Year");
		JDatePanelImpl overviewStartDatePanel = new JDatePanelImpl(startDateModel, startDateDisplay);
		startDatePicker = new JDatePickerImpl(overviewStartDatePanel, new DateLabelFormatter());
		startDatePicker.setBounds(513, 41, 145, 26);//513, 41, 130, 26
		addHolidayFrame.add(startDatePicker);
		
		
		// create appointment calendar
		endDateModel = new SqlDateModel();
		endDateModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		endDateModel.setSelected(true);
		endDateDisplay = new Properties();
		endDateDisplay.put("text.today", "Today");
		endDateDisplay.put("text.month", "Month");
		endDateDisplay.put("text.year", "Year");
		JDatePanelImpl overviewEndDatePanel = new JDatePanelImpl(endDateModel, endDateDisplay);
		endDatePicker = new JDatePickerImpl(overviewEndDatePanel, new DateLabelFormatter());
		endDatePicker.setBounds(513, 41, 145, 26);//513, 41, 130, 26
		addHolidayFrame.add(endDatePicker);
		
		addHolidayFrame.add(addTitle);
		addHolidayFrame.add(startDate);
		addHolidayFrame.add(startDateText);
		addHolidayFrame.add(startTime);
		addHolidayFrame.add(startTimeText);
		addHolidayFrame.add(endDate);
		addHolidayFrame.add(endDateText);
		addHolidayFrame.add(endTime);
		addHolidayFrame.add(endTimeText);
		addHolidayFrame.add(addHol);
		
		GroupLayout layout = new GroupLayout(addHolidayFrame.getContentPane());
		addHolidayFrame.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		//horizontal
		layout.setHorizontalGroup(
				layout.createParallelGroup()
					.addComponent(smallErrorLabel)
					.addComponent(addTitle)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(startDate)
									.addComponent(startTime)
									.addComponent(endDate)
									.addComponent(endTime))
							.addGroup(layout.createParallelGroup()
									.addComponent(startDatePicker)
									.addComponent(startTimeText)
									.addComponent(endDatePicker)
									.addComponent(endTimeText)))
							.addComponent(addHol));
		

		
		//vertical 
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(smallErrorLabel)
					.addComponent(addTitle)
					.addGroup(layout.createParallelGroup()
							.addComponent(startDate)
							.addComponent(startDatePicker))
					.addGroup(layout.createParallelGroup()
							.addComponent(startTime)
							.addComponent(startTimeText))
					.addGroup(layout.createParallelGroup()
							.addComponent(endDate)
							.addComponent(endDatePicker))
					.addGroup(layout.createParallelGroup()
							.addComponent(endTime)
							.addComponent(endTimeText))
					.addComponent(addHol));
				
		addHolidayFrame.pack();
		centerWindow(addHolidayFrame);
		addHolidayFrame.setVisible(true);
		
		addHol.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String newName = startDateText.getText();
				String newEmail = startTimeText.getText();
				String newAddress = endDateText.getText();
				String newPhoneNum = endTimeText.getText();
				updateInfoActionPerformed(evt, newName, newPhoneNum, newEmail, newAddress, addHolidayFrame);
			}
		});
		
	}

	public static void refreshData() {
    	if (errorMessage != null) errorLabel.setText(errorMessage);
    	else errorLabel.setText(errorMessage);
    	if (smallErrorMessage != null) smallErrorLabel.setText(smallErrorMessage);
    	try {
    		// all throw exceptions if business is null
			businessName.setText("Name:\t" + CarShopController.getBusinessName());
	    	email.setText("Email:\t" + CarShopController.getBusinessEmail());
	    	phoneNum.setText("Phone #:\t" + CarShopController.getBusinessPhone());
	    	address.setText("Address:\t" + CarShopController.getBusinessAddress());

	    	// get all TO business hours
	    	TOBusinessHoursCS = CarShopController.getBusinessHours();
	    	// put them all into a string
	    	stringBusinessHours = listBHToString(TOBusinessHoursCS);
	    	
	    	// get all TO holidays
	    	TOHolidaysCS = CarShopController.getHolidays();
	    	// convert all holidays to a string
	    	stringHolidays = listTSToString(TOHolidaysCS);
	    	
	    	// get all TO vacations
	    	TOVacationsCS = CarShopController.getVacations();
	    	// convert them all to a string (same method as before)
	    	stringVacations = listTSToString(TOVacationsCS);
	    	
		} catch (Exception e) {
			errorLabel.setText(e.getMessage());
		}

    	bhm.clear();
    	hm.clear();
    	vm.clear();
    	
    	// condition check
    	if(stringBusinessHours!=null) {
	    	for (String s : stringBusinessHours) {
	    		bhm.addElement(s);
	    	}
    	}
    	
    	if(stringHolidays!=null) {
	    	for (String s : stringHolidays) {
	    		hm.addElement(s);
	    	}
    	}
    	
    	if(stringVacations!=null) {
	    	for (String s : stringVacations) {
	    		vm.addElement(s);
	    	}
    	}

    }
    
    
    
@SuppressWarnings({ "rawtypes", "unchecked" })
private void updateWeeklyHoursActionPerformed(ActionEvent evt) {

    	JFrame updateWeeklyHours = new JFrame();
    	try {
    	int selectedIndex = weeklySchedule.getSelectedIndex();
    	errorMessage = null;
    	TOBusinessHour updatingBH = TOBusinessHoursCS.get(selectedIndex);
		smallErrorLabel = new JLabel();
		smallErrorLabel.setForeground(Color.RED);
		JLabel addTitle = new JLabel("Update Weekly Hour");
		addTitle.setFont(new Font("Arial", Font.BOLD, 22));
		JLabel day = new JLabel("Day:");
		String [] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		JComboBox dayPicker = new JComboBox(dayNames);
		dayPicker.setSelectedItem(updatingBH.getDayOfWeek());
		JLabel startTime = new JLabel("Strat Time:");

		//START TIME
		SpinnerDateModel model = new SpinnerDateModel();
		model.setValue(updatingBH.getStartTime());
		
		JSpinner startTimePicker = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(startTimePicker, "HH:mm:ss");
		DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); 
		formatter.setOverwriteMode(true);
		
		startTimePicker.setEditor(editor);
		
		JLabel endTime = new JLabel("End Time:");
		//END TIME
		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setValue(updatingBH.getEndTime());
		
		JSpinner endTimePicker = new JSpinner(model2);
		JSpinner.DateEditor editor2 = new JSpinner.DateEditor(endTimePicker, "HH:mm:ss");
		DateFormatter formatter2 = (DateFormatter) editor2.getTextField().getFormatter();
		formatter2.setAllowsInvalid(false); 
		formatter2.setOverwriteMode(true);
		
		endTimePicker.setEditor(editor2);
		JButton updateWeeklyHoursButton = new JButton("Update");
		
		updateWeeklyHours.add(smallErrorLabel);
		updateWeeklyHours.add(addTitle);
		updateWeeklyHours.add(day);
		updateWeeklyHours.add(dayPicker);
		updateWeeklyHours.add(startTime);
		updateWeeklyHours.add(startTimePicker);
		updateWeeklyHours.add(endTime);
		updateWeeklyHours.add(endTimePicker);
		updateWeeklyHours.add(updateWeeklyHoursButton);
		
		GroupLayout layout = new GroupLayout(updateWeeklyHours.getContentPane());
		updateWeeklyHours.getContentPane().setLayout(layout);
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
					.addComponent(updateWeeklyHoursButton));
		
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
					.addComponent(updateWeeklyHoursButton));
		
		updateWeeklyHours.pack();
		centerWindow(updateWeeklyHours);
		updateWeeklyHours.setVisible(true);
		
		updateWeeklyHoursButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String dayOfWeek = (String)(dayPicker.getSelectedItem());
				String startTime = CarShopController.timeToString(new Time (model.getDate().getTime()));
				String endTime = CarShopController.timeToString(new Time (model2.getDate().getTime()));
				String oldDay = updatingBH.getDayOfWeek();
				String oldStartTime = CarShopController.timeToString(updatingBH.getStartTime());
				updateWeeklyHoursButtonActionPerformed(evt, oldDay, oldStartTime, dayOfWeek, startTime, endTime, updateWeeklyHours);
			}
		});
		
		refreshData();
    	} catch (Exception e) {
    		errorMessage = "Select a business hour first";
    		refreshData();
    	}
	}
    

	private void updateWeeklyHoursButtonActionPerformed(ActionEvent evt, String oldDay, String oldStartTime, String dayOfWeek, String startTime,
			String endTime, JFrame frame) {
		try {
			CarShopController.modifyBusinessHour(oldDay, oldStartTime, dayOfWeek, startTime, endTime);
			smallErrorMessage = null;
			frame.setVisible(false);
		} catch (Exception e) {
			smallErrorMessage = e.getMessage();
		}
		refreshData();
		frame.pack();
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
    	if (list.size()==0) return newStringArray;
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
    	if (list.size() == 0) return newStringArray;	
    	for (TOTimeSlot ts : list) {
    		String info = "From " + CarShopController.dateToString(ts.getStartDate()) 
	    		+ " at " + CarShopController.timeToString(ts.getStartTime()) 
	    		+ " to " + CarShopController.dateToString(ts.getEndDate()) 
	    		+ " at " + CarShopController.timeToString(ts.getEndTime());
    		newStringArray[index++] = info;
    	}
    	return newStringArray;

    }
    
    
    public static String splitBasedOnTab(String str) {
    	String[] arr = str.split("\t");
    	if(arr.length>1) {
    		return arr[1];
    	}
    	return "";
    }
    
    //action methods
    @SuppressWarnings("static-access")
	private void updateBusinessInfoActionPerformed(ActionEvent evt) {
		JFrame updateBusinessInfo = new JFrame();
		smallErrorLabel = new JLabel();
		smallErrorLabel.setForeground(Color.RED);
		JLabel updateTitle = new JLabel("Update Business Information");
		updateTitle.setFont(new Font("Arial", Font.BOLD, 22));
		
		// have to split the string
		JLabel name = new JLabel("Name:");
		JTextField nameText = new JTextField(splitBasedOnTab(this.businessName.getText()));
		
		// have to split the string
		JLabel address = new JLabel("Address:");
		JTextField addressText = new JTextField(splitBasedOnTab(this.address.getText()));

		// have to split the string
		JLabel phoneNum = new JLabel("Phone Number:");
		JFormattedTextField phoneNumText = new JFormattedTextField(splitBasedOnTab(this.phoneNum.getText()));

		// have to split the string
		JLabel email = new JLabel("Email:");
		JTextField emailText = new JTextField(splitBasedOnTab(this.email.getText()));


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
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
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
		model.setValue(CarShopController.getSystemTime());
		
		JSpinner startTimePicker = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(startTimePicker, "HH:mm:ss");
		DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); 
		formatter.setOverwriteMode(true);
		
		startTimePicker.setEditor(editor);
		
		JLabel endTime = new JLabel("End Time:");
		//END TIME
		SpinnerDateModel model2 = new SpinnerDateModel();
		model2.setValue(CarShopController.getSystemTime());
		
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
				String startTime = CarShopController.timeToString( new Time(model.getDate().getTime()));
				String endTime = CarShopController.timeToString(new Time(model2.getDate().getTime()));
				addWeeklyHoursButtonActionPerformed(evt, dayOfWeek, startTime, endTime, addWeeklyHours);
			}
		});
	}


	private void addWeeklyHoursButtonActionPerformed(ActionEvent evt, String dayOfWeek, String startTime,
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
