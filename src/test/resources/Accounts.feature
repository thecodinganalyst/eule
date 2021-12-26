Feature: Account
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

  Scenario: Account exists
    Given the following accounts already exist
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    When the account with id "cash" is requested
    Then the following accounts are returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |

  Scenario: Account doesn't exists
    When the account with id "credit card" is requested
    Then HttpStatus 404 is expected

  Scenario: Normal behavior
    When account list is requested
    Then the following accounts are returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |