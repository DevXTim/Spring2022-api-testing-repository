package rest;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import utils.ConfigReader;

public abstract class BaseClass {

    // Arrange
    // Set up baseUrl to RestAssured
    private static final String apiHost = ConfigReader.getProperty("apiHost");
    private static final String apiVersion = ConfigReader.getProperty("apiVersion");
    public static final String token = ConfigReader.getProperty("token");
    public static final Faker faker = new Faker();
    public static final Gson gson = new Gson();


    @BeforeEach
    public void baseUrlSetup() {
        RestAssured.baseURI = apiHost + apiVersion;
    }
}
