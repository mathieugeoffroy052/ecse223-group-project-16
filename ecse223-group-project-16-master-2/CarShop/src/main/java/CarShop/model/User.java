/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;

// line 52 "../../CarShop.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String name;

  //User Associations
  private UserAccount userAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aName)
  {
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public UserAccount getUserAccount()
  {
    return userAccount;
  }

  public boolean hasUserAccount()
  {
    boolean has = userAccount != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setUserAccount(UserAccount aNewUserAccount)
  {
    boolean wasSet = false;
    if (userAccount != null && !userAccount.equals(aNewUserAccount) && equals(userAccount.getUser()))
    {
      //Unable to setUserAccount, as existing userAccount would become an orphan
      return wasSet;
    }

    userAccount = aNewUserAccount;
    User anOldUser = aNewUserAccount != null ? aNewUserAccount.getUser() : null;

    if (!this.equals(anOldUser))
    {
      if (anOldUser != null)
      {
        anOldUser.userAccount = null;
      }
      if (userAccount != null)
      {
        userAccount.setUser(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    UserAccount existingUserAccount = userAccount;
    userAccount = null;
    if (existingUserAccount != null)
    {
      existingUserAccount.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "userAccount = "+(getUserAccount()!=null?Integer.toHexString(System.identityHashCode(getUserAccount())):"null");
  }
}