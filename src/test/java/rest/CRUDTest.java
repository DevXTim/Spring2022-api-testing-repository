package rest;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import pojos.User;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class CRUDTest extends BaseClass {

    // CRUD -> Create, Read, Update, Delete

    // Assumptions -> checking if steps that we do are correct before we do assertion

    //soft and hard assert
    // assertAll

    // Goal: test if users method run() gives 10km/h
    // When create a user, you assume it was created and it has run()
    // Assertion of run() method -> final testing

    // Assertions -> method we use to verify whether the condition is true.
    // Validation of expected with actual result using different statement


    // given when then (arrange act assert)
    @Test
    public void createUser_givenValidResponse_createdStatusCode() {
        User user = new User();
        user.setName(faker.name().fullName());
        user.setEmail(user.getName().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        // to serialize from pojo(class) format to json format
        System.out.println(gson.toJson(user));

        Response postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + token)
                .accept(ContentType.JSON)
                .body(gson.toJson(user))
                .when()
                .post("/users");

        System.out.println(postResponse.asString());

        assertAll(
                () -> assertEquals(201, postResponse.getStatusCode()),
                () -> assertTrue(postResponse.asString().contains(user.getName())),
                () -> assertTrue(postResponse.asString().contains(user.getEmail()))
        );
    }

    // Read -> create a user, then we have to retrieve the created user using id from response

    @Test
    public void getUser_givenValidResponse_okStatusCode() {
        User user = new User();
        user.setName(faker.name().fullName());
        user.setEmail(user.getName().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        // to serialize from pojo(class) format to json format
        System.out.println(gson.toJson(user));

        Response postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + token)
                .accept(ContentType.JSON)
                .body(gson.toJson(user))
                .when()
                .post("/users");

        // Given and When are correct
        Assumptions.assumeTrue(201 == postResponse.getStatusCode());

        System.out.println(postResponse.asString());

        User userPost = gson.fromJson(postResponse.asString(), User.class);

        Response getResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization","Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .pathParam("userId", userPost.getId())
                .get("/users/{userId}");

        assertAll(
                () -> assertEquals(200, getResponse.getStatusCode()),
                () -> assertTrue(getResponse.asString().contains(user.getName())),
                () -> assertTrue(getResponse.asString().contains(user.getEmail()))
        );
    }
}
