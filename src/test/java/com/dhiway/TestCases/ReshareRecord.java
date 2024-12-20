package com.dhiway.TestCases;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class ReshareRecord extends BaseClass {
    @Test
    public void Resharerecords() throws InterruptedException, IOException {
        String testcasename = "ReshareRecord";

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");
                ExcelUtils Testcases = new ExcelUtils("Testcases");
        if (createspace != null) {
            if (firstRowData.containsKey("spacename")) {
                String Spacename = firstRowData.get("spacename");
                WebElement selectspace = driver.findElement(By.xpath("//h6[text()='" + Spacename + "']"));
                selectspace.click();
            }
            WebElement Resharebtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#table-container > table > tbody > tr:nth-child(1) > td:nth-child(1) > span > a")));
            Resharebtn.click();
            Thread.sleep(2000);
            WebElement Textarea= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main-container\"]/div[4]/div/div[5]/div/div[2]/div/div/div/div[1]/div/div[2]/textarea")));
            Textarea.sendKeys("Test textarea reshare single record");
WebElement AdminCCselect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class='h6 font-regular text-label d-flex flex-row mb-0']")));
AdminCCselect.click();

    WebElement Customccselect= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class='h6 font-regular text-label d-flex flex-row']")));
Customccselect.click();
WebElement Customemail = driver.findElement(By.xpath("//input[@id='email']"));
        Customemail.sendKeys("sreepriya+360@dhiway.com");
        WebElement Sendbtn= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=\"Send\"]")));
Sendbtn.click();

Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/Reshare.jpg");

        }
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
