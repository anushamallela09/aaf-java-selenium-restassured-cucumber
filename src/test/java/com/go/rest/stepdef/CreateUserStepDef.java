package com.go.rest.stepdef;

import com.go.rest.utils.Config;
import com.go.rest.utils.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class CreateUserStepDef {

    Services services;
    String payLoad = null;
    Response response = null;
    String responseString = "";
    public Logger log = LogManager.getLogger(this.getClass());

    @Before
    public void before(Scenario scenario) {
        System.out.println("Before Scenario");
    }

    @After
    public void after(Scenario scenario) {
        System.out.println("After Scenario");
    }

    @Given("Create JSON request using json template {string}")
    public void create_json_request_using_json_template_create_user_post(String templatename, DataTable dataTable) {
        services = new Services();
        HashMap<String, String> testData = this.services.convertDataTableValuesToList(dataTable);
        payLoad = this.services.createPayload(templatename,testData);
    }

    @Given("I submit the JSON POST request")
    public void submit_theJason_Post_Request() {

        String GorestBaseURL = Config.properties.getProperty("GorestBaseURL");
        String GetUserEndPoint = Config.properties.getProperty("GetUserEndPoint");

        String CreateUserHeaders = Config.properties.getProperty("CreateUserHeaders");
        String[] headers = CreateUserHeaders.split(";");
        String[] contentType = headers[0].split(":");
        String[] auth = headers[1].split(":");

        RestAssured.baseURI = GorestBaseURL;
        RestAssured.basePath = GetUserEndPoint;
        log.info("Sending Get User Request '" + GorestBaseURL + "/" + GetUserEndPoint +"'");
        log.info("Request Headers '" + CreateUserHeaders + "'");
        response = given()
                .header(contentType[0],contentType[1])
                .header(auth[0],auth[1])
                .body(payLoad)
                .when()
                .post();
    }

    @Then("validate the status code {string} from json response")
    public void validate_the_status_code_from_json_response(String statusCode) {

        ResponseBody body = response.getBody();
        responseString = body.asString();
        if (response.getStatusCode() != 201) {
            log.info("Validation : " + "Status Code expected value: '" + 201 + "' and actual value: '" + response.getStatusCode() + "'");
            Assert.assertEquals(response.getStatusCode(), statusCode, "status code not matched");
        }else{
            log.info("Validation : " + "Status Code expected value: '" + 201 + "' and actual value: '" + response.getStatusCode() + "'");
        }

    }
    @Then("validate {string} from {string} JSON response - json path {string}")
    public void validate_from_json_response_json_path(String expectedvalue, String nodeName, String jsonPath) {

        String actualvalue = services.getValueFromJsonString(responseString, jsonPath);
        log.info("Validation : " + "'" + nodeName + "' node name. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
        if (actualvalue.equals(expectedvalue)) {
            Assert.assertTrue(true, nodeName + " node name is matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
        } else {
            Assert.assertTrue(false, nodeName + " node name not matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
        }

    }

}