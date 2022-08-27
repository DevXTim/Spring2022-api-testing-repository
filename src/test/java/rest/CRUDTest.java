package rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CRUDTest {


    // Arrange
    // Set up baseUrl to RestAssured
    private static final String apiHost = "https://gorest.co.in/public";
    private static final String apiVersion = "/v2";

//    private static final String token = "0958864dd3f5b9cac67e0564b03444d7a9a1033897f724f1eff926f4f96ccdb0";
    private static final String token = "123";

    // Arrange
    // Act
    // Assert


    @Test
    public void givenValidResponse_checkStatusCode() {
        //Setting up baseUrl as in postman
        RestAssured.baseURI = apiHost + apiVersion;

        // RestAssured also uses triple A
        // given when then (arrange, act, assert)
        Response getResponse = RestAssured
                // Arrange
                .given()
                // contentType is about type of data you send to server
                .contentType(ContentType.JSON)
                // Headers include authorization and accept data in key value type
                .headers("Authorization", token)
                // this is about what type of data YOU receive as a client
                .accept(ContentType.JSON)
                // Act -> send is happening
                .when()
                .get("/users");

        System.out.println(getResponse.asString());

        assertEquals(201, getResponse.getStatusCode());

    }

    @Test
    public void givenInvalidToken_checkStatusCode() {
        //Setting up baseUrl as in postman
        // Use hooks
        RestAssured.baseURI = apiHost + apiVersion;

        String postBody = "{\n" +
                "    \"name\": \"Saturday august\",\n" +
                "    \"email\": \"devxadmin1351351351@gmail.com\",\n" +
                "    \"gender\": \"female\",\n" +
                "    \"status\": \"active\"\n" +
                "}";

        // RestAssured also uses triple A
        // given when then (arrange, act, assert)
        Response postResponse = RestAssured
                // Arrange
                .given()
                // contentType is about type of data you send to server
                .contentType(ContentType.JSON)
                // Headers include authorization and accept data in key value type
                .headers("Authorization", token)
                // this is about what type of data YOU receive as a client
                .accept(ContentType.JSON)
                .body(postBody)
                // Act -> send is happening
                .when()
                .post("/users");

        System.out.println(postResponse.asString());

        assertEquals(401, postResponse.getStatusCode());


        // De/Serialization -> converting one data source to another type
        // JSON to -> map, object (POJO), List
        // Object, List -> JSON

        // Cucumber ->
    }
}
