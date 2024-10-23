package com.dhiway.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Revokepage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public Revokepage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Define WebDriverWait here
        PageFactory.initElements(driver, this);  // Initialize page elements
    }

    @FindBy(xpath =" //span[text()='Revoke']")
    WebElement Revokebtn;
    @FindBy(css="#table-container > table > tbody > tr.active-row > td:nth-child(1) > span > button:nth-child(2) > img")
    WebElement Revokebtnimage;
  
   public void Revokebtnclick(){
     wait.until(ExpectedConditions.elementToBeClickable(Revokebtn)).click();
    }
    public void Revokebtnimageclick(){
        wait.until(ExpectedConditions.elementToBeClickable(Revokebtnimage)).click();
    }
        public void revokeaction() throws InterruptedException{
            driver.findElement(By.cssSelector("#main-container > div.pt-6-new > div > div:nth-child(1) > div > div.react-responsive-modal-container.react-responsive-modal-containerCenter > div"));
              WebElement Textarea =driver.findElement(By.xpath("//textarea[@id='Revoke']"));
              Textarea.sendKeys("Revoke record test");
              Thread.sleep(10000);
              }

   
public void FinalRevokebtn(){
    WebElement FinalRevokeBtn= driver.findElement(By.xpath("//button[text()='Revoke']"));
    FinalRevokeBtn.click();

}
   }
