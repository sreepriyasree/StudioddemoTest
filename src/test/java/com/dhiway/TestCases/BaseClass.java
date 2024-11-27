package com.dhiway.TestCases;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.dhiway.Utilities.ReadConfig;
import io.github.bonigarcia.wdm.WebDriverManager;


   
public class BaseClass {
    ReadConfig read = new ReadConfig();
    public FirefoxDriver driver;

	@BeforeMethod
	
    public void setUp() {
       
        WebDriverManager.chromedriver().setup();
       // driver = new ChromeDriver();
       driver = new FirefoxDriver();
       System.setProperty("Webdriver.firefox.driver", "/Users/sreepriyasreekumar/Desktop/StudiodemoTest/Drivers/geckodriver");
       // System.setProperty("webdriver.chrome.driver", "/Users/sreepriyasreekumar/Desktop/StudiodemoTest/Drivers/chromedriver");

        //System.setProperty("webdriver.chrome.driver", "/Users/sreepriyasreekumar/Desktop/StudiodemoTest/Drivers/chromedriver.app");
         // No need for casting
        //driver = WebDriverManager.chromedriver().create();
        ((WebDriver) driver).manage().window().maximize();
        ((WebDriver) driver).manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

 
	@AfterMethod
	
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Ensures the driver is stopped
        }
    }
}