package ca.mcgill.ecse.carshop.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Appointment;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.User;


public class CarShopController {
	
	public CarShopController() {
	}
	
	// TODO Kalvin
	
	public static User signUpUser(String username, String password, CarShopApplication.AccountType userType) throws InvalidInputException
	{
		
		User user = null;
		if (User.hasWithUsername(username))
		{
			throw new InvalidInputException("The username already exists");
		}
		
		else if (username.equals(""))
		{
			throw new InvalidInputException("The user name cannot be empty");
		}
		
		else if (password.equals(""))
		{
			throw new InvalidInputException("The password cannot be empty");
		}
		
		else if (CarShopApplication.getLoggedIn())
		{
			if (CarShopApplication.getAccountType().equals(CarShopApplication.AccountType.Owner))
			{
				throw new InvalidInputException("You must log out of the owner account before creating a customer account");
			}
			else 
			{
				throw new InvalidInputException("You must log out of the technician account before creating a customer account");
			}
			
		}
		
		CarShop carShop = CarShopApplication.getCarShop();
		
		if (userType.equals(CarShopApplication.AccountType.Customer))
		{
			user = CarShopApplication.getCarShop().addCustomer(username, password);
		}
		
		else if (userType.equals(CarShopApplication.AccountType.Owner))
		{
			// Constraint: the username of the owner account is always owner
			if (CarShopApplication.getCarShop().getOwner() != null)
			{
				throw new InvalidInputException("The password cannot be empty");
			}
			Owner owner = new Owner("owner", password, carShop);
			CarShopApplication.getCarShop().setOwner(owner);
			user = owner;
			
		}
		
		else // Technician 
		{
			
			// Constraint: username of the technician
			TechnicianType technicianType = TechnicianType.Engine;
			switch (userType) 
			{
			case EngineTechnician:
				technicianType = TechnicianType.Engine;
				break;
			case TireTechnician:
				technicianType = TechnicianType.Tire;
				break;
			case TransmissionTechnician:
				technicianType = TechnicianType.Transmission;
				break;
			case ElectronicsTechnician:
				technicianType = TechnicianType.Electronics;
				break;
			case FluidsTechnician:
				technicianType = TechnicianType.Fluids;
				break;
			default:
				break;
			}
			String technicianUsername = technicianType.toString() + "-Technician";
			user = CarShopApplication.getCarShop().addTechnician(technicianUsername, password, technicianType);
		}	
		
		return user;
		
	}
	
	
	public static void updateUser(String username, String password) throws InvalidInputException
	{
		
		if (! CarShopApplication.getLoggedIn())
		{
			throw new InvalidInputException("Must be logged in to update");
		}
		
		User loggedInUser = User.getWithUsername(CarShopApplication.getUser().getUsername()); 
		if (loggedInUser == null)
		{
			// Not the actual error
			throw new InvalidInputException("User not found");
		}
		
		if (loggedInUser instanceof Owner && !(username.equals("owner")))
		{
			throw new InvalidInputException("Changing username of owner is not allowed");
		}
		if (loggedInUser instanceof Technician && !(username.equals(loggedInUser.getUsername())))
		{
			throw new InvalidInputException("Changing username of technician is not allowed");
		}
		if (!loggedInUser.getUsername().equals(username) && User.hasWithUsername(username))
		{
			throw new InvalidInputException("Username not available");
		}
		
		else if (username.equals(""))
		{
			throw new InvalidInputException("The user name cannot be empty");

		}
		else if (password.equals(""))
		{
			throw new InvalidInputException("The password cannot be empty");
		}
		
		loggedInUser.setUsername(username);
		loggedInUser.setPassword(password);
		
		
		
	}
	
	// TODO Matthew
	
	public static void customerLogin(String username, String password) throws InvalidInputException{
//		if(CarShopApplication.getUser()!=null) {
//			throw new InvalidInputException("Cannot log in while already logged in");
//		}
		User user = User.getWithUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			CarShopApplication.setUser(user);
			CarShopApplication.setAccountType(CarShopApplication.AccountType.Customer);
			CarShopApplication.setLoggedIn(true);
		}else {
			throw new InvalidInputException("Username/password not found");
		}
	}
	
	public static void technicianLogin(String username, String password) throws InvalidInputException {
//		if(CarShopApplication.getUser()!=null) {
//			throw new InvalidInputException("Cannot log in while already logged in");
//		}
//		System.out.println("");
		User user = User.getWithUsername(username);
//		System.out.println("THIS IS THE USERNAME==================== "+ user.getUsername());
		if(user != null && user.getPassword().equals(password)) {
			CarShopApplication.setUser(user);
			CarShopApplication.setAccountType(CarShopApplication.AccountType.Customer);
			CarShopApplication.setLoggedIn(true);
		}else {
			throw new InvalidInputException("Username/password not found");
		}
	}
	
	public static void ownerLogin(String username, String password) throws InvalidInputException {
//		if(CarShopApplication.getUser()!=null) {
//			throw new InvalidInputException("Cannot log in while already logged in");
//		}
		if(CarShopApplication.getCarShop().getOwner() == null) {
			Owner newOwner = new Owner(username, password, CarShopApplication.getCarShop());
			CarShopApplication.getCarShop().setOwner(newOwner);
			CarShopApplication.setUser(newOwner);
		}
		else if(User.getWithUsername(username) != null && 
				User.getWithUsername(username).getPassword().equals(password)) {
			CarShopApplication.setUser(User.getWithUsername(username));
			CarShopApplication.setAccountType(CarShopApplication.AccountType.Owner);
			CarShopApplication.setLoggedIn(true);
		}else {
			throw new InvalidInputException("Username/password not found");
		}
	}
	
	public static void login(String username, String password) throws InvalidInputException {
		if(username.equals("owner")) {
			ownerLogin(username, password);
		}else if(username.contains("Technician")) {
			technicianLogin(username, password);
		}else {
			customerLogin(username, password);
		}
	}
	
	
	public static TechnicianType getTechnicianType(String str) {
		str = str.toLowerCase();
		if(str.contains("tire")) {
			return Technician.TechnicianType.Tire;
		}
		else if(str.contains("engine")) {
			return Technician.TechnicianType.Engine;
		}
		else if(str.contains("transmission")) {
			return Technician.TechnicianType.Transmission;
		}
		else if(str.contains("electronics")) {
			return Technician.TechnicianType.Electronics;
		}
		else if(str.contains("fluids")) {
			return Technician.TechnicianType.Fluids;
		}
		else return null;
	}
	
	public static int getTechnician(String str, CarShop cs) {
		int iend = str.indexOf("-");
		String subString = null;
		if (iend != -1) 
		{
		    subString = str.substring(0, iend); //this will give abc
		}
		subString = subString.toLowerCase();
		for(int i = 0; i < cs.numberOfTechnicians(); i++) {
			String userName = cs.getTechnician(i).getUsername();
			userName = userName.toLowerCase();
			if(userName.contains(subString)) return i;
		}
		return -1;
	}
	
	public static void newAccount(String username, String password, CarShop cs) {
		Owner owner = null;
		Technician technician = null;
		if(username.equals("owner") && password.equals("owner")) {
			if(CarShopApplication.getCarShop().getOwner() == null) owner = new Owner(username, password, cs);
		}
		else if(username.equals("Tire-Technician") && password.equals("Tire-Technician")) {
			technician = new Technician(username, password, TechnicianType.Tire, cs);
		}
		else if(username.equals("Engine-Technician") && password.equals("Engine-Technician")) {
			technician = new Technician(username, password, TechnicianType.Engine, cs);
		}
		else if(username.equals("Transmission-Technician") && password.equals("Transmission-Technician")) {
			technician = new Technician(username, password, TechnicianType.Transmission, cs);
		}
		else if(username.equals("Electronics-Technician") && password.equals("Electronics-Technician")) {
			technician = new Technician(username, password, TechnicianType.Electronics, cs);
		}
		else if(username.equals("Fluids-Technician") && password.equals("Fluids-Technician")) {
			technician = new Technician(username, password, TechnicianType.Fluids, cs);
		}
	}
	
	public static void setGarage(Technician t, CarShop cs) {
		t.setGarage(new Garage(cs, t));
	}
	
	
	public static DayOfWeek getWeekDay(String day) {
		DayOfWeek dayOfWeek = null;
		if(day.equals("Monday")) {
			dayOfWeek = DayOfWeek.Monday;
		}
		if(day.equals("Tuesday")) {
			dayOfWeek = DayOfWeek.Tuesday;
		}
		if(day.equals("Wednesday")) {
			dayOfWeek = DayOfWeek.Wednesday;
		}
		if(day.equals("Thursday")) {
			dayOfWeek = DayOfWeek.Thursday;
		}
		if(day.equals("Friday")) {
			dayOfWeek = DayOfWeek.Friday;
		}
		return dayOfWeek;
	}
	
	public static Time stringToTimeMatthew(String time) {
		String[] timeArr1 = time.split(":");
		Time finalTime = new Time (Integer.parseInt(timeArr1[0]), Integer.parseInt(timeArr1[1]), 0);
		return finalTime;
	}
	
	public static void removeBusinessHour(BusinessHour removedHours, User user, Garage garage, CarShop cs) throws InvalidInputException {
		String username = user.getUsername();
		TechnicianType technicianType = getTechnicianType(username);
		if(technicianType!=null) {
//			Technician technician = cs.getTechnician(getTechnician(username, cs));
			if(technicianType.equals(garage.getTechnician().getType())) {
				garage.removeBusinessHour(removedHours);
			}else {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
		}else {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
	}	
	
	//go through and add business hours!
	public static void addBusinessHour(BusinessHour addedHours, User user, Garage garage, CarShop cs) throws InvalidInputException{
		String username = user.getUsername();
		TechnicianType technicianType = getTechnicianType(username);
		if(technicianType!=null) {
//			Technician technician = cs.getTechnician(getTechnician(username, cs));
			if(technicianType.equals(garage.getTechnician().getType())) {
				garage.addBusinessHour(addedHours);
		}else {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		}else {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
	}
	
	// TODO Mathieu
	// define service
	public static void ownerDefinesService(String user, String name, String duration, String garage, CarShop cs) throws InvalidInputException {

		try {
			if (CarShopApplication.accountType.equals(CarShopApplication.AccountType.Owner) //check to make sure owner is accessing this method
					&& user.equals(CarShopApplication.getCarShop().getOwner().getUsername())) {

				List<BookableService> bookableServiceList = cs.getBookableServices();
				
				int intDuration = Integer.parseInt(duration);
				
				if (intDuration <= 0) { //error if duration of service is < 0 
					throw new InvalidInputException("Duration must be positive");
				}

				int techIndex = cs.getTechnicianWithString(garage +"-"); //find current garage from string
				Technician currentTech = cs.getTechnician(techIndex);
				Garage currentGar = currentTech.getGarage();

				for (BookableService bookableService : bookableServiceList) { //go through list to check if adding same service to throw error
					if (bookableService instanceof Service) {
						Service service = (Service) (bookableService);
						if (service.getName().equals(name)) {
							throw new InvalidInputException("Service " + name + " already exists");
						}
					}
				}

				BookableService newService = new Service(name, cs, intDuration, currentGar);
				cs.addBookableService(newService); //add service

			} else throw new InvalidInputException("You are not authorized to perform this operation");

		} catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}
	}



	// Mathieu
	// Update Service
	@SuppressWarnings("static-access")
	public static void updateService(String user, String service, String name, String duration, String garage, CarShop cs) throws InvalidInputException {

		try {
			if (CarShopApplication.accountType.equals(CarShopApplication.AccountType.Owner) //check to make sure owner is accessing this method
					&& user.equals(CarShopApplication.getCarShop().getOwner().getUsername())) {

				int newIntDuration = Integer.parseInt(duration);
				
				if (newIntDuration <= 0) { //error if duration of service is < 0 
					throw new InvalidInputException("Duration must be positive");
				}			
				
				List<BookableService> bookableServiceList = cs.getBookableServices();

				int techIndex = cs.getTechnicianWithString(garage + "-"); //find current garage from string
				Technician currentTech = cs.getTechnician(techIndex);
				Garage currentGar = currentTech.getGarage();

				boolean changedService = false;

				if (!service.equals(name)) { //if want to change name, look through list for same name before
					for (BookableService bookableService : bookableServiceList) {
						if (bookableService instanceof Service && bookableService.getName().equals(name)) {
							throw new InvalidInputException("Service " + name + " already exists");
						}
					}
				}

				for (BookableService bookableService : bookableServiceList) {	//find service and change parameters
					if (bookableService instanceof Service) {
						if (bookableService.hasWithName(service)) {
							((Service) bookableService).setName(name);
							((Service) bookableService).setDuration(newIntDuration);
							((Service) bookableService).setGarage(currentGar);
							changedService = true;
						} 
					} 
				}

				if (!changedService) throw new InvalidInputException("Cannot find " + service + " in Bookeable Services List");

			} else throw new InvalidInputException("You are not authorized to perform this operation");

		} catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	

	private static Time stringToTime(String string) throws InvalidInputException {
        String pattern = "hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Time(formatter.parse(string).getTime());
        } catch (Exception e) {
            throw new InvalidInputException("parsing error");
        }
    }

    // done, but might not work, converts a string to a sql.Date
    private static Date stringtoDate(String string) throws InvalidInputException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Date(formatter.parse(string).getTime());
        } catch (Exception e) {
            throw new InvalidInputException("parsing error");
        }
    }

    // done converts a sql.Date to a string
    private static String dateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    // done converts a sql.Time to a string
    private static String timeToString(Time time) {
        String pattern = "hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(time);
    }

	public static void OwnerDefinesServiceCombo(String owner, String name, String mainservice, 
			String services, String mandatory, CarShop cs) throws InvalidInputException {
		try {
			if(cs.getBookableService(0).hasWithName(name)==true)
				throw new InvalidInputException("Service combo " + name + " already exists");
			/*
			 *     When "owner" initiates the definition of a service combo "<name>" with main service
			 *      "<mainService>", services "<services>" and mandatory setting "<mandatory>
			 */
			// Owner is used
			if(CarShopApplication.accountType.equals(CarShopApplication.AccountType.Owner)
					&& owner.equals(cs.getOwner().getUsername())) {	// if the owner is logged in	
		//-------------------------------------------------------------------------------------------------------//
				// all of this is to find if the mainservice is mandatory
				// gets the lists
				List<String> servicesList = parseStringByServices(services);
				boolean[] mandatoryList = parseStringByMandatory(mandatory);
				if(mandatoryList.length<2) throw new InvalidInputException("A service Combo must contain at least 2 services");
		//-------------------------------------------------------------------------------------------------------//
				// get the car shop
				// create a new servicecombo object
				ServiceCombo newServiceCombo = new ServiceCombo(name, cs);

		//-------------------------------------------------------------------------------------------------------//
				// temporary service object to add to the servicecombo object
				for(int i = 0; i < servicesList.size(); i++) {
					boolean found = false;
					for(BookableService s1 : cs.getBookableServices()) {
						if(s1.getName().equals(servicesList.get(i))) {
							found = true;
							ComboItem ci1 = new ComboItem(mandatoryList[i], (Service) s1, newServiceCombo);
							newServiceCombo.addService(ci1);
							if(ci1.getService().getName().contains(mainservice)) newServiceCombo.setMainService(ci1);
							break;
						}
					}
					if(found == false) throw new InvalidInputException("Service " + servicesList.get(i) + " does not exist");
				}
				if(newServiceCombo.getMainService()==null) {
					newServiceCombo.delete();
					throw new InvalidInputException("Main service must be included in the services");
				}
				if(newServiceCombo.getMainService().getMandatory()!=true) {
					newServiceCombo.delete();
					throw new InvalidInputException("Main service must be mandatory");
				}
				if(!newServiceCombo.isNumberOfServicesValid()) {
					newServiceCombo.delete();
					throw new InvalidInputException("A service Combo must contain at least 2 services");
				}
			}
			else throw new InvalidInputException("You are not authorized to perform this operation");
		} catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}

	public static List<String> parseStringByServices(String services) throws InvalidInputException {
		List<String> list = Arrays.asList(services.split(","));
		CarShop cs = CarShopApplication.getCarShop();
		for(String s : list) {
			// if the name is not in the list
			if(cs.getBookableService(0).getWithName(s) == null) 
				throw new InvalidInputException("Service " + s + " does not exist");
		}
		return list;
	}
	
	public static boolean[] parseStringByMandatory(String mandatory) {
		List<String> list = Arrays.asList(mandatory.split(","));
		boolean[] toReturn = new boolean[list.size()];
		int i = 0;
		for(String s : list) {
			if(s.equals("true")) {
				toReturn[i]=true;
				i++;
			}
			else {
				toReturn[i]=false;
				i++;
			}
		}
		return toReturn;
	}
	


	@SuppressWarnings("static-access")
	public static void updateServiceCombo(String username, String serviceComboName, 
			String newServiceComboName, String mainService, String services,
			String mandatory, CarShop cs) throws InvalidInputException {
		try {

		// Owner is used
		if(CarShopApplication.accountType.equals(CarShopApplication.AccountType.Owner)
				&& username.equals(CarShopApplication.getCarShop().getOwner().getUsername())) {	// if the owner is logged in	
	//-------------------------------------------------------------------------------------------------------//
			// all of this is to find if the mainservice is mandatory
			//if(CarShopApplication.getCarShop().getBookableService(0).hasWithName(newServiceComboName)==true) throw new InvalidInputException("Service " + serviceComboName + " does not exist");
			//if(CarShopApplication.getCarShop().getBookableService(0).hasWithName(newServiceComboName)==true) throw new InvalidInputException("Service combo " + newServiceComboName + " already exists");
			//if(CarShopApplication.getCarShop().getBookableService(0).hasWithName(serviceComboName)==true) throw new InvalidInputException("Service " + newServiceComboName + " already exists");
			if(!serviceComboName.equals(newServiceComboName) && CarShopApplication.getCarShop().getBookableService(0).hasWithName(newServiceComboName)) throw new InvalidInputException("Service combo " + newServiceComboName + " already exists");
			// gets the lists
			List<String> servicesList = parseStringByServices(services);
			boolean[] mandatoryList = parseStringByMandatory(mandatory);
			if(mandatoryList.length<2) throw new InvalidInputException("A service Combo must contain at least 2 services");
	//-------------------------------------------------------------------------------------------------------//
			// get the car shop
			// create a new servicecombo object
			ServiceCombo newServiceCombo = null;
			boolean ifSame = false;
			if(newServiceComboName.equals(serviceComboName)) {	// if the same name exists
				ifSame = true;
				String newName = newServiceComboName.concat("1");
				newServiceCombo = new ServiceCombo(newName, cs);
			} else newServiceCombo = new ServiceCombo(newServiceComboName, cs);
			
			BookableService toUpdate = cs.getBookableService(cs.getBookableServices().size()-1).getWithName(serviceComboName);
	//-------------------------------------------------------------------------------------------------------//
			// temporary service object to add to the servicecombo object
			for(int i = 0; i < servicesList.size(); i++) {
				boolean found = false;
				for(BookableService s1 : cs.getBookableServices()) {
					if(s1.getName().equals(servicesList.get(i))) {
						found = true;
						ComboItem ci1 = new ComboItem(mandatoryList[i], (Service) s1, newServiceCombo);
						newServiceCombo.addService(ci1);
						if(ci1.getService().getName().contains(mainService)) newServiceCombo.setMainService(ci1);
						break;
					}
				}
				if(found == false) throw new InvalidInputException("Service " + servicesList.get(i) + " does not exist");
			}
			if(newServiceCombo.getMainService()==null) {
				newServiceCombo.delete();
				throw new InvalidInputException("Main service must be included in the services");
			}
			if(newServiceCombo.getMainService().getMandatory()!=true) {
				newServiceCombo.delete();
				throw new InvalidInputException("Main service must be mandatory");
			}
			if(!newServiceCombo.isNumberOfServicesValid()) {
				newServiceCombo.delete();
				throw new InvalidInputException("A service Combo must contain at least 2 services");
			}
			if(ifSame == true) {
				toUpdate.delete();
				newServiceCombo.setName(newServiceComboName);
				cs.addBookableService(newServiceCombo);
			}
			else {
				toUpdate.delete();
				cs.addBookableService(newServiceCombo);
			}
			

		}
		else throw new InvalidInputException("You are not authorized to perform this operation");
	} catch (Exception e) {
		throw new InvalidInputException(e.getMessage());
	}

	}
	
}