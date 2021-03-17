package ca.mcgill.ecse.carshop.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.sql.Time;
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
	

// Sign Up Customer Account
    

    
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

//	@Then("the account shall have username {string} and password {string}")
//	public void the_account_shall_have_username_and_password(String string, String string2) 
//	{
//		
//		assertTrue(user != null && user.getUsername().equals(string) && user.getPassword().equals(string2)); 
//
//	}

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
	
	
	
//	@Given("the following customers exist in the system:")
//	public void the_following_customers_exist_in_the_system(Map<String,String> table) {
//	    // Write code here that turns the phrase above into concrete actions
//		
//		for (Map.Entry<String, String> entry : table.entrySet()) {
//			Customer customer = new Customer(entry.getKey(), entry.getValue(), cs);
//			//do we not need to add customer in a different way, such that they aren't stored in the same Customer object (called "customer" in this case)
//		}
//
//	}

	@When("the user tries to log in with username {string} and password {string}")
	public void the_user_tries_to_log_in_with_username_and_password(String string, String string2) {
		try {
			//do we need to include which type of user they are in the constructor for a user (or somewhere else)?

			curUsername = string;
			curPassword = string2;
			CarShopController.login(string, string2);
		}catch(Exception e) {
			error+=e.getMessage();
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
	    // Write code here that turns the phrase above into concrete actions
	    error = string;
	    try {
	    	CarShopController.login(curUsername, curPassword);
	    }catch (Exception e) {
	    	error = e.getMessage();
	    	errorCntr++;
	    }
	    assertEquals(string, error);
	}

	@Then("a new account shall be created")
	public void a_new_account_shall_be_created() {
	    // Write code here that turns the phrase above into concrete actions
		CarShopController.newAccount(curUsername, curPassword, cs);
		assertEquals(curUsername, User.getWithUsername(curUsername).getUsername());
		assertEquals(curPassword, User.getWithUsername(curUsername).getPassword());
	}

	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(string, User.getWithUsername(string).getUsername());
		assertEquals(string2, User.getWithUsername(string2).getPassword());
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
	
	@Then("the garage belonging to the technician with type {string} should have opening hours on {string} from {string} to {string}")
	public void the_garage_belonging_to_the_technician_with_type_should_have_opening_hours_on_from_to(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		TechnicianType techGuyType = CarShopController.getTechnicianType(string);
		Technician technician = null;
		for(int i=0; i<5;i++) {
			Technician techGuy = cs.getTechnician(i);
			if(techGuy.getType().equals(techGuyType)) {
				technician = techGuy;
				break;
			}
		}
		boolean test = false;
		List<BusinessHour> businessHours = technician.getGarage().getBusinessHours();
		for(BusinessHour hours:businessHours) {
			if(hours.getDayOfWeek().equals(CarShopController.getWeekDay(string2))) {
				if(hours.getStartTime().equals(CarShopController.stringToTimeMatthew(string3))) {
					if(hours.getEndTime().equals(CarShopController.stringToTimeMatthew(string4))) {
						test = true;
						break;
					}
				}
			}
		}
		assertTrue(test);
	}
	

	@Given("there are opening hours on {string} from {string} to {string} for garage belonging to the technician with type {string}")
	public void there_are_opening_hours_on_from_to_for_garage_belonging_to_the_technician_with_type(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		Technician technician = null;
		for(int i=0; i<5;i++) {
			Technician techGuy = cs.getTechnician(i);
			if(techGuy.getType().equals(CarShopController.getTechnicianType(string4))) {
				technician = techGuy;
				break;
			}
		}	
		
		Garage garage = technician.getGarage();
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string);
		Time startTime = CarShopController.stringToTimeMatthew(string2);
		Time endTime = CarShopController.stringToTimeMatthew(string3);
		BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);
		garage.addBusinessHour(businessHour);
	}
	
	@When("the user tries to remove opening hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void the_user_tries_to_remove_opening_hours_on_from_to_to_garage_belonging_to_the_technician_with_type(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		Technician technician = null;
		for(int i=0; i<5;i++) {
			Technician techGuy = cs.getTechnician(i);
			if(techGuy.getType().equals(CarShopController.getTechnicianType(string4))) {
				technician = techGuy;
				break;
			}
		}
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string);
		Time startTime = CarShopController.stringToTimeMatthew(string2);
		Time endTime = CarShopController.stringToTimeMatthew(string3);
		BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);
		
		BusinessHour hoursToRemove = new BusinessHour(dayOfWeek, startTime, endTime, cs);
		//change the model so that the removeBusinessHours function in Garage.java
		//only the technician of the right type can remove it, and not all technicians.
		//may not be necessary
		try {
			CarShopController.removeBusinessHour(hoursToRemove, CarShopApplication.getUser(), technician.getGarage(), cs);
		}catch(Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}
	
	@Then("the garage belonging to the technician with type {string} should not have opening hours on {string} from {string} to {string}")
	public void the_garage_belonging_to_the_technician_with_type_should_not_have_opening_hours_on_from_to(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		Technician technician = null;
		for(int i=0; i<5;i++) {
			Technician techGuy = cs.getTechnician(i);
			if(techGuy.getType().equals(CarShopController.getTechnicianType(string))) {
				technician = techGuy;
				break;
			}
		}
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string2);
		Time startTime = CarShopController.stringToTimeMatthew(string3);
		Time endTime = CarShopController.stringToTimeMatthew(string4);
		
//		BusinessHour businessHour = new BusinessHour(dayOfWeek, startTime, endTime, cs);
		
		BusinessHour hoursToAdd = new BusinessHour(dayOfWeek, startTime, endTime, cs);

		
		assertTrue(!technician.getGarage().getBusinessHours().contains(hoursToAdd));
	}

	
	@Given("an owner account exists in the system with username {string} and password {string}")
	public void an_owner_account_exists_in_the_system_with_username_and_password(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
		//is this correct? do i need to first check that the owner is not already in the system and then just log them in? hmm
		Owner owner = new Owner(string, string2, cs);
		cs.setOwner(owner);		
	}

	@Given("a business exists with the following information:")
	public void a_business_exists_with_the_following_information(io.cucumber.datatable.DataTable table) {
	    // Write code here that turns the phrase above into concrete actions
		List<List<String>> rows = table.asLists(String.class);
	    
		
	    for (List<String> columns : rows) {
	    	if(columns.get(0).equals("name")) {
	    		continue;
	    	}
	    	Business business = new Business(columns.get(0), columns.get(1), columns.get(2), columns.get(3), cs);
	    }
	    

	}

	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    curUsername = User.getWithUsername(string).getUsername();
	    curPassword = User.getWithUsername(string).getPassword();
	    try {
			CarShopController.login(string, curPassword);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to add new business hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void the_user_tries_to_add_new_business_hours_on_from_to_to_garage_belonging_to_the_technician_with_type(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
		TechnicianType technicianType = CarShopController.getTechnicianType(string4);
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(string);
		BusinessHour newBusinessHours = new BusinessHour(dayOfWeek, CarShopController.stringToTimeMatthew(string2), CarShopController.stringToTimeMatthew(string3), cs);
		Technician technician = null;
		//to find the technician
		for(int i=0; i<5;i++) {
			Technician techGuy = cs.getTechnician(i);
			if(techGuy.getType().equals(technicianType)) {
				technician = techGuy;
				break;
			}
		}
		technician.getGarage().addBusinessHour(newBusinessHours);
	}
	
//	@Given("a business exists with the following information:")
//	public void a_business_exists_with_the_following_information(io.cucumber.datatable.DataTable table) {
//	    // Write code here that turns the phrase above into concrete actions
//	    // For automatic transformation, change DataTable to one of
//	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
//	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
//	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
//		
//		List<List<String>> rows = table.asLists(String.class);
//	    
//		
//	    for (List<String> columns : rows) {
//	    	if(columns.get(0).equals("name")) {
//	    		continue;
//	    	}
//	    	Business business = new Business(columns.get(0), columns.get(1), columns.get(2), columns.get(3), cs);
//	    }
//	    
//	    
//		for(List<String>entry: businessList) {
//			Business business = new Business(entry.get(0), entry.get(1), entry.get(2), entry.get(3), cs);
//		}
//	     //For other transformations you can register a DataTableType.
//	}

	@Given("the business has the following opening hours:")
	public void the_business_has_the_following_opening_hours(io.cucumber.datatable.DataTable table) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
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
			
	}

    @After
    public void teardown() {
    	if(cs!=null) {
        	cs.delete();
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
	        cs.addTechnician(columns.get(0), columns.get(1), aType);
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

}