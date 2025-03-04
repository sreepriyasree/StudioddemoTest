package com.dhiway.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Deleterecordpage {
     WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public void DeleteRecordpages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Define WebDriverWait here
        PageFactory.initElements(driver, this);  // Initialize page elements
    }

    //@FindBy(xpath = "//span[text()= 'Delete']")
   // WebElement Deletebtn;
    public void Deletebtnclick() {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='btn-sm btn btn-danger h6 font-regular text-white mb-0 ml-2 p-2 mb-0 btn btn-primary'] span[class='align-bottom']")));
deleteButton.click();

      
    }
}
