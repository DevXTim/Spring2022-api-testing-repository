package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojos.User;
import rest.BaseClass;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Locale;

public class RestClientBDD extends BaseClass {

    public static void createUserValidateCreated() {
        User user = new User();
        user.setName(faker.name().fullName());
        user.setEmail(user.getname().toLowerCase(Locale.ROOT).trim().replace(" ", "") + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        String jsonUser = gson.toJson(user);

        Response postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .body(jsonUser)
                .when()
                .post("/users");
        assumeTrue(postResponse.getStatusCode() == 201);
    }
}
