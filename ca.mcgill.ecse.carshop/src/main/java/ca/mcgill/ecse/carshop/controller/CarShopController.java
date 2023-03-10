package ca.mcgill.ecse.carshop.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.application.CarShopApplication.AccountType;
import ca.mcgill.ecse.carshop.model.Appointment;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceBooking;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse.carshop.persistence.CarShopPersistence;


public class CarShopController {
	
	public CarShopController() {
	}
	
	public static Time getSystemTime() {
		return CarShopApplication.getSystemTime();
	}
	
	public static User getUser() {
		return CarShopApplication.getUser();
	}
	
	public static void logOut() {
		CarShopApplication.logOut();
	}
	
	public static CarShop getCarShop() {
		return CarShopApplication.getCarShop();
	}
	
	public static String getCurrentUser() {
		return CarShopApplication.getCurrentUser();
	}
	
	public static String getSystemDateTime() {
		return CarShopApplication.getSystemDateTime();
	}
	
	@SuppressWarnings("static-access")

	public static List<TOComboItem> getOptServicesWithName(String apptName) throws Exception {

		List<ComboItem> list1 = CarShopApplication.getCarShop().getBookableService(0).getWithName(apptName).getMainService().getServiceCombo().getServices();
		List<TOComboItem> toReturn = new ArrayList<>();
		String mainService = CarShopApplication.getCarShop().getBookableService(0).getWithName(apptName).getMainService().getService().toString();
		for(ComboItem ci : list1) {
			if(ci.getService().toString().equals(mainService) || ci.getMandatory()==true) continue;
			else toReturn.add(new TOComboItem(ci.getMandatory(),ci.getService().getName()));
		}
		return toReturn;

	}
	/**
	 * method to get a lit of bookable services
	 */
	public static List<TOBookableService> getCarShopBookableServices() {
		CarShop cs = CarShopApplication.getCarShop();
		List<TOBookableService> toReturn = new ArrayList<TOBookableService>();
		List<BookableService> bs = cs.getBookableServices();
		for(BookableService b : bs) {
			TOBookableService toAdd = new TOBookableService(b.getName());
			toReturn.add(toAdd);
		}
		return toReturn;
	}
	/**
	 * method to get a list of customer appointments
	 */
	public static List<TOAppointment> getCustomerAppointments(String username) {
		for(Customer c : CarShopApplication.getCarShop().getCustomers()) {
			if(c.getUsername().equals(username)) {
				int size = c.getAppointments().size();
				List<TOAppointment> temp = new ArrayList<>();	// create a list to store the customer's appointments
				for(int i = 0; i < size; i++) {	// iterate through this list of appointments
					Appointment curAppt = c.getAppointment(i);
					String nameOfAppt = curAppt.getBookableService().getName();
					Time startTime = curAppt.getServiceBooking(0).getTimeSlot().getStartTime();
					Date startDate = curAppt.getServiceBooking(0).getTimeSlot().getStartDate();
					List<TOServiceBooking> temp1 = new ArrayList<>();
					for(ServiceBooking sb : curAppt.getServiceBookings()) {
						TOGarage garage = new TOGarage(sb.getService().getGarage().getTechnician().getUsername());	// assuming name of garage is technician's username
						TOService serv = new TOService(sb.getService().getName(),sb.getService().getDuration(),garage);
						TOTimeSlot timeSlot = new TOTimeSlot(sb.getTimeSlot().getStartDate(),sb.getTimeSlot().getStartTime(),sb.getTimeSlot().getEndDate(),sb.getTimeSlot().getEndTime());
						
						// adds it to the array of service bookings
						temp1.add(new TOServiceBooking(serv, timeSlot));
						// finds the earliest time slot and adds it
						if(sb.getTimeSlot().getStartDate().before(startDate) || sb.getTimeSlot().getStartDate().equals(startDate)) {
							if(sb.getTimeSlot().getStartTime().before(startTime) || sb.getTimeSlot().getStartTime().equals(startTime)) {
								startTime = sb.getTimeSlot().getStartTime();
								startDate = sb.getTimeSlot().getStartDate();
							}
						}
					}
					// adds to list of TO appointments
					temp.add(new TOAppointment(username,nameOfAppt,startDate,startTime,curAppt.getAppointmentStatusFullName(),temp1));
				}
				return temp;
			}
		}
		return null;
	}
	/**
	 * method to remove individual business hours
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @param cs
	 */
	public static void removeBusinessHourIndividually(String day, String startTime, String endTime, String type, CarShop cs) throws InvalidInputException {
		if(CarShopApplication.getCurrentUser()!=null) {
			TechnicianType technicianType = getTechnicianType(CarShopApplication.getCurrentUser());
			if(technicianType!=null) {
				String toCheckString = technicianType.toString().toLowerCase();
				if(!toCheckString.equals(type.toLowerCase())) throw new InvalidInputException("You are not authorized to perform this operation");
				Technician technician = cs.getTechnician(getTechnician(CarShopApplication.getCurrentUser(), cs));
				Garage garage = technician.getGarage();
				if(technicianType.equals(garage.getTechnician().getType())) {
						
				}else {
					throw new InvalidInputException("You are not authorized to perform this operation");
				}
			}else {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
		}
		

		
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(day);
		int toCheck = 0;
		if(dayOfWeek.equals(BusinessHour.DayOfWeek.Monday)) toCheck = 0; 
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Tuesday)) toCheck = 1;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Wednesday)) toCheck = 2;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Thursday)) toCheck = 3;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Friday)) toCheck = 4;
		else throw new InvalidInputException("The opening hours are not within the opening hours of the business");
		// converts from string to time with method in the controller

		Technician technician = CarShopController.findTechnician(type, cs);
		
		Garage garage = technician.getGarage();
		garage.getBusinessHour(toCheck).delete();
		//persistence
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}
	/**
	 * method to add business hours individually
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @param cs
	 */
	public static void addBusinessHourIndividually(String day, String startTime, String endTime, String type, CarShop cs) throws InvalidInputException {
		if(CarShopApplication.getCurrentUser()!=null) {
			TechnicianType technicianType = getTechnicianType(CarShopApplication.getCurrentUser());
			if(technicianType!=null) {
				String toCheckString = technicianType.toString().toLowerCase();
				if(!toCheckString.equals(type.toLowerCase())) throw new InvalidInputException("You are not authorized to perform this operation");
				Technician technician = cs.getTechnician(getTechnician(CarShopApplication.getCurrentUser(), cs));
				Garage garage = technician.getGarage();
				if(technicianType.equals(garage.getTechnician().getType())) {
						
				}else {
					throw new InvalidInputException("You are not authorized to perform this operation");
				}
			}else {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
		}
		
		
		DayOfWeek dayOfWeek = CarShopController.getWeekDay(day);
		int toCheck = 0;
		if(dayOfWeek.equals(BusinessHour.DayOfWeek.Monday)) toCheck = 0; 
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Tuesday)) toCheck = 1;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Wednesday)) toCheck = 2;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Thursday)) toCheck = 3;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Friday)) toCheck = 4;
		else throw new InvalidInputException("The opening hours are not within the opening hours of the business");
		// converts from string to time with method in the controller
		Time ourStartTime = CarShopController.stringToTime(startTime);
		Time ourEndTime = CarShopController.stringToTime(endTime);
		Technician technician = CarShopController.findTechnician(type, cs);
		
		if(ourStartTime.after(ourEndTime)) throw new InvalidInputException("Start time must be before end time");
		
		// "The opening hours cannot overlap"
		Garage garage = technician.getGarage();
		if(garage.getBusinessHour(toCheck)!=null) {
			
			//DO NOT TOUCH! we may need this commented code later!
			// case 1	| @		@ | or @|		@|	// before, after
			// hardcoded :(
			if((garage.getBusinessHour(toCheck).getStartTime().equals(ourStartTime) &&
			(garage.getBusinessHour(toCheck).getEndTime().after(ourEndTime)))) throw new InvalidInputException("The opening hours cannot overlap");
			
//			// case 2	| @		  | @	// before, before
//			if((garage.getBusinessHour(toCheck).getStartTime().equals(ourStartTime) || 
//					garage.getBusinessHour(toCheck).getStartTime().before(ourStartTime))
//					&&
//				(garage.getBusinessHour(toCheck).getEndTime().equals(ourEndTime) || 
//					garage.getBusinessHour(toCheck).getEndTime().before(ourEndTime))) throw new InvalidInputException("The opening hours cannot overlap");
//			
//			// case 3	@ |		  @ |	// after, after
//			else if((garage.getBusinessHour(toCheck).getStartTime().equals(ourStartTime) || 
//					garage.getBusinessHour(toCheck).getStartTime().after(ourStartTime))
//					&&
//				(garage.getBusinessHour(toCheck).getEndTime().equals(ourEndTime) || 
//					garage.getBusinessHour(toCheck).getEndTime().after(ourEndTime))) throw new InvalidInputException("The opening hours cannot overlap");
//			
//			// case 4	@ |		  @ |	// after, before
//			else if((garage.getBusinessHour(toCheck).getStartTime().equals(ourStartTime) || 
//					garage.getBusinessHour(toCheck).getStartTime().after(ourStartTime))
//					&&
//				(garage.getBusinessHour(toCheck).getEndTime().equals(ourEndTime) || 
//					garage.getBusinessHour(toCheck).getEndTime().before(ourEndTime))) throw new InvalidInputException("The opening hours cannot overlap");
			
			// "The opening hours are not within the opening hours of the business"
			if(garage.getBusinessHour(toCheck).getStartTime().after(ourStartTime) ||
					garage.getBusinessHour(toCheck).getEndTime().before(ourEndTime)) throw new InvalidInputException("The opening hours are not within the opening hours of the business");
		}
		
		
		
		garage.getBusinessHour(toCheck).setStartTime(ourStartTime);
		//persistence
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		garage.getBusinessHour(toCheck).setEndTime(ourEndTime);
		//persistence
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	/**
	 * method to change business hour
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @param cs
	 */
	public static void changeBusinessHour(String day, String startTime, String endTime, String type, CarShop cs) throws InvalidInputException {

		DayOfWeek dayOfWeek = CarShopController.getWeekDay(day);
		int toCheck = 0;
		if(dayOfWeek.equals(BusinessHour.DayOfWeek.Monday)) toCheck = 0; 
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Tuesday)) toCheck = 1;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Wednesday)) toCheck = 2;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Thursday)) toCheck = 3;
		else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Friday)) toCheck = 4;
		else throw new InvalidInputException("The opening hours are not within the opening hours of the business");
		// converts from string to time with method in the controller
		Time ourStartTime = CarShopController.stringToTime(startTime);
		Time ourEndTime = CarShopController.stringToTime(endTime);
		Technician technician = CarShopController.findTechnician(type, cs);		
		// "The opening hours cannot overlap"
		Garage garage = technician.getGarage();
		
		garage.getBusinessHour(toCheck).setStartTime(ourStartTime);
		//persistence
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		garage.getBusinessHour(toCheck).setEndTime(ourEndTime);
		//persistence
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
//	public static void CreateAppointmentWithOptServices(String customer, String serviceComboName, String startTime, String startDate, String optServices) {
//		CreateAppointmentWithOptServices(customer, serviceComboName, startTime, startDate, CarShopApplication.getCarShop(), optServices, true);
//	}
	// creating appointment method
	/**
	 * method to create appointment
	 * @param customer
	 * @param serviceComboName
	 * @param startTime
	 * @param startDate
	 * @param cs
	 * @param optServices
	 * @param isTrue
	 */
	public static void CreateAppointmentWithOptServices(String customer, String serviceComboName, String startTime, String startDate, CarShop cs, String optServices, boolean isTrue) throws Exception {
		if(customer.contains("owner") || customer.contains("Technician")) throw new InvalidInputException("Only customers can make an appointment");
		String[] startTimes = startTime.split(","); // gets the start time of the first service
		Date date = stringToDate(startDate);	// turns the string "date" to an actual date
		try {
			// checking if the date is before the current date,
			for(int i = 0; i < startTimes.length; i++) {
				checkIfDateWorksWithCurrentTime(date, startTimes[i]);
			}
		} catch(Exception e) {
			throw new InvalidInputException("There are no available slots for " + serviceComboName + " on " + startDate + " at " + startTime);
		}
			//if it isn't a customer making the appointment
		User user = User.getWithUsername(customer);
		if(user == null) throw new InvalidInputException("Only customers can make an appointment");

		Customer cust = (Customer) user;	// explicit downcasting
		int duration, minutes, hours = 0;
		String[] optServicesToPutIn = null;
		if(optServices != null) {
			if(!optServices.equals("")) {
				optServicesToPutIn = optServices.split(",");
				// puts the optional services in. Requires the service combo name to do so.
				putOptServicesIn(optServicesToPutIn, serviceComboName, isTrue);
				//persistence
				try {
					CarShopPersistence.save(cs);
				}catch(RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
			}	
		}
		

		BookableService bookServ = getBookableService(serviceComboName);
		
		//create new object of type appointment - can be deleted later as well
		Appointment appointment = cs.addAppointment(cust, bookServ);
		if (bookServ instanceof Service) {
			//if service is service
			Time startTime1 = stringToTime(startTimes[0]);
			Service serv = (Service) bookServ;
			duration = serv.getDuration();
			minutes = duration%60;
			hours = duration/60;
				
			LocalTime localtime = startTime1.toLocalTime();
			localtime = localtime.plusMinutes(minutes);
			localtime = localtime.plusHours(hours);
			Time endTime = Time.valueOf(localtime);
			if(!checkInGarageBusinessHours(cs, startTime1, endTime, date, serv.getGarage())) {

				appointment.delete();
				//persistence
				try {
					CarShopPersistence.save(cs);
				}catch(RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
				throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
			} 
			if(!checkByGarageServiceBookings(cs, serv, startTime1, endTime, date)) {
				
				appointment.delete();
				//persistence
				try {
					CarShopPersistence.save(cs);
				}catch(RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
				throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
			}
			
			//check conflicts with holidays and vacations
			if (appointmentConflictsWithHolidays(date, startTime1, endTime)) {
				appointment.delete();
				//persistence
				try {
					CarShopPersistence.save(cs);
				}catch(RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
				throw new InvalidInputException("There are no available slots for " + serviceComboName + " on " + startDate + " at " + startTime);
			}
			if (appointmentConflictsWithVacations(date, startTime1, endTime)) {
				appointment.delete();
				//persistence
				try {
					CarShopPersistence.save(cs);
				}catch(RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
				throw new InvalidInputException("There are no available slots for " + serviceComboName + " on " + startDate + " at " + startTime);
			}

			TimeSlot timeSlot1 = cs.addTimeSlot(date, startTime1, date, endTime);
			//persistence
			try {
				CarShopPersistence.save(cs);
			}catch(RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
			serv.addServiceBooking(timeSlot1, appointment);
			//persistence
			try {
				CarShopPersistence.save(cs);
			}catch(RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
			
			 
		} else {
			//since some services are opt, we need to actually find the services we want to add
			List<Service> servicesToAddList = new ArrayList<>();
			
			//find main service
			Service mainService = ((ServiceCombo)bookServ).getMainService().getService();
			servicesToAddList.add(mainService);
			//persistence
			try {
				CarShopPersistence.save(cs);
			}catch(RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
			
			//add optional services
			for(String string : optServicesToPutIn) {
				BookableService serviceToAdd = BookableService.getWithName(string); 
				if (serviceToAdd instanceof Service) {
					servicesToAddList.add((Service) serviceToAdd);
					//persistence
					try {
						CarShopPersistence.save(cs);
					}catch(RuntimeException e) {
						throw new InvalidInputException(e.getMessage());
					}
				}
			}
			
			//changed the condition of the for loop
			for (int i = 0; i < servicesToAddList.size(); i++) { 
				Time startTime1 = stringToTime(startTimes[i]);
				
				//use helper method to find end time
				Time endTime = findEndTime(servicesToAddList.get(i), startTime1);
				
				//perform holiday and vacation checks
				if (appointmentConflictsWithHolidays(date, startTime1, endTime)) {
					appointment.delete();
					//persistence
					try {
						CarShopPersistence.save(cs);
					}catch(RuntimeException e) {
						throw new InvalidInputException(e.getMessage());
					}
					throw new InvalidInputException("There are no available slots for " + serviceComboName + " on " + startDate + " at " + startTime);
				}
				if (appointmentConflictsWithVacations(date, startTime1, endTime)) {
					appointment.delete();
					//persistence
					try {
						CarShopPersistence.save(cs);
					}catch(RuntimeException e) {
						throw new InvalidInputException(e.getMessage());
					}
					throw new InvalidInputException("There are no available slots for " + serviceComboName + " on " + startDate + " at " + startTime);
				}
				
				
				if(!checkInGarageBusinessHours(cs, startTime1, endTime, date, servicesToAddList.get(i).getGarage())) {

					appointment.delete();
					try {
						CarShopPersistence.save(cs);
					}catch(RuntimeException e) {
						throw new InvalidInputException(e.getMessage());
					}
					throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
				}

				if(!checkByGarageServiceBookings(cs, servicesToAddList.get(i), startTime1, endTime, date)) {
					
					appointment.delete();
					//persistence
					try {
						CarShopPersistence.save(cs);
					}catch(RuntimeException e) {
						throw new InvalidInputException(e.getMessage());
					}
					throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
				}
				if(startTimes.length > (i+1) && endTime.after(stringToTime(startTimes[i+1]))) {
					appointment.delete();
					//persistence
					try {
						CarShopPersistence.save(cs);
					}catch(RuntimeException e) {
						throw new InvalidInputException(e.getMessage());
					}
					throw new InvalidInputException("Time slots for two services are overlapping");
				}
				TimeSlot timeSlot1 = cs.addTimeSlot(date, startTime1, date, endTime);
				// create new service booking
				new ServiceBooking(servicesToAddList.get(i), timeSlot1, appointment);				
			}
		}
	}
	//checks the service booking per garage
	private static boolean checkByGarageServiceBookings(CarShop cs, Service serv, Time startTime1, Time endTime, Date date) {
		for(ServiceBooking sb : serv.getServiceBookings()) {
			if(sb.getTimeSlot().getStartDate().equals(date)) {	// if the date is the same
				if((sb.getTimeSlot().getStartTime().before(startTime1) || 
						sb.getTimeSlot().getStartTime().equals(startTime1)) &&
						(sb.getTimeSlot().getEndTime().before(endTime) ||
						sb.getTimeSlot().getEndTime().equals(endTime)) &&
						!sb.getTimeSlot().getEndTime().before(startTime1)) {
					return false;
				}	
			}
			// otherwise keep going
		}
		return true;
	}

	//never used, but may need to be in the future?
	@SuppressWarnings("unused")
	private static boolean checkHolidays(Date date) {
		List<TimeSlot> holiday = CarShopApplication.getCarShop().getBusiness().getHolidays();
		for (TimeSlot h: holiday) {
			if (h.getStartDate().compareTo(date)*date.compareTo(h.getEndDate()) >= 0) {
				return false;
			}
		}
		return true;
	}

	private static BookableService getBookableService(String serviceComboName) {
		BookableService bookServ = null;
		for (BookableService bs: CarShopApplication.getCarShop().getBookableServices()) {
			if (bs.getName().equals(serviceComboName)) {
				bookServ = bs;
				break;
			}
		}
		return bookServ;
	}

	@SuppressWarnings("static-access")
	private static boolean putOptServicesIn(String[] optServicesToPutIn, String serviceComboName, boolean isTrue) throws InvalidInputException {
		Boolean toReturn = false;
		CarShop cs = CarShopApplication.getCarShop();
		for(String opt : optServicesToPutIn) {
			Service toPut = null;
			for(BookableService sb : cs.getBookableServices()) {
				if(sb.getName().equals(opt)) {
					toPut = (Service) sb;
					break;
				}
			}
			// get the main service
			Boolean found = false;
			ComboItem mainServiceInServiceCombo = cs.getBookableService(0).getWithName(serviceComboName).getMainService();
			for(ComboItem checkIfSame : mainServiceInServiceCombo.getServiceCombo().getServices()) {
				// if the service is already in the service combo...
				if(checkIfSame.getService().equals((Service) toPut)) {
					if(!isTrue) {
						found = true;
						break; // don't add the optional service (it's already there)
					}
					else {
						checkIfSame.setMandatory(true);	// newly added code
						try {
							CarShopPersistence.save(cs);
						}catch(RuntimeException e) {
							throw new InvalidInputException(e.getMessage());
						}
						found = true;
						break; // don't add the optional service (it's already there)
					}

				}
			}
			if(!found) {mainServiceInServiceCombo.getServiceCombo().addService(true, toPut);
				toReturn = true;
			}
			// is automatically assigned a value of true
		}
		return toReturn;
	}
	//checks if the date works with the current time
	private static void checkIfDateWorksWithCurrentTime(Date date, String time) throws Exception {
		Time enteredTime = stringToTime(time);
		Date currentDate = (Date) CarShopApplication.getSystemDate();
		Time currentTime = (Time) CarShopApplication.getSystemTime();
		// if the date is before the current date in the system
		if(date.before(currentDate)) throw new Exception();
		// else, if on the same date but at a time before regarding the current date
		else if(enteredTime.before(currentTime) 
				&& date.equals(currentDate)) {
			throw new Exception();
		}
	}

	// since garage business hours are within carshop business hours, if this test fails it means that the appointment
	// is also not during the carhop's business hours
	public static boolean checkInGarageBusinessHours(CarShop cs, Time startTime, Time endTime, Date day, Garage garage) {
		List<BusinessHour> bhours = garage.getBusinessHours();
		LocalDate localDate = day.toLocalDate();
		String DayOfWeek = localDate.getDayOfWeek().toString();
		for (BusinessHour bhour: bhours) {
			if(bhour.getDayOfWeek()==null) return false;
			if (bhour.getDayOfWeek().toString().toLowerCase().equals(DayOfWeek.toLowerCase())) {
				if ((endTime.before(bhour.getEndTime()) || endTime.equals(bhour.getEndTime())) && 
						(startTime.after(bhour.getStartTime())  || startTime.equals(bhour.getStartTime()))) {
					
					return true;
				}
			}
		}
		return false;
	}
	//checks if the time is available
	public static boolean checkTimeAvailable(CarShop cs, Time startTime, Time endTime, Date day) {
		// get the list of appointments in the carshop system
		List<Appointment> appointment = cs.getAppointments();
		// also goes through the newly created appointment... which doesn't have any service bookings
		for (Appointment app: appointment) {	// iterate through this list
			// get all the services bookings in this appointment specifically
			List<ServiceBooking> serviceBooking = app.getServiceBookings();
			for (ServiceBooking sb: serviceBooking) {	// for each of these serviceBookings
				TimeSlot timeSlot = sb.getTimeSlot();	// get the time slot
				if (timeSlot.getStartDate().equals(day)) {	// if so, a problem might arise
					if (timeSlot.getStartTime().before(endTime) && startTime.before(timeSlot.getEndTime())) {
						return false; //&& sb.getService().getGarage().equals(garageName)
					}
				}
			}
		}
		return true;
	}
	
	//cancel appointment method
	public static void CancelAppointment(String custName, String name, String startDate2, String time, CarShop cs) throws InvalidInputException {
		if(custName.contains("owner")) throw new InvalidInputException("An owner cannot cancel an appointment");
		else if(custName.contains("Technician")) throw new InvalidInputException("A technician cannot cancel an appointment");
		
		Date date = stringToDate(startDate2);
		try {
			// checking if the date is before the current date,
			checkIfDateWorksWithCurrentTime(date, time);

		} catch(Exception e) {
			throw new InvalidInputException("Cannot cancel an appointment on the appointment date");
		}
		Customer customer = (Customer) User.getWithUsername(custName);

		Appointment appName = null;
		for (Appointment app: customer.getAppointments()) {
			if (app.getBookableService().getName().equals(name)) {
				appName = app; 
				break;
			}
		}
		//checks whether its either owner, thechnician or a different customer that is cancelling the appointment
		if (custName.contains("owner")) {
			throw new InvalidInputException("An owner cannot cancel an appointment");
		}
		if (custName.contains("Technician")) {
			throw new InvalidInputException("A technician cannot cancel an appointment");
		}
		if (CarShopApplication.getSystemDate().equals(date)) {
			throw new InvalidInputException("Cannot cancel an appointment on the appointment date");
		}
		if (appName==null || !appName.getCustomer().getUsername().equals(custName)) {
			throw new InvalidInputException("A customer can only cancel their own appointments");
		}
		for (int i = appName.getServiceBookings().size()-1; i >= 0 ; i--) {
			appName.getServiceBooking(i).delete();
		}
		appName.delete();
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	// stringToDate
    // done, but might not work, converts a string to a sql.Date
    public static Date stringToDate(String string) throws InvalidInputException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Date(formatter.parse(string).getTime());
        } catch (Exception e) {
            throw new InvalidInputException("parsing error");
        }
    }

	/** ** START HONG YI ** **/ 
	
	//method that sets up business enters information into system
	public static void setUpBusinessInfo(String nameString, String address, String phoneNumber, String emailAddress) 
			throws InvalidInputException {
		
//		if (CarShopApplication.getCarShop().getBusiness() == null) {
//			throw new InvalidInputException("The business doesn't exist yet");
//		}
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to set up business information");
		}
		CarShop carShop = CarShopApplication.getCarShop();
		if (nameString == null || nameString.length() == 0 || address == null || address.length() == 0
				|| phoneNumber == null || phoneNumber.length() == 0 || emailAddress == null
				|| emailAddress.length() == 0) {
			throw new InvalidInputException("Empty/null fields");
		}
		try {
			if (carShop.getBusiness() == null) {
				isProperEmailAddress(emailAddress);
				Business business = new Business(nameString, address, phoneNumber, emailAddress, carShop);
				carShop.setBusiness(business);
			} else {
				throw new InvalidInputException("Can't set up business info, the business already exists");
			}

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	//method that creates hours for business
	public static void createBusinessHour(String day, String startTime, String endTime) throws InvalidInputException {
		//checks if it's owner entering the information
		if (CarShopApplication.getCarShop().getBusiness() == null) {
			throw new InvalidInputException("The business doesn't exist yet");
		}
		
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to update business information");
		}
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			BusinessHour businessHour = new BusinessHour(convertDayOfWeek(day), stringToTime(startTime),
					stringToTime(endTime), carShop);

			if (isBusinessHourConflict(businessHour, businessHour)) {
				throw new InvalidInputException("The business hours cannot overlap");
			}
			if (!startTimeBeforeEndTime(businessHour.getStartTime(), businessHour.getEndTime())) {
				throw new InvalidInputException("Start time must be before end time");
			}
			carShop.getBusiness().addBusinessHour(businessHour);

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	//method to create holidays // doesnt have proper checks
	public static void createHoliday(String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
		//checks if it's owner entering the information
		
		if (CarShopApplication.getCarShop().getBusiness() == null) {
			throw new InvalidInputException("The business doesn't exist yet");
		}
		
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to set up business information");
		}
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			carShop.getBusiness().addHoliday(new TimeSlot(stringtoDate(startDate), stringToTime(startTime),
					stringtoDate(endDate), stringToTime(endTime), carShop));
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	//method to create vacations // doesnt have proper checks
	public static void createVacation(String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
		if (CarShopApplication.getCarShop().getBusiness() == null) {
			throw new InvalidInputException("The business doesn't exist yet");
		}
		
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to set up business information");
		}
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			carShop.getBusiness().addVacation(new TimeSlot(stringtoDate(startDate), stringToTime(startTime),
					stringtoDate(endDate), stringToTime(endTime), carShop));
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	//method that creates time slots
	public static void createTimeSlot(String type, String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
		if (CarShopApplication.getCarShop().getBusiness() == null) {
			throw new InvalidInputException("The business doesn't exist yet");
		}
		//checks if it's owner entering the information
		CarShop carShop = CarShopApplication.getCarShop();
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to update business information");
		}
		Date sDate = stringtoDate(startDate);
		Time sTime = stringToTime(startTime);
		Date eDate = stringtoDate(endDate);
		Time eTime = stringToTime(endTime);
		//checks to see if the start time is before the end time & if the date and time entered is before the system date
		if (!startDateBeforeEndDate(sDate, eDate)) {
			throw new InvalidInputException("Start time must be before end time ");
		}
		if (startDate.equalsIgnoreCase(endDate) && !startTimeBeforeEndTime(sTime, eTime)) {
			throw new InvalidInputException("Start time must be before end time ");
		}
		if (sDate.before(CarShopApplication.getSystemDate()) || (!sDate.after(CarShopApplication.getSystemDate())
				&& sTime.before(CarShopApplication.getSystemTime()))) {
			if (type.equalsIgnoreCase("vacation")) {
				throw new InvalidInputException("Vacation cannot start in the past");
			} else {
				throw new InvalidInputException("Holiday cannot start in the past");
			}
		}

		TimeSlot toAddSlot = new TimeSlot(sDate, sTime, eDate, eTime, carShop);
		isVacationAndHolidayConflict(type, toAddSlot, null);
		if (type.equalsIgnoreCase("vacation")) {
			carShop.getBusiness().addVacation(toAddSlot);
		} else if (type.equalsIgnoreCase("holiday")) {
			carShop.getBusiness().addHoliday(toAddSlot);
		} else {
			throw new InvalidInputException("no such time slot");
		}
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	//method to update business information
	public static void updateBusinessInfo(String nameString, String address, String phoneNumber, String emailAddress)
			throws InvalidInputException {
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to update business information");
		}
		CarShop carShop = CarShopApplication.getCarShop();
		Business business = carShop.getBusiness();
		if (business == null) {
			carShop.setBusiness(new Business("", "", "", "", carShop));
			business = carShop.getBusiness();
		}
		//checks if the inputs are null or empty
		if (nameString == null || nameString.length() == 0 || address == null || address.length() == 0
				|| phoneNumber == null || phoneNumber.length() == 0 || emailAddress == null
				|| emailAddress.length() == 0) {
			throw new InvalidInputException("Empty fields");
		}
		
		try {
			//checks if the email is in a proper format
			isProperEmailAddress(emailAddress);
			business.setName(nameString);
			business.setAddress(address);
			business.setPhoneNumber(phoneNumber);
			business.setEmail(emailAddress);
		} catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	//method to delete business hours
	public static void deleteBusinessHour(String dayOfweek, String startTime) throws InvalidInputException {
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to update business information");
		}
		//finds the business hour that they have to delete
		Business business = CarShopApplication.getCarShop().getBusiness();
		List<BusinessHour> businessHours = business.getBusinessHours();
		BusinessHour toDelete = null;
		for (BusinessHour businessHour : businessHours) {
			if (businessHour.getDayOfWeek().name().equalsIgnoreCase(dayOfweek)
					&& timeToString(businessHour.getStartTime()).equalsIgnoreCase(startTime)) {
				toDelete = businessHour;
				break;
			}
		}
		if (toDelete != null) {
			business.removeBusinessHour(toDelete);
		}
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	//method to modify the time slots
	public static void modifyTimeSlot(String type, String oldStart, String oldTime, String startDate, String startTime,
			String endDate, String endTime) throws InvalidInputException {
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to update business information");
		}
		//getting the business from the carshop
		Business business = CarShopApplication.getCarShop().getBusiness();

		Date sDate = stringtoDate(startDate);
		Time sTime = stringToTime(startTime);
		Date eDate = stringtoDate(endDate);
		Time eTime = stringToTime(endTime);
		//checking if the start time is before the end time
		if (!startDateBeforeEndDate(sDate, eDate)) {
			throw new InvalidInputException("Start time must be before end time ");
		}
		if (startDate.equalsIgnoreCase(endDate) && !startTimeBeforeEndTime(sTime, eTime)) {
			throw new InvalidInputException("Start time must be before end time ");
		}
		if (sDate.before(CarShopApplication.getSystemDate()) || (!sDate.after(CarShopApplication.getSystemDate())
				&& sTime.before(CarShopApplication.getSystemTime()))) {
			if (type.equalsIgnoreCase("vacation")) {
				throw new InvalidInputException("Vacation cannot start in the past");
			} else {
				throw new InvalidInputException("Holiday cannot start in the past");
			}
		}
		//to check if the time slot has conflict with any existing time slots
		TimeSlot timeSlot = null;
		if (type.equalsIgnoreCase("vacation")) {
			for (TimeSlot slot : business.getVacations()) {

				if (oldStart.equalsIgnoreCase(dateToString(slot.getStartDate()))
						&& oldTime.equalsIgnoreCase(timeToString(slot.getStartTime()))) {
					timeSlot = slot;
				}
			}

		} else {
			for (TimeSlot slot : business.getHolidays()) {
				if (oldStart.equalsIgnoreCase(dateToString(slot.getStartDate()))
						&& oldTime.equalsIgnoreCase(timeToString(slot.getStartTime()))) {
					timeSlot = slot;

				}
			}
		}
		if (timeSlot == null) {
			throw new InvalidInputException("Can't find this " + type);
		}

		TimeSlot test = new TimeSlot(sDate, sTime, eDate, eTime, CarShopApplication.getCarShop());

		isVacationAndHolidayConflict(type, test, timeSlot);

		timeSlot.setStartDate(sDate);
		timeSlot.setStartTime(sTime);
		timeSlot.setEndDate(eDate);
		timeSlot.setEndTime(eTime);
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}
	
	//method to delete time slots
	public static void deleteTimeSlot(String type, String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to update business information");
		}
		Business business = CarShopApplication.getCarShop().getBusiness();
		TimeSlot timeSlot = null;
		if (type.equalsIgnoreCase("vacation")) {
			timeSlot = findVacation(startDate, startTime, endDate, endTime);
			if (timeSlot != null) {
				business.removeVacation(timeSlot);
			}

		} else {
			timeSlot = findHoliday(startDate, startTime, endDate, endTime);
			if (timeSlot != null) {
				business.removeHoliday(timeSlot);
			}
		}
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	public static void deleteHoliday(String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to set up business information");
		}
		//getting the business
		Business business = CarShopApplication.getCarShop().getBusiness();
		TimeSlot toDelete = null;
		toDelete = findHoliday(startDate, startTime, endDate, endTime);
		if (toDelete != null) {
			business.removeHoliday(toDelete);
		}
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	//method delete vacations
	public static void deleteVacation(String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
		//checks if it's owner entering the information
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to set up business information");
		}
		Business business = CarShopApplication.getCarShop().getBusiness();
		TimeSlot toDelete = null;
		toDelete = findVacation(startDate, startTime, endDate, endTime);
		if (toDelete != null) {
			business.removeVacation(toDelete);
		}
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	//returning the business hours
	public static List<TOBusinessHour> getBusinessHours() throws Exception{
		Business business = CarShopApplication.getCarShop().getBusiness();
		if(business == null) throw new Exception("Business does not exist yet!");
		List<BusinessHour> businessHours = business.getBusinessHours();
		List<TOBusinessHour> toBusinessHours = new ArrayList<>();
		for (BusinessHour businessHour : businessHours) {
			TOBusinessHour toBusinessHour = new TOBusinessHour(businessHour.getDayOfWeek().name(),
					businessHour.getStartTime(), businessHour.getEndTime());
			toBusinessHours.add(toBusinessHour);
		}
		return toBusinessHours;
	}
	
	//returning the holidays
	public static List<TOTimeSlot> getHolidays() throws Exception {
		Business business = CarShopApplication.getCarShop().getBusiness();
		if(business == null) throw new Exception("Business does not exist yet!");
		List<TimeSlot> holidaySlots = business.getHolidays();
		List<TOTimeSlot> toHolidays = new ArrayList<>();
		for (TimeSlot holiday : holidaySlots) {
			TOTimeSlot toTimeSlot = new TOTimeSlot(holiday.getStartDate(), holiday.getStartTime(), holiday.getEndDate(),
					holiday.getEndTime());
			toHolidays.add(toTimeSlot);
		}
		return toHolidays;
	}
	
	//returning the vacations
	public static List<TOTimeSlot> getVacations() throws Exception {
		Business business = CarShopApplication.getCarShop().getBusiness();
		if(business == null) throw new Exception("Business does not exist yet!");
		List<TimeSlot> vacationSlots = business.getVacations();
		List<TOTimeSlot> toVacations = new ArrayList<>();
		for (TimeSlot vacation : vacationSlots) {
			TOTimeSlot toTimeSlot = new TOTimeSlot(vacation.getStartDate(), vacation.getStartTime(),
					vacation.getEndDate(), vacation.getEndTime());
			toVacations.add(toTimeSlot);
		}
		return toVacations;
	}

	//getting the business information
	public static TOBusiness getBusinessInfo() {
		Business business = CarShopApplication.getCarShop().getBusiness();
		TOBusiness toBusiness = new TOBusiness(business.getName(), business.getAddress(), business.getPhoneNumber(),
				business.getEmail());
		return toBusiness;
	}
	
	//changing the business hours
	public static void modifyBusinessHour(String weekDay, String startTime, String newDay, String newStart,
			String newEnd) throws InvalidInputException {
		CarShop carShop = CarShopApplication.getCarShop();
		if (!hasAuthorization()) {
			throw new InvalidInputException("No permission to update business information");
		}

		Business business = CarShopApplication.getCarShop().getBusiness();
		List<BusinessHour> businessHours = business.getBusinessHours();
		BusinessHour foundHour = null;
		for (BusinessHour bHour : businessHours) {
			Time start = bHour.getStartTime();
			String timeAsString = timeToString(start);
			if (bHour.getDayOfWeek().name().equalsIgnoreCase(weekDay) && timeAsString.equalsIgnoreCase(startTime)) {
				foundHour = bHour;
			}
		}
		if (!startTimeBeforeEndTime(stringToTime(newStart), stringToTime(newEnd))) {
			throw new InvalidInputException("Start time must be before end time");
		}

		if (isBusinessHourConflict(
				new BusinessHour(convertDayOfWeek(newDay), stringToTime(newStart), stringToTime(newEnd), carShop),
				foundHour)) {
			business.addBusinessHour(foundHour);
			throw new InvalidInputException("The business hours cannot overlap");
		} else {
			foundHour.setDayOfWeek(convertDayOfWeek(newDay));
			foundHour.setStartTime(stringToTime(newStart));
			foundHour.setEndTime(stringToTime(newEnd));

		}
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	//making sure that the start time is before the end time
	private static boolean startTimeBeforeEndTime(Time startTime, Time endTime) {
		return startTime.before(endTime);
	}

	//making sure that the start date is before the end date
	private static boolean startDateBeforeEndDate(Date startDate, Date endDate) {
		return !startDate.after(endDate);
	}

	// check if business hour conflicts
	private static boolean isBusinessHourConflict(BusinessHour businessHour, BusinessHour exception) {
		Business business = CarShopApplication.getCarShop().getBusiness();
		for (BusinessHour bHour : business.getBusinessHours()) {
			boolean sameDay = isSameDay(businessHour.getDayOfWeek().name(), bHour.getDayOfWeek().name());
			boolean overlap = isTimeOverlap(businessHour.getStartTime(), businessHour.getEndTime(),
					bHour.getStartTime(), bHour.getEndTime());
			if (bHour != exception && businessHour != bHour
					&& sameDay && overlap) {
				return true;

			}
		}
		return false;
	}

	// verifies if two string representing dayofweeks are the same
	private static boolean isSameDay(String day1, String day2) {
		return day1.equalsIgnoreCase(day2);
	}

	// check if vacation and holiday conflict
	private static boolean isVacationAndHolidayConflict(String type, TimeSlot timeSlot, TimeSlot exception)
			throws InvalidInputException {
		Business business = CarShopApplication.getCarShop().getBusiness();
		// check if conflicts with vacations
		for (TimeSlot t : business.getVacations()) {
			if (timeSlot != t && exception != t
					&& inDateTimeRange(timeSlot.getStartDate(), timeSlot.getStartTime(), timeSlot.getEndDate(), timeSlot.getEndTime(), t.getStartDate(), t.getStartTime(), t.getEndDate(), t.getEndTime())) {
				if (type.equalsIgnoreCase("vacation")) {
					throw new InvalidInputException("Vacation times cannot overlap");
				} else if (type.equalsIgnoreCase("holiday")) {
					throw new InvalidInputException("Holiday and vacation times cannot overlap");
				} else {
					throw new InvalidInputException("Unexpected input");
				}

			}
		}
		// check if conflicts with holidays
		for (TimeSlot t : business.getHolidays()) {
			if (timeSlot != t && exception != t
					&& inDateTimeRange(timeSlot.getStartDate(), timeSlot.getStartTime(), timeSlot.getEndDate(), timeSlot.getEndTime(), t.getStartDate(), t.getStartTime(), t.getEndDate(), t.getEndTime())) {
				if (type.equalsIgnoreCase("Holiday")) {
					throw new InvalidInputException("Holiday times cannot overlap");
				} else if (type.equalsIgnoreCase("Vacation")) {
					throw new InvalidInputException("Holiday and vacation times cannot overlap");
				} else {
					throw new InvalidInputException("Unexpected input");
				}

			}
		}
		return false;
	}

	// find a holiday given the start/end date/time in string form
	private static TimeSlot findHoliday(String startDate, String startTime, String endDate, String endTime) {
		Business business = CarShopApplication.getCarShop().getBusiness();

		TimeSlot foundSlot = null;

		for (TimeSlot slot : business.getHolidays()) {
			if (dateToString(slot.getStartDate()).equals(startDate)
					&& timeToString(slot.getStartTime()).equals(startTime)
					&& dateToString(slot.getEndDate()).equals(endDate)
					&& timeToString(slot.getEndTime()).equals(endTime)) {
				foundSlot = slot;
				break;

			}
		}
		return foundSlot;
	}
	
	
	// find a vacation given the start/end date/time in string form
	private static TimeSlot findVacation(String startDate, String startTime, String endDate, String endTime) {
		Business business = CarShopApplication.getCarShop().getBusiness();

		TimeSlot foundSlot = null;

		for (TimeSlot slot : business.getVacations()) {
			if (dateToString(slot.getStartDate()).equals(startDate)
					&& timeToString(slot.getStartTime()).equals(startTime)
					&& dateToString(slot.getEndDate()).equals(endDate)
					&& timeToString(slot.getEndTime()).equals(endTime)) {
				foundSlot = slot;
				break;

			}
		}
		return foundSlot;
	}

	//checks if the time overlaps
	private static boolean isTimeOverlap(Time start1, Time end1, Time start2, Time end2) {
		return (start1.before(end2) && start2.before(end1));
	}

	//checks if email address is correct
	private static boolean isProperEmailAddress(String emailAddress) throws InvalidInputException {
		if (!emailAddress.contains("@") || !emailAddress.contains(".")
				|| emailAddress.indexOf("@") > emailAddress.indexOf("@")) {
			throw new InvalidInputException("Invalid email");
		}
		return true;
	}

	// converts a String to a dayofweek
	private static DayOfWeek convertDayOfWeek(String day) throws InvalidInputException {
		try {
			return DayOfWeek.valueOf(day);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	// verifies that the current user is the owner;
	private static boolean hasAuthorization() {
		return CarShopApplication.getCurrentUser().equals("owner");
	}


	/** ** END HONG YI ** **/ 
		
	//method to sign up users
	public static User signUpUser(String username, String password, CarShopApplication.AccountType userType) throws InvalidInputException
	{
		//checks for invalid input
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
		
		//adding the users
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
		try {
			CarShopPersistence.save(carShop);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		return user;
		
	}
	
	// updates the users information
	public static void updateUser(String username, String password) throws InvalidInputException
	{
		//checks for invalid inputs
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
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}
	
	//find the technician type 
	public static Technician findTechnician(String technicianType, CarShop cs) {
		Technician technician = null;
		for(int i=0; i<5;i++) {
			Technician techGuy = cs.getTechnician(i);
			if(techGuy.getType().equals(CarShopController.getTechnicianType(technicianType))) {
				technician = techGuy;
				break;
			}
		}
		return technician;

}
	
	//checks the business hours of a certain garage and places it into three different categories
	public static int checkBusinessHour(Garage garage, BusinessHour businessHour,CarShop cs) {
		Business business = cs.getBusiness();
		for(BusinessHour hours:garage.getBusinessHours()) {
			if(hours.getDayOfWeek().equals(businessHour.getDayOfWeek())) {
				//overlap situation
				if(hours.getStartTime().before(businessHour.getEndTime()) && businessHour.getStartTime().before(hours.getEndTime())){
					return 0;
				}
			}
		}
		boolean correct = false;
		for(BusinessHour hours:business.getBusinessHours()) {
			if(hours.getDayOfWeek().equals(businessHour.getDayOfWeek())) {
				if(hours.getStartTime().before(businessHour.getEndTime()) && businessHour.getStartTime().before(hours.getEndTime())){
					if(businessHour.getStartTime().equals(hours.getStartTime())||businessHour.getStartTime().after(hours.getStartTime())) {
						if(businessHour.getEndTime().equals(hours.getEndTime())||businessHour.getEndTime().before(hours.getEndTime())) {
							correct = true;
						}
					}
				}
			}
		}if(!correct) {
			return 1;
		}
		return 2;
	}
	
	//removes the business hours
	public static void removeBusinessHour(BusinessHour removedHours, User user, Garage garage, CarShop cs) throws InvalidInputException {

		String username = user.getUsername();
		TechnicianType technicianType = getTechnicianType(username);
		//must fit certain criteria before removing business hours
		if(technicianType!=null) {
			if(technicianType.equals(garage.getTechnician().getType())) {
				if(removedHours.getStartTime().after(removedHours.getEndTime())) {
					throw new InvalidInputException("Start time must be before end time");
				}else {
					garage.removeBusinessHour(removedHours);
				}
			}else {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
		}else {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}	
	
	//go through and add business hours!
	public static void addBusinessHour(BusinessHour addedHours, User user, Garage garage, CarShop cs) throws InvalidInputException{
		if(addedHours.getStartTime().after(addedHours.getEndTime())) {
			throw new InvalidInputException("Start time must be before end time");
		}
		String username = user.getUsername();
		int value = checkBusinessHour(garage, addedHours, cs);
		if(value == 0) {
			throw new InvalidInputException("The opening hours cannot overlap");
		}
		if(value == 1) {
			throw new InvalidInputException("The opening hours are not within the opening hours of the business");
		}
		//no need to check 2
		TechnicianType technicianType = getTechnicianType(username);
		if(technicianType!=null) {
			if(technicianType.equals(garage.getTechnician().getType())) {
				garage.addBusinessHour(addedHours);	
			}else {
				throw new InvalidInputException("You are not authorized to perform this operation");
			}
		}else {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	// login for a customer
	public static void customerLogin(String username, String password) throws InvalidInputException{
		User user = User.getWithUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			CarShopApplication.setUser(user);
			CarShopApplication.setAccountType(CarShopApplication.AccountType.Customer);
			CarShopApplication.setLoggedIn(true);
		}else {
			throw new InvalidInputException("Username/password not found");
		}
	}
	
	// loging for a technician
	public static void technicianLogin(String username, String password) throws InvalidInputException {
//		User user = User.getWithUsername(username);
		Technician user = findTechnician(username, CarShopApplication.getCarShop());
		if(user != null && user.getPassword().equals(password)) {
			CarShopApplication.setUser(user);
			CarShopApplication.setAccountType(getApplicationTechnicianType(user.getUsername()));
			CarShopApplication.setLoggedIn(true);
		}else {
			throw new InvalidInputException("Username/password not found");
		}
	}
	
	//login for the owner
	public static void ownerLogin(String username, String password) throws InvalidInputException {
		if(CarShopApplication.getCarShop().getOwner() == null) {
			Owner newOwner = new Owner(username, password, CarShopApplication.getCarShop());
			CarShopApplication.getCarShop().setOwner(newOwner);
			CarShopApplication.setUser(newOwner);
//			CarShopApplication.setAccountType(CarShopApplication.AccountType.Owner);
//			CarShopApplication.setLoggedIn(true);
			try {
				CarShopPersistence.save(CarShopApplication.getCarShop());
			}catch(RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
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
	
	//decides which login method (see above methods) to use based on username
	public static void login(String username, String password) throws InvalidInputException {
		if(username.equals("owner")) {
			ownerLogin(username, password);
		}else if(username.toLowerCase().contains("technician")) {
			technicianLogin(username, password);
		}else {
			customerLogin(username, password);
		}
	}
	
	//gets the type of technician
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
	
	//gets the account type of technician
	public static AccountType getApplicationTechnicianType(String str) {
		str = str.toLowerCase();
		if(str.contains("tire")) {
			return CarShopApplication.AccountType.TireTechnician;
		}
		else if(str.contains("engine")) {
			return CarShopApplication.AccountType.EngineTechnician;
		}
		else if(str.contains("transmission")) {
			return CarShopApplication.AccountType.TransmissionTechnician;
		}
		else if(str.contains("electronics")) {
			return CarShopApplication.AccountType.ElectronicsTechnician;
		}
		else if(str.contains("fluids")) {
			return CarShopApplication.AccountType.FluidsTechnician;
		}
		else return null;
	}
	
	//gets the index of technician in order to find it from the list
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
	
	//makes a new account for technician and owners
	public static void newAccount(String username, String password, CarShop cs) throws InvalidInputException {
		@SuppressWarnings("unused")
		Owner owner = null;
		@SuppressWarnings("unused")
		Technician technician = null;
		if(username.equals("owner") && password.equals("owner")) {
			if(CarShopApplication.getCarShop().getOwner() == null) owner = new Owner(username, password, cs);
		}
		else if(username.equals("Tire-Technician") && password.equals("Tire-Technician")) {
			// check if the technician exists, get their password, if it's NOT "Tire-Technician" then just get out of the method
//			Technician t = getTechnicianWithName("Tire-Technician");
			int toUse = CarShopController.getTechnician("Tire-Technician", cs);
			if(toUse == -1) {
				technician = new Technician(username, password, TechnicianType.Tire, cs);
			}
		}
		else if(username.equals("Engine-Technician") && password.equals("Engine-Technician")) {
			int toUse = CarShopController.getTechnician("Engine-Technician", cs);
			if(toUse == -1) {
				technician = new Technician(username, password, TechnicianType.Engine, cs);

			}		
		}
		else if(username.equals("Transmission-Technician") && password.equals("Transmission-Technician")) {
			int toUse = CarShopController.getTechnician("Transmission-Technician", cs);
			if(toUse == -1) {
				technician = new Technician(username, password, TechnicianType.Transmission, cs);

			}
		}
		else if(username.equals("Electronics-Technician") && password.equals("Electronics-Technician")) {
			int toUse = CarShopController.getTechnician("Electronics-Technician", cs);
			if(toUse == -1) {
				technician = new Technician(username, password, TechnicianType.Electronics, cs);

			}
		}
		else if(username.equals("Fluids-Technician") && password.equals("Fluids-Technician")) {
			int toUse = CarShopController.getTechnician("Fluids-Technician", cs);
			if(toUse == -1) {
				technician = new Technician(username, password, TechnicianType.Fluids, cs);
			}
		}
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	//sets a technician's garage
	public static void setGarage(Technician t, CarShop cs) throws InvalidInputException {
		t.setGarage(new Garage(cs, t));
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	//returns DayOfWeek object
	public static DayOfWeek getWeekDay(String day) throws InvalidInputException {
		DayOfWeek dayOfWeek = null;
		if(day.equals("Monday")) {
			dayOfWeek = DayOfWeek.Monday;
		}
		else if(day.equals("Tuesday")) {
			dayOfWeek = DayOfWeek.Tuesday;
		}
		else if(day.equals("Wednesday")) {
			dayOfWeek = DayOfWeek.Wednesday;
		}
		else if(day.equals("Thursday")) {
			dayOfWeek = DayOfWeek.Thursday;
		}
		else if(day.equals("Friday")) {
			dayOfWeek = DayOfWeek.Friday;
		}
		else if(day.equals("Saturday")) {
			dayOfWeek = DayOfWeek.Saturday;
		}
		else if(day.equals("Sunday")) {
			dayOfWeek = DayOfWeek.Sunday;
		}
		
		return dayOfWeek;
	}

	// defines services
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
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
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
						if (bookableService.getName().equals(service)) {
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
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	//converts from string to time
	public static Time stringToTime(String string) throws InvalidInputException {
        String pattern = "HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Time(formatter.parse(string).getTime());
        } catch (Exception e) {
            throw new InvalidInputException("parsing error");
        }
    }

    // converts string to date
    private static Date stringtoDate(String string) throws InvalidInputException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Date(formatter.parse(string).getTime());
        } catch (Exception e) {
            throw new InvalidInputException("parsing error");
        }
    }

    // converts a sql.Date to a string
    public static String dateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    // converts a sql.Time to a string
    public static String timeToString(Time time) {
        String pattern = "HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(time);
    }
	//defines all the service combos
	@SuppressWarnings("static-access")
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
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}
	//method to parse strings
	@SuppressWarnings("static-access")
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
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}
	
	// END JERRY
	
	
	/** ** START APPOINTMENT MANAGEMENT ** 


	/**
	 * method to create an appointment for single service or combos
	 * @param customerName customer name
	 * @param serviceName main service or combo
	 * @param optService optional services (service or combo)
	 * @param date start date
	 * @param startTime start time
	 * @param currentTime current time
	 * @throws Exception incorrect format
	 */
	public static void createAppointmentAt(String customerName, String serviceName, String optionalService, String date, String startTime, String currentTime) throws Exception {
		setSystemDateAndTime(currentTime);
		CreateAppointmentWithOptServices(customerName, serviceName, startTime, date, CarShopApplication.getCarShop(), optionalService, true);
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * change the service of a service appointment
	 * @param appointment the appointment
	 * @param customerName the customer name
	 * @param serviceName the new service name
	 * @param currentTime the logged system time
	 * @throws InvalidInputException if inputs are incorrect
	 */
	public static void changeServiceAt(Appointment appointment, String customerName, String serviceName, String currentTime) throws InvalidInputException {
		if (appointment == null) {
			throw new InvalidInputException("The appointment cannot be null");
		}
		if(!customerName.equals(appointment.getCustomer().getUsername())) {
			throw new InvalidInputException("Only the customer can make changes to his appointment");
		}
		
		setSystemDateAndTime(currentTime);
		
		BookableService service = BookableService.getWithName(serviceName);
		if(!(service instanceof Service)) {
			throw new InvalidInputException("This is not a service");
		}
		
		Date date = appointment.getServiceBooking(0).getTimeSlot().getStartDate();
		Time startTime = appointment.getServiceBooking(0).getTimeSlot().getStartTime();
		Time endTime = findEndTime(service, startTime);
		
		if (appointmentConflitsWithAppointments(appointment, (Service) service, date, startTime, endTime, true)) {
//			// new code
//			CarShop cs = CarShopApplication.getCarShop();
//			CarShopApplication.getCarShop().getAppointment(cs.getAppointments().size()-1).delete();
//			// new code
			throw new InvalidInputException("Appointment time conflicts with existing appointments");
		}
		
		if (timeIsOutsideBusinessHours((Service) service, date, startTime, endTime)) {
//			// new code
//			CarShop cs = CarShopApplication.getCarShop();
//			CarShopApplication.getCarShop().getAppointment(cs.getAppointments().size()-1).delete();
//			// new code
			throw new InvalidInputException("Appointment time is not in garage business hours");
		}
		
		if (appointmentConflictsWithVacations(date, startTime, endTime)) {
//			// new code
//			CarShop cs = CarShopApplication.getCarShop();
//			CarShopApplication.getCarShop().getAppointment(cs.getAppointments().size()-1).delete();
//			// new code
			throw new InvalidInputException("Appointment overlaps vacations");
		}
		
		if (appointmentConflictsWithHolidays(date, startTime, endTime)) {
//			// new code
//			CarShop cs = CarShopApplication.getCarShop();
//			CarShopApplication.getCarShop().getAppointment(cs.getAppointments().size()-1).delete();
//			// new code
			throw new InvalidInputException("Appointment overlaps holidays");
		}
		
		appointment.changeAppointmentService((Service) service);
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}

	/**
	 * method to update the time of an appointment, works for single service and combos
	 * @param appointment the appointment
	 * @param customerName the customer name
	 * @param newDate the new date
	 * @param newTime the new start times
	 * @param currentTime the logged system time
	 * @throws InvalidInputException if inputs are invalid
	 */
	public static void updateDateAndTimeAt(Appointment appointment, String customerName, String newDate, String newTime, String currentTime) throws InvalidInputException {
		if (appointment == null) {
			throw new InvalidInputException("The appointment cannot be null");
		}
		if(!customerName.equals(appointment.getCustomer().getUsername())) {
			throw new InvalidInputException("Only the customer can make changes to his appointment");
		}
		setSystemDateAndTime(currentTime);
		
		if (appointment.getBookableService() instanceof Service) {
			Date startDate = stringtoDate(newDate);
			Time startTime = stringToTime(newTime);
			Service service = (Service) appointment.getBookableService();
			Time endTime = findEndTime(service, startTime);
			
			if (appointmentConflitsWithAppointments(appointment, service, startDate, startTime, endTime, true)) {
				throw new InvalidInputException("Appointment time conflicts with existing appointments");
			}
			
			if (timeIsOutsideBusinessHours(service, startDate, startTime, endTime)) {
				throw new InvalidInputException("Appointment time is not in garage business hours");
			}
			
			if (appointmentConflictsWithVacations(startDate, startTime, endTime)) {
				throw new InvalidInputException("Appointment overlaps vacations");
			}
			
			if (appointmentConflictsWithHolidays(startDate, startTime, endTime)) {
				throw new InvalidInputException("Appointment overlaps holidays");
			}
			
			List<Time> startTimes = new ArrayList<Time>();
			startTimes.add(startTime);
			appointment.changeStartDateAndTime(startDate, startTimes);
			
		} else {
			Date date = stringtoDate(newDate);
			List<Time> startTimes = parseComboTimes(newTime);
			List<Service> services = new ArrayList<>();
			List<ServiceBooking> serviceBookings = appointment.getServiceBookings();
			for(ServiceBooking serviceBooking : serviceBookings) {
				services.add(serviceBooking.getService());
			}
			List<Time> endTimes = new ArrayList<>();
			for(int i = 0; i < services.size(); i++) {
				Service service = services.get(i);
				Time startTime = startTimes.get(i);
				Time endTime = findEndTime(service, startTime);
				endTimes.add(endTime);
			}
			if (comboTimeConflictsWithAppointments(appointment, services, date, startTimes, endTimes, true)) {
				throw new InvalidInputException("Combo items times conflicts with existing appointments");
			}
			
			if (comboItemsConflicts(startTimes, endTimes)) {
				throw new InvalidInputException("Combo items times conflict with each other");
			}
			
			if (comboTimesIsOutsideBusinessHours(services, date, startTimes, endTimes)) {
				throw new InvalidInputException("Combo items times are outside business hours");
			}
			
			if (comboTimesConflictsWithVacations(date, startTimes, endTimes)) {
				throw new InvalidInputException("Combo items conflict with vacations");
			}
			
			if (comboTimesConflictsWithHolidays(date, startTimes, endTimes)) {
				throw new InvalidInputException("Combo items conflict with holidays");
			}
			
			appointment.changeStartDateAndTime(date, startTimes);
		}
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}
	
	/**
	 * method to cancel the appointment at a specific time
	 * @param appointment the appointment
	 * @param customerName the customer name
	 * @param currentTime the system time
	 * @throws InvalidInputException throw exception if inputs are invalid
	 */
	public static void cancelAppointmentAt(Appointment appointment, String customerName, String currentTime) throws InvalidInputException {
		if (appointment == null) {
			throw new InvalidInputException("The appointment cannot be null");
		}
		setSystemDateAndTime(currentTime);
		if (customerName.contains("owner")) {
			throw new InvalidInputException("An owner cannot cancel an appointment");
		}
		if (customerName.contains("Technician")) {
			throw new InvalidInputException("A technician cannot cancel an appointment");
		}
		if (!appointment.getCustomer().getUsername().equals(customerName)) {	// added !
			throw new InvalidInputException("A customer can only cancel their own appointments");
		}
		appointment.cancelBooking();
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
		
	/**
	 * method to add an optional service to an appointment
	 * @param appointment the appointment
	 * @param customerName the customer name
	 * @param serviceName the service name
	 * @param startTime the start time
	 * @param currentTime the logged system time
	 * @throws InvalidInputException if the input is invalid
	 */
	public static void addOptServiceAt(Appointment appointment, String customerName, String serviceName, String startTime, String currentTime) throws InvalidInputException{
		if (appointment == null) {
			throw new InvalidInputException("The appointment cannot be null");
		}
		if(!customerName.equals(appointment.getCustomer().getUsername())) {
			throw new InvalidInputException("Only the customer can make changes to his appointment");
		}
		setSystemDateAndTime(currentTime);
		
		BookableService bookableService = BookableService.getWithName(serviceName);
		if(!(bookableService instanceof Service)) {
			throw new InvalidInputException("This is not a service");
		}
		
		Service service = (Service) bookableService;
		Date date = appointment.getServiceBooking(0).getTimeSlot().getStartDate();
		Time sTime = stringToTime(startTime);
		Time eTime = findEndTime(service, sTime);
		
		if (appointmentConflitsWithAppointments(appointment, service, date, sTime, eTime, true)) {
			throw new InvalidInputException("Can't add item, time conflicts with other appointment");
		}
		
		List<Time> startTimes = new ArrayList<>();
		List<Time> endTimes = new ArrayList<>();
		List<ServiceBooking> serviceBookings = appointment.getServiceBookings();
		for(int i = 0; i < serviceBookings.size(); i++) {
			Time start = serviceBookings.get(i).getTimeSlot().getStartTime();
			Time end = serviceBookings.get(i).getTimeSlot().getEndTime();
			startTimes.add(start);
			endTimes.add(end);
		}
		startTimes.add(sTime);
		endTimes.add(eTime);
		
		if (comboItemsConflicts(startTimes, endTimes)) {
			throw new InvalidInputException("Can't add item, time conflicts with other items");
		}
		
		if (timeIsOutsideBusinessHours(service, date, sTime, eTime)) {
			throw new InvalidInputException("Can't add item, time is outside business hours");
		}
		
		if (appointmentConflictsWithVacations(date, sTime, eTime)) {
			throw new InvalidInputException("Can't add item, time is during vacations");
		}
		
		if (appointmentConflictsWithHolidays(date, sTime, eTime)) {
			throw new InvalidInputException("Can't add item, time is during holidays");
		}
		
		appointment.addOptServiceToCombo(service, sTime);
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * Method for the owner to start an appointment
	 * @param appointment the appointment
	 * @param currentTime time when the action was triggered
	 * @throws InvalidInputException if the inputs are invalid
	 */
	public static void startAppointmentAt(Appointment appointment, String currentTime) throws InvalidInputException{
		if (appointment == null) {
			throw new InvalidInputException("The appointment cannot be null");
		}
		if (!appointment.getCarShop().getOwner().equals(CarShopApplication.getUser())) {
			throw new InvalidInputException("Only the owner can start appointments.");
		}
		setSystemDateAndTime(currentTime);
		appointment.startAppointment();
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * Method for the owner to end an appointment
	 * @param appointment the appointment
	 * @param currentTime time when the action was triggered
	 * @throws InvalidInputException if the inputs are invalid
	 */
	public static void endAppointmentAt(Appointment appointment, String currentTime) throws InvalidInputException{
		if (appointment == null) {
			throw new InvalidInputException("The appointment cannot be null");
		}
		if (!appointment.getCarShop().getOwner().equals(CarShopApplication.getUser())) {
			throw new InvalidInputException("Only the owner can end appointments.");
		}
		setSystemDateAndTime(currentTime);
		appointment.endAppointment();
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * Method for the owner to update the "no show" counter of the customer
	 * @param appointment the appointment
	 * @param currentTime time when the action was triggered
	 * @throws InvalidInputException if the inputs are invalid
	 */
	public static void updateNoShowAt(Appointment appointment, String currentTime) throws InvalidInputException{
		if (appointment == null) {
			throw new InvalidInputException("The appointment cannot be null");
		}
		if (!appointment.getCarShop().getOwner().equals(CarShopApplication.getUser())) {
			throw new InvalidInputException("Only the owner set no shows.");
		}
		setSystemDateAndTime(currentTime);
		appointment.noShow();
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * helper method to parse the date and time from a string, then update the system date/time
	 * @param dateTime string representing the date and time
	 * @throws InvalidInputException if the string is in an incorrect format
	 */
	public static void setSystemDateAndTime(String dateTime) throws InvalidInputException {
		String datePattern = "yyyy-MM-dd";
		String timePattern = "HH:mm";
		String[] splitString = dateTime.split("\\+");// uses a formatter from java.sql.Date/Time
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);// create a new object
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);// create a new object
		Date newDate = null;
		Time newTime = null;
		try {
			// uses a formatter from java.sql.Date/Time
			newDate = new java.sql.Date(dateFormatter.parse(splitString[0]).getTime());// create a new object
			newTime = new java.sql.Time(timeFormatter.parse(splitString[1]).getTime());// create a new object
		} catch (Exception e) {
			throw new InvalidInputException("Incorrect date time format");
		}
		CarShopApplication.setSystemDate(newDate);
		CarShopApplication.setSystemTime(newTime);
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * helper method to find the end time of a service based on the start time
	 * @param service the service
	 * @param startTime the start time
	 * @return the end time
	 */
	private static Time findEndTime(BookableService service, Time startTime) {
		int duration = service.getDuration();
		int minutes = duration % 60;
		int hours = duration / 60;
		
		LocalTime localtime = startTime.toLocalTime();
		localtime = localtime.plusMinutes(minutes);
		localtime = localtime.plusHours(hours);
		Time endTime = Time.valueOf(localtime);
		return endTime;
	}
	
	/**
	 * checks whether the new time conflicts with any existing appointments
	 * @param appointment the appointment we want to change
	 * @param name the service
	 * @param date the new date
	 * @param startTime the new start time
	 * @param endTime the new end time
	 * @param excludeSelf set to true if we want to exclude this appointment
	 * @return true if conflicts
	 */
	private static boolean appointmentConflitsWithAppointments(Appointment appointment, Service name, Date date, Time startTime, Time endTime, boolean excludeSelf) {
		Garage garage = name.getGarage();
		List<Appointment> appointments = CarShopApplication.getCarShop().getAppointments();
		for(Appointment appt : appointments) {
			if (!excludeSelf || !appt.equals(appointment)) {
				for(ServiceBooking serviceBooking : appt.getServiceBookings()) {
					if (serviceBooking.getService().getGarage().equals(garage) 
							&& serviceBooking.getTimeSlot().getStartDate().equals(date)) {
						if(isTimeOverlap(startTime, endTime, serviceBooking.getTimeSlot().getStartTime(), serviceBooking.getTimeSlot().getEndTime())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * checks whether the new time conflicts is outside business hours
	 * @param name the service
	 * @param date the date 
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return true if it conflicts
	 */
	private static boolean timeIsOutsideBusinessHours(Service name, Date date, Time startTime, Time endTime) {
		Garage garage = name.getGarage();
		for(BusinessHour bHour : garage.getBusinessHours()) {
			String ourDate = dayOfWeekFromDate(date).toString().toLowerCase();
			String existingDate = bHour.getDayOfWeek().toString().toLowerCase();
			if (ourDate.equals(existingDate)) {
				Time existingStartTime = bHour.getStartTime();
				Time existingEndTime = bHour.getEndTime();
				if (!startTime.before(existingStartTime) && 
						!endTime.after(existingEndTime)) {	// changed to an OR statement
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * helper method to get the day of week of a day
	 * @param date the date
	 * @return a java.time.DayOfWeek object
	 */
	private static java.time.DayOfWeek dayOfWeekFromDate(Date date) {
		LocalDate localDate = date.toLocalDate();
		return localDate.getDayOfWeek();
	}
	
	/**
	 * checks if the appointment time conflicts with vacations
	 * @param date the appointment date
	 * @param startTime the appointment start time
	 * @param endTime the appointment end time
	 * @return true if conflicts
	 */
	private static boolean appointmentConflictsWithVacations(Date date, Time startTime, Time endTime) {
		List<TimeSlot> vacationSlots = CarShopApplication.getCarShop().getBusiness().getVacations();
		
		for(TimeSlot timeSlot : vacationSlots) {
			if(inDateTimeRange(date, startTime, endTime, timeSlot.getStartDate(), timeSlot.getStartTime(), timeSlot.getEndDate(), timeSlot.getEndTime())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if the appointment time conflicts with holidays
	 * @param date the appointment date
	 * @param startTime the appointment start time
	 * @param endTime the appointment end time
	 * @return true if conflicts
	 */
	private static boolean appointmentConflictsWithHolidays(Date date, Time startTime, Time endTime) {
		List<TimeSlot> holidaySlots = CarShopApplication.getCarShop().getBusiness().getHolidays();
		
		for(TimeSlot timeSlot : holidaySlots) {
			if(inDateTimeRange(date, startTime, endTime, timeSlot.getStartDate(), timeSlot.getStartTime(), timeSlot.getEndDate(), timeSlot.getEndTime())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * method to check if the combo times conflict with existing appointments
	 * @param appointment the appointment
	 * @param services the list of services
	 * @param date the date
	 * @param startTimes the list of start times
	 * @param endTimes the list of end times
	 * @param excludeSelf set true if we want to exclude this appointment
	 * @return true if conflicts
	 */
	private static boolean comboTimeConflictsWithAppointments(Appointment appointment, List<Service> services, Date date, List<Time> startTimes, List<Time> endTimes, boolean excludeSelf) {
		for(int i = 0; i < services.size(); i++) {
			Service service = services.get(i);
			if (appointmentConflitsWithAppointments(appointment, service, date, startTimes.get(i), endTimes.get(i), excludeSelf)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * helper method to parse all the start times of a service combo
	 * @param string the start times
	 * @return a list of sql.Time
	 * @throws InvalidInputException if input is invalid
	 */
	public static List<Time> parseComboTimes(String string) throws InvalidInputException {
		List<Time> startTimes = new ArrayList<Time>();
		String[] strings = string.split(",");
		for(String s : strings) {
			startTimes.add(stringToTime(s));
		}
				
		return startTimes;
	}
	
	/**
	 * checks if combo items conflict with each other
	 * @param startTimes the start times of the items
	 * @param endTimes the end times of the items
	 * @return true if conflicts
	 */
	private static boolean comboItemsConflicts(List<Time> startTimes, List<Time> endTimes) {
		for(int i = 0; i < startTimes.size(); i++) {
			for (int j = 0; j < startTimes.size(); j++) {
				if(i != j && isTimeOverlap(startTimes.get(i), endTimes.get(i), startTimes.get(j), endTimes.get(j))) {
					return true;
				}
				
			}
		}
		return false;
	}
	
	/**
	 * checks if combo items are outside business hours
	 * @param services the services
	 * @param date the date
	 * @param startTimes the start times
	 * @param endTimes the end times
	 * @return true if conflicts
	 */
	private static boolean comboTimesIsOutsideBusinessHours(List<Service> services, Date date, List<Time> startTimes, List<Time> endTimes) {
		for (int i = 0; i < services.size(); i++) {
			Service service = services.get(i);
			Time startTime = startTimes.get(i);
			Time endTime = endTimes.get(i);
			
			if (timeIsOutsideBusinessHours(service, date, startTime, endTime)) {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * checks if combo items conflict with vacations
	 * @param services the services
	 * @param date the date
	 * @param startTimes the start times
	 * @param endTimes the end times
	 * @return true if conflicts
	 */
	private static boolean comboTimesConflictsWithVacations(Date date, List<Time> startTimes, List<Time> endTimes) {
		for(int i = 0; i < startTimes.size(); i++) {
			if (appointmentConflictsWithVacations(date, startTimes.get(i), endTimes.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if combo items conflict with holidays
	 * @param services the services
	 * @param date the date
	 * @param startTimes the start times
	 * @param endTimes the end times
	 * @return true if conflicts
	 */
	private static boolean comboTimesConflictsWithHolidays(Date date, List<Time> startTimes, List<Time> endTimes) {
		for(int i = 0; i < startTimes.size(); i++) {
			if (appointmentConflictsWithHolidays(date, startTimes.get(i), endTimes.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method compares input date inclusively to date range denoted by start date and end date, inputs cannot be null
	 * uses the other method
	 * @param inputDate
	 * @param inputStartTime 
	 * @param inputEndTime
	 * @param startDate
	 * @param startTime - time that range starts at on start day
	 * @param endDate
	 * @param endTime - time that range ends on end day
	 * @return
	 */
	private static boolean inDateTimeRange(Date inputDate, Time inputStartTime, Time inputEndTime, Date startDate, Time startTime, Date endDate, Time endTime){
		return inDateTimeRange(inputDate, inputStartTime, inputDate, inputEndTime, startDate, startTime, endDate, endTime);
	}
	
	/**
	 * Method compares input date inclusively to date range denoted by start date and end date, inputs cannot be null
	 * @param inputDate
	 * @param inputStartTime 
	 * @param inputEndTime
	 * @param startDate
	 * @param startTime - time that range starts at on start day
	 * @param endDate
	 * @param endTime - time that range ends on end day
	 * @return
	 */
	private static boolean inDateTimeRange(Date inputStartDate, Time inputStartTime, Date inputEndDate,Time inputEndTime, Date startDate, Time startTime, Date endDate, Time endTime){
		LocalTime inputStartTimeLocal = inputStartTime.toLocalTime();
		LocalTime inputEndTimeLocal = inputEndTime.toLocalTime();
		LocalDate inputStartDateLocal = inputStartDate.toLocalDate();
		LocalDate inputEndDateLocal = inputStartDate.toLocalDate();
		LocalDateTime inputStartDateTime = LocalDateTime.of(inputStartDateLocal, inputStartTimeLocal);
		LocalDateTime inputEndDateTime = LocalDateTime.of(inputEndDateLocal, inputEndTimeLocal);
		
		LocalTime startTimeLocal = startTime.toLocalTime();
		LocalTime endTimeLocal = endTime.toLocalTime();
		LocalDate startDateLocal = startDate.toLocalDate();
		LocalDate endDateLocal = endDate.toLocalDate();
		LocalDateTime startDateTime = LocalDateTime.of(startDateLocal, startTimeLocal);
		LocalDateTime endDateTime = LocalDateTime.of(endDateLocal, endTimeLocal);
		
			
		if ((startDateTime.isBefore(inputStartDateTime) && endDateTime.isAfter(inputEndDateTime))
				|| (inputStartDateTime.isBefore(startDateTime) && inputEndDateTime.isAfter(endDateTime))
				|| (inputStartDateTime.isBefore(startDateTime) && startDateTime.isBefore(inputEndDateTime))
				|| (inputStartDateTime.isBefore(endDateTime) && endDateTime.isBefore(inputEndDateTime))
				|| (inputStartDateTime.equals(startDateTime) && inputEndDateTime.equals(endDateTime))
				) {
			return true;
		}
		return false;
	}
	
	/**
	 * method to find appointment
	 * @param toAppointmentment
	 */
	public static Appointment findAppointment(TOAppointment toAppointment) throws InvalidInputException{
		List<Appointment> appointments = CarShopApplication.getCarShop().getAppointments();
		Appointment appointment = null;
		for(Appointment appt: appointments) {
			if (appt.getCustomer().getUsername().equals(toAppointment.getCustomerName())
					&& appt.getBookableService().getName().equals(toAppointment.getServiceName())
					&& appt.getServiceBooking(0).getTimeSlot().getStartDate().equals(toAppointment.getDate())
					&& appt.getServiceBooking(0).getTimeSlot().getStartTime().equals(toAppointment.getStartTime())) {
				appointment = appt;
				break;
			}
			
		}
		if (appointment == null) {
			throw new InvalidInputException("Can't find appointment");
		}
		
		return appointment;
		
	}
	
	
	/** ** END APPOINTMENT MANAGEMENT ** **/
	
	/**
	 * method get the owner of appointment
	 */
	public static List<TOAppointment> getAppointmentsOwner() {
		CarShop carShop = CarShopApplication.getCarShop();
		List<Appointment> appointments = carShop.getAppointments();
		List<TOAppointment> toAppointments = new ArrayList<>();
		
		for (Appointment appointment : appointments) {
			String customerName = appointment.getCustomer().getUsername();
			String serviceName = appointment.getBookableService().getName();
			
			Date date = null;
			Time time = null;
			if (appointment.getServiceBookings().size() > 0) {
				date = appointment.getServiceBooking(0).getTimeSlot().getStartDate();
				time = appointment.getServiceBooking(0).getTimeSlot().getStartTime();
			}
			
			List<ServiceBooking> serviceBookings = appointment.getServiceBookings();
			List<TOServiceBooking> toServiceBookings = new ArrayList<>();
			
			for (ServiceBooking serviceBooking : serviceBookings) {
				String name = serviceBooking.getService().getName();
				int duration = serviceBooking.getService().getDuration();
				String garage = serviceBooking.getService().getGarage().getTechnician().getType().name();
				
				TOGarage toGarage = new TOGarage(garage);
				TOService toService = new TOService(name, duration, toGarage);
				
				Date startDate = serviceBooking.getTimeSlot().getStartDate();
				Date endDate = serviceBooking.getTimeSlot().getEndDate();
				Time startTime = serviceBooking.getTimeSlot().getStartTime();
				Time endTime = serviceBooking.getTimeSlot().getEndTime();
				
				TOTimeSlot toTimeSlot = new TOTimeSlot(startDate, startTime, endDate, endTime);
				
				TOServiceBooking toServiceBooking = new TOServiceBooking(toService, toTimeSlot);
				toServiceBookings.add(toServiceBooking);
			}
			String status = appointment.getAppointmentStatusFullName();
			
			TOAppointment toAppointment = new TOAppointment(customerName, serviceName, date, time, status, toServiceBookings);
			toAppointments.add(toAppointment);
		}
		
		return toAppointments;
	}
	

	public static String getBusinessName() throws Exception {
		String name = "";
		try {
			CarShopApplication.getCarShop().getBusiness().getName();
		} catch (Exception e) {
			throw new Exception("Business does not exist yet!");
		}
		name = CarShopApplication.getCarShop().getBusiness().getName();
		return name;
	}
	
	public static String getBusinessEmail() throws Exception {
		String email = "";
		try {
			CarShopApplication.getCarShop().getBusiness().getEmail();		
		} catch (NullPointerException e) {
			throw new Exception("Business does not exist yet!");
		}
		
		email = CarShopApplication.getCarShop().getBusiness().getEmail();
		return email;
	}
	
	public static String getBusinessPhone() throws Exception {
		String phone = "";
		try {
			CarShopApplication.getCarShop().getBusiness().getPhoneNumber();
		} catch (NullPointerException e) {
			throw new Exception("Business does not exist yet!");
		}
		phone = CarShopApplication.getCarShop().getBusiness().getPhoneNumber();
		return phone;
	}
	
	public static String getBusinessAddress() throws Exception {
		
		String address = "";
		try {
		CarShopApplication.getCarShop().getBusiness().getAddress();
		} catch (NullPointerException e) {
			throw new Exception("Business does not exist yet!");
		}
		address = CarShopApplication.getCarShop().getBusiness().getAddress();
		return address;
	}

	@SuppressWarnings("static-access")
	/**
	 * method to get the start time with services
	 * @param apptName as name of appointment
	 * @param optServices as optional services
	 * @param startTime as starting time
	 */
	public static String getStartTimesWithServices(String apptName, String optServices, String startTime) throws InvalidInputException {
		try {	// if it's a service then don't mind it
			CarShopApplication.getCarShop().getBookableService(0).getWithName(apptName).getMainService();
		} catch(Exception e) {
			return startTime;
		}
		String toReturn = "";
		String[] toCheck = optServices.split(",");
		ArrayList<String> startTimes = new ArrayList<>();
		startTimes.add(startTime);
		ServiceCombo sb = CarShopApplication.getCarShop().getBookableService(0).getWithName(apptName).getMainService().getServiceCombo();
		for(int i = 0; i < sb.getServices().size(); i++) {	// add all mandatory services first
			ComboItem ci = sb.getService(i);
			if(ci.getMandatory()==true) {
				String tempStartTime = startTimes.get(startTimes.size()-1);
				Time startTime1 = stringToTime(tempStartTime);
				int duration = ci.getService().getDuration();
				int minutes = duration%60;
				int hours = duration/60;
					
				LocalTime localtime = startTime1.toLocalTime();
				localtime = localtime.plusMinutes(minutes+10);
				localtime = localtime.plusHours(hours);
				Time endTime = Time.valueOf(localtime);
				String endTimeString = endTime.toString().substring(0, 5);
				startTimes.add(endTimeString);
			}
		}
		for(int i = 0; i < toCheck.length; i++) {
			String toExamine = toCheck[i];
			String tempStartTime = startTimes.get(startTimes.size()-1);
			Time startTime1 = stringToTime(tempStartTime);
			int duration = CarShopApplication.getCarShop().getBookableService(0).getWithName(toExamine).getDuration();
			int minutes = duration%60;
			int hours = duration/60;
				
			LocalTime localtime = startTime1.toLocalTime();
			localtime = localtime.plusMinutes(minutes+10);
			localtime = localtime.plusHours(hours);
			Time endTime = Time.valueOf(localtime);
			String endTimeString = endTime.toString().substring(0, 5);
			startTimes.add(endTimeString);
		}
		for(int i = 0; i < startTimes.size()-1; i++) {
			toReturn+=startTimes.get(i)+",";
		}
		return toReturn;
	}
	/**
	 * method update service of appointment
	 * @param username
	 * @param prevServName as previous service name
	 * @param serviceName as new service name
	 */
	public static void updateAppointmentCase1(String username, TOAppointment prevServName, String serviceName,
			String timeOfChange) throws InvalidInputException {
		Appointment toPassIn = findAppointment(prevServName);
		changeServiceAt(toPassIn, username, serviceName, timeOfChange);
	}
	/**
	 * method to update appointment
	 * @param username
	 * @param prevTOappt
	 * @param dateString
	 * @param time
	 */
	public static void updateAppointmentCase2(String username, TOAppointment prevTOAppt, String dateString, String time,
			String timeOfChange) throws InvalidInputException {
		Appointment toPassIn = findAppointment(prevTOAppt);
		CarShopController.updateDateAndTimeAt(toPassIn, username, dateString, time, timeOfChange);
		
	}
	/**
	 * method to update appointment
	 * @param username
	 * @param prevTOappt
	 * @param optServices as optional services
	 */
	public static void updateAppointmentCase3(String username, TOAppointment prevTOAppt, String optServices,
			String time, String timeOfChange) throws InvalidInputException {
		Appointment toPassIn = findAppointment(prevTOAppt);
		CarShopController.addOptServiceAt(toPassIn, username, optServices, time, timeOfChange);
		
	}
	/**
	 * method to cancel appointment
	 * @param prevTOAppt
	 * @param customer
	 * @param currentDateAndTime as current date and time
	 */
	public static void cancelAppointmentCase1(TOAppointment prevTOAppt, String customer, String currentDateAndTime) throws InvalidInputException {
		Appointment toPassIn = findAppointment(prevTOAppt);
		CarShopController.cancelAppointmentAt(toPassIn, customer, currentDateAndTime);
	}
	
	
	/**
	 * method to set password for technician
	 * @param username
	 * @param password
	 * @param cs
	 */
	public static void setTechnicianPassword(String username, String password, CarShop cs) throws InvalidInputException {
		Technician technician = findTechnician(username, cs);
		technician.setPassword(password);
		//persistence
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * method to get the list of business hours
	 */
	public static List<TOBusinessHour> getGarageTOBusinessHours() {
		String username = CarShopApplication.getUser().getUsername();
		Technician technician = findTechnician(username, CarShopApplication.getCarShop());
		List<BusinessHour> businessHours = technician.getGarage().getBusinessHours();
		List<TOBusinessHour> toBusinessHours = new ArrayList<>();
		for (BusinessHour businessHour : businessHours) {
			TOBusinessHour toBusinessHour = new TOBusinessHour(businessHour.getDayOfWeek().name(),
					businessHour.getStartTime(), businessHour.getEndTime());
			toBusinessHours.add(toBusinessHour);
		}		
		return toBusinessHours;
	}
	
	//returns the transfer objects for every appointment in a technician's garage
	//the technician must be logged in for this to work
	/**
	 * method to get the transfer objects for every appointment in technician's garage
	 */
	public static List<TOAppointment> getGarageAppointments(){
		String username = CarShopApplication.getUser().getUsername();
		CarShop cs = CarShopApplication.getCarShop();
		Technician technician = findTechnician(username, cs);
		Garage technicianGarage = technician.getGarage();
		List<Appointment> csAppointments = cs.getAppointments();
		List<TOAppointment> toGarageAppointments = new ArrayList<>();
		
		
		for (Appointment appointment : csAppointments) {
			String customerName = appointment.getCustomer().getUsername();
			
			List<ServiceBooking> serviceBookings = appointment.getServiceBookings();
			List<TOServiceBooking> toServiceBookings = new ArrayList<>();
			
			for (ServiceBooking serviceBooking : serviceBookings) {
				String name = serviceBooking.getService().getName();
				int duration = serviceBooking.getService().getDuration();
				Garage garage = serviceBooking.getService().getGarage();
				String garageName = garage.getTechnician().getType().name();
				if(garage.equals(technicianGarage)) {
					
					TOGarage toGarage = new TOGarage(garageName);
					TOService toService = new TOService(name, duration, toGarage);
					
					Date startDate = serviceBooking.getTimeSlot().getStartDate();
					Date endDate = serviceBooking.getTimeSlot().getEndDate();
					Time startTime = serviceBooking.getTimeSlot().getStartTime();
					Time endTime = serviceBooking.getTimeSlot().getEndTime();
					String specificServiceName = serviceBooking.getService().getName();
					
					TOTimeSlot toTimeSlot = new TOTimeSlot(startDate, startTime, endDate, endTime);
					
					TOServiceBooking toServiceBooking = new TOServiceBooking(toService, toTimeSlot);
					toServiceBookings.add(toServiceBooking);
					
					String status = appointment.getAppointmentStatusFullName();
					
					TOAppointment toAppointment = new TOAppointment(customerName, specificServiceName, startDate, startTime, status, toServiceBookings);
					toGarageAppointments.add(toAppointment);
				}
			}
		}
		return toGarageAppointments;
	}
	/**
	 * method to set the garage business hours
	 */
	//this method does not work yet!
	public static void setGarageBusinessHours(String day, String newStart, String newEnd, String oldStart, String oldEnd, CarShop cs) throws InvalidInputException {
		Technician technician = findTechnician(CarShopApplication.getCurrentUser(), cs);
		Garage garage = technician.getGarage();
		DayOfWeek weekday = null;
		if(day.equals("Monday")) {
			weekday = DayOfWeek.Monday;
		}
		if(day.equals("Tuesday")) {
			weekday = DayOfWeek.Tuesday;
		}
		if(day.equals("Wednesday")) {
			weekday = DayOfWeek.Wednesday;
		}
		if(day.equals("Thursday")) {
			weekday = DayOfWeek.Thursday;
		}
		if(day.equals("Friday")) {
			weekday = DayOfWeek.Friday;
		}
		//need to remove the old business hour, but this is becoming a bit of an issue
		//i think this works correctly, but unsure...
		Time newGarageStart = stringToTime(newStart);
		Time newGarageEnd = stringToTime(newEnd);
		BusinessHour newBusinessHour = new BusinessHour(weekday, newGarageStart, newGarageEnd, cs);
		List<BusinessHour> garageHours = garage.getBusinessHours();
		for(int i=0; i< garageHours.size();i++) {
			if(garageHours.get(i).getStartTime().toString().equals(oldStart) && garageHours.get(i).getEndTime().toString().equals(oldStart)) {
				garage.removeBusinessHour(garageHours.get(i));
				garage.addOrMoveBusinessHourAt(newBusinessHour, i);
			}
		}
//		BusinessHour newBusinessHour = new BusinessHour(weekday, stringToTime(start), stringToTime(end), cs);
//		garage.addBusinessHour(newBusinessHour);
		try {
			CarShopPersistence.save(cs);
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	

	/**
	 * method to get list of services
	 */
	public static List<TOCarshopService> getCarshopServices() {
		List<TOCarshopService> toReturn = new ArrayList<>();
		CarShop carShop = CarShopApplication.getCarShop();
		
		List<BookableService> bookableServices = carShop.getBookableServices();
		
		for(BookableService bookableService : bookableServices) {
			if (bookableService instanceof Service) {
				Service service = (Service) bookableService;
				String serviceName = service.getName();
				String garage = service.getGarage().getTechnician().getType().name();
				int duration = service.getDuration();
				
				TOCarshopService toCarshopService = new TOCarshopService(serviceName, garage, duration);
				toReturn.add(toCarshopService);
			}
		}
		return toReturn;
	}
	
	public static List<TOCarshopCombo> getCarshopServiceCombos() {
		List<TOCarshopCombo> toReturn = new ArrayList<>();
		CarShop carShop = CarShopApplication.getCarShop();
		
		List<BookableService> bookableServices = carShop.getBookableServices();
		//checks through all bookable services
		for(BookableService bookableService : bookableServices) {
			if (bookableService instanceof ServiceCombo) {
				ServiceCombo serviceCombo = (ServiceCombo) bookableService;
				String comboName = serviceCombo.getName();
				String mainService = serviceCombo.getMainService().getService().getName();
				List<String> services = new ArrayList<>();
				List<String> garages = new ArrayList<>();
				List<Integer> durations = new ArrayList<>();
				List<Boolean> mandatory = new ArrayList<>();
				
				for(ComboItem item : serviceCombo.getServices()) {
					services.add(item.getService().getName());
					garages.add(item.getService().getGarage().getTechnician().getType().name());
					durations.add(item.getService().getDuration());
					mandatory.add(item.getMandatory());
				}
				
				TOCarshopCombo toCarshopCombo = new TOCarshopCombo(comboName, mainService, services, garages, durations, mandatory);
				toReturn.add(toCarshopCombo);
			}
		}
		return toReturn;
	}
	/**
	 * method to change the garage business hours
	 */
	public static void changeGarageBusinessHour(String day, String startTime, String endTime, String type, CarShop cs) throws InvalidInputException {


		DayOfWeek dayOfWeek = CarShopController.getWeekDay(day);
		int toCheck = 0;
		if(cs.getBusiness()==null) throw new InvalidInputException("No business has been created yet!");
		List<BusinessHour> businessHours = cs.getBusiness().getBusinessHours();
		boolean found = false;
		for(int i=0; i<businessHours.size();i++) {
			BusinessHour bh = businessHours.get(i);
			if(bh.getDayOfWeek().toString().equals(day)) {
				found = true;
				if(bh.getEndTime().before(stringToTime(endTime))) {
					throw new IllegalArgumentException("Garage opening hours must be within weekly business hours");
				}
				if(bh.getStartTime().after(stringToTime(startTime))) {
					throw new IllegalArgumentException("Garage opening hours must be within weekly business hours");
				}
				if(stringToTime(startTime).after(stringToTime(endTime))){
					throw new IllegalArgumentException("Opening time must be before end time");
				}
				
				//need to check if the hours im trying to set are vacation days, maybe....
				if(dayOfWeek.equals(BusinessHour.DayOfWeek.Monday)) toCheck = 0; 
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Tuesday)) toCheck = 1;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Wednesday)) toCheck = 2;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Thursday)) toCheck = 3;
				else if(dayOfWeek.equals(BusinessHour.DayOfWeek.Friday)) toCheck = 4;
				else throw new InvalidInputException("The opening hours are not within the opening hours of the business");
				// converts from string to time with method in the controller
				Time ourStartTime = CarShopController.stringToTime(startTime);
				Time ourEndTime = CarShopController.stringToTime(endTime);
				Technician technician = CarShopController.findTechnician(type, cs);		
				// "The opening hours cannot overlap"
				Garage garage = technician.getGarage();
				if(garage.getBusinessHours().size()==0) {
					BusinessHour bh1 = new BusinessHour(DayOfWeek.Monday, null, null, cs);
					garage.addBusinessHour(bh1);
					
					BusinessHour bh2 = new BusinessHour(DayOfWeek.Tuesday, null, null, cs);
					garage.addBusinessHour(bh2);
					
					BusinessHour bh3 = new BusinessHour(DayOfWeek.Wednesday, null, null, cs);
					garage.addBusinessHour(bh3);
					
					BusinessHour bh4 = new BusinessHour(DayOfWeek.Thursday, null, null, cs);
					garage.addBusinessHour(bh4);
					
					BusinessHour bh5 = new BusinessHour(DayOfWeek.Friday, null, null, cs);
					garage.addBusinessHour(bh5);		
				}

				garage.getBusinessHour(toCheck).setStartTime(ourStartTime);
				//persistence
				try {
					CarShopPersistence.save(cs);
				}catch(RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
				garage.getBusinessHour(toCheck).setEndTime(ourEndTime);
				//persistence
				try {
					CarShopPersistence.save(cs);
				}catch(RuntimeException e) {
					throw new InvalidInputException(e.getMessage());
				}
				break;				
			}
		}
		if(found == false) throw new InvalidInputException("Garage opening hours must be within weekly business hours");

	}
	/**
	 * method to check the password
	 */
	public static boolean checkIfPasswordCorrect(String newUsername, String newPassword) throws InvalidInputException {
		Customer c = (Customer) Customer.getWithUsername(newUsername);
		if(c.getUsername().equals(newUsername) && !c.getPassword().equals(newPassword)) throw new InvalidInputException("Password is incorrect!");
		//password is equivalent
		return c.getPassword().equals(newPassword);
	}
	/**
	 * method to return the customer with only its name
	 * @param newUsername
	 */
	public static void getCustomerByUsername(String newUsername) throws InvalidInputException {
		try {
			Customer.getWithUsername(newUsername);
		} catch (Exception e) {
			throw new InvalidInputException("Username does not exist!");
		}
		//Persistence
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	/**
	 * method to remove the customer with only its name
	 * @param newUsername
	 */
	public static void deleteCustomerAccount(String newUsername) throws InvalidInputException {
		for(Customer c : CarShopApplication.getCarShop().getCustomers()) {
			if(c.getUsername().equals(newUsername)) {
				c.delete();
			}
		}
		//Persistence
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	/**
	 * method to update the password
	 * @param currentUser
	 * @param text
	 */
	public static void updatePassword(String currentUser, String text) throws InvalidInputException {
		Customer.getWithUsername(currentUser).setPassword(text);
		//Persistence
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	/**
	 * method to update the username
	 * @param currentUser
	 * @param text
	 */
	public static void updateUsername(String currentUser, String text) throws InvalidInputException {
		Customer.getWithUsername(currentUser).setUsername(text);
		//Persistence
		try {
			CarShopPersistence.save(CarShopApplication.getCarShop());
		}catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}


}
