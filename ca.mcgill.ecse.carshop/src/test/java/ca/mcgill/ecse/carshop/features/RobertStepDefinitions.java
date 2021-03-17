package ca.mcgill.ecse.carshop.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import ca.mcgill.ecse.carshop.application.*;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.model.*;
import ca.mcgill.ecse.carshop.model.CarShop.*;
import ca.mcgill.ecse.carshop.model.BusinessHour.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RobertStepDefinitions {

	// make appointment
	private LocalDate systemDate;
	private LocalDate systemTime;
	private Owner owner1;
	private String error;
	private int errorCntr;
	private CarShop cs;
	private Business business1;	
	private int errorCounter;	
	
	
	@Given("all garages has the following opening hours")
	public void all_garages_has_the_following_opening_hours(io.cucumber.datatable.DataTable dataTable) throws ParseException {
		DateFormat timeFormat = new SimpleDateFormat("h:mm");
		//Date Start = dateFormat.parse(startTime);
		//Date End = dateFormat.parse(endTime);
		cs = CarShopApplication.getCarShop();
		List<Garage> garages = cs.getGarages();
		List<Map<String,String>> rows = dataTable.asMaps(String.class,String.class);
		
		for (Garage garage : garages) {
			for (Map<String,String> columns : rows) {
				BusinessHour.DayOfWeek day = BusinessHour.DayOfWeek.valueOf(columns.get("day")); //columns.get("day");
				Time start = (Time) timeFormat.parse(columns.get("startTime"));
				Time end = (Time) timeFormat.parse(columns.get("endtime"));
				garage.addBusinessHour(new BusinessHour(day, start, end, cs));
			}	
		}

	}

	// TODO
	@Given("the business has the following holidays")
	public void the_business_has_the_following_holidays(io.cucumber.datatable.DataTable dataTable) throws ParseException, InvalidInputException {
		cs = CarShopApplication.getCarShop();
		List<Map<String,String>> rows = dataTable.asMaps(String.class,String.class);
		for (Map<String,String> columns : rows) {
//			BusinessHour.DayOfWeek day = BusinessHour.DayOfWeek.valueOf(columns.get("day")); //columns.get("day");
			Date startDate = stringtoDate(columns.get("startDate"));
			Date endDate = stringtoDate(columns.get("endDate"));
			Time startTime = stringToTime(columns.get("startTime"));
			Time endTime = stringToTime(columns.get("endtime"));
			//TimeSlot timeSlot = new TimeSlot(startDate,startTime,endDate,endTime, cs);
			cs.getBusiness().addHoliday(new TimeSlot(startDate,startTime,endDate,endTime, cs));
		}
				
	}
	
	private Time stringToTime(String string) throws InvalidInputException {
        String pattern = "hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Time(formatter.parse(string).getTime());
        } catch (Exception e) {
            throw new InvalidInputException("parsing error");
        }
    }

    // done, but might not work, converts a string to a sql.Date
    private Date stringtoDate(String string) throws InvalidInputException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Date(formatter.parse(string).getTime());
        } catch (Exception e) {
            throw new InvalidInputException("parsing error");
        }
    }

    // done converts a sql.Date to a string
    private String dateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    // done converts a sql.Time to a string
    private String timeToString(Time time) {
        String pattern = "hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(time);
    }

    // TODO
    @When("{string} schedules an appointment on {string} for {string} at {string}")
	public void schedules_an_appointment_on_for_at(String string, String date, String serviceName, String startTime) throws InvalidInputException, ParseException{
//		String endTime = null;
//	    CarShopController.CreateAppointment(string,serviceName,startTime,endTime,date);
//		// Write code here that turns the phrase above into concrete actions
	}
	
	// TODO
	@Then("{string} shall have a {string} appointment on {string} from {string} to {string}")
	public void shall_have_a_appointment_on_from_to(String string, String string2, String string3, String string4, String string5) {

	}

	
	// TODO
	@When("{string} schedules an appointment on {string} for {string} with {string} at {string}")
	public void schedules_an_appointment_on_for_with_at(String string, String string2, String string3, String string4, String string5) {
	    // Write code here that turns the phrase above into concrete actions
	}
	
	
	//Cancel appointment
	
	// TODO
//	@Given("the system's time and date is {string}")
//	public void the_system_s_time_and_date_is(String string) throws ParseException { //hmm hong yi did a better one?
//		DateTimeFormatter fmt = new DateTimeFormatterBuilder()
//			    // date and offset
//			    .append(DateTimeFormatter.ISO_OFFSET_DATE)
//			    // default values for hour and minute
//			    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
//			    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
//			    .toFormatter();
//			ZonedDateTime parsed = ZonedDateTime.parse(string, fmt); // 2018-04-19T00:00+02:00
//			Date toPut = Date.from(parsed.toInstant());
//		CarShopApplication.setDate(toPut);
//	}


	@Given("the business has the following opening hours")
	public void the_business_has_the_following_opening_hours(io.cucumber.datatable.DataTable dataTable) throws ParseException {
//		DateFormat dateFormat = new SimpleDateFormat("h:mm");
//		cs = CarShopApplication.getCarShop();
//		Business businesses = cs.getBusiness();
//		List<Map<String,String>> rows = dataTable.asMaps(String.class,String.class);
//
//			for (Map<String,String> columns : rows) {
//				BusinessHour.DayOfWeek day = BusinessHour.DayOfWeek.valueOf(columns.get("day")); //columns.get("day");
//				Time start = (Time) dateFormat.parse(columns.get("startTime"));
//				Time end = (Time) dateFormat.parse(columns.get("endtime"));
//				businesses.addBusinessHour(new BusinessHour(day, start, end, cs));
//			}	

	}

	// TODO
	@Given("the following appointments exist in the system:")
	public void the_following_appointments_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		cs = CarShopApplication.getCarShop();
//		cs.ge
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//		List<Map<String,String>> rows = dataTable.asMaps(String.class,String.class);
//		
//		for (Map<String,String> columns : rows) {
//			String customer = columns.get("customer");
//			String serviceName = columns.get("serviceName");
//			String optServices = columns.get("optServices");
//			Date date = dateFormat.parse(columns.get("date"));
//			Customer customers;
//			for (Customer c: cs.getCustomers()) {
//				if (c.getUsername().equals(customer)) {
//					customers = c;
//				}
//			}
//			cs.addAppointment(customers, serviceName);
//
//		}
//
//	    throw new io.cucumber.java.PendingException();
	}

	// TODO
	@Given("{string} is logged in to their account")
	public void is_logged_in_to_their_account(String string) {
//		CarShopApplication.logInAsWithUsername(string);
	    // Write code here that turns the phrase above into concrete actions
	}

	@When("{string} attempts to cancel their {string} appointment on {string} at {string}")
	public void attempts_to_cancel_their_appointment_on_at(String string, String string2, String string3, String string4) {
	    
		// Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	}

	@Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	public void s_appointment_on_at_shall_be_removed_from_the_system(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	}

	@Then("there shall be {int} less appointment in the system")
	public void there_shall_be_less_appointment_in_the_system(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	}

	@Then("the system shall report {string}")
	public void the_system_shall_report(String string) {
	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	}

	@Then("{string} shall have a {string} appointment on {string} at {string} with the following properties")
	public void shall_have_a_appointment_on_at_with_the_following_properties(String string, String string2, String string3, String string4, io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
//	    throw new io.cucumber.java.PendingException();
	}

	@Then("there shall be {int} more appointment in the system")
	public void there_shall_be_more_appointment_in_the_system(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	}

	@When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
	public void attempts_to_cancel_s_appointment_on_at(String string, String string2, String string3, String string4, String string5) {
	    // Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	}

}