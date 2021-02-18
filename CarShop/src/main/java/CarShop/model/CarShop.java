/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;
import java.util.*;

/**
 * Constraints
 * 
 * 1. One of each technician type
 * 2. User account can only be logged in if the user is signed up
 * 3. Garage opening hours must be within the weekly business hours
 * 4. Garage business hours must not conflict with garage holidays or breaks
 * 5. You can't have two appointments with the same technician at the same time
 * 6. A service combo cannot have two of the same services
 * 7. Every service should see its duration rounded up to the nearest 30 minute interval
 */
// line 19 "../../CarShop.ump"
public class CarShop
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CarShop Attributes
  private Date date;
  private String generalInfo;
  private String address;
  private String emailAddress;
  private String phoneNUmber;

  //CarShop Associations
  private Owner owner;
  private List<WeeklySchedule> weeklySchedule;
  private List<Service> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CarShop(Date aDate, String aGeneralInfo, String aAddress, String aEmailAddress, String aPhoneNUmber, Owner aOwner)
  {
    date = aDate;
    generalInfo = aGeneralInfo;
    address = aAddress;
    emailAddress = aEmailAddress;
    phoneNUmber = aPhoneNUmber;
    if (aOwner == null || aOwner.getCarShop() != null)
    {
      throw new RuntimeException("Unable to create CarShop due to aOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    owner = aOwner;
    weeklySchedule = new ArrayList<WeeklySchedule>();
    services = new ArrayList<Service>();
  }

  public CarShop(Date aDate, String aGeneralInfo, String aAddress, String aEmailAddress, String aPhoneNUmber, String aUsernameForOwner, String aPasswordForOwner, boolean aIsLoggedInForOwner)
  {
    date = aDate;
    generalInfo = aGeneralInfo;
    address = aAddress;
    emailAddress = aEmailAddress;
    phoneNUmber = aPhoneNUmber;
    owner = new Owner(aUsernameForOwner, aPasswordForOwner, aIsLoggedInForOwner, this);
    weeklySchedule = new ArrayList<WeeklySchedule>();
    services = new ArrayList<Service>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setGeneralInfo(String aGeneralInfo)
  {
    boolean wasSet = false;
    generalInfo = aGeneralInfo;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNUmber(String aPhoneNUmber)
  {
    boolean wasSet = false;
    phoneNUmber = aPhoneNUmber;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public String getGeneralInfo()
  {
    return generalInfo;
  }

  public String getAddress()
  {
    return address;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public String getPhoneNUmber()
  {
    return phoneNUmber;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetMany */
  public WeeklySchedule getWeeklySchedule(int index)
  {
    WeeklySchedule aWeeklySchedule = weeklySchedule.get(index);
    return aWeeklySchedule;
  }

  public List<WeeklySchedule> getWeeklySchedule()
  {
    List<WeeklySchedule> newWeeklySchedule = Collections.unmodifiableList(weeklySchedule);
    return newWeeklySchedule;
  }

  public int numberOfWeeklySchedule()
  {
    int number = weeklySchedule.size();
    return number;
  }

  public boolean hasWeeklySchedule()
  {
    boolean has = weeklySchedule.size() > 0;
    return has;
  }

  public int indexOfWeeklySchedule(WeeklySchedule aWeeklySchedule)
  {
    int index = weeklySchedule.indexOf(aWeeklySchedule);
    return index;
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
  public static int minimumNumberOfWeeklySchedule()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WeeklySchedule addWeeklySchedule()
  {
    return new WeeklySchedule(this);
  }

  public boolean addWeeklySchedule(WeeklySchedule aWeeklySchedule)
  {
    boolean wasAdded = false;
    if (weeklySchedule.contains(aWeeklySchedule)) { return false; }
    CarShop existingShop = aWeeklySchedule.getShop();
    boolean isNewShop = existingShop != null && !this.equals(existingShop);
    if (isNewShop)
    {
      aWeeklySchedule.setShop(this);
    }
    else
    {
      weeklySchedule.add(aWeeklySchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWeeklySchedule(WeeklySchedule aWeeklySchedule)
  {
    boolean wasRemoved = false;
    //Unable to remove aWeeklySchedule, as it must always have a shop
    if (!this.equals(aWeeklySchedule.getShop()))
    {
      weeklySchedule.remove(aWeeklySchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWeeklyScheduleAt(WeeklySchedule aWeeklySchedule, int index)
  {  
    boolean wasAdded = false;
    if(addWeeklySchedule(aWeeklySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeeklySchedule()) { index = numberOfWeeklySchedule() - 1; }
      weeklySchedule.remove(aWeeklySchedule);
      weeklySchedule.add(index, aWeeklySchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWeeklyScheduleAt(WeeklySchedule aWeeklySchedule, int index)
  {
    boolean wasAdded = false;
    if(weeklySchedule.contains(aWeeklySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeeklySchedule()) { index = numberOfWeeklySchedule() - 1; }
      weeklySchedule.remove(aWeeklySchedule);
      weeklySchedule.add(index, aWeeklySchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWeeklyScheduleAt(aWeeklySchedule, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, Service.WorkType aGeneralService, double aDurationInHours, Garage aWorkingGarage)
  {
    return new Service(aName, aGeneralService, aDurationInHours, aWorkingGarage, this);
  }

  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    CarShop existingCarShop = aService.getCarShop();
    boolean isNewCarShop = existingCarShop != null && !this.equals(existingCarShop);
    if (isNewCarShop)
    {
      aService.setCarShop(this);
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
    //Unable to remove aService, as it must always have a carShop
    if (!this.equals(aService.getCarShop()))
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
    Owner existingOwner = owner;
    owner = null;
    if (existingOwner != null)
    {
      existingOwner.delete();
    }
    for(int i=weeklySchedule.size(); i > 0; i--)
    {
      WeeklySchedule aWeeklySchedule = weeklySchedule.get(i - 1);
      aWeeklySchedule.delete();
    }
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "generalInfo" + ":" + getGeneralInfo()+ "," +
            "address" + ":" + getAddress()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "," +
            "phoneNUmber" + ":" + getPhoneNUmber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null");
  }
}