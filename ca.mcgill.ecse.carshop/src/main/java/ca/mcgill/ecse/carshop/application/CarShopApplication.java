/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ca.mcgill.ecse.carshop.application;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse.carshop.persistence.CarShopPersistence;
import ca.mcgill.ecse.carshop.view.CarShopPage;

public class CarShopApplication {
	private static CarShop carShop = null;	//all applications are associated with the same CarShop carShop
	private static User user = null;	//might need to remove static
	public static AccountType accountType = null;
	private static boolean isLoggedIn = false;
	private static Date systemDate = null;
	private static Time systemTime = null;

	public enum AccountType{EngineTechnician, TireTechnician, TransmissionTechnician, ElectronicsTechnician, FluidsTechnician, 
		Customer, Owner};

		public static CarShop getCarShop() {
			if(carShop == null) {
				//carShop = new CarShop();
				carShop = (CarShop) CarShopPersistence.load();	//load previously stored information
			}
			return carShop;
		}
		
		public static void setCarShop(CarShop cs) {
			carShop = cs;
		}

		public static void restart() {
			if(carShop != null) {
				carShop.delete();
			}
			carShop = null;
			user = null;
			accountType = null;
			isLoggedIn = false;
			systemDate = null;
			systemTime = null;
		}

		public static void logIn(String username, String password) {
			if(carShop.getOwner() != null && username.equals(carShop.getOwner().getUsername()) && password.equals(carShop.getOwner().getPassword())) {
				accountType = CarShopApplication.AccountType.Owner;
				user = carShop.getOwner();
				isLoggedIn = true;
				setUser(carShop.getOwner());
			}
			else if(username.contains("Technician")) {
				int i = carShop.getTechnicianWithString(username);
				String comparePassword = carShop.getTechnician(i).getPassword();
				if(password.equals(comparePassword)) {
					Technician.TechnicianType a = carShop.getTechnician(0).getTechnicianType(username);
					if(a.equals(Technician.TechnicianType.Engine)) {
						accountType = CarShopApplication.AccountType.EngineTechnician;
						user = carShop.getTechnician(i);
					}
					else if(a.equals(Technician.TechnicianType.Tire)) {
						accountType = CarShopApplication.AccountType.TireTechnician;
						user = carShop.getTechnician(i);
					}
					else if(a.equals(Technician.TechnicianType.Transmission)) {
						accountType = CarShopApplication.AccountType.TransmissionTechnician;
						user = carShop.getTechnician(i);
					}
					else if(a.equals(Technician.TechnicianType.Electronics)) {
						accountType = CarShopApplication.AccountType.ElectronicsTechnician;
						user = carShop.getTechnician(i);
					}
					else {
						accountType = CarShopApplication.AccountType.FluidsTechnician;
						user = carShop.getTechnician(i);
					}
					setUser(carShop.getTechnician(i));
					isLoggedIn = true;
				}
			}
			else {
				for(int i = 0; i < carShop.getCustomers().size(); i++) {
					if(carShop.getCustomer(i).getUsername().equals(username)) {
						if(carShop.getCustomer(i).getPassword().equals(password)) {
							accountType = CarShopApplication.AccountType.Customer;
							isLoggedIn = true;
							user = carShop.getCustomer(i);
							setUser(carShop.getCustomer(i));
						}
					}
				}
			}
		}

		public static void setUsername(String name) {
			user.setUsername(name);
		}

		public static String getCurrentUser() {
			if(user == null) {
				return null;
			}
			return user.getUsername();
		}

		public static void setSystemDate(Date date) {
			systemDate = date;
		}

		public static Date getSystemDate() {
			return systemDate;
		}

		public static Time getSystemTime() {
			return systemTime;
		}

		public static void setSystemTime(Time time) {
			systemTime = time;
		}

		public static User getUser() {
			return user;
		}

		public static void setUser(User newUser) {
			user = newUser;
		}

		public static void setAccountType(AccountType type) {
			accountType = type;
		}


		public static void logOut() {
			accountType = null;
			isLoggedIn = false;
			user = null;
		}

		public static void setLoggedIn(Boolean bool) {
			isLoggedIn = bool;
		}

		public static AccountType getAccountType() {
			return accountType;
		}

		public static boolean getLoggedIn() {
			return isLoggedIn;
		}
		
		public static void main(String[] args) {

			//start UI
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						carShop = setUp();
						
					} catch (Exception e) {}
					new CarShopPage().setVisible(true);
				}
			});
		}
		
		public static String getSystemDateTime() {
			return CarShopController.dateToString(systemDate) + "+" + CarShopController.timeToString(systemTime);
		}
		
		private static CarShop setUp() throws Exception {
			// TODO Auto-generated method stub
			// getting the carshop loads it
			CarShop cs = getCarShop();
			cs.delete();
			setCurrentDateAndTime("2021-02-01+09:00");	// sets in carshopapplication
			// sets the owner
//			Owner owner = new Owner("owner", "owner", cs);
//			Technician technician1 = new Technician("Tires-Technician", "Tires-Technician", TechnicianType.Tire, cs);
//			Technician technician2 = new Technician("Engine-Technician", "Engine-Technician", TechnicianType.Engine, cs);
//			Technician technician3 = new Technician("Transmission-Technician", "Transmission-Technician", TechnicianType.Transmissionm, cs);
//			Technician technician4 = new Technician("Electronics-Technician", "Electronics-Technician", TechnicianType.Electronics, cs);
//			Technician technician5 = new Technician("Fluids-Technician", "Fluids-Technician", TechnicianType.Fluids, cs);

			CarShopController.newAccount("owner",  "owner", cs);
			CarShopController.newAccount("Tire-Technician", "Tire-Technician", cs);
			CarShopController.newAccount("Engine-Technician", "Engine-Technician", cs);
			CarShopController.newAccount("Transmission-Technician", "Transmission-Technician", cs);
			CarShopController.newAccount("Electronics-Technician", "Electronics-Technician", cs);
			CarShopController.newAccount("Fluids-Technician", "Fluids-Technician", cs);
			
			//			cs.setOwner(owner); // unnecessary step
			// a business exists in the system
			Business bs = new Business("car-shop", "montreal", "5141234567", "xyz@mcgill.ca", cs);// create a new object
//			cs.setBusiness(bs); // unnecessary step
			// create a customer in the system
			cs.addCustomer("customer1", "12345678");
			
//			createTechniciansWithGarages(cs);
			setUpBusinessHours(cs);
			
			// adds the one holiday
			Date startDate = CarShopController.stringToDate("2021-04-18"); // dateFormat.parse(columns.get("startDate"));
			// uses method in the controller
			Date endDate = CarShopController.stringToDate("2021-12-18"); // dateFormat.parse(columns.get("endDate"));
			// converts from string to time with method in the controller
			Time startTime = CarShopController.stringToTime("10:00"); // (Time)
			// timeFormat.parse(columns.get("startTime"));
			Time endTime = CarShopController.stringToTime("23:59"); // (Time)
			// timeFormat.parse(columns.get("endtime"));
			bs.addHoliday(new TimeSlot(startDate, startTime, endDate, endTime, cs));
			
			
			addSpecificTimeSlot("vacation","2021-04-14","12:00","2021-04-16","13:00",cs);
			
			// the following services exist ...
			int toPut1 = cs.getTechnicianWithString("tire-change");
			cs.addBookableService(new Service("tire-change", cs, 10, // create a new object
					cs.getTechnician(toPut1).getGarage()));
			
			int toPut2 = cs.getTechnicianWithString("transmission-check");
			cs.addBookableService(new Service("transmission-check", cs, 75, // create a new object
					cs.getTechnician(toPut2).getGarage()));
			
			int toPut3 = cs.getTechnicianWithString("engine-check");
			cs.addBookableService(new Service("engine-check", cs, 20, // create a new object
					cs.getTechnician(toPut3).getGarage()));
			
			int toPut4 = cs.getTechnicianWithString("electronics-repair");
			cs.addBookableService(new Service("electronics-repair", cs, 10, // create a new object
					cs.getTechnician(toPut4).getGarage()));
			
			// the following service combo exists ...
			accountType = accountType.Owner;
			OwnerDefinesServiceCombo("owner","transmission-check-combo", "transmission-check", "tire-change,transmission-check,electronics-repair,engine-check","false,true,true,false",cs);
		
			accountType = accountType.Customer;
			String customer = "customer1";
			String serviceName = "transmission-check-combo";
			String optServices = "tire-change,electronics-repair,engine-check";
			String date = "2021-04-08";
			String timeSlots = "13:00-14:15,14:20-14:30,14:40-14:50,15:00-15:20";
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
		
			return cs;
		}

		// helper methods for set up
		private static void OwnerDefinesServiceCombo(String owner, String name, String mainservice, 
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
			try {
				CarShopPersistence.save(cs);
			}catch(RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
			
		}
		
		private static List<String> parseStringByServices(String services) throws InvalidInputException {
			List<String> list = Arrays.asList(services.split(","));
			CarShop cs = CarShopApplication.getCarShop();
			for(String s : list) {
				// if the name is not in the list
				if(cs.getBookableService(0).getWithName(s) == null) 
					throw new InvalidInputException("Service " + s + " does not exist");
			}
			return list;
		}
		
		private static boolean[] parseStringByMandatory(String mandatory) {
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
		
		private static void addSpecificTimeSlot(String timeSlot, String startDate,
				String startTime, String endDate, String endTime, CarShop cs) {
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
				} else {
					cs.getBusiness().addHoliday(new TimeSlot(sDate, sTime, eDate, eTime, cs));
				}
			} catch (Exception e) {
			}
		}
		
		private static void setUpBusinessHours(CarShop cs) throws InvalidInputException {
			// TODO Auto-generated method stub
			Business business = cs.getBusiness();

			DayOfWeek Monday = CarShopController.getWeekDay("Monday");
			DayOfWeek Tuesday = CarShopController.getWeekDay("Tuesday");
			DayOfWeek Wednesday = CarShopController.getWeekDay("Wednesday");
			DayOfWeek Thursday = CarShopController.getWeekDay("Thursday");
			DayOfWeek Friday = CarShopController.getWeekDay("Friday");
			DayOfWeek Saturday = CarShopController.getWeekDay("Saturday");
			DayOfWeek Sunday = CarShopController.getWeekDay("Sunday");


			// converts from string to time with method in the controller
			Time startTime = CarShopController.stringToTime("8:00");
			Time endTime = CarShopController.stringToTime("18:00");
			Time endTime2 = CarShopController.stringToTime("17:00");

			
			BusinessHour businessHourM = new BusinessHour(Monday, startTime, endTime, cs);// create a new object
			business.addBusinessHour(businessHourM);
			for(Garage g : cs.getGarages()) {
				g.addBusinessHour(new BusinessHour(Monday, startTime, endTime2, cs));
			}
			
			BusinessHour businessHourT = new BusinessHour(Tuesday, startTime, endTime, cs);// create a new object
			business.addBusinessHour(businessHourT);
			for(Garage g : cs.getGarages()) {
				g.addBusinessHour(new BusinessHour(Tuesday, startTime, endTime2, cs));
			}
			
			BusinessHour businessHourW = new BusinessHour(Wednesday, startTime, endTime, cs);// create a new object
			business.addBusinessHour(businessHourW);
			for(Garage g : cs.getGarages()) {
				g.addBusinessHour(new BusinessHour(Wednesday, startTime, endTime2, cs));
			}
			
			BusinessHour businessHourTh = new BusinessHour(Thursday, startTime, endTime, cs);// create a new object
			business.addBusinessHour(businessHourTh);
			for(Garage g : cs.getGarages()) {
				g.addBusinessHour(new BusinessHour(Thursday, startTime, endTime2, cs));
			}
			
			BusinessHour businessHourF = new BusinessHour(Friday, startTime, endTime, cs);// create a new object
			business.addBusinessHour(businessHourF);
			for(Garage g : cs.getGarages()) {
				g.addBusinessHour(new BusinessHour(Friday, startTime, endTime2, cs));
			}
			
			BusinessHour businessHourSat = new BusinessHour(Saturday, startTime, endTime2, cs);// create a new object
			business.addBusinessHour(businessHourSat);
			for(Garage g : cs.getGarages()) {
				g.addBusinessHour(new BusinessHour(Saturday, startTime, endTime2, cs));
			}
			
			BusinessHour businessHourSun = new BusinessHour(Sunday, startTime, endTime2, cs);// create a new object
			business.addBusinessHour(businessHourSun);
			for(Garage g : cs.getGarages()) {
				g.addBusinessHour(new BusinessHour(Sunday, startTime, endTime2, cs));
			}


		}

		private static void createTechniciansWithGarages(CarShop cs) {
			// TODO Auto-generated method stub
			cs.addTechnician(new Technician("Tire-Technician", "pass1", Technician.TechnicianType.Tire, cs));
			cs.addTechnician(new Technician("Engine-Technician", "pass2", Technician.TechnicianType.Engine, cs));
			cs.addTechnician(new Technician("Transmission-Technician", "pass3", Technician.TechnicianType.Transmission, cs));
			cs.addTechnician(new Technician("Electronics-Technician", "pass4", Technician.TechnicianType.Electronics, cs));
			cs.addTechnician(new Technician("Fluids-Technician", "pass5", Technician.TechnicianType.Fluids, cs));

			for(Technician t : cs.getTechnicians()) {
				t.setGarage(new Garage(cs, t));// create a new object
			}
		}

		private static void setCurrentDateAndTime(String date) throws ParseException {
			String datePattern = "yyyy-MM-dd";
			String timePattern = "HH:mm";
			String[] splitString = date.split("\\+");// uses a formatter from java.sql.Date/Time
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);// create a new object
			SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);// create a new object
			Date newDate = null;
			Time newTime = null;

			// uses a formatter from java.sql.Date/Time
			newDate = new java.sql.Date(dateFormatter.parse(splitString[0]).getTime());// create a new object
			newTime = new java.sql.Time(timeFormatter.parse(splitString[1]).getTime());// create a new object

			CarShopApplication.setSystemDate(newDate);
			CarShopApplication.setSystemTime(newTime);
		}

		// end helper methods for set up

}

