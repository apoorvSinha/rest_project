Feature: test the Jira project APIs
  Scenario: need to verify the project
    Given user is logged in
    When user is able to see the project details and the status code is "200"
    And user is able to create an issue


  Scenario: user is able to play around with existing issue
    Given user is logged in
    When user is able to see the project details and the status code is "200"
    And user is able to update the issue
