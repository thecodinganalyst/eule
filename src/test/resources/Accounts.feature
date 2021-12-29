Feature: Account Management
  Scenario: Adding an account
    When "/accounts" is called with "POST" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    Then HttpStatus 201 is expected
    And the following data is returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |

  Scenario: Adding an account which already exists
    When "/accounts" is called with "POST" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    Then HttpStatus 409 is expected

  Scenario: Getting an existing account
    When "/accounts" is called with "GET" with the params "/cash"
    Then the following data is returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |

  Scenario: Getting a non-existent account
    When "/accounts" is called with "GET" with the params "/CREDIT-CARD"
    Then HttpStatus 404 is expected

  Scenario: List accounts
    When "/accounts" is called with "GET"
    Then the following data list is returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |