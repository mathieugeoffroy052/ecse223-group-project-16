/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;

// line 121 "../../CarShop.ump"
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
  private Service firstTerm;
  private Service mainService;
  private TimeSlot timeSlot;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceList(Service aFirstItem, ComboType aComboType, boolean aIsServiceCombo, Service aMainService, TimeSlot aTimeSlot)
  {
    firstItem = aFirstItem;
    comboType = aComboType;
    isServiceCombo = aIsServiceCombo;
    if (!setMainService(aMainService))
    {
      throw new RuntimeException("Unable to create ServiceList due to aMainService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTimeSlot = setTimeSlot(aTimeSlot);
    if (!didAddTimeSlot)
    {
      throw new RuntimeException("Unable to create serviceList due to timeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public Service getFirstTerm()
  {
    return firstTerm;
  }

  public boolean hasFirstTerm()
  {
    boolean has = firstTerm != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Service getMainService()
  {
    return mainService;
  }
  /* Code from template association_GetOne */
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setFirstTerm(Service aNewFirstTerm)
  {
    boolean wasSet = false;
    if (aNewFirstTerm == null)
    {
      Service existingFirstTerm = firstTerm;
      firstTerm = null;
      
      if (existingFirstTerm != null && existingFirstTerm.getServiceList() != null)
      {
        existingFirstTerm.setServiceList(null);
      }
      wasSet = true;
      return wasSet;
    }

    Service currentFirstTerm = getFirstTerm();
    if (currentFirstTerm != null && !currentFirstTerm.equals(aNewFirstTerm))
    {
      currentFirstTerm.setServiceList(null);
    }

    firstTerm = aNewFirstTerm;
    ServiceList existingServiceList = aNewFirstTerm.getServiceList();

    if (!equals(existingServiceList))
    {
      aNewFirstTerm.setServiceList(this);
    }
    wasSet = true;
    return wasSet;
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTimeSlot(TimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    if (aNewTimeSlot == null)
    {
      //Unable to setTimeSlot to null, as serviceList must always be associated to a timeSlot
      return wasSet;
    }
    
    ServiceList existingServiceList = aNewTimeSlot.getServiceList();
    if (existingServiceList != null && !equals(existingServiceList))
    {
      //Unable to setTimeSlot, the current timeSlot already has a serviceList, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    TimeSlot anOldTimeSlot = timeSlot;
    timeSlot = aNewTimeSlot;
    timeSlot.setServiceList(this);

    if (anOldTimeSlot != null)
    {
      anOldTimeSlot.setServiceList(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Service existingFirstTerm = firstTerm;
    firstTerm = null;
    if (existingFirstTerm != null)
    {
      existingFirstTerm.delete();
      existingFirstTerm.setServiceList(null);
    }
    mainService = null;
    TimeSlot existingTimeSlot = timeSlot;
    timeSlot = null;
    if (existingTimeSlot != null)
    {
      existingTimeSlot.setServiceList(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isServiceCombo" + ":" + getIsServiceCombo()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "firstItem" + "=" + (getFirstItem() != null ? !getFirstItem().equals(this)  ? getFirstItem().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "comboType" + "=" + (getComboType() != null ? !getComboType().equals(this)  ? getComboType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "firstTerm = "+(getFirstTerm()!=null?Integer.toHexString(System.identityHashCode(getFirstTerm())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "mainService = "+(getMainService()!=null?Integer.toHexString(System.identityHashCode(getMainService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null");
  }
}