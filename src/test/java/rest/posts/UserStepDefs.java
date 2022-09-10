package rest.posts;

import io.cucumber.java.en.Given;
import utils.RestClientBDD;

public class UserStepDefs {

    RestClientBDD restClientBDD = new RestClientBDD();

    @Given("user creates a user")
    public void user_creates_a_user() {
        restClientBDD.createUserValidateCreated();
    }
}
