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
import org.testng.AssertJUnit;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class NewDownloadbtn extends BaseClass
{
    public void downloadButtonoption() throws InterruptedException, IOException{
         String testcasename =  "NewDownloadpdfwithbkgndIssued";
              // Ensure the driver is initialized
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
WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
Loginbtn.click();
Thread.sleep(20000);
        String timestamp = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss").format(LocalDateTime.now());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + "_" + timestamp + "/login.jpg");

        // Wait for the dashboard to load
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + "_" + timestamp + "/Dashboard.jpg");

        // Test result log
        ExcelUtils Testcases = new ExcelUtils("Testcases");

        // Select space if available
        if (createspace != null) {
            if (firstRowData.containsKey("spacename")) {
                String spacename = firstRowData.get("spacename");
                WebElement selectspace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='" + spacename + "']")));
                selectspace.click();
                Thread.sleep(2000);
            }
            WebElement Issuedtab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div[4]/div/div[14]/div/div[1]/div[1]/p[1]"))));

            if(Issuedtab !=null){
                WebElement Actions = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mb-4 > th:nth-child(1) > label:nth-child(2)")));
                Actions.click();
            }
WebElement newDownloadbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tn-sm")));
newDownloadbtn.click();
WebElement pdfwithbkd = driver.findElement(By.cssSelector("ul.d-flex:nth-child(11) > li:nth-child(1) > img:nth-child(1)"));
pdfwithbkd.click();
WebElement FinalDownloadbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Download")));
FinalDownloadbtn.click();
Thread.sleep(10000);
Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/Downloadnew.jpg");


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
