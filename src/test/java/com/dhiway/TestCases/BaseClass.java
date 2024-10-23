package com.dhiway.TestCases;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.dhiway.Utilities.ReadConfig;

   
    public class BaseClass
	{
	    ReadConfig read = new ReadConfig();
	    public WebDriver driver;
	 


	    @BeforeClass
	    public void SetUp() {
	        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
	     
	        driver = new ChromeDriver();  // Initialize the ChromeDriver
	        driver.manage().window().maximize();  // Maximize the browser window
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  
	     
	    }

	   
		@AfterClass
	    public void tearDown() {
	        driver.quit();
	    }

    public ReadConfig getRead() {
        return read;
    }
	}