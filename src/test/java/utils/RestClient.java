package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestClient {

    public Response createUser(String bearerToken, String jsonBody) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/users");
    }

    public Response getUser(String bearerToken, String userId) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .when()
                .pathParam("userId", userId)
                .get("/users/{userId}");
    }

    public Response putUser(String bearerToken, String userId, String jsonBody) {
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

    public Response deleteUser(String bearerToken, String userId) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + bearerToken)
                .accept(ContentType.JSON)
                .when()
                .pathParam("userId", userId)
                .delete("/users/{userId}");
    }

    public Response getUsersUsingQueryParamName(String userName, String token) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/users?name=" + userName);
    }

    public Response createPostForUser(int userId, String jsonBody, String token) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .body(jsonBody)
                .when()
                .pathParam("userId", userId)
                .post("/users/{userId}/posts");
    }

    public Response getPostsOfUser(int userId, String token) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .pathParam("userId", userId)
                .get("/users/{userId}/posts");
    }
}
