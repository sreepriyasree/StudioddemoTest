package com.dhiway.TestCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import com.dhiway.pages.CreateSpacepage;
import com.dhiway.pages.LoginPage;

public class CreateSpacewithCertificateSelection extends BaseClass {
    
    /**
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void Spacecreation() throws InterruptedException, IOException {
        String testcasename = "CreateSpacewithCertificate";
        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        // Fetching test data from Excel
        ExcelUtils TestData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = TestData.getTestData();

        // Logging in
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        }
        
        Lp.submitButton();
        Thread.sleep(2000);
        // Screenshot after login
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new java.util.Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");

        // Wait until 'Create Space' button is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        // Proceed with space creation
        if (createspace != null) {
            CreateSpacepage CS = new CreateSpacepage(driver);
            CS.createspacebtnclick();

            if (firstRowData.containsKey("spacename")) {
                CS.spacenameenter(firstRowData.get("spacename"));
            }
            if (firstRowData.containsKey("spacedescription")) {
                CS.spacedescriptionenter(firstRowData.get("spacedescription"));
            }

            // Choose design for space
            CS.choosedesignbtnclick();
            Thread.sleep(8000);

            WebElement certificateAccordionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(@class, 'accordion-header')]//button[.//span[contains(text(), 'Certificate')]]")
            ));
            
            // Check if the accordion is collapsed (contains the class 'collapsed')
            String isCollapsed = certificateAccordionButton.getAttribute("class");
            if (isCollapsed.contains("collapsed")) {
                // Scroll into view if needed
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", certificateAccordionButton);
            
                // Click the button to expand the accordion
                certificateAccordionButton.click();
            }
            Thread.sleep(8000);
        // Click on the certificate named "Check -june20"
        WebElement selectedCert = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//*[@id=\"june-13th8\"]")));
        Thread.sleep(8000);
        selectedCert.click();

            Thread.sleep(10000);


            // Add certificate to the space and submit
            CS.addthistospacebtnclick();
            Thread.sleep(2000);
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/Createspace.jpg");
            CS.submitbtnclick();
            Thread.sleep(20000);
            Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
            "Screenshots/" + testcasename + " " + datetimetoday + "/spaceview.jpg");

            // Verify space creation and log the result
            WebElement selectspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-record")));
            ExcelUtils Testcases = new ExcelUtils("Testcases");
            if (selectspace != null) {
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

            // Close Excel files
            TestData.close();
            Testcases.close();
      
        
    }
}
}


