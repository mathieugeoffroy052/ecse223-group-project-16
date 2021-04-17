/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;
import java.util.List;

// line 101 "../../../../../CarShopTransferObjects.ump"
public class TOCarshopCombo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOCarshopCombo Attributes
  private String comboName;
  private String mainService;
  private List<String> services;
  private List<String> garages;
  private List<Integer> duration;
  private List<Boolean> mandatory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOCarshopCombo(String aComboName, String aMainService, List<String> aServices, List<String> aGarages, List<Integer> aDuration, List<Boolean> aMandatory)
  {
    comboName = aComboName;
    mainService = aMainService;
    services = aServices;
    garages = aGarages;
    duration = aDuration;
    mandatory = aMandatory;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setComboName(String aComboName)
  {
    boolean wasSet = false;
    comboName = aComboName;
    wasSet = true;
    return wasSet;
  }

  public boolean setMainService(String aMainService)
  {
    boolean wasSet = false;
    mainService = aMainService;
    wasSet = true;
    return wasSet;
  }

  public boolean setServices(List<String> aServices)
  {
    boolean wasSet = false;
    services = aServices;
    wasSet = true;
    return wasSet;
  }

  public boolean setGarages(List<String> aGarages)
  {
    boolean wasSet = false;
    garages = aGarages;
    wasSet = true;
    return wasSet;
  }

  public boolean setDuration(List<Integer> aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setMandatory(List<Boolean> aMandatory)
  {
    boolean wasSet = false;
    mandatory = aMandatory;
    wasSet = true;
    return wasSet;
  }

  public String getComboName()
  {
    return comboName;
  }

  public String getMainService()
  {
    return mainService;
  }

  public List<String> getServices()
  {
    return services;
  }

  public List<String> getGarages()
  {
    return garages;
  }

  public List<Integer> getDuration()
  {
    return duration;
  }

  public List<Boolean> getMandatory()
  {
    return mandatory;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "comboName" + ":" + getComboName()+ "," +
            "mainService" + ":" + getMainService()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "services" + "=" + (getServices() != null ? !getServices().equals(this)  ? getServices().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "garages" + "=" + (getGarages() != null ? !getGarages().equals(this)  ? getGarages().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "duration" + "=" + (getDuration() != null ? !getDuration().equals(this)  ? getDuration().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mandatory" + "=" + (getMandatory() != null ? !getMandatory().equals(this)  ? getMandatory().toString().replaceAll("  ","    ") : "this" : "null");
  }
}