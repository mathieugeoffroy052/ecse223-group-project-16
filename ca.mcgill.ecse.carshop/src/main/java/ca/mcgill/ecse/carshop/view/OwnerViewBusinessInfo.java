package ca.mcgill.ecse.carshop.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse.carshop.controller.TOTimeSlot;

public class OwnerViewBusinessInfo extends JPanel {
	
	private JLabel businessName;
    private String carshopName = "name";
    private JLabel phoneNum;
    private String carshopPhoneNum = "###";
    private JLabel email;
    private String carshopEmail = "a@a";
    private JLabel address;
    private String carshopAddress = "3333";
    private JButton updateBusinessInfo;
    //weekly business hours
    private JLabel businessHoursTitle;
    private JList weeklySchedule; //list to hold weekly schedule (no scrolling)
    private TOBusinessHour[] weeklyHours = new TOBusinessHour[1]; //transfer object for business hours TODO
    private JButton addWeeklyHours;
    private JButton updateWeeklyHours;
    //holidays
    private JLabel holidayTitle;
    private JList upcomingHolidays; //list to show all the upcoming holidays
    private TOTimeSlot[] carshopHolidays = new TOTimeSlot[0]; //TO for holidays TODO
    private JScrollPane holidayScroller;
    private JButton addHoliday;
    private JButton updateHoliday;
    //vacations
    private JLabel vacationTitle;
    private JList upcomingVacations; //list to show all upcoming vacations
    private TOTimeSlot[] carshopVacations = new TOTimeSlot[0]; //TO for vacations TODO
    private JScrollPane vacationScroller;
    private JButton addVacation;
    private JButton updateVacation;
    
    public OwnerViewBusinessInfo() {
    	
		
		//set up components
		businessName = new JLabel(carshopName);
	    phoneNum = new JLabel(carshopPhoneNum);
	    email = new JLabel(carshopEmail);
	    address = new JLabel(carshopAddress);
	    updateBusinessInfo = new JButton("Update");
	    
	    businessHoursTitle = new JLabel("Weekly Business Hours");
	    weeklySchedule = new JList(weeklyHours);
	    weeklySchedule.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    weeklySchedule.setLayoutOrientation(JList.VERTICAL);
	    weeklySchedule.setVisibleRowCount(7); //for 7 days in a week
	    addWeeklyHours = new JButton("Add");
	    updateWeeklyHours = new JButton("Update");
	    
	    holidayTitle = new JLabel("Holidays");
	    upcomingHolidays = new JList(carshopHolidays); 
	    upcomingHolidays.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    upcomingHolidays.setLayoutOrientation(JList.VERTICAL);
	    upcomingHolidays.setVisibleRowCount(10); //to change later
	    holidayScroller = new JScrollPane(upcomingHolidays);
	    addHoliday = new JButton("Add");
	    updateHoliday = new JButton("Update");
	    
	    vacationTitle = new JLabel("Vacations");
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
	    
		//layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		//horizontal Group
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(horizontalLineTop)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
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
						.addComponent(horizontalLineTop)
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
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

}
