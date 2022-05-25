Feature: Account Groups
  Scenario: Getting all account groups
    When "/accountGroups" is called with "GET"
    Then the following list is returned
      | Assets      |
      | Liabilities |
      | Revenue     |
      | Expenses    |
      | Gains       |
      | Loss        |

  Scenario: Getting debit account groups
    When "/accountGroups" is called with "GET" with the following queryParams
      | entryType | debit |
    Then the following list is returned
      | Assets      |
      | Expenses    |
      | Loss        |

  Scenario: Getting credit account groups
    When "/accountGroups" is called with "GET" with the following queryParams
      | entryType | credit |
    Then the following list is returned
      | Liabilities |
      | Revenue     |
      | Gains       |