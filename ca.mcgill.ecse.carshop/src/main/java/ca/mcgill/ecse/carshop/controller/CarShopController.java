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
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.TimeSlot;


public class CarShopController {
	
	public CarShopController() {
	}
	
	
	// Mathieu
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
	
//	public static void CreateAppointment(String customer, String service, String startTime, String endTime, String startDate) throws 
//	InvalidInputException {
//			
//		
//		BookableService serv = null;
//		List<BookableService> bookableService = cs.getBookableServices(); //assuming getServices exist
//		if (enTime == null) {
//			//enTime = 
//		}
//		if (services.length == 1) {
//			 //assuming getServices exist
//			for (BookableService bs: bookableService) {
//				if (bs.getName().equals(services)) {
//					serv = bs; 
//				}
//			}
//		}
//		else if (services.length > 1) {
//			int i;
//			for (i=0;i<4;i++) {
//				for (BookableService bs: bookableService) {
//					if (bs.getName().equals(services[i])) {
//						serv = bs; 
//					}
//				}
//		}
//		
//		Customer ct3 = null;
//		List<Customer> ct1 = cs.getCustomers();
//		for (Customer ct2: ct1) {
//			if (ct2.getUsername().equals(customer)) {
//				ct3 = ct2;
//			}
//		}
//		
//		try {
//			cs.addAppointment(new Appointment(ct3, serv, cs));
//			cs.addTimeSlot(new TimeSlot(date,stTime,date,enTime,cs));
//		} catch (RuntimeException e) {
//			throw new InvalidInputException(e.getMessage());
//		}
//		}
//	}

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