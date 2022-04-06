Feature: Income Statement Generation
  Scenario: Building a simple income statement
    When "/incomeStatement" is called with "GET" with the params "/SGD/2022-01-01/2022-01-10"
    Then HttpStatus 200 is expected
    And the following data is returned
      | fromDate   | toDate     | currency |
      | 2022-01-01 | 2022-01-10 | SGD      |
    And the data has property "expenses" with the following data values
      | Food  |
      | 27.00 |
