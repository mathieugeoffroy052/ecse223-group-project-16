/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;
import java.util.*;

// line 39 "../../CarShop.ump"
public abstract class UserAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, UserAccount> useraccountsByUsername = new HashMap<String, UserAccount>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserAccount Attributes
  private String username;
  private String password;
  private boolean isLoggedIn;
  private String warningMessage;

  //UserAccount Associations
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aUsername, String aPassword, boolean aIsLoggedIn, String aWarningMessage, User aUser)
  {
    password = aPassword;
    isLoggedIn = aIsLoggedIn;
    warningMessage = aWarningMessage;
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create userAccount due to user. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    String anOldUsername = getUsername();
    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
      return true;
    }
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      useraccountsByUsername.remove(anOldUsername);
    }
    useraccountsByUsername.put(aUsername, this);
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsLoggedIn(boolean aIsLoggedIn)
  {
    boolean wasSet = false;
    isLoggedIn = aIsLoggedIn;
    wasSet = true;
    return wasSet;
  }

  public boolean setWarningMessage(String aWarningMessage)
  {
    boolean wasSet = false;
    warningMessage = aWarningMessage;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }
  /* Code from template attribute_GetUnique */
  public static UserAccount getWithUsername(String aUsername)
  {
    return useraccountsByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }

  public String getPassword()
  {
    return password;
  }

  public boolean getIsLoggedIn()
  {
    return isLoggedIn;
  }

  public String getWarningMessage()
  {
    return warningMessage;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsLoggedIn()
  {
    return isLoggedIn;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setUser(User aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser == null)
    {
      //Unable to setUser to null, as userAccount must always be associated to a user
      return wasSet;
    }
    
    UserAccount existingUserAccount = aNewUser.getUserAccount();
    if (existingUserAccount != null && !equals(existingUserAccount))
    {
      //Unable to setUser, the current user already has a userAccount, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    User anOldUser = user;
    user = aNewUser;
    user.setUserAccount(this);

    if (anOldUser != null)
    {
      anOldUser.setUserAccount(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    useraccountsByUsername.remove(getUsername());
    User existingUser = user;
    user = null;
    if (existingUser != null)
    {
      existingUser.setUserAccount(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "isLoggedIn" + ":" + getIsLoggedIn()+ "," +
            "warningMessage" + ":" + getWarningMessage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}