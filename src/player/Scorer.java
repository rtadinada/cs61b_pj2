package player;

import util.IntHashMap;
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
	
	private static IntHashMap<Board> scoreCache = new IntHashMap<>();
	
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
		if(scoreCache.containsKey(b))
			return scoreCache.get(b);
		int score = Integer.MIN_VALUE;
		
		
		scoreCache.put(b, score);
		return score;
	}
	
	static boolean hasScore(Board b) {
		return scoreCache.containsKey(b);
	}
	
	static void clearCache() {
		scoreCache = new IntHashMap<>();
	}
	
}
