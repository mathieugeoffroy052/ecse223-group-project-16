/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;

// line 67 "../../CarShop.ump"
public class CustomerAccount extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CustomerAccount Associations
  private AppointmentCalendar calendar;
  private Appointment customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CustomerAccount(String aUsername, String aPassword, boolean aIsLoggedIn, AppointmentCalendar aCalendar, Appointment aCustomer)
  {
    super(aUsername, aPassword, aIsLoggedIn);
    if (aCalendar == null || aCalendar.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create CustomerAccount due to aCalendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    calendar = aCalendar;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appt due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public CustomerAccount(String aUsername, String aPassword, boolean aIsLoggedIn, Garage aGarageForCalendar, Appointment aCustomer)
  {
    super(aUsername, aPassword, aIsLoggedIn);
    calendar = new AppointmentCalendar(this, aGarageForCalendar);
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appt due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public AppointmentCalendar getCalendar()
  {
    return calendar;
  }
  /* Code from template association_GetOne */
  public Appointment getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Appointment aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Appointment existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppt(this);
    }
    customer.addAppt(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AppointmentCalendar existingCalendar = calendar;
    calendar = null;
    if (existingCalendar != null)
    {
      existingCalendar.delete();
    }
    Appointment placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeAppt(this);
    }
    super.delete();
  }

}