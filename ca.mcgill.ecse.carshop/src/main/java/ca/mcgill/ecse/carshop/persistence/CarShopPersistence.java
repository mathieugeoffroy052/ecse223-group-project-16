package ca.mcgill.ecse.carshop.persistence;

import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.persistence.CarShopPersistence;
import ca.mcgill.ecse.carshop.persistence.PersistenceObjectStream;

public class CarShopPersistence {
	private static String filename = "data.btms";
	
	public static void setFilename(String filename) {
		CarShopPersistence.filename = filename;
	}
	
	public static void save(CarShop cs) {
	    PersistenceObjectStream.serialize(cs);
	}

	public static CarShop load() {
	    PersistenceObjectStream.setFilename(filename);
	    CarShop cs = (CarShop) PersistenceObjectStream.deserialize();
	    // model cannot be loaded - create empty BTMS
	    if (cs == null) {
	        cs = new CarShop();
	    }
	    else {
			cs.reinitialize();
		}
	    return cs;
	}
}
