package com.dhiway.TestCases;

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
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class EditSchemaTC extends BaseClass {

    @Test
    public void Editschema() throws InterruptedException, IOException {
        String testcasename = "EditSchema";
        LoginPage loginPage = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));
        
        ExcelUtils testData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = testData.getTestData();

        // Log in with email from Excel
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            loginPage.enterUsername(email);
        }

        // Screenshot after login
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");

        loginPage.submitButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Wait for OTP input and submit
      loginPage.enterotp();
        Thread.sleep(2000);
WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
Loginbtn.click();
Thread.sleep(20000);

        // Wait for the dashboard to load
        WebElement createSpaceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");
            ExcelUtils Testcases = new ExcelUtils("Testcases");
            if (createSpaceBtn != null) {
                // Checking if the dashboard is visible or not
                String result = config.getProperty("StudioBaseUrl") + "admin/dashboard";
                if (driver.getCurrentUrl().startsWith(result)) {
                    List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                    Testcases.writeDataToSheet("Testcases", "LoginTC", data);
                    AssertJUnit.assertTrue(true);
                } else {
                    List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error",
                            "TestCaseFailed");
                    Testcases.writeDataToSheet("Testcases", "LoginTC", data);
                    AssertJUnit.assertTrue(false);
                }
        // Navigate to Designer and search for schema
        WebElement designerBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#designer-id > span")));
        designerBtn.click();

        WebElement searchTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-name-modal']")));
        searchTab.sendKeys("Test ");
        
        
        WebElement schemaSelected = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
            "#root > div > div:nth-child(2) > div > div > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > section > p:nth-child(2)")));
        schemaSelected.click();

        // Edit schema
        WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
            "#space-editor-container > div > div.positon-edit-schema-designer.w-100 > div > div:nth-child(2) > button")));
        editBtn.click();

        WebElement schemaNameTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='title-schema']")));
        schemaNameTab.sendKeys("Schemaedited");

        // Save the changes
        WebElement nextBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
            "#root > div > div > div.mt-2 > div > div.col-md-5.col-lg-5.schema-cards-container.overflow-webkit-none.px-0.mt-60 > div > div > div.tab-content > div.tab-pane.fade-ui.active > div > div > div.position-next > button")));
        nextBtn.click();

        WebElement SaveSchemaBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='SAVE SCHEMA']")));
            SaveSchemaBtn.click();
            Thread.sleep(20000);
    
        // Take screenshot after editing the schema
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/EditSchema.jpg");

        // Validate the URL
        String expectedUrlStart = config.getProperty("StudioBaseUrl") + "space/editor/";
        if (driver.getCurrentUrl().startsWith(expectedUrlStart)) {
            List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
            Testcases.writeDataToSheet("Testcases", testcasename, data);
            AssertJUnit.assertTrue(true);
        } else {
            List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error", "TestCaseFailed");
            Testcases.writeDataToSheet("Testcases", testcasename, data);
            AssertJUnit.assertTrue(false);
        }

        testData.close();
        Testcases.close();
    }
}
}
