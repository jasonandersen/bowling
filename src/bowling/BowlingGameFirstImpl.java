package bowling;

import java.util.LinkedList;

public class BowlingGameFirstImpl implements BowlingGame {

	private LinkedList<Frame> frames = new LinkedList<Frame>();

	@Override
	public void throwBall(int pins) {
		if (pins < 0) {
			throw new IllegalArgumentException("score cannot be less than zero");
		}
		if (pins > 10) {
			throw new IllegalArgumentException("score cannot be greater than " + 10);
		}
		Frame currentFrame = frames.peekLast();
		if (currentFrame == null) {
			frames.add(new Frame(1));
		} else if(currentFrame.isComplete()) {
			if (currentFrame instanceof FinalFrame) {
				throw new GameOverException();
			}
			Frame nextFrame;
			if (currentFrame.getFrameNumber() < 9) {
				nextFrame = new Frame(currentFrame.getFrameNumber() + 1);
			} else {
				nextFrame = new FinalFrame(currentFrame.getFrameNumber() + 1);
			}
			currentFrame.setNextFrame(nextFrame);
			frames.add(nextFrame);
		}
		frames.peekLast().recordPins(pins);
	}

	@Override
	public int getCurrentFrameNumber() {
		return frames.peekLast() == null ? 0 : frames.peekLast().getFrameNumber();
	}
	
	@Override
	public int getTotalScore() {
		int score = 0;
		for (Frame frame : frames) {
			score += frame.calculateScore();
		}
		return score;
	}
	
	@Override
	public boolean isComplete() {
		return frames.peekLast() instanceof FinalFrame && frames.peekLast().isComplete();
	}
	
	private class Frame {
		
		protected Integer throw1Score;
		protected Integer throw2Score;
		private final int frameNumber;
		private Frame nextFrame;
		
		
		public Frame(int frameNumber) {
			this.frameNumber = frameNumber;
		}
		
		public void recordPins(int pins) {
			if (throw1Score == null) {
				throw1Score = pins;
			} else if (throw2Score == null) {
				throw2Score = pins;
			}
		}
		
		public int calculateScore() {
			int score = getThrow1Score() + getThrow2Score();
			if (isSpare() && nextFrame != null) {
				score += nextFrame.getThrow1Score();
			} else if (isStrike() && nextFrame != null) {
				score += nextFrame.getThrow1Score() + nextFrame.getThrow2Score();
				if (nextFrame.isStrike() && nextFrame.nextFrame != null) {
					score += nextFrame.nextFrame.getThrow1Score();
				}
			}
			return score;
		}
		
		public boolean isComplete() {
			return isOpen() || isSpare() || isStrike();
		}
		
		public boolean isOpen() {
			return (throw1Score != null && throw2Score != null) && throw1Score + throw2Score < 10;
		}
		
		public boolean isSpare() {
			return getThrow1Score() + getThrow2Score() == 10 && getThrow2Score() > 0;
		}
		
		public boolean isStrike() {
			return getThrow1Score() == 10;
		}
		
		public int getFrameNumber() {
			return frameNumber;
		}
		
		public void setNextFrame(Frame nextFrame) {
			this.nextFrame = nextFrame;
		}
		
		protected int getThrow1Score() {
			return throw1Score == null ? 0 : throw1Score;
		}
		
		protected int getThrow2Score() {
			return throw2Score == null ? 0 : throw2Score;
		}
	}
	
	private class FinalFrame extends Frame {
		
		private Integer throw3Score;
		
		public FinalFrame(int frameNumber) {
			super(frameNumber);
		}
		
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
		
		@Override
		public int calculateScore() {
			return getThrow1Score() + getThrow2Score() + getThrow3Score();
		}
		
		@Override
		public boolean isComplete() {
			if (throw2Score == null) {
				return false;
			}
			if (throw3Score == null) {
				if (getThrow1Score() + getThrow2Score() == 10) {
					return false;
				} else if (getThrow1Score() == 10 && getThrow2Score() == 10) {
					return false;
				}
			}
			return true;
		}
		
		private int getThrow3Score() {
			return throw3Score == null ? 0 : throw3Score;
		}
		
	}



}
