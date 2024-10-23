package com.dhiway.TestCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
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
import com.dhiway.pages.EditRegistrypage;
import com.dhiway.pages.LoginPage;

public class EditRegistryTC extends BaseClass {
    @Test
    public void Editregistry() throws InterruptedException, IOException {
        String testcasename = "Editregistry";
         LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));
         ExcelUtils TestData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = TestData.getTestData();

        // Log in with email from Excel
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        }

        // Screenshot after login
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
        "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");

        Lp.submitButton();

        // Wait for the dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        if (createspace != null) {
            EditRegistrypage ER = new EditRegistrypage(driver);
            ER.searchBox();
            Thread.sleep(20000);
            ER.EditRegistry();
            Thread.sleep(2000);
            // Take a screenshot after issuing the record
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/Editregistry.jpg");
            Thread.sleep(5000);
          

WebElement cs = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

ExcelUtils Testcases = new ExcelUtils("Testcases");

            if (cs != null) {
                // Log test result as "Passed"
                java.util.List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                Assert.assertTrue(true);
            } else {
                // Log test result as "Failed"
                java.util.List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Failed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                Assert.assertTrue(false);
            }
           
            TestData.close();
            Testcases.close();
        }

    }
    
}
