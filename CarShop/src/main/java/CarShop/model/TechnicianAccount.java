/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;

// line 40 "../../CarShop.ump"
public class TechnicianAccount extends UserAccount
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum JobType { Tires, Engine, Transmission, Electronics, Fluids }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TechnicianAccount Attributes
  private JobType technicianType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TechnicianAccount(String aUsername, String aPassword, boolean aIsLoggedIn, JobType aTechnicianType)
  {
    super(aUsername, aPassword, aIsLoggedIn);
    technicianType = aTechnicianType;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTechnicianType(JobType aTechnicianType)
  {
    boolean wasSet = false;
    technicianType = aTechnicianType;
    wasSet = true;
    return wasSet;
  }

  public JobType getTechnicianType()
  {
    return technicianType;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "technicianType" + "=" + (getTechnicianType() != null ? !getTechnicianType().equals(this)  ? getTechnicianType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}