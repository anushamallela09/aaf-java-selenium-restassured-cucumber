Feature: Verify the create user

  @PostSmokeTest @SmokeTest1
  Scenario: Verify the create user functionality
    Given Create JSON request using json template "CreateUserPost.txt"
    |nodename |values|
    |name   |  raj8  |
    |gender |  Male |
    |status |  active|
    |email  |  raj458@gmail.com |
    When I submit the JSON POST request
    Then validate the status code "201" from json response
    Then validate "raj8" from "name" JSON response - json path "$.data.name"
    Then validate "male" from "gender" JSON response - json path "$.data.gender"
    Then validate "active" from "status" JSON response - json path "$.data.status"
    Then validate "raj458@gmail.com" from "email" JSON response - json path "$.data.email"

