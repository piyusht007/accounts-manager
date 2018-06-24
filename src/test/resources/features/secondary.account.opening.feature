@IntegrationTest

Feature: Submit secondary current account creation request and fetch the details thereafter.

  Scenario: Clean up the system
    Then delete all customers, accounts and transactions

  Scenario: Create accounts for customers
    Given the following customers needs to open accounts
      | firstName | lastName |
      | Sam       | Billings |
      | Peter     | Jacobson |

  Scenario Outline:
    When i submit the secondary current account creation request for: <customerName> with initial credit amount: <amount>
    Then <customerName> should get a new current account with opening balance <balance>

    Examples:
      | customerName | amount | balance |
      | Sam          | 20000  | 20000   |
      | Peter        | 0      | 0       |

  Scenario: Clean up the system
    Then delete all customers, accounts and transactions

