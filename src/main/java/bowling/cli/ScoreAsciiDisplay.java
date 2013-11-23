package bowling.cli;

import bowling.BowlingGame;

/**
 * Displays the game score using ASCII characters in the following format:
 * <pre>
 *   ---------
 *   | score |
 *   |-------|
 *   |   221 |
 *   ---------
 * </pre>
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public class ScoreAsciiDisplay implements AsciiCellDisplay {
	
	private static final int CELL_WIDTH = 9;
	
	private static final String SCORE_FORMAT = "| %-5d |";
	
	private final BowlingGame game;
	
	/**
	 * Constructor
	 * @param game
	 */
	public ScoreAsciiDisplay(BowlingGame game) {
		this.game = game;
	}

	/*
	 * @see bowling.cli.AsciiCellDisplay#display()
	 */
	public char[][] display() {
		char[][] out = new char[5][CELL_WIDTH]; //TODO remove that magic number
		
		out[0] = "---------".toCharArray();
		out[1] = "| score |".toCharArray();
		out[2] = "|-------|".toCharArray();
		out[3] = String.format(SCORE_FORMAT, game.getTotalScore()).toCharArray();
		out[4] = "---------".toCharArray();

		return out;
	}
	
	
}
