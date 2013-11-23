package bowling.cli;

/**
 * Displays a single cell of information using ASCII characters.
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public interface AsciiCellDisplay {
	
	/**
	 * @return a display of ASCII characters in a character matrix
	 */
	public char[][] display();

}
