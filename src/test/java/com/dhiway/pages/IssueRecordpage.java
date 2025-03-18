package com.dhiway.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IssueRecordpage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public IssueRecordpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Define WebDriverWait here
        PageFactory.initElements(driver, this);  // Initialize page elements
    }

    @FindBy(xpath = "//button[text()= 'Issue']")
    WebElement Issuebtn;

  
   

   

    @FindBy(xpath = "//label[@class='h6 font-regular text-label d-flex flex-row mb-0']")
    WebElement Adminccselect;

    @FindBy(xpath = "//label[@class='h6 font-regular text-label d-flex flex-row']")
    WebElement CustomCCselect;

    @FindBy(xpath = "//button[text()='Issue Now']")
    WebElement Issuenowbtn;

    @FindBy(xpath = "//button[@id='optionsDropDown']")
    WebElement threedotsbtn;

    @FindBy(xpath = "//li[@id='issueallcred']")
    WebElement IssueAllCredbtn;

    @FindBy(xpath = "//input[@id='styled-checkbox-0']")
    WebElement firstrecordsel;

    @FindBy(xpath = "//span[text()='Issue']")
    WebElement TopIssuebtn;

    
    public void Issuebtnclick() {
        wait.until(ExpectedConditions.elementToBeClickable(Issuebtn)).click();
    }

    public void Enablebtnclck() throws InterruptedException {
        WebElement enablebtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='checkbox']")));
        wait.until(ExpectedConditions.elementToBeClickable(enablebtn));
    
        // Scroll into view if necessary
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", enablebtn);
    
        // Check the current state of the toggle button (e.g., aria-checked attribute)
        String toggleState = enablebtn.getAttribute("aria-checked");
    
        // If the toggle is off, click it to turn it on
        if (toggleState.equals("false")) {
            // Use JavaScript to click the checkbox if it's being intercepted by other elements
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", enablebtn);
    
            // Wait a short time for the state change
            Thread.sleep(1000); // Optional: Adjust the time as needed
            
            // Re-check the state after clicking
            String newToggleState = enablebtn.getAttribute("aria-checked");
            System.out.println("Toggle state after clicking: " + newToggleState);
        }
    }
    


    public void Textareaentry() throws InterruptedException {
       
        WebElement Textarea = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='email-textarea-holder']//textarea")));  // Or the correct tag inside the div
        Textarea.sendKeys("Mail check text area TEST");
        
        Thread.sleep(10000);
    }

    public void Adminselectcc() {
        Adminccselect.click();
    }

    public void Customselectcc() {
       
        CustomCCselect.click();
        WebElement Customemail = driver.findElement(By.xpath("//input[@id='email']"));
        Customemail.sendKeys("sreepriya+360@dhiway.com");
    }

    public void IssueNowbtnmain() {
        Issuenowbtn.click();
    }

    public void threedots() {
        threedotsbtn.click();
    }

    public void IssueAllCred() {
        IssueAllCredbtn.click();
    }

    public void TopIssue() {
        TopIssuebtn.click();
    }

    public void firstrecordselection() {
        firstrecordsel.click();
    }
}
