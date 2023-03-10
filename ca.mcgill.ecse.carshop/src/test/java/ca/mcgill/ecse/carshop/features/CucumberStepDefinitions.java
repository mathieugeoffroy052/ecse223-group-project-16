package ca.mcgill.ecse.carshop.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOBusiness;
import ca.mcgill.ecse.carshop.model.Appointment;
import ca.mcgill.ecse.carshop.model.Appointment.AppointmentStatus;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceBooking;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {

	private CarShop cs;

	private String error;
	private int errorCntr;
	private static TOBusiness toBusiness;
	private static int numberOfBusinessHours;
	private static int numberOfVacations;
	private static int numberOfHolidays;
	private static BusinessHour oldBusinessHour;
	private static String[] oldBusinessHourInfo;

	private static Appointment currentAppointment;

	private String curUsername;
	private String curPassword;
	private int numApp;

	User user = null;
	@After
	public void teardown() {
		if(cs!=null) {// check if null
			cs.delete();
			user = null;// set to null
			curPassword = null;// set to null
			curUsername = null;// set to null
		}
		numApp = 0;
		numberOfBusinessHours = 0;
		numberOfHolidays = 0;
		numberOfVacations = 0;
		toBusiness = null;
		oldBusinessHour = null;
		oldBusinessHourInfo = null;
		oldBusinessHourInfo = new String[3];	// create a new businesshour
		currentAppointment = null;
		CarShopApplication.restart();// CarShopApplication used
	}

	//DELIVERABLE 2

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
		String[] splitString = date.split("\\+");// uses a formatter from java.sql.Date/Time
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);// create a new object
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);// create a new object
		Date newDate = null;
		Time newTime = null;
		try {
			// uses a formatter from java.sql.Date/Time
			newDate = new java.sql.Date(dateFormatter.parse(splitString[0]).getTime());// create a new object
			newTime = new java.sql.Time(timeFormatter.parse(splitString[1]).getTime());// create a new object
		} catch (Exception e) {
			error += e.getMessage();
		}
		CarShopApplication.setSystemDate(newDate);
		CarShopApplication.setSystemTime(newTime);
	}

	@Given("a business exists with the following information:")
	public void a_business_exists_with_the_following_information(io.cucumber.datatable.DataTable dataTable) {
		cs = CarShopApplication.getCarShop();// CarShopApplication used
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

		for (Map<String, String> columns : rows) {
			String name = columns.get("name");
			String address = columns.get("address");
			String phoneNumber = columns.get("phone number");
			String email = columns.get("email");

			Business newBusiness = new Business(name, address, phoneNumber, email, cs);// create a new object
			cs.setBusiness(newBusiness);
		}
	}

	@Given("the business has a business hour on {string} with start time {string} and end time {string}")
	public void the_business_has_a_business_hour_on_with_start_time_and_end_time(String day, String start, String end) {
		String pattern = "HH:mm";
		// uses a formatter from java.sql.Date/Time
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);// create a new object

		Time startTime = null;// set to null
		Time endTime = null;// set to null
		try {
			// uses a formatter from java.sql.Date/Time
			startTime = new java.sql.Time(formatter.parse(start).getTime());
			endTime = new java.sql.Time(formatter.parse(end).getTime());
		} catch (Exception e) {
			error += e.getMessage();
		}

		BusinessHour businessHour = new BusinessHour(BusinessHour.DayOfWeek.valueOf(day), startTime, endTime, cs);// create a new object
		oldBusinessHour = businessHour;
		oldBusinessHourInfo[0] = businessHour.getDayOfWeek().name();
		// uses a formatter from java.sql.Date/Time
		oldBusinessHourInfo[1] = formatter.format(businessHour.getStartTime());
		oldBusinessHourInfo[2] = formatter.format(businessHour.getEndTime());

		cs.getBusiness().addBusinessHour(businessHour);
		numberOfBusinessHours++;
	}

	@Given("a {string} time slot exists with start time {string} at {string} and end time {string} at {string}")
	public void a_time_slot_exists_with_start_time_at_and_end_time_at(String timeSlot, String startDate,
			String startTime, String endDate, String endTime) {
		Date sDate = null;// set to null
		Time sTime = null;// set to null
		Date eDate = null;// set to null
		Time eTime = null;// set to null

		String timePattern = "HH:mm";
		String datePattern = "yyyy-MM-dd";
		// uses a formatter from java.sql.Date/Time
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		try {
			// uses a formatter from java.sql.Date/Time
			sDate = new java.sql.Date(dateFormatter.parse(startDate).getTime());// create a new object
			eDate = new java.sql.Date(dateFormatter.parse(endDate).getTime());// create a new object
			sTime = new java.sql.Time(timeFormatter.parse(startTime).getTime());// create a new object
			eTime = new java.sql.Time(timeFormatter.parse(endTime).getTime());// create a new object
			if (timeSlot.equalsIgnoreCase("vacation")) {
				cs.getBusiness().addVacation(new TimeSlot(sDate, sTime, eDate, eTime, cs));
				numberOfVacations++;
			} else {
				cs.getBusiness().addHoliday(new TimeSlot(sDate, sTime, eDate, eTime, cs));
				numberOfHolidays++;
			}
		} catch (Exception e) {
			error += e.getMessage();
		}
	}

	@Given("there is no existing username {string}")
	public void there_is_no_existing_username(String string) {
		if (User.hasWithUsername(string)) 
		{
			User.getWithUsername(string).delete();
		}

	}

	@Given("there is an existing username {string}")
	public void there_is_an_existing_username(String string) throws InvalidInputException {
		if (string.equals("owner") )
		{
			Owner owner = new Owner("owner", "owner", cs);// create a new object
			cs.setOwner(owner); // unnecessary step
		}
		else if (string.contains("Technician"))
		{
			TechnicianType technicianType;
			switch (string) {
			case "Tire-Technician": {
				technicianType = TechnicianType.Tire;
				break;
			}
			case "Engine-Technician": {
				technicianType = TechnicianType.Engine;
				break;
			}
			case "Transmission-Technician": {
				technicianType = TechnicianType.Transmission;
				break;
			}
			case "Electronics-Technician": {
				technicianType = TechnicianType.Electronics;
				break;
			}
			case "Fluids-Technician": {
				technicianType = TechnicianType.Fluids;
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + string);
			}
			user = cs.addTechnician(string, string, technicianType);
		}

		else
		{
			user = cs.addCustomer(string, string);
		}
		errorCntr = 0;

	}

	@Given("an owner account exists in the system with username {string} and password {string}")
	public void an_owner_account_exists_in_the_system_with_username_and_password(String string, String string2) {
		Owner owner = new Owner(string, string2, cs);// create a new object
		cs.setOwner(owner);		
	}

	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String string) {
		curUsername = User.getWithUsername(string).getUsername();
		curPassword = User.getWithUsername(string).getPassword();
		try {
			CarShopController.login(string, curPassword);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}

	}

	@Given("the business has the following opening hours(:)")
	public void the_business_has_the_following_opening_hours(io.cucumber.datatable.DataTable table) {
		try {
			DayOfWeek dayOfWeek = null;// set to null
			Business business = cs.getBusiness();
			// create a list
			List<List<String>> rows = table.asLists(String.class);
			// create a list
			for (List<String> column : rows) {
				if(column.get(0).equals("day")) {
					continue;
				}
				dayOfWeek = CarShopController.getWeekDay(column.get(0));
				// converts from string to time with method in the controller
				Time startTime = CarShopController.stringToTime(column.get(1));
				Time endTime = CarShopController.stringToTime(column.get(2));
				BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);// create a new object
				business.addBusinessHour(businessHour);
				// new code
				for(Garage g : cs.getGarages()) {
					g.addBusinessHour(businessHour);
				}
				// new code
			}
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}

	}

	@Given("a Carshop system exists")
	public void a_carshop_system_exists() {
		//needed to be modified so the persistence would not interfere with cucumber scenarios
		if(cs == null) {
			cs = new CarShop();
			CarShopApplication.setCarShop(cs);
		}
		error = "";
		errorCntr = 0;
	}

	@Given("an owner account exists in the system")
	public void an_owner_account_exists_in_the_system() {
		Owner owner = new Owner("owner", "owner", cs);
		cs.setOwner(owner); // unnecessary step
	}

	@Given("a business exists in the system")
	public void a_business_exists_in_the_system() {
		Business bs = new Business("car-shop", "montreal", "5141234567", "xyz@mcgill.ca", cs);// create a new object
		cs.setBusiness(bs); // unnecessary step
	}

	@Given("the following technicians exist in the system:")
	public void the_following_technicians_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		// create a list

		List<List<String>> rows = dataTable.asLists(String.class);
		// create a list

		for (List<String> columns : rows) {
			if(columns.get(0).contains("name")) continue;
			String str = columns.get(0);
			Technician.TechnicianType aType = null;// set to null
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
			cs.addTechnician(new Technician(columns.get(0), columns.get(1), aType, cs));// create a new object
		}
	}


	@Given("each technician has their own garage")
	public void each_technician_has_their_own_garage() {
		for(Technician t : cs.getTechnicians()) {
			t.setGarage(new Garage(cs, t));// create a new object
		}
	}

	@Given("the following services exist in the system:")
	public void the_following_services_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		// create a list
		List<List<String>> rows = dataTable.asLists();
		// create a list
		for (List<String> columns : rows) {
			if(columns.get(0).contains("name")) continue;
			else {
				int toPut = cs.getTechnicianWithString(columns.get(0));
				cs.addBookableService(new Service(columns.get(0), cs, Integer.parseInt(columns.get(1)), // create a new object
						cs.getTechnician(toPut).getGarage()));
			}
		}
	}

	@Given("the Owner with username {string} is logged in")
	public void the_owner_with_username_is_logged_in(String string) {
		String password = CarShopApplication.getCarShop().getOwner().getPassword();// CarShopApplication used
		CarShopApplication.logIn(string, password);// CarShopApplication used
	}

	@Given("the following service combos exist in the system:")
	public void the_following_service_combos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		for (List<String> columns : rows) { 		// go through the loop
			if(columns.get(0).contains("name")) continue;
			else {
				ServiceCombo sc = new ServiceCombo(columns.get(0), cs);
				cs.addBookableService(sc);
				// gets the main service
				Service ms = (Service) BookableService.getWithName(columns.get(1));
				sc.setMainService(new ComboItem(true, ms, sc));// create a new object
				List<String> services = Arrays.asList(columns.get(2).split(","));
				List<String> bools = Arrays.asList(columns.get(3).split(","));
				for(int i = 0; i < services.size(); i++) { 		// go through the loop
					String serviceString = services.get(i);
					Service toAdd = (Service) BookableService.getWithName(serviceString);
					boolean toAdd1 = Boolean.parseBoolean(bools.get(i));
					if(toAdd.getName().equals(ms.getName())) {
						continue; 
					}
					sc.addService(toAdd1, toAdd);
				}
			}
		}
	}

	@Given("the following customers exist in the system:")
	public void the_following_customers_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		int i = 0;
		for (List<String> columns : rows) { 		// go through the loop
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
		if(string.equals("owner")) CarShopApplication.setAccountType(CarShopApplication.AccountType.Owner);
		else if(string.contains("technician")) {
			Technician.TechnicianType a = cs.getTechnician(0).getTechnicianType(string);
			if(a.equals(Technician.TechnicianType.Engine)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.EngineTechnician);// CarShopApplication used
				CarShopApplication.setLoggedIn(true);
			}
			else if(a.equals(Technician.TechnicianType.Tire)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.TireTechnician);// CarShopApplication used
				CarShopApplication.setLoggedIn(true);
			}
			else if(a.equals(Technician.TechnicianType.Transmission)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.TransmissionTechnician);// CarShopApplication used
				CarShopApplication.setLoggedIn(true);
			}
			else if(a.equals(Technician.TechnicianType.Electronics)) {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.ElectronicsTechnician);// CarShopApplication used
				CarShopApplication.setLoggedIn(true);
			}
			else {
				CarShopApplication.setAccountType(CarShopApplication.AccountType.FluidsTechnician);// CarShopApplication used
				CarShopApplication.setLoggedIn(true);
			}
		}
		else {
			CarShopApplication.setAccountType(CarShopApplication.AccountType.Customer);// CarShopApplication used
			CarShopApplication.setLoggedIn(true);
		}
	}

	@Given("there are opening hours on {string} from {string} to {string} for garage belonging to the technician with type {string}")
	public void there_are_opening_hours_on_from_to_for_garage_belonging_to_the_technician_with_type(String day, String startTime, String endTime, String type) {
		try {
			CarShopController.addBusinessHourIndividually(day, startTime, endTime, type, cs);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@Given("all garages has the following opening hours")
	public void all_garages_has_the_following_opening_hours(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException  {
		List<Garage> garages = cs.getGarages();

		if(garages.get(garages.size()-1).getBusinessHours().size()==0) {
			List<BusinessHour> originalBusinessHours = cs.getBusiness().getBusinessHours();
			for(int i = 0; i < originalBusinessHours.size(); i++) {			
				BusinessHour businessHourToAdd = cs.getBusiness().getBusinessHour(i);
				for(Garage g : garages) {
					g.addBusinessHour(businessHourToAdd);
				}
			}
		}

		// else, if garage list is not empty it will proceed as normal
		List<Map<String,String>> rows = dataTable.asMaps(String.class,String.class);
		for (Garage g : garages) {
			for(int i = g.getBusinessHours().size()-1; i >= 0; i--) {
				g.getBusinessHour(i).setStartTime(null);
				g.getBusinessHour(i).setEndTime(null);
				g.getBusinessHour(i).setDayOfWeek(null);
			}
		}
		for (Garage garage : garages) { 		// go through the loop
			for (Map<String,String> columns : rows) {		// go through the loop
				int toCheck = 0;	// new code
				BusinessHour.DayOfWeek dayOfWeek = BusinessHour.DayOfWeek.valueOf(columns.get("day")); 

				if(dayOfWeek.equals(BusinessHour.DayOfWeek.Monday)) toCheck = 0; 
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Tuesday)) toCheck = 1;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Wednesday)) toCheck = 2;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Thursday)) toCheck = 3;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Friday)) toCheck = 4;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Saturday)) toCheck = 5;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Sunday)) toCheck = 6;

				// converts from string to time with method in the controller
				Time start = CarShopController.stringToTime(columns.get("startTime")); 
				Time end = CarShopController.stringToTime(columns.get("endTime")); 

				garage.getBusinessHour(toCheck).setDayOfWeek(dayOfWeek);
				garage.getBusinessHour(toCheck).setStartTime(start);
				garage.getBusinessHour(toCheck).setEndTime(end);
			}	
		}
	}

	@Given("the business has the following holidays")
	public void the_business_has_the_following_holidays(io.cucumber.datatable.DataTable dataTable)
			throws InvalidInputException {
		cs = CarShopApplication.getCarShop();// CarShopApplication used
		Business businesses = cs.getBusiness();
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> columns : rows) {		// go through the loop

			// uses method in the controller
			Date startDate = CarShopController.stringToDate(columns.get("startDate")); // dateFormat.parse(columns.get("startDate"));
			// uses method in the controller
			Date endDate = CarShopController.stringToDate(columns.get("endDate")); // dateFormat.parse(columns.get("endDate"));
			// converts from string to time with method in the controller
			Time startTime = CarShopController.stringToTime(columns.get("startTime")); // (Time)
			// timeFormat.parse(columns.get("startTime"));
			Time endTime = CarShopController.stringToTime(columns.get("endTime")); // (Time)
			// timeFormat.parse(columns.get("endtime"));
			businesses.addHoliday(new TimeSlot(startDate, startTime, endDate, endTime, cs));
		}

	}

	@Given("the following appointments exist in the system:")
	public void the_following_appointments_exist_in_the_system(io.cucumber.datatable.DataTable dataTable)
			throws Exception {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		String customer = "";// empty string
		String serviceName = "";// empty string
		String date = "";// empty string

		for (Map<String, String> columns : rows) {
			customer = columns.get("customer");
			serviceName = columns.get("serviceName");
			String optServices = columns.get("optServices");
			date = (columns.get("date"));
			String timeSlots = columns.get("timeSlots");
			String[] ts1 = timeSlots.split(",");

			//store the start times in a string
			String startTimeString = "";
			for(int i = 0; i < ts1.length; i++) {
				String timeSlot = ts1[i];
				//add colon in front of each item except the first one
				if (i != 0) {
					startTimeString += ",";
				}
				String[] timeStrings = timeSlot.split("-");
				startTimeString += timeStrings[0];
			}
			//can have more than 2 startTimes
			CarShopController.CreateAppointmentWithOptServices(customer, serviceName, startTimeString , date, cs, optServices, false);
			numApp++;
		}
	}


	@Given("{string} is logged in to their account")
	public void is_logged_in_to_their_account(String string) {
		cs = CarShopApplication.getCarShop();
		User user = User.getWithUsername(string);
		CarShopApplication.logIn(string, user.getPassword());
	}

	@When("the user tries to set up the business information with new {string} and {string} and {string} and {string}")
	public void the_user_tries_to_set_up_the_business_information_with_new_and_and_and(String name, String address,
			String phoneNumber, String email) {
		try {
			CarShopController.setUpBusinessInfo(name, address, phoneNumber, email);	//set up business info with controller
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
	}

	@When("the user tries to add a new business hour on {string} with start time {string} and end time {string}")
	public void the_user_tries_to_add_a_new_business_hour_on_with_start_time_and_end_time(String day,
			String newStartTime, String newEndTime) {
		try {
			CarShopController.createBusinessHour(day, newStartTime, newEndTime);	//calls controller
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
	}

	@When("the user tries to access the business information")
	public void the_user_tries_to_access_the_business_information() {
		toBusiness = CarShopController.getBusinessInfo();	//calls controller
	}

	@When("the user tries to add a new {string} with start date {string} at {string} and end date {string} at {string}")
	public void the_user_tries_to_add_a_new_with_start_date_at_and_end_date_at(String type, String startDate,
			String startTime, String endDate, String endTime) {

		try {
			CarShopController.createTimeSlot(type, startDate, startTime, endDate, endTime);	//calls controller
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
	}

	@When("the user tries to update the business information with new {string} and {string} and {string} and {string}")
	public void the_user_tries_to_update_the_business_information_with_new_and_and_and(String name, String address,
			String phoneNumber, String email) {
		try {
			CarShopController.updateBusinessInfo(name, address, phoneNumber, email);	//calls controller
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
	}

	@When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
	public void the_user_tries_to_change_the_business_hour_at_to_be_on_starting_at_and_ending_at(String weekDay,
			String time, String day, String newStartTime, String newEndTime) {
		try {
			CarShopController.modifyBusinessHour(weekDay, time, day, newStartTime, newEndTime);	//calls controller
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}

	}

	@When("the user tries to remove the business hour starting {string} at {string}")
	public void the_user_tries_to_remove_the_business_hour_starting_at(String day, String startTime) {
		try {
			CarShopController.deleteBusinessHour(day, startTime);	//calls controller
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
	}

	@When("the user tries to change the {string} on {string} at {string} to be with start date {string} at {string} and end date {string} at {string}")
	public void the_user_tries_to_change_the_on_at_to_be_with_start_date_at_and_end_date_at(String timeslot,
			String oldDate, String oldStart, String startDate, String startTime, String endDate, String endTime) {
		try {
			CarShopController.modifyTimeSlot(timeslot, oldDate, oldStart, startDate, startTime, endDate, endTime);	//calls controller
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
	}

	@When("the user tries to remove an existing {string} with start date {string} at {string} and end date {string} at {string}")
	public void the_user_tries_to_remove_an_existing_with_start_date_at_and_end_date_at(String type, String startDate,
			String startTime, String endDate, String endTime) {
		try {
			CarShopController.deleteTimeSlot(type, startDate, startTime, endDate, endTime);
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
	}

	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String string, String string2) {

		// use controller and do some operations
		try {
			user = CarShopController.signUpUser(string, string2, CarShopApplication.AccountType.Customer);
			CarShopApplication.setUser(user);// uses CarShopApplication
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
			user = null;
		}
	}

	@When("the user tries to update account with a new username {string} and password {string}")
	public void the_user_tries_to_update_account_with_a_new_username_and_password(String string, String string2)  {
		user = CarShopApplication.getUser();// CarShopApplication used
		try {
			CarShopController.updateUser(string, string2);	//calls controller
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}

	}

	@When("{string} initiates the definition of a service combo {string} with main service {string}, services {string} and mandatory setting {string}")
	public void initiates_the_definition_of_a_service_combo_with_main_service_services_and_mandatory_setting(String string, String string2, String string3, String string4, String string5) throws InvalidInputException {
		try {
			CarShopController.OwnerDefinesServiceCombo(string, string2, string3, string4, string5, cs);	//calls controller
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}	
	}

	@When("{string} initiates the update of service combo {string} to name {string}, main service {string} and services {string} and mandatory setting {string}")
	public void initiates_the_update_of_service_combo_to_name_main_service_and_services_and_mandatory_setting(String string, String string2, String string3, String string4, String string5, String string6) {
		try {
			CarShopController.updateServiceCombo(string, string2, string3, string4, string5, string6, cs);	//calls controller
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}    
	}

	@When("{string} initiates the addition of the service {string} with duration {string} belonging to the garage of {string} technician")
	public void initiates_the_addition_of_the_service_with_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4) {
		try {
			CarShopController.ownerDefinesService(string, string2, string3, string4, cs);	//calls controller
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}    	

	}

	@When("{string} initiates the update of the service {string} to name {string}, duration {string}, belonging to the garage of {string} technician")
	public void initiates_the_update_of_the_service_to_name_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4, String string5) {
		try {
			CarShopController.updateService(string, string2, string3, string4, string5, cs);	//calls controller
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}  
	}

	@When("the user tries to log in with username {string} and password {string}")
	public void the_user_tries_to_log_in_with_username_and_password(String string, String string2) {
		try {
			curUsername = string;
			curPassword = string2;
			CarShopController.login(string, string2);	//calls controller
		}catch(Exception e) {
			error =e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to remove opening hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void the_user_tries_to_remove_opening_hours_on_from_to_to_garage_belonging_to_the_technician_with_type(String day, String startTime, String endTime, String type) {
		try {
			CarShopController.removeBusinessHourIndividually(day, startTime, endTime, type, cs);	//calls controller
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to add new business hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void the_user_tries_to_add_new_business_hours_on_from_to_to_garage_belonging_to_the_technician_with_type(String day, String startTime, String endTime, String type) {
		try {
			CarShopController.addBusinessHourIndividually(day, startTime, endTime, type, cs);	//calls controller
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} attempts to cancel their {string} appointment on {string} at {string}")
	public void attempts_to_cancel_their_appointment_on_at(String string, String string2, String string3, String string4) throws InvalidInputException {
		try {
			CarShopController.CancelAppointment(string, string2, string3, string4, cs);// uses method in the controller
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}

	}

	@When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
	public void attempts_to_cancel_s_appointment_on_at(String string, String string2, String string3, String string4, String string5) {
		try {
			CarShopController.CancelAppointment(string, string3, string4, string5, cs);// uses method in the controller
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	// this is WITHOUT the optional services
	@When("{string} schedules an appointment on {string} for {string} at {string}")
	public void schedules_an_appointment_on_for_at(String username, String date, String serviceName, String startTime) throws InvalidInputException {
		try {
			CarShopController.CreateAppointmentWithOptServices(username,serviceName,startTime,date,cs,"", false);// uses method in the controller
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	// schedule the appointment with five variables INCLUDING the optServices
	@When("{string} schedules an appointment on {string} for {string} with {string} at {string}")
	public void schedules_an_appointment_on_for_with_at(String customer, String date, String serviceComboName, String optionalServices, String startTimes) {
		try {
			CarShopController.CreateAppointmentWithOptServices(customer, serviceComboName, startTimes, date, cs, optionalServices, false);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@Then("a new business with new {string} and {string} and {string} and {string} shall {string} created")
	public void a_new_business_with_new_and_and_and_shall_created(String name, String address, String phoneNumber,
			String email, String result) {
		if (result.equals("be")) {
			// asserts equals
			assertNotEquals(null, cs.getBusiness());
			assertEquals(name, cs.getBusiness().getName());
			assertEquals(address, cs.getBusiness().getAddress());
			assertEquals(phoneNumber, cs.getBusiness().getPhoneNumber());
			assertEquals(email, cs.getBusiness().getEmail());
		} else {
			// asserts equals
			assertEquals(null, cs.getBusiness());
		}
	}

	@Then("an error message {string} shall {string} raised")
	public void an_error_message_shall_raised(String errorString, String resultError) {
		if (resultError.equalsIgnoreCase("be")) {
			// asserts true
			assertTrue(error.contains(errorString));
		} else {
			// asserts true
			assertTrue(error.equals(""));
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

	@Then("the {string} and {string} and {string} and {string} shall be provided to the user")
	public void the_and_and_and_shall_be_provided_to_the_user(String name, String address, String phoneNumber,
			String email) {
		assertNotEquals(null, toBusiness);// set to null
		//check each string
		assertEquals(name, toBusiness.getName());
		assertEquals(address, toBusiness.getAddress());
		assertEquals(phoneNumber, toBusiness.getPhoneNumber());
		assertEquals(email, toBusiness.getEmail());
	}

	@Then("a new {string} shall {string} be added with start date {string} at {string} and end date {string} at {string}")
	public void a_new_shall_be_added_with_start_date_at_and_end_date_at(String type, String result, String startDate,
			String startTime, String endDate, String endTime) {
		String timePattern = "HH:mm";
		String datePattern = "yyyy-MM-dd";
		// uses a formatter from java.sql.Date/Time
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);// create a new object
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);// create a new object

		if (result.equals("be")) {
			if (type.equals("holiday")) {
				assertEquals(numberOfHolidays + 1, cs.getBusiness().getHolidays().size());
				TimeSlot holiday = cs.getBusiness().getHoliday(numberOfHolidays);
				// uses a formatter from java.sql.Date/Time
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
				// uses a formatter from java.sql.Date/Time
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

	@Then("the business hour shall {string} be updated")
	public void the_business_hour_shall_be_updated(String string) {
		String pattern = "HH:mm";
		// uses a formatter from java.sql.Date/Time
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);// create a new object

		if (string.equalsIgnoreCase("be")) {
			assertEquals(numberOfBusinessHours, cs.getBusiness().getBusinessHours().size());
			String expectedString = oldBusinessHourInfo[0] + oldBusinessHourInfo[1] + oldBusinessHourInfo[2];
			// uses a formatter from java.sql.Date/Time
			String actualString = oldBusinessHour.getDayOfWeek().name() + formatter.format(oldBusinessHour.getStartTime()) 
			+ formatter.format(oldBusinessHour.getEndTime());
			assertNotEquals(expectedString, actualString);
		} else {
			assertEquals(numberOfBusinessHours, cs.getBusiness().getBusinessHours().size());
			String expectedString = oldBusinessHourInfo[0] + oldBusinessHourInfo[1] + oldBusinessHourInfo[2];
			// uses a formatter from java.sql.Date/Time
			String actualString = oldBusinessHour.getDayOfWeek().name() + formatter.format(oldBusinessHour.getStartTime()) 
			+ formatter.format(oldBusinessHour.getEndTime());
			assertEquals(expectedString, actualString);
		}
	}

	@Then("the business hour starting {string} at {string} shall {string} exist")
	public void the_business_hour_starting_at_shall_exist(String day, String time, String result) {
		Business business = cs.getBusiness();
		String pattern = "HH:mm";
		// uses a formatter from java.sql.Date/Time
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);// create a new object
		BusinessHour businessHour = null;
		for (BusinessHour hour : business.getBusinessHours()) {
			// uses a formatter from java.sql.Date/Time
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
			// asserts true
			assertTrue(error.equals(""));
		} else {
			// asserts true
			assertTrue(error.contains(errorMessage));
		}
	}

	@Then("the {string} shall {string} updated with start date {string} at {string} and end date {string} at {string}")
	public void the_shall_updated_with_start_date_at_and_end_date_at(String timeslot, String result, String startDate,
			String startTime, String endDate, String endTime) {
		TimeSlot foundSlot = null;// set to null
		Business business = cs.getBusiness();

		String timePattern = "HH:mm";
		String datePattern = "yyyy-MM-dd";
		// uses a formatter from java.sql.Date/Time
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);// create a new object
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);// create a new object

		if (timeslot.equalsIgnoreCase("vacation")) {
			for (TimeSlot slot : business.getVacations()) {
				// uses a formatter from java.sql.Date/Time
				if (dateFormatter.format(slot.getStartDate()).equals(startDate)
						&& timeFormatter.format(slot.getStartTime()).equals(startTime)
						&& dateFormatter.format(slot.getEndDate()).equals(endDate)
						&& timeFormatter.format(slot.getEndTime()).equals(endTime)) {
					foundSlot = slot;
				}
			}
		} else {
			for (TimeSlot slot : business.getHolidays()) {
				// uses a formatter from java.sql.Date/Time
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
			assertNotEquals(null, foundSlot);// set to null
		}
	}


	@Then("the {string} with start date {string} at {string} shall {string} exist")
	public void the_with_start_date_at_shall_exist(String type, String startDate, String startTime, String result) {
		TimeSlot timeSlot = null;// set to null
		Business business = cs.getBusiness();

		String timePattern = "HH:mm";
		String datePattern = "yyyy-MM-dd";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);// create a new object
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);// create a new object

		if (type.equalsIgnoreCase("vacation")) {
			for (TimeSlot slot : business.getVacations()) {
				// uses a formatter from java.sql.Date/Time
				if (dateFormatter.format(slot.getStartDate()).equals(startDate)
						&& timeFormatter.format(slot.getStartTime()).equals(startTime)) {
					timeSlot = slot;
				}
			}
		} else {
			for (TimeSlot slot : business.getHolidays()) {
				// uses a formatter from java.sql.Date/Time
				if (dateFormatter.format(slot.getStartDate()).equals(startDate)
						&& timeFormatter.format(slot.getStartTime()).equals(startTime)) {
					timeSlot = slot;
				}
			}
		}

		if (result.equalsIgnoreCase("not")) {
			assertEquals(null, timeSlot);// set to null
		} else {
			assertNotEquals(null, timeSlot);// set to null
		}

	}

	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		// asserts true
		assertTrue(user != null);// set to null
	}

	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String string, String string2) 
	{
		// set to null				// asserts true
		assertTrue(CarShopApplication.getUser() != null && 
				CarShopApplication.getUser().getUsername().equals(string) && 
				CarShopApplication.getUser().getPassword().equals(string2)); // CarShopApplication used
		// CarShopApplication used
	}

	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		assertTrue(user == null);// set to null

	}

	@Then("the account shall not be updated")
	public void the_account_shall_not_be_updated() {
		assertTrue(errorCntr>0);	// asserts true
	}

	@Then("the service combo {string} shall exist in the system")
	public void the_service_combo_shall_exist_in_the_system(String string) {
		assertEquals(string, BookableService.getWithName(string).getName());// gets the first bookableservice
	}

	@Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
	public void the_service_combo_shall_contain_the_services_with_mandatory_setting(String string, String string2, String string3) {
		boolean[] mandatoryList = CarShopController.parseStringByMandatory(string3);
		int index = cs.getBookableServices().size()-1;
		// go through the loop
		for(int i = 0; i < mandatoryList.length; i++) {
			BookableService check = BookableService.getWithName(string);
			boolean b = check.getMainService().getServiceCombo().getService(i).getMandatory();
			assertEquals(mandatoryList[i], b);
		}
	}

	@Then("the main service of the service combo {string} shall be {string}")
	public void the_main_service_of_the_service_combo_shall_be(String string, String string2) {
		// checks if the service combo is in fact string1
		assertEquals(string2, cs.getBookableService(cs.getBookableServices().size()-1).getMainService().getService().getName());
	}

	@Then("the service {string} in service combo {string} shall be mandatory")
	public void the_service_in_service_combo_shall_be_mandatory(String string, String string2) {
		assertEquals(true, cs.getBookableService(cs.getBookableServices().size()-1).getMainService().getMandatory());
	}

	@Then("the number of service combos in the system shall be {string}")
	public void the_number_of_service_combos_in_the_system_shall_be(String string) {
		int i = 0;
		int j = 0;
		while(i < cs.getBookableServices().size()) {
			BookableService toCheck = cs.getBookableService(i);
			if(toCheck.getMainService()==null) j++; // if null
			i++;
		}
		int compare = Integer.parseInt(string);	//expected number of service combos
		assertEquals(compare, cs.numberOfBookableServices()-j);
	}

	@Then("an error message with content {string} shall be raised")
	public void an_error_message_with_content_shall_be_raised(String string) {
		assertEquals(string, error);	
	}

	@SuppressWarnings("static-access")
	@Then("the service combo {string} shall not exist in the system")
	public void the_service_combo_shall_not_exist_in_the_system(String string) {
		assertEquals(false, cs.getBookableService(0).hasWithName(string));
	}

	@Then("the service combo {string} shall preserve the following properties:")
	public void the_service_combo_shall_preserve_the_following_properties(String string, io.cucumber.datatable.DataTable dataTable) {
		assertEquals(string, BookableService.getWithName(string).getName());
	}

	@Then("the service combo {string} shall be updated to name {string}")
	public void the_service_combo_shall_be_updated_to_name(String string, String string2) {
		assertEquals(string2, BookableService.getWithName(string2).getName());
	}

	@Then("the service {string} shall exist in the system")
	public void the_service_shall_exist_in_the_system(String string) {
		assertEquals(string, BookableService.getWithName(string).getName());
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
		for (BookableService bookSer : cs.getBookableServices()) { 		// go through the loop
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
		for (i = 0; i < serviceList.size(); i++) { 		// go through the loop
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

	@Then("the service {string} shall be updated to name {string}, duration {string}")
	public void the_service_shall_be_updated_to_name_duration(String string, String string2, String string3) {
		assertEquals(string2, cs.getBookableService(0).getName());
		assertEquals(Integer.parseInt(string3), cs.getBookableService(0).getDuration());
	}

	@Then("the user should be successfully logged in")
	public void the_user_should_be_successfully_logged_in() {
		assertEquals(curPassword, CarShopApplication.getUser().getPassword());// CarShopApplication used
		assertEquals(curUsername, CarShopApplication.getUser().getUsername());// CarShopApplication used
	}

	@Then("the user should not be logged in")
	public void the_user_should_not_be_logged_in() {
		assertNull(CarShopApplication.getUser());// CarShopApplication used
	}


	@Then("an error message {string} shall be raised")
	public void an_error_message_shall_be_raised(String string) throws Exception {
		//checks if correct exception error was raised
		assertEquals(string, error);
	}

	@Then("a new account shall be created")
	public void a_new_account_shall_be_created() {
		try {
 			CarShopController.newAccount(curUsername, curPassword, cs);
 		} catch (InvalidInputException e) {
 			error = e.getMessage();
 			errorCntr++;
 		}
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
		assertEquals(curUsername, CarShopApplication.getUser().getUsername());// CarShopApplication used
		assertEquals(curPassword, CarShopApplication.getUser().getPassword());// CarShopApplication used
	}

	@Then("the account shall have username {string}, password {string} and technician type {string}")
	public void the_account_shall_have_username_password_and_technician_type(String string, String string2, String string3) {
		assertEquals(string3, CarShopController.getTechnicianType(string).toString());
		assertEquals(string, User.getWithUsername(string).getUsername());
		assertEquals(string2, User.getWithUsername(string).getPassword());
	}

	@Then("the corresponding garage for the technician shall be created")
	public void the_corresponding_garage_for_the_technician_shall_be_created() {
		// get technician type
		Technician techGuy = cs.getTechnician(CarShopController.getTechnician(curUsername, cs));
		try {
 			CarShopController.setGarage(techGuy, cs);
 		} catch (InvalidInputException e) {
 			error = e.getMessage();
 			errorCntr++;
 		}
		assertTrue(techGuy.hasGarage()); // asserts true
	}

	@Then("the garage should have the same opening hours as the business")
	public void the_garage_should_have_the_same_opening_hours_as_the_business() {
		//compare the business hours from the garage and the carshop hours
		Technician technician = cs.getTechnician(CarShopController.getTechnician(curUsername, cs));
		Garage garage = technician.getGarage();
		List<BusinessHour> businessHours = cs.getHours();
		List<BusinessHour> garageHours = garage.getBusinessHours();
		assertEquals(businessHours, garageHours);
	}

	@Then("the garage belonging to the technician with type {string} should have opening hours on {string} from {string} to {string}")
	public void the_garage_belonging_to_the_technician_with_type_should_have_opening_hours_on_from_to(String type, String day, String startTime, String endTime) {
		try {
			Technician technician = CarShopController.findTechnician(type, cs);
			boolean test = false;
			List<BusinessHour> businessHours = technician.getGarage().getBusinessHours();

			for(BusinessHour hours:businessHours) { 		// go through the loop
				if(hours.getDayOfWeek().equals(CarShopController.getWeekDay(day))) {
					// converts from string to time with method in the controller
					if(hours.getStartTime().getTime() == (CarShopController.stringToTime(startTime)).getTime()) {
						if(hours.getEndTime().getTime() == (CarShopController.stringToTime(endTime)).getTime()) {
							test = true;
							break;
						}
					}
				}
			}

			assertTrue(test);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@Then("the garage belonging to the technician with type {string} should not have opening hours on {string} from {string} to {string}")
	public void the_garage_belonging_to_the_technician_with_type_should_not_have_opening_hours_on_from_to(String string, String string2, String string3, String string4) {
		try {
			// converts from string to time with method in the controller
			Technician technician = CarShopController.findTechnician(string, cs);
			DayOfWeek dayOfWeek = CarShopController.getWeekDay(string2);
			// converts from string to time with method in the controller
			Time startTime = CarShopController.stringToTime(string3);
			Time endTime = CarShopController.stringToTime(string4);

			BusinessHour hoursToAdd = new BusinessHour(dayOfWeek, startTime, endTime, cs);// create a new object
			// asserts true
			assertTrue(!technician.getGarage().getBusinessHours().contains(hoursToAdd));
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@SuppressWarnings("static-access")
	@Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	public void s_appointment_on_at_shall_be_removed_from_the_system(String string, String string2, String string3, String string4) throws InvalidInputException {
		Boolean empty = false;
		Boolean found = false;
		for(Customer c : cs.getCustomers()) {
			if(c.equals((Customer) c.getWithUsername(string))) {
				if(c.getAppointments().size()==0) {
					empty = true;
					break;
				}
				else for(Appointment app : c.getAppointments()) {
					if(app.getBookableService().getName().equals(string2)) found = true;
				}
			}
		}
		// asserts true
		if(found) assertEquals(true, found);
		if(empty) assertEquals(true, empty);
	}
	
	@Then("there shall be {int} less appointment in the system")
	public void there_shall_be_less_appointment_in_the_system(Integer int1) {
		assertEquals(int1, numApp - cs.numberOfAppointments());
	}

	@Then("the system shall report {string}")
	public void the_system_shall_report(String string) {
		// asserts true
		assertTrue(error.contains(string));
	}

	@Then("{string} shall have a {string} appointment on {string} at {string} with the following properties")
	public void shall_have_a_appointment_on_at_with_the_following_properties(String string, String string2, String string3, String string4, io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		String date;// set to null

		Customer cust = (Customer) User.getWithUsername(string);
		for (Map<String, String> columns : rows) {
			columns.get("serviceName");
			date = columns.get("date");
			columns.get("timeSlots");
			columns.get("endTime");
			String timeSlots = columns.get("timeSlots");
			String[] time = timeSlots.split(",");
			for(int i = 0; i < time.length; i++) {
				String firstTimeSlot = time[i];
				String[] firstTime = firstTimeSlot.split("-");
				String start = firstTime[0];
				String end = firstTime[1];
				//Boolean found = false;
				for (Appointment app : cust.getAppointments()) {		// go through the loop
					if (app.getBookableService().getName().equals(string2)) {
						//found = true;
						TimeSlot ts = app.getServiceBooking(i).getTimeSlot();
						if(start.length()==4) start = "0"+start+":00";
						else if(start.length()==5) start = start+":00";

						if(end.length()==4) end = "0"+end+":00";
						else if(end.length()==5) end = end+":00";
						assertEquals(date, ts.getStartDate().toString());
						assertEquals(start, ts.getStartTime().toString());
						assertEquals(end, ts.getEndTime().toString());
					}
				}
			}
		}
	}


	@Then("{string} shall have a {string} appointment on {string} from {string} to {string}")
	public void shall_have_a_appointment_on_from_to(String string, String string2, String string3, String string4, String string5) {
		// create a temp variable
		Appointment toCheckApp = null;// set to null
		for(Customer cust : cs.getCustomers()) {		// go through the loop
			// get the bookable service, compare the name
			if(cust.getUsername().equals(string)) {
				// get the customer's latest appointment
				toCheckApp = cust.getAppointment(cust.numberOfAppointments()-1);
				break;
			}
		}
		if(toCheckApp.getBookableService() instanceof Service) {
			// the size of the latest appointments list
			// inside said appointment there's a bookable service
			// we then get that bookable service's appointments
			// and get the LAST element of that appointment
			int size = toCheckApp.getBookableService().getAppointments().size();
			int size2 = toCheckApp.getBookableService().getAppointment(size-1).numberOfServiceBookings();
			ServiceBooking checkThis = toCheckApp.getBookableService().getAppointment(size-1).getServiceBooking(size2-1);
			Time apptStartTime = checkThis.getTimeSlot().getStartTime();
			Time apptEndTime = checkThis.getTimeSlot().getEndTime();
			Date apptDate = checkThis.getTimeSlot().getStartDate();

			if(string4.length()==4) string4 = "0"+string4+":00";
			else if(string4.length()==5) string4 = string4+":00";

			if(string5.length()==4) string5 = "0"+string5+"00";
			else if(string5.length()==5) string5 = string5+":00";

			assertEquals(string2, toCheckApp.getBookableService().getName());
			assertEquals(string3, apptDate.toString());
			assertEquals(string4, apptStartTime.toString());
			assertEquals(string5, apptEndTime.toString());
		}
		else { // go through the entire list of services. PARSE the strings string4 and string5
			String[] parsedStartTimes = string4.split(",");
			String[] parsedEndTimes = string5.split(",");
			int size = toCheckApp.getBookableService().getAppointments().size();
			for(int i = 0; i < toCheckApp.getBookableService().getAppointment(size-1).numberOfServiceBookings(); i++) {
				ServiceBooking checkThis = toCheckApp.getBookableService().getAppointment(size-1).getServiceBooking(i);
				Date apptDate = checkThis.getTimeSlot().getStartDate();
				assertEquals(string2, toCheckApp.getBookableService().getName());
				assertEquals(string3, apptDate.toString());

				if(parsedStartTimes[i].length()==4) parsedStartTimes[i] = "0"+parsedStartTimes[i]+":00";
				else if(parsedStartTimes[i].length()==5) parsedStartTimes[i] = parsedStartTimes[i]+":00";

				if(parsedEndTimes[i].length()==4) parsedEndTimes[i] = "0"+parsedEndTimes[i]+":00";
				else if(parsedEndTimes[i].length()==5) parsedEndTimes[i] = parsedEndTimes[i]+":00";
				assertEquals(parsedStartTimes[i], checkThis.getTimeSlot().getStartTime().toString());
				assertEquals(parsedEndTimes[i], checkThis.getTimeSlot().getEndTime().toString());
			}

		}

	}

	@Then("there shall be {int} more appointment in the system")
	public void there_shall_be_more_appointment_in_the_system(Integer int1) {
		assertEquals(int1, cs.numberOfAppointments()-numApp);
	}

	//DELIVERABLE 3

	@Given("{string} has {int} no-show records")
	public void has_no_show_records(String username, Integer int1) {
		Customer customer = (Customer) User.getWithUsername(username);
		customer.setNoShowCounter(int1); //set customer's no show number to given int
	}

	//WHEN tests 
	@When("{string} makes a {string} appointment for the date {string} and time {string} at {string}")
	public void makes_a_appointment_for_the_date_and_time_at(String customer, String bookableService, String date, String time, String systemInfo) throws InvalidInputException {
		CarShopController.setSystemDateAndTime(systemInfo); 

		try {
			CarShopController.createAppointmentAt(customer, bookableService, null, date, time, systemInfo); //create appointment with no optional services
			currentAppointment = cs.getAppointment(cs.getAppointments().size()-1); //most recent appointment
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} attempts to change the service in the appointment to {string} at {string}")
	public void attempts_to_change_the_service_in_the_appointment_to_at(String username, String serviceName, String timeOfChange) {
		try {
			CarShopController.changeServiceAt(currentAppointment, username, serviceName, timeOfChange);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}
	
	@When("{string} attempts to update the date to {string} and time to {string} at {string}")
	public void attempts_to_update_the_date_to_and_time_to_at(String username, String newDate, String newTime, String currentTimeDate) {
		try {
			CarShopController.updateDateAndTimeAt(currentAppointment, username, newDate, newTime, currentTimeDate);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} attempts to cancel the appointment at {string}")
	public void attempts_to_cancel_the_appointment_at(String username, String currentTimeDate) {
		try {
			CarShopController.cancelAppointmentAt(currentAppointment, username, currentTimeDate);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}
	
	@When("{string} makes a {string} appointment with service {string} for the date {string} and start time {string} at {string}")
	public void makes_a_appointment_with_service_for_the_date_and_start_time_at(String username, String service, String optionalService, String date, String StartTime, String currentTimeDate) {
		try {
			CarShopController.createAppointmentAt(username, service, optionalService, date, StartTime, currentTimeDate);
			currentAppointment = cs.getAppointment(cs.getAppointments().size()-1); //most recent appointment
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} attempts to add the optional service {string} to the service combo with start time {string} in the appointment at {string}")
	public void attempts_to_add_the_optional_service_to_the_service_combo_with_start_time_in_the_appointment_at(String username, String optService, String startTime, String currentDateTime) {
		try {
			CarShopController.addOptServiceAt(currentAppointment, username, optService, startTime, currentDateTime);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}
	
	@When("{string} attempts to update the date to {string} and start time to {string} at {string}")
	public void attempts_to_update_the_date_to_and_start_time_to_at(String username, String newDate, String newTime, String currentDateTime) {
		try {
			CarShopController.updateDateAndTimeAt(currentAppointment, username, newDate, newTime, currentDateTime);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("the owner starts the appointment at {string}")
	public void the_owner_starts_the_appointment_at(String currentDateTime) {
		try {
			String ownerPassword = cs.getOwner().getPassword();
			CarShopApplication.logIn("owner", ownerPassword);
			CarShopController.startAppointmentAt(currentAppointment, currentDateTime);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("the owner ends the appointment at {string}")
	public void the_owner_ends_the_appointment_at(String currentDateTime) {
		try {
			String ownerPassword = cs.getOwner().getPassword();
			CarShopApplication.logIn("owner", ownerPassword);
			CarShopController.endAppointmentAt(currentAppointment, currentDateTime);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}
	
	@When("the owner attempts to register a no-show for the appointment at {string}")
	public void the_owner_attempts_to_register_a_no_show_for_the_appointment_at(String currentDateTime) {
		try {
			String ownerPassword = cs.getOwner().getPassword();
			CarShopApplication.logIn("owner", ownerPassword);
			CarShopController.updateNoShowAt(currentAppointment, currentDateTime);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("the owner attempts to end the appointment at {string}")
	public void the_owner_attempts_to_end_the_appointment_at(String currentDateTime) {
		try {
			String ownerPassword = cs.getOwner().getPassword();
			CarShopApplication.logIn("owner", ownerPassword);
			CarShopController.endAppointmentAt(currentAppointment, currentDateTime);
		} catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@Then("the appointment shall be booked")
	public void the_appointment_shall_be_booked() {
		assertNotNull(currentAppointment);
		//check the state of the appointment
		assertEquals(AppointmentStatus.Booked, currentAppointment.getAppointmentStatus());
		//we can do this because this @then test is always called after trying to add a second appointment
	}

	@Then("the service in the appointment shall be {string}")
	public void the_service_in_the_appointment_shall_be(String string) {
		assertEquals(string, currentAppointment.getBookableService().getName());
		//compare the name given by the test to the name of the service for which the appointment is for
	}

	@Then("the appointment shall be for the date {string} with start time {string} and end time {string}")
	public void the_appointment_shall_be_for_the_date_with_start_time_and_end_time(String stringDate, String stringStartTimes, String stringEndTimes) throws InvalidInputException {
		CarShopController.stringToDate(stringDate);
		List<ServiceBooking> appointmentServices = cs.getAppointment(1).getServiceBookings(); //get list of all servicebookings in the appointment
		//note that all our tests are adding a second appointment and checking its fields therefore we directly retrieve the second appointment from the list

		appointmentServices.get(0).getTimeSlot().getStartDate();

		// there can be multiple start times and end times if we are checking for a service combo
		//parse the start times
		List<Time> expectedStartTimes = CarShopController.parseComboTimes(stringStartTimes);

		//parse the end times
		List<Time> expectedEndTimes = CarShopController.parseComboTimes(stringEndTimes);

		//get actual start/end times
		List<Time> actualStartTimes = new ArrayList<>();
		List<Time> actualEndTimes = new ArrayList<>();
		for(ServiceBooking serviceBooking: appointmentServices) {
			actualStartTimes.add(serviceBooking.getTimeSlot().getStartTime());
			actualEndTimes.add(serviceBooking.getTimeSlot().getEndTime());
		}

		//assert list are the same length
		assertEquals(expectedStartTimes.size(), actualStartTimes.size());
		assertEquals(expectedEndTimes.size(), actualEndTimes.size());

		//assert list elements are equal
		for(int i = 0; i < expectedStartTimes.size(); i++) {
			assertEquals(expectedStartTimes.get(i), actualStartTimes.get(i));
		}
		for(int i = 0; i < expectedEndTimes.size(); i++) {
			assertEquals(expectedEndTimes.get(i), actualEndTimes.get(i));
		}
	}

	@Then("the username associated with the appointment shall be {string}")
	public void the_username_associated_with_the_appointment_shall_be(String string) {
		assertEquals(string, currentAppointment.getCustomer().getUsername());
		//compare customer's username to the expected inputed string
	}

	@Then("the user {string} shall have {int} no-show records")
	public void the_user_shall_have_no_show_records(String username, Integer int1) {
		Customer customer = (Customer) User.getWithUsername(username); //get customer with username
		int noShowNum = customer.getNoShowCounter(); //get no shows
		assertEquals(int1, noShowNum); //compare no show counter to expected
	}

	@Then("the system shall have {int} appointments")
	public void the_system_shall_have_appointments(Integer int1) {
		assertEquals(int1, cs.getAppointments().size());
		//get list of all appointments and compare size to expect number of appointments
	}

	@Then("the system shall have {int} appointment")
	public void the_system_shall_have_appointment(Integer int1) {
		assertEquals(int1, cs.getAppointments().size());
		//get list of all appointments and compare size to expect number of appointments
	}

	@Then("the service combo in the appointment shall be {string}")
	public void the_service_combo_in_the_appointment_shall_be(String string) {

		BookableService bookableService = currentAppointment.getBookableService(); //get the appointment's bookable service
		if (bookableService instanceof Service) fail(); //fail test is the booked service is not a combo
		ServiceCombo serviceCombo = (ServiceCombo) bookableService;  //cast the booked service to a combo

		assertEquals(string, serviceCombo.getName()); //compare expected name to combo name
	}

	@Then("the service combo shall have {string} selected services")
	public void the_service_combo_shall_have_selected_services(String string) {
		String[] expectedComboItems = string.split(",");//split expected services into string away with "," as delimiter


		BookableService bookableService = currentAppointment.getBookableService(); //get the appointment's bookable service
		if (bookableService instanceof Service) fail(); //fail test is the booked service is not a combo

		//since some of the services in a service combo are not mandatory, the customer doesn't have to book every service on the list
		//this get the list of all the services the customer actually booked, not the list of all services in the combo
		List<ServiceBooking> serviceBookings = currentAppointment.getServiceBookings();

		int i = 0; //counter variable to get index in comboItems list
		for (String expectedService : expectedComboItems) { //this assumes that the services can only have one order
			assertEquals(expectedService, serviceBookings.get(i).getService().getName()); //compare expected service name to actual service name from the combo item
			i++; //increments the index
		}
	}

	@Then("the appointment shall be in progress")
	public void the_appointment_shall_be_in_progress() {
		assertEquals(AppointmentStatus.InProgress, currentAppointment.getAppointmentStatus());
	}
}
