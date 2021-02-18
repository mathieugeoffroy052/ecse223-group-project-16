/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 84 "../../CarShop.ump"
public class AppointmentCalendar
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AppointmentCalendar Associations
  private CustomerAccount customer;
  private List<Appointment> appointments;
  private Garage garage;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AppointmentCalendar(CustomerAccount aCustomer, Garage aGarage)
  {
    if (aCustomer == null || aCustomer.getCalendar() != null)
    {
      throw new RuntimeException("Unable to create AppointmentCalendar due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customer = aCustomer;
    appointments = new ArrayList<Appointment>();
    if (aGarage == null || aGarage.getAppointmentSchedule() != null)
    {
      throw new RuntimeException("Unable to create AppointmentCalendar due to aGarage. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    garage = aGarage;
  }

  public AppointmentCalendar(String aUsernameForCustomer, String aPasswordForCustomer, boolean aIsLoggedInForCustomer, Appointment aCustomerForCustomer)
  {
    customer = new CustomerAccount(aUsernameForCustomer, aPasswordForCustomer, aIsLoggedInForCustomer, this, aCustomerForCustomer);
    appointments = new ArrayList<Appointment>();
    garage = new Garage(this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public CustomerAccount getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }
  /* Code from template association_GetOne */
  public Garage getGarage()
  {
    return garage;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(int aDaysBeforeAppointment, Date aStartTime, Date aEndTime, ServiceList aService)
  {
    return new Appointment(aDaysBeforeAppointment, aStartTime, aEndTime, aService, this);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    AppointmentCalendar existingCalendar = aAppointment.getCalendar();
    boolean isNewCalendar = existingCalendar != null && !this.equals(existingCalendar);
    if (isNewCalendar)
    {
      aAppointment.setCalendar(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a calendar
    if (!this.equals(aAppointment.getCalendar()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    CustomerAccount existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.delete();
    }
    while (appointments.size() > 0)
    {
      Appointment aAppointment = appointments.get(appointments.size() - 1);
      aAppointment.delete();
      appointments.remove(aAppointment);
    }
    
    Garage existingGarage = garage;
    garage = null;
    if (existingGarage != null)
    {
      existingGarage.delete();
    }
  }

}