package stepdefination;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import report.ExtentReportManager;
import utils.ConfigManager;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class OMP_PLANT_ACTIVEPOWER {

    private String baseURI= ConfigManager.getProperty("url");
    private String accessToken;
    private Response apiResponse;
    ExtentTest test;

    @Given("I authenticate to the Plant Active Power OMP API")
    public void i_authenticate_to_the_plant_active_power_omp_api() {
        ExtentReportManager.initReports();

        // Create a new test in the report
        test = ExtentReportManager.createTest("Portfolio KPI Selection - Authentication");

        RestAssured.baseURI = baseURI;
        RestAssured.useRelaxedHTTPSValidation();

        String username = ConfigManager.getProperty("username");
        String password = ConfigManager.getProperty("password");

        String authRequestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"rememberMe\":true}";

        Response authResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/auth/login");

        assertEquals(authResponse.getStatusCode(), 200, "Authentication failed");
        accessToken = authResponse.jsonPath().getString("access_token");
        assertNotNull(accessToken, "Access token not found");

        System.out.println("Authentication successful, access token obtained.");
    }

    @When("I send a request to the Plant Active Power OMP API")
    public void i_send_a_request_to_the_plant_active_power_omp_api() {

        apiResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)

                .body("{\n" +
                        "    \"orgId\": 1,\n" +
                        "    \"kpiCode\": \"PLANT_ACTIVE_POWER\",\n" +
                        "    \"fromDate\": \"2024-10-23\",\n" +
                        "    \"fromTime\": \"00:00\",\n" +
                        "    \"toDate\": \"2024-10-23\",\n" +
                        "    \"toTime\": \"23:59\",\n" +
                        "    \"intraDayOperation\": null,\n" +
                        "    \"interDayOperation\": null,\n" +
                        "    \"groupBy\": \"MINUTE\"\n" +
                        "}")
                .when()
                .post("/apollokpimgmt/api/portfolio-kpi-provider/readings");

        int statusCode = apiResponse.getStatusCode();
        assertEquals(statusCode, 200, "API request failed with incorrect status code");
        System.out.println("API request successful with status code: " + statusCode);
    }

    @Then("the response should contain data for multiple plants")
    public void the_response_should_contain_data_for_multiple_plants() {
        String responseBody = apiResponse.asString();
        System.out.println("Response Body: " + responseBody);

        // Validate that the response contains data for multiple plants
        assertTrue(responseBody.contains("plantId"), "Response does not contain data for multiple plants");
    }

    @Then("the response should perform a sum operation if there are multiple HT panels for a plant, showing the sum of all HT panel active power values")
    public void the_response_should_perform_a_sum_operation_if_there_are_multiple_ht_panels_for_a_plant_showing_the_sum_of_all_ht_panel_active_power_values() {
        // Implement logic to extract HT panel data and check for sum operation
        int totalActivePower = apiResponse.jsonPath().getInt("data.totalActivePower");

        // Assuming here you need a specific sum for validation; replace the value as per your need
        int expectedTotalPower = 1000; // Replace this with the actual expected sum for validation

        assertEquals(totalActivePower, expectedTotalPower, "Sum operation validation failed for HT panels.");
    }

    @Then("the response time should be within the limit of {int} ms")
    public void the_response_time_should_be_within_the_limit_of_ms(Integer maxResponseTime) {
        long responseTime = apiResponse.getTime();
        System.out.println("Response time: " + responseTime + " ms");

        assertTrue(responseTime < maxResponseTime, "Response time exceeded the limit of " + maxResponseTime + " ms.");
    }
}
