/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;
import java.util.*;

// line 89 "../../CarShop.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private int daysBeforeAppointment;
  private Date startTime;
  private Date endTime;
  private ServiceList service;

  //Appointment Associations
  private List<ServiceList> serviceLists;
  private List<CustomerAccount> appt;
  private Appointment nextAppt;
  private AppointmentCalendar calendar;
  private Appointment prevAcct;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(int aDaysBeforeAppointment, Date aStartTime, Date aEndTime, ServiceList aService, AppointmentCalendar aCalendar)
  {
    daysBeforeAppointment = aDaysBeforeAppointment;
    startTime = aStartTime;
    endTime = aEndTime;
    service = aService;
    serviceLists = new ArrayList<ServiceList>();
    appt = new ArrayList<CustomerAccount>();
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create appointment due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDaysBeforeAppointment(int aDaysBeforeAppointment)
  {
    boolean wasSet = false;
    daysBeforeAppointment = aDaysBeforeAppointment;
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

  public boolean setService(ServiceList aService)
  {
    boolean wasSet = false;
    service = aService;
    wasSet = true;
    return wasSet;
  }

  public int getDaysBeforeAppointment()
  {
    return daysBeforeAppointment;
  }

  public Date getStartTime()
  {
    return startTime;
  }

  public Date getEndTime()
  {
    return endTime;
  }

  public ServiceList getService()
  {
    return service;
  }
  /* Code from template association_GetMany */
  public ServiceList getServiceList(int index)
  {
    ServiceList aServiceList = serviceLists.get(index);
    return aServiceList;
  }

  public List<ServiceList> getServiceLists()
  {
    List<ServiceList> newServiceLists = Collections.unmodifiableList(serviceLists);
    return newServiceLists;
  }

  public int numberOfServiceLists()
  {
    int number = serviceLists.size();
    return number;
  }

  public boolean hasServiceLists()
  {
    boolean has = serviceLists.size() > 0;
    return has;
  }

  public int indexOfServiceList(ServiceList aServiceList)
  {
    int index = serviceLists.indexOf(aServiceList);
    return index;
  }
  /* Code from template association_GetMany */
  public CustomerAccount getAppt(int index)
  {
    CustomerAccount aAppt = appt.get(index);
    return aAppt;
  }

  public List<CustomerAccount> getAppt()
  {
    List<CustomerAccount> newAppt = Collections.unmodifiableList(appt);
    return newAppt;
  }

  public int numberOfAppt()
  {
    int number = appt.size();
    return number;
  }

  public boolean hasAppt()
  {
    boolean has = appt.size() > 0;
    return has;
  }

  public int indexOfAppt(CustomerAccount aAppt)
  {
    int index = appt.indexOf(aAppt);
    return index;
  }
  /* Code from template association_GetOne */
  public Appointment getNextAppt()
  {
    return nextAppt;
  }

  public boolean hasNextAppt()
  {
    boolean has = nextAppt != null;
    return has;
  }
  /* Code from template association_GetOne */
  public AppointmentCalendar getCalendar()
  {
    return calendar;
  }
  /* Code from template association_GetOne */
  public Appointment getPrevAcct()
  {
    return prevAcct;
  }

  public boolean hasPrevAcct()
  {
    boolean has = prevAcct != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceLists()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceList addServiceList(Service aServiceName, ServiceList.ComboType aComboType, boolean aIsServiceCombo, Service aMainService, Owner aOwner)
  {
    return new ServiceList(aServiceName, aComboType, aIsServiceCombo, aMainService, aOwner, this);
  }

  public boolean addServiceList(ServiceList aServiceList)
  {
    boolean wasAdded = false;
    if (serviceLists.contains(aServiceList)) { return false; }
    Appointment existingAppointment = aServiceList.getAppointment();
    boolean isNewAppointment = existingAppointment != null && !this.equals(existingAppointment);
    if (isNewAppointment)
    {
      aServiceList.setAppointment(this);
    }
    else
    {
      serviceLists.add(aServiceList);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServiceList(ServiceList aServiceList)
  {
    boolean wasRemoved = false;
    //Unable to remove aServiceList, as it must always have a appointment
    if (!this.equals(aServiceList.getAppointment()))
    {
      serviceLists.remove(aServiceList);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceListAt(ServiceList aServiceList, int index)
  {  
    boolean wasAdded = false;
    if(addServiceList(aServiceList))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceLists()) { index = numberOfServiceLists() - 1; }
      serviceLists.remove(aServiceList);
      serviceLists.add(index, aServiceList);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceListAt(ServiceList aServiceList, int index)
  {
    boolean wasAdded = false;
    if(serviceLists.contains(aServiceList))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceLists()) { index = numberOfServiceLists() - 1; }
      serviceLists.remove(aServiceList);
      serviceLists.add(index, aServiceList);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceListAt(aServiceList, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppt()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public CustomerAccount addAppt(String aUsername, String aPassword, boolean aIsLoggedIn, AppointmentCalendar aCalendar)
  {
    return new CustomerAccount(aUsername, aPassword, aIsLoggedIn, aCalendar, this);
  }

  public boolean addAppt(CustomerAccount aAppt)
  {
    boolean wasAdded = false;
    if (appt.contains(aAppt)) { return false; }
    Appointment existingCustomer = aAppt.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aAppt.setCustomer(this);
    }
    else
    {
      appt.add(aAppt);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppt(CustomerAccount aAppt)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppt, as it must always have a customer
    if (!this.equals(aAppt.getCustomer()))
    {
      appt.remove(aAppt);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addApptAt(CustomerAccount aAppt, int index)
  {  
    boolean wasAdded = false;
    if(addAppt(aAppt))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppt()) { index = numberOfAppt() - 1; }
      appt.remove(aAppt);
      appt.add(index, aAppt);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveApptAt(CustomerAccount aAppt, int index)
  {
    boolean wasAdded = false;
    if(appt.contains(aAppt))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppt()) { index = numberOfAppt() - 1; }
      appt.remove(aAppt);
      appt.add(index, aAppt);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addApptAt(aAppt, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setNextAppt(Appointment aNewNextAppt)
  {
    boolean wasSet = false;
    if (aNewNextAppt == null)
    {
      Appointment existingNextAppt = nextAppt;
      nextAppt = null;
      
      if (existingNextAppt != null && existingNextAppt.getPrevAcct() != null)
      {
        existingNextAppt.setPrevAcct(null);
      }
      wasSet = true;
      return wasSet;
    }

    Appointment currentNextAppt = getNextAppt();
    if (currentNextAppt != null && !currentNextAppt.equals(aNewNextAppt))
    {
      currentNextAppt.setPrevAcct(null);
    }

    nextAppt = aNewNextAppt;
    Appointment existingPrevAcct = aNewNextAppt.getPrevAcct();

    if (!equals(existingPrevAcct))
    {
      aNewNextAppt.setPrevAcct(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCalendar(AppointmentCalendar aCalendar)
  {
    boolean wasSet = false;
    if (aCalendar == null)
    {
      return wasSet;
    }

    AppointmentCalendar existingCalendar = calendar;
    calendar = aCalendar;
    if (existingCalendar != null && !existingCalendar.equals(aCalendar))
    {
      existingCalendar.removeAppointment(this);
    }
    calendar.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPrevAcct(Appointment aNewPrevAcct)
  {
    boolean wasSet = false;
    if (aNewPrevAcct == null)
    {
      Appointment existingPrevAcct = prevAcct;
      prevAcct = null;
      
      if (existingPrevAcct != null && existingPrevAcct.getNextAppt() != null)
      {
        existingPrevAcct.setNextAppt(null);
      }
      wasSet = true;
      return wasSet;
    }

    Appointment currentPrevAcct = getPrevAcct();
    if (currentPrevAcct != null && !currentPrevAcct.equals(aNewPrevAcct))
    {
      currentPrevAcct.setNextAppt(null);
    }

    prevAcct = aNewPrevAcct;
    Appointment existingNextAppt = aNewPrevAcct.getNextAppt();

    if (!equals(existingNextAppt))
    {
      aNewPrevAcct.setNextAppt(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (serviceLists.size() > 0)
    {
      ServiceList aServiceList = serviceLists.get(serviceLists.size() - 1);
      aServiceList.delete();
      serviceLists.remove(aServiceList);
    }
    
    for(int i=appt.size(); i > 0; i--)
    {
      CustomerAccount aAppt = appt.get(i - 1);
      aAppt.delete();
    }
    if (nextAppt != null)
    {
      nextAppt.setPrevAcct(null);
    }
    AppointmentCalendar placeholderCalendar = calendar;
    this.calendar = null;
    if(placeholderCalendar != null)
    {
      placeholderCalendar.removeAppointment(this);
    }
    if (prevAcct != null)
    {
      prevAcct.setNextAppt(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "daysBeforeAppointment" + ":" + getDaysBeforeAppointment()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "service" + "=" + (getService() != null ? !getService().equals(this)  ? getService().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}