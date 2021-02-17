/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 25 "../../CarShop.ump"
public class WeeklyBusinessHours
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeeklyBusinessHours Associations
  private CarShop shop;
  private List<Schedule> days;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WeeklyBusinessHours(CarShop aShop)
  {
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

    WeeklyBusinessHours existingWeek = aDay.getWeek();
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

}