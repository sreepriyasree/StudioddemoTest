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

public class EditRegistrypage {
    WebDriver driver;
    WebDriverWait wait;

    public EditRegistrypage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    // Corrected CSS selector to avoid NoSuchElementException
    @FindBy(css = "#scrollableDiv .main-container.fade-ui.mt-2 .infinite-scroll-component__outerdiv > div > div > div:nth-child(2)")
    WebElement FirstSpace;

    @FindBy(css="div.cursor-pointer:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(1)")
    WebElement threedot;

    @FindBy(id = "edit-space-action-id")
    WebElement EditRegistrybtn;

    @FindBy(xpath = "//input[@id='space-name-input-id']")
    WebElement Spacenametextbox;

    @FindBy(xpath = "//input[@id='searchSpace-id']")
    WebElement Searchbox;

    public void searchBox() {
        wait.until(ExpectedConditions.elementToBeClickable(Searchbox)).click();
        Searchbox.sendKeys("Test Space Creation");
    }

    public void EditRegistry() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    
    wait.until(ExpectedConditions.elementToBeClickable(FirstSpace));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", FirstSpace);
    
    wait.until(ExpectedConditions.elementToBeClickable(threedot));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", threedot);

    wait.until(ExpectedConditions.elementToBeClickable(EditRegistrybtn)).click();

    wait.until(ExpectedConditions.visibilityOf(Spacenametextbox)).sendKeys("edited T");

    WebElement UpdateRegistrybtn = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//button[text()='Update Registry']")));
    UpdateRegistrybtn.click();
}

}
