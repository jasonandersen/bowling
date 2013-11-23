package bowling.cli;

import java.util.ArrayList;
import java.util.List;

import bowling.BowlingGame;
import bowling.FinalFrame;
import bowling.Frame;

/**
 * Displays the score of a bowling game using ASCII characters.
 * 
 * @author Jason Andersen andersen.jason@gmail.com
 */
public class BowlingGameAsciiDisplay {
	
	private final BowlingGame game;
	
	/**
	 * Constructor
	 * @param game
	 */
	public BowlingGameAsciiDisplay(BowlingGame game) {
		this.game = game;
	}
	
	/**
	 * Displays the current state of the game in ASCII characters
	 */
	@Override
	public String toString() {
		List<char[][]> displayMatrix = buildFrameMatrix();
		StringBuilder output = renderDisplayMatrix(displayMatrix);
		return output.toString();
	}
	
	/**
	 * @return retrieves a display matrix for each frame and combines them in a list
	 */
	private List<char[][]> buildFrameMatrix() {
		List<char[][]> matrix = new ArrayList<char[][]>();
		matrix.addAll(buildExistingFramesMatrix());		
		matrix.addAll(buildBlankFramesMatrix(matrix.size()));
		matrix.add(buildScoreMatrix());
		return matrix;
	}
	
	/**
	 * @return a display matrix for existing frames
	 */
	private List<char[][]> buildExistingFramesMatrix() {
		List<char[][]> matrix = new ArrayList<char[][]>();
		for (Frame frame : game.getFrames()) {
			FrameAsciiDisplay display;
			if (frame instanceof FinalFrame) {
				display = new FinalFrameAsciiDisplay((FinalFrame)frame);
			} else {
				display = new FrameAsciiDisplay(frame);
			}
			matrix.add(display.display());
		}
		return matrix;
	}
	
	/**
	 * @param startingFrameIndex
	 * @return a display matrix for any unplayed frames
	 */
	private List<char[][]> buildBlankFramesMatrix(int startingFrameIndex) {
		List<char[][]> matrix = new ArrayList<char[][]>();
		for (int index = startingFrameIndex; index < BowlingGame.MAX_FRAMES; index++) {
			FrameAsciiDisplay display;
			if (index < BowlingGame.MAX_FRAMES - 1) {
				display = new FrameAsciiDisplay(null); //add regular frame
			} else {
				display = new FinalFrameAsciiDisplay(null); //add final frame
			}
			matrix.add(display.display());
		}
		return matrix;
	}
	
	/**
	 * @return a display matrix for the score box at the end of the game
	 */
	private char[][] buildScoreMatrix() {
		ScoreAsciiDisplay score = new ScoreAsciiDisplay(game);
		return score.display();
	}
	
	/**
	 * @param matrix
	 * @return a StringBuilder containing all of the frames in a horizontal display of ASCII characters suitable
	 * 		for rendering in a fixed-width display
	 */
	private StringBuilder renderDisplayMatrix(List<char[][]> matrix) {
		StringBuilder out = new StringBuilder();
		int displayHeight = matrix.iterator().next().length;
		for (int row = 0; row < displayHeight; row++) {
			for (char[][] frame : matrix) {
				out.append(frame[row]);
			}
			out.append("\n");
		}
		
		return out;
	}
}
