package com.dhiway.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Archivepage {
    WebDriver driver;
    WebDriverWait wait;
public Archivepage(WebDriver driver){
    this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Define WebDriverWait here
        PageFactory.initElements(driver, this);  // Initialize page elements
}
@FindBy(xpath ="//input[@id='searchSpace-id']")
WebElement Searchbox;
@FindBy (css = "#scrollableDiv > div.main-container.fade-ui.mt-2 > div > div.infinite-scroll-component__outerdiv > div > div > div:nth-child(2)")
WebElement FirstSpace;
@FindBy(css="body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1) > div:nth-child(1)")
WebElement threedot;
@FindBy(css = "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1) > ul:nth-child(2) > li:nth-child(3)")
WebElement ArchiveRegistrybtn;
@FindBy(xpath = "//input[@id='space-name-input-id']")
WebElement Spacenametextbox;
public void searchBox(){
    Searchbox.sendKeys("Test space");
    Searchbox.click();
}
public void ArchiveRegistry() throws InterruptedException{
    FirstSpace.isEnabled();
    threedot.click();
    
    
    Thread.sleep(10000);
    ArchiveRegistrybtn.click();
    Thread.sleep(20000);

    WebElement ArchiveRegistryfinalbtn = driver.findElement(By.cssSelector("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(4) > button:nth-child(2)"));
    ArchiveRegistryfinalbtn.click();
}
}
