package rest.posts;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ConfigReader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class Hooks {

    // Arrange
    // Set up baseUrl to RestAssured
    private static final String apiHost = ConfigReader.getProperty("apiHost");
    private static final String apiVersion = ConfigReader.getProperty("apiVersion");

    @Before
    public void baseUrlSetupBDD() {
        RestAssured.baseURI = apiHost + apiVersion;
    }

    @After
    public void cleanUpTestAccs() {
        Response getResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + ConfigReader.getProperty("token"))
                .accept(ContentType.JSON)
                .when()
                .get("/users?name=DevxTestingAccount");
        assumeTrue(getResponse.getStatusCode() == 200);

        ArrayList ids = (ArrayList) getResponse.jsonPath().getList("id");

        for (Object o : ids) {
            Response deleteResponse = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + ConfigReader.getProperty("token"))
                    .accept(ContentType.JSON)
                    .when()
                    .pathParam("userId", o.toString())
                    .delete("/users/{userId}");
            assumeTrue(deleteResponse.getStatusCode() == 204);
        }
    }
}
