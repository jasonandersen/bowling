package bowling.cli;

import bowling.BowlingGame;
import bowling.FinalFrame;

/**
 * Displays a single bowling frame using ASCII characters in the following format:
 * <pre>
 *   -------------
 *   |     10    |
 *   |-----------|
 *   | 5 : / : 8 |
 *   -------------
 * </pre>
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public class FinalFrameAsciiDisplay extends FrameAsciiDisplay {
	
	private static final String FRAME_NUM_FORMAT = "|     %-2s    |";
	
	private static final String SCORES_FORMAT = "| %s : %s : %s |";
	
	/**
	 * Constructor
	 * @param frame
	 */
	public FinalFrameAsciiDisplay(FinalFrame frame) {
		super(frame);
	}
	
	/**
	 * @return the display for this 
	 */
	@Override
	public char[][] display() {
		char[][] display = new char[CELL_HEIGHT][getCellWidth()];
		display[0] = "-------------".toCharArray();
		display[1] = String.format(FRAME_NUM_FORMAT, getFrameNumber()).toCharArray();
		display[2] = "|-----------|".toCharArray();
		display[3] = String.format(SCORES_FORMAT, getThrow1Score(), getThrow2Score(), getThrow3Score()).toCharArray();
		display[4] = "-------------".toCharArray();
		return display;
	}
	
	/**
	 * @return the second throw of the frame, will return a space if the frame is null or the second throw is null
	 */
	@Override
	protected String getThrow2Score() {
		if (frame == null) {
			return " ";
		}
		if (frame.getThrow2() == null) {
			return " ";
		}
		if (frame.isStrike() && frame.getThrow2() == BowlingGame.TOTAL_PINS) {
			return "X";
		}
		return super.getThrow2Score();
	}
	
	/**
	 * @return the second throw of the frame, will return a space if the frame is null or the third throw is null
	 */
	private String getThrow3Score() {
		FinalFrame finalFrame = getFinalFrame();
		if (finalFrame == null) {
			return " ";
		}
		if (finalFrame.getThrow3() == null) {
			return " ";
		}
		if (finalFrame.getThrow3() == BowlingGame.TOTAL_PINS) {
			return "X";
		}
		if (finalFrame.getThrow3() == 0) {
			return "-";
		}
		return finalFrame.getThrow3().toString();
	}
	
	/**
	 * @return frame casted into a final frame
	 */
	private FinalFrame getFinalFrame() {
		return frame == null ? null : (FinalFrame) super.frame;
	}
	
}
