/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;

// line 20 "../../../../../CarShopTransferObjects.ump"
public class TOTechnician
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TechnicianType { Tire, Engine, Transmission, Electronics, Fluids }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOTechnician Attributes
  private TechnicianType type;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOTechnician(TechnicianType aType)
  {
    type = aType;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(TechnicianType aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public TechnicianType getType()
  {
    return type;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}