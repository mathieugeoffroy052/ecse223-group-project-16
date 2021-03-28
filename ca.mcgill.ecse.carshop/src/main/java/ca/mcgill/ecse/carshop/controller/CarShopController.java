package ca.mcgill.ecse.carshop.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
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


public class CarShopController {
	
	public CarShopController() {
	}
	
	// TODO
	
	// creating appointment method
	@SuppressWarnings("static-access")
	public static void CreateAppointmentWithOptServices(String customer, String serviceComboName, String startTime, String startDate, CarShop cs, String optServices) throws Exception {
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

		//String[] services = service.split(" ");
		//BookableService serv = null;
		String[] optServicesToPutIn;
		if(!optServices.equals("")) {
			optServicesToPutIn = optServices.split(",");
			// puts the optional services in. Requires the service combo name to do so.
			putOptServicesIn(optServicesToPutIn, serviceComboName);
		}
		

		BookableService bookServ = getBookableService(serviceComboName);
		
		if(!checkHolidays(date)) throw new InvalidInputException("There are no available slots for " + serviceComboName + " on " + startDate + " at " + startTime);
		
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
				throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
			}
			if(!checkByGarageServiceBookings(cs, serv, startTime1, endTime, date)) {
				
				appointment.delete();
				throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
			}

			TimeSlot timeSlot1 = cs.addTimeSlot(date, startTime1, date, endTime);
			serv.addServiceBooking(timeSlot1, appointment);
			 
		} else {
			//if service is service combo
			List<ComboItem> comboItems = ((ServiceCombo) bookServ).getServices();
			//for(ComboItem ci: comboItems)
			for (int i = 0; i < comboItems.size() ; i++) {
				Time startTime1 = stringToTime(startTimes[i]);
				
				duration = comboItems.get(i).getService().getDuration();
				
				minutes = duration%60;
//				String minutes2 = String.format("%02d", minutes);
				hours = duration/60;
//				String hours2 = String.format("%02d", hours);
//				String time = hours2 + ":" + minutes2;
				
				
				LocalTime localtime = startTime1.toLocalTime();
				localtime = localtime.plusMinutes(minutes);
				localtime = localtime.plusHours(hours);
				Time endTime = Time.valueOf(localtime);
				if(!checkInGarageBusinessHours(cs, startTime1, endTime, date, comboItems.get(i).getService().getGarage())) {

					appointment.delete();
					throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
				}
//				if(!checkTimeAvailable(cs, startTime1, endTime, date)) {
////					cs.removeAppointment(appointment);
//					
//					appointment.delete();
//					throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
//				}
				if(!checkByGarageServiceBookings(cs, comboItems.get(i).getService(), startTime1, endTime, date)) {
					
					appointment.delete();
					throw new InvalidInputException("There are no available slots for " +  serviceComboName + " on " + startDate + " at " + startTime);
				}
				if(startTimes.length > (i+1) && endTime.after(stringToTime(startTimes[i+1]))) {
					appointment.delete();
					throw new InvalidInputException("Time slots for two services are overlapping");
				}
				TimeSlot timeSlot1 = cs.addTimeSlot(date, startTime1, date, endTime);
				comboItems.get(i).getService().addServiceBooking(timeSlot1, appointment);
			}
		}
	}
	
	private static boolean checkByGarageServiceBookings(CarShop cs, Service serv, Time startTime1, Time endTime, Date date) {
		// TODO Auto-generated method stub
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
	private static boolean putOptServicesIn(String[] optServicesToPutIn, String serviceComboName) {
		// TODO Auto-generated method stub
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
					found = true;
					break; // don't add the optional service (it's already there)
				}
			}
			if(!found) {mainServiceInServiceCombo.getServiceCombo().addService(true, toPut);
				toReturn = true;
			}
			// is automatically assigned a value of true
		}
		return toReturn;
	}

	private static void checkIfDateWorksWithCurrentTime(Date date, String time) throws Exception {
		// TODO Auto-generated method stub
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
			if (bhour.getDayOfWeek().toString().toLowerCase().equals(DayOfWeek.toLowerCase())) {
				if ((endTime.before(bhour.getEndTime()) || endTime.equals(bhour.getEndTime())) && 
						(startTime.after(bhour.getStartTime())  || startTime.equals(bhour.getStartTime()))) {
					
					return true;
				}
			}
		}
		return false;
	}
	
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
		Boolean gotAppointment = false;
		Time stTime = stringToTime(time);
		try {
			// checking if the date is before the current date,
			checkIfDateWorksWithCurrentTime(date, time);

		} catch(Exception e) {
			throw new InvalidInputException("Cannot cancel an appointment on the appointment date");
		}
//		BookableService serve = null;
		Customer customer = (Customer) User.getWithUsername(custName);
//		List<BookableService> bookableService = cs.getBookableServices();
//		for (BookableService bs: bookableService) {
//			if (bs.getName().equals(name)) {
//				serve = bs; 
//			}
//		}
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
	}
	//method that creates hours for business
	public static void createBusinessHour(String day, String startTime, String endTime) throws InvalidInputException {
		//checks if it's owner entering the information
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
	}

	//method to create holidays
	public static void createHoliday(String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
		//checks if it's owner entering the information
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
	}
	//method to create vacations
	public static void createVacation(String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
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
	}
	
	//method that creates time slots
	public static void createTimeSlot(String type, String startDate, String startTime, String endDate, String endTime)
			throws InvalidInputException {
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
			throw new InvalidInputException("Set up a business first");
		}
		//checks if the inputs are null or empty
		if (nameString == null || nameString.length() == 0 || address == null || address.length() == 0
				|| phoneNumber == null || phoneNumber.length() == 0 || emailAddress == null
				|| emailAddress.length() == 0) {
			throw new InvalidInputException("Empty fields");
		}
		//checks if the email is in a proper format
		isProperEmailAddress(emailAddress);
		try {
			business.setName(nameString);
			business.setAddress(address);
			business.setPhoneNumber(phoneNumber);
			business.setEmail(emailAddress);
		} catch (Exception e) {
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

	}
	// TODO:
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

	}

	//returning the business hours
	public static List<TOBusinessHour> getBusinessHours() {
		Business business = CarShopApplication.getCarShop().getBusiness();
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
	public static List<TOTimeSlot> getHolidays() {
		Business business = CarShopApplication.getCarShop().getBusiness();
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
	public static List<TOTimeSlot> getVacations() {
		Business business = CarShopApplication.getCarShop().getBusiness();
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
	}
	//making sure that the start time is before the end time
	private static boolean startTimeBeforeEndTime(Time startTime, Time endTime) {
		return startTime.before(endTime);
	}

	//making sure that the start date is before the end date
	private static boolean startDateBeforeEndDate(Date startDate, Date endDate) {
		return startDate.before(endDate);
	}

	// done, check if business hour conflicts
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

	// done, verifies if two string representing dayofweeks are the same
	private static boolean isSameDay(String day1, String day2) {
		return day1.equalsIgnoreCase(day2);
	}

	// done, check if vacation and holiday conflict
	private static boolean isVacationAndHolidayConflict(String type, TimeSlot timeSlot, TimeSlot exception)
			throws InvalidInputException {
		Business business = CarShopApplication.getCarShop().getBusiness();
		// check if conflicts with vacations
		for (TimeSlot t : business.getVacations()) {
			if (timeSlot != t && exception != t
					&& strictlyOverlap(timeSlot.getStartDate(), timeSlot.getEndDate(), t.getStartDate(), t.getEndDate())
					|| isDateAndTimeOverLap(timeSlot.getStartDate(), timeSlot.getEndDate(), t.getStartDate(),
							t.getEndDate(), timeSlot.getStartTime(), timeSlot.getEndTime(), t.getStartTime(),
							t.getEndTime())) {
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
					&& strictlyOverlap(timeSlot.getStartDate(), timeSlot.getEndDate(), t.getStartDate(), t.getEndDate())
					|| isDateAndTimeOverLap(timeSlot.getStartDate(), timeSlot.getEndDate(), t.getStartDate(),
							t.getEndDate(), timeSlot.getStartTime(), timeSlot.getEndTime(), t.getStartTime(),
							t.getEndTime())) {
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
	//check if date and time is overlapping
	private static boolean isDateAndTimeOverLap(Date startDate1, Date endDate1, Date startDate2, Date endDate2,
			Time start1, Time end1, Time start2, Time end2) {
		return startDate1.compareTo(startDate2) == 0 && (isTimeOverlap(start1, end1, start2, end2))
				|| endDate1.compareTo(endDate2) == 0 && (isTimeOverlap(start1, end1, start2, end2))
				|| (startDate1.compareTo(endDate2) == 0 && start1.before(end2))
				|| (startDate2.compareTo(endDate1) == 0 && start2.before(end1));
	}

	// overlap by more than a day
	private static boolean strictlyOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
		return (startDate1.before(endDate2) && startDate2.before(endDate1));
	}

	private static boolean barelyOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
		return (startDate1.compareTo(startDate2) == 0 || endDate1.compareTo(endDate2) == 0
				|| startDate1.compareTo(endDate2) == 0 || startDate2.compareTo(endDate1) == 0);
	}

	private static boolean isDateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
		return (startDate1.before(endDate2) && startDate2.before(endDate1))
				|| (startDate1.compareTo(startDate2) == 0 || (endDate1.compareTo(endDate2) == 0
						|| startDate1.compareTo(endDate2) == 0 || startDate2.compareTo(endDate1) == 0));
	}

	private static boolean isTimeOverlap(Time start1, Time end1, Time start2, Time end2) {
		return (start1.before(end2) && start2.before(end1));
	}

//	private static boolean isProperBusinessName(String name) {
//		return false;
//	}
//
//	private static boolean isProperAddress(String address) {
//		return false;
//	}
//
//	private static boolean isProperPhoneNumer(String phoneNumer) {
//		return false;
//	}

	// TODO: need other cases
	private static boolean isProperEmailAddress(String emailAddress) throws InvalidInputException {
		if (!emailAddress.contains("@") || !emailAddress.contains(".")
				|| emailAddress.indexOf("@") > emailAddress.indexOf("@")) {
			throw new InvalidInputException("Invalid email");
		}
		return true;
	}

//	private static boolean isValidBusinessHour(BusinessHour businessHour) {
//		return false;
//	}
//
//	private static boolean isValidTimeSlot(TimeSlot timeSlot) {
//		return false;
//	}

	// done, converts a String to a dayofweek
	private static DayOfWeek convertDayOfWeek(String day) throws InvalidInputException {
		try {
			return DayOfWeek.valueOf(day);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	// done, verifies that the current user is the owner;
	private static boolean hasAuthorization() {
		return CarShopApplication.getCurrentUser().equals("owner");
	}

//	private static BusinessHour findBusinessHour(DayOfWeek day, Time starTime, Time endTime) {
//		CarShop carShop = CarShopApplication.getCarShop();
//		BusinessHour foundBusinessHour = null;
//		for(BusinessHour businessHour : carShop.)
//	}

	/** ** END HONG YI ** **/ 
	
	// TODO Kalvin
	
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
		
		
		
	}
	
	// findTechnician by Matthew
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
	
	// by Matthew
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
		if(technicianType!=null) {
//			Technician technician = cs.getTechnician(getTechnician(username, cs));
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
	// login a customer
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
	// loging a technician
	public static void technicianLogin(String username, String password) throws InvalidInputException {
		User user = User.getWithUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			CarShopApplication.setUser(user);
			CarShopApplication.setAccountType(getApplicationTechnicianType(user.getUsername()));
			CarShopApplication.setLoggedIn(true);
		}else {
			throw new InvalidInputException("Username/password not found");
		}
	}
	//login owner
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
	
	//decides which login to use based on username
	public static void login(String username, String password) throws InvalidInputException {
		if(username.equals("owner")) {
			ownerLogin(username, password);
		}else if(username.contains("Technician")) {
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
	
	//sets garages
	public static void setGarage(Technician t, CarShop cs) {
		t.setGarage(new Garage(cs, t));
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
		else if(day.equals("Saturday") || day.equals("Sunday")) {
			throw new InvalidInputException("The opening hours are not within the opening hours of the business");
		}
		return dayOfWeek;
	}
	//method to convert strings to time
	public static Time stringToTimeMatthew(String time) {
		String[] timeArr1 = time.split(":");
		Time finalTime = new Time (Integer.parseInt(timeArr1[0]), Integer.parseInt(timeArr1[1]), 0);
		return finalTime;
	}
	
	

	// define service
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

    // done converts a sql.Date to a string
    private static String dateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    // done converts a sql.Time to a string
    private static String timeToString(Time time) {
        String pattern = "HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(time);
    }
	//defines all the service combos
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
	//method to parse strings
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
	
	// END JERRY
	
}
