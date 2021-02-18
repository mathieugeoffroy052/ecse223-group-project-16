/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 71 "../../CarShop.ump"
public class Garage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Garage Associations
  private List<Schedule> activeDays;
  private AppointmentCalendar appointmentSchedule;
  private List<Service> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Garage(AppointmentCalendar aAppointmentSchedule)
  {
    activeDays = new ArrayList<Schedule>();
    if (aAppointmentSchedule == null || aAppointmentSchedule.getGarage() != null)
    {
      throw new RuntimeException("Unable to create Garage due to aAppointmentSchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointmentSchedule = aAppointmentSchedule;
    services = new ArrayList<Service>();
  }

  public Garage(CustomerAccount aCustomerForAppointmentSchedule)
  {
    activeDays = new ArrayList<Schedule>();
    appointmentSchedule = new AppointmentCalendar(aCustomerForAppointmentSchedule, this);
    services = new ArrayList<Service>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Schedule getActiveDay(int index)
  {
    Schedule aActiveDay = activeDays.get(index);
    return aActiveDay;
  }

  public List<Schedule> getActiveDays()
  {
    List<Schedule> newActiveDays = Collections.unmodifiableList(activeDays);
    return newActiveDays;
  }

  public int numberOfActiveDays()
  {
    int number = activeDays.size();
    return number;
  }

  public boolean hasActiveDays()
  {
    boolean has = activeDays.size() > 0;
    return has;
  }

  public int indexOfActiveDay(Schedule aActiveDay)
  {
    int index = activeDays.indexOf(aActiveDay);
    return index;
  }
  /* Code from template association_GetOne */
  public AppointmentCalendar getAppointmentSchedule()
  {
    return appointmentSchedule;
  }
  /* Code from template association_GetMany */
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
    return newServices;
  }

  public int numberOfServices()
  {
    int number = services.size();
    return number;
  }

  public boolean hasServices()
  {
    boolean has = services.size() > 0;
    return has;
  }

  public int indexOfService(Service aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfActiveDays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Schedule addActiveDay(boolean aIsHoliday, boolean aIsBreakDay, Date aStartTime, Date aEndTime, WeeklySchedule aWeek)
  {
    return new Schedule(aIsHoliday, aIsBreakDay, aStartTime, aEndTime, aWeek, this);
  }

  public boolean addActiveDay(Schedule aActiveDay)
  {
    boolean wasAdded = false;
    if (activeDays.contains(aActiveDay)) { return false; }
    Garage existingGarage = aActiveDay.getGarage();
    boolean isNewGarage = existingGarage != null && !this.equals(existingGarage);
    if (isNewGarage)
    {
      aActiveDay.setGarage(this);
    }
    else
    {
      activeDays.add(aActiveDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeActiveDay(Schedule aActiveDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aActiveDay, as it must always have a garage
    if (!this.equals(aActiveDay.getGarage()))
    {
      activeDays.remove(aActiveDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addActiveDayAt(Schedule aActiveDay, int index)
  {  
    boolean wasAdded = false;
    if(addActiveDay(aActiveDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActiveDays()) { index = numberOfActiveDays() - 1; }
      activeDays.remove(aActiveDay);
      activeDays.add(index, aActiveDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveActiveDayAt(Schedule aActiveDay, int index)
  {
    boolean wasAdded = false;
    if(activeDays.contains(aActiveDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActiveDays()) { index = numberOfActiveDays() - 1; }
      activeDays.remove(aActiveDay);
      activeDays.add(index, aActiveDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addActiveDayAt(aActiveDay, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, Service.WorkType aGeneralService, double aDurationInHours, CarShop aCarShop)
  {
    return new Service(aName, aGeneralService, aDurationInHours, this, aCarShop);
  }

  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    Garage existingWorkingGarage = aService.getWorkingGarage();
    boolean isNewWorkingGarage = existingWorkingGarage != null && !this.equals(existingWorkingGarage);
    if (isNewWorkingGarage)
    {
      aService.setWorkingGarage(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a workingGarage
    if (!this.equals(aService.getWorkingGarage()))
    {
      services.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(Service aService, int index)
  {
    boolean wasAdded = false;
    if(services.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=activeDays.size(); i > 0; i--)
    {
      Schedule aActiveDay = activeDays.get(i - 1);
      aActiveDay.delete();
    }
    AppointmentCalendar existingAppointmentSchedule = appointmentSchedule;
    appointmentSchedule = null;
    if (existingAppointmentSchedule != null)
    {
      existingAppointmentSchedule.delete();
    }
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
  }

}