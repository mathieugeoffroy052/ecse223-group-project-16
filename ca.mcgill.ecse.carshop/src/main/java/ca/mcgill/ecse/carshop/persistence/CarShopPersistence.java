package ca.mcgill.ecse.carshop.persistence;

import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.persistence.CarShopPersistence;

public class CarShopPersistence {
	private static String filename = "data.cs";
	
	public static void setFilename(String filename) {
		CarShopPersistence.filename = filename;
	}
	
	public static void save(CarShop cs) {
	    PersistenceObjectStream.serialize(cs);
	}

	public static CarShop load() {
	    PersistenceObjectStream.setFilename(filename);
	    CarShop cs = (CarShop) PersistenceObjectStream.deserialize();
	    // model cannot be loaded - create empty CarShop
	    if (cs == null) {
	        cs = new CarShop();
	    }
	    else {
			cs.reinitialize();
		}
	    return cs;
	}
}
