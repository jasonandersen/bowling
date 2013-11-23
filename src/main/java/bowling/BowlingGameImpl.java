package bowling;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Tracks the frames and scoring for a game of bowling.
 */
public class BowlingGameImpl implements BowlingGame {
	
	private Deque<Frame> frames = new LinkedList<Frame>();

	/* 
	 * @see bowling.BowlingGame#getCurrentFrameNumber()
	 */
	public int getCurrentFrameNumber() {
		return getCurrentFrame() == null ? 0 : getCurrentFrame().getFrameNumber();
	}
	
	/* 
	 * @see bowling.BowlingGame#getTotalScore()
	 */
	public int getTotalScore() {
		int score = 0;
		for (Frame frame : frames) {
			score += frame.calculateScore();
		}
		return score;
	}
	
	/* 
	 * @see bowling.BowlingGame#isComplete()
	 */
	public boolean isComplete() {
		return getCurrentFrame() instanceof FinalFrame && getCurrentFrame().isComplete();
	}
	
	/* 
	 * @see bowling.BowlingGame#throwBall(int)
	 */
	public void throwBall(int pins) {
		validateScore(pins);
		setupCurrentFrame();
		getCurrentFrame().recordPins(pins);
	}
	
	/*
	 * @see bowling.BowlingGame#getFrames()
	 */
	public Collection<Frame> getFrames() {
		return Collections.unmodifiableCollection(frames);
	}
	
	/**
	 * Ensure that the score is in the valid range
	 * @param pins
	 */
	private void validateScore(int pins) {
		if (pins < 0) {
			throw new IllegalArgumentException("score cannot be less than zero");
		}
		if (pins > TOTAL_PINS) {
			throw new IllegalArgumentException("score cannot be greater than " + TOTAL_PINS);
		}
	}
	
	/**
	 * Ensure that the current frame is in a proper state
	 */
	private void setupCurrentFrame() {
		Frame currentFrame = getCurrentFrame();
		if (currentFrame == null) {
			frames.add(new Frame(1));
		} else if(currentFrame.isComplete()) {
			Frame nextFrame = buildNextFrame(currentFrame);
			frames.add(nextFrame);
		}
	}
	
	/**
	 * Builds out the next frame based on the current frame
	 * @param currentFrame
	 * @return a properly constructed frame
	 * @throws GameOverException when a FinalFrame is passed in
	 */
	private Frame buildNextFrame(Frame currentFrame) {
		if (currentFrame instanceof FinalFrame) {
			throw new GameOverException();
		}
		Frame nextFrame;
		if (isNextToLastFrame(currentFrame)) {
			nextFrame = new FinalFrame(currentFrame.getFrameNumber() + 1);
		} else {
			nextFrame = new Frame(currentFrame.getFrameNumber() + 1);
		}
		currentFrame.setNextFrame(nextFrame);
		return nextFrame;
	}
	
	/**
	 * @param frame
	 * @return true if the next frame to be built will be the final frame for this game
	 */
	private boolean isNextToLastFrame(Frame frame) {
		return frame.getFrameNumber() == MAX_FRAMES - 1;
	}
	
	/**
	 * @return the current frame being played
	 */
	private Frame getCurrentFrame() {
		return frames.peekLast();
	}
	
	


}
