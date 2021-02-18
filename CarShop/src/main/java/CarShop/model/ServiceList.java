/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;

// line 122 "../../CarShop.ump"
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
  private Service serviceName;
  private ComboType comboType;
  private boolean isServiceCombo;

  //ServiceList Associations
  private Service firstTerm;
  private Service mainService;
  private Owner owner;
  private Appointment appointment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceList(Service aServiceName, ComboType aComboType, boolean aIsServiceCombo, Service aMainService, Owner aOwner, Appointment aAppointment)
  {
    serviceName = aServiceName;
    comboType = aComboType;
    isServiceCombo = aIsServiceCombo;
    if (!setMainService(aMainService))
    {
      throw new RuntimeException("Unable to create ServiceList due to aMainService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create serviceList due to owner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAppointment = setAppointment(aAppointment);
    if (!didAddAppointment)
    {
      throw new RuntimeException("Unable to create serviceList due to appointment. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setServiceName(Service aServiceName)
  {
    boolean wasSet = false;
    serviceName = aServiceName;
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

  public Service getServiceName()
  {
    return serviceName;
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
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetOne */
  public Appointment getAppointment()
  {
    return appointment;
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
      existingOwner.removeServiceList(this);
    }
    owner.addServiceList(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAppointment(Appointment aAppointment)
  {
    boolean wasSet = false;
    if (aAppointment == null)
    {
      return wasSet;
    }

    Appointment existingAppointment = appointment;
    appointment = aAppointment;
    if (existingAppointment != null && !existingAppointment.equals(aAppointment))
    {
      existingAppointment.removeServiceList(this);
    }
    appointment.addServiceList(this);
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
    Owner placeholderOwner = owner;
    this.owner = null;
    if(placeholderOwner != null)
    {
      placeholderOwner.removeServiceList(this);
    }
    Appointment placeholderAppointment = appointment;
    this.appointment = null;
    if(placeholderAppointment != null)
    {
      placeholderAppointment.removeServiceList(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isServiceCombo" + ":" + getIsServiceCombo()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "serviceName" + "=" + (getServiceName() != null ? !getServiceName().equals(this)  ? getServiceName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "comboType" + "=" + (getComboType() != null ? !getComboType().equals(this)  ? getComboType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "firstTerm = "+(getFirstTerm()!=null?Integer.toHexString(System.identityHashCode(getFirstTerm())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "mainService = "+(getMainService()!=null?Integer.toHexString(System.identityHashCode(getMainService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointment = "+(getAppointment()!=null?Integer.toHexString(System.identityHashCode(getAppointment())):"null");
  }
}