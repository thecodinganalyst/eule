Feature: AccountController
  Scenario: Add account
    When the following accounts are added
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |
    Then the following accounts are returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2021-01-01 |