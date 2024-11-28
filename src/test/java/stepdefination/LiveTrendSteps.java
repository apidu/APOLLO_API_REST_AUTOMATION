package stepdefination;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ConfigManager;

public class LiveTrendSteps {


    

        //String baseURI = "https://sandbox.apolloenergyanalytics.com";
        private String baseURI= ConfigManager.getProperty("url");
        String accessToken;
        Response apiResponse;

        @Given("I authenticate to the Live Trend API")
        public void authenticateToLiveTrendAPI() {
        RestAssured.baseURI = baseURI;
        RestAssured.useRelaxedHTTPSValidation();

            String username = ConfigManager.getProperty("username");
            String password = ConfigManager.getProperty("password");
        //String username = "prameshwar";
        //String password = "Hzb@3333";

        String authRequestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"rememberMe\":true}";

        Response authResponse = given()
                .contentType(ContentType.JSON)
                .body(authRequestBody)
                .when()
                .post("/auth/login");

        assertEquals(authResponse.getStatusCode(), 200, "Authentication failed");
        accessToken = authResponse.jsonPath().getString("access_token");
        assertNotNull(accessToken, "Access token not found");
    }

        @When("I send a request to the Live Trend API")
        public void sendRequestToLiveTrendAPI() {
        String apiRequestBody = "[{\"plantId\":2,\"instanceType\":\"TRACKER\",\"instanceIdList\":[\"1\",\"13\",\"14\",\"15\"],\"kpiCode\":\"TRACKER_ACCURACY_LIVE\",\"fromDate\":\"2024-08-27\",\"toDate\":\"2024-08-27\"}]";

            apiResponse = given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + accessToken)

                .body(apiRequestBody)
                .when()
                .post("/apollokpimgmt/api/plant-kpi-provider/chart");
    }

        @Then("I should receive a successful response")
        public void validateSuccessfulResponse() {
        assertEquals(apiResponse.getStatusCode(), 200, "API request failed");
        String responsebody=apiResponse.getBody().asString();
        System.out.println(responsebody);


    }
    }

