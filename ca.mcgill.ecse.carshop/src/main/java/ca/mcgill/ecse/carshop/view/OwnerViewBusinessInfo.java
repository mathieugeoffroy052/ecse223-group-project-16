package ca.mcgill.ecse.carshop.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import ca.mcgill.ecse.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse.carshop.controller.TOTimeSlot;

public class OwnerViewBusinessInfo {
	
	private JPanel businessInfoPanel;
	
	private JLabel businessName;
    private String carshopName;
    private JLabel phoneNum;
    private String carshopPhoneNum;
    private JLabel email;
    private String carshopEmail;
    private JButton updateBusinessInfo;
    //weekly business hours
    private JLabel businessHoursTitle;
    private JList weeklySchedule; //list to hold weekly schedule (no scrolling)
    private TOBusinessHour[] weeklyHours; //transfer object for business hours TODO
    private JButton addWeeklyHours;
    private JButton updateWeeklyHours;
    //holidays
    private JLabel holidayTitle;
    private JList upcomingHolidays; //list to show all the upcoming holidays
    private TOTimeSlot[] carshopHolidays; //TO for holidays TODO
    private JScrollPane holidayScroller;
    private JButton addHoliday;
    private JButton updateHoliday;
    //vacations
    private JLabel vacationTitle;
    private JList upcomingVacations; //list to show all upcoming vacations
    private TOTimeSlot[] carshopVacations; //TO for vacations TODO
    private JScrollPane vacationScroller;
    private JButton addVacation;
    private JButton updateVacation;
    
    public OwnerViewBusinessInfo() {
    	businessInfoPanel = new JPanel();
		
		//set up components
		businessName = new JLabel(carshopName);
	    phoneNum = new JLabel(carshopPhoneNum);
	    email = new JLabel(carshopEmail);
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
	    
	    businessInfoPanel.add(businessName);
	    businessInfoPanel.add(phoneNum);
	    businessInfoPanel.add(email);
	    businessInfoPanel.add(updateBusinessInfo);
	    
	    businessInfoPanel.add(businessHoursTitle);
	    businessInfoPanel.add(weeklySchedule);
	    businessInfoPanel.add(addWeeklyHours);
	    businessInfoPanel.add(updateWeeklyHours);
	    
	    businessInfoPanel.add(holidayTitle);
	    businessInfoPanel.add(upcomingHolidays);
	    businessInfoPanel.add(holidayScroller);
	    businessInfoPanel.add(addHoliday);
	    businessInfoPanel.add(updateHoliday);
	    
	    businessInfoPanel.add(vacationTitle);
	    businessInfoPanel.add(upcomingVacations);
	    businessInfoPanel.add(vacationScroller);
	    businessInfoPanel.add(addVacation);
	    businessInfoPanel.add(updateVacation);
	    
		//layout
		GroupLayout layout = new GroupLayout(businessInfoPanel);
		businessInfoPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		//horizontal Group
		//layout.setHorizontalGroup(
    }

}
