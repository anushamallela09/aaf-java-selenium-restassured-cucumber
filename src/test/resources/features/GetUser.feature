Feature: Verify the given user information

  @GetSmokeTest @SmokeTest
  Scenario: Verify the given user details
    When I submit the JSON GET request for user id "1985"
    Then validate the GET status code "200" from json response
    Then Validate "vAllasani Peddanar119" from "name" node name in JSON response - json path "$.data.name"
    Then Validate "allasani.peddanar5@222ce.com" from "email" node name in JSON response - json path "data.email"
    Then Validate "male" from "gender" node name in JSON response - json path "data.gender"
    Then Validate "active" from "status" node name in JSON response - json path "data.status"
