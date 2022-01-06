Feature: Verify the create user

  @PostSmokeTest @SmokeTest
  Scenario: Verify the create user functionality
    Given Create JSON request using json template "CreateUserPost.txt"
    |nodename |values|
    |name   |  raj91  |
    |gender |  Male |
    |status |  active|
    |email  |  raj4591@gmail.com |
    When I submit the JSON POST request
    Then validate the status code "201" from json response
    Then validate "raj91" from "name" JSON response - json path "$.data.name"
    Then validate "male" from "gender" JSON response - json path "$.data.gender"
    Then validate "active" from "status" JSON response - json path "$.data.status"
    Then validate "raj4591@gmail.com" from "email" JSON response - json path "$.data.email"

