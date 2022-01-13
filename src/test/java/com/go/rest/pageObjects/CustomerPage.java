package com.go.rest.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomerPage {
    WebDriver driver;

    public CustomerPage(WebDriver driver){
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy (xpath ="//a[@class='btn btn-primary']")
    WebElement btnAddnew;

    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissable']")
    WebElement SuccessMessage;

    @FindBy(id="SearchEmail")
    WebElement txtSearch;

    @FindBy(id="search-customers")
    WebElement btnSearch;

    @FindBy(xpath = "//table[@id='customers-grid']//tbody//tr//td[2]")
    WebElement getEmail;

    public void clickAddNew() {
        btnAddnew.click();
    }

    public String verifySucessMessage() {
      String actualScuccessMessage =  SuccessMessage.getText();
      return actualScuccessMessage;
    }

    public void setSearch(String email) throws InterruptedException {
        Thread.sleep(3000);
        txtSearch.sendKeys(email);
        btnSearch.click();
    }

    public String verifySearchEmail() {
    String actualEmail = getEmail.getText();
    return actualEmail;
    }


}
