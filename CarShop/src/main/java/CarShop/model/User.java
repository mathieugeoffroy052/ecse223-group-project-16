/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;

// line 42 "../../CarShop.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String name;
  private boolean isSignedUp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aName, boolean aIsSignedUp)
  {
    name = aName;
    isSignedUp = aIsSignedUp;
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

  public boolean setIsSignedUp(boolean aIsSignedUp)
  {
    boolean wasSet = false;
    isSignedUp = aIsSignedUp;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public boolean getIsSignedUp()
  {
    return isSignedUp;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsSignedUp()
  {
    return isSignedUp;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "isSignedUp" + ":" + getIsSignedUp()+ "]";
  }
}