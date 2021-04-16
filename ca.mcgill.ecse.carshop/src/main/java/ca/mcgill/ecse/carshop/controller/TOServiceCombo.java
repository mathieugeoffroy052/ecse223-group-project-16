/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;
import java.util.List;

// line 68 "../../../../../CarShopTransferObjects.ump"
public class TOServiceCombo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOServiceCombo Attributes
  private String name;
  private TOComboItem mainService;
  private List<TOComboItem> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOServiceCombo(String aName, TOComboItem aMainService, List<TOComboItem> aServices)
  {
    name = aName;
    mainService = aMainService;
    services = aServices;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setMainService(TOComboItem aMainService)
  {
    boolean wasSet = false;
    mainService = aMainService;
    wasSet = true;
    return wasSet;
  }

  public boolean setServices(List<TOComboItem> aServices)
  {
    boolean wasSet = false;
    services = aServices;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public TOComboItem getMainService()
  {
    return mainService;
  }

  public List<TOComboItem> getServices()
  {
    return services;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mainService" + "=" + (getMainService() != null ? !getMainService().equals(this)  ? getMainService().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "services" + "=" + (getServices() != null ? !getServices().equals(this)  ? getServices().toString().replaceAll("  ","    ") : "this" : "null");
  }
}