/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.io.Serializable;

// line 10 "CarShopPersistence.ump"
public class BookableService implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BookableService()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}


  /**
   * private static final long serialVersionUID = 2315072607928790501L;
   */
  // line 15 "CarShopPersistence.ump"
   public static  void reinitializeBookableServiceList(List<BookableService> bookableServices){
    bookableservicesByName = new HashMap<String, BookableService>();
	    for (BookableService bs : bookableServices) {
	    	bookableservicesByName.put(bs.getName(), bs);
	    }
  }

}