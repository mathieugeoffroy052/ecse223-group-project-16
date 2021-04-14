/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.controller;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

// line 80 "../../../../../CarShopTransferObjects.ump"
public class TOAppointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAppointment Attributes
  private String customerName;
  private String serviceName;
  private Date date;
  private Time startTime;
  private List<TOServiceBooking> serviceBookings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAppointment(String aCustomerName, String aServiceName, Date aDate, Time aStartTime, List<TOServiceBooking> aServiceBookings)
  {
    customerName = aCustomerName;
    serviceName = aServiceName;
    date = aDate;
    startTime = aStartTime;
    serviceBookings = aServiceBookings;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCustomerName(String aCustomerName)
  {
    boolean wasSet = false;
    customerName = aCustomerName;
    wasSet = true;
    return wasSet;
  }

  public boolean setServiceName(String aServiceName)
  {
    boolean wasSet = false;
    serviceName = aServiceName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setServiceBookings(List<TOServiceBooking> aServiceBookings)
  {
    boolean wasSet = false;
    serviceBookings = aServiceBookings;
    wasSet = true;
    return wasSet;
  }

  public String getCustomerName()
  {
    return customerName;
  }

  public String getServiceName()
  {
    return serviceName;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public List<TOServiceBooking> getServiceBookings()
  {
    return serviceBookings;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "customerName" + ":" + getCustomerName()+ "," +
            "serviceName" + ":" + getServiceName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceBookings" + "=" + (getServiceBookings() != null ? !getServiceBookings().equals(this)  ? getServiceBookings().toString().replaceAll("  ","    ") : "this" : "null");
  }
}