package com.dhiway.TestCases;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class NewDownloadbtn extends BaseClass {
 @Test
    public void downloadButtonOption() throws InterruptedException, IOException {
        String testcasename = "NewDownloadpdfwithbkgndIssued";

        // Initialize driver and page objects
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
        }

        // Wait for the login button, submit, and capture screenshot
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        Lp.submitButton();
        Thread.sleep(2000);
        Lp.enterotp();
            Thread.sleep(2000);

            // Click Login button
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-btn-id")));
            loginBtn.click();
            Thread.sleep(20000);

            // Capture login screenshot
            String timestamp = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss").format(LocalDateTime.now());
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + "_" + timestamp + "/login.jpg");

            // Wait for the dashboard to load and capture screenshot
          //  WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + "_" + timestamp + "/Dashboard.jpg");

            // Select space if specified in test data
            if (firstRowData.containsKey("spacename")) {
                String spaceName = firstRowData.get("spacename");
                WebElement searchregistry= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='searchSpace-id']")));
                searchregistry.sendKeys("Newreg");
                WebElement selectSpace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='" + spaceName + "']")));
                selectSpace.click();
                Thread.sleep(2000);
            }

            // Perform actions on Issued tab
            WebElement issuedTab = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("p.cursor-pointer:nth-child(1)")));
            issuedTab.click();

            WebElement actions = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='styled-checkbox-all']")));//label[for='styled-checkbox-all']
            actions.click();
Thread.sleep(2000);
            // Click the download button and select PDF with background
            WebElement newDownloadBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Download']")));
            newDownloadBtn.click();

            WebElement pdfWithBackground = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img[alt='PDF with Bg']"))); //img[alt='PDF with Bg']
            pdfWithBackground.click();

            WebElement finalDownloadBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-dwnld")));//button[class='btn-dwnld letter-space-min opacity-handle font-regular btn btn-secondary']
            finalDownloadBtn.click();
            Thread.sleep(10000);

            // Save screenshot after download
            String dateTimeToday = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss").format(LocalDateTime.now());
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + "_" + dateTimeToday + "/DownloadNew.jpg");

            // Verify the final URL and log the test result
            String expectedUrlStart = config.getProperty("StudioBaseUrl") + "admin/dashboard/records-list/";
            ExcelUtils testCases = new ExcelUtils("Testcases");
            if (driver.getCurrentUrl().startsWith(expectedUrlStart)) {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                testCases.writeDataToSheet("Testcases", testcasename, data);
                Assert.assertTrue(true);
            } else {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error", "TestCaseFailed");
                testCases.writeDataToSheet("Testcases", testcasename, data);
                Assert.fail("Test case failed");
            }
            testCases.close();
       
            // Close resources
            TestData.close();
        }
    }

