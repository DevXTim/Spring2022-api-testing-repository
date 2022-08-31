package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import pojos.User;
import utils.RestClient;

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

    RestClient restClient = new RestClient();

    @Test
    public void createUser_givenValidResponse_createdStatusCode() {
        User user = new User();
        user.setname(faker.name().fullName());
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        // to serialize from pojo(class) format to json format
        System.out.println(gson.toJson(user));

        Response postResponse = restClient.createUser(token, gson.toJson(user));

        System.out.println(postResponse.asString());

        assertAll(
                () -> assertEquals(201, postResponse.getStatusCode()),
                () -> assertTrue(postResponse.asString().contains(user.getname())),
                () -> assertTrue(postResponse.asString().contains(user.getEmail()))
        );
    }

    // Read -> create a user, then we have to retrieve the created user using id from response

    @Test
    public void getUser_givenValidResponse_okStatusCode() {
        // Arrange
        User user = new User();
        user.setname(faker.name().fullName());
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        // to serialize from pojo(class) format to json format
        System.out.println(gson.toJson(user));

        Response postResponse = restClient.createUser(token, gson.toJson(user));

        // Given and When are correct
        Assumptions.assumeTrue(201 == postResponse.getStatusCode());

        System.out.println(postResponse.asString());

        // Act
        User userPost = gson.fromJson(postResponse.asString(), User.class);

        Response getResponse = restClient.getUser(token, userPost.getId());

        // Assert
        assertAll(
                () -> assertEquals(200, getResponse.getStatusCode()),
                () -> assertTrue(getResponse.asString().contains(user.getname())),
                () -> assertTrue(getResponse.asString().contains(user.getEmail()))
        );
    }

    @Test
    public void serializationWithGson_DeserializationWithRestAssuredAndJsonPath() {
        // Arrange
        User user = new User();
        user.setname(faker.name().fullName());
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        // to serialize from pojo(class) format to json format
        System.out.println(gson.toJson(user));

        Response postResponse = restClient.createUser(token, gson.toJson(user));

        // Given and When are correct
        Assumptions.assumeTrue(201 == postResponse.getStatusCode());

        System.out.println(postResponse.asString());

        // Act
        // Task is to retrieve id from json response without using pojo
        // Covering diff ways to retrieve info from JSON response
        // Gson library, convert JSON to POJO
        // Address directly to values in JSON using key and jsonPath
        int postUserId = postResponse.jsonPath().getInt("id");

        // Task: convert JSON to POJO without using GSON
        User postUser = postResponse.as(new TypeRef<User>() {});

        System.out.println(postUser.getname());
        System.out.println(postUser.getId());
        System.out.println(postUser.getEmail());
        System.out.println(postUser.getStatus());

        Response getResponse = restClient.getUser(token, postUserId + "");

        // Assert
        assertAll(
                () -> assertEquals(200, getResponse.getStatusCode()),
                () -> assertTrue(getResponse.asString().contains(user.getname())),
                () -> assertTrue(getResponse.asString().contains(user.getEmail()))
        );
    }

    @Test
    public void serializationAndDeserializationUsingJackson() throws JsonProcessingException {
        // Arrange
        User user = new User();
        user.setname(faker.name().fullName());
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");


        // I need to convert POJO to JSON to send POST request using Jackson
        // to serialize from pojo(class) format to json format

        // Serialization process
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(user);
        
        System.out.println(jsonBody);

        Response postResponse = restClient.createUser(token, jsonBody);

        // Deserialization process
        User postUser = objectMapper.readValue(postResponse.asString(), User.class);

        System.out.println(postUser.toString());
        System.out.println(postUser.getId());



//        // Given and When are correct
//        Assumptions.assumeTrue(201 == postResponse.getStatusCode());
//
//        System.out.println(postResponse.asString());
//
//        // Act
//
//
//        Response getResponse = restClient.getUser(token, userPost.getId());
//
//        // Assert
//        assertAll(
//                () -> assertEquals(200, getResponse.getStatusCode()),
//                () -> assertTrue(getResponse.asString().contains(user.getname())),
//                () -> assertTrue(getResponse.asString().contains(user.getEmail()))
//        );
    }
}
