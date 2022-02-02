Feature: Journal Entry Management
  Scenario: Adding a journal entry
    Given the following "Account" exists
      | id    | name  | group     | currency | openBal | openDate   |
      | cash  | Cash  | Assets    | SGD      | 100.00  | 2021-01-01 |
      | food  | Food  | Expenses  | SGD      |         |            |
    When "/journalEntry" is called with "POST" with the following data
      | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 2021-01-10 | Lunch       | SGD      | 5.00   | food           | cash            | 2021-01-10 |            |
    Then HttpStatus 201 is expected
    And the following data is returned
      | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 2021-01-10 | Lunch       | SGD      | 5.00   | food           | cash              | 2021-01-10 |            |


  Scenario: Adding a journal entry with invalid data
    When "/journalEntry" is called with "POST" with the following data
      | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 2021-01-10 | Lunch       | Empty    | 5.00   | food           | cash            | 2021-01-10 |            |
    Then HttpStatus 400 is expected

  Scenario: Getting an existing journal entry
    When "/journalEntry" is called with "GET" with the params "/1"
    Then the following data is returned
      | id | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 1  | 2021-01-10 | Lunch       | SGD      | 5.00   | food           | cash            | 2021-01-10 |            |

  Scenario: Getting a non-existent journal entry
    When "/journalEntry" is called with "GET" with the params "/99"
    Then HttpStatus 404 is expected

  Scenario: Update journal entry
    When "/journalEntry" is called with "PUT" with the following data
      | id | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 1  | 2021-01-10 | Lunch       | SGD      | 7.00   | food           | cash            | 2021-01-10 |            |
    Then HttpStatus 200 is expected
    And the following data is returned
      | id | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 1  | 2021-01-10 | Lunch       | SGD      | 7.00   | food           | cash            | 2021-01-10 |            |

  Scenario: Update non existent journal entry
    When "/journalEntry" is called with "PUT" with the following data
      | id  | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 99  | 2021-01-10 | Lunch       | SGD      | 7.00   | food           | cash            | 2021-01-10 |            |
    Then HttpStatus 404 is expected

  Scenario: Update journal entry with invalid data
    When "/journalEntry" is called with "PUT" with the following data
      | id  | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 1   | 2021-01-10 | Lunch       | Empty    | 7.00   | food           | cash            | 2021-01-10 |            |
    Then HttpStatus 400 is expected

  Scenario: List journal entries
    When "/journalEntry" is called with "GET"
    Then the following data list is returned
      | id | txDate     | description | currency | amount | debitAccountId | creditAccountId | postDate   | recurrence |
      | 1  | 2021-01-10 | Lunch       | SGD      | 7.00   | food           | cash            | 2021-01-10 |            |