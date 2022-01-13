package com.go.rest.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddNewCustomerPage {
    WebDriver driver;

    public AddNewCustomerPage(WebDriver driver){
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy (xpath = "//input[@id='Email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='Password']")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@id='FirstName']")
    WebElement txtFirstName;

    @FindBy(xpath = "//input[@id='LastName']")
    WebElement txtLastName;

    @FindBy(xpath = "//input[@id='Gender_Female']")
    WebElement radioGender;

    @FindBy(xpath = "//button[@name='save']")
    WebElement btnSave;

    @FindBy(xpath = "//h1[@class='float-left']")
    WebElement AddCustomerPage;

    public String verifyAddCustomerPage() {
        String actualpage = AddCustomerPage.getText();
        return actualpage;
    }

    public void setCustomer(String email,String password,String FirstName,String LastName) {
        txtEmail.sendKeys(email);
        txtPassword.sendKeys(password);
        txtFirstName.sendKeys(FirstName);
        txtLastName.sendKeys(LastName);
        radioGender.click();
        btnSave.click();
    }
}
