package bowling;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

/**
 * Step definitions for bowling game cucumber tests.
 * 
 * @author Jason Andersen
 */
public class BowlingGameStepDefs {
	
	private BowlingGame game;
	
	@Given("^a new bowling game$")
	public void aNewBowlingGame() throws Throwable {
		game = new BowlingGameImpl();
	}

	@When("^a player knocks down (\\d+) pins$")
	public void aPlayerKnocksDownPins(int numPins) throws Throwable {
	    game.throwBall(numPins);
	}

	@Then("^the game score is (\\d+)$")
	public void theGameScoreIs(int expectedScore) throws Throwable {
	    assertEquals(expectedScore, game.getTotalScore());
	}
}
