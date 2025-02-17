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
import com.dhiway.pages.RecordsPage;

public class Bulkupload  extends BaseClass {
/**
 * @throws InterruptedException
 * @throws IOException
 */
@Test
    public void BulkRecordinSpace() throws InterruptedException, IOException {
        String testcasename = "Bulkupload";
        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        // Fetching data from excel
        ExcelUtils TestData = new ExcelUtils(testcasename);

        // Get all data from the first row
        Map<String, String> firstRowData = TestData.getTestData();

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
Thread.sleep(20000);
        Thread.sleep(2000);
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/verify.jpg");

        // WebDriver wait for element visibility
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space"))); 
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        ExcelUtils Testcases = new ExcelUtils("Testcases");
        if (createspace != null) {

            // Checking if the dashboard is visible or not
            WebElement selectspace = driver.findElement(By.cssSelector("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > p:nth-child(2)"));
                selectspace.click();
        
            Thread.sleep(2000);
            

            RecordsPage RP = new RecordsPage(driver);
           // RP.addRecordbtn();
            
            // Find the element for Bulk record and check if it's not null
            WebElement Addrecord = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='add-record']"))); 
            Addrecord.click();
            WebElement BulkRecord = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id=\"bulk-record-id\"]")));
            
            if ( BulkRecord != null) {
                BulkRecord.click();
            } else {
                System.out.println("Bulk Record button element is not found!");
            }

           // WebElement BulkUpload = driver.findElement(By.xpath("//u[text()='Click Here']"));
            RP.clickherelink();
            Thread.sleep(20000);
           

           //WebElement previewRecord = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Preview Records']")));
            RP.PreviewRecord();
            Thread.sleep(20000);
            RP.BulkaddRecord();
            Thread.sleep(20000);
            // Taking a screenshot after adding Record
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/BulkRecordcreated.jpg");

            Thread.sleep(5000);

            String result = config.getProperty("StudioBaseUrl") + "admin/dashboard";
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

    
