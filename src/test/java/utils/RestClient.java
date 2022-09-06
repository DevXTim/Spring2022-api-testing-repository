package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestClient {

    public static Response createUser(String bearerToken, String jsonBody) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/users");
    }

    public static Response getUser(String bearerToken, String userId) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .when()
                .pathParam("userId", userId)
                .get("/users/{userId}");
    }

    public static Response putUser(String bearerToken, String userId, String jsonBody) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .body(jsonBody)
                .when()
                .pathParam("userId", userId)
                .put("/users/{userId}");
    }

    public static Response deleteUser(String bearerToken, String userId) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .when()
                .pathParam("userId", userId)
                .delete("/users/{userId}");
    }
}
