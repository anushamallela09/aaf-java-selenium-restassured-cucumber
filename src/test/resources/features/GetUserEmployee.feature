@Regression
Feature: Verify the given user information


  @SmokeTest @APITest003
  Scenario: Verify the given Employee user details
    When I submit the JSON GET request for Employee user id "1"
    Then validate the GET status code "200" from json response
    Then Validate "George" from "first_name" node name in JSON response - json path "data.first_name"
    Then Validate "george.bluth@reqres.in" from "email" node name in JSON response - json path "data.email"
    Then Validate "Bluth" from "last_name" node name in JSON response - json path "data.last_name"


 @APITest004
  Scenario Outline: Verify the given user details for multiple users
    When I submit the JSON GET request for Employee user id "<userid>"
    Then validate the GET status code "200" from json response
    Then Validate "<firstname>" from "first_name" node name in JSON response - json path "$.data.first_name"
    Then Validate "<email>" from "email" node name in JSON response - json path "$.data.email"
    Then Validate "<lastname>" from "last_name" node name in JSON response - json path "$.data.last_name"
    Examples:
      | userid | firstname | email                  | lastname |
      | 2      | Janet     | janet.weaver@reqres.in | Weaver   |
      | 3      | Emma      | emma.wong@reqres.in    | Wong     |
      | 4      | Eve       | eve.holt@reqres.in     | Holt     |
