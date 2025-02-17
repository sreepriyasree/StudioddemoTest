package com.dhiway.TestCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class CreateDesignTC extends BaseClass {
    @Test
    public void createDesign() throws InterruptedException, IOException {
        String testcasename = "CreateDesign";
        LoginPage loginPage = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));
        
        ExcelUtils testData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = testData.getTestData();
    
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            loginPage.enterUsername(email);
        }
    
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");
    
        loginPage.submitButton();
    
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    
        loginPage.enterotp();
        Thread.sleep(2000);
        WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
        Loginbtn.click();
        Thread.sleep(20000);
    
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");
    
        ExcelUtils Testcases = new ExcelUtils("Testcases");
        Thread.sleep(10000);
        WebElement Designerbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#designer-id > span")));
        Designerbtn.click();
        Thread.sleep(5000);
        WebElement Searchfield = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-name-modal']")));
        Searchfield.sendKeys("Test Schema Creation 20");
        Thread.sleep(6000);
        WebElement Selectschema= wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(" #root > div > div:nth-child(2) > div > div > div:nth-child(3) > div:nth-child(2)")));
        Selectschema.click();
        Thread.sleep(6000);
        WebElement Addbtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"space-template-cards\"]/div")));
        Addbtn.click();
        Thread.sleep(6000);
        WebElement Letsstartdesignbtn= wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".submit-design")));
        Letsstartdesignbtn.click();
    
        WebElement Addfieldbtn =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='add-button']")));
        Addfieldbtn.click();
        Thread.sleep(2000);
        
        // Wait until dropdown disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".dropdown-variables-items")));
        
        // Scroll element into view before clicking
        WebElement variables = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dropdown-variables-items.position-relative")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", variables);
        Thread.sleep(2000);
        variables.click();
        
        Thread.sleep(2000);
        WebElement Namefield = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[class='dropdown-variables-items position-relative'] li:nth-child(1) span:nth-child(1)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", Namefield);
        
        Thread.sleep(6000);
        Addfieldbtn.click();
        
        WebElement Backgndbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dropdown-variables > li:nth-child(5)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Backgndbtn);
        Thread.sleep(500);
        Backgndbtn.click();
        
        Thread.sleep(6000);
        WebElement Recentbkgnd = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(2) > div:nth-child(2) > div:nth-child(4) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(1)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Recentbkgnd);
        Thread.sleep(500);
        Recentbkgnd.click();
        
        Thread.sleep(6000);
        WebElement defbckgnd = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'d-flex flex-row flex-wrap bg-white px-3')]//img")));
        defbckgnd.click();
        Thread.sleep(6000);
        WebElement Exitdesignerbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".back-button-header")));
        Exitdesignerbtn.click();
        Thread.sleep(6000);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
WebElement Savenametext = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder='Name this design']")));
Savenametext.sendKeys("Testdesignname_" + timestamp);
        Thread.sleep(6000);
        WebElement Savebtn =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='save']")));
        Savebtn.click();
        Thread.sleep(6000);
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
        "Screenshots/" + testcasename + " " + datetimetoday + "/Designsaved.jpg");
        
        boolean isUrlCorrect = wait.until(ExpectedConditions.urlContains("/space/editor"));
        AssertJUnit.assertTrue(isUrlCorrect);
        testData.close();
        Testcases.close();
    }
}
