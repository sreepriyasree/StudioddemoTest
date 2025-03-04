package com.dhiway.TestCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;


import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BulkDownloadpdfwithoutbg extends BaseClass {

    private static final By DROPDOWN_ELEMENT = By.id("optionsDropDown");
    private static final By BULK_DOWNLOAD_ELEMENT = By.id("bulkdown");
    private static final By PDF_WITHOUT_ELEMENT = By.id("pdfwithout");
    private static final By CREATE_SPACE = By.id("create-space");

    @Test
    public void BulkDownloadwithoutbkgd() throws InterruptedException, IOException {
        String testcasename = "Bulkdownloadwithoutbkgd";

        // Initialize PageFactory for the driver
        PageFactory.initElements(driver, this);

        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        // Fetching data from Excel
        ExcelUtils TestData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = TestData.getTestData();

        // Log in with email from Excel
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        } else {
            throw new IllegalArgumentException("Email is missing in the test data");
        }

        // Submit login and capture screenshot
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        Lp.submitButton();
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);
WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
Loginbtn.click();
Thread.sleep(20000);
        String timestamp = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss").format(LocalDateTime.now());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + "_" + timestamp + "/login.jpg");

        // Wait for the dashboard to load
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(CREATE_SPACE));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + "_" + timestamp + "/Dashboard.jpg");

        // Test result log
        ExcelUtils Testcases = new ExcelUtils("Testcases");

       
            // Select space if available
            if (createspace != null && firstRowData.containsKey("spacename")) {
                String spacename = firstRowData.get("spacename");
                WebElement searchregistry= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='searchSpace-id']")));
                searchregistry.sendKeys("Newreg");
                WebElement selectspace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='" + spacename + "']")));
                selectspace.click();

                // Interact with the dropdown and Bulk Download elements
                WebElement Dropdownelement = wait.until(ExpectedConditions.elementToBeClickable(DROPDOWN_ELEMENT));
                Dropdownelement.click(); // Expand dropdown

                WebElement BulkDownloadele = wait.until(ExpectedConditions.elementToBeClickable(BULK_DOWNLOAD_ELEMENT));
                BulkDownloadele.click();
                
                WebElement pdfElement = wait.until(ExpectedConditions.elementToBeClickable(PDF_WITHOUT_ELEMENT));
                pdfElement.click();

                // Wait for the download process
               Thread.sleep(20000);

                // Take screenshot after download
                Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                        "Screenshots/" + testcasename + "_" + timestamp + "/Bulkpdfdownloadwithoutbkgd.jpg");

                // Validate the URL after the operation
                String expectedUrlStart = config.getProperty("StudioBaseUrl") + "admin/dashboard/records-list/";
            if (driver.getCurrentUrl().startsWith(expectedUrlStart)) {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(true);
            } else {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error", "TestCaseFailed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(false);
            }

            // Close resources
            TestData.close();
            Testcases.close();
        }
    
    
    }}
