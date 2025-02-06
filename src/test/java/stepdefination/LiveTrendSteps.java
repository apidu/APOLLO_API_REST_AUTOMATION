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
import paylaods.Payloads;
import utils.ConfigManager;
import utils.TokenManager;

public class LiveTrendSteps {


    //Genrate acces token
    //User name pass and   stored in config proeprty.


        private String baseURI= ConfigManager.getProperty("url");
        String accessToken;
        Response apiResponse;

        @Given("I authenticate to the Live Trend API")
        public void authenticateToLiveTrendAPI() {

            String accessToken = TokenManager.getAccessToken();
            System.out.println("Access Token: " + accessToken);
      /**  RestAssured.baseURI = baseURI;
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
        assertNotNull(accessToken, "Access token not found");**/
    }
//LIVE TREND  WIDGET PLANT LEVEL ASSET NAME: TRACKER
        @When("I send a request to the Live Trend API")
        public void sendRequestToLiveTrendAPI() {
       // String apiRequestBody = "[{\"plantId\":2,\"instanceType\":\"TRACKER\",\"instanceIdList\":[\"1\",\"13\",\"14\",\"15\"],\"kpiCode\":\"TRACKER_ACCURACY_LIVE\",\"fromDate\":\"2025-01-20\",\"toDate\":\"2025-01-23\"}]";

            String payload = Payloads.getLiveTrendPayload();
            apiResponse = given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + TokenManager.getAccessToken())

                .body(payload)
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

