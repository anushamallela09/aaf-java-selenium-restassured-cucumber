package com.go.rest.stepdef;

import com.go.rest.utils.Config;
import com.go.rest.utils.Services;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GetUserStepDef {

    Scenario scenario;
    Response response;
    Services services;
    String responseString = "";
    public Logger log = LogManager.getLogger(this.getClass());

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        System.out.println("Before Scenario");
    }

    @After
    public void after(Scenario scenario) {
        System.out.println("After Scenario");
    }

    @When("I submit the JSON GET request for user id {string}")
    public void i_submit_the_json_get_request_with_endpoint_with_parameter(String userid) {

        String GorestBaseURL = Config.properties.getProperty("GorestBaseURL");
        String GetUserEndPoint = Config.properties.getProperty("GetUserEndPoint");

        String GetUserHeaders = Config.properties.getProperty("GetUserHeaders");
        String[] headers = GetUserHeaders.split(";");
        String[] contentType = headers[0].split(":");

        String actualendpoint = GorestBaseURL + GetUserEndPoint + "/" + userid;
        log.info("Sending Get User Request '" + actualendpoint + "'");
        log.info("Request Headers '" + GetUserHeaders + "'");
        //scenario.log("Sending Get User Request '" + actualendpoint + "'");
        //scenario.log("Request Headers '" + GetUserHeaders + "'");

        response = given()
                .when()
                .header(contentType[0], contentType[1])
                .get(actualendpoint);
    }

    @When("I submit the JSON GET request for Employee user id {string}")
    public void i_submit_the_json_get_request_for_employee_user_id(String userid) {
        String GorestBaseURL = Config.properties.getProperty("EmployeeBaseURL");
        String GetUserEndPoint = Config.properties.getProperty("GetEmployeeEndpoint");

        String GetUserHeaders = Config.properties.getProperty("GetEmployeeHeaders");
        String[] headers = GetUserHeaders.split(";");
        String[] contentType = headers[0].split(":");

        String actualendpoint = GorestBaseURL + GetUserEndPoint + "/" + userid;
        log.info("Sending Get Employee Request '" + actualendpoint + "'");
        log.info("Request Headers '" + GetUserHeaders + "'");
        //scenario.log("Sending Get Employee Request '" + actualendpoint + "'");
        //scenario.log("Request Headers '" + GetUserHeaders + "'");
        response = given()
                .when()
                .header(contentType[0], contentType[1])
                .get(actualendpoint);
    }


    @Then("validate the GET status code {string} from json response")
    public void validate_the_status_code_from_json_response(String expectedStatusCode) {
        responseString = response.getBody().asString();
        log.info("response '" + responseString + "'");
        //scenario.log("response '" + responseString + "'");

        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != Integer.parseInt(expectedStatusCode)) {
            log.info("Validation : " + "Status Code expected value: '" + 200 + "' and actual value: '" + response.getStatusCode() + "'");
            //scenario.log("Validation : " + "Status Code expected value: '" + 200 + "' and actual value: '" + response.getStatusCode() + "'");
            Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "status code not matched");
        }else{
            log.info("Validation : " + "Status Code expected value: '" + 200 + "' and actual value: '" + response.getStatusCode() + "'");
            //scenario.log("Validation : " + "Status Code expected value: '" + 200 + "' and actual value: '" + response.getStatusCode() + "'");
        }

    }

    @Then("Validate {string} from {string} node name in JSON response - json path {string}")
    public void validate_from_node_in_json_response_json_path(String expectedvalue, String nodename, String jsonpath) {
        services = new Services();
        responseString = response.getBody().asString();

        String actualvalue = services.getValueFromJsonString(responseString, jsonpath);
        log.info("Validation : " + "'" + nodename + "' node name. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
        //scenario.log("Validation : " + "'" + nodename + "' node name. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
        if (actualvalue.equals(expectedvalue)) {
            Assert.assertTrue(true, nodename + " node name is matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
        } else {
            Assert.assertTrue(false, nodename + " node name not matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
        }

    }

}