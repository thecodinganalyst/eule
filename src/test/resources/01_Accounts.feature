Feature: Account Management
  Scenario: Adding an asset account
    When "/accounts" is called with "POST" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2022-01-01 |
    Then HttpStatus 201 is expected
    And the following data is returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2022-01-01 |

  Scenario: Adding an expense account
    When "/accounts" is called with "POST" with the following data
      | id    | name  | group    | currency | openBal | openDate   |
      | food  | Food  | Expenses |          |         |            |
    Then HttpStatus 201 is expected
    And the following data is returned
      | id    | name  | group    | currency | openBal | openDate   |
      | food  | Food  | Expenses |          |         |            |

  Scenario: Adding an account which already exists
    When "/accounts" is called with "POST" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2022-01-01 |
    Then HttpStatus 409 is expected

  Scenario: Adding an account with invalid data
    When "/accounts" is called with "POST" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | bank  | Bank  | Empty   | SGD      | 100.00  | 2022-01-01 |
    Then HttpStatus 400 is expected

  Scenario: Getting an existing account
    When "/accounts" is called with "GET" with the params "/cash"
    Then the following data is returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2022-01-01 |

  Scenario: Getting a non-existent account
    When "/accounts" is called with "GET" with the params "/CREDIT-CARD"
    Then HttpStatus 404 is expected

  Scenario: Update account
    When "/accounts" is called with "PUT" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 200.00  | 2022-01-01 |
    Then HttpStatus 200 is expected
    And the following data is returned
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 200.00  | 2022-01-01 |

  Scenario: Update non existent account
    When "/accounts" is called with "PUT" with the following data
      | id      | name    | group   | currency | openBal | openDate   |
      | credit  | Credit  | Assets  | SGD      | 200.00  | 2022-01-01 |
    Then HttpStatus 404 is expected

  Scenario: Update account with invalid data
    When "/accounts" is called with "PUT" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Empty   | SGD      | 200.00  | 2022-01-01 |
    Then HttpStatus 400 is expected

  Scenario: List accounts
    When "/accounts" is called with "GET"
    Then the following data list is returned
      | id    | name  | group    | currency | openBal | openDate   |
      | cash  | Cash  | Assets   | SGD      | 200.00  | 2022-01-01 |
      | food  | Food  | Expenses |          |         |            |

  Scenario: Deleting an existing account
    When "/accounts" is called with "DELETE" with the params "/cash"
    Then HttpStatus 200 is expected

  Scenario: List accounts after deleting
    When "/accounts" is called with "GET"
    Then the following data list is returned
      | id    | name  | group    | currency | openBal | openDate   |
      | food  | Food  | Expenses |          |         |            |

  Scenario: Deleting a non-existent account
    When "/accounts" is called with "DELETE" with the params "/credit"
    Then HttpStatus 404 is expected

  Scenario: Setting up end scenario for accounts
    When "/accounts" is called with "POST" with the following data
      | id    | name  | group   | currency | openBal | openDate   |
      | cash  | Cash  | Assets  | SGD      | 100.00  | 2022-01-01 |
    Then HttpStatus 201 is expected
    When "/accounts" is called with "POST" with the following data
      | id     | name        | group       | currency | openBal | openDate   |
      | credit | Credit Card | Liabilities | SGD      | 0.00    | 2022-01-01 |
    Then HttpStatus 201 is expected
    When "/accounts" is called with "GET"
    Then the following data list is returned
      | id     | name        | group       | currency | openBal | openDate   |
      | food   | Food        | Expenses    |          |         |            |
      | cash   | Cash        | Assets      | SGD      | 100.00  | 2022-01-01 |
      | credit | Credit Card | Liabilities | SGD      | 0.00    | 2022-01-01 |