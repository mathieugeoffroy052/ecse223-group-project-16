/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;
import java.sql.Date;

// line 11 "../../CarShop.ump"
public class Owner extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private CarShop owns;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, String aWarningMessage, User aUser, CarShop aOwns)
  {
    super(aUsername, aPassword, aIsLoggedIn, aWarningMessage, aUser);
    if (aOwns == null || aOwns.getOwnedBy() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aOwns. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    owns = aOwns;
  }

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, String aWarningMessage, User aUser, Date aDateForOwns, String aGeneralInfoForOwns, String aAddressForOwns, String aEmailAddressForOwns, String aPhoneNumberForOwns, GeneralDailySchedule aGeneralDailyScheduleForOwns)
  {
    super(aUsername, aPassword, aIsLoggedIn, aWarningMessage, aUser);
    owns = new CarShop(aDateForOwns, aGeneralInfoForOwns, aAddressForOwns, aEmailAddressForOwns, aPhoneNumberForOwns, this, aGeneralDailyScheduleForOwns);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public CarShop getOwns()
  {
    return owns;
  }

  public void delete()
  {
    CarShop existingOwns = owns;
    owns = null;
    if (existingOwns != null)
    {
      existingOwns.delete();
    }
    super.delete();
  }

}