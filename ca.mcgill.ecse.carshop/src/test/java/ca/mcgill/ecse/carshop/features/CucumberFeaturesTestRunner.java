package ca.mcgill.ecse.carshop.features;

import io.cucumber.java.en.Given;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.CarShop;

@RunWith(Cucumber.class)  // /AppointmentManagement.feature
@CucumberOptions(plugin = "pretty", features = "src/test/resources")
public class CucumberFeaturesTestRunner {
}