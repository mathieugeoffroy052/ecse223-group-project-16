package ca.mcgill.ecse.carshop.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.html.CSS;

import org.checkerframework.checker.units.qual.C;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
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
	
	@Before
    public void init() {
    }

    @After
    public void teardown() {
        cs.delete();
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