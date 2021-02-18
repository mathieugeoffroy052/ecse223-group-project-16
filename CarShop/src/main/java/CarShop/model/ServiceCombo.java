/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package CarShop.model;

// line 78 "../../CarShop.ump"
public class ServiceCombo
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ComboType { Required, Optional }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceCombo Attributes
  private ComboType comboType;
  private boolean isServiceCombo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceCombo(ComboType aComboType, boolean aIsServiceCombo)
  {
    comboType = aComboType;
    isServiceCombo = aIsServiceCombo;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setComboType(ComboType aComboType)
  {
    boolean wasSet = false;
    comboType = aComboType;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsServiceCombo(boolean aIsServiceCombo)
  {
    boolean wasSet = false;
    isServiceCombo = aIsServiceCombo;
    wasSet = true;
    return wasSet;
  }

  /**
   * Service name;
   */
  public ComboType getComboType()
  {
    return comboType;
  }

  public boolean getIsServiceCombo()
  {
    return isServiceCombo;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsServiceCombo()
  {
    return isServiceCombo;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "isServiceCombo" + ":" + getIsServiceCombo()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "comboType" + "=" + (getComboType() != null ? !getComboType().equals(this)  ? getComboType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}