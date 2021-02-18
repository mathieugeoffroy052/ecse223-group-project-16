/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;

// line 99 "../../CarShop.ump"
public class TimeSlot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private Date startTime;
  private Date endTime;

  //TimeSlot Associations
  private ServiceList serviceList;
  private TimeSlot nextTimeSlot;
  private DailySchedule dailySchedule;
  private TimeSlot prevTimeSlot;
  private AppointmentInfo appointmentInfo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Date aStartTime, Date aEndTime, DailySchedule aDailySchedule, AppointmentInfo aAppointmentInfo)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddDailySchedule = setDailySchedule(aDailySchedule);
    if (!didAddDailySchedule)
    {
      throw new RuntimeException("Unable to create firstAppointment due to dailySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aAppointmentInfo == null || aAppointmentInfo.getTimeSlot() != null)
    {
      throw new RuntimeException("Unable to create TimeSlot due to aAppointmentInfo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointmentInfo = aAppointmentInfo;
  }

  public TimeSlot(Date aStartTime, Date aEndTime, DailySchedule aDailySchedule, int aDaysBeforeApptForAppointmentInfo, CustomerAccount aCustomerAccountForAppointmentInfo)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddDailySchedule = setDailySchedule(aDailySchedule);
    if (!didAddDailySchedule)
    {
      throw new RuntimeException("Unable to create firstAppointment due to dailySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointmentInfo = new AppointmentInfo(aDaysBeforeApptForAppointmentInfo, aCustomerAccountForAppointmentInfo, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public Date getStartTime()
  {
    return startTime;
  }

  public Date getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  public ServiceList getServiceList()
  {
    return serviceList;
  }

  public boolean hasServiceList()
  {
    boolean has = serviceList != null;
    return has;
  }
  /* Code from template association_GetOne */
  public TimeSlot getNextTimeSlot()
  {
    return nextTimeSlot;
  }

  public boolean hasNextTimeSlot()
  {
    boolean has = nextTimeSlot != null;
    return has;
  }
  /* Code from template association_GetOne */
  public DailySchedule getDailySchedule()
  {
    return dailySchedule;
  }
  /* Code from template association_GetOne */
  public TimeSlot getPrevTimeSlot()
  {
    return prevTimeSlot;
  }

  public boolean hasPrevTimeSlot()
  {
    boolean has = prevTimeSlot != null;
    return has;
  }
  /* Code from template association_GetOne */
  public AppointmentInfo getAppointmentInfo()
  {
    return appointmentInfo;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setServiceList(ServiceList aNewServiceList)
  {
    boolean wasSet = false;
    if (serviceList != null && !serviceList.equals(aNewServiceList) && equals(serviceList.getTimeSlot()))
    {
      //Unable to setServiceList, as existing serviceList would become an orphan
      return wasSet;
    }

    serviceList = aNewServiceList;
    TimeSlot anOldTimeSlot = aNewServiceList != null ? aNewServiceList.getTimeSlot() : null;

    if (!this.equals(anOldTimeSlot))
    {
      if (anOldTimeSlot != null)
      {
        anOldTimeSlot.serviceList = null;
      }
      if (serviceList != null)
      {
        serviceList.setTimeSlot(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setNextTimeSlot(TimeSlot aNewNextTimeSlot)
  {
    boolean wasSet = false;
    if (aNewNextTimeSlot == null)
    {
      TimeSlot existingNextTimeSlot = nextTimeSlot;
      nextTimeSlot = null;
      
      if (existingNextTimeSlot != null && existingNextTimeSlot.getPrevTimeSlot() != null)
      {
        existingNextTimeSlot.setPrevTimeSlot(null);
      }
      wasSet = true;
      return wasSet;
    }

    TimeSlot currentNextTimeSlot = getNextTimeSlot();
    if (currentNextTimeSlot != null && !currentNextTimeSlot.equals(aNewNextTimeSlot))
    {
      currentNextTimeSlot.setPrevTimeSlot(null);
    }

    nextTimeSlot = aNewNextTimeSlot;
    TimeSlot existingPrevTimeSlot = aNewNextTimeSlot.getPrevTimeSlot();

    if (!equals(existingPrevTimeSlot))
    {
      aNewNextTimeSlot.setPrevTimeSlot(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setDailySchedule(DailySchedule aNewDailySchedule)
  {
    boolean wasSet = false;
    if (aNewDailySchedule == null)
    {
      //Unable to setDailySchedule to null, as firstAppointment must always be associated to a dailySchedule
      return wasSet;
    }
    
    TimeSlot existingFirstAppointment = aNewDailySchedule.getFirstAppointment();
    if (existingFirstAppointment != null && !equals(existingFirstAppointment))
    {
      //Unable to setDailySchedule, the current dailySchedule already has a firstAppointment, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    DailySchedule anOldDailySchedule = dailySchedule;
    dailySchedule = aNewDailySchedule;
    dailySchedule.setFirstAppointment(this);

    if (anOldDailySchedule != null)
    {
      anOldDailySchedule.setFirstAppointment(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPrevTimeSlot(TimeSlot aNewPrevTimeSlot)
  {
    boolean wasSet = false;
    if (aNewPrevTimeSlot == null)
    {
      TimeSlot existingPrevTimeSlot = prevTimeSlot;
      prevTimeSlot = null;
      
      if (existingPrevTimeSlot != null && existingPrevTimeSlot.getNextTimeSlot() != null)
      {
        existingPrevTimeSlot.setNextTimeSlot(null);
      }
      wasSet = true;
      return wasSet;
    }

    TimeSlot currentPrevTimeSlot = getPrevTimeSlot();
    if (currentPrevTimeSlot != null && !currentPrevTimeSlot.equals(aNewPrevTimeSlot))
    {
      currentPrevTimeSlot.setNextTimeSlot(null);
    }

    prevTimeSlot = aNewPrevTimeSlot;
    TimeSlot existingNextTimeSlot = aNewPrevTimeSlot.getNextTimeSlot();

    if (!equals(existingNextTimeSlot))
    {
      aNewPrevTimeSlot.setNextTimeSlot(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ServiceList existingServiceList = serviceList;
    serviceList = null;
    if (existingServiceList != null)
    {
      existingServiceList.delete();
      existingServiceList.setTimeSlot(null);
    }
    if (nextTimeSlot != null)
    {
      nextTimeSlot.setPrevTimeSlot(null);
    }
    DailySchedule existingDailySchedule = dailySchedule;
    dailySchedule = null;
    if (existingDailySchedule != null)
    {
      existingDailySchedule.setFirstAppointment(null);
    }
    if (prevTimeSlot != null)
    {
      prevTimeSlot.setNextTimeSlot(null);
    }
    AppointmentInfo existingAppointmentInfo = appointmentInfo;
    appointmentInfo = null;
    if (existingAppointmentInfo != null)
    {
      existingAppointmentInfo.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceList = "+(getServiceList()!=null?Integer.toHexString(System.identityHashCode(getServiceList())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "dailySchedule = "+(getDailySchedule()!=null?Integer.toHexString(System.identityHashCode(getDailySchedule())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentInfo = "+(getAppointmentInfo()!=null?Integer.toHexString(System.identityHashCode(getAppointmentInfo())):"null");
  }
}