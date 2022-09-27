Feature: withdrawal feature
  Scenario: A client make a withdrawal from his account
    Given the following withdrawal operation
      | clientId         | amount |
      | ayman.aboueloula | 1000   |
    When the user make a withdrawal
    Then we return the following withdrawal operation
      | amount  | operation   | operationDate
      | -1000   | WITHDRAWAL  | 2022-01-02T00:00:00.000Z