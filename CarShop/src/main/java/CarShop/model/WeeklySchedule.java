/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;
import java.util.*;

// line 36 "../../CarShop.ump"
public class WeeklySchedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeeklySchedule Attributes
  private List<Date> holidaySchedule;

  //WeeklySchedule Associations
  private CarShop shop;
  private List<Schedule> days;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WeeklySchedule(CarShop aShop)
  {
    holidaySchedule = new ArrayList<Date>();
    boolean didAddShop = setShop(aShop);
    if (!didAddShop)
    {
      throw new RuntimeException("Unable to create weeklySchedule due to shop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    days = new ArrayList<Schedule>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetMany */
  public boolean addHolidaySchedule(Date aHolidaySchedule)
  {
    boolean wasAdded = false;
    wasAdded = holidaySchedule.add(aHolidaySchedule);
    return wasAdded;
  }

  public boolean removeHolidaySchedule(Date aHolidaySchedule)
  {
    boolean wasRemoved = false;
    wasRemoved = holidaySchedule.remove(aHolidaySchedule);
    return wasRemoved;
  }
  /* Code from template attribute_GetMany */
  public Date getHolidaySchedule(int index)
  {
    Date aHolidaySchedule = holidaySchedule.get(index);
    return aHolidaySchedule;
  }

  public Date[] getHolidaySchedule()
  {
    Date[] newHolidaySchedule = holidaySchedule.toArray(new Date[holidaySchedule.size()]);
    return newHolidaySchedule;
  }

  public int numberOfHolidaySchedule()
  {
    int number = holidaySchedule.size();
    return number;
  }

  public boolean hasHolidaySchedule()
  {
    boolean has = holidaySchedule.size() > 0;
    return has;
  }

  public int indexOfHolidaySchedule(Date aHolidaySchedule)
  {
    int index = holidaySchedule.indexOf(aHolidaySchedule);
    return index;
  }
  /* Code from template association_GetOne */
  public CarShop getShop()
  {
    return shop;
  }
  /* Code from template association_GetMany */
  public Schedule getDay(int index)
  {
    Schedule aDay = days.get(index);
    return aDay;
  }

  public List<Schedule> getDays()
  {
    List<Schedule> newDays = Collections.unmodifiableList(days);
    return newDays;
  }

  public int numberOfDays()
  {
    int number = days.size();
    return number;
  }

  public boolean hasDays()
  {
    boolean has = days.size() > 0;
    return has;
  }

  public int indexOfDay(Schedule aDay)
  {
    int index = days.indexOf(aDay);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setShop(CarShop aShop)
  {
    boolean wasSet = false;
    if (aShop == null)
    {
      return wasSet;
    }

    CarShop existingShop = shop;
    shop = aShop;
    if (existingShop != null && !existingShop.equals(aShop))
    {
      existingShop.removeWeeklySchedule(this);
    }
    shop.addWeeklySchedule(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfDaysValid()
  {
    boolean isValid = numberOfDays() >= minimumNumberOfDays() && numberOfDays() <= maximumNumberOfDays();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfDays()
  {
    return 7;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDays()
  {
    return 7;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDays()
  {
    return 7;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Schedule addDay(boolean aIsHoliday, boolean aIsBreakDay, Date aStartTime, Date aEndTime, Garage aGarage)
  {
    if (numberOfDays() >= maximumNumberOfDays())
    {
      return null;
    }
    else
    {
      return new Schedule(aIsHoliday, aIsBreakDay, aStartTime, aEndTime, this, aGarage);
    }
  }

  public boolean addDay(Schedule aDay)
  {
    boolean wasAdded = false;
    if (days.contains(aDay)) { return false; }
    if (numberOfDays() >= maximumNumberOfDays())
    {
      return wasAdded;
    }

    WeeklySchedule existingWeek = aDay.getWeek();
    boolean isNewWeek = existingWeek != null && !this.equals(existingWeek);

    if (isNewWeek && existingWeek.numberOfDays() <= minimumNumberOfDays())
    {
      return wasAdded;
    }

    if (isNewWeek)
    {
      aDay.setWeek(this);
    }
    else
    {
      days.add(aDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Schedule aDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aDay, as it must always have a week
    if (this.equals(aDay.getWeek()))
    {
      return wasRemoved;
    }

    //week already at minimum (7)
    if (numberOfDays() <= minimumNumberOfDays())
    {
      return wasRemoved;
    }
    days.remove(aDay);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    CarShop placeholderShop = shop;
    this.shop = null;
    if(placeholderShop != null)
    {
      placeholderShop.removeWeeklySchedule(this);
    }
    for(int i=days.size(); i > 0; i--)
    {
      Schedule aDay = days.get(i - 1);
      aDay.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shop = "+(getShop()!=null?Integer.toHexString(System.identityHashCode(getShop())):"null");
  }
}