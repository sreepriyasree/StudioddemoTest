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
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.IssueRecordpage;
import com.dhiway.pages.LoginPage;
import com.dhiway.pages.RecordsPage;

public class Activitiestab extends BaseClass {

    @Test
    public void Activitiestabtc() throws InterruptedException, IOException {
        String testcasename = "Activitiestab";

        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        ExcelUtils TestData = new ExcelUtils(testcasename);
        ExcelUtils Testcases = new ExcelUtils("Testcases");
        Map<String, String> firstRowData = TestData.getTestData();

        if (firstRowData.containsKey("email")) {
            Lp.enterUsername(firstRowData.get("email"));
        }

        Thread.sleep(1000);
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");

        Lp.submitButton();
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);
        driver.findElement(By.id("login-btn-id")).click();
        Thread.sleep(20000);

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/verify.jpg");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        WebElement Filterby = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".css-8mmkcg")));
        Filterby.click();
        WebElement Activetab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-select-2-option-1\"]")));
        Activetab.click();
        Thread.sleep(2000);

        WebElement Searchregistry = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#searchSpace-id")));
        Searchregistry.sendKeys("Test Space ");
        Thread.sleep(6000);

        if (createspace != null) {
            WebElement selectspace = driver.findElement(By.cssSelector(
                    "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > p:nth-child(2)"));
            selectspace.click();
            Thread.sleep(2000);

            RecordsPage RP = new RecordsPage(driver);
            RP.addRecordbtn();
            Thread.sleep(5000);

            WebElement Singlerecord = driver.findElement(By.xpath("//button[@id='single-record-id']"));
            Singlerecord.click();
            Thread.sleep(2000);

            if (firstRowData.containsKey("Name")) {
                driver.findElement(By.xpath("//input[@id='Name']")).sendKeys(firstRowData.get("Name"));
            }

            Thread.sleep(5000);

            if (firstRowData.containsKey("Email")) {
                driver.findElement(By.xpath("//input[@id='Email ID']")).sendKeys(firstRowData.get("Email"));
            }

            Thread.sleep(2000);
            RP.SubmitAddRecord();
            Thread.sleep(10000);

            IssueRecordpage IR = new IssueRecordpage(driver);

            WebElement issuedElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                    "div[class='d-flex flex-row'] p:nth-child(1)")));
            if (issuedElement.isDisplayed()) {
                issuedElement.click();
            } else {
                WebElement draftClick = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                        "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(14) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > p:nth-child(2)")));
                draftClick.click();
            }

            WebElement firstCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                    "#table-container > table > tbody > tr:nth-child(1) > td:nth-child(1) > label")));
            firstCheckbox.click();

            IR.Issuebtnclick();
            Thread.sleep(2000);
            IR.Enablebtnclck();
            Thread.sleep(10000);
            IR.Textareaentry();
            Thread.sleep(2000);
            IR.Adminselectcc();
            Thread.sleep(2000);
            IR.Customselectcc();
            Thread.sleep(2000);
            IR.IssueNowbtnmain();
            Thread.sleep(2000);

            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/SingleIssue.jpg");

            Thread.sleep(5000);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".react-responsive-modal-container.react-responsive-modal-containerCenter")));

            WebElement Activitytab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                    "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(13) > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1)")));
            Activitytab.click();

  Thread.sleep(5000);
            WebElement latest = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Latest']")));
            Thread.sleep(5000);
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/Activitytab.jpg");

            if (latest != null) {
                Testcases.writeDataToSheet("Testcases", testcasename,
                        Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed"));
                AssertJUnit.assertTrue(true);
            } else {
                Testcases.writeDataToSheet("Testcases", testcasename,
                        Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Failed"));
                AssertJUnit.assertTrue(false);
            }

            TestData.close();
            Testcases.close();
        }
    }
}
