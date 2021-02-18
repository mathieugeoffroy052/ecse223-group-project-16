/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;

// line 66 "../../CarShop.ump"
public class CustomerAccount extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CustomerAccount Associations
  private List<AppointmentInfo> appointmentInfos;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CustomerAccount(String aUsername, String aPassword, boolean aIsLoggedIn, User aUser)
  {
    super(aUsername, aPassword, aIsLoggedIn, aUser);
    appointmentInfos = new ArrayList<AppointmentInfo>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public AppointmentInfo getAppointmentInfo(int index)
  {
    AppointmentInfo aAppointmentInfo = appointmentInfos.get(index);
    return aAppointmentInfo;
  }

  public List<AppointmentInfo> getAppointmentInfos()
  {
    List<AppointmentInfo> newAppointmentInfos = Collections.unmodifiableList(appointmentInfos);
    return newAppointmentInfos;
  }

  public int numberOfAppointmentInfos()
  {
    int number = appointmentInfos.size();
    return number;
  }

  public boolean hasAppointmentInfos()
  {
    boolean has = appointmentInfos.size() > 0;
    return has;
  }

  public int indexOfAppointmentInfo(AppointmentInfo aAppointmentInfo)
  {
    int index = appointmentInfos.indexOf(aAppointmentInfo);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointmentInfos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public AppointmentInfo addAppointmentInfo(int aDaysBeforeAppt, TimeSlot aTimeSlot)
  {
    return new AppointmentInfo(aDaysBeforeAppt, this, aTimeSlot);
  }

  public boolean addAppointmentInfo(AppointmentInfo aAppointmentInfo)
  {
    boolean wasAdded = false;
    if (appointmentInfos.contains(aAppointmentInfo)) { return false; }
    CustomerAccount existingCustomerAccount = aAppointmentInfo.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount)
    {
      aAppointmentInfo.setCustomerAccount(this);
    }
    else
    {
      appointmentInfos.add(aAppointmentInfo);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointmentInfo(AppointmentInfo aAppointmentInfo)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointmentInfo, as it must always have a customerAccount
    if (!this.equals(aAppointmentInfo.getCustomerAccount()))
    {
      appointmentInfos.remove(aAppointmentInfo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentInfoAt(AppointmentInfo aAppointmentInfo, int index)
  {  
    boolean wasAdded = false;
    if(addAppointmentInfo(aAppointmentInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointmentInfos()) { index = numberOfAppointmentInfos() - 1; }
      appointmentInfos.remove(aAppointmentInfo);
      appointmentInfos.add(index, aAppointmentInfo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentInfoAt(AppointmentInfo aAppointmentInfo, int index)
  {
    boolean wasAdded = false;
    if(appointmentInfos.contains(aAppointmentInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointmentInfos()) { index = numberOfAppointmentInfos() - 1; }
      appointmentInfos.remove(aAppointmentInfo);
      appointmentInfos.add(index, aAppointmentInfo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentInfoAt(aAppointmentInfo, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=appointmentInfos.size(); i > 0; i--)
    {
      AppointmentInfo aAppointmentInfo = appointmentInfos.get(i - 1);
      aAppointmentInfo.delete();
    }
    super.delete();
  }

}