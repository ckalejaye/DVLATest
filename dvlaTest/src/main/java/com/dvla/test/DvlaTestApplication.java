package com.dvla.test;


//import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber; 

@RunWith(Cucumber.class)
@CucumberOptions( format = {"pretty", "html:target/cucumber"},
	    features = {"Feature"})
public class DvlaTestApplication {
	
	//private static WebDriver driver = null;
	
	public static void main(String[] args) {
		//FirefoxProfile profile = new FirefoxProfile(); 
		//profile.setPreference("webdriver.firefox.port", 4444); 
		//WebDriver driver = new FirefoxDriver(profile);
		
		//ProfilesIni allProfiles = new ProfilesIni();
		//FirefoxProfile profile = allProfiles.getProfile("FirefoxDriver");
		//profile.setPreference(FirefoxProfile.PORT_PREFERENCE, 4444);
		//driver = new FirefoxDriver(profile);//


		//driver = new FirefoxDriver();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//driver.get("http://www.satconcepts.com");
		
	}
}