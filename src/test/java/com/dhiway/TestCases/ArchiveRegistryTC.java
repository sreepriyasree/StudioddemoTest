package com.dhiway.TestCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.Archivepage;
import com.dhiway.pages.LoginPage;

public class ArchiveRegistryTC extends BaseClass {

    @Test
    public void ArchiveRegistry() throws InterruptedException, IOException {
        String testcasename = "Archiveregistry";
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
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);
        WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
        Loginbtn.click();
        Thread.sleep(20000);

        // Wait for the dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        WebElement Filterby = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".css-8mmkcg")));
        Filterby.click();
        WebElement Activetab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-select-2-option-1\"]")));
        Activetab.click();
        Thread.sleep(2000);

        if (createspace != null) {
            Archivepage AR = new Archivepage(driver);
            AR.searchBox();
            Thread.sleep(20000);

            // ✅ Fix: Locate FirstSpace dynamically
          
                WebElement FirstSpace = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("#scrollableDiv > div.main-container.fade-ui.mt-2 > div > div.infinite-scroll-component__outerdiv > div > div > div:nth-child(2)")));

FirstSpace.isEnabled();
                // Scroll into view
                //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", FirstSpace);
                Thread.sleep(1000);

            

           
                WebElement threedot = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1) > div:nth-child(1)")));
               
                Thread.sleep(1000);
                threedot.click();
           

            
                WebElement archiveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//div[1]//div[2]//div[1]//div[1]//div[2]//div[1]//div[1]//div[1]//div[1]//div[3]//button[1]//ul[1]//li[3]")));

                //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", archiveButton);
                Thread.sleep(1000);
                archiveButton.click();
           

            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/ArchiveRegistry.jpg");

            Thread.sleep(5000);

            WebElement cs = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

            ExcelUtils Testcases = new ExcelUtils("Testcases");

            if (cs != null) {
                java.util.List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(true);
            } else {
                java.util.List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Failed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(false);
            }

            TestData.close();
            Testcases.close();
        }
    }
}
