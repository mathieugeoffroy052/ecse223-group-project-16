/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;

// line 120 "../../CarShop.ump"
public class AppointmentInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AppointmentInfo Attributes
  private int daysBeforeAppt;

  //AppointmentInfo Associations
  private CustomerAccount customerAccount;
  private TimeSlot timeSlot;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetCustomerAccount;
  private boolean canSetTimeSlot;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AppointmentInfo(int aDaysBeforeAppt, CustomerAccount aCustomerAccount, TimeSlot aTimeSlot)
  {
    cachedHashCode = -1;
    canSetCustomerAccount = true;
    canSetTimeSlot = true;
    daysBeforeAppt = aDaysBeforeAppt;
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create appointmentInfo due to customerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aTimeSlot == null || aTimeSlot.getAppointmentInfo() != null)
    {
      throw new RuntimeException("Unable to create AppointmentInfo due to aTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    timeSlot = aTimeSlot;
  }

  public AppointmentInfo(int aDaysBeforeAppt, CustomerAccount aCustomerAccount, Date aStartTimeForTimeSlot, Date aEndTimeForTimeSlot, DailySchedule aDailyScheduleForTimeSlot)
  {
    daysBeforeAppt = aDaysBeforeAppt;
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create appointmentInfo due to customerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    timeSlot = new TimeSlot(aStartTimeForTimeSlot, aEndTimeForTimeSlot, aDailyScheduleForTimeSlot, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDaysBeforeAppt(int aDaysBeforeAppt)
  {
    boolean wasSet = false;
    daysBeforeAppt = aDaysBeforeAppt;
    wasSet = true;
    return wasSet;
  }

  public int getDaysBeforeAppt()
  {
    return daysBeforeAppt;
  }
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_GetOne */
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setCustomerAccount(CustomerAccount aCustomerAccount)
  {
    boolean wasSet = false;
    if (!canSetCustomerAccount) { return false; }
    if (aCustomerAccount == null)
    {
      return wasSet;
    }

    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = aCustomerAccount;
    if (existingCustomerAccount != null && !existingCustomerAccount.equals(aCustomerAccount))
    {
      existingCustomerAccount.removeAppointmentInfo(this);
    }
    if (!customerAccount.addAppointmentInfo(this))
    {
      customerAccount = existingCustomerAccount;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    AppointmentInfo compareTo = (AppointmentInfo)obj;
  
    if (getCustomerAccount() == null && compareTo.getCustomerAccount() != null)
    {
      return false;
    }
    else if (getCustomerAccount() != null && !getCustomerAccount().equals(compareTo.getCustomerAccount()))
    {
      return false;
    }

    if (getTimeSlot() == null && compareTo.getTimeSlot() != null)
    {
      return false;
    }
    else if (getTimeSlot() != null && !getTimeSlot().equals(compareTo.getTimeSlot()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getCustomerAccount() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getCustomerAccount().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getTimeSlot() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getTimeSlot().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetCustomerAccount = false;
    canSetTimeSlot = false;
    return cachedHashCode;
  }

  public void delete()
  {
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if(placeholderCustomerAccount != null)
    {
      placeholderCustomerAccount.removeAppointmentInfo(this);
    }
    TimeSlot existingTimeSlot = timeSlot;
    timeSlot = null;
    if (existingTimeSlot != null)
    {
      existingTimeSlot.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "daysBeforeAppt" + ":" + getDaysBeforeAppt()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null");
  }
}