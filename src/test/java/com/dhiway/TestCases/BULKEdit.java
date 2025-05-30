package com.dhiway.TestCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;
import com.dhiway.pages.RecordsPage;

public class BULKEdit extends BaseClass{
    @Test
    public void Bulkedittc()throws InterruptedException, IOException{
 String testcasename = "BulkEdit";
        
        // Ensure the driver is initialized
        PageFactory.initElements(driver, this);

        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        // Fetching data from Excel
        ExcelUtils TestData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = TestData.getTestData();

        // Check if "email" is in the first row and get corresponding data
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        }

        Thread.sleep(1000);
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new java.util.Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");
        Lp.submitButton();
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);
WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
Loginbtn.click();
Thread.sleep(20000);
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/verify.jpg");

        // WebDriver wait for the element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        ExcelUtils Testcases = new ExcelUtils("Testcases");
        if (createspace != null) {
            RecordsPage RP = new RecordsPage(driver);
            RP.searchBox();

            Thread.sleep(2000);
            // Selecting the space
            
                WebElement selectspace = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > p:nth-child(2)")));
                selectspace.click();
            
            Thread.sleep(10000);

            // Wait for the dropdown element to be visible and clickable
            WebElement Dropdownelement = wait.until(ExpectedConditions.elementToBeClickable(By.id("optionsDropDown")));
            Dropdownelement.click();  // Click the dropdown to expand it

        WebElement BulkEditbtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("editbulk")));
        BulkEditbtn.click();
        Thread.sleep(2000);
        WebElement Downloadbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Download']")));
        Downloadbtn.click();
        Thread.sleep(10000);
        WebElement Uploadbtn= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='UPLOAD']")));
Uploadbtn.click();

Thread.sleep(2000);
WebElement Previewrecordbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='float-right medium-button mr-1 cursor-pointer font-medium btn btn-primary']")));
Previewrecordbtn.click();
Thread.sleep(2000);
WebElement UpdateRecordsbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='float-right medium-button mr-1 cursor-pointer font-medium btn btn-primary']"))); //button[class='float-right medium-button mr-1 cursor-pointer font-medium btn btn-primary']
UpdateRecordsbtn.click();
Thread.sleep(10000);
 Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/BulkEditdown.jpg");

            Thread.sleep(6000);

            String result = config.getProperty("StudioBaseUrl") + "admin/dashboard/records-list/";
            if (driver.getCurrentUrl().startsWith(result)) {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime().toString(), "Passed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(true);
            } else {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime().toString(), "Error", "TestCaseFailed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(false);
            }
        } else {
            List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime().toString(), "Error", "TestCaseFailed");
            Testcases.writeDataToSheet("Testcases", testcasename, data);
            AssertJUnit.assertTrue(false);
        }

        TestData.close();
        Testcases.close();
    }
    
}
