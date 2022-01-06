package com.go.rest.stepdef;

import com.go.rest.utils.Config;
import com.go.rest.utils.Services;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import static io.restassured.RestAssured.given;

public class GetUserStepDef {

    Response response;
    Services services;
    String responseString = "";
    public Logger log = LogManager.getLogger(this.getClass());

    @Before
    public void before() {
        System.out.println("Before Scenario");
    }

    @After
    public void after() {
        System.out.println("After Scenario");
    }

    @When("I submit the JSON GET request for user id {string}")
    public void i_submit_the_json_get_request_with_endpoint_with_parameter(String userid) {

        String GorestBaseURL = Config.properties.getProperty("GorestBaseURL");
        String GetUserEndPoint = Config.properties.getProperty("GetUserEndPoint");

        String actualendpoint = GorestBaseURL + GetUserEndPoint + "/" +userid;
        services = new Services();
        response = given()
                .when()
                .get(actualendpoint);
    }

    @Then("validate the GET status code {string} from json response")
    public void validate_the_status_code_from_json_response(String statusCode) {
        ResponseBody body = response.getBody();
        responseString = body.asString();

        if(response.getStatusCode() != 200){
            Assert.assertEquals(response.getStatusCode(),statusCode, "status code not matched");
        }
    }

    @Then("Validate {string} from {string} node name in JSON response - json path {string}")
    public void validate_from_node_in_json_response_json_path(String expectedvalue, String nodename, String jsonpath) {
        String actualvalue = services.getValueFromJsonString(responseString,jsonpath);
       if(actualvalue.equals(expectedvalue)){
           Assert.assertTrue(true, nodename + " node name is matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
       } else {
           Assert.assertTrue(false, nodename + " node name not matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
       }
    }

}
