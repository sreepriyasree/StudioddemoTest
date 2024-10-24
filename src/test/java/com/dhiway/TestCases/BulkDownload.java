package com.dhiway.TestCases;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;
import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.LoginPage;

public class BulkDownload extends BaseClass {
    @Test
    public void BulkDownloadCredentials () throws IOException, InterruptedException {
         String testcasename = "BulkDownload";
        
        // Ensure the driver is initialized
        PageFactory.initElements(driver, this);

        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

        // Fetching data from Excel
        ExcelUtils TestData = new ExcelUtils(testcasename);
        Map<String, String> firstRowData = TestData.getTestData();

        // Check if "email" is in the first row and get corresponding data
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        }

        Thread.sleep(1000);
        String datetimetoday = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new java.util.Date());
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");
        Lp.submitButton();
        Thread.sleep(2000);
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/verify.jpg");

        // WebDriver wait for the element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");

        ExcelUtils Testcases = new ExcelUtils("Testcases");
        if (createspace != null) {
            // Selecting the space
            if (firstRowData.containsKey("spacename")) {
                String Spacename = firstRowData.get("spacename");
                WebElement selectspace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='" + Spacename + "']")));
                selectspace.click();
            }
            Thread.sleep(10000);

            // Wait for the dropdown element to be visible and clickable
            WebElement Dropdownelement = wait.until(ExpectedConditions.elementToBeClickable(By.id("optionsDropDown")));
            Dropdownelement.click();  // Click the dropdown to expand it
            WebElement BulkDownloadele = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bulkdown")));

            WebElement pdfeElement= wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#pdf")));
            Thread.sleep(1000);
                 Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    "Screenshots/" + testcasename + " " + datetimetoday + "/Bulkpdfdownload.jpg");
                    // Verify URL after operation
            String expectedUrlStart = config.getProperty("StudioBaseUrl") + "admin/dashboard/records-list/";
            if (driver.getCurrentUrl().startsWith(expectedUrlStart)) {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Passed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                Assert.assertTrue(true);
            } else {
                List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime(), "Error", "TestCaseFailed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);
                Assert.assertTrue(false);
            }
       

        TestData.close();
        Testcases.close();
    }
}
    }
    

