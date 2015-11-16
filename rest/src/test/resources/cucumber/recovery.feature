Feature: Password Recovery
  As a user
  I want to recover my password
  So that I can use the website

  Scenario: Recover my password
    Given I already have an account
    When I try to recover my password
    Then I should be redirected to a recovery page