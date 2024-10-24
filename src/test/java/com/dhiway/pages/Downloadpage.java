package com.dhiway.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Downloadpage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public IssueRecordpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Define WebDriverWait here
        PageFactory.initElements(driver, this);  // Initialize page elements
    
}

}
