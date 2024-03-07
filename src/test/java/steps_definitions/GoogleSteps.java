package steps_definitions;

import Actions.GoogleActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSteps {
    private final GoogleActions actions = new GoogleActions();
    @Given("I'm on the google home page")
    public void ImOnGoogleHomePage(){
        actions.ImInTheHomePage();
    }

    @When("I search for wwe")
    public void iSearchForWwe() {
        actions.SearchFor("WWE");
    }

    @When("I search for {string}")
    public void iSearchFor(String search) {
        actions.SearchFor(search);
    }

    @Then("I see results relating to wwe")
    public void iSeeResultsRelatingToWwe() {
        actions.ValidateResults();
    }
}
