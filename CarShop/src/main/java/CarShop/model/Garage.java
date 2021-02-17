/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 57 "../../CarShop.ump"
public class Garage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Garage Associations
  private List<Schedule> activeDays;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Garage()
  {
    activeDays = new ArrayList<Schedule>();
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfActiveDays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Schedule addActiveDay(boolean aIsHoliday, boolean aIsBreakDay, Date aStartTime, Date aEndTime, WeeklyBusinessHours aWeek)
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

  public void delete()
  {
    for(int i=activeDays.size(); i > 0; i--)
    {
      Schedule aActiveDay = activeDays.get(i - 1);
      aActiveDay.delete();
    }
  }

}