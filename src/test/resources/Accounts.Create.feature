Feature: Add Account
  Scenario: Normal behavior
    When the following accounts are added
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    Then the following accounts are returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |

  Scenario: Account already exists
    Given the following accounts already exist
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    When the following accounts are added
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    Then HttpStatus 409 is expected