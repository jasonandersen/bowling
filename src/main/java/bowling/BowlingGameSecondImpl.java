package bowling;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Tracks the frames and scoring for a game of bowling.
 */
public class BowlingGameSecondImpl implements BowlingGame {
	
	private static final int MAX_FRAMES = 10;
	private static final int TOTAL_PINS = 10;
	
	private Deque<Frame> frames = new LinkedList<Frame>();

	/* 
	 * @see bowling.BowlingGame#getCurrentFrameNumber()
	 */
	@Override
	public int getCurrentFrameNumber() {
		return getCurrentFrame() == null ? 0 : getCurrentFrame().getFrameNumber();
	}
	
	/* 
	 * @see bowling.BowlingGame#getTotalScore()
	 */
	@Override
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
	@Override
	public boolean isComplete() {
		return getCurrentFrame() instanceof FinalFrame && getCurrentFrame().isComplete();
	}
	
	/* 
	 * @see bowling.BowlingGame#throwBall(int)
	 */
	@Override
	public void throwBall(int pins) {
		validateScore(pins);
		setupCurrentFrame();
		getCurrentFrame().recordPins(pins);
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
	
	
	/**
	 * Represents a single frame within a single game of bowling.
	 */
	private class Frame {
		
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
				throw1Score = pins;
			} else if (throw2Score == null) {
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
			return (throw1Score != null && throw2Score != null) && throw1Score + throw2Score < TOTAL_PINS;
		}
		
		/**
		 * @return true if a frame is a spare
		 */
		public boolean isSpare() {
			return getThrow1Score() + getThrow2Score() == TOTAL_PINS && getThrow2Score() > 0;
		}
		
		/**
		 * @return true if a frame is a strike
		 */
		public boolean isStrike() {
			return getThrow1Score() == TOTAL_PINS;
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
	
	/**
	 * represents the last frame in a game
	 */
	private class FinalFrame extends Frame {
		
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
				if (getThrow1Score() + getThrow2Score() == TOTAL_PINS) {
					return false;
				} else if (getThrow1Score() == TOTAL_PINS && getThrow2Score() == TOTAL_PINS) {
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



}
