/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 70 "../../CarShop.ump"
public class Garage
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum JobType { Tires, Engine, Transmission, Electronics, Fluids }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Garage Attributes
  private JobType garageType;

  //Garage Associations
  private List<DailySchedule> dailySchedules;
  private TechnicianAccount technicianAccount;
  private List<Service> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Garage(JobType aGarageType, TechnicianAccount aTechnicianAccount)
  {
    garageType = aGarageType;
    dailySchedules = new ArrayList<DailySchedule>();
    if (aTechnicianAccount == null || aTechnicianAccount.getWorksAt() != null)
    {
      throw new RuntimeException("Unable to create Garage due to aTechnicianAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    technicianAccount = aTechnicianAccount;
    services = new ArrayList<Service>();
  }

  public Garage(JobType aGarageType, String aUsernameForTechnicianAccount, String aPasswordForTechnicianAccount, boolean aIsLoggedInForTechnicianAccount, User aUserForTechnicianAccount, JobType aTechnicianTypeForTechnicianAccount)
  {
    garageType = aGarageType;
    dailySchedules = new ArrayList<DailySchedule>();
    technicianAccount = new TechnicianAccount(aUsernameForTechnicianAccount, aPasswordForTechnicianAccount, aIsLoggedInForTechnicianAccount, aUserForTechnicianAccount, aTechnicianTypeForTechnicianAccount, this);
    services = new ArrayList<Service>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGarageType(JobType aGarageType)
  {
    boolean wasSet = false;
    garageType = aGarageType;
    wasSet = true;
    return wasSet;
  }

  public JobType getGarageType()
  {
    return garageType;
  }
  /* Code from template association_GetMany */
  public DailySchedule getDailySchedule(int index)
  {
    DailySchedule aDailySchedule = dailySchedules.get(index);
    return aDailySchedule;
  }

  public List<DailySchedule> getDailySchedules()
  {
    List<DailySchedule> newDailySchedules = Collections.unmodifiableList(dailySchedules);
    return newDailySchedules;
  }

  public int numberOfDailySchedules()
  {
    int number = dailySchedules.size();
    return number;
  }

  public boolean hasDailySchedules()
  {
    boolean has = dailySchedules.size() > 0;
    return has;
  }

  public int indexOfDailySchedule(DailySchedule aDailySchedule)
  {
    int index = dailySchedules.indexOf(aDailySchedule);
    return index;
  }
  /* Code from template association_GetOne */
  public TechnicianAccount getTechnicianAccount()
  {
    return technicianAccount;
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
  public static int minimumNumberOfDailySchedules()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public DailySchedule addDailySchedule(DailySchedule.DayType aDayType, Date aOpeningTime, Date aClosingTime)
  {
    return new DailySchedule(aDayType, aOpeningTime, aClosingTime, this);
  }

  public boolean addDailySchedule(DailySchedule aDailySchedule)
  {
    boolean wasAdded = false;
    if (dailySchedules.contains(aDailySchedule)) { return false; }
    Garage existingScheduleForGarage = aDailySchedule.getScheduleForGarage();
    boolean isNewScheduleForGarage = existingScheduleForGarage != null && !this.equals(existingScheduleForGarage);
    if (isNewScheduleForGarage)
    {
      aDailySchedule.setScheduleForGarage(this);
    }
    else
    {
      dailySchedules.add(aDailySchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDailySchedule(DailySchedule aDailySchedule)
  {
    boolean wasRemoved = false;
    //Unable to remove aDailySchedule, as it must always have a scheduleForGarage
    if (!this.equals(aDailySchedule.getScheduleForGarage()))
    {
      dailySchedules.remove(aDailySchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDailyScheduleAt(DailySchedule aDailySchedule, int index)
  {  
    boolean wasAdded = false;
    if(addDailySchedule(aDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDailySchedules()) { index = numberOfDailySchedules() - 1; }
      dailySchedules.remove(aDailySchedule);
      dailySchedules.add(index, aDailySchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDailyScheduleAt(DailySchedule aDailySchedule, int index)
  {
    boolean wasAdded = false;
    if(dailySchedules.contains(aDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDailySchedules()) { index = numberOfDailySchedules() - 1; }
      dailySchedules.remove(aDailySchedule);
      dailySchedules.add(index, aDailySchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDailyScheduleAt(aDailySchedule, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, double aDurationInHours, CarShop aCarShop, Owner aOwner)
  {
    return new Service(aName, aDurationInHours, this, aCarShop, aOwner);
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
    for(int i=dailySchedules.size(); i > 0; i--)
    {
      DailySchedule aDailySchedule = dailySchedules.get(i - 1);
      aDailySchedule.delete();
    }
    TechnicianAccount existingTechnicianAccount = technicianAccount;
    technicianAccount = null;
    if (existingTechnicianAccount != null)
    {
      existingTechnicianAccount.delete();
    }
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "garageType" + "=" + (getGarageType() != null ? !getGarageType().equals(this)  ? getGarageType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "technicianAccount = "+(getTechnicianAccount()!=null?Integer.toHexString(System.identityHashCode(getTechnicianAccount())):"null");
  }
}