package com.dvla.test;
 

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then; 

public class DvlaTest {
	 
	
	private static WebDriver driver = null;
	private List<DvlaFiles> fileList;
	private List<String> dvlalist;
	
	public DvlaTest(){ 
	}
	

	@Given("^a request to the API to list files$")
    public void aRequestToTheAPIToListFiles() throws Throwable {
		URL url = new URL("http://localhost:88/file/list"); 
        URLConnection urlc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlc
            .getInputStream()));
        
        StringBuffer response = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();  
		

		ObjectMapper mapper = new ObjectMapper(); // json converter object
		this.fileList = mapper.readValue(response.toString(), 
				TypeFactory.defaultInstance().constructCollectionType(List.class, DvlaFiles.class)); // converting json response to a list of file object 
		
		
	}
 
	// this is to locate a csv file and if none found an exception is thrown
	// if file found it is downloaded
	@And("^get a csv file from the list$")
    public void getACsvFileFromTheList() throws Throwable {
		DvlaFiles csvFile = null;
		
		for (ListIterator<DvlaFiles> iter = this.fileList.listIterator(); iter.hasNext(); ) {
			DvlaFiles element = iter.next();
		    if(element.getExtension().equals("csv")) {
		    		csvFile = element; 
		    		break;
		    }
		}
		
		if(csvFile == null) {
			throw new Exception();
		}
		
		// file object converted to json string and posted to the second end point 
		String postData = (new ObjectMapper()).writeValueAsString(csvFile);
		
		URL obj = new URL("http://localhost:88/file/get");
  		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
  		con.setRequestProperty("Content-Type", "application/json");
  		con.setRequestMethod("POST");   
		 
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postData); wr.flush(); wr.close();

		// file read from second end point
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		this.dvlalist = new ArrayList<String>();
		String inputLine;
		while ((inputLine = in.readLine()) != null) { 
			this.dvlalist.add(inputLine);
		}
		in.close();   
 
	}
	
	
	@Then("^confirm all vehicles license$")
    public void confirmAllVehiclesLicense() throws Throwable {
		PrintWriter pw = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();
        
       // driver = new FirefoxDriver(); 
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444"),capability);
		driver.get("https://vehicleenquiry.service.gov.uk/");
		
		for (ListIterator<String> iter = this.dvlalist.listIterator(); iter.hasNext(); ) {
			String reg_num =iter.next().toString();
			WebElement strLocator = driver.findElement(By.xpath("//*[@id='Vrm']"));
			strLocator.sendKeys(reg_num);
			strLocator.submit();
			 
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.invisibilityOf(strLocator));

			sb.append(reg_num); 
			List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Vehicle details could not be found')]")); 
			if(list.size() > 0) {
			//String bodyText = driver.findElement(By.tagName("body")).getText();
			//if(bodyText.contains("Vehicle details could not be found")) {
			//	Assert.assertTrue("Text found", true);
				sb.append(",nothing"); 
			}
			else {
				sb.append(",color/make"); 
			}
			
			sb.append("\n");  
			
		    driver.navigate().to("https://vehicleenquiry.service.gov.uk/");    
		}
     
        pw.write(sb.toString());
        pw.close(); 
	}
	

	
}
