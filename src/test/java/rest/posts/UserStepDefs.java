package rest.posts;

import io.cucumber.java.en.Given;
import rest.BaseClass;
import utils.RestClientBDD;

public class UserStepDefs {

    @Given("user creates a user")
    public void user_creates_a_user() {
        RestClientBDD.createUserValidateCreated();
    }
}
