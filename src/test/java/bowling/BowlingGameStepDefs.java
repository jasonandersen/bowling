package bowling;

import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

/**
 * Step definitions for bowling game cucumber tests.
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
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
	
	@Given("^these throws were recorded:$")
	public void theseThrowsWereRecorded(List<Integer> scores) throws Throwable {
		for (Integer score : scores) {
			game.throwBall(score);
		}
	}
}
