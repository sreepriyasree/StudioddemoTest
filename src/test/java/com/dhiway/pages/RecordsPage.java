package com.dhiway.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecordsPage {
    WebDriver driver;

    public RecordsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "add-record") 
    WebElement addRecord;
@FindBy(xpath = "//button[text()='Add Record']")
WebElement SubmitAddRecord;
@FindBy(xpath = "//u[text()='Click Here']")
WebElement clicklink;
@FindBy(xpath = "//button[text()='Preview Records']")
WebElement previewrecordbtn;
@FindBy(xpath = "//button[text()='Add Records']")
WebElement BulkAddRecordsBtn;

    public void addRecordbtn() {
        addRecord.click();
    }

    public void SubmitAddRecord() {
        SubmitAddRecord.click();
    }
    //Bulk upload
    public void clickherelink() throws InterruptedException{
        clicklink.click();
       
          //xpath -> //u[text()='Click Here']
    }
    public void PreviewRecord(){
        previewrecordbtn.click();  // xpath -> //button[text()='Preview Records']
    }
    public void BulkaddRecord() {
        BulkAddRecordsBtn.click();
    }
}
