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


public class CreateSchemaTC extends BaseClass {

    @Test
    public void createschema() throws InterruptedException, IOException {
        String testcasename = "CreateSchema";
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
    
        // Use a single WebDriverWait instance throughout the method
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    
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
            String result = config.getProperty("StudioBaseUrl") + "admin/dashboard";
            if (driver.getCurrentUrl().startsWith(result)) {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                Testcases.writeDataToSheet("Testcases", "LoginTC", data);
                AssertJUnit.assertTrue(true);
            } else {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error", "TestCaseFailed");
                Testcases.writeDataToSheet("Testcases", "LoginTC", data);
                AssertJUnit.assertTrue(false);
            }
    
            // Navigate to Designer and search for schema
            WebElement designerBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#designer-id > span")));
            designerBtn.click();
            Thread.sleep(1000);
            WebElement Createschematempbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#root > div > div:nth-child(2) > div > div > div:nth-child(2) > div > p")));
            Createschematempbtn.click();
            Thread.sleep(10000);
           

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
String schemaTitleText = "Test Schema Creation " + timestamp;

WebElement SchemaTitle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='title-schema']")));
SchemaTitle.click();
SchemaTitle.sendKeys(schemaTitleText);
    
            WebElement SchemaDesc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='schema-desc-id']")));
            SchemaDesc.click();
            SchemaDesc.sendKeys("Test Schema description text");
    
            WebElement SchemaVer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='version-id']")));
            SchemaVer.sendKeys("2");
    
            WebElement nextBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='m-0 mr-2 w-100 py-3 bg-dark-blue-banner text-white font-bold h5 next-btn btn btn-primary']")));
            nextBtn.click();
            Thread.sleep(2000);
    
            WebElement Addfieldbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='ADD A FIELD']")));
            Addfieldbtn.click();
    
            WebElement Requiredcheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='required_checkbox0']")));
            Requiredcheckbox.click();
    
            WebElement Fieldnamebox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Field Name']")));
            Fieldnamebox.sendKeys("name test");
    
            WebElement Selectlist = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#scroll-bottom > div.padding-right-20 > div > div:nth-child(3) > div > div > div.css-1wy0on6")));
            Selectlist.click();
    
            WebElement Textsel = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-3-option-1")));
            Textsel.click();
    
            WebElement FieldDesc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Description']")));
            FieldDesc.sendKeys("Description text name");
    
            WebElement SaveSchemaBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class='m-0 mr-2 w-100 py-3 bg-dark-blue-banner text-white font-bold h5 next-btn btn btn-primary disabled']")));
            SaveSchemaBtn.click();
            Thread.sleep(20000);
    
            // Take screenshot after editing the schema
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/CreateSchema.jpg");
    
            // Validate the URL
            String expectedUrlStart = config.getProperty("StudioBaseUrl") + "/space/editor";
            try {
                boolean isUrlCorrect = wait.until(ExpectedConditions.urlContains("/space/editor"));
                if (isUrlCorrect) {
                    List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                    Testcases.writeDataToSheet("Testcases", testcasename, data);
                    AssertJUnit.assertTrue(true);
                } else {
                    throw new AssertionError("URL does not match the expected start.");
                }
            } catch (Exception e) {
                List<String> data = Arrays.asList(
                    DateTimeUtil.getCurrentDateTime().toString(),
                    "Error",
                    "TestCaseFailed",
                    "Expected URL: " + expectedUrlStart,
                    "Actual URL: " + driver.getCurrentUrl(),
                    "Error Message: " + e.getMessage()
                );
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                AssertJUnit.assertTrue(false);
            }
    
            testData.close();
            Testcases.close();
        }
    }
}
    