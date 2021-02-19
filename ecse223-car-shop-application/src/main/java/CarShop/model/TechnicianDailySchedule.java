/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 51 "../../CarShop.ump"
public class TechnicianDailySchedule extends GeneralDailySchedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TechnicianDailySchedule Associations
  private List<TimeSlot> timeSlots;
  private TechnicianAccount technician;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TechnicianDailySchedule(DayType aDayType, Date aOpeningTime, Date aClosingTime, Date aCurrentDate)
  {
    super(aDayType, aOpeningTime, aClosingTime, aCurrentDate);
    timeSlots = new ArrayList<TimeSlot>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public TimeSlot getTimeSlot(int index)
  {
    TimeSlot aTimeSlot = timeSlots.get(index);
    return aTimeSlot;
  }

  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
  }

  public int numberOfTimeSlots()
  {
    int number = timeSlots.size();
    return number;
  }

  public boolean hasTimeSlots()
  {
    boolean has = timeSlots.size() > 0;
    return has;
  }

  public int indexOfTimeSlot(TimeSlot aTimeSlot)
  {
    int index = timeSlots.indexOf(aTimeSlot);
    return index;
  }
  /* Code from template association_GetOne */
  public TechnicianAccount getTechnician()
  {
    return technician;
  }

  public boolean hasTechnician()
  {
    boolean has = technician != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TimeSlot addTimeSlot(Date aStartTime, Date aEndTime, int aDaysBeforeAppointment, Service aCurrentService, AppointmentInfo aAppointmentInfo)
  {
    return new TimeSlot(aStartTime, aEndTime, aDaysBeforeAppointment, aCurrentService, this, aAppointmentInfo);
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    TechnicianDailySchedule existingTechnicianDailySchedule = aTimeSlot.getTechnicianDailySchedule();
    boolean isNewTechnicianDailySchedule = existingTechnicianDailySchedule != null && !this.equals(existingTechnicianDailySchedule);
    if (isNewTechnicianDailySchedule)
    {
      aTimeSlot.setTechnicianDailySchedule(this);
    }
    else
    {
      timeSlots.add(aTimeSlot);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasRemoved = false;
    //Unable to remove aTimeSlot, as it must always have a technicianDailySchedule
    if (!this.equals(aTimeSlot.getTechnicianDailySchedule()))
    {
      timeSlots.remove(aTimeSlot);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTimeSlotAt(TimeSlot aTimeSlot, int index)
  {  
    boolean wasAdded = false;
    if(addTimeSlot(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTimeSlotAt(TimeSlot aTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(timeSlots.contains(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTimeSlotAt(aTimeSlot, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setTechnician(TechnicianAccount aTechnician)
  {
    boolean wasSet = false;
    TechnicianAccount existingTechnician = technician;
    technician = aTechnician;
    if (existingTechnician != null && !existingTechnician.equals(aTechnician))
    {
      existingTechnician.removeDailySchedule(this);
    }
    if (aTechnician != null)
    {
      aTechnician.addDailySchedule(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=timeSlots.size(); i > 0; i--)
    {
      TimeSlot aTimeSlot = timeSlots.get(i - 1);
      aTimeSlot.delete();
    }
    if (technician != null)
    {
      TechnicianAccount placeholderTechnician = technician;
      this.technician = null;
      placeholderTechnician.removeDailySchedule(this);
    }
    super.delete();
  }

}