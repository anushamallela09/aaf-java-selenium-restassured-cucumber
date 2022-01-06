package com.go.rest.stepdef;

import com.go.rest.utils.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class CreateUserStepDef {

    Services services;
    public Logger log = LogManager.getLogger(this.getClass());
    String response = null;

    @Given("Create JSON request using json template {string}")
    public void create_json_request_using_json_template_create_user_post(String templatename, DataTable dataTable) {
        HashMap<String, String> testData = this.services.convertDataTableValuesToList(dataTable);
        String request = this.services.createRestRequest(templatename,testData);
    }
    @Then("validate the status code {string} from json response")
    public void validate_the_status_code_from_json_response(String string) {

    }
    @Then("validate {string} from {string} JSON response - json path {string}")
    public void validate_from_json_response_json_path(String string, String string2, String string3) {

    }
    @Then("validate {string} from email JSON response - json path {string}")
    public void validate_from_email_json_response_json_path(String string, String string2) {

    }
}