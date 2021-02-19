/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 63 "../../CarShop.ump"
public class Service
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Service> servicesByName = new HashMap<String, Service>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String name;
  private boolean isCompleted;
  private double durationInHours;

  //Service Associations
  private Service successor;
  private CarShop carShop;
  private List<TimeSlot> timeSlots;
  private List<Service> prerequesite;
  private ServiceList serviceList;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aName, boolean aIsCompleted, double aDurationInHours, CarShop aCarShop, ServiceList aServiceList)
  {
    // line 67 "../../CarShop.ump"
    if (aName == null || aName.length() == 0) {
    			throw new RuntimeException("Please enter a valid name");
    			}
    // END OF UMPLE BEFORE INJECTION
    isCompleted = aIsCompleted;
    durationInHours = aDurationInHours;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddCarShop = setCarShop(aCarShop);
    if (!didAddCarShop)
    {
      throw new RuntimeException("Unable to create servicesOffered due to carShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    timeSlots = new ArrayList<TimeSlot>();
    prerequesite = new ArrayList<Service>();
    boolean didAddServiceList = setServiceList(aServiceList);
    if (!didAddServiceList)
    {
      throw new RuntimeException("Unable to create service due to serviceList. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    // line 67 "../../CarShop.ump"
    if (aName == null || aName.length() == 0) {
    			throw new RuntimeException("Please enter a valid name");
    			}
    // END OF UMPLE BEFORE INJECTION
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      servicesByName.remove(anOldName);
    }
    servicesByName.put(aName, this);
    return wasSet;
  }

  public boolean setIsCompleted(boolean aIsCompleted)
  {
    boolean wasSet = false;
    isCompleted = aIsCompleted;
    wasSet = true;
    return wasSet;
  }

  public boolean setDurationInHours(double aDurationInHours)
  {
    boolean wasSet = false;
    durationInHours = aDurationInHours;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static Service getWithName(String aName)
  {
    return servicesByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public boolean getIsCompleted()
  {
    return isCompleted;
  }

  public double getDurationInHours()
  {
    return durationInHours;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsCompleted()
  {
    return isCompleted;
  }
  /* Code from template association_GetOne */
  public Service getSuccessor()
  {
    return successor;
  }

  public boolean hasSuccessor()
  {
    boolean has = successor != null;
    return has;
  }
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
  }
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
  /* Code from template association_GetMany */
  public Service getPrerequesite(int index)
  {
    Service aPrerequesite = prerequesite.get(index);
    return aPrerequesite;
  }

  public List<Service> getPrerequesite()
  {
    List<Service> newPrerequesite = Collections.unmodifiableList(prerequesite);
    return newPrerequesite;
  }

  public int numberOfPrerequesite()
  {
    int number = prerequesite.size();
    return number;
  }

  public boolean hasPrerequesite()
  {
    boolean has = prerequesite.size() > 0;
    return has;
  }

  public int indexOfPrerequesite(Service aPrerequesite)
  {
    int index = prerequesite.indexOf(aPrerequesite);
    return index;
  }
  /* Code from template association_GetOne */
  public ServiceList getServiceList()
  {
    return serviceList;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setSuccessor(Service aSuccessor)
  {
    boolean wasSet = false;
    Service existingSuccessor = successor;
    successor = aSuccessor;
    if (existingSuccessor != null && !existingSuccessor.equals(aSuccessor))
    {
      existingSuccessor.removePrerequesite(this);
    }
    if (aSuccessor != null)
    {
      aSuccessor.addPrerequesite(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCarShop(CarShop aCarShop)
  {
    boolean wasSet = false;
    if (aCarShop == null)
    {
      return wasSet;
    }

    CarShop existingCarShop = carShop;
    carShop = aCarShop;
    if (existingCarShop != null && !existingCarShop.equals(aCarShop))
    {
      existingCarShop.removeServicesOffered(this);
    }
    carShop.addServicesOffered(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TimeSlot addTimeSlot(Date aStartTime, Date aEndTime, int aDaysBeforeAppointment, TechnicianDailySchedule aTechnicianDailySchedule, AppointmentInfo aAppointmentInfo)
  {
    return new TimeSlot(aStartTime, aEndTime, aDaysBeforeAppointment, this, aTechnicianDailySchedule, aAppointmentInfo);
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    Service existingCurrentService = aTimeSlot.getCurrentService();
    boolean isNewCurrentService = existingCurrentService != null && !this.equals(existingCurrentService);
    if (isNewCurrentService)
    {
      aTimeSlot.setCurrentService(this);
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
    //Unable to remove aTimeSlot, as it must always have a currentService
    if (!this.equals(aTimeSlot.getCurrentService()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPrerequesite()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addPrerequesite(Service aPrerequesite)
  {
    boolean wasAdded = false;
    if (prerequesite.contains(aPrerequesite)) { return false; }
    Service existingSuccessor = aPrerequesite.getSuccessor();
    if (existingSuccessor == null)
    {
      aPrerequesite.setSuccessor(this);
    }
    else if (!this.equals(existingSuccessor))
    {
      existingSuccessor.removePrerequesite(aPrerequesite);
      addPrerequesite(aPrerequesite);
    }
    else
    {
      prerequesite.add(aPrerequesite);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePrerequesite(Service aPrerequesite)
  {
    boolean wasRemoved = false;
    if (prerequesite.contains(aPrerequesite))
    {
      prerequesite.remove(aPrerequesite);
      aPrerequesite.setSuccessor(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPrerequesiteAt(Service aPrerequesite, int index)
  {  
    boolean wasAdded = false;
    if(addPrerequesite(aPrerequesite))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrerequesite()) { index = numberOfPrerequesite() - 1; }
      prerequesite.remove(aPrerequesite);
      prerequesite.add(index, aPrerequesite);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePrerequesiteAt(Service aPrerequesite, int index)
  {
    boolean wasAdded = false;
    if(prerequesite.contains(aPrerequesite))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrerequesite()) { index = numberOfPrerequesite() - 1; }
      prerequesite.remove(aPrerequesite);
      prerequesite.add(index, aPrerequesite);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPrerequesiteAt(aPrerequesite, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setServiceList(ServiceList aServiceList)
  {
    boolean wasSet = false;
    if (aServiceList == null)
    {
      return wasSet;
    }

    ServiceList existingServiceList = serviceList;
    serviceList = aServiceList;
    if (existingServiceList != null && !existingServiceList.equals(aServiceList))
    {
      existingServiceList.removeService(this);
    }
    serviceList.addService(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    servicesByName.remove(getName());
    if (successor != null)
    {
      Service placeholderSuccessor = successor;
      this.successor = null;
      placeholderSuccessor.removePrerequesite(this);
    }
    CarShop placeholderCarShop = carShop;
    this.carShop = null;
    if(placeholderCarShop != null)
    {
      placeholderCarShop.removeServicesOffered(this);
    }
    for(int i=timeSlots.size(); i > 0; i--)
    {
      TimeSlot aTimeSlot = timeSlots.get(i - 1);
      aTimeSlot.delete();
    }
    while( !prerequesite.isEmpty() )
    {
      prerequesite.get(0).setSuccessor(null);
    }
    ServiceList placeholderServiceList = serviceList;
    this.serviceList = null;
    if(placeholderServiceList != null)
    {
      placeholderServiceList.removeService(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "isCompleted" + ":" + getIsCompleted()+ "," +
            "durationInHours" + ":" + getDurationInHours()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "carShop = "+(getCarShop()!=null?Integer.toHexString(System.identityHashCode(getCarShop())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceList = "+(getServiceList()!=null?Integer.toHexString(System.identityHashCode(getServiceList())):"null");
  }
}