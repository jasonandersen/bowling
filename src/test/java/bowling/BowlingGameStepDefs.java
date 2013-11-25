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
	
	
	/*
	 * GIVEN step defs
	 */
	
	@Given("^a new bowling game$")
	public void aNewBowlingGame() throws Throwable {
		game = new BowlingGameImpl();
		exception = null;
	}
	
	@Given("^there are no errors$")
	public void there_are_no_errors() throws Throwable {
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
	
	@Given("^a bowling game at the beginning of the final frame with a zero score$")
	public void aBowlingGameAtTheBeginningOfTheFinalFrameWithAZeroScore() throws Throwable {
		game = new BowlingGameImpl();
		while (game.getCurrentFrameNumber() < BowlingGame.MAX_FRAMES - 1) {
			game.throwBall(0);
		}
		game.throwBall(0);
		assertEquals(BowlingGame.MAX_FRAMES - 1, game.getCurrentFrameNumber());
		assertTrue(game.getCurrentFrameIsComplete());
		assertEquals(0, game.getTotalScore());
	}
	
	
	/*
	 * WHEN step defs
	 */
	
	@When("^the player scores (-?\\d+) pins?$")
	public void thePlayerScoresPins(Integer numPins) throws Throwable {
	    throwBall(numPins);
	}
	
	@When("^the player throws a strike$")
	public void thePlayerThrowsAStrike() throws Throwable {
	    throwBall(BowlingGame.TOTAL_PINS);
	}
	
	
	/*
	 * THEN step defs
	 */
	
	@Then("^the game score is (\\d+)$")
	public void theGameScoreIs(int expectedScore) throws Throwable {
	    assertEquals(expectedScore, game.getTotalScore());
	    assertNull(exception);
	}

	@Then("^the current frame number is (\\d+)$")
	public void givenTheCurrentFrameNumberIs(int frameNum) throws Throwable {
		assertEquals(frameNum, game.getCurrentFrameNumber());
		assertNull(exception);
	}
	
	@Then("^an error occurs$")
	public void anErrorShouldOccur() throws Throwable {
	    assertNotNull(exception);
	}
	
	@Then("^the game is over$")
	public void theGameIsOver() throws Throwable {
		assertTrue(game.isComplete());
		assertNull(exception);
	}
	
	@Then("^the game is not over$")
	public void theGameIsNotOver() throws Throwable {
		assertFalse(game.isComplete());
		assertNull(exception);
	}
	
	/**
	 * Calls the throwBall method and catches any exceptions to store for later inspection
	 * @param score
	 */
	private void throwBall(Integer score) {
		try {
			game.throwBall(score);
		} catch (Exception e) {
			exception = e;
		}
	}
}
