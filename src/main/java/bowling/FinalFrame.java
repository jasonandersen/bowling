package bowling;


/**
 * represents the last frame in a game
 */
public class FinalFrame extends Frame {
	
	private Integer throw3Score;
	
	/**
	 * Constructor
	 * @param previousFrame
	 */
	public FinalFrame(int frameNumber) {
		super(frameNumber);
	}
	
	/**
	 * Records a score within this frame
	 * @param pins
	 */
	@Override
	public void recordPins(int pins) {
		if (throw1Score == null) {
			throw1Score = pins;
		} else if (throw2Score == null) {
			throw2Score = pins;
		} else {
			//allow a third score
			if (isStrike() || isSpare()) {
				throw3Score = pins;
			}
		}
	}
	
	/**
	 * @return calculates the score
	 */
	@Override
	public int calculateScore() {
		return getThrow1Score() + getThrow2Score() + getThrow3Score();
	}
	
	/**
	 * @return true if the third throw has been registered or the first two throws add up to less than 10
	 */
	@Override
	public boolean isComplete() {
		if (throw2Score == null) {
			return false;
		}
		if (throw3Score == null) {
			if (getThrow1Score() + getThrow2Score() == BowlingGame.TOTAL_PINS) {
				return false;
			} else if (getThrow1Score() == BowlingGame.TOTAL_PINS && getThrow2Score() == BowlingGame.TOTAL_PINS) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Convenience method to get around NPE's on Integer objects
	 * @param score
	 * @return
	 */
	private int getThrow3Score() {
		return throw3Score == null ? 0 : throw3Score;
	}
	
}

