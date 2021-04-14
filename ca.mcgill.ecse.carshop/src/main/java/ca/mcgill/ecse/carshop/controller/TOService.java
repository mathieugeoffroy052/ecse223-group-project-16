/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;

// line 62 "../../../../../CarShopTransferObjects.ump"
public class TOService
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOService Attributes
  private int duration;
  private TOGarage garage;
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOService(int aDuration, TOGarage aGarage, String aName)
  {
    duration = aDuration;
    garage = aGarage;
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setGarage(TOGarage aGarage)
  {
    boolean wasSet = false;
    garage = aGarage;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getDuration()
  {
    return duration;
  }

  public TOGarage getGarage()
  {
    return garage;
  }

  public String getName()
  {
    return name;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "duration" + ":" + getDuration()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "garage" + "=" + (getGarage() != null ? !getGarage().equals(this)  ? getGarage().toString().replaceAll("  ","    ") : "this" : "null");
  }
}