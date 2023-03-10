/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ca.mcgill.ecse.carshop.application;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse.carshop.persistence.CarShopPersistence;
import ca.mcgill.ecse.carshop.view.CarShopPage;

public class CarShopApplication {
	private static CarShop carShop = null;	//all applications are associated with the same CarShop carShop
	private static User user = null;		//might need to remove static
	public static AccountType accountType = null;
	private static boolean isLoggedIn = false;
	private static Date systemDate = null;
	private static Time systemTime = null;

	public enum AccountType{EngineTechnician, TireTechnician, TransmissionTechnician, ElectronicsTechnician, FluidsTechnician, 
		Customer, Owner};

		public static CarShop getCarShop() {
			if(carShop == null) {
				carShop = (CarShop) CarShopPersistence.load();	//load previously stored information
			}
			return carShop;
		}
		
		public static void setCarShop(CarShop cs) {
			carShop = cs;
		}

		public static void restart() {
			if(carShop != null) {
				carShop.delete();
			}
			carShop = null;
			user = null;
			accountType = null;
			isLoggedIn = false;
			systemDate = null;
			systemTime = null;
		}

		public static void logIn(String username, String password) {
			if(carShop.getOwner() != null && username.equals(carShop.getOwner().getUsername()) && password.equals(carShop.getOwner().getPassword())) {
				accountType = CarShopApplication.AccountType.Owner;
				user = carShop.getOwner();
				isLoggedIn = true;
				setUser(carShop.getOwner());
			}
			else if(username.contains("Technician")) {
				int i = carShop.getTechnicianWithString(username);
				String comparePassword = carShop.getTechnician(i).getPassword();
				if(password.equals(comparePassword)) {
					Technician.TechnicianType a = carShop.getTechnician(0).getTechnicianType(username);
					if(a.equals(Technician.TechnicianType.Engine)) {
						accountType = CarShopApplication.AccountType.EngineTechnician;
						user = carShop.getTechnician(i);
					}
					else if(a.equals(Technician.TechnicianType.Tire)) {
						accountType = CarShopApplication.AccountType.TireTechnician;
						user = carShop.getTechnician(i);
					}
					else if(a.equals(Technician.TechnicianType.Transmission)) {
						accountType = CarShopApplication.AccountType.TransmissionTechnician;
						user = carShop.getTechnician(i);
					}
					else if(a.equals(Technician.TechnicianType.Electronics)) {
						accountType = CarShopApplication.AccountType.ElectronicsTechnician;
						user = carShop.getTechnician(i);
					}
					else {
						accountType = CarShopApplication.AccountType.FluidsTechnician;
						user = carShop.getTechnician(i);
					}
					setUser(carShop.getTechnician(i));
					isLoggedIn = true;
				}
			}
			else {
				for(int i = 0; i < carShop.getCustomers().size(); i++) {
					if(carShop.getCustomer(i).getUsername().equals(username)) {
						if(carShop.getCustomer(i).getPassword().equals(password)) {
							accountType = CarShopApplication.AccountType.Customer;
							isLoggedIn = true;
							user = carShop.getCustomer(i);
							setUser(carShop.getCustomer(i));
						}
					}
				}
			}
		}

		public static void setUsername(String name) {
			user.setUsername(name);
		}

		public static String getCurrentUser() {
			if(user == null) {
				return null;
			}
			return user.getUsername();
		}

		public static void setSystemDate(Date date) {
			systemDate = date;
		}

		public static Date getSystemDate() {
			return systemDate;
		}

		public static Time getSystemTime() {
			return systemTime;
		}

		public static void setSystemTime(Time time) {
			systemTime = time;
		}

		public static User getUser() {
			return user;
		}

		public static void setUser(User newUser) {
			user = newUser;
		}

		public static void setAccountType(AccountType type) {
			accountType = type;
		}


		public static void logOut() {
			accountType = null;
			isLoggedIn = false;
			user = null;
		}

		public static void setLoggedIn(Boolean bool) {
			isLoggedIn = bool;
		}

		public static AccountType getAccountType() {
			return accountType;
		}

		public static boolean getLoggedIn() {
			return isLoggedIn;
		}
		
		public static void main(String[] args) throws Exception {
			//start UI
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						carShop = setUp();
						
					} catch (Exception e) {}
					new CarShopPage().setVisible(true);
				}
			});
		}
		
		public static String getSystemDateTime() {
			return CarShopController.dateToString(systemDate) + "+" + CarShopController.timeToString(systemTime);
		}
		
		private static CarShop setUp() throws Exception {
			// getting the carshop loads it
			CarShop cs = getCarShop();
			//cs.delete();
			
			// sets to current date and time
	        long millis=System.currentTimeMillis();  
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd+HH:mm").format(new Date(millis));
			
			setCurrentDateAndTime(timeStamp);	// sets in carshopapplication

			// sets the owner
			if(cs.getOwner() == null) {
				CarShopController.newAccount("owner",  "owner", cs);
			}

			List<Technician> technicians = cs.getTechnicians();
			if(technicians.size() != 5) {
				if(technicians.size() == 0) {
					CarShopController.newAccount("Tire-Technician", "Tire-Technician", cs);
					CarShopController.newAccount("Engine-Technician", "Engine-Technician", cs);
					CarShopController.newAccount("Transmission-Technician", "Transmission-Technician", cs);
					CarShopController.newAccount("Electronics-Technician", "Electronics-Technician", cs);
					CarShopController.newAccount("Fluids-Technician", "Fluids-Technician", cs);
				
					for(Technician t : cs.getTechnicians()) {
						t.setGarage(new Garage(cs, t));
					}
				}else {
					for(Technician t:technicians) {
						if(CarShopController.findTechnician("Tire-Technician",cs)==null) {
							CarShopController.newAccount("Tire-Technician", "Tire-Technician", cs);
						}
						if(CarShopController.findTechnician("Engine-Technician",cs)==null) {
							CarShopController.newAccount("Engine-Technician", "Engine-Technician", cs);
						}
						if(CarShopController.findTechnician("Transmission-Technician",cs)==null) {
							CarShopController.newAccount("Transmission-Technician", "Transmission-Technician", cs);
						}
						if(CarShopController.findTechnician("Electronics-Technician",cs)==null) {
							CarShopController.newAccount("Electronics-Technician", "Electronics-Technician", cs);
						}
						if(CarShopController.findTechnician("Fluids-Technician",cs)==null) {
							CarShopController.newAccount("Fluids-Technician", "Fluids-Technician", cs);
						}
						for(Technician tech : cs.getTechnicians()) {
							tech.setGarage(new Garage(cs, t));
						}

					}
				}
			}
			
			// fills up garages
			for(Technician t: technicians) {
				if(t.getGarage() == null) {
					t.setGarage(new Garage(cs, t));
				}
			}
			
			return cs;
			

		}

		private static void setCurrentDateAndTime(String date) throws ParseException {
			String datePattern = "yyyy-MM-dd";
			String timePattern = "HH:mm";
			String[] splitString = date.split("\\+");// uses a formatter from java.sql.Date/Time
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);// create a new object
			SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);// create a new object
			Date newDate = null;
			Time newTime = null;

			// uses a formatter from java.sql.Date/Time
			newDate = new java.sql.Date(dateFormatter.parse(splitString[0]).getTime());// create a new object
			newTime = new java.sql.Time(timeFormatter.parse(splitString[1]).getTime());// create a new object

			CarShopApplication.setSystemDate(newDate);
			CarShopApplication.setSystemTime(newTime);
		}


}


