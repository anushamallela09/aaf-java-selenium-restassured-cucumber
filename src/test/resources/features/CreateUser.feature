Feature: Verify the create user

  Scenario: Verify the create user functionality
    Given Create JSON request using json template "CreateUserPost"
    |nodename| |values|
    |name   | | raj  |
    |gender | | Male |
    |status | | active|
    |email  | | raj45@gmail.com |
    When I submit the JSON "POST" request
    Then validate the status code "201" from json response
    Then validate "raj" from "name" JSON response - json path "$.data.name"
    Then validate "Male" from "gender" JSON response - json path "data.gender"
    Then validate "active" from "status" JSON response - json path "data.status"
    Then validate "raj45@gmail.com" from email JSON response - json path "data.email"

