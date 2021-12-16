Feature: List Account
  Scenario: Normal behavior
    When account list is requested
    Then HttpStatus 200 is expected

