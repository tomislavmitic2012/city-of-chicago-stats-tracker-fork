Feature: Log In
  As a user
  I want to Log In
  So that I can use the website

  Scenario: Log in with username and password
    Given I have an account
    When I enter a username and password
    Then I should be redirected to the dashboard

