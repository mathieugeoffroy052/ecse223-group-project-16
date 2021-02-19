/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;

// line 79 "../../CarShop.ump"
public class ServiceList
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ComboType { Required, Optional }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceList Attributes
  private Service firstItem;
  private ComboType comboType;
  private boolean isServiceCombo;

  //ServiceList Associations
  private List<Service> services;
  private Service mainService;
  private CarShop carShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceList(Service aFirstItem, ComboType aComboType, boolean aIsServiceCombo, Service aMainService, CarShop aCarShop)
  {
    firstItem = aFirstItem;
    comboType = aComboType;
    isServiceCombo = aIsServiceCombo;
    services = new ArrayList<Service>();
    if (!setMainService(aMainService))
    {
      throw new RuntimeException("Unable to create ServiceList due to aMainService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCarShop = setCarShop(aCarShop);
    if (!didAddCarShop)
    {
      throw new RuntimeException("Unable to create serviceListsOffered due to carShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstItem(Service aFirstItem)
  {
    boolean wasSet = false;
    firstItem = aFirstItem;
    wasSet = true;
    return wasSet;
  }

  public boolean setComboType(ComboType aComboType)
  {
    boolean wasSet = false;
    comboType = aComboType;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsServiceCombo(boolean aIsServiceCombo)
  {
    boolean wasSet = false;
    isServiceCombo = aIsServiceCombo;
    wasSet = true;
    return wasSet;
  }

  public Service getFirstItem()
  {
    return firstItem;
  }

  public ComboType getComboType()
  {
    return comboType;
  }

  public boolean getIsServiceCombo()
  {
    return isServiceCombo;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsServiceCombo()
  {
    return isServiceCombo;
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
  /* Code from template association_GetOne */
  public Service getMainService()
  {
    return mainService;
  }
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, boolean aIsCompleted, double aDurationInHours, CarShop aCarShop)
  {
    return new Service(aName, aIsCompleted, aDurationInHours, aCarShop, this);
  }

  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    ServiceList existingServiceList = aService.getServiceList();
    boolean isNewServiceList = existingServiceList != null && !this.equals(existingServiceList);
    if (isNewServiceList)
    {
      aService.setServiceList(this);
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
    //Unable to remove aService, as it must always have a serviceList
    if (!this.equals(aService.getServiceList()))
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setMainService(Service aNewMainService)
  {
    boolean wasSet = false;
    if (aNewMainService != null)
    {
      mainService = aNewMainService;
      wasSet = true;
    }
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
      existingCarShop.removeServiceListsOffered(this);
    }
    carShop.addServiceListsOffered(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
    mainService = null;
    CarShop placeholderCarShop = carShop;
    this.carShop = null;
    if(placeholderCarShop != null)
    {
      placeholderCarShop.removeServiceListsOffered(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isServiceCombo" + ":" + getIsServiceCombo()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "firstItem" + "=" + (getFirstItem() != null ? !getFirstItem().equals(this)  ? getFirstItem().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "comboType" + "=" + (getComboType() != null ? !getComboType().equals(this)  ? getComboType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mainService = "+(getMainService()!=null?Integer.toHexString(System.identityHashCode(getMainService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShop = "+(getCarShop()!=null?Integer.toHexString(System.identityHashCode(getCarShop())):"null");
  }
}