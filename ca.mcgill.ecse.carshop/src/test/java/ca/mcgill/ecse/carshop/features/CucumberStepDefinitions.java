package ca.mcgill.ecse.carshop.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.CSS;

import org.checkerframework.checker.units.qual.C;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOBusiness;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {
	
	private CarShop cs;
	private String error;
	private int errorCntr;
	

	// Hongyi
//	private String error;
	private int errorCounter;

	private static TOBusiness toBusiness;
	private static int numberOfBusinessHours;
	private static int numberOfVacations;
	private static int numberOfHolidays;
	private static BusinessHour oldBusinessHour;
	private static String[] oldBusinessHourInfo;
	
	
	
	
// Sign Up Customer Account Kalvin
    

    
    /*
	 * Given is to setup fkn everything u need in life (for testing)
	 * When is to call the method needed (try catch type of shit) can used some inputs and shit
	 * Then is to check if the system is the same state
	 * */
	
	// Variables for sign Up
		private String username;
		private String password;
		User user = null;
		private int numberOfChanges;

		
		@Before
		public static void setUp() {
			//CarShopApplication.getCarShop().delete();
			numberOfBusinessHours = 0;
			numberOfHolidays = 0;
			numberOfVacations = 0;
			toBusiness = null;
			oldBusinessHour = null;
			oldBusinessHourInfo = null;
			oldBusinessHourInfo = new String[3];
		}


		@Given("no business exists")
		public void no_business_exists() {
			cs = CarShopApplication.getCarShop();
			if (cs.getBusiness() != null) {
				cs.setBusiness(null);
			}
		}

		@Given("the system's time and date is {string}")
		public void the_system_s_time_and_date_is(String date) {
			String datePattern = "yyyy-MM-dd";
			String timePattern = "HH:mm";
			String[] splitString = date.split("\\+");
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
			SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
			Date newDate = null;
			Time newTime = null;
			try {
				newDate = new java.sql.Date(dateFormatter.parse(splitString[0]).getTime());
				newTime = new java.sql.Time(timeFormatter.parse(splitString[1]).getTime());
			} catch (Exception e) {
				error += e.getMessage();
				errorCounter++;
			}
			CarShopApplication.setSystemDate(newDate);
		}

//		@Given("the user is logged in to an account with username {string}")
//		public void the_user_is_logged_in_to_an_account_with_username(String username) {
//			CarShopApplication.setUsername(username);
//		}

		@When("the user tries to set up the business information with new {string} and {string} and {string} and {string}")
		public void the_user_tries_to_set_up_the_business_information_with_new_and_and_and(String name, String address,
				String phoneNumber, String email) {
			try {
				CarShopController.setUpBusinessInfo(name, address, phoneNumber, email);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@Then("a new business with new {string} and {string} and {string} and {string} shall {string} created")
		public void a_new_business_with_new_and_and_and_shall_created(String name, String address, String phoneNumber,
				String email, String result) {
			if (result.equals("be")) {
				assertNotEquals(null, cs.getBusiness());
				assertEquals(name, cs.getBusiness().getName());
				assertEquals(address, cs.getBusiness().getAddress());
				assertEquals(phoneNumber, cs.getBusiness().getPhoneNumber());
				assertEquals(email, cs.getBusiness().getEmail());
			} else {
				assertEquals(null, cs.getBusiness());
			}
		}

		@Then("an error message {string} shall {string} raised")
		public void an_error_message_shall_raised(String errorString, String resultError) {
			if (resultError.equalsIgnoreCase("be")) {
				assertTrue(error.contains(errorString));
			} else {
				assertTrue(error.equals(""));
			}
		}

		@Given("a business exists with the following information:")
		public void a_business_exists_with_the_following_information(io.cucumber.datatable.DataTable dataTable) {
			cs = CarShopApplication.getCarShop();
			List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

			for (Map<String, String> columns : rows) {
				String name = columns.get("name");
				String address = columns.get("address");
				String phoneNumber = columns.get("phone number");
				String email = columns.get("email");

				Business newBusiness = new Business(name, address, phoneNumber, email, cs);
				cs.setBusiness(newBusiness);
			}
		}

		@Given("the business has a business hour on {string} with start time {string} and end time {string}")
		public void the_business_has_a_business_hour_on_with_start_time_and_end_time(String day, String start, String end) {
			String pattern = "HH:mm";
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);

			Time startTime = null;
			Time endTime = null;
			try {
				startTime = new java.sql.Time(formatter.parse(start).getTime());
				endTime = new java.sql.Time(formatter.parse(end).getTime());
			} catch (Exception e) {
				error += e.getMessage();
				errorCounter++;
			}

			BusinessHour businessHour = new BusinessHour(BusinessHour.DayOfWeek.valueOf(day), startTime, endTime, cs);
			oldBusinessHour = businessHour;
			oldBusinessHourInfo[0] = businessHour.getDayOfWeek().name();
			oldBusinessHourInfo[1] = formatter.format(businessHour.getStartTime());
			oldBusinessHourInfo[2] = formatter.format(businessHour.getEndTime());

			cs.getBusiness().addBusinessHour(businessHour);
			numberOfBusinessHours++;
		}

		@When("the user tries to add a new business hour on {string} with start time {string} and end time {string}")
		public void the_user_tries_to_add_a_new_business_hour_on_with_start_time_and_end_time(String day,
				String newStartTime, String newEndTime) {
			try {
				CarShopController.createBusinessHour(day, newStartTime, newEndTime);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@Then("a new business hour shall {string} created")
		public void a_new_business_hour_shall_created(String string) {
			if (string.equals("be")) {
				assertEquals(numberOfBusinessHours + 1, cs.getBusiness().getBusinessHours().size());
			} else {
				assertEquals(numberOfBusinessHours, cs.getBusiness().getBusinessHours().size());
			}
		}

		@When("the user tries to access the business information")
		public void the_user_tries_to_access_the_business_information() {
			toBusiness = CarShopController.getBusinessInfo();
		}

		@Then("the {string} and {string} and {string} and {string} shall be provided to the user")
		public void the_and_and_and_shall_be_provided_to_the_user(String name, String address, String phoneNumber,
				String email) {
			assertNotEquals(null, toBusiness);
			assertEquals(name, toBusiness.getName());
			assertEquals(address, toBusiness.getAddress());
			assertEquals(phoneNumber, toBusiness.getPhoneNumber());
			assertEquals(email, toBusiness.getEmail());
		}

		@Given("a {string} time slot exists with start time {string} at {string} and end time {string} at {string}")
		public void a_time_slot_exists_with_start_time_at_and_end_time_at(String timeSlot, String startDate,
				String startTime, String endDate, String endTime) {
			Date sDate = null;
			Time sTime = null;
			Date eDate = null;
			Time eTime = null;

			String timePattern = "HH:mm";
			String datePattern = "yyyy-MM-dd";
			SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			try {
				sDate = new java.sql.Date(dateFormatter.parse(startDate).getTime());
				eDate = new java.sql.Date(dateFormatter.parse(endDate).getTime());
				sTime = new java.sql.Time(timeFormatter.parse(startTime).getTime());
				eTime = new java.sql.Time(timeFormatter.parse(endTime).getTime());
				if (timeSlot.equalsIgnoreCase("vacation")) {
					cs.getBusiness().addVacation(new TimeSlot(sDate, sTime, eDate, eTime, cs));
					numberOfVacations++;
				} else {
					cs.getBusiness().addHoliday(new TimeSlot(sDate, sTime, eDate, eTime, cs));
					numberOfHolidays++;
				}
			} catch (Exception e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@When("the user tries to add a new {string} with start date {string} at {string} and end date {string} at {string}")
		public void the_user_tries_to_add_a_new_with_start_date_at_and_end_date_at(String type, String startDate,
				String startTime, String endDate, String endTime) {

			try {
				CarShopController.createTimeSlot(type, startDate, startTime, endDate, endTime);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@Then("a new {string} shall {string} be added with start date {string} at {string} and end date {string} at {string}")
		public void a_new_shall_be_added_with_start_date_at_and_end_date_at(String type, String result, String startDate,
				String startTime, String endDate, String endTime) {
			String timePattern = "HH:mm";
			String datePattern = "yyyy-MM-dd";
			SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			if (result.equals("be")) {
				if (type.equals("holiday")) {
					assertEquals(numberOfHolidays + 1, cs.getBusiness().getHolidays().size());
					TimeSlot holiday = cs.getBusiness().getHoliday(numberOfHolidays);
					String sDate = dateFormatter.format(holiday.getStartDate());
					String sTime = timeFormatter.format(holiday.getStartTime());
					String eDate = dateFormatter.format(holiday.getEndDate());
					String eTime = timeFormatter.format(holiday.getEndTime());
					assertEquals(startDate, sDate);
					assertEquals(startTime, sTime);
					assertEquals(endDate, eDate);
					assertEquals(endTime, eTime);
				} else {
					assertEquals(numberOfVacations + 1, cs.getBusiness().getVacations().size());
					TimeSlot vacation = cs.getBusiness().getVacation(numberOfVacations);
					String sDate = dateFormatter.format(vacation.getStartDate());
					String sTime = timeFormatter.format(vacation.getStartTime());
					String eDate = dateFormatter.format(vacation.getEndDate());
					String eTime = timeFormatter.format(vacation.getEndTime());
					assertEquals(startDate, sDate);
					assertEquals(startTime, sTime);
					assertEquals(endDate, eDate);
					assertEquals(endTime, eTime);
				}
			} else {
				if (type.equals("holiday")) {
					assertEquals(numberOfHolidays, cs.getBusiness().getHolidays().size());
				} else {
					assertEquals(numberOfVacations, cs.getBusiness().getVacations().size());
				}
			}
		}

		@After
		public void tearDown() {
			cs.delete();
		}

		/** update business info **/

		@When("the user tries to update the business information with new {string} and {string} and {string} and {string}")
		public void the_user_tries_to_update_the_business_information_with_new_and_and_and(String name, String address,
				String phoneNumber, String email) {
			try {
				CarShopController.updateBusinessInfo(name, address, phoneNumber, email);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@Then("the business information shall {string} updated with new {string} and {string} and {string} and {string}")
		public void the_business_information_shall_updated_with_new_and_and_and(String result, String name, String address,
				String phoneNumber, String email) {
			if (result.equalsIgnoreCase("be")) {
				assertEquals(name, cs.getBusiness().getName());
				assertEquals(address, cs.getBusiness().getAddress());
				assertEquals(phoneNumber, cs.getBusiness().getPhoneNumber());
				assertEquals(email, cs.getBusiness().getEmail());
			} else {
				assertNotEquals(name, cs.getBusiness().getName());
				assertNotEquals(address, cs.getBusiness().getAddress());
				assertNotEquals(phoneNumber, cs.getBusiness().getPhoneNumber());
				assertNotEquals(email, cs.getBusiness().getEmail());
			}
		}

		@When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
		public void the_user_tries_to_change_the_business_hour_at_to_be_on_starting_at_and_ending_at(String weekDay,
				String time, String day, String newStartTime, String newEndTime) {
			try {
				CarShopController.modifyBusinessHour(weekDay, time, day, newStartTime, newEndTime);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}

		}

		// TODO: not sure if this is the correct way to do it
		@Then("the business hour shall {string} be updated")
		public void the_business_hour_shall_be_updated(String string) {
			String pattern = "HH:mm";
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);

			if (string.equalsIgnoreCase("be")) {
				assertEquals(numberOfBusinessHours, cs.getBusiness().getBusinessHours().size());
				String expectedString = oldBusinessHourInfo[0] + oldBusinessHourInfo[1] + oldBusinessHourInfo[2];
				String actualString = oldBusinessHour.getDayOfWeek().name() + formatter.format(oldBusinessHour.getStartTime()) 
					+ formatter.format(oldBusinessHour.getEndTime());
				assertNotEquals(expectedString, actualString);
			} else {
				assertEquals(numberOfBusinessHours, cs.getBusiness().getBusinessHours().size());
				String expectedString = oldBusinessHourInfo[0] + oldBusinessHourInfo[1] + oldBusinessHourInfo[2];
				String actualString = oldBusinessHour.getDayOfWeek().name() + formatter.format(oldBusinessHour.getStartTime()) 
					+ formatter.format(oldBusinessHour.getEndTime());
				assertEquals(expectedString, actualString);
			}
		}

		@When("the user tries to remove the business hour starting {string} at {string}")
		public void the_user_tries_to_remove_the_business_hour_starting_at(String day, String startTime) {
			try {
				CarShopController.deleteBusinessHour(day, startTime);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@Then("the business hour starting {string} at {string} shall {string} exist")
		public void the_business_hour_starting_at_shall_exist(String day, String time, String result) {
			Business business = cs.getBusiness();
			String pattern = "HH:mm";
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			BusinessHour businessHour = null;
			for (BusinessHour hour : business.getBusinessHours()) {
				if (hour.getDayOfWeek().name().equals(day) && formatter.format(hour.getStartTime()).equals(time)) {
					businessHour = hour;
				}
			}
			if (result.equalsIgnoreCase("not")) {
				assertEquals(null, businessHour);
			} else {
				assertNotEquals(null, businessHour);
			}
		}

		@Then("an error message {string} shall {string} be raised")
		public void an_error_message_shall_be_raised(String errorMessage, String result) {
			if (result.equalsIgnoreCase("not")) {
				assertTrue(error.equals(""));
			} else {
				assertTrue(error.contains(errorMessage));
			}
		}

		@When("the user tries to change the {string} on {string} at {string} to be with start date {string} at {string} and end date {string} at {string}")
		public void the_user_tries_to_change_the_on_at_to_be_with_start_date_at_and_end_date_at(String timeslot,
				String oldDate, String oldStart, String startDate, String startTime, String endDate, String endTime) {
			try {
				CarShopController.modifyTimeSlot(timeslot, oldDate, oldStart, startDate, startTime, endDate, endTime);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@Then("the {string} shall {string} updated with start date {string} at {string} and end date {string} at {string}")
		public void the_shall_updated_with_start_date_at_and_end_date_at(String timeslot, String result, String startDate,
				String startTime, String endDate, String endTime) {
			TimeSlot foundSlot = null;
			Business business = cs.getBusiness();

			String timePattern = "HH:mm";
			String datePattern = "yyyy-MM-dd";
			SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			if (timeslot.equalsIgnoreCase("vacation")) {
				for (TimeSlot slot : business.getVacations()) {
					if (dateFormatter.format(slot.getStartDate()).equals(startDate)
							&& timeFormatter.format(slot.getStartTime()).equals(startTime)
							&& dateFormatter.format(slot.getEndDate()).equals(endDate)
							&& timeFormatter.format(slot.getEndTime()).equals(endTime)) {
						foundSlot = slot;
					}
				}
			} else {
				for (TimeSlot slot : business.getHolidays()) {
					if (dateFormatter.format(slot.getStartDate()).equals(startDate)
							&& timeFormatter.format(slot.getStartTime()).equals(startTime)
							&& dateFormatter.format(slot.getEndDate()).equals(endDate)
							&& timeFormatter.format(slot.getEndTime()).equals(endTime)) {
						foundSlot = slot;
					}
				}
			}
			assertEquals(numberOfVacations, business.getVacations().size());
			assertEquals(numberOfHolidays, business.getHolidays().size());
			if (result.equalsIgnoreCase("not be")) {
				assertEquals(null, foundSlot);
			} else {
				assertNotEquals(null, foundSlot);
			}
		}

		@When("the user tries to remove an existing {string} with start date {string} at {string} and end date {string} at {string}")
		public void the_user_tries_to_remove_an_existing_with_start_date_at_and_end_date_at(String type, String startDate,
				String startTime, String endDate, String endTime) {
			try {
				CarShopController.deleteTimeSlot(type, startDate, startTime, endDate, endTime);
			} catch (InvalidInputException e) {
				error += e.getMessage();
				errorCounter++;
			}
		}

		@Then("the {string} with start date {string} at {string} shall {string} exist")
		public void the_with_start_date_at_shall_exist(String type, String startDate, String startTime, String result) {
			TimeSlot timeSlot = null;
			Business business = cs.getBusiness();

			String timePattern = "HH:mm";
			String datePattern = "yyyy-MM-dd";
			SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			if (type.equalsIgnoreCase("vacation")) {
				for (TimeSlot slot : business.getVacations()) {
					if (dateFormatter.format(slot.getStartDate()).equals(startDate)
							&& timeFormatter.format(slot.getStartTime()).equals(startTime)) {
						timeSlot = slot;
					}
				}
			} else {
				for (TimeSlot slot : business.getHolidays()) {
					if (dateFormatter.format(slot.getStartDate()).equals(startDate)
							&& timeFormatter.format(slot.getStartTime()).equals(startTime)) {
						timeSlot = slot;
					}
				}
			}

			if (result.equalsIgnoreCase("not")) {
				assertEquals(null, timeSlot);
			} else {
				assertNotEquals(null, timeSlot);
			}

		}
		
		
	@Given("there is no existing username {string}")
	public void there_is_no_existing_username(String string) {
		if (User.hasWithUsername(string)) 
		{
			User.getWithUsername(string).delete();
		}
		
	}

	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String string, String string2) {

		// use controller and do some operations
		try {
			user = CarShopController.signUpUser(string, string2, CarShopApplication.AccountType.Customer);
			CarShopApplication.setUser(user);
			numberOfChanges++;
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
			user = null;
		}
	}

	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		assertTrue(user != null);
	}

	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String string, String string2) 
	{
		
		assertTrue(CarShopApplication.getUser() != null && 
				CarShopApplication.getUser().getUsername().equals(string) && 
				CarShopApplication.getUser().getPassword().equals(string2)); 

	}

	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		assertTrue(user == null);

	}


	@Given("there is an existing username {string}")
	public void there_is_an_existing_username(String string) throws InvalidInputException {

		
		if (string.equals("owner") )
		{
			Owner owner = new Owner("owner", "owner", cs);
			cs.setOwner(owner); // unnecessary step
		}
		else if (string.contains("Technician"))
		{
			user = cs.addTechnician(string, string, TechnicianType.Tire);
		}
		
		else
		{
			user = cs.addCustomer(string, string);
		}
		errorCntr = 0;
		
	}


	@When("the user tries to update account with a new username {string} and password {string}")
	public void the_user_tries_to_update_account_with_a_new_username_and_password(String string, String string2)  {

		user = CarShopApplication.getUser();
		try {
			CarShopController.updateUser(string, string2);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
		
	}

	@Then("the account shall not be updated")
	public void the_account_shall_not_be_updated(){
		assertTrue(errorCntr>0);		
	}
	
	// TODO Matthew
	private String curUsername;
	private String curPassword;
	
	
	@Given("an owner account exists in the system with username {string} and password {string}")
	public void an_owner_account_exists_in_the_system_with_username_and_password(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
		//is this correct? do i need to first check that the owner is not already in the system and then just log them in? hmm
		Owner owner = new Owner(string, string2, cs);
		cs.setOwner(owner);		
	}

	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    curUsername = User.getWithUsername(string).getUsername();
	    curPassword = User.getWithUsername(string).getPassword();
	    try {
	    	CarShopApplication.setUsername(string);
			CarShopController.login(string, curPassword);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	    
	}

	@Given("the business has the following opening hours:")
	public void the_business_has_the_following_opening_hours(io.cucumber.datatable.DataTable table) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
		try {
		DayOfWeek dayOfWeek = null;
		Business business = cs.getBusiness();
		List<List<String>> rows = table.asLists(String.class);
		for (List<String> column : rows) {
			if(column.get(0).equals("day")) {
	    		continue;
	    	}
			dayOfWeek = CarShopController.getWeekDay(column.get(0));
			Time startTime = CarShopController.stringToTimeMatthew(column.get(1));
			Time endTime = CarShopController.stringToTimeMatthew(column.get(2));
			BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);
			business.addBusinessHour(businessHour);

		}
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
			
	}

    @After
    public void teardown() {
    	if(cs!=null) {
        	cs.delete();
        	numberOfChanges = 0;
        	user = null;
        	username = null;
        	password = null;
        	curPassword = null;
        	curUsername = null;
    	}
    	CarShopApplication.restart();
    }
	
	// DEFINE SERVICE COMBO	
	
	@Given("a Carshop system exists")
	public void a_carshop_system_exists() {
        // Write code here that turns the phrase above into concrete actions
    	cs = CarShopApplication.getCarShop();
		error = "";
		errorCntr = 0;
	}

	@Given("an owner account exists in the system")
	public void an_owner_account_exists_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
		Owner owner = new Owner("owner", "owner", cs);
		cs.setOwner(owner); // unnecessary step
	}

	@Given("a business exists in the system")
	public void a_business_exists_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
		Business bs = new Business("car-shop", "montreal", "5141234567", "xyz@mcgill.ca", cs);
		cs.setBusiness(bs); // unnecessary step
	}

	@Given("the following technicians exist in the system:")
	public void the_following_technicians_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
		List<List<String>> rows = dataTable.asLists(String.class);
	    for (List<String> columns : rows) {
	    	if(columns.get(0).contains("name")) continue;
	    	String str = columns.get(0);
	    	Technician.TechnicianType aType = null;
	    	str = str.toLowerCase();
			if(str.contains("tire")) {
				aType = Technician.TechnicianType.Tire;
			}
			else if(str.contains("engine")) {
				aType = Technician.TechnicianType.Engine;
			}
			else if(str.contains("transmission")) {
				aType = Technician.TechnicianType.Transmission;
			}
			else if(str.contains("electronics")) {
				aType = Technician.TechnicianType.Electronics;
			}
			else if(str.contains("fluids")) {
				aType = Technician.TechnicianType.Fluids;
			}
	        cs.addTechnician(new Technician(columns.get(0), columns.get(1), aType, cs));
	    }
	}


	@Given("each technician has their own garage")
	public void each_technician_has_their_own_garage() {
	    // Write code here that turns the phrase above into concrete actions
		for(Technician t : cs.getTechnicians()) {
			t.setGarage(new Garage(cs, t));
		}
	}

	@Given("the following services exist in the system:")
	public void the_following_services_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
		List<List<String>> rows = dataTable.asLists();
	    for (List<String> columns : rows) {
	    if(columns.get(0).contains("name")) continue;
	    else {
	    	int toPut = cs.getTechnicianWithString(columns.get(0));
	    	cs.addBookableService(new Service(columns.get(0), cs, Integer.parseInt(columns.get(1)), 
	    			cs.getTechnician(toPut).getGarage()));
	    	}
	    }
	}
	
	@Given("the Owner with username {string} is logged in")
	public void the_owner_with_username_is_logged_in(String string) {
	    // Write code here that turns the phrase above into concrete actions
		String password = CarShopApplication.getCarShop().getOwner().getPassword();
		CarShopApplication.logIn(string, password);
	}

	@When("{string} initiates the definition of a service combo {string} with main service {string}, services {string} and mandatory setting {string}")
	public void initiates_the_definition_of_a_service_combo_with_main_service_services_and_mandatory_setting(String string, String string2, String string3, String string4, String string5) throws InvalidInputException {
	    // Write code here that turns the phrase above into concrete actions
	    try {
	    	CarShopController.OwnerDefinesServiceCombo(string, string2, string3, string4, string5, cs);
	    } catch (Exception e) {
	    	error += e.getMessage();
   			errorCntr++;
	    }	
	}

	@SuppressWarnings("static-access")
	@Then("the service combo {string} shall exist in the system")
	public void the_service_combo_shall_exist_in_the_system(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(string, cs.getBookableService(0).getWithName(string).getName());// gets the first bookableservice
	}

	@Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
	public void the_service_combo_shall_contain_the_services_with_mandatory_setting(String string, String string2, String string3) {
	    // Write code here that turns the phrase above into concrete actions
		boolean[] mandatoryList = CarShopController.parseStringByMandatory(string3);
		int index = cs.getBookableServices().size()-1;
	
		for(int i = 0; i < mandatoryList.length; i++) {
			@SuppressWarnings("static-access")
			BookableService check = cs.getBookableService(index).getWithName(string);
			boolean b = check.getMainService().getServiceCombo().getService(i).getMandatory();
			assertEquals(mandatoryList[i], b);
		}
	}

	@Then("the main service of the service combo {string} shall be {string}")
	public void the_main_service_of_the_service_combo_shall_be(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
		// checks if the service combo is in fact string1
		assertEquals(string2, cs.getBookableService(cs.getBookableServices().size()-1).getMainService().getService().getName());
//		assertEquals(string2, cs.getBookableService(cs.getBookableServices().size()-1).getWithName(string).getMainService().getService().getName());

	}

	@Then("the service {string} in service combo {string} shall be mandatory")
	public void the_service_in_service_combo_shall_be_mandatory(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(true, cs.getBookableService(cs.getBookableServices().size()-1).getMainService().getMandatory());
	}

	@Then("the number of service combos in the system shall be {string}")
	public void the_number_of_service_combos_in_the_system_shall_be(String string) {
	    // Write code here that turns the phrase above into concrete actions
		int i = 0;
		int j = 0;
		while(i < cs.getBookableServices().size()) {
			BookableService toCheck = cs.getBookableService(i);
			if(toCheck.getMainService()==null) j++;
			i++;
		}
		int compare = Integer.parseInt(string);
		assertEquals(compare, cs.numberOfBookableServices()-j);
	}

	@Given("the following service combos exist in the system:")
	public void the_following_service_combos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
		List<List<String>> rows = dataTable.asLists();
	    for (List<String> columns : rows) {
	    if(columns.get(0).contains("name")) continue;
	    else {
	    	ServiceCombo sc = new ServiceCombo(columns.get(0), cs);
	    	cs.addBookableService(sc);
	    	// gets the main service
	    	@SuppressWarnings("static-access")
			Service ms = (Service) cs.getBookableService(0).getWithName(columns.get(1));
	    	sc.setMainService(new ComboItem(true, ms, sc));
	    	List<String> services = Arrays.asList(columns.get(2).split(","));
	    	List<String> bools = Arrays.asList(columns.get(3).split(","));
	    	int i = 0;
			for(String s : services) {
				@SuppressWarnings("static-access")
				Service toAdd = (Service) cs.getBookableService(0).getWithName(s);
				boolean toAdd1 = Boolean.parseBoolean(bools.get(i));
				if(toAdd.getName().equals(ms.getName())) { i++; continue; }
				cs.getBookableService(cs.getBookableServices().size()-1).getMainService().getServiceCombo().addService(toAdd1, toAdd);
				i++;
			}
	    	
	    	}
	    }
	}

	@Then("an error message with content {string} shall be raised")
	public void an_error_message_with_content_shall_be_raised(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(string, error);	
	}

	@SuppressWarnings("static-access")
	@Then("the service combo {string} shall not exist in the system")
	public void the_service_combo_shall_not_exist_in_the_system(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(false, cs.getBookableService(0).hasWithName(string));
	}

	@SuppressWarnings("static-access")
	@Then("the service combo {string} shall preserve the following properties:")
	public void the_service_combo_shall_preserve_the_following_properties(String string, io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(string, cs.getBookableService(0).getWithName(string).getName());
	}

	@Given("the following customers exist in the system:")
	public void the_following_customers_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
    	List<List<String>> rows = dataTable.asLists();
		int i = 0;
	    for (List<String> columns : rows) {
	    if(i == 0) {
	    	i++;
	    }
	    else {
	    	String username = columns.get(0);
	    	String password = columns.get(1);
	    	cs.addCustomer(username, password);
	    	}
	    }

	}

	@Given("the user with username {string} is logged in")
	public void the_user_with_username_is_logged_in(String string) {
	    // Write code here that turns the phrase above into concrete actions
		if(string.equals("owner")) CarShopApplication.setAccountType(CarShopApplication.AccountType.Owner);
		else if(string.contains("technician")) {
			Technician.TechnicianType a = cs.getTechnician(0).getTechnicianType(string);
			if(a.equals(Technician.TechnicianType.Engine)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.EngineTechnician);
				CarShopApplication.setLoggedIn(true);
			}
			else if(a.equals(Technician.TechnicianType.Tire)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.TireTechnician);
				CarShopApplication.setLoggedIn(true);
			}
			else if(a.equals(Technician.TechnicianType.Transmission)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.TransmissionTechnician);
				CarShopApplication.setLoggedIn(true);
			}
			else if(a.equals(Technician.TechnicianType.Electronics)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.ElectronicsTechnician);
				CarShopApplication.setLoggedIn(true);
			}
			else {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.FluidsTechnician);
				CarShopApplication.setLoggedIn(true);
			}
		}
		else {
			CarShopApplication.setAccountType(CarShopApplication.AccountType.Customer);
			CarShopApplication.setLoggedIn(true);
		}
	}
	
	
	// UPDATE SERVICE COMBO
	
    @When("{string} initiates the update of service combo {string} to name {string}, main service {string} and services {string} and mandatory setting {string}")
    public void initiates_the_update_of_service_combo_to_name_main_service_and_services_and_mandatory_setting(String string, String string2, String string3, String string4, String string5, String string6) {
        // Write code here that turns the phrase above into concrete actions
	    try {
	    	CarShopController.updateServiceCombo(string, string2, string3, string4, string5, string6, cs);
	    } catch (Exception e) {
	    	error += e.getMessage();
   			errorCntr++;
	    }    
    }

    @SuppressWarnings("static-access")
	@Then("the service combo {string} shall be updated to name {string}")
    public void the_service_combo_shall_be_updated_to_name(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
    	assertEquals(string2, cs.getBookableService(0).getWithName(string2).getName());
    }
    
    // TODO Mathieu
    @SuppressWarnings("static-access")
	@Then("the service {string} shall exist in the system")
    public void the_service_shall_exist_in_the_system(String string) {
    	 assertEquals(string, cs.getBookableService(0).getWithName(string).getName());
    }

    @Then("the service {string} shall belong to the garage of {string} technician")
    public void the_service_shall_belong_to_the_garage_of_technician(String string, String string2) {
        Technician tech = cs.getTechnician(cs.getTechnicianWithString(string));
        assertEquals(tech, ((Service) (cs.getBookableService(cs.getBookableServices().size()-1))).getGarage().getTechnician());
    }

    @Then("the number of services in the system shall be {string}")
    public void the_number_of_services_in_the_system_shall_be(String string) {
    	int compare = Integer.parseInt(string);
    	int counter = 0;
    	for (BookableService bookSer : cs.getBookableServices()) {
    		if (bookSer instanceof Service) {
    			counter++;
    		}
    	}
    	assertEquals(compare, counter);
    	
    }

    @SuppressWarnings("static-access")
	@Then("the service {string} shall not exist in the system")
    public void the_service_shall_not_exist_in_the_system(String string) {
    	List<BookableService> serviceList = cs.getBookableServices();
    	int i;
    	boolean serviceExists = false;
    	for (i = 0; i < serviceList.size(); i++) {
    		if (cs.getBookableService(i).hasWithName(string))
    		serviceExists = true;
    	}
    	assertEquals(false, serviceExists);
    }

    @Then("the service {string} shall still preserve the following properties:")
    public void the_service_shall_still_preserve_the_following_properties(String string, io.cucumber.datatable.DataTable dataTable) {
    	Service service = (Service) (cs.getBookableService(0));
    	List<String> inputs = dataTable.asList();
    	assertEquals(inputs.get(3), service.getName());
    	assertEquals(Integer.parseInt(inputs.get(4)), service.getDuration());
    	assertEquals(cs.getGarage(cs.getTechnicianWithString(inputs.get(5) + "-")), service.getGarage());
    }
    
    @When("{string} initiates the addition of the service {string} with duration {string} belonging to the garage of {string} technician")
    public void initiates_the_addition_of_the_service_with_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4) {
    	try {
	    	CarShopController.ownerDefinesService(string, string2, string3, string4, cs);
	    } catch (Exception e) {
	    	error += e.getMessage();
   			errorCntr++;
	    }    	
        
    }
    
    //updating service
    
    @When("{string} initiates the update of the service {string} to name {string}, duration {string}, belonging to the garage of {string} technician")
    public void initiates_the_update_of_the_service_to_name_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4, String string5) {
    	try {
	    	CarShopController.updateService(string, string2, string3, string4, string5, cs);
	    } catch (Exception e) {
	    	error += e.getMessage();
   			errorCntr++;
	    }  
    }

    @Then("the service {string} shall be updated to name {string}, duration {string}")
    public void the_service_shall_be_updated_to_name_duration(String string, String string2, String string3) {
       assertEquals(string2, cs.getBookableService(0).getName());
       assertEquals(Integer.parseInt(string3), cs.getBookableService(0).getDuration());
    }
    

	@When("the user tries to log in with username {string} and password {string}")
	public void the_user_tries_to_log_in_with_username_and_password(String string, String string2) {
		try {
			//do we need to include which type of user they are in the constructor for a user (or somewhere else)?

			curUsername = string;
			curPassword = string2;
			CarShopController.login(string, string2);
		}catch(Exception e) {
			error =e.getMessage();
			errorCntr++;
		}
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("the user should be successfully logged in")
	public void the_user_should_be_successfully_logged_in() {
	    // Write code here that turns the phrase above into concrete actions
//		assertNotNull(CarShopApplication.getUser());
		//is this correct?
		assertEquals(curPassword, CarShopApplication.getUser().getPassword());
		assertEquals(curUsername, CarShopApplication.getUser().getUsername());
//		assertEquals(CarShopApplication.AccountType.Customer, CarShopApplication.getAccountType(CarShopApplication.getUser()));
		}

	@Then("the user should not be logged in")
	public void the_user_should_not_be_logged_in() {
		// how does this tie in to the previous scenario? is it assumed that this is a new scenario? how do we know?

		assertNull(CarShopApplication.getUser());
//		assertNotEquals(curUsername, CarShopApplication.getUser().getUsername());
	}
	

	@Then("an error message {string} shall be raised")
	public void an_error_message_shall_be_raised(String string) throws Exception {
		
	    assertEquals(string, error);
	}

	@Then("a new account shall be created")
	public void a_new_account_shall_be_created() {
	    // Write code here that turns the phrase above into concrete actions
		CarShopController.newAccount(curUsername, curPassword, cs);
		assertEquals(curUsername, User.getWithUsername(curUsername).getUsername());
		assertEquals(curPassword, User.getWithUsername(curUsername).getPassword());
	}


	@Then("the user shall be successfully logged in")
	public void the_user_shall_be_successfully_logged_in() {
		try{
			CarShopController.login(curUsername, curPassword);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(curUsername, CarShopApplication.getUser().getUsername());
		assertEquals(curPassword, CarShopApplication.getUser().getPassword());
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("the account shall have username {string}, password {string} and technician type {string}")
	public void the_account_shall_have_username_password_and_technician_type(String string, String string2, String string3) {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(string3, CarShopController.getTechnicianType(string).toString());
		assertEquals(string, User.getWithUsername(string).getUsername());
		assertEquals(string2, User.getWithUsername(string).getPassword());
	}

	@Then("the corresponding garage for the technician shall be created")
	public void the_corresponding_garage_for_the_technician_shall_be_created() {
		// get technician type
		Technician techGuy = cs.getTechnician(CarShopController.getTechnician(curUsername, cs));
		CarShopController.setGarage(techGuy, cs);
		assertTrue(techGuy.hasGarage());
	}

	@Then("the garage should have the same opening hours as the business")
	public void the_garage_should_have_the_same_opening_hours_as_the_business() {
	    // Write code here that turns the phrase above into concrete actions
		//once we figure out how to return a technician based on their username, we can access their garage and the garage's business hours.
//		assertEquals()
		//TODO
		Technician techGuy = cs.getTechnician(CarShopController.getTechnician(curUsername, cs));
		Garage techGuyGarage = techGuy.getGarage();
		Business csBusiness = cs.getBusiness();
		List<BusinessHour> businessHours;
		if(csBusiness == null) {
			businessHours = new ArrayList<BusinessHour>(); 
		}else{
			businessHours = csBusiness.getBusinessHours();
			for(BusinessHour entry: businessHours) {
				techGuyGarage.addBusinessHour(entry);
			}
		}

		//carshop has a business belonging to it, as well as a list of garages.
		assertEquals(businessHours, techGuyGarage.getBusinessHours());
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	
	
	@Then("the garage belonging to the technician with type {string} should have opening hours on {string} from {string} to {string}")
	public void the_garage_belonging_to_the_technician_with_type_should_have_opening_hours_on_from_to(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		try {
		Technician technician = CarShopController.findTechnician(string, cs);
		boolean test = false;
		//TODO
		List<BusinessHour> businessHours = technician.getGarage().getBusinessHours();
		
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string2);
		Time startTime = CarShopController.stringToTimeMatthew(string3);
		Time endTime = CarShopController.stringToTimeMatthew(string4);
		BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);
		
		long endLong = endTime.getTime();
		
		for(BusinessHour hours:businessHours) {
			if(hours.getDayOfWeek().equals(CarShopController.getWeekDay(string2))) {
				if(hours.getStartTime().getTime() == (CarShopController.stringToTimeMatthew(string3)).getTime()) {
					if(hours.getEndTime().getTime() == (CarShopController.stringToTimeMatthew(string4)).getTime()) {
						test = true;
						break;
					}
				}
			}
			if(hours.equals(businessHour)) {
				test = true;
			}
		}


		assertTrue(test);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@Given("there are opening hours on {string} from {string} to {string} for garage belonging to the technician with type {string}")
	public void there_are_opening_hours_on_from_to_for_garage_belonging_to_the_technician_with_type(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		try {
			Technician technician = CarShopController.findTechnician(string4, cs);
			Garage garage = technician.getGarage();
			DayOfWeek dayOfWeek = CarShopController.getWeekDay(string);
			Time startTime = CarShopController.stringToTimeMatthew(string2);
			Time endTime = CarShopController.stringToTimeMatthew(string3);
			BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);
			garage.addBusinessHour(businessHour);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to remove opening hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void the_user_tries_to_remove_opening_hours_on_from_to_to_garage_belonging_to_the_technician_with_type(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		try {
		Technician technician = CarShopController.findTechnician(string4, cs);
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string);
		Time startTime = CarShopController.stringToTimeMatthew(string2);
		Time endTime = CarShopController.stringToTimeMatthew(string3);
		BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);
		BusinessHour hoursToRemove = new BusinessHour(dayOfWeek, startTime, endTime, cs);

		try {
			CarShopController.removeBusinessHour(hoursToRemove, CarShopApplication.getUser(), technician.getGarage(), cs);
		}catch(Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@Then("the garage belonging to the technician with type {string} should not have opening hours on {string} from {string} to {string}")
	public void the_garage_belonging_to_the_technician_with_type_should_not_have_opening_hours_on_from_to(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		try {
		Technician technician = CarShopController.findTechnician(string, cs);
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string2);
		Time startTime = CarShopController.stringToTimeMatthew(string3);
		Time endTime = CarShopController.stringToTimeMatthew(string4);
				
		BusinessHour hoursToAdd = new BusinessHour(dayOfWeek, startTime, endTime, cs);
		
		assertTrue(!technician.getGarage().getBusinessHours().contains(hoursToAdd));
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}
	@When("the user tries to add new business hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void the_user_tries_to_add_new_business_hours_on_from_to_to_garage_belonging_to_the_technician_with_type(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		try {
		TechnicianType technicianType = CarShopController.getTechnicianType(string4);
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string);
		int toCheck = 0;
		if(dayOfWeek.equals(BusinessHour.DayOfWeek.Monday)) toCheck = 0;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Tuesday)) toCheck = 1;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Wednesday)) toCheck = 2;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Thursday)) toCheck = 3;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Friday)) toCheck = 4;
		else throw new InvalidInputException("The opening hours are not within the opening hours");
		Time ourStartTime = CarShopController.stringToTimeMatthew(string2);
		Time ourEndTime = CarShopController.stringToTimeMatthew(string3);
		Technician technician = CarShopController.findTechnician(string4, cs);
	
		Garage garage = technician.getGarage();
		BusinessHour businessHour = new BusinessHour(dayOfWeek, ourStartTime, ourEndTime, cs);
		CarShopController.addBusinessHour(businessHour, CarShopApplication.getUser(), garage, cs);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}
	

}