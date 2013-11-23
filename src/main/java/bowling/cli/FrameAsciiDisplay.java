package bowling.cli;


import java.util.Arrays;

import bowling.Frame;

/**
 * Displays a single bowling frame using ASCII characters in the following format:
 * <pre>
 *   ---------
 *   |   1   |
 *   |-------|
 *   | 5 | / |
 *   ---------
 * </pre>
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public class FrameAsciiDisplay {
	
	private static final int CELL_HEIGHT = 5;
	
	private static final int CELL_WIDTH = 9;
	
	private final Frame frame;
	
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
		display[0] = buildOuterBorder();
		display[1] = buildFrameNumber();
		display[2] = buildInnerBorder();
		display[3] = buildScores();
		display[4] = buildOuterBorder();
		return display;
	}
	
	/**
	 * @return the width of the cell
	 */
	protected int getCellWidth() {
		return CELL_WIDTH;
	}
	
	/**
	 * @return an array filled with the displayer border
	 */
	private char[] buildOuterBorder() {
		char[] border = new char[getCellWidth()];
		Arrays.fill(border, '-');
		return border;
	}
	
	/**
	 * @return an array containing the frame number
	 */
	private char[] buildFrameNumber() {
		char[] frameNumber = new char[getCellWidth()];
		Arrays.fill(frameNumber, ' ');
		frameNumber[0] = '|';
		frameNumber[4] = getFrameNumber();
		frameNumber[8] = '|';
		return frameNumber;
	}
	
	/**
	 * @return an array containing the inner border
	 */
	private char[] buildInnerBorder() {
		char[] border = new char[getCellWidth()];
		Arrays.fill(border, '-');
		border[0] = '|';
		border[border.length - 1] = '|';
		return border;
	}
	
	/**
	 * @return an array containing the frame scores
	 */
	private char[] buildScores() {
		char[] scores = new char[getCellWidth()];
		Arrays.fill(scores, '|');
		
		scores[1] = ' ';
		scores[2] = getThrow1Score();
		scores[3] = ' ';
		scores[4] = ':';
		scores[5] = ' ';
		scores[6] = getThrow2Score();
		scores[7] = ' ';
		
		return scores;
	}

	/**
	 * @return the frame number, will return a space if the frame is null
	 */
	private char getFrameNumber() {
		if (frame == null) {
			return ' ';
		}
		return renderInteger(frame.getFrameNumber());
	}
	
	/**
	 * @return the first throw of the frame, will return a space if the frame is null or the first throw is null
	 */
	private char getThrow1Score() {
		if (frame == null) {
			return ' ';
		}
		if (frame.getThrow1() == null) {
			return ' ';
		}
		if (frame.isStrike()) {
			return 'X';
		}
		if (frame.getThrow1() == 0) {
			return '-';
		}
		return renderInteger(frame.getThrow1().intValue());
	}
	
	/**
	 * @return the second throw of the frame, will return a space if the frame is null or the second throw is null
	 */
	private char getThrow2Score() {
		if (frame == null) {
			return ' ';
		}
		if (frame.getThrow2() == null) {
			return ' ';
		}
		if (frame.isSpare()) {
			return '/';
		}
		if (frame.getThrow2() == 0) {
			return '-';
		}
		return renderInteger(frame.getThrow2().intValue());
	}
	
	/**
	 * @param integer
	 * @return converts an integer into a single character
	 */
	private char renderInteger(Integer integer) {
		if (integer == null) {
			return ' ';
		}
		char[] chars = integer.toString().toCharArray();
		return chars[0];
	}
	

}
