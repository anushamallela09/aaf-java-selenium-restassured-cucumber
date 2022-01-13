@Regression
Feature: Login

  @SmokeTest @UITest001 @UI
  Scenario: Successful Login with Valid Credentials
    Given User Launch Chrome browser
    When User opens URL
    And user enters Email as "admin@yourstore.com" and password as "admin"
    Then Page Title should be "Dashboard"
    When User click on Log out link
    Then User navigate to Login Page and Title should be "Your store. Login"
    And close browser

  @UITest002 @UI @SmokeTest
  Scenario Outline: Login Data Driven
    Given User Launch Chrome browser
    When User opens URL
    And user enters Email as "<email>" and password as "<password>"
    Then Page Title should be "Dashboard"
    When User click on Log out link
    Then User navigate to Login Page and Title should be "Your store. Login"
    And close browser

    Examples:
    |email| |password|
    |admin@yourstore.com| |admin|
    |admin@yourstore.com| |admin|


