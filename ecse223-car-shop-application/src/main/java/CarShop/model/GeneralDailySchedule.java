/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;

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
  private CarShop carShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GeneralDailySchedule(DayType aDayType, Date aOpeningTime, Date aClosingTime, Date aCurrentDate, CarShop aCarShop)
  {
    dayType = aDayType;
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
    currentDate = aCurrentDate;
    boolean didAddCarShop = setCarShop(aCarShop);
    if (!didAddCarShop)
    {
      throw new RuntimeException("Unable to create generalDailySchedule due to carShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
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
      existingCarShop.removeGeneralDailySchedule(this);
    }
    carShop.addGeneralDailySchedule(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    CarShop placeholderCarShop = carShop;
    this.carShop = null;
    if(placeholderCarShop != null)
    {
      placeholderCarShop.removeGeneralDailySchedule(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayType" + "=" + (getDayType() != null ? !getDayType().equals(this)  ? getDayType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openingTime" + "=" + (getOpeningTime() != null ? !getOpeningTime().equals(this)  ? getOpeningTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingTime" + "=" + (getClosingTime() != null ? !getClosingTime().equals(this)  ? getClosingTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentDate" + "=" + (getCurrentDate() != null ? !getCurrentDate().equals(this)  ? getCurrentDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShop = "+(getCarShop()!=null?Integer.toHexString(System.identityHashCode(getCarShop())):"null");
  }
}