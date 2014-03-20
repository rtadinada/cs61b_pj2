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
	
	public static final int MAXSCORE = 1000;
	public static final int MINSCORE = -1000;
	
	/**
	 * Scores the specified board for the specified color and returns an int
	 * on the scale from MAXSCORE to MINSCORE, where MAXSCORE specifies the player of
	 * the specified color winning and MINSCORE, losing. Values in between are
	 * determined by heuristics. Color is Board.BLACK for black and
	 * Board.WHITE for white.
	 * 
	 * @param b			the board to score
	 * @param color		the color of the player to score
	 * @return	an integer from MINSCORE to MAXSCORE specifying the score of the board
	 */
	static int getScore(Board b, int color) {
		return Integer.MIN_VALUE;
	}
	
}
