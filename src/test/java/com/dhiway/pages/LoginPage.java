package com.dhiway.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "email")
    WebElement usernameBox;

    @FindBy(id = "login-btn-id")
    WebElement SignInBtn;
    @FindBy(xpath = "//*[@id=\"otpcode\"]/div[1]/input")
    WebElement Otp;

    @FindBy(id = "create-account-btn-id")  
    WebElement registerButton;

    public void enterUsername(String uname) {
        usernameBox.sendKeys(uname);
      
    }
    
public void enterotp(){
    Otp.sendKeys("913345");
}
    public void registerButton() {
        registerButton.click();
    }

    public void submitButton() {
        SignInBtn.click();
    }

    public WebDriver getDriver() {
        return driver;
    }
   
}
