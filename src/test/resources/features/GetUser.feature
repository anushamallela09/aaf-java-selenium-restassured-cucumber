@Regression
Feature: Verify the given user information


 @APITest001
  Scenario: Verify the given user details
    When I submit the JSON GET request for user id "1985"
    Then validate the GET status code "200" from json response
    Then Validate "Sundar" from "name" node name in JSON response - json path "data.name"
    Then Validate "pluiral142@gmail.org" from "email" node name in JSON response - json path "data.email"
    Then Validate "male" from "gender" node name in JSON response - json path "data.gender"
    Then Validate "active" from "status" node name in JSON response - json path "data.status"

 @APITest002
  Scenario Outline: Verify the given user details for multiple users
    When I submit the JSON GET request for user id "<userid>"
    Then validate the GET status code "200" from json response
    Then Validate "<name>" from "name" node name in JSON response - json path "$.data.name"
    Then Validate "<email>" from "email" node name in JSON response - json path "$.data.email"
    Then Validate "<gender>" from "gender" node name in JSON response - json path "$.data.gender"
    Then Validate "<status>" from "status" node name in JSON response - json path "$.data.status"
    Examples:
      | userid | name     | email                    | gender | status |
      | 2235   | John Doe | meera.joe+13805@test.com | male   | active |
      | 2263   | John Doe | meera.joe+86174@test.com | male   | active |
      | 2253   | John Doe | meera.joe+43358@test.com | male   | active |
