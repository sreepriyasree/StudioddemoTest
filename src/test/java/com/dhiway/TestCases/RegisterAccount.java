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
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.CreateAccount;
import com.dhiway.pages.LoginPage;

public class RegisterAccount extends BaseClass{
     @Test
    public void RegisterNewAccount() throws InterruptedException, IOException {
   String testcasename= "RegisterAccount";
   LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        // Fetching data from excel
        ExcelUtils TestData = new ExcelUtils(testcasename);
        Thread.sleep(2000);

        // Get all data from the first row
        Map<String, String> firstRowData = TestData.getTestData();
String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");
        Lp.registerButton();
        Thread.sleep(1000);
        CreateAccount CA= new CreateAccount(driver);
     if(firstRowData.containsKey("fullname")){
        CA.firstname(firstRowData.get("fullname"));
        
     }
     if(firstRowData.containsKey("mob") && firstRowData.containsKey("orgname") && firstRowData.containsKey("email")){
        CA.phonenumber("9876545646");
        CA.organization(firstRowData.get("orgname"));
        CA.email(firstRowData.get("email"));
        Thread.sleep(3000);
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/register.jpg");
        CA.registerButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60000));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        ExcelUtils Testcases = new ExcelUtils("Testcases");
    if (createspace != null) {
            // Checking if the dashboard is visible or not
            String result = config.getProperty("StudioBaseUrl") + "admin/dashboard";
            if (driver.getCurrentUrl().startsWith(result)) {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                Testcases.writeDataToSheet("Testcases",testcasename, data);
                Assert.assertTrue(true);
            } else {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error",
                        "TestCaseFailed");
                Testcases.writeDataToSheet("Testcases",testcasename, data);
                Assert.assertTrue(false);
            }
        } else {
            List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error", "TestCaseFailed");
            Testcases.writeDataToSheet("Testcases",testcasename, data);
            Assert.assertTrue(false);
        }

        TestData.close();
        Testcases.close();
    }
     
    }
    
}
