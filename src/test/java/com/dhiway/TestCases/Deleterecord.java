package com.dhiway.TestCases;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
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
import com.dhiway.pages.LoginPage;

public class Deleterecord extends BaseClass {

    @Test
    public void DeleteSingle() throws InterruptedException, IOException {
        String testcasename = "DeleteSingleRecord";
        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        ExcelUtils TestData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = TestData.getTestData();

        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        }

        String datetimetoday = java.time.LocalDate.now().toString();
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");

        Lp.submitButton();
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);

        WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
        Loginbtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));

        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        WebElement Filterby = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".css-8mmkcg")));
        Filterby.click();
        WebElement Activetab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='react-select-2-option-1']")));
        Activetab.click();
        Thread.sleep(2000);

        if (createspace != null) {
            WebElement Searchspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#searchSpace-id")));
            Searchspace.sendKeys("Test Space Creation");

            WebElement Selectspace = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                    "div:nth-child(1) div:nth-child(2) div:nth-child(1) div:nth-child(1) div:nth-child(2) div:nth-child(1) div:nth-child(1) div:nth-child(1) div:nth-child(1) div:nth-child(2) h6:nth-child(1)")));
            Selectspace.click();

            WebElement issuedElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                    "div[class='d-flex flex-row'] p:nth-child(1)")));
            Thread.sleep(2000);
            if (issuedElement.isDisplayed()) {
               WebElement draftClick = wait.until(ExpectedConditions.presenceOfElementLocated(
    By.xpath("//p[contains(text(),'Draft')]")));

                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", draftClick);
                Thread.sleep(1000);

                // Click using JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", draftClick);
                Thread.sleep(2000);
            }

            retryClick(By.xpath("//*[@id='table-container']/table/tbody/tr[1]/td[1]/label"));
            Thread.sleep(2000);

            WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                    "button[class='btn-sm btn btn-danger h6 font-regular text-white mb-0 ml-2 p-2 mb-0 btn btn-primary'] span[class='align-bottom']")));
            deleteButton.click();

            WebElement Finaldeletebtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div[class='modal-content'] button:nth-child(2)")));
            Finaldeletebtn.click();

            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/DeletedRecord.jpg");
            Thread.sleep(5000);

            WebElement selectspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-record")));
            ExcelUtils Testcases = new ExcelUtils("Testcases");

            if (selectspace != null) {
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

    private void retryClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement element = wait.until(ExpectedConditions.refreshed(
                        ExpectedConditions.elementToBeClickable(locator)));
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying due to stale element: " + locator);
            }
            attempts++;
        }
        throw new RuntimeException("Failed to click element after retries: " + locator);
    }
}
