package com.dhiway.TestCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
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
import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class SettingsTC extends BaseClass {

    @Test
    public void Settings() throws InterruptedException, IOException {
        String testcasename = "SettingsTC";
        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        // Fetching data from excel
        ExcelUtils TestData = new ExcelUtils(testcasename);

        // Get all data from the first row
        Map<String, String> firstRowData = TestData.getTestData();

        // Log in using data from Excel
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        }

        Thread.sleep(1000);
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");
        Lp.submitButton();
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);
WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
Loginbtn.click();


        // Wait until the dashboard is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
       WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");
                if(createspace!= null){
        // Click on the user image and logout
        WebElement userImageElement = driver.findElement(By.xpath("//a[@class='avatar avatar-sm']"));
        userImageElement.click();
        Thread.sleep(1000);  // Short wait to ensure the dropdown is fully displayed
        WebElement SettingsBtn = driver.findElement(By.xpath("//button[@id='settings-id']"));
        SettingsBtn.click();
        Thread.sleep(2000);  // Wait for logout process
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Settingsscreen.jpg");

        

                 
                ExcelUtils Testcases = new ExcelUtils("Testcases");
                //if (createspace != null) {
                    // Checking if the dashboard is visible or not
                    String result = config.getProperty("StudioBaseUrl") + "admin/settings/";
                    if (driver.getCurrentUrl().startsWith(result)) {
                        List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                        Testcases.writeDataToSheet("Testcases", "LogoutTC", data);
                        AssertJUnit.assertTrue(true);
                    } else {
                        List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error",
                                "TestCaseFailed");
                        Testcases.writeDataToSheet("Testcases", "LogoutTC", data);
                        AssertJUnit.assertTrue(false);
                    }
                
        
                TestData.close();
                Testcases.close();
            }
        }
}