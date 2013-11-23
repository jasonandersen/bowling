package bowling.cli;


import bowling.Frame;

/**
 * Displays a single bowling frame using ASCII characters in the following format:
 * <pre>
 *   ---------
 *   |   1   |
 *   |-------|
 *   | 5 : / |
 *   ---------
 * </pre>
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public class FrameAsciiDisplay implements AsciiCellDisplay {
	
	protected static final int CELL_HEIGHT = 5;
	
	private static final int CELL_WIDTH = 9;
	
	private static final String FRAME_NUM_FORMAT = "|   %-2s  |";
	
	private static final String SCORES_FORMAT = "| %s : %s |";
	
	protected final Frame frame;
	
	/**
	 * Constructor
	 * @param frame
	 */
	public FrameAsciiDisplay(Frame frame) {
		//NOTE: frame can be null
		this.frame = frame;
	}
	
	/**
	 * @return the display for this 
	 */
	public char[][] display() {
		char[][] display = new char[CELL_HEIGHT][getCellWidth()];
		display[0] = "---------".toCharArray();
		display[1] = String.format(FRAME_NUM_FORMAT, getFrameNumber()).toCharArray();
		display[2] = "|-------|".toCharArray();
		display[3] = String.format(SCORES_FORMAT, getThrow1Score(), getThrow2Score()).toCharArray();
		display[4] = "---------".toCharArray();
		return display;
	}
	
	/**
	 * @return the width of the cell
	 */
	protected int getCellWidth() {
		return CELL_WIDTH;
	}
	
	/**
	 * @return the number of the frame, will return a space if the frame is null
	 */
	protected String getFrameNumber() {
		if (frame == null) {
			return " ";
		}
		return frame.getFrameNumber() + "";
	}
	
	/**
	 * @return the first throw of the frame, will return a space if the frame is null or the first throw is null
	 */
	protected String getThrow1Score() {
		if (frame == null) {
			return " ";
		}
		if (frame.getThrow1() == null) {
			return " ";
		}
		if (frame.isStrike()) {
			return "X";
		}
		if (frame.getThrow1() == 0) {
			return "-";
		}
		return frame.getThrow1().toString();
	}
	
	/**
	 * @return the second throw of the frame, will return a space if the frame is null or the second throw is null
	 */
	protected String getThrow2Score() {
		if (frame == null) {
			return " ";
		}
		if (frame.getThrow2() == null) {
			return " ";
		}
		if (frame.isSpare()) {
			return "/";
		}
		if (frame.getThrow2() == 0) {
			return "-";
		}
		return frame.getThrow2().toString();
	}

}
