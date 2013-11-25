package bowling;

import java.util.Collection;

/**
 * Tracks the frames and scoring for a game of bowling.
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public interface BowlingGame {

	/**
	 * Records a ball thrown
	 * @param pins knocked down, must be between 0 and 10
	 * @throws IllegalArgumentException when pins is less than 0 or greater than 10
	 */
	public abstract void throwBall(int pins);

	/**
	 * @return the number of the current frame
	 */
	public abstract int getCurrentFrameNumber();

	/**
	 * @return true if the current frame is complete
	 */
	public abstract boolean getCurrentFrameIsComplete();
	
	/**
	 * @return the current score of the game
	 */
	public abstract int getTotalScore();

	/**
	 * @return true if this bowling game is complete
	 */
	public abstract boolean isComplete();
	
	/**
	 * @return a read-only collection of frames, will never return a null but can return an empty collection
	 */
	public abstract Collection<Frame> getFrames();
	
	/**
	 * Maximum number of frames in a game.
	 */
	public static final int MAX_FRAMES = 10;
	
	/**
	 * The total pins in a single frame.
	 */
	public static final int TOTAL_PINS = 10;
	
}