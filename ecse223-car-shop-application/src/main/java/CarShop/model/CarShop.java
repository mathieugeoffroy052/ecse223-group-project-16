/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;
import java.util.*;

// line 3 "../../CarShop.ump"
public class CarShop
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CarShop Attributes
  private Date date;
  private String generalInfo;
  private String address;
  private String emailAddress;
  private String phoneNumber;

  //CarShop Associations
  private Owner ownedBy;
  private List<TechnicianAccount> employee;
  private List<Service> servicesOffered;
  private List<ServiceList> serviceListsOffered;
  private List<GeneralDailySchedule> generalDailySchedules;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CarShop(Date aDate, String aGeneralInfo, String aAddress, String aEmailAddress, String aPhoneNumber, Owner aOwnedBy)
  {
    date = aDate;
    generalInfo = aGeneralInfo;
    address = aAddress;
    emailAddress = aEmailAddress;
    phoneNumber = aPhoneNumber;
    if (aOwnedBy == null || aOwnedBy.getOwns() != null)
    {
      throw new RuntimeException("Unable to create CarShop due to aOwnedBy. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    ownedBy = aOwnedBy;
    employee = new ArrayList<TechnicianAccount>();
    servicesOffered = new ArrayList<Service>();
    serviceListsOffered = new ArrayList<ServiceList>();
    generalDailySchedules = new ArrayList<GeneralDailySchedule>();
  }

  public CarShop(Date aDate, String aGeneralInfo, String aAddress, String aEmailAddress, String aPhoneNumber, String aUsernameForOwnedBy, String aPasswordForOwnedBy, boolean aIsLoggedInForOwnedBy, String aWarningMessageForOwnedBy, User aUserForOwnedBy)
  {
    date = aDate;
    generalInfo = aGeneralInfo;
    address = aAddress;
    emailAddress = aEmailAddress;
    phoneNumber = aPhoneNumber;
    ownedBy = new Owner(aUsernameForOwnedBy, aPasswordForOwnedBy, aIsLoggedInForOwnedBy, aWarningMessageForOwnedBy, aUserForOwnedBy, this);
    employee = new ArrayList<TechnicianAccount>();
    servicesOffered = new ArrayList<Service>();
    serviceListsOffered = new ArrayList<ServiceList>();
    generalDailySchedules = new ArrayList<GeneralDailySchedule>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setGeneralInfo(String aGeneralInfo)
  {
    boolean wasSet = false;
    generalInfo = aGeneralInfo;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public String getGeneralInfo()
  {
    return generalInfo;
  }

  public String getAddress()
  {
    return address;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /* Code from template association_GetOne */
  public Owner getOwnedBy()
  {
    return ownedBy;
  }
  /* Code from template association_GetMany */
  public TechnicianAccount getEmployee(int index)
  {
    TechnicianAccount aEmployee = employee.get(index);
    return aEmployee;
  }

  public List<TechnicianAccount> getEmployee()
  {
    List<TechnicianAccount> newEmployee = Collections.unmodifiableList(employee);
    return newEmployee;
  }

  public int numberOfEmployee()
  {
    int number = employee.size();
    return number;
  }

  public boolean hasEmployee()
  {
    boolean has = employee.size() > 0;
    return has;
  }

  public int indexOfEmployee(TechnicianAccount aEmployee)
  {
    int index = employee.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_GetMany */
  public Service getServicesOffered(int index)
  {
    Service aServicesOffered = servicesOffered.get(index);
    return aServicesOffered;
  }

  public List<Service> getServicesOffered()
  {
    List<Service> newServicesOffered = Collections.unmodifiableList(servicesOffered);
    return newServicesOffered;
  }

  public int numberOfServicesOffered()
  {
    int number = servicesOffered.size();
    return number;
  }

  public boolean hasServicesOffered()
  {
    boolean has = servicesOffered.size() > 0;
    return has;
  }

  public int indexOfServicesOffered(Service aServicesOffered)
  {
    int index = servicesOffered.indexOf(aServicesOffered);
    return index;
  }
  /* Code from template association_GetMany */
  public ServiceList getServiceListsOffered(int index)
  {
    ServiceList aServiceListsOffered = serviceListsOffered.get(index);
    return aServiceListsOffered;
  }

  public List<ServiceList> getServiceListsOffered()
  {
    List<ServiceList> newServiceListsOffered = Collections.unmodifiableList(serviceListsOffered);
    return newServiceListsOffered;
  }

  public int numberOfServiceListsOffered()
  {
    int number = serviceListsOffered.size();
    return number;
  }

  public boolean hasServiceListsOffered()
  {
    boolean has = serviceListsOffered.size() > 0;
    return has;
  }

  public int indexOfServiceListsOffered(ServiceList aServiceListsOffered)
  {
    int index = serviceListsOffered.indexOf(aServiceListsOffered);
    return index;
  }
  /* Code from template association_GetMany */
  public GeneralDailySchedule getGeneralDailySchedule(int index)
  {
    GeneralDailySchedule aGeneralDailySchedule = generalDailySchedules.get(index);
    return aGeneralDailySchedule;
  }

  public List<GeneralDailySchedule> getGeneralDailySchedules()
  {
    List<GeneralDailySchedule> newGeneralDailySchedules = Collections.unmodifiableList(generalDailySchedules);
    return newGeneralDailySchedules;
  }

  public int numberOfGeneralDailySchedules()
  {
    int number = generalDailySchedules.size();
    return number;
  }

  public boolean hasGeneralDailySchedules()
  {
    boolean has = generalDailySchedules.size() > 0;
    return has;
  }

  public int indexOfGeneralDailySchedule(GeneralDailySchedule aGeneralDailySchedule)
  {
    int index = generalDailySchedules.indexOf(aGeneralDailySchedule);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfEmployeeValid()
  {
    boolean isValid = numberOfEmployee() >= minimumNumberOfEmployee() && numberOfEmployee() <= maximumNumberOfEmployee();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfEmployee()
  {
    return 5;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployee()
  {
    return 5;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfEmployee()
  {
    return 5;
  }
  /* Code from template association_AddMNToOnlyOne */
  public TechnicianAccount addEmployee(String aUsername, String aPassword, boolean aIsLoggedIn, String aWarningMessage, User aUser, TechnicianAccount.JobType aGarageType)
  {
    if (numberOfEmployee() >= maximumNumberOfEmployee())
    {
      return null;
    }
    else
    {
      return new TechnicianAccount(aUsername, aPassword, aIsLoggedIn, aWarningMessage, aUser, aGarageType, this);
    }
  }

  public boolean addEmployee(TechnicianAccount aEmployee)
  {
    boolean wasAdded = false;
    if (employee.contains(aEmployee)) { return false; }
    if (numberOfEmployee() >= maximumNumberOfEmployee())
    {
      return wasAdded;
    }

    CarShop existingWorkplace = aEmployee.getWorkplace();
    boolean isNewWorkplace = existingWorkplace != null && !this.equals(existingWorkplace);

    if (isNewWorkplace && existingWorkplace.numberOfEmployee() <= minimumNumberOfEmployee())
    {
      return wasAdded;
    }

    if (isNewWorkplace)
    {
      aEmployee.setWorkplace(this);
    }
    else
    {
      employee.add(aEmployee);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(TechnicianAccount aEmployee)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployee, as it must always have a workplace
    if (this.equals(aEmployee.getWorkplace()))
    {
      return wasRemoved;
    }

    //workplace already at minimum (5)
    if (numberOfEmployee() <= minimumNumberOfEmployee())
    {
      return wasRemoved;
    }
    employee.remove(aEmployee);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServicesOffered()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addServicesOffered(String aName, boolean aIsCompleted, double aDurationInHours, ServiceList aServiceList)
  {
    return new Service(aName, aIsCompleted, aDurationInHours, this, aServiceList);
  }

  public boolean addServicesOffered(Service aServicesOffered)
  {
    boolean wasAdded = false;
    if (servicesOffered.contains(aServicesOffered)) { return false; }
    CarShop existingCarShop = aServicesOffered.getCarShop();
    boolean isNewCarShop = existingCarShop != null && !this.equals(existingCarShop);
    if (isNewCarShop)
    {
      aServicesOffered.setCarShop(this);
    }
    else
    {
      servicesOffered.add(aServicesOffered);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServicesOffered(Service aServicesOffered)
  {
    boolean wasRemoved = false;
    //Unable to remove aServicesOffered, as it must always have a carShop
    if (!this.equals(aServicesOffered.getCarShop()))
    {
      servicesOffered.remove(aServicesOffered);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServicesOfferedAt(Service aServicesOffered, int index)
  {  
    boolean wasAdded = false;
    if(addServicesOffered(aServicesOffered))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServicesOffered()) { index = numberOfServicesOffered() - 1; }
      servicesOffered.remove(aServicesOffered);
      servicesOffered.add(index, aServicesOffered);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServicesOfferedAt(Service aServicesOffered, int index)
  {
    boolean wasAdded = false;
    if(servicesOffered.contains(aServicesOffered))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServicesOffered()) { index = numberOfServicesOffered() - 1; }
      servicesOffered.remove(aServicesOffered);
      servicesOffered.add(index, aServicesOffered);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServicesOfferedAt(aServicesOffered, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceListsOffered()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceList addServiceListsOffered(Service aFirstItem, ServiceList.ComboType aComboType, boolean aIsServiceCombo, Service aMainService)
  {
    return new ServiceList(aFirstItem, aComboType, aIsServiceCombo, aMainService, this);
  }

  public boolean addServiceListsOffered(ServiceList aServiceListsOffered)
  {
    boolean wasAdded = false;
    if (serviceListsOffered.contains(aServiceListsOffered)) { return false; }
    CarShop existingCarShop = aServiceListsOffered.getCarShop();
    boolean isNewCarShop = existingCarShop != null && !this.equals(existingCarShop);
    if (isNewCarShop)
    {
      aServiceListsOffered.setCarShop(this);
    }
    else
    {
      serviceListsOffered.add(aServiceListsOffered);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServiceListsOffered(ServiceList aServiceListsOffered)
  {
    boolean wasRemoved = false;
    //Unable to remove aServiceListsOffered, as it must always have a carShop
    if (!this.equals(aServiceListsOffered.getCarShop()))
    {
      serviceListsOffered.remove(aServiceListsOffered);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceListsOfferedAt(ServiceList aServiceListsOffered, int index)
  {  
    boolean wasAdded = false;
    if(addServiceListsOffered(aServiceListsOffered))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceListsOffered()) { index = numberOfServiceListsOffered() - 1; }
      serviceListsOffered.remove(aServiceListsOffered);
      serviceListsOffered.add(index, aServiceListsOffered);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceListsOfferedAt(ServiceList aServiceListsOffered, int index)
  {
    boolean wasAdded = false;
    if(serviceListsOffered.contains(aServiceListsOffered))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceListsOffered()) { index = numberOfServiceListsOffered() - 1; }
      serviceListsOffered.remove(aServiceListsOffered);
      serviceListsOffered.add(index, aServiceListsOffered);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceListsOfferedAt(aServiceListsOffered, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGeneralDailySchedules()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GeneralDailySchedule addGeneralDailySchedule(GeneralDailySchedule.DayType aDayType, Date aOpeningTime, Date aClosingTime, Date aCurrentDate)
  {
    return new GeneralDailySchedule(aDayType, aOpeningTime, aClosingTime, aCurrentDate, this);
  }

  public boolean addGeneralDailySchedule(GeneralDailySchedule aGeneralDailySchedule)
  {
    boolean wasAdded = false;
    if (generalDailySchedules.contains(aGeneralDailySchedule)) { return false; }
    CarShop existingCarShop = aGeneralDailySchedule.getCarShop();
    boolean isNewCarShop = existingCarShop != null && !this.equals(existingCarShop);
    if (isNewCarShop)
    {
      aGeneralDailySchedule.setCarShop(this);
    }
    else
    {
      generalDailySchedules.add(aGeneralDailySchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGeneralDailySchedule(GeneralDailySchedule aGeneralDailySchedule)
  {
    boolean wasRemoved = false;
    //Unable to remove aGeneralDailySchedule, as it must always have a carShop
    if (!this.equals(aGeneralDailySchedule.getCarShop()))
    {
      generalDailySchedules.remove(aGeneralDailySchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGeneralDailyScheduleAt(GeneralDailySchedule aGeneralDailySchedule, int index)
  {  
    boolean wasAdded = false;
    if(addGeneralDailySchedule(aGeneralDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGeneralDailySchedules()) { index = numberOfGeneralDailySchedules() - 1; }
      generalDailySchedules.remove(aGeneralDailySchedule);
      generalDailySchedules.add(index, aGeneralDailySchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGeneralDailyScheduleAt(GeneralDailySchedule aGeneralDailySchedule, int index)
  {
    boolean wasAdded = false;
    if(generalDailySchedules.contains(aGeneralDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGeneralDailySchedules()) { index = numberOfGeneralDailySchedules() - 1; }
      generalDailySchedules.remove(aGeneralDailySchedule);
      generalDailySchedules.add(index, aGeneralDailySchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGeneralDailyScheduleAt(aGeneralDailySchedule, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Owner existingOwnedBy = ownedBy;
    ownedBy = null;
    if (existingOwnedBy != null)
    {
      existingOwnedBy.delete();
    }
    for(int i=employee.size(); i > 0; i--)
    {
      TechnicianAccount aEmployee = employee.get(i - 1);
      aEmployee.delete();
    }
    for(int i=servicesOffered.size(); i > 0; i--)
    {
      Service aServicesOffered = servicesOffered.get(i - 1);
      aServicesOffered.delete();
    }
    for(int i=serviceListsOffered.size(); i > 0; i--)
    {
      ServiceList aServiceListsOffered = serviceListsOffered.get(i - 1);
      aServiceListsOffered.delete();
    }
    for(int i=generalDailySchedules.size(); i > 0; i--)
    {
      GeneralDailySchedule aGeneralDailySchedule = generalDailySchedules.get(i - 1);
      aGeneralDailySchedule.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "generalInfo" + ":" + getGeneralInfo()+ "," +
            "address" + ":" + getAddress()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "ownedBy = "+(getOwnedBy()!=null?Integer.toHexString(System.identityHashCode(getOwnedBy())):"null");
  }
}