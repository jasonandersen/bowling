package bowling;

/**
 * Displays the score of a bowling game using ASCII characters.
 * 
 * @author Jason Andersen andersen.jason@gmail.com
 *
 */
public class BowlingGameAsciiDisplay {
	
	private final BowlingGame game;
	
	private static final String DISPLAY_FORMAT = "[Frame %d] The current score is %d.%n";
	
	/**
	 * Constructor
	 * @param game
	 */
	public BowlingGameAsciiDisplay(BowlingGame game) {
		this.game = game;
	}
	
	/**
	 * Displays
	 */
	public String toString() {
		return String.format(DISPLAY_FORMAT, game.getCurrentFrameNumber(), game.getTotalScore());
	}
}
