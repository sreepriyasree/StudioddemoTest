package com.dhiway.TestCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class IssueAll extends BaseClass {

    @Test
    public void IssueAllCredential() throws IOException, InterruptedException {
        String testcasename = "IssueAll";
        
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
            // Selecting the space
            if (firstRowData.containsKey("spacename")) {
                String Spacename = firstRowData.get("spacename");
                WebElement selectspace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='" + Spacename + "']")));
                selectspace.click();
            }
            Thread.sleep(10000);

            // Wait for the dropdown element to be visible and clickable
            WebElement Dropdownelement = wait.until(ExpectedConditions.elementToBeClickable(By.id("optionsDropDown")));
            Dropdownelement.click();  // Click the dropdown to expand it

            // Find and click the "Issue all credentials" option
            WebElement issueAllOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#issueallcred")));
            issueAllOption.click();
            
            Thread.sleep(2000);

            // Handle the enable button toggle using JavaScript to avoid ElementClickInterceptedException
            WebElement switchElement = driver.findElement(By.xpath("//input[@role='switch']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", switchElement);
            Thread.sleep(500);  // Give it some time to scroll
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", switchElement);  // Click the switch via JavaScript
Thread.sleep(20000);
            // Fill out the rest of the form
            WebElement Textarea = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='email-textarea-holder']//textarea")));  // Or the correct tag inside the div
            Textarea.sendKeys("Mail check text area");
            
            Thread.sleep(6000);
            WebElement Adminccselect = driver.findElement(By.xpath("//label[@class='h6 font-regular text-label d-flex flex-row mb-0']"));
            Adminccselect.click();
            Thread.sleep(10000);
            WebElement CustomCCselect = driver.findElement(By.xpath("//label[@class='h6 font-regular text-label d-flex flex-row']"));
            CustomCCselect.click();
            WebElement Customemail = driver.findElement(By.xpath("//input[@id='email']"));
            Customemail.sendKeys("sreepriya+360@dhiway.com");
            Thread.sleep(2000);
            WebElement Issuenowbtn = driver.findElement(By.xpath("//button[text()='Issue Now']"));
            Issuenowbtn.click();
    
            Thread.sleep(10000);

            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/ALLRecordsIssued.jpg");

            Thread.sleep(5000);
            WebElement Issuedtab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main-container\"]/div[4]/div/div[14]/div/div[1]/div[1]/p[1]")));
          //  ExcelUtils Testcases = new ExcelUtils("Testcases");
            if (Issuedtab != null) {
                // Log test result as "Passed"
                java.util.List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(true);
            } else {
                // Log test result as "Failed"
                java.util.List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Failed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(false);
            }
            // Verify URL after operation
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
       

        TestData.close();
        Testcases.close();
    }
}
}