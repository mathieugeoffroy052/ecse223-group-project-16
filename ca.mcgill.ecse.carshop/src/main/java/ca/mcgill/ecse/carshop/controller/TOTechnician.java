/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;

// line 22 "../../../../../CarShopTransferObjects.ump"
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
  private String username;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOTechnician(TechnicianType aType, String aUsername, String aPassword)
  {
    type = aType;
    username = aUsername;
    password = aPassword;
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

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public TechnicianType getType()
  {
    return type;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}