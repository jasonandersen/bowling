package bowling;



/**
 * Represents a single frame within a single game of bowling.
 */
public class Frame {
	
	protected Integer throw1Score;
	protected Integer throw2Score;
	private final int frameNumber;
	private Frame nextFrame;
	
	
	/**
	 * Constructor
	 * @param frameNumber
	 */
	public Frame(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	
	/**
	 * Records a score within this frame
	 * @param pins
	 */
	public void recordPins(int pins) {
		if (throw1Score == null) {
			if (pins > BowlingGame.TOTAL_PINS) {
				throw new IllegalArgumentException(pins + " is not a valid score");
			}
			throw1Score = pins;
		} else if (throw2Score == null) {
			if (throw1Score + pins > BowlingGame.TOTAL_PINS) {
				String message = String.format("%d will push the total number of pins for this frame (%d) past the maximum: %d", 
						pins, pins + throw1Score, BowlingGame.TOTAL_PINS);
				throw new IllegalArgumentException(message);
			}
			throw2Score = pins;
		}
	}
	
	/**
	 * @return the score for _this_ frame only
	 */
	public int calculateScore() {
		int score = getThrow1Score() + getThrow2Score();
		if (isSpare() && nextFrame != null) {
			//for a spare, add the next throw to the score for this frame
			score += nextFrame.getThrow1Score();
		} else if (isStrike() && nextFrame != null) {
			//for strikes, add the next two throws to the score for this frame
			score += nextFrame.getThrow1Score() + nextFrame.getThrow2Score();
			if (nextFrame.isStrike() && nextFrame.nextFrame != null) {
				score += nextFrame.nextFrame.getThrow1Score();
			}
		}
		return score;
	}
	
	/**
	 * @return true if a frame has been completed
	 */
	public boolean isComplete() {
		return isOpen() || isSpare() || isStrike();
	}
	
	/**
	 * @return true if this is an open frame
	 */
	public boolean isOpen() {
		return (throw1Score != null && throw2Score != null) && throw1Score + throw2Score < BowlingGame.TOTAL_PINS;
	}
	
	/**
	 * @return true if a frame is a spare
	 */
	public boolean isSpare() {
		return getThrow1Score() + getThrow2Score() == BowlingGame.TOTAL_PINS && getThrow2Score() > 0;
	}
	
	/**
	 * @return true if a frame is a strike
	 */
	public boolean isStrike() {
		return getThrow1Score() == BowlingGame.TOTAL_PINS;
	}
	
	/**
	 * @return the frame number of this frame
	 */
	public int getFrameNumber() {
		return frameNumber;
	}
	
	/**
	 * @param nextFrame
	 */
	public void setNextFrame(Frame nextFrame) {
		this.nextFrame = nextFrame;
	}
	
	/**
	 * @return the score for throw 1, will return null if throw 1 has not been registered yet
	 */
	public Integer getThrow1() {
		return throw1Score;
	}
	
	/**
	 * @return the score for throw 2, will return null if throw 2 has not been registered yet
	 */
	public Integer getThrow2() {
		return throw2Score;
	}
	
	/**
	 * Convenience method to get around NPE's on Integer objects
	 * @param score
	 * @return
	 */
	protected int getThrow1Score() {
		return throw1Score == null ? 0 : throw1Score;
	}
	
	/**
	 * Convenience method to get around NPE's on Integer objects
	 * @param score
	 * @return
	 */
	protected int getThrow2Score() {
		return throw2Score == null ? 0 : throw2Score;
	}
}

