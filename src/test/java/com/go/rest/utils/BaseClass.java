package com.go.rest.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.go.rest.pageObjects.AddNewCustomerPage;
import com.go.rest.pageObjects.CustomerPage;
import com.go.rest.pageObjects.DashboardPage;
import com.go.rest.pageObjects.LoginPage;

public class BaseClass {
    public WebDriver driver;
    public LoginPage loginPage;
    public DashboardPage dashboardPage;
    public CustomerPage customerPage;
    public AddNewCustomerPage addNewCustomerPage;
    public Logger logger = LogManager.getLogger(this.getClass());

    public static String randomestring() {
        String generatedString1 = RandomStringUtils.randomAlphabetic(5);
        return (generatedString1);
    }
}
