package com.go.rest.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

   WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="Email")
    WebElement txtEmail;

    @FindBy(id="Password")
    WebElement txtPassword;

    @FindBy(xpath="//button[@type='submit']")
    WebElement btnLogin;


    public void setLogin(String Email,String Password){
        txtEmail.clear();
        txtEmail.sendKeys(Email);
        txtPassword.clear();
        txtPassword.sendKeys(Password);
        btnLogin.click();
    }

    public String verifyLogout() {
       String actualtitle = driver.getTitle();
       return actualtitle;
    }


}
