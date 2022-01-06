package com.go.rest.stepdef;

import com.go.rest.utils.Config;
import com.go.rest.utils.Services;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.ResourceBundle;

public class GetUserStepDef {
    Services services;
    public Logger log = LogManager.getLogger(this.getClass());
    String response = null;

    @When("I submit the JSON GET request for user id {string}")
    public void i_submit_the_json_get_request_with_endpoint_with_parameter(String userid) {
        String endpoint = Config.properties.getProperty("GetUserEndPoint");
        String actualendpoint = endpoint+"/"+userid;
        services = new Services();
        response = services.getResponseFromGetMethod(actualendpoint);

    }

    @Then("Validate {string} from {string} node name in JSON response - json path {string}")
    public void validate_from_node_in_json_response_json_path(String expectedvalue, String nodename, String jsonpath) {
        String actualvalue = services.getValueFromJsonString(response,jsonpath);
       if(actualvalue.equals(expectedvalue)){
           Assert.assertTrue(true, nodename + " node name is matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
       } else {
           Assert.assertTrue(false, nodename + " node name not matched. expected value: '" + expectedvalue + "' and actual value: '" + actualvalue + "'");
       }
    }

}
