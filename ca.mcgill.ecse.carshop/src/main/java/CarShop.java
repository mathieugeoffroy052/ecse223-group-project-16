/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.io.Serializable;

// line 35 "CarShopPersistence.ump"
public class CarShop implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CarShop()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * private static final long serialVersionUID = 2045406856025012133L;
   */
  // line 41 "CarShopPersistence.ump"
   public void reinitialize(){
    BookableService.reinitializeBookableServiceList(this.getBookableServices());
	    Customer.reinitializeUniqueUsernames(this.getCustomers());
  }

}