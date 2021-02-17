/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;
import java.util.*;

/**
 * association{
 * 1 User -> 0..1 UserAccount;
 * }
 */
// line 8 "../../CarShop.ump"
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
  private List<WeeklyBusinessHours> weeklySchedules;

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
    weeklySchedules = new ArrayList<WeeklyBusinessHours>();
  }

  public CarShop(Date aDate, String aGeneralInfo, String aAddress, String aEmailAddress, String aPhoneNUmber, String aUsernameForOwner, String aPasswordForOwner, boolean aIsLoggedInForOwner)
  {
    date = aDate;
    generalInfo = aGeneralInfo;
    address = aAddress;
    emailAddress = aEmailAddress;
    phoneNUmber = aPhoneNUmber;
    owner = new Owner(aUsernameForOwner, aPasswordForOwner, aIsLoggedInForOwner, this);
    weeklySchedules = new ArrayList<WeeklyBusinessHours>();
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
  public WeeklyBusinessHours getWeeklySchedule(int index)
  {
    WeeklyBusinessHours aWeeklySchedule = weeklySchedules.get(index);
    return aWeeklySchedule;
  }

  public List<WeeklyBusinessHours> getWeeklySchedules()
  {
    List<WeeklyBusinessHours> newWeeklySchedules = Collections.unmodifiableList(weeklySchedules);
    return newWeeklySchedules;
  }

  public int numberOfWeeklySchedules()
  {
    int number = weeklySchedules.size();
    return number;
  }

  public boolean hasWeeklySchedules()
  {
    boolean has = weeklySchedules.size() > 0;
    return has;
  }

  public int indexOfWeeklySchedule(WeeklyBusinessHours aWeeklySchedule)
  {
    int index = weeklySchedules.indexOf(aWeeklySchedule);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWeeklySchedules()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WeeklyBusinessHours addWeeklySchedule()
  {
    return new WeeklyBusinessHours(this);
  }

  public boolean addWeeklySchedule(WeeklyBusinessHours aWeeklySchedule)
  {
    boolean wasAdded = false;
    if (weeklySchedules.contains(aWeeklySchedule)) { return false; }
    CarShop existingShop = aWeeklySchedule.getShop();
    boolean isNewShop = existingShop != null && !this.equals(existingShop);
    if (isNewShop)
    {
      aWeeklySchedule.setShop(this);
    }
    else
    {
      weeklySchedules.add(aWeeklySchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWeeklySchedule(WeeklyBusinessHours aWeeklySchedule)
  {
    boolean wasRemoved = false;
    //Unable to remove aWeeklySchedule, as it must always have a shop
    if (!this.equals(aWeeklySchedule.getShop()))
    {
      weeklySchedules.remove(aWeeklySchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWeeklyScheduleAt(WeeklyBusinessHours aWeeklySchedule, int index)
  {  
    boolean wasAdded = false;
    if(addWeeklySchedule(aWeeklySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeeklySchedules()) { index = numberOfWeeklySchedules() - 1; }
      weeklySchedules.remove(aWeeklySchedule);
      weeklySchedules.add(index, aWeeklySchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWeeklyScheduleAt(WeeklyBusinessHours aWeeklySchedule, int index)
  {
    boolean wasAdded = false;
    if(weeklySchedules.contains(aWeeklySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWeeklySchedules()) { index = numberOfWeeklySchedules() - 1; }
      weeklySchedules.remove(aWeeklySchedule);
      weeklySchedules.add(index, aWeeklySchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWeeklyScheduleAt(aWeeklySchedule, index);
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
    for(int i=weeklySchedules.size(); i > 0; i--)
    {
      WeeklyBusinessHours aWeeklySchedule = weeklySchedules.get(i - 1);
      aWeeklySchedule.delete();
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