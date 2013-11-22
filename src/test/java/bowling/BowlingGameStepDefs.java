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
	
	private Exception exception;
	
	@Given("^a new bowling game$")
	public void aNewBowlingGame() throws Throwable {
		game = new BowlingGameImpl();
		exception = null;
	}

	@When("^a player knocks down (\\d+) pins$")
	public void aPlayerKnocksDownPins(Integer numPins) throws Throwable {
	    throwBall(numPins);
	}

	@Then("^the game score is (\\d+)$")
	public void theGameScoreIs(int expectedScore) throws Throwable {
	    assertEquals(expectedScore, game.getTotalScore());
	    assertNull(exception);
	}
	
	@Given("^these throws were recorded:$")
	public void theseThrowsWereRecorded(List<Integer> scores) throws Throwable {
		assert(exception == null);
		for (Integer score : scores) {
			if (exception == null) {
				throwBall(score);
			}
		}
	}
	
	@Then("^an error should occur$")
	public void anErrorShouldOccur() throws Throwable {
	    assertNotNull(exception);
	}
	
	private void throwBall(Integer score) {
		try {
			game.throwBall(score);
		} catch (Exception e) {
			exception = e;
		}
	}
}
