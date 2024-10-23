package com.dhiway.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateSpacepage {
    WebDriver driver;

    public CreateSpacepage(WebDriver driver) {
        this.driver = driver;
        extracted(driver);
    }
    private void extracted(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "create-space")
    WebElement createspacebtn;

    @FindBy(id = "space-name-input-id")
    WebElement spacename;

    @FindBy(id = "space-description-id")
    WebElement spacedescription;

    @FindBy(id = "web-url")
    WebElement weburl;

    @FindBy(id = "space-tags-id")
    WebElement spacetags;

    @FindBy(id = "choose-design-template-id")
    WebElement choosedesignbtn;
@FindBy(xpath = "//button[text()='Create Registry']")
WebElement submitbtn;
    

// selecting certificate and design ( help )

    //add-this-to-btn-id
    @FindBy(id = "add-this-to-btn-id")
    WebElement addthistospacebtn;
    

    public void createspacebtnclick() {
        createspacebtn.click();
    }
    public void spacenameenter(String name){
        spacename.sendKeys(name);
    }
    public void spacedescriptionenter(String name){
        spacedescription.sendKeys(name);
    }
    public void choosedesignbtnclick(){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", choosedesignbtn);
        choosedesignbtn.click();
    }
    public void addthistospacebtnclick(){
        addthistospacebtn.click();
    }
    public void submitbtnclick(){
        submitbtn.click();
    }
}
