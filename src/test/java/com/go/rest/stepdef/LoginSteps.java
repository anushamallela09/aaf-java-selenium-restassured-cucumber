package com.go.rest.stepdef;

import com.go.rest.utils.BaseClass;
import com.go.rest.utils.Config;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import com.go.rest.pageObjects.AddNewCustomerPage;
import com.go.rest.pageObjects.CustomerPage;
import com.go.rest.pageObjects.DashboardPage;
import com.go.rest.pageObjects.LoginPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LoginSteps extends BaseClass {

    public WebDriver driver;
    public LoginPage loginPage;
    public DashboardPage dashboardPage;
    public CustomerPage customerPage;
    public AddNewCustomerPage addNewCustomerPage;
    String inputEmail = "abc@gmail.com";
    public ResourceBundle rb;

    @Before
    public void setup() {


    }

    @After
    public void after(){

    }
    @Given("User Launch Chrome browser")
    public void user_launch_chrome_browser() {
        String br = Config.properties.getProperty("browser");
        String ExecutionMode = Config.properties.getProperty("ExecutionMode");

        if (ExecutionMode.contains("local")) {
            if (br.equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                logger.info("Launched Chrome Browser");
            } else if (br.equals("edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                logger.info("Launched Edge Browser");
            } else if (br.equals("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                logger.info("Launched firefox Browser");
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } else if (ExecutionMode.contains("remote")) {
            DesiredCapabilities cap = null;
            if (br.equals("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("no-sandbox");
                options.addArguments("start-maximized");
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
                cap = DesiredCapabilities.chrome();
                cap.setCapability(ChromeOptions.CAPABILITY, options);
                cap.setBrowserName("chrome");
                // if (RemoteType.contains("VM")) {
                //     cap.setPlatform(Platform.WINDOWS);
                //} else if (RemoteType.contains("AWS")) {
                //    cap.setPlatform(Platform.LINUX);
                //}

                try {
                    driver = new RemoteWebDriver(new URL(Config.properties.getProperty("RemoteExecutionURL")), cap);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }
        logger.info("launch browser");
        loginPage = new LoginPage(driver);
    }

    @When("User opens URL")
    public void user_opens_url() {
       String Environment = Config.properties.getProperty("Environment");
       String url=null;

       if(Environment.equals("QA")) {
            url = Config.properties.getProperty("QA_UI");
       } else if(Environment.equals("Int")) {
            url = Config.properties.getProperty("Int_UI");
       }
        driver.get(url);
        logger.info("openurl");
        driver.manage().window().maximize();
    }

    @When("user enters Email as {string} and password as {string}")
    public void user_enters_email_as_and_password_as(String email, String password) {
        loginPage.setLogin(email, password);
    }

    @Then("Page Title should be {string}")
    public void page_title_should_be(String expectedText) {
        dashboardPage = new DashboardPage(driver);
        String actualText = dashboardPage.getDashboardtext();
        if (actualText.equals(expectedText)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }

    }

    @When("User click on Log out link")
    public void user_click_on_log_out_link() {
        dashboardPage.clickLogout();
    }

    @Then("User navigate to Login Page and Title should be {string}")
    public void user_navigate_to_login_page_and_title_should_be(String expectedTitle) {
        String actualTitle = loginPage.verifyLogout();
        if (actualTitle.equals(expectedTitle)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

    @Then("close browser")
    public void close_browser() {
        driver.quit();
    }

    //Add customer
    @When("User click on Customers main Menu and click on Customers sub menu")
    public void user_click_on_customers_main_menu_and_click_on_customers_sub_menu() throws InterruptedException {
        dashboardPage.clickCustomers();
        customerPage = new CustomerPage(driver);
        customerPage.clickAddNew();
    }

    @Then("click on Add new Customer Page and User can view Add customer page")
    public void click_on_add_new_customer_page_and_user_can_view_add_customer_page() {
        addNewCustomerPage = new AddNewCustomerPage(driver);
        addNewCustomerPage.setCustomer((LoginSteps.randomestring() + "@gmail.com"), "India@123", "Usha",
                "Reddy");
    }

    @Then("Verify Success Message {string}")
    public void verify_Success_Message(String expectedSuccessMessage) {
        String actualSuccessMessage = customerPage.verifySucessMessage();
        if (actualSuccessMessage.contains(expectedSuccessMessage)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

    //Search Customer
    @When("User click on Customers main Menu and click on Customers sub menu in Dashboard Page")
    public void clickCustomers() throws InterruptedException {
        dashboardPage.clickCustomers();
    }

    @When("Enter Customer Mail and click on search button")
    public void enter_customer_mail_and_click_on_search_button() throws InterruptedException {
        customerPage = new CustomerPage(driver);
        customerPage.setSearch(inputEmail);
    }

    @Then("User should found email in Search table")
    public void user_should_found_email_in_search_table() {
        String actualSearchEmail = customerPage.verifySearchEmail();
        if (actualSearchEmail.equals(inputEmail)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertFalse(false);
        }
    }
}
