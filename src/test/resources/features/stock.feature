Feature: Stock management

  Scenario: Create a new stock
    Given the stock API is available
    When I create a stock with symbol "AAPL", company name "Apple Inc.", sector "Technology", price 195.40 and currency "USD"
    Then the stock should be created successfully
    And the response should contain symbol "AAPL"

  Scenario: Get all stocks
    Given the stock API is available
    When I request all stocks
    Then the stock list response should be successful

  Scenario: Get non-existing stock
    Given the stock API is available
    When I request stock with id 999
    Then the response status should be 404