/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 31 "../../CarShop.ump"
public class Owner extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private CarShop carShop;
  private List<ServiceList> serviceLists;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, CarShop aCarShop)
  {
    super(aUsername, aPassword, aIsLoggedIn);
    if (aCarShop == null || aCarShop.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aCarShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    carShop = aCarShop;
    serviceLists = new ArrayList<ServiceList>();
  }

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, Date aDateForCarShop, String aGeneralInfoForCarShop, String aAddressForCarShop, String aEmailAddressForCarShop, String aPhoneNUmberForCarShop)
  {
    super(aUsername, aPassword, aIsLoggedIn);
    carShop = new CarShop(aDateForCarShop, aGeneralInfoForCarShop, aAddressForCarShop, aEmailAddressForCarShop, aPhoneNUmberForCarShop, this);
    serviceLists = new ArrayList<ServiceList>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceLists()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceList addServiceList(Service aServiceName, ServiceList.ComboType aComboType, boolean aIsServiceCombo, Service aMainService, Appointment aAppointment)
  {
    return new ServiceList(aServiceName, aComboType, aIsServiceCombo, aMainService, this, aAppointment);
  }

  public boolean addServiceList(ServiceList aServiceList)
  {
    boolean wasAdded = false;
    if (serviceLists.contains(aServiceList)) { return false; }
    Owner existingOwner = aServiceList.getOwner();
    boolean isNewOwner = existingOwner != null && !this.equals(existingOwner);
    if (isNewOwner)
    {
      aServiceList.setOwner(this);
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
    //Unable to remove aServiceList, as it must always have a owner
    if (!this.equals(aServiceList.getOwner()))
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

  public void delete()
  {
    CarShop existingCarShop = carShop;
    carShop = null;
    if (existingCarShop != null)
    {
      existingCarShop.delete();
    }
    for(int i=serviceLists.size(); i > 0; i--)
    {
      ServiceList aServiceList = serviceLists.get(i - 1);
      aServiceList.delete();
    }
    super.delete();
  }

}