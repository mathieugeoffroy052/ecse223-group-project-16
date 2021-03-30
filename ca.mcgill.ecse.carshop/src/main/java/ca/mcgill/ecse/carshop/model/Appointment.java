/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.model;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import ca.mcgill.ecse.carshop.application.CarShopApplication;
import java.io.Serializable;
import java.util.*;

// line 1 "../../../../../CarShopStates.ump"
// line 3 "../../../../../CarShopPersistence.ump"
// line 154 "../../../../../carshop.ump"
public class Appointment implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment State Machines
  public enum AppointmentStatus { Booked, Final, InProgress, HasEnded }
  private AppointmentStatus appointmentStatus;

  //Appointment Associations
  private Customer customer;
  private BookableService bookableService;
  private List<ServiceBooking> serviceBookings;
  private CarShop carShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Customer aCustomer, BookableService aBookableService, CarShop aCarShop)
  {
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appointment due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBookableService = setBookableService(aBookableService);
    if (!didAddBookableService)
    {
      throw new RuntimeException("Unable to create appointment due to bookableService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    serviceBookings = new ArrayList<ServiceBooking>();
    boolean didAddCarShop = setCarShop(aCarShop);
    if (!didAddCarShop)
    {
      throw new RuntimeException("Unable to create appointment due to carShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setAppointmentStatus(AppointmentStatus.Booked);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getAppointmentStatusFullName()
  {
    String answer = appointmentStatus.toString();
    return answer;
  }

  public AppointmentStatus getAppointmentStatus()
  {
    return appointmentStatus;
  }

  public boolean changeAppointmentService(Service newService)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (!(startingSoon()))
        {
        // line 11 "../../../../../CarShopStates.ump"
          changeService(newService);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        if (startingSoon())
        {
        // line 13 "../../../../../CarShopStates.ump"
          changeServiceUnsuccessful(newService);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 60 "../../../../../CarShopStates.ump"
        changeServiceInProgress(newService);
        setAppointmentStatus(AppointmentStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean changeStartDateAndTime(Date newStartDate,List<Time> newStartTimes)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (startingSoon())
        {
        // line 18 "../../../../../CarShopStates.ump"
          changeDateAndTimeUnsuccessful(newStartDate, newStartTimes);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        if (!(startingSoon()))
        {
        // line 22 "../../../../../CarShopStates.ump"
          changeDateAndTime(newStartDate, newStartTimes);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 62 "../../../../../CarShopStates.ump"
        changeDateAndTimeInProgress(newStartDate, newStartTimes);
        setAppointmentStatus(AppointmentStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean addOptServiceToCombo(Service service,Time startTime)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (!(startingSoon()))
        {
        // line 27 "../../../../../CarShopStates.ump"
          addOptService(service, startTime);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        if (startingSoon())
        {
        // line 31 "../../../../../CarShopStates.ump"
          addOptServiceUnsuccessful(service, startTime);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 66 "../../../../../CarShopStates.ump"
        addOptService(service, startTime);
        setAppointmentStatus(AppointmentStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancelBooking()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (!(startingSoon()))
        {
          setAppointmentStatus(AppointmentStatus.Final);
          wasEventProcessed = true;
          break;
        }
        if (startingSoon())
        {
        // line 39 "../../../../../CarShopStates.ump"
          cancelAppointmentUnsuccessful();
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startAppointment()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (!(isEarly()))
        {
          setAppointmentStatus(AppointmentStatus.InProgress);
          wasEventProcessed = true;
          break;
        }
        if (isEarly())
        {
        // line 45 "../../../../../CarShopStates.ump"
          startingAppointmentUnsuccessful();
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 57 "../../../../../CarShopStates.ump"
        startAppointmentInProgress();
        setAppointmentStatus(AppointmentStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean noShow()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (hasEnded())
        {
        // line 49 "../../../../../CarShopStates.ump"
          updateNoShow();
          setAppointmentStatus(AppointmentStatus.Final);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 73 "../../../../../CarShopStates.ump"
        updateNoShowInProgress();
        setAppointmentStatus(AppointmentStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean endAppointment()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        // line 52 "../../../../../CarShopStates.ump"
        endAppointmentUnsuccessful();
        setAppointmentStatus(AppointmentStatus.Booked);
        wasEventProcessed = true;
        break;
      case InProgress:
        setAppointmentStatus(AppointmentStatus.HasEnded);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancelAppointment()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case InProgress:
        // line 70 "../../../../../CarShopStates.ump"
        cancelAppointmentInProgress();
        setAppointmentStatus(AppointmentStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition7__()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case HasEnded:
        setAppointmentStatus(AppointmentStatus.Final);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setAppointmentStatus(AppointmentStatus aAppointmentStatus)
  {
    appointmentStatus = aAppointmentStatus;

    // entry actions and do activities
    switch(appointmentStatus)
    {
      case Final:
        delete();
        break;
      case HasEnded:
        __autotransition7__();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public BookableService getBookableService()
  {
    return bookableService;
  }
  /* Code from template association_GetMany */
  public ServiceBooking getServiceBooking(int index)
  {
    ServiceBooking aServiceBooking = serviceBookings.get(index);
    return aServiceBooking;
  }

  public List<ServiceBooking> getServiceBookings()
  {
    List<ServiceBooking> newServiceBookings = Collections.unmodifiableList(serviceBookings);
    return newServiceBookings;
  }

  public int numberOfServiceBookings()
  {
    int number = serviceBookings.size();
    return number;
  }

  public boolean hasServiceBookings()
  {
    boolean has = serviceBookings.size() > 0;
    return has;
  }

  public int indexOfServiceBooking(ServiceBooking aServiceBooking)
  {
    int index = serviceBookings.indexOf(aServiceBooking);
    return index;
  }
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppointment(this);
    }
    customer.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBookableService(BookableService aBookableService)
  {
    boolean wasSet = false;
    if (aBookableService == null)
    {
      return wasSet;
    }

    BookableService existingBookableService = bookableService;
    bookableService = aBookableService;
    if (existingBookableService != null && !existingBookableService.equals(aBookableService))
    {
      existingBookableService.removeAppointment(this);
    }
    bookableService.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceBookings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceBooking addServiceBooking(Service aService, TimeSlot aTimeSlot)
  {
    return new ServiceBooking(aService, aTimeSlot, this);
  }

  public boolean addServiceBooking(ServiceBooking aServiceBooking)
  {
    boolean wasAdded = false;
    if (serviceBookings.contains(aServiceBooking)) { return false; }
    Appointment existingAppointment = aServiceBooking.getAppointment();
    boolean isNewAppointment = existingAppointment != null && !this.equals(existingAppointment);
    if (isNewAppointment)
    {
      aServiceBooking.setAppointment(this);
    }
    else
    {
      serviceBookings.add(aServiceBooking);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServiceBooking(ServiceBooking aServiceBooking)
  {
    boolean wasRemoved = false;
    //Unable to remove aServiceBooking, as it must always have a appointment
    if (!this.equals(aServiceBooking.getAppointment()))
    {
      serviceBookings.remove(aServiceBooking);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceBookingAt(ServiceBooking aServiceBooking, int index)
  {  
    boolean wasAdded = false;
    if(addServiceBooking(aServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceBookings()) { index = numberOfServiceBookings() - 1; }
      serviceBookings.remove(aServiceBooking);
      serviceBookings.add(index, aServiceBooking);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceBookingAt(ServiceBooking aServiceBooking, int index)
  {
    boolean wasAdded = false;
    if(serviceBookings.contains(aServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceBookings()) { index = numberOfServiceBookings() - 1; }
      serviceBookings.remove(aServiceBooking);
      serviceBookings.add(index, aServiceBooking);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceBookingAt(aServiceBooking, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCarShop(CarShop aCarShop)
  {
    boolean wasSet = false;
    if (aCarShop == null)
    {
      return wasSet;
    }

    CarShop existingCarShop = carShop;
    carShop = aCarShop;
    if (existingCarShop != null && !existingCarShop.equals(aCarShop))
    {
      existingCarShop.removeAppointment(this);
    }
    carShop.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeAppointment(this);
    }
    BookableService placeholderBookableService = bookableService;
    this.bookableService = null;
    if(placeholderBookableService != null)
    {
      placeholderBookableService.removeAppointment(this);
    }
    while (serviceBookings.size() > 0)
    {
      ServiceBooking aServiceBooking = serviceBookings.get(serviceBookings.size() - 1);
      aServiceBooking.delete();
      serviceBookings.remove(aServiceBooking);
    }
    
    CarShop placeholderCarShop = carShop;
    this.carShop = null;
    if(placeholderCarShop != null)
    {
      placeholderCarShop.removeAppointment(this);
    }
  }

  // line 88 "../../../../../CarShopStates.ump"
   private static  Time findEndTime(BookableService service, Time startTime){
    int duration = service.getDuration();
		int minutes = duration % 60;
		int hours = duration / 60;
		
		LocalTime localtime = startTime.toLocalTime();
		localtime = localtime.plusMinutes(minutes);
		localtime = localtime.plusHours(hours);
		Time endTime = Time.valueOf(localtime);
		return endTime;
  }

  // line 101 "../../../../../CarShopStates.ump"
   private void changeService(Service serviceName){
    if (this.getBookableService() instanceof Service) {
			this.setBookableService(serviceName);
			this.getServiceBooking(0).setService(serviceName);
			Time endTime = findEndTime(serviceName, this.getServiceBooking(0).getTimeSlot().getStartTime());
			this.getServiceBooking(0).getTimeSlot().setEndTime(endTime);
		}
  }

  // line 110 "../../../../../CarShopStates.ump"
   private void changeDateAndTime(Date date, List<Time> times){
    if (this.getBookableService() instanceof Service) {
			Time endTime = findEndTime(this.getBookableService(), times.get(0));
			
			this.getServiceBooking(0).getTimeSlot().setStartDate(date);
			this.getServiceBooking(0).getTimeSlot().setEndDate(date);
			this.getServiceBooking(0).getTimeSlot().setStartTime(times.get(0));
			this.getServiceBooking(0).getTimeSlot().setEndTime(endTime);
		} else {
			for(int i = 0; i < times.size(); i++) {
				ServiceBooking sb = this.getServiceBooking(i);
				TimeSlot timeSlot = sb.getTimeSlot();
				
				Time endTime = findEndTime(sb.getService(), times.get(i));
				
				timeSlot.setStartDate(date);
				timeSlot.setEndDate(date);
				timeSlot.setStartTime(times.get(i));
				timeSlot.setEndTime(endTime);
			}
		}
  }

  // line 133 "../../../../../CarShopStates.ump"
   private void addOptService(Service service, Time startTime){
    if (this.bookableService instanceof ServiceCombo) {
			Date date = this.getServiceBooking(0).getTimeSlot().getStartDate();
			Time endTime = findEndTime(service, startTime);
			
			TimeSlot newTimeSlot = new TimeSlot(date, startTime, date, endTime, carShop);
			
			new ServiceBooking(service, newTimeSlot, this);
		}
  }

  // line 144 "../../../../../CarShopStates.ump"
   private void startingAppointmentUnsuccessful(){
    throw new RuntimeException("Cannot start appointment early");
  }

  // line 148 "../../../../../CarShopStates.ump"
   private void changeServiceUnsuccessful(Service service){
    throw new RuntimeException("Cannot change the service now, appointment starting within 24 hours");
  }

  // line 152 "../../../../../CarShopStates.ump"
   private void changeDateAndTimeUnsuccessful(Date date, List<Time> newStartTimes){
    throw new RuntimeException("Cannot change the start time now, appointment starting within 24 hours");
  }

  // line 156 "../../../../../CarShopStates.ump"
   private void cancelAppointmentUnsuccessful(){
    throw new RuntimeException("Cannot cancel appointment now, appointment starting within 24 hours");
  }

  // line 160 "../../../../../CarShopStates.ump"
   private void addOptServiceUnsuccessful(Service service, Time startTime){
    throw new RuntimeException("Cannot add optional now, appointment starting within 24 hours");
  }

  // line 164 "../../../../../CarShopStates.ump"
   private void updateNoShow(){
    this.getCustomer().updateNoShow();
  }

  // line 168 "../../../../../CarShopStates.ump"
   private void endAppointmentUnsuccessful(){
    throw new RuntimeException("Cannot end appointment before it starts");
  }

  // line 172 "../../../../../CarShopStates.ump"
   private void startAppointmentInProgress(){
    throw new RuntimeException("Appointment is already in progress");
  }

  // line 176 "../../../../../CarShopStates.ump"
   private void changeServiceInProgress(Service service){
    throw new RuntimeException("Cannot change the service, appointment is in progress");
  }

  // line 180 "../../../../../CarShopStates.ump"
   private void changeDateAndTimeInProgress(Date date, List<Time> times){
    throw new RuntimeException("Cannot change start time now, appointment is in progress");
  }

  // line 184 "../../../../../CarShopStates.ump"
   private void cancelAppointmentInProgress(){
    throw new RuntimeException("Cannot cancel, appointment is in progress");
  }

  // line 188 "../../../../../CarShopStates.ump"
   private void updateNoShowInProgress(){
    throw new RuntimeException("Cannot update no show, appointment is in progress");
  }

  // line 194 "../../../../../CarShopStates.ump"
   private boolean startingSoon(){
    Date systemDate = CarShopApplication.getSystemDate();
		Time systemTime = CarShopApplication.getSystemTime();
		
		Date startDate = this.getServiceBooking(0).getTimeSlot().getStartDate();
		Time startTime = this.getServiceBooking(0).getTimeSlot().getStartTime();
		
		if(startDate.equals(systemDate)) {
			return true;
		}
		
		LocalDate localDate = startDate.toLocalDate();
		localDate = localDate.minusDays(1);
		
		
		if(Date.valueOf(localDate).equals(systemDate) && startTime.before(systemTime)) {
			return true;
		}
		
		return false;
  }

  // line 216 "../../../../../CarShopStates.ump"
   private boolean isEarly(){
    Date systemDate = CarShopApplication.getSystemDate();
		Time systemTime = CarShopApplication.getSystemTime();
		
		Date startDate = this.getServiceBooking(0).getTimeSlot().getStartDate();
		Time startTime = this.getServiceBooking(0).getTimeSlot().getStartTime();
		
		if(startDate.equals(systemDate) && !startTime.after(systemTime)) {
			return false;
		}
		return true;
  }

  // line 229 "../../../../../CarShopStates.ump"
   private boolean hasEnded(){
    Date systemDate = CarShopApplication.getSystemDate();
		Time systemTime = CarShopApplication.getSystemTime();
		
		Date endDate = this.getServiceBooking(this.getServiceBookings().size() - 1).getTimeSlot().getEndDate();
		Time endTime = this.getServiceBooking(this.getServiceBookings().size() - 1).getTimeSlot().getEndTime();
		
		if(endDate.before(systemDate)) {
			return true;
		}
		if (endDate.equals(systemDate) && endTime.before(systemTime)) {
			return true;
		}
		return false;
  }

}