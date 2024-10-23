package com.dhiway.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateAccount {

    public CreateAccount(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "first-name-create")
    WebElement firstnamecreate;

    @FindBy(id = "phone-number-create")
    WebElement phonenumberbox;

    @FindBy(id = "company-name-create")
    WebElement organizationBox;

    @FindBy(id = "email-create")
    WebElement emailBox;

    @FindBy(id = "register-account-btn-id")
    WebElement registerbtn;

    public void firstname(String uname) {
        firstnamecreate.sendKeys(uname);
    }

    public void phonenumber(String number) {
        phonenumberbox.sendKeys(number);
    }

    public void organization(String number) {
        organizationBox.sendKeys(number);
    }

    public void email(String number) {
        emailBox.sendKeys(number);
    }

    public void registerButton() {
        registerbtn.click();
    }
}
