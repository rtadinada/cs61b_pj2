package player;
import util.LinkedList;
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

	private static IntHashMap<BoardColor> scoreCache = new IntHashMap<BoardColor>(1500);

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
		BoardColor bc = new BoardColor(b, color);
		if(scoreCache.containsKey(bc))
			return scoreCache.get(bc);

		//Score based on the difference between your max partial network size and theirs
		LinkedList<Integer> myNetworks = b.getNetworkSizes(color);
		LinkedList<Integer> oppNetworks = b.getNetworkSizes(-color);		
		int score = 50*(10 - getMax(oppNetworks));
		score += 10*getMax(myNetworks);
		scoreCache.put(bc, score);
		return score;
	}
	/**
	 * Returns the size of the largest partial network
	 * 
	 * @param lst list of partial network sizes
	 * @return
	 */
	private static int getMax(LinkedList<Integer> lst) {
		//Max of a list of positive numbers
		int max = -1;
		for (int i: lst) 
		{
			if (i > max)
			{
				max = i;
			}
		}
		return max;
	}

	/**
	 * Determines if a board has already been scored for a color
	 * @param b 	Board to check
	 * @param color	color of the score being checked
	 * @return
	 */
	static boolean hasScore(Board b, int color) 
	{
		return scoreCache.containsKey(new BoardColor(b, color));
	}

	/**
	 * Inserts the score for the Board b for the color color into the list of scored boards
	 * @param b 		Board being stored
	 * @param color		Color the board was evaluated for
	 * @param score		Score of the board evaluated
	 */
	static void addScore(Board b, int color, int score) 
	{
		scoreCache.put(new BoardColor(b, color), score);
	}

	/**
	 * clears the cache of scored boards. 
	 */
	
	static void clearCache() 
	{
		scoreCache = new IntHashMap<BoardColor>(1500);
	}

	/**
	 * Remembers what color a board was evaluated for 
	 * @author User
	 *
	 */
	private static class BoardColor 
	{

		Board b;
		int color;

		BoardColor(Board b, int color) 
		{
			this.b = b;
			this.color = color;
		}

		public int hashCode() 
		{
			return b.hashCode()*3 + color;
		}

		public boolean equals(Object o) {
			return b.equals(((BoardColor)o).b) && color == ((BoardColor)o).color;
		}

	}

}
