/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;

// line 95 "../../CarShop.ump"
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
  private double durationInHours;

  //Service Associations
  private Service successor;
  private Garage workingGarage;
  private CarShop carShop;
  private Owner owner;
  private Service prerequesite;
  private ServiceList serviceList;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aName, double aDurationInHours, Garage aWorkingGarage, CarShop aCarShop, Owner aOwner)
  {
    // line 98 "../../CarShop.ump"
    if (aName == null || aName.length() == 0) {
    			throw new RuntimeException("Please enter a valid name");
    			}
    // END OF UMPLE BEFORE INJECTION
    durationInHours = aDurationInHours;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddWorkingGarage = setWorkingGarage(aWorkingGarage);
    if (!didAddWorkingGarage)
    {
      throw new RuntimeException("Unable to create service due to workingGarage. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCarShop = setCarShop(aCarShop);
    if (!didAddCarShop)
    {
      throw new RuntimeException("Unable to create service due to carShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create service due to owner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    // line 98 "../../CarShop.ump"
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

  public double getDurationInHours()
  {
    return durationInHours;
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
  public Garage getWorkingGarage()
  {
    return workingGarage;
  }
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetOne */
  public Service getPrerequesite()
  {
    return prerequesite;
  }

  public boolean hasPrerequesite()
  {
    boolean has = prerequesite != null;
    return has;
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
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setSuccessor(Service aNewSuccessor)
  {
    boolean wasSet = false;
    if (aNewSuccessor == null)
    {
      Service existingSuccessor = successor;
      successor = null;
      
      if (existingSuccessor != null && existingSuccessor.getPrerequesite() != null)
      {
        existingSuccessor.setPrerequesite(null);
      }
      wasSet = true;
      return wasSet;
    }

    Service currentSuccessor = getSuccessor();
    if (currentSuccessor != null && !currentSuccessor.equals(aNewSuccessor))
    {
      currentSuccessor.setPrerequesite(null);
    }

    successor = aNewSuccessor;
    Service existingPrerequesite = aNewSuccessor.getPrerequesite();

    if (!equals(existingPrerequesite))
    {
      aNewSuccessor.setPrerequesite(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWorkingGarage(Garage aWorkingGarage)
  {
    boolean wasSet = false;
    if (aWorkingGarage == null)
    {
      return wasSet;
    }

    Garage existingWorkingGarage = workingGarage;
    workingGarage = aWorkingGarage;
    if (existingWorkingGarage != null && !existingWorkingGarage.equals(aWorkingGarage))
    {
      existingWorkingGarage.removeService(this);
    }
    workingGarage.addService(this);
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
      existingCarShop.removeService(this);
    }
    carShop.addService(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOwner(Owner aOwner)
  {
    boolean wasSet = false;
    if (aOwner == null)
    {
      return wasSet;
    }

    Owner existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      existingOwner.removeService(this);
    }
    owner.addService(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPrerequesite(Service aNewPrerequesite)
  {
    boolean wasSet = false;
    if (aNewPrerequesite == null)
    {
      Service existingPrerequesite = prerequesite;
      prerequesite = null;
      
      if (existingPrerequesite != null && existingPrerequesite.getSuccessor() != null)
      {
        existingPrerequesite.setSuccessor(null);
      }
      wasSet = true;
      return wasSet;
    }

    Service currentPrerequesite = getPrerequesite();
    if (currentPrerequesite != null && !currentPrerequesite.equals(aNewPrerequesite))
    {
      currentPrerequesite.setSuccessor(null);
    }

    prerequesite = aNewPrerequesite;
    Service existingSuccessor = aNewPrerequesite.getSuccessor();

    if (!equals(existingSuccessor))
    {
      aNewPrerequesite.setSuccessor(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setServiceList(ServiceList aNewServiceList)
  {
    boolean wasSet = false;
    if (aNewServiceList == null)
    {
      ServiceList existingServiceList = serviceList;
      serviceList = null;
      
      if (existingServiceList != null && existingServiceList.getFirstTerm() != null)
      {
        existingServiceList.setFirstTerm(null);
      }
      wasSet = true;
      return wasSet;
    }

    ServiceList currentServiceList = getServiceList();
    if (currentServiceList != null && !currentServiceList.equals(aNewServiceList))
    {
      currentServiceList.setFirstTerm(null);
    }

    serviceList = aNewServiceList;
    Service existingFirstTerm = aNewServiceList.getFirstTerm();

    if (!equals(existingFirstTerm))
    {
      aNewServiceList.setFirstTerm(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    servicesByName.remove(getName());
    if (successor != null)
    {
      successor.setPrerequesite(null);
    }
    Garage placeholderWorkingGarage = workingGarage;
    this.workingGarage = null;
    if(placeholderWorkingGarage != null)
    {
      placeholderWorkingGarage.removeService(this);
    }
    CarShop placeholderCarShop = carShop;
    this.carShop = null;
    if(placeholderCarShop != null)
    {
      placeholderCarShop.removeService(this);
    }
    Owner placeholderOwner = owner;
    this.owner = null;
    if(placeholderOwner != null)
    {
      placeholderOwner.removeService(this);
    }
    if (prerequesite != null)
    {
      prerequesite.setSuccessor(null);
    }
    if (serviceList != null)
    {
      serviceList.setFirstTerm(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "durationInHours" + ":" + getDurationInHours()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "workingGarage = "+(getWorkingGarage()!=null?Integer.toHexString(System.identityHashCode(getWorkingGarage())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShop = "+(getCarShop()!=null?Integer.toHexString(System.identityHashCode(getCarShop())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceList = "+(getServiceList()!=null?Integer.toHexString(System.identityHashCode(getServiceList())):"null");
  }
}