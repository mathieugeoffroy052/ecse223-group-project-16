/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 29 "../../CarShop.ump"
public class Owner extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private CarShop carShop;
  private List<Service> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, User aUser, CarShop aCarShop)
  {
    super(aUsername, aPassword, aIsLoggedIn, aUser);
    if (aCarShop == null || aCarShop.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aCarShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    carShop = aCarShop;
    services = new ArrayList<Service>();
  }

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, User aUser, Date aDateForCarShop, String aGeneralInfoForCarShop, String aAddressForCarShop, String aEmailAddressForCarShop, String aPhoneNUmberForCarShop)
  {
    super(aUsername, aPassword, aIsLoggedIn, aUser);
    carShop = new CarShop(aDateForCarShop, aGeneralInfoForCarShop, aAddressForCarShop, aEmailAddressForCarShop, aPhoneNUmberForCarShop, this);
    services = new ArrayList<Service>();
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
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
    return newServices;
  }

  public int numberOfServices()
  {
    int number = services.size();
    return number;
  }

  public boolean hasServices()
  {
    boolean has = services.size() > 0;
    return has;
  }

  public int indexOfService(Service aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, double aDurationInHours, Garage aWorkingGarage, CarShop aCarShop)
  {
    return new Service(aName, aDurationInHours, aWorkingGarage, aCarShop, this);
  }

  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    Owner existingOwner = aService.getOwner();
    boolean isNewOwner = existingOwner != null && !this.equals(existingOwner);
    if (isNewOwner)
    {
      aService.setOwner(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a owner
    if (!this.equals(aService.getOwner()))
    {
      services.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(Service aService, int index)
  {
    boolean wasAdded = false;
    if(services.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
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
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
    super.delete();
  }

}