package com.dhiway.TestCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;
import com.dhiway.pages.Revokepage;

public class RevokeTC extends BaseClass {

    @Test
    public void RevokeSingle() throws InterruptedException, IOException {
        String testcasename = "RevokeSingleRecord";
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

        // Screenshot after login
        String datetimetoday = java.time.LocalDate.now().toString();
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");

        Lp.submitButton();
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);
WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
Loginbtn.click();

        // Wait for the dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        if (createspace != null) {
            if (firstRowData.containsKey("spacename")) {
                String Spacename = firstRowData.get("spacename");
                WebElement selectspace = driver.findElement(By.xpath("//h6[text()='" + Spacename + "']"));
                selectspace.click();
            }

            // Initialize IssueRecordpage
            Revokepage RP = new Revokepage(driver);

            // Handle the Issued or Drafts case
            Thread.sleep(20000);
            WebElement issuedElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main-container > div.pt-6-new > div > div.main-container > div > div.d-flex.flex-row.justify-content-between > div.d-flex.flex-row > p.mb-0.cursor-pointer.h6.font-regular.width-tab.h6.text-primary.box-shadow1.bg-white")));
            
            if (issuedElement.isDisplayed()) {
                issuedElement.click();
            }

            // Select the first record to revoke
            WebElement firstCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#table-container > table > tbody > tr:nth-child(1) > td:nth-child(1) > label")));
            firstCheckbox.click();


            Thread.sleep(20000);  // Consider removing or reducing this sleep

            RP.Revokebtnclick();
            Thread.sleep(20000);
            RP.revokeaction();
            Thread.sleep(2000);
            RP.FinalRevokebtn();
            Thread.sleep(20000);

            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/SingleRevoke.jpg");
            Thread.sleep(5000);

            // Verify URL after operation
            WebElement selectspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-record")));
            ExcelUtils Testcases = new ExcelUtils("Testcases");
            if (selectspace != null) {
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

            TestData.close();
            Testcases.close();
        }
    }
}
