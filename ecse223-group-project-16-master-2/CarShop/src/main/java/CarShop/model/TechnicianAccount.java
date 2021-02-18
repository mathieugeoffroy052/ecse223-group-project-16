/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;

// line 37 "../../CarShop.ump"
public class TechnicianAccount extends UserAccount
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum JobType { Tires, Engine, Transmission, Electronics, Fluids }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<JobType, TechnicianAccount> technicianaccountsByTechnicianType = new HashMap<JobType, TechnicianAccount>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TechnicianAccount Attributes
  private JobType technicianType;

  //TechnicianAccount Associations
  private Garage worksAt;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TechnicianAccount(String aUsername, String aPassword, boolean aIsLoggedIn, User aUser, JobType aTechnicianType, Garage aWorksAt)
  {
    super(aUsername, aPassword, aIsLoggedIn, aUser);
    if (!setTechnicianType(aTechnicianType))
    {
      throw new RuntimeException("Cannot create due to duplicate technicianType. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (aWorksAt == null || aWorksAt.getTechnicianAccount() != null)
    {
      throw new RuntimeException("Unable to create TechnicianAccount due to aWorksAt. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    worksAt = aWorksAt;
  }

  public TechnicianAccount(String aUsername, String aPassword, boolean aIsLoggedIn, User aUser, JobType aTechnicianType, JobType aGarageTypeForWorksAt)
  {
    super(aUsername, aPassword, aIsLoggedIn, aUser);
    if (!setTechnicianType(aTechnicianType))
    {
      throw new RuntimeException("Cannot create due to duplicate technicianType. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    worksAt = new Garage(aGarageTypeForWorksAt, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTechnicianType(JobType aTechnicianType)
  {
    boolean wasSet = false;
    JobType anOldTechnicianType = getTechnicianType();
    if (anOldTechnicianType != null && anOldTechnicianType.equals(aTechnicianType)) {
      return true;
    }
    if (hasWithTechnicianType(aTechnicianType)) {
      return wasSet;
    }
    technicianType = aTechnicianType;
    wasSet = true;
    if (anOldTechnicianType != null) {
      technicianaccountsByTechnicianType.remove(anOldTechnicianType);
    }
    technicianaccountsByTechnicianType.put(aTechnicianType, this);
    return wasSet;
  }

  public JobType getTechnicianType()
  {
    return technicianType;
  }
  /* Code from template attribute_GetUnique */
  public static TechnicianAccount getWithTechnicianType(JobType aTechnicianType)
  {
    return technicianaccountsByTechnicianType.get(aTechnicianType);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithTechnicianType(JobType aTechnicianType)
  {
    return getWithTechnicianType(aTechnicianType) != null;
  }
  /* Code from template association_GetOne */
  public Garage getWorksAt()
  {
    return worksAt;
  }

  public void delete()
  {
    technicianaccountsByTechnicianType.remove(getTechnicianType());
    Garage existingWorksAt = worksAt;
    worksAt = null;
    if (existingWorksAt != null)
    {
      existingWorksAt.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "technicianType" + "=" + (getTechnicianType() != null ? !getTechnicianType().equals(this)  ? getTechnicianType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "worksAt = "+(getWorksAt()!=null?Integer.toHexString(System.identityHashCode(getWorksAt())):"null");
  }
}