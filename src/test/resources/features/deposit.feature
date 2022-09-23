Feature: deposit feature
  Scenario: A client make a deposit to his account
    Given the following transaction
      | clientId         | amount |
      | ayman.aboueloula | 1000   |
    When the user make a deposit
    Then we return the following history
      | clientId         | amount | operation| operationDate              | balance |
      | ayman.aboueloula | 1000   | DEPOSIT  | 2022-01-02T00:00:00.000Z   | 2000    |