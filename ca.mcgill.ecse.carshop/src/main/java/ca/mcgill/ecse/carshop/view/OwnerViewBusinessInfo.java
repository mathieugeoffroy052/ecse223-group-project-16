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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import javax.swing.JTextField;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse.carshop.controller.TOTimeSlot;

public class OwnerViewBusinessInfo extends JPanel {
	
	private static String errorMessage = "";
	private static JLabel errorLabel;
	private static JLabel businessInfoTitle;
	private static JLabel businessName;
    private static JLabel phoneNum;
    private static JLabel email;
    private static JLabel address;
    private static JButton updateBusinessInfo;
    //weekly business hours
<<<<<<< Updated upstream
    private JLabel businessHoursTitle;
    private JList weeklySchedule; //list to hold weekly schedule (no scrolling)
    private TOBusinessHour[] weeklyHours = new TOBusinessHour[1]; //transfer object for business hours TODO

    private static List<TOBusinessHour> TOBusinessHoursCS;

    private JButton addWeeklyHours;
    private JButton updateWeeklyHours;
    //holidays
    private JLabel holidayTitle;
    private JList upcomingHolidays; //list to show all the upcoming holidays
    private TOTimeSlot[] carshopHolidays = new TOTimeSlot[0]; //TO for holidays TODO

    private static List<TOTimeSlot> TOHolidaysCS;

    private JScrollPane holidayScroller;
    private JButton addHoliday;
    private JButton updateHoliday;
    //vacations
    private JLabel vacationTitle;
    private JList upcomingVacations; //list to show all upcoming vacations
    private TOTimeSlot[] carshopVacations = new TOTimeSlot[0]; //TO for vacations TODO

    private static List<TOTimeSlot> TOVacationsCS;

    private JScrollPane vacationScroller;
    private JButton addVacation;
    private JButton updateVacation;
=======
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
>>>>>>> Stashed changes
    
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
	    weeklySchedule = new JList(weeklyHours);
	    weeklySchedule.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    weeklySchedule.setLayoutOrientation(JList.VERTICAL);
	    weeklySchedule.setVisibleRowCount(7); //for 7 days in a week
	    addWeeklyHours = new JButton("Add");
	    updateWeeklyHours = new JButton("Update");
	    
	    holidayTitle = new JLabel("Holidays");
	    holidayTitle.setFont(new Font("Arial", Font.BOLD, 22));
	    upcomingHolidays = new JList(carshopHolidays); 
	    upcomingHolidays.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    upcomingHolidays.setLayoutOrientation(JList.VERTICAL);
	    upcomingHolidays.setVisibleRowCount(10); //to change later
	    holidayScroller = new JScrollPane(upcomingHolidays);
	    addHoliday = new JButton("Add");
	    updateHoliday = new JButton("Update");
	    
	    vacationTitle = new JLabel("Vacations");
	    vacationTitle.setFont(new Font("Arial", Font.BOLD, 22));
	    upcomingVacations = new JList(carshopVacations); 
	    upcomingVacations.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    upcomingVacations.setLayoutOrientation(JList.VERTICAL);
	    upcomingVacations.setVisibleRowCount(10); //to change later
	    vacationScroller = new JScrollPane(upcomingVacations);
	    addVacation = new JButton("Add");
	    updateVacation = new JButton("Update");
	    
	    JSeparator horizontalLineTop = new JSeparator();
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
	    
		//layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		//horizontal Group
		layout.setHorizontalGroup(
				layout.createParallelGroup()

					.addComponent(errorLabel)
					.addComponent(horizontalLineTop)
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
						.addComponent(horizontalLineTop)
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
    	for (Object bh : list) {
    		TOBusinessHour BH = (TOBusinessHour) bh;
    		String info = BH.getDayOfWeek() 
    				+ ": " + CarShopController.timeToString(BH.getStartTime()) 
    				+ " - " + CarShopController.timeToString(BH.getEndTime());
    		newStringArray[index++] = info;
    	}		    	
    	return newStringArray;
    }

    public static String[] listTSToString(List<TOTimeSlot> list) {
    	String[] newStringArray = new String[list.size()];
    	int index = 0;
    	if (list.get(0) == null) return null;	
    	for (Object ts : list) {
    		TOTimeSlot TS = (TOTimeSlot) ts;
    		String info = "From " + CarShopController.dateToString(TS.getStartDate()) 
	    		+ " at " + CarShopController.timeToString(TS.getStartTime()) 
	    		+ " to " + CarShopController.dateToString(TS.getEndDate()) 
	    		+ " at " + CarShopController.timeToString(TS.getEndTime());
    		newStringArray[index++] = info;
    	}
    	return newStringArray;

    }
    
    public static void refreshData() {
    	try {
    		
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
    		
    		weeklySchedule.setListData(stringBusinessHours);
    		upcomingHolidays.setListData(stringHolidays);
    		upcomingVacations.setListData(stringVacations);
    		
    	} catch (Exception e) {
    		errorMessage += e.getMessage();
    	}
    }
    
    //action methods
    private void updateBusinessInfoActionPerformed(ActionEvent evt) {
		JFrame updateBusinessInfo = new JFrame();
		JLabel updateTitle = new JLabel("Update Business Information");
		updateTitle.setFont(new Font("Arial", Font.BOLD, 22));
		JLabel name = new JLabel("Name:");
		JTextField nameText = new JTextField();
		JLabel address = new JLabel("Address:");
		JTextField addressText = new JTextField();
		JLabel phoneNum = new JLabel("Phone Number:");
		JFormattedTextField phoneNumText = new JFormattedTextField();
		JLabel email = new JLabel("Email:");
		JTextField emailText = new JTextField();
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
				updateInfoActionPerformed(evt, newName, newPhoneNum, newEmail, newAddress);
			}
		});
		
	}
    
    private void updateInfoActionPerformed(ActionEvent evt, String name, String phoneNum, String email, String address) {
		try {
			CarShopController.updateBusinessInfo(name, address, phoneNum, email);
			refreshData();
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage += e.getMessage();
		}
		
	}
    
}
