/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;

// line 17 "../../CarShop.ump"
public class TechnicianAccount extends UserAccount
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum JobType { Tires, Engine, Transmission, Electronics, Fluids }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<JobType, TechnicianAccount> technicianaccountsByGarageType = new HashMap<JobType, TechnicianAccount>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TechnicianAccount Attributes
  private JobType garageType;

  //TechnicianAccount Associations
  private CarShop workplace;
  private List<TechnicianDailySchedule> dailySchedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TechnicianAccount(String aUsername, String aPassword, boolean aIsLoggedIn, String aWarningMessage, User aUser, JobType aGarageType, CarShop aWorkplace)
  {
    super(aUsername, aPassword, aIsLoggedIn, aWarningMessage, aUser);
    if (!setGarageType(aGarageType))
    {
      throw new RuntimeException("Cannot create due to duplicate garageType. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddWorkplace = setWorkplace(aWorkplace);
    if (!didAddWorkplace)
    {
      throw new RuntimeException("Unable to create employee due to workplace. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    dailySchedule = new ArrayList<TechnicianDailySchedule>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGarageType(JobType aGarageType)
  {
    boolean wasSet = false;
    JobType anOldGarageType = getGarageType();
    if (anOldGarageType != null && anOldGarageType.equals(aGarageType)) {
      return true;
    }
    if (hasWithGarageType(aGarageType)) {
      return wasSet;
    }
    garageType = aGarageType;
    wasSet = true;
    if (anOldGarageType != null) {
      technicianaccountsByGarageType.remove(anOldGarageType);
    }
    technicianaccountsByGarageType.put(aGarageType, this);
    return wasSet;
  }

  public JobType getGarageType()
  {
    return garageType;
  }
  /* Code from template attribute_GetUnique */
  public static TechnicianAccount getWithGarageType(JobType aGarageType)
  {
    return technicianaccountsByGarageType.get(aGarageType);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithGarageType(JobType aGarageType)
  {
    return getWithGarageType(aGarageType) != null;
  }
  /* Code from template association_GetOne */
  public CarShop getWorkplace()
  {
    return workplace;
  }
  /* Code from template association_GetMany */
  public TechnicianDailySchedule getDailySchedule(int index)
  {
    TechnicianDailySchedule aDailySchedule = dailySchedule.get(index);
    return aDailySchedule;
  }

  public List<TechnicianDailySchedule> getDailySchedule()
  {
    List<TechnicianDailySchedule> newDailySchedule = Collections.unmodifiableList(dailySchedule);
    return newDailySchedule;
  }

  public int numberOfDailySchedule()
  {
    int number = dailySchedule.size();
    return number;
  }

  public boolean hasDailySchedule()
  {
    boolean has = dailySchedule.size() > 0;
    return has;
  }

  public int indexOfDailySchedule(TechnicianDailySchedule aDailySchedule)
  {
    int index = dailySchedule.indexOf(aDailySchedule);
    return index;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setWorkplace(CarShop aWorkplace)
  {
    boolean wasSet = false;
    //Must provide workplace to employee
    if (aWorkplace == null)
    {
      return wasSet;
    }

    //workplace already at maximum (5)
    if (aWorkplace.numberOfEmployee() >= CarShop.maximumNumberOfEmployee())
    {
      return wasSet;
    }
    
    CarShop existingWorkplace = workplace;
    workplace = aWorkplace;
    if (existingWorkplace != null && !existingWorkplace.equals(aWorkplace))
    {
      boolean didRemove = existingWorkplace.removeEmployee(this);
      if (!didRemove)
      {
        workplace = existingWorkplace;
        return wasSet;
      }
    }
    workplace.addEmployee(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDailySchedule()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addDailySchedule(TechnicianDailySchedule aDailySchedule)
  {
    boolean wasAdded = false;
    if (dailySchedule.contains(aDailySchedule)) { return false; }
    TechnicianAccount existingTechnician = aDailySchedule.getTechnician();
    if (existingTechnician == null)
    {
      aDailySchedule.setTechnician(this);
    }
    else if (!this.equals(existingTechnician))
    {
      existingTechnician.removeDailySchedule(aDailySchedule);
      addDailySchedule(aDailySchedule);
    }
    else
    {
      dailySchedule.add(aDailySchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDailySchedule(TechnicianDailySchedule aDailySchedule)
  {
    boolean wasRemoved = false;
    if (dailySchedule.contains(aDailySchedule))
    {
      dailySchedule.remove(aDailySchedule);
      aDailySchedule.setTechnician(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDailyScheduleAt(TechnicianDailySchedule aDailySchedule, int index)
  {  
    boolean wasAdded = false;
    if(addDailySchedule(aDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDailySchedule()) { index = numberOfDailySchedule() - 1; }
      dailySchedule.remove(aDailySchedule);
      dailySchedule.add(index, aDailySchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDailyScheduleAt(TechnicianDailySchedule aDailySchedule, int index)
  {
    boolean wasAdded = false;
    if(dailySchedule.contains(aDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDailySchedule()) { index = numberOfDailySchedule() - 1; }
      dailySchedule.remove(aDailySchedule);
      dailySchedule.add(index, aDailySchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDailyScheduleAt(aDailySchedule, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    technicianaccountsByGarageType.remove(getGarageType());
    CarShop placeholderWorkplace = workplace;
    this.workplace = null;
    if(placeholderWorkplace != null)
    {
      placeholderWorkplace.removeEmployee(this);
    }
    while( !dailySchedule.isEmpty() )
    {
      dailySchedule.get(0).setTechnician(null);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "garageType" + "=" + (getGarageType() != null ? !getGarageType().equals(this)  ? getGarageType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "workplace = "+(getWorkplace()!=null?Integer.toHexString(System.identityHashCode(getWorkplace())):"null");
  }
}