/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;

// line 56 "../../CarShop.ump"
public class TimeSlot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private Date startTime;
  private Date endTime;
  private int daysBeforeAppointment;

  //TimeSlot Associations
  private Service currentService;
  private TechnicianDailySchedule technicianDailySchedule;
  private AppointmentInfo appointmentInfo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Date aStartTime, Date aEndTime, int aDaysBeforeAppointment, Service aCurrentService, TechnicianDailySchedule aTechnicianDailySchedule, AppointmentInfo aAppointmentInfo)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    daysBeforeAppointment = aDaysBeforeAppointment;
    boolean didAddCurrentService = setCurrentService(aCurrentService);
    if (!didAddCurrentService)
    {
      throw new RuntimeException("Unable to create timeSlot due to currentService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTechnicianDailySchedule = setTechnicianDailySchedule(aTechnicianDailySchedule);
    if (!didAddTechnicianDailySchedule)
    {
      throw new RuntimeException("Unable to create timeSlot due to technicianDailySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aAppointmentInfo == null || aAppointmentInfo.getTimeSlot() != null)
    {
      throw new RuntimeException("Unable to create TimeSlot due to aAppointmentInfo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointmentInfo = aAppointmentInfo;
  }

  public TimeSlot(Date aStartTime, Date aEndTime, int aDaysBeforeAppointment, Service aCurrentService, TechnicianDailySchedule aTechnicianDailySchedule, boolean aCustomerCanCancelForAppointmentInfo, int aDaysBeforeApptForAppointmentInfo, CustomerAccount aCustomerAccountForAppointmentInfo)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    daysBeforeAppointment = aDaysBeforeAppointment;
    boolean didAddCurrentService = setCurrentService(aCurrentService);
    if (!didAddCurrentService)
    {
      throw new RuntimeException("Unable to create timeSlot due to currentService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTechnicianDailySchedule = setTechnicianDailySchedule(aTechnicianDailySchedule);
    if (!didAddTechnicianDailySchedule)
    {
      throw new RuntimeException("Unable to create timeSlot due to technicianDailySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointmentInfo = new AppointmentInfo(aCustomerCanCancelForAppointmentInfo, aDaysBeforeApptForAppointmentInfo, aCustomerAccountForAppointmentInfo, this);
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

  public boolean setDaysBeforeAppointment(int aDaysBeforeAppointment)
  {
    boolean wasSet = false;
    daysBeforeAppointment = aDaysBeforeAppointment;
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

  public int getDaysBeforeAppointment()
  {
    return daysBeforeAppointment;
  }
  /* Code from template association_GetOne */
  public Service getCurrentService()
  {
    return currentService;
  }
  /* Code from template association_GetOne */
  public TechnicianDailySchedule getTechnicianDailySchedule()
  {
    return technicianDailySchedule;
  }
  /* Code from template association_GetOne */
  public AppointmentInfo getAppointmentInfo()
  {
    return appointmentInfo;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCurrentService(Service aCurrentService)
  {
    boolean wasSet = false;
    if (aCurrentService == null)
    {
      return wasSet;
    }

    Service existingCurrentService = currentService;
    currentService = aCurrentService;
    if (existingCurrentService != null && !existingCurrentService.equals(aCurrentService))
    {
      existingCurrentService.removeTimeSlot(this);
    }
    currentService.addTimeSlot(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTechnicianDailySchedule(TechnicianDailySchedule aTechnicianDailySchedule)
  {
    boolean wasSet = false;
    if (aTechnicianDailySchedule == null)
    {
      return wasSet;
    }

    TechnicianDailySchedule existingTechnicianDailySchedule = technicianDailySchedule;
    technicianDailySchedule = aTechnicianDailySchedule;
    if (existingTechnicianDailySchedule != null && !existingTechnicianDailySchedule.equals(aTechnicianDailySchedule))
    {
      existingTechnicianDailySchedule.removeTimeSlot(this);
    }
    technicianDailySchedule.addTimeSlot(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Service placeholderCurrentService = currentService;
    this.currentService = null;
    if(placeholderCurrentService != null)
    {
      placeholderCurrentService.removeTimeSlot(this);
    }
    TechnicianDailySchedule placeholderTechnicianDailySchedule = technicianDailySchedule;
    this.technicianDailySchedule = null;
    if(placeholderTechnicianDailySchedule != null)
    {
      placeholderTechnicianDailySchedule.removeTimeSlot(this);
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
    return super.toString() + "["+
            "daysBeforeAppointment" + ":" + getDaysBeforeAppointment()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentService = "+(getCurrentService()!=null?Integer.toHexString(System.identityHashCode(getCurrentService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "technicianDailySchedule = "+(getTechnicianDailySchedule()!=null?Integer.toHexString(System.identityHashCode(getTechnicianDailySchedule())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentInfo = "+(getAppointmentInfo()!=null?Integer.toHexString(System.identityHashCode(getAppointmentInfo())):"null");
  }
}