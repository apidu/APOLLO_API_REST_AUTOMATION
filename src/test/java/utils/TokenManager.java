package utils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Objects;
public class TokenManager {

    private static String accessToken;

    // Method to get the access token; generates a new token if not already available or expired
    public static String getAccessToken() {
        if (Objects.isNull(accessToken)) {
            generateAccessToken();
        }
        return accessToken;
    }

    // Method to generate and store the access token
    private static void generateAccessToken() {
        String baseURI = ConfigManager.getProperty("url");
        String username = ConfigManager.getProperty("username");
        String password = ConfigManager.getProperty("password");

        String authRequestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"rememberMe\":true}";

        RestAssured.baseURI = baseURI;
        RestAssured.useRelaxedHTTPSValidation();

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(authRequestBody)
                .post("/auth/login");

        if (response.getStatusCode() == 200) {
            accessToken = response.jsonPath().getString("access_token");
            System.out.println("Access Token Generated Successfully: " + accessToken);
        } else {
            throw new RuntimeException("Failed to generate access token, status code: " + response.getStatusCode());
        }
    }
}
