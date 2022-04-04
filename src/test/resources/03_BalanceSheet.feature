Feature: Balance Sheet Generation
  Scenario: Building a simple balance sheet
    Given the following "Account" exists
      | id     | name        | group       | currency | openBal | openDate   |
      | cash   | Cash        | Assets      | SGD      | 100.00  | 2022-01-01 |
      | food   | Food        | Expenses    | SGD      |         |            |
      | credit | Credit Card | Liabilities | SGD      | 0.00    | 2022-01-01 |
    And the following "JournalEntry" exists
      | txDate     | description | currency | amount  | debitAccountId | creditAccountId | postDate   | recurrence |
      | 2022-01-10 | Breakfast   | SGD      | 5.00    | food           | cash            | 2022-01-10 |            |
      | 2022-01-10 | Lunch       | SGD      | 7.00    | food           | cash            | 2022-01-10 |            |
      | 2022-01-10 | Dinner      | SGD      | 15.00   | food           | credit          | 2022-01-10 |            |
    When "/balanceSheet" is called with "GET" with the params "/SGD/2022-01-10"
    Then HttpStatus 200 is expected
    And the following data is returned
      | balanceDate | currency |
      | 2022-01-10  | SGD      |
    And the data has property "assets" with the following data values
      | Cash  |
      | 88.00 |
    And the data has property "liabilities" with the following data values
      | Credit Card |
      | 15.00       |
