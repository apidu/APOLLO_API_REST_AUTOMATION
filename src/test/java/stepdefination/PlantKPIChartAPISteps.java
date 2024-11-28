package stepdefination;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import report.ExtentReportManager;
import utils.ConfigManager;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class PlantKPIChartAPISteps {

    private String baseURI= ConfigManager.getProperty("url");
    String accessToken;
    Response apiResponse;

    @Given("I authenticate to the Plant KPI Chart API")
    public void authenticateToPlantKPIChartAPI() {
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

    @When("I send a POST request to the Plant KPI Chart API")
    public void sendRequestToPlantKPIChartAPI() {
        String requestBody = "[{\"plantId\":2,\"instanceType\":\"PLANT\",\"instanceIdList\":[2],\"kpiCode\":\"PLANT_ACTIVE_POWER\",\"fromDate\":\"2024-10-20\",\"toDate\":\"2024-10-20\",\"interDayOperation\":null,\"groupBy\":\"MINUTE\",\"timezone\":null,\"fromTime\":\"00:00\",\"toTime\":\"23:59\"},{\"plantId\":2,\"instanceType\":\"PLANT\",\"instanceIdList\":[2],\"kpiCode\":\"PLANT_POA_IRRADIATION_POWER\",\"fromDate\":\"2024-10-20\",\"toDate\":\"2024-10-20\",\"interDayOperation\":null,\"groupBy\":\"MINUTE\",\"timezone\":null,\"fromTime\":\"00:00\",\"toTime\":\"23:59\"}]";

        apiResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/apollokpimgmt/api/plant-kpi-provider/chart");

        assertEquals(apiResponse.getStatusCode(),200,"Status code not matched ");
    }

   /* @Then("I should receive a status code of {int}")
    public void validateStatusCode(int expectedStatusCode) {
        assertEquals(apiResponse.getStatusCode(), expectedStatusCode, "Status code mismatch");
    }*/

    @Then("the response should contain plantId {int}")
    public void validatePlantId(int plantId) {
        String responseBody = apiResponse.getBody().asString();
        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            assertEquals(obj.getInt("plantId"), plantId, "Plant ID mismatch");
        }
    }

    @Then("the response should contain kpiCode {string}")
    public void validateKpiCode(String kpiCode) {
        String responseBody = apiResponse.getBody().asString();
        JSONArray jsonArray = new JSONArray(responseBody);
        boolean found = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj.getString("kpiCode").equals(kpiCode)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "KPI Code not found: " + kpiCode);
    }
}

