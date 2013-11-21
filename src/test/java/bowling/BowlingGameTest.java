package bowling;

import org.junit.Assert;
import org.junit.Test;

public class BowlingGameTest {
	
	private BowlingGame game = new BowlingGameImpl();
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeScore() {
		game.throwBall(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testScoreGreaterThan10() {
		game.throwBall(11);
	}
	
	@Test
	public void testGutterBall() {
		game.throwBall(0);
		Assert.assertEquals(0, game.getTotalScore());
	}
	
	@Test
	public void testSingleThrow() {
		game.throwBall(7);
		Assert.assertEquals(7, game.getTotalScore());
	}
	
	@Test
	public void testTwoThrows() {
		game.throwBall(7);
		game.throwBall(1);
		Assert.assertEquals(8, game.getTotalScore());
	}
	
	@Test
	public void testSpare() {
		game.throwBall(7);
		game.throwBall(3);
		game.throwBall(5);
		Assert.assertEquals(20, game.getTotalScore());
	}
	
	@Test
	public void testStrike() {
		game.throwBall(10);
		game.throwBall(5);
		game.throwBall(3);
		Assert.assertEquals(26, game.getTotalScore());
	}
	
	@Test
	public void testOpenFrame() {
		game.throwBall(5);
		game.throwBall(3);
		game.throwBall(5);
		Assert.assertEquals(13, game.getTotalScore());
	}
	
	@Test
	public void test2StrikesInARow() {
		game.throwBall(10);
		game.throwBall(10);
		game.throwBall(5);
		Assert.assertEquals(45, game.getTotalScore());
	}
	
	@Test
	public void test3StrikesInARow() {
		game.throwBall(10);
		game.throwBall(10);
		game.throwBall(10);
		Assert.assertEquals(60, game.getTotalScore());
	}
	
	@Test
	public void testFrameCount() {
		game.throwBall(1);
		game.throwBall(1);
		Assert.assertEquals(1, game.getCurrentFrameNumber());
	}
	
	@Test
	public void testFrameCount2OpenFrames() {
		game.throwBall(1);
		game.throwBall(1);
		game.throwBall(2);
		game.throwBall(2);
		Assert.assertEquals(2, game.getCurrentFrameNumber());
	}
	
	@Test
	public void testFrameCountStrike() {
		game.throwBall(10);
		game.throwBall(1);
		Assert.assertEquals(2, game.getCurrentFrameNumber());
	}
	
	@Test
	public void testFrameCountSpare() {
		game.throwBall(5);
		game.throwBall(5);
		game.throwBall(1);
		Assert.assertEquals(2, game.getCurrentFrameNumber());
	}

	@Test
	public void testAdvanceTo10thFrame() {
		advanceTo10thFrame();
		Assert.assertEquals(9, game.getCurrentFrameNumber());
		game.throwBall(1);
		Assert.assertEquals(10, game.getCurrentFrameNumber());
	}
	
	@Test(expected = GameOverException.class)
	public void testDoNotAllow11thFrame() {
		advanceTo10thFrame();
		game.throwBall(1); //10th
		game.throwBall(1); //10th
		game.throwBall(1); //11th <-- bad!
	}
	
	@Test
	public void test10thFrameAllowThirdBallOnSpare() {
		advanceTo10thFrame();
		game.throwBall(5); //10th
		game.throwBall(5); //10th
		game.throwBall(5); //legal third ball on 10th frame
		Assert.assertEquals(10, game.getCurrentFrameNumber());
	}
	
	@Test
	public void test10thFrameSpare() {
		advanceTo10thFrame();
		int scoreAtEndOf9thFrame = game.getTotalScore();
		game.throwBall(5); //10th
		game.throwBall(5); //10th
		game.throwBall(5); //legal third ball on 10th frame
		Assert.assertEquals(scoreAtEndOf9thFrame + 15, game.getTotalScore());
	}
	
	@Test
	public void test10thFrameStrike() {
		advanceTo10thFrame();
		int scoreAtEndOf9thFrame = game.getTotalScore();
		game.throwBall(10);
		game.throwBall(5); 
		Assert.assertEquals(scoreAtEndOf9thFrame + 15, game.getTotalScore());
	}
	
	@Test
	public void testGameIsCompleteLastFrameOpen() {
		advanceTo10thFrame();
		game.throwBall(1);
		game.throwBall(1);
		Assert.assertTrue(game.isComplete());
	}
	
	@Test
	public void testGameIsCompleteLastFrameSpare() {
		advanceTo10thFrame();
		game.throwBall(5);
		game.throwBall(5);
		game.throwBall(1);
		Assert.assertTrue(game.isComplete());
	}
	
	@Test
	public void testGameIsCompleteLastFrameStrike() {
		advanceTo10thFrame();
		game.throwBall(10);
		game.throwBall(1);
		Assert.assertTrue(game.isComplete());
	}
	
	@Test
	public void testGameIsNotComplete1ThrowIn10thFrame() {
		advanceTo10thFrame();
		game.throwBall(1);
		Assert.assertFalse(game.isComplete());
	}
	
	@Test
	public void testGameIsNotCompleteSpareIn10thFrame() {
		advanceTo10thFrame();
		game.throwBall(5);
		game.throwBall(5);
		Assert.assertFalse(game.isComplete());
	}
	
	@Test
	public void testGameIsNotCompleteStrikeIn10thFrame() {
		advanceTo10thFrame();
		game.throwBall(10);
		Assert.assertFalse(game.isComplete());
	}
	
	@Test
	public void testEmptyGame3StrikesIn10thFrame() {
		advanceTo10thFrame();
		Assert.assertEquals(0, game.getTotalScore());
		game.throwBall(10);
		game.throwBall(10);
		game.throwBall(10);
		Assert.assertEquals(30, game.getTotalScore());
		Assert.assertTrue(game.isComplete());
	}
	
	@Test
	public void testScorePerfectGame() {
		for (int i = 1; i <= 12; i++) {
			game.throwBall(10);
		}
		Assert.assertEquals(300, game.getTotalScore());
	}
	
	/**
	 * Advances the game so that the next ball thrown will be in the tenth frame
	 */
	private void advanceTo10thFrame() {
		while (game.getCurrentFrameNumber() < 9) {
			game.throwBall(0);
		}
		game.throwBall(0);
		Assert.assertEquals(9, game.getCurrentFrameNumber());
	}

}
