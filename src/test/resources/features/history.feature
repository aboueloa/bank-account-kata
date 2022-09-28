Feature: history feature
  Scenario: A client want to see his history of operation
    When a client ayman.aboueloula want to see his history
    Then we return the following history operation
      | balance  | operations
      | 0        | [{"amount": 1000, "operation": "DEPOSIT"}]