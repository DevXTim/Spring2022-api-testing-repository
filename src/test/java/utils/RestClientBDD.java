package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojos.Post;
import pojos.User;
import rest.users.BaseClass;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class RestClientBDD extends BaseClass {

    RestClient restClient = new RestClient();
    Date date = new Date();

    public void createUserValidateCreated() {
        User user = new User();
        user.setName("DevxTestingAccount");
        user.setEmail("devxtestingaccount" + date.getTime() + "@gmail.com");
        user.setGender("male");
        user.setStatus("active");

        System.out.println(user.toString() + "\n");

        String jsonUser = gson.toJson(user);

        Response postResponse = restClient.createUser(token, jsonUser);

        System.out.println(postResponse.asString() + "\n");

        assumeTrue(postResponse.getStatusCode() == 201);
    }

    public int getUserIdFromTestAccount() {
        Response getResponse = restClient.getUsersUsingQueryParamName("DevxTestingAccount", token);
        assumeTrue(getResponse.getStatusCode() == 200);

        ArrayList ids = (ArrayList) getResponse.jsonPath().getList("id");
        return (int) ids.get(0);
    }

    public void createPostUsingUserId(String title, String body) {
        Post post = new Post(title, body);

        System.out.println(post.toString() + "\n");

        String jsonPost = gson.toJson(post);

        Response postResponse = restClient.createPostForUser(getUserIdFromTestAccount(), jsonPost, token);

        System.out.println(postResponse.asString() + "\n");

        assumeTrue(postResponse.getStatusCode() == 201);
    }

    public void validatePostWithTitleAndBodyIsCreated(String title, String body) {
        Response getResponse = restClient.getPostsOfUser(getUserIdFromTestAccount(), token);

        String getJson = getResponse.asString().replace("[", "").replace("]", "");

        System.out.println(getJson + "\n");

        Post getPost = gson.fromJson(getJson, Post.class);

        assertAll(
                () -> assertEquals(getUserIdFromTestAccount() + "", getPost.getUserId()),
                () -> assertEquals(title, getPost.getTitle()),
                () -> assertEquals(body, getPost.getBody())
        );
    }
}
