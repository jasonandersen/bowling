/**
 * 
 */
package bowling.cli;

import java.io.Console;
import java.io.IOException;

import bowling.BowlingGame;
import bowling.BowlingGameImpl;

/**
 * An entry point into our application with a command line interface.
 * 
 * @author Jason Andersen(andersen.jason@gmail.com)
 */
public class CommandLineMain {

	/**
	 * Entry point method
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BowlingGame game = new BowlingGameImpl();
		while (!game.isComplete()) {
			String scoreInput = readInput("How many pins did you knock down?\n");
			registerPins(game, scoreInput);
			writeGameScore(game);
		}
	}
	
	/**
	 * Register the number pins knocked down as input from the user
	 * @param game
	 * @param scoreInput
	 */
	private static void registerPins(BowlingGame game, String scoreInput) {
		try {
			game.throwBall(Integer.parseInt(scoreInput));
		} catch (NumberFormatException e) {
			writeOutput(String.format("\"%s\" is not a valid number. Please try again.", scoreInput));
		} catch (IllegalArgumentException e) {
			writeOutput(String.format("\"%s\" is not a valid score - %s", scoreInput, e.getMessage()));
		}
		
	}
	
	/**
	 * Write the game score out to the console
	 * @param game
	 */
	private static void writeGameScore(BowlingGame game) {
		BowlingGameAsciiDisplay display = new BowlingGameAsciiDisplay(game);
		writeOutput(display.toString());
	}
	
	/**
	 * Read input in from the console from the user
	 * @param format
	 * @return
	 */
	private static String readInput(String format) {
		Console console = System.console();
		String input = console.readLine(format);
		return input;
	}
	
	/**
	 * Write output to the console
	 * @param output
	 */
	private static void writeOutput(Object output) {
		Console console = System.console();
		console.printf("%s\n", output);
	}

}
