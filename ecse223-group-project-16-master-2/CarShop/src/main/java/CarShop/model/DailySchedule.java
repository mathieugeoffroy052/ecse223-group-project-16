/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;

// line 91 "../../CarShop.ump"
public class DailySchedule
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayType { Holiday, Break, Work }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DailySchedule Attributes
  private DayType dayType;
  private Date openingTime;
  private Date closingTime;

  //DailySchedule Associations
  private TimeSlot firstAppointment;
  private Garage scheduleForGarage;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DailySchedule(DayType aDayType, Date aOpeningTime, Date aClosingTime, Garage aScheduleForGarage)
  {
    dayType = aDayType;
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
    boolean didAddScheduleForGarage = setScheduleForGarage(aScheduleForGarage);
    if (!didAddScheduleForGarage)
    {
      throw new RuntimeException("Unable to create dailySchedule due to scheduleForGarage. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public TimeSlot getFirstAppointment()
  {
    return firstAppointment;
  }

  public boolean hasFirstAppointment()
  {
    boolean has = firstAppointment != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Garage getScheduleForGarage()
  {
    return scheduleForGarage;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setFirstAppointment(TimeSlot aNewFirstAppointment)
  {
    boolean wasSet = false;
    if (firstAppointment != null && !firstAppointment.equals(aNewFirstAppointment) && equals(firstAppointment.getDailySchedule()))
    {
      //Unable to setFirstAppointment, as existing firstAppointment would become an orphan
      return wasSet;
    }

    firstAppointment = aNewFirstAppointment;
    DailySchedule anOldDailySchedule = aNewFirstAppointment != null ? aNewFirstAppointment.getDailySchedule() : null;

    if (!this.equals(anOldDailySchedule))
    {
      if (anOldDailySchedule != null)
      {
        anOldDailySchedule.firstAppointment = null;
      }
      if (firstAppointment != null)
      {
        firstAppointment.setDailySchedule(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setScheduleForGarage(Garage aScheduleForGarage)
  {
    boolean wasSet = false;
    if (aScheduleForGarage == null)
    {
      return wasSet;
    }

    Garage existingScheduleForGarage = scheduleForGarage;
    scheduleForGarage = aScheduleForGarage;
    if (existingScheduleForGarage != null && !existingScheduleForGarage.equals(aScheduleForGarage))
    {
      existingScheduleForGarage.removeDailySchedule(this);
    }
    scheduleForGarage.addDailySchedule(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TimeSlot existingFirstAppointment = firstAppointment;
    firstAppointment = null;
    if (existingFirstAppointment != null)
    {
      existingFirstAppointment.delete();
      existingFirstAppointment.setDailySchedule(null);
    }
    Garage placeholderScheduleForGarage = scheduleForGarage;
    this.scheduleForGarage = null;
    if(placeholderScheduleForGarage != null)
    {
      placeholderScheduleForGarage.removeDailySchedule(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayType" + "=" + (getDayType() != null ? !getDayType().equals(this)  ? getDayType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openingTime" + "=" + (getOpeningTime() != null ? !getOpeningTime().equals(this)  ? getOpeningTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingTime" + "=" + (getClosingTime() != null ? !getClosingTime().equals(this)  ? getClosingTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "firstAppointment = "+(getFirstAppointment()!=null?Integer.toHexString(System.identityHashCode(getFirstAppointment())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "scheduleForGarage = "+(getScheduleForGarage()!=null?Integer.toHexString(System.identityHashCode(getScheduleForGarage())):"null");
  }
}