Feature: Get Account
  Scenario: Account exists
    Given the following accounts already exist
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    When the account with id "cash" is requested
    Then the following accounts are returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
