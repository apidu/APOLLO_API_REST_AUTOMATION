Feature: API Testing
#LiveTrendStep
  Scenario: Test Live Trend API
    Given I authenticate to the Live Trend API
    When I send a request to the Live Trend API
    Then I should receive a successful response
#PortfolioKPISelectionSteps
  Scenario: Validate Portfolio KPI Selection API
    Given I authenticate to the Portfolio KPI Selection API
    When I send a request to the Portfolio KPI Selection endpoint
    Then I should receive a status code of 200


  Scenario: Validate Plant Active Power KPI Calculation from HT Panel OMP
    Given I authenticate to the Plant Active Power OMP API
    When I send a request to the Plant Active Power OMP API
    Then the response should contain data for multiple plants
    And the response should perform a sum operation if there are multiple HT panels for a plant, showing the sum of all HT panel active power values
    And the response time should be within the limit of 1000 ms
