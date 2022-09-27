Feature: deposit feature
  Scenario: A client make a deposit to his account
    Given the following operation
      | clientId         | amount |
      | ayman.aboueloula | 1000   |
    When the user make a deposit
    Then we return the following history
      | amount | operation| operationDate
      | 1000   | DEPOSIT  | 2022-01-02T00:00:00.000Z