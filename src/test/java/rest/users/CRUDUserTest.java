package rest.users;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import pojos.User;
import utils.ConfigReader;
import utils.RestClient;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CRUDUserTest extends BaseClass{
    
    RestClient restClient = new RestClient();
    
    // CRUD - create, read

    @Test
    public void createUser_givenValidResponse_createdStatusCode() {
        User user = new User();
        user.setName(faker.name().fullName());
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        System.out.println(gson.toJson(user));

        Response postResponse = restClient.createUser(token, gson.toJson(user));

        System.out.println(postResponse.asString());

        assertAll(
                () -> assertEquals(201, postResponse.getStatusCode()),
                () -> assertTrue(postResponse.asString().contains(user.getname())),
                () -> assertTrue(postResponse.asString().contains(user.getEmail()))
        );
    }


    @Test
    public void getUser_givenValidResponse_okStatusCode() {
        User user = new User(faker.name().fullName(), "", "male", "active");
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");

        System.out.println(gson.toJson(user));

        Response postResponse = restClient.createUser(token, gson.toJson(user));
        Assumptions.assumeTrue(201 == postResponse.getStatusCode());

        System.out.println(postResponse.asString());

        User userPost = gson.fromJson(postResponse.asString(), User.class);

        Response getResponse = restClient.getUser(token, userPost.getId());

        assertAll(
                () -> assertEquals(200, getResponse.getStatusCode()),
                () -> assertTrue(getResponse.asString().contains(user.getname())),
                () -> assertTrue(getResponse.asString().contains(user.getEmail()))
        );
    }


    @Test
    public void putUser_GivenValidResponse_okStatusCode() {
        User user = new User(faker.name().fullName(), "", "male", "active");
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");

        System.out.println(gson.toJson(user));

        Response postResponse = restClient.createUser(token, gson.toJson(user));
        Assumptions.assumeTrue(201 == postResponse.getStatusCode());

        System.out.println(postResponse.asString());

        User userPost = gson.fromJson(postResponse.asString(), User.class);

        user.setStatus("inactive");
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@yahoo.com");

        Response putResponse = restClient.putUser(token, userPost.getId(), gson.toJson(user));

        Response getResponse = restClient.getUser(token, userPost.getId());

        System.out.println(getResponse.asString());

        User userGet = gson.fromJson(getResponse.asString(), User.class);

        assertAll(
                () -> assertEquals(200, putResponse.getStatusCode()),
                () -> assertEquals(200, getResponse.getStatusCode()),
                () -> assertEquals(user.getStatus(), userGet.getStatus()),
                () -> assertEquals(user.getEmail(), userGet.getEmail())
        );
    }


    @Test
    public void deleteUser_GivenValidResponse_noContentStatusCode() {
        User user = new User(faker.name().fullName(), "", "male", "active");
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");

        System.out.println(gson.toJson(user));

        Response postResponse = restClient.createUser(token, gson.toJson(user));
        Assumptions.assumeTrue(201 == postResponse.getStatusCode());

        System.out.println(postResponse.asString());

        User userPost = gson.fromJson(postResponse.asString(), User.class);

        Response deleteResponse = restClient.deleteUser(token, userPost.getId());
        Response getResponse = restClient.getUser(token, userPost.getId());

        assertAll(
                () -> assertEquals(204, deleteResponse.getStatusCode()),
                () -> assertEquals(404, getResponse.getStatusCode()),
                () -> assertEquals("Resource not found", getResponse.jsonPath().getString("message"))
        );
    }
}
