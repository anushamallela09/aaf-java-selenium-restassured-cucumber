package com.go.rest.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {
    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[normalize-space()='Dashboard']")
    WebElement txtDashboard;

    @FindBy(xpath = "//div[@id='navbarText']//li[3]//a")
    WebElement lnkLogout;

    @FindBy(xpath = "//a[@href='#']//p[contains(text(),'Customers')]")
    WebElement lnkCustomers;

    @FindBy(xpath = "//a[@href='/Admin/Customer/List']//p[contains(text(),'Customers')]")
    WebElement lnksubCustomers;

    public String getDashboardtext() {
        String actualText = txtDashboard.getText();
        return actualText;
    }

    public void clickLogout() {
        lnkLogout.click();
    }

    public void clickCustomers() throws InterruptedException {
        lnkCustomers.click();
        Thread.sleep(3000);
        lnksubCustomers.click();

    }
}
