package stepdefination;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import report.ExtentReportManager;
import utils.ConfigManager;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PortfolioKPISelectionSteps {

   // private String baseURI = "https://sandbox.apolloenergyanalytics.com";
    private String baseURI= ConfigManager.getProperty("url");
    private String accessToken;
    private Response apiResponse;
    ExtentTest test;

    @Given("I authenticate to the Portfolio KPI Selection API")
    public void authenticateToPortfolioKPISelectionAPI() {

// Initialize the Extent Report
        ExtentReportManager.initReports();

        // Create a new test in the report
        test = ExtentReportManager.createTest("Portfolio KPI Selection - Authentication");

        
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

        int statusCode = authResponse.getStatusCode();


    }

    @When("I send a request to the Portfolio KPI Selection endpoint")
    public void sendRequestToPortfolioKPISelection() {
        apiResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/apollokpimgmt/api/portfolio-kpi/advanceanalytics/selection");
    }

    @Then("I should receive a status code of {int}")
    public void validateStatusCode(int expectedStatusCode) {
        assertEquals(apiResponse.getStatusCode(), expectedStatusCode, "API request failed");
        System.out.println(apiResponse.getStatusCode());


        test.pass("Received expected status code: " + expectedStatusCode);
        test.info("Actual Status Code: " + apiResponse.getStatusCode());


    }
}
