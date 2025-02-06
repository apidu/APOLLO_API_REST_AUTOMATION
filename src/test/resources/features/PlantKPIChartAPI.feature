Feature: Plant KPI Chart API Testing
#PlantKPIChartAPISteps   //Plant level Roc Live Trend Widget
  Scenario: Validate Plant KPI Chart API Response
    Given I authenticate to the Plant KPI Chart API
    When I send a POST request to the Plant KPI Chart API
    And the response should contain plantId 2
    And the response should contain kpiCode "PLANT_ACTIVE_POWER"
    And the response should contain kpiCode "PLANT_POA_IRRADIATION_POWER"