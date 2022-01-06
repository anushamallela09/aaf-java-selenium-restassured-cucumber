Feature: Verify the given user information
@SmokeTest
  Scenario: Verify the given user details
    When I submit the JSON GET request for user id "2505"
    Then Validate "gomorra Ega" from "name" node name in JSON response - json path "$.data.name"
    Then Validate "gomorra@gmail.com" from "email" node name in JSON response - json path "data.email"
    Then Validate "female" from "gender" node name in JSON response - json path "data.gender"
    Then Validate "inactive" from "status" node name in JSON response - json path "data.status"
