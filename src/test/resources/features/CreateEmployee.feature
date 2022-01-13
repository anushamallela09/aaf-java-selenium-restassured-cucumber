@Regression
Feature: Verify the create Employee

  @APITest006
  Scenario Outline: Verify the create employee functionality
    Given Create JSON request using json template "CreateEmployeePost.txt" with "<first_name>" "<last_name>" "<avatar>" "<email>"
    When I submit the JSON Employee Creation POST request
    Then validate the status code "200" from json response
    Then validate "<first_name>" from "firstname" JSON response - json path "$.data.first_name"
    Then validate "<last_name>" from "lastname" JSON response - json path "$.data.last_name"
    Then validate "<avatar>" from "image" JSON response - json path "$.data.avatar"
    Then validate "<email>" from "email" JSON response - json path "$.data.email"

    Examples:
      | first_name | last_name | avatar                                  | email                 |
      | raj92      | reddy     | https://reqres.in/img/faces/7-image.jpg | raj92.reddy@gmail.com |
    #  | usha       | reddy     | https://reqres.in/img/faces/8-image.jpg | usha.reddy@gmail.com  |
     # | test       | reddy     | https://reqres.in/img/faces/9-image.jpg | test.reddy@gmail.com  |
