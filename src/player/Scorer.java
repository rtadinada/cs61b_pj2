package player;

import board.Board;

/**
 * The Scorer class provides a static method for scoring a given board for a
 * specified color.
 * 
 * @author ________
 *
 */
class Scorer {
	
	/**
	 * Scores the specified board for the specified color and returns an int
	 * on the scale from -1000 to 1000, where 1000 specifies the player of
	 * the specified color winning and -1000, losing. Values in between are
	 * determined by heuristics. Color is Board.BLACK for black and
	 * Board.WHITE for white.
	 * 
	 * @param b			the board to score
	 * @param color		the color of the player to score
	 * @return	an integer from -1000 to 1000 specifying the score of the board
	 */
	static int getScore(Board b, int color) {
		return Integer.MIN_VALUE;
	}
	
}
