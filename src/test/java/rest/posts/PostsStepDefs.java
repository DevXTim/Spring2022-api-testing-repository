package rest.posts;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.RestClientBDD;

public class PostsStepDefs {

    RestClientBDD restClientBDD = new RestClientBDD();

    @When("user creates a post with title {string} and body {string}")
    public void user_creates_a_post_with_title_and_body(String title, String body) {
        restClientBDD.createPostUsingUserId(title, body);
    }

    @Then("check if post is created with title {string} and body {string}")
    public void check_if_post_is_created_with_title_and_body(String title, String body) {
        restClientBDD.validatePostWithTitleAndBodyIsCreated(title, body);
    }
}
