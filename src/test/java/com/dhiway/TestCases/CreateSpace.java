package com.dhiway.TestCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
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
import com.dhiway.Utilities.DateTimeUtil;
import com.dhiway.Utilities.ExcelUtils;
import com.dhiway.Utilities.ReadConfig;
import com.dhiway.Utilities.Screenshot;
import com.dhiway.pages.CreateSpacepage;
import com.dhiway.pages.LoginPage;

public class CreateSpace extends BaseClass{
    //private static final ExcelUtils Testcases = null;

    @Test
    public void Spacecreation() throws InterruptedException, IOException{
        String testcasename = "CreateSpace";
        LoginPage Lp = new LoginPage(driver);
        ReadConfig config = new ReadConfig();
        driver.get(config.getProperty("StudioBaseUrl"));

 
    ExcelUtils TestData = new ExcelUtils(testcasename);

        // Get all data from the first row
        Map<String, String> firstRowData = TestData.getTestData();

        // Check if "email" is in the first row and get corresponding data
        if (firstRowData.containsKey("email")) {
            String email = firstRowData.get("email");
            Lp.enterUsername(email);
        }

        Thread.sleep(1000);
        String datetimetoday = "10/10/2024";
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/login.jpg");
        Lp.submitButton();
        
        Thread.sleep(2000);
        Lp.enterotp();
        Thread.sleep(2000);
WebElement Loginbtn = driver.findElement(By.id("login-btn-id"));
Loginbtn.click();
Thread.sleep(20000);
      
        // webdriver wait given for wait till the elemt is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60000));
        WebElement createspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-space")));
        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Dashboard.jpg");
                if (createspace != null) {
                CreateSpacepage CS = new CreateSpacepage(driver);
                CS.createspacebtnclick();
               if (firstRowData.containsKey("spacename")) {
                  //  String spacename = firstRowData.get("spacename");
                    CS.spacenameenter(firstRowData.get("spacename"));
                }
             

                if (firstRowData.containsKey("spacedescription")) {
                    //  String spacename = firstRowData.get("spacename");
                      CS.spacedescriptionenter(firstRowData.get("spacedescription"));
                  }

                  CS.choosedesignbtnclick();
                  Thread.sleep(8000);
                 // WebElement searchbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@class='ml-auto cursor-pointer']")));
                 //searchbox.click();
                //  Thread.sleep(8000);
                  WebElement searchbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@class='ml-auto cursor-pointer']")));
                    searchbox.click(); // Ensure the click happens if necessary

    
                  //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                  WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("template-search-in32")));
                  element.sendKeys("Test design");
                 // element.submit();
                //  searchbox.sendKeys("sept17-2-sree-afternoon");
                  
                  Thread.sleep(8000);
                  
                 

               /*  if (firstRowData.containsKey("templatetype")) {
                    String templatetype = firstRowData.get("templatetype");
                    WebElement templateschema = driver.findElement(By.xpath("//span[text()= '"+ templatetype + "']"));
                    templateschema.click();
                } 
               // Thread.sleep(10000);
                if (firstRowData.containsKey("design")) {
                    String design = firstRowData.get("design");
                    try {
                        //WebElement templatedesignsel = driver.findElement(By.xpath("//*[@id=\"template-cards\"]/div[1]/p'" + templatedesign + "']"));
                        //WebElement templatedesignsel = driver.findElement(By.linkText( "//p[text()=' Copy of Template 28']"));
                        WebElement templatedesignsel = driver.findElement(By.xpath("//*[starts-with(@id,'sept17-2-sree-afternoon')]"));


                        templatedesignsel.click();
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", templatedesignsel);
                        Thread.sleep(30000);
                       
                    } catch (Exception e) {
                        System.out.println("Error interacting with template design: " + e.getMessage());
                        Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                                "Screenshots/" + testcasename + " " + datetimetoday + "/error.jpg");
                    }
                } */
                Thread.sleep(10000);
                CS.addthistospacebtnclick();
                Thread.sleep(2000);
                Screenshot.saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                "Screenshots/" + testcasename + " " + datetimetoday + "/Createspace.jpg");
                CS.submitbtnclick();
                Thread.sleep(2000);
                ExcelUtils Testcases = new ExcelUtils("Testcases");
            if (firstRowData.containsKey("spacename")) {
              //  String Spacename = firstRowData.get("spacename");
                WebElement selectspace = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-one-id")));
               // ExcelUtils Testcases = new ExcelUtils("Testcases");
                if (selectspace!=null){
                     List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime().toString(), "Passed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);

                AssertJUnit.assertTrue(true);
                }
                else{
                    List<String> data = Arrays.asList(DateTimeUtil.getCurrentDateTime().toString(), "failed");
                Testcases.writeDataToSheet("Testcases", testcasename, data);

                AssertJUnit.assertTrue(false);  
                }
            }

                

              
                TestData.close();
                Testcases.close();
            }
}
}