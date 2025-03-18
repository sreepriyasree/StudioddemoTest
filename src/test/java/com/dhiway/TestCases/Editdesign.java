package com.dhiway.TestCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class Editdesign extends BaseClass {
    @Test
    public void DesignEdit() throws InterruptedException, IOException {
        String testcasename = "EditDesign";
        LoginPage loginPage = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));
        
        ExcelUtils testData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = testData.getTestData();
    
        // Log in with email from Excel
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            loginPage.enterUsername(email);
        }
    
        // Screenshot after login
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");
    
        loginPage.submitButton();
    
        // Use a single WebDriverWait instance throughout the method
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    
        loginPage.enterotp();
        Thread.sleep(2000);
        WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
        Loginbtn.click();
        Thread.sleep(20000);
    
        // Wait for the dashboard to load
        //WebElement createSpaceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");
    
        ExcelUtils Testcases = new ExcelUtils("Testcases");
        Thread.sleep(10000);
        WebElement Designerbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#designer-id > span")));
        Designerbtn.click();
        Thread.sleep(10000);
        WebElement Searchfield = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-name-modal']")));
        Searchfield.sendKeys("Test Schema Creation");
        Thread.sleep(6000);
        WebElement Selectschema= wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(" #root > div > div:nth-child(2) > div > div > div:nth-child(3) > div:nth-child(2)")));
        Selectschema.click();
        Thread.sleep(6000);
WebElement design1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#template-card-preview-image")));
design1.click();
Thread.sleep(6000);
WebElement Addshape = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[data-name='circle']")));
Addshape.click();
Thread.sleep(6000);
WebElement Exitdesignerbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".back-button-header")));
Exitdesignerbtn.click();
Thread.sleep(6000);
WebElement Updatedesign =wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".text-success")));
Updatedesign.click();
Thread.sleep(10000);
Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
"Screenshots/" + testcasename + " " + datetimetoday + "/DesignUpdated.jpg");
   // Validate the URL
   String expectedUrlStart = config.getProperty("StudioBaseUrl") + "/space/editor";
   try {
       boolean isUrlCorrect = wait.until(ExpectedConditions.urlContains("/space/editor"));
       if (isUrlCorrect) {
           List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
           Testcases.writeDataToSheet("Testcases", testcasename, data);
           AssertJUnit.assertTrue(true);
       } else {
           throw new AssertionError("URL does not match the expected start.");
       }
   } catch (Exception e) {
       List<String> data = Arrays.asList(
           DateTimeUtil.getCurrentDateTime().toString(),
           "Error",
           "TestCaseFailed",
           "Expected URL: " + expectedUrlStart,
           "Actual URL: " + driver.getCurrentUrl(),
           "Error Message: " + e.getMessage()
       );
       Testcases.writeDataToSheet("Testcases", testcasename, data);
       AssertJUnit.assertTrue(false);
   }

   testData.close();
   Testcases.close();
}
}

    

