/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;
import java.util.*;

// line 90 "../../CarShop.ump"
public class GeneralDailySchedule
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayType { Holiday, Break, Work }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GeneralDailySchedule Attributes
  private DayType dayType;
  private Date openingTime;
  private Date closingTime;
  private Date currentDate;

  //GeneralDailySchedule Associations
  private List<CarShop> carShops;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GeneralDailySchedule(DayType aDayType, Date aOpeningTime, Date aClosingTime, Date aCurrentDate)
  {
    dayType = aDayType;
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
    currentDate = aCurrentDate;
    carShops = new ArrayList<CarShop>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDayType(DayType aDayType)
  {
    boolean wasSet = false;
    dayType = aDayType;
    wasSet = true;
    return wasSet;
  }

  public boolean setOpeningTime(Date aOpeningTime)
  {
    boolean wasSet = false;
    openingTime = aOpeningTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingTime(Date aClosingTime)
  {
    boolean wasSet = false;
    closingTime = aClosingTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentDate(Date aCurrentDate)
  {
    boolean wasSet = false;
    currentDate = aCurrentDate;
    wasSet = true;
    return wasSet;
  }

  public DayType getDayType()
  {
    return dayType;
  }

  public Date getOpeningTime()
  {
    return openingTime;
  }

  public Date getClosingTime()
  {
    return closingTime;
  }

  public Date getCurrentDate()
  {
    return currentDate;
  }
  /* Code from template association_GetMany */
  public CarShop getCarShop(int index)
  {
    CarShop aCarShop = carShops.get(index);
    return aCarShop;
  }

  public List<CarShop> getCarShops()
  {
    List<CarShop> newCarShops = Collections.unmodifiableList(carShops);
    return newCarShops;
  }

  public int numberOfCarShops()
  {
    int number = carShops.size();
    return number;
  }

  public boolean hasCarShops()
  {
    boolean has = carShops.size() > 0;
    return has;
  }

  public int indexOfCarShop(CarShop aCarShop)
  {
    int index = carShops.indexOf(aCarShop);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCarShops()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public CarShop addCarShop(Date aDate, String aGeneralInfo, String aAddress, String aEmailAddress, String aPhoneNumber, Owner aOwnedBy)
  {
    return new CarShop(aDate, aGeneralInfo, aAddress, aEmailAddress, aPhoneNumber, aOwnedBy, this);
  }

  public boolean addCarShop(CarShop aCarShop)
  {
    boolean wasAdded = false;
    if (carShops.contains(aCarShop)) { return false; }
    GeneralDailySchedule existingGeneralDailySchedule = aCarShop.getGeneralDailySchedule();
    boolean isNewGeneralDailySchedule = existingGeneralDailySchedule != null && !this.equals(existingGeneralDailySchedule);
    if (isNewGeneralDailySchedule)
    {
      aCarShop.setGeneralDailySchedule(this);
    }
    else
    {
      carShops.add(aCarShop);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCarShop(CarShop aCarShop)
  {
    boolean wasRemoved = false;
    //Unable to remove aCarShop, as it must always have a generalDailySchedule
    if (!this.equals(aCarShop.getGeneralDailySchedule()))
    {
      carShops.remove(aCarShop);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCarShopAt(CarShop aCarShop, int index)
  {  
    boolean wasAdded = false;
    if(addCarShop(aCarShop))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCarShops()) { index = numberOfCarShops() - 1; }
      carShops.remove(aCarShop);
      carShops.add(index, aCarShop);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCarShopAt(CarShop aCarShop, int index)
  {
    boolean wasAdded = false;
    if(carShops.contains(aCarShop))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCarShops()) { index = numberOfCarShops() - 1; }
      carShops.remove(aCarShop);
      carShops.add(index, aCarShop);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCarShopAt(aCarShop, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=carShops.size(); i > 0; i--)
    {
      CarShop aCarShop = carShops.get(i - 1);
      aCarShop.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayType" + "=" + (getDayType() != null ? !getDayType().equals(this)  ? getDayType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openingTime" + "=" + (getOpeningTime() != null ? !getOpeningTime().equals(this)  ? getOpeningTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingTime" + "=" + (getClosingTime() != null ? !getClosingTime().equals(this)  ? getClosingTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentDate" + "=" + (getCurrentDate() != null ? !getCurrentDate().equals(this)  ? getCurrentDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}