/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;

// line 92 "../../../../../CarShopTransferObjects.ump"
public class TOServiceBooking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOServiceBooking Attributes
  private TOService service;
  private TOTimeSlot timeSlot;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOServiceBooking(TOService aService, TOTimeSlot aTimeSlot)
  {
    service = aService;
    timeSlot = aTimeSlot;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setService(TOService aService)
  {
    boolean wasSet = false;
    service = aService;
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

  public TOService getService()
  {
    return service;
  }

  public TOTimeSlot getTimeSlot()
  {
    return timeSlot;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service" + "=" + (getService() != null ? !getService().equals(this)  ? getService().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot" + "=" + (getTimeSlot() != null ? !getTimeSlot().equals(this)  ? getTimeSlot().toString().replaceAll("  ","    ") : "this" : "null");
  }
}