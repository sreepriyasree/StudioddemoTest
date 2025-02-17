package com.dhiway.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditRegistrypage {
    WebDriver driver;
    WebDriverWait wait;
public EditRegistrypage(WebDriver driver){
    this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Define WebDriverWait here
        PageFactory.initElements(driver, this);  // Initialize page elements
}

@FindBy (css = "#scrollableDiv > div.main-container.fade-ui.mt-2 > div > div.infinite-scroll-component__outerdiv > div > div > div:nth-child(2)")
WebElement FirstSpace;
@FindBy(css="div.cursor-pointer:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1)")
WebElement threedot;
@FindBy(css  = "div.cursor-pointer:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1) > ul:nth-child(2) > li:nth-child(2)")
WebElement EditRegistrybtn;
@FindBy(xpath = "//input[@id='space-name-input-id']")
WebElement Spacenametextbox;
@FindBy(xpath ="//input[@id='searchSpace-id']")
WebElement Searchbox;
public void searchBox(){
    Searchbox.sendKeys("Test Space Creation");
    Searchbox.click();
}
public void EditRegistry() throws InterruptedException{
    FirstSpace.isEnabled();
    threedot.click();
    Thread.sleep(20000);
    EditRegistrybtn.click();
    Thread.sleep(10000);
    Spacenametextbox.sendKeys("edited T");
    Thread.sleep(10000);
    WebElement UpdateRegistrybtn = driver.findElement(By.xpath("//button[text()='Update Registry']"));
    UpdateRegistrybtn.click();
}
}
