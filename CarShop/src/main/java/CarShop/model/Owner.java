/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.sql.Date;

// line 20 "../../CarShop.ump"
public class Owner extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private CarShop carShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, CarShop aCarShop)
  {
    super(aUsername, aPassword, aIsLoggedIn);
    if (aCarShop == null || aCarShop.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aCarShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    carShop = aCarShop;
  }

  public Owner(String aUsername, String aPassword, boolean aIsLoggedIn, Date aDateForCarShop, String aGeneralInfoForCarShop, String aAddressForCarShop, String aEmailAddressForCarShop, String aPhoneNUmberForCarShop)
  {
    super(aUsername, aPassword, aIsLoggedIn);
    carShop = new CarShop(aDateForCarShop, aGeneralInfoForCarShop, aAddressForCarShop, aEmailAddressForCarShop, aPhoneNUmberForCarShop, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
  }

  public void delete()
  {
    CarShop existingCarShop = carShop;
    carShop = null;
    if (existingCarShop != null)
    {
      existingCarShop.delete();
    }
    super.delete();
  }

}