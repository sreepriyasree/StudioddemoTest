package com.dhiway.TestCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class CreateDraftDesignTC extends BaseClass {
    @Test
    public void createDraftDesign() throws InterruptedException, IOException {
        String testcasename = "CreateDraftDesign";
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
        WebElement Loginbtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-btn-id")));
        Loginbtn.click();
        Thread.sleep(20000);
    
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");
    
        ExcelUtils Testcases = new ExcelUtils("Testcases");
        Thread.sleep(10000);
        
        WebElement Designerbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#designer-id > span")));
        Designerbtn.click();
        Thread.sleep(5000);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String schematitle = "Test Schema Creation" ;

        WebElement Searchfield = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-name-modal']")));
        Searchfield.sendKeys(schematitle);
        Thread.sleep(6000);

        WebElement Selectschema = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#root > div > div:nth-child(2) > div > div > div:nth-child(3) > div:nth-child(2)")));
        Selectschema.click();
        Thread.sleep(6000);

        WebElement Addbtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"space-template-cards\"]/div")));
        Addbtn.click();
        Thread.sleep(6000);

        WebElement Letsstartdesignbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".submit-design")));
        Letsstartdesignbtn.click();
        Thread.sleep(3000);

        WebElement Addfieldbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#add-button")));
        Addfieldbtn.click();
        Thread.sleep(2000);

        // **Handling Dropdown for Variable Selection**
        WebElement variables = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dropdown-variables-items.position-relative")));
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", variables);

variables.click();



        Thread.sleep(2000);

        WebElement Namefield = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[class='dropdown-variables-items position-relative'] li:nth-child(1)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", Namefield);
        Thread.sleep(2000);

        Addfieldbtn.click();
        Thread.sleep(2000);
        // **Handling Background Selection**
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".dropdown-variables-items")));

        WebElement Backgndbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul[id='dropdown-variables'] li:nth-child(5)")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Backgndbtn);
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", Backgndbtn);

        // Ensure dropdown is closed before interacting
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".dropdown-variables-items")));

        // **Selecting Recent Background**
        WebElement Recentbkgnd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".font-size-14.border-0.light.cursor-pointer.mb-0.pb-2.nav-link")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Recentbkgnd);
        Thread.sleep(500);

        // Click using JavaScript for reliability
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", Recentbkgnd);

        // Verify selection success
        Thread.sleep(3000);
       

        // **Selecting Default Background**
        Thread.sleep(3000);
        WebElement defbckgnd = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img[src='https://markstudio-test.s3.ap-south-1.amazonaws.com/apidata/image/oc31123b8-49b2-4a04-bcd8-a825044fd885_c3dd6bfa-d2fc-4d80-94c2-e81514b4cfaf..png']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", defbckgnd);
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", defbckgnd);

      
      

        // **Saving the Design**
        Thread.sleep(6000);
        WebElement Exitdesignerbtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".back-button-header")));
        Exitdesignerbtn.click();
        Thread.sleep(6000);

       // String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        WebElement Savenametext = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder='Name this design']")));
        Savenametext.sendKeys("Testdesignname_" + timestamp);
        Thread.sleep(6000);

        WebElement MarkasDraftbtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='draft']")));
        MarkasDraftbtn.click();
        Thread.sleep(6000);

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/Designsaved.jpg");

        boolean isUrlCorrect = wait.until(ExpectedConditions.urlContains("/space/editor"));
        AssertJUnit.assertTrue(isUrlCorrect);

        testData.close();
        Testcases.close();
    }
}
