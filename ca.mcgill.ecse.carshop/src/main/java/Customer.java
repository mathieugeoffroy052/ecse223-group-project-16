/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.io.Serializable;

// line 53 "CarShopPersistence.ump"
public class Customer implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * private static final long serialVersionUID = -7403802774454467836L;
   */
  // line 59 "CarShopPersistence.ump"
   public static  void reinitializeUniqueUsernames(List<Customer> customers){
    User.setUsersByUsername(new HashMap<String, User>());
    for (User user : customers) {
    	User.getUsersByUsername().put(user.getUsername(), user);
    }
  }

}