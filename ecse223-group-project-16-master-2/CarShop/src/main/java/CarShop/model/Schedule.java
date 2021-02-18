/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;

// line 83 "../../CarShop.ump"
public class Schedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Schedule Attributes
  private boolean isHoliday;
  private boolean isBreakDay;
  private Date startTime;
  private Date endTime;

  //Schedule Associations
  private WeeklySchedule week;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Schedule(boolean aIsHoliday, boolean aIsBreakDay, Date aStartTime, Date aEndTime, WeeklySchedule aWeek)
  {
    isHoliday = aIsHoliday;
    isBreakDay = aIsBreakDay;
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddWeek = setWeek(aWeek);
    if (!didAddWeek)
    {
      throw new RuntimeException("Unable to create day due to week. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsHoliday(boolean aIsHoliday)
  {
    boolean wasSet = false;
    isHoliday = aIsHoliday;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsBreakDay(boolean aIsBreakDay)
  {
    boolean wasSet = false;
    isBreakDay = aIsBreakDay;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Date aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Date aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsHoliday()
  {
    return isHoliday;
  }

  public boolean getIsBreakDay()
  {
    return isBreakDay;
  }

  public Date getStartTime()
  {
    return startTime;
  }

  public Date getEndTime()
  {
    return endTime;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsHoliday()
  {
    return isHoliday;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsBreakDay()
  {
    return isBreakDay;
  }
  /* Code from template association_GetOne */
  public WeeklySchedule getWeek()
  {
    return week;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setWeek(WeeklySchedule aWeek)
  {
    boolean wasSet = false;
    //Must provide week to day
    if (aWeek == null)
    {
      return wasSet;
    }

    //week already at maximum (7)
    if (aWeek.numberOfDays() >= WeeklySchedule.maximumNumberOfDays())
    {
      return wasSet;
    }
    
    WeeklySchedule existingWeek = week;
    week = aWeek;
    if (existingWeek != null && !existingWeek.equals(aWeek))
    {
      boolean didRemove = existingWeek.removeDay(this);
      if (!didRemove)
      {
        week = existingWeek;
        return wasSet;
      }
    }
    week.addDay(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    WeeklySchedule placeholderWeek = week;
    this.week = null;
    if(placeholderWeek != null)
    {
      placeholderWeek.removeDay(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isHoliday" + ":" + getIsHoliday()+ "," +
            "isBreakDay" + ":" + getIsBreakDay()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "week = "+(getWeek()!=null?Integer.toHexString(System.identityHashCode(getWeek())):"null");
  }
}