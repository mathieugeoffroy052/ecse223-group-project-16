/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;

// line 89 "../../../../../CarShopTransferObjects.ump"
public class TOServiceBooking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOServiceBooking Attributes
  private String TOService;
  private TOTimeSlot timeSlot;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOServiceBooking(String aTOService, TOTimeSlot aTimeSlot)
  {
    TOService = aTOService;
    timeSlot = aTimeSlot;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTOService(String aTOService)
  {
    boolean wasSet = false;
    TOService = aTOService;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeSlot(TOTimeSlot aTimeSlot)
  {
    boolean wasSet = false;
    timeSlot = aTimeSlot;
    wasSet = true;
    return wasSet;
  }

  public String getTOService()
  {
    return TOService;
  }

  public TOTimeSlot getTimeSlot()
  {
    return timeSlot;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "TOService" + ":" + getTOService()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot" + "=" + (getTimeSlot() != null ? !getTimeSlot().equals(this)  ? getTimeSlot().toString().replaceAll("  ","    ") : "this" : "null");
  }
}