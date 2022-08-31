package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestClient {

    public Response createUser(String bearerToken, String jsonBody) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/users");
    }

    public Response getUser(String bearerToken, String userId) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .when()
                .pathParam("userId", userId)
                .get("/users/{userId}");
    }
}
