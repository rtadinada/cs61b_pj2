package player;

import util.LinkedList;
import board.Board;
/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 *  
 *  @author ________
 */
public class MachinePlayer extends Player {

	//Instance Fields
	private Board board;
	private int color;
	private int searchDepth;
	private static final int VARDEPTH = -1;	// Set searchDepth to -1 for variable case
	
	/**
	 * Constructs a machine player of specified color and variable search
	 * depth. Color is either Board.BLACK (0) or Board.WHITE (1). White
	 * has the first move.
	 * 
	 * @param color		the color of this MachinePlayer; 0 black, 1 white
	 */
	public MachinePlayer(int color) {
		this(color, VARDEPTH);
	}

	/**
	 * Constructs a machine player of specified color and search depth. Color
	 * is either Board.BLACK (0) or Board.WHITE (1). White has the first move.
	 * 
	 * @param color			the color of this MachinePlayer; 0 black, 1 white
	 * @param searchDepth	the maximum search depth for <code>chooseMove</code>
	 */
	public MachinePlayer(int color, int searchDepth) {
		if (color == 0) {
			this.color = Board.BLACK;
		} else {
			this.color = Board.WHITE;
		}		
		this.searchDepth = searchDepth;
		this.board = new Board();
	}

	
	/**
	 * Returns a new <code>Move</code> by this player.  Internally records the
	 * move (update the internal game board) as a move by this player.
	 * 
	 * @return	the Move chosen by this player
	 */
	public Move chooseMove() {
		long start = System.currentTimeMillis();
		int maxDepth = searchDepth;
		if(maxDepth == VARDEPTH) {
			if(board.getNumPieces(color) > 8)		// Step move
				maxDepth = 3;
			else									// Add move
				maxDepth = 5;
		}
		Move m;
		if(board.getNumPieces(color) == 0) { 		// First move starts in center
			m = new Move(3,3);
			if(!board.isValidMove(m, color))
				m = new Move(3, 4);
		}
		else {
			m = chooseMove(maxDepth, color, Scorer.MINSCORE, Scorer.MAXSCORE).move;
			Scorer.clearCache();
		}
		board.makeMove(m, color);
		double seconds = (System.currentTimeMillis() - start)/1000d;
		// System.out.println("Move chosen in " + seconds + " seconds.");
		return m;
	}
	
	/**
	 * Returns the move with the best score for the selected player, doing a
	 * recursive search to the specified depth. Returns an object that holds
	 * the move and its score
	 * 
	 * @param depth		the max depth to search to
	 * @param color		the color whose score to maximize
	 * @return	the highest scoring move and score
	 */
	private ScoreMove chooseMove(int depth, int color, int a, int b) {
		Move bestMove = new Move();
		int bestScore = 0;
		if(color == this.color)
			bestScore = a;
		else
			bestScore = b;
		
		boolean first = true;
		for(Move move : board.getValidMoves(color)) {
			int moveScore = getScore(move, depth, color, a, b);
			if(first || 
					(this.color == color && moveScore > bestScore) ||					// This player's move (max score)
					(oppositeColor(this.color) == color && moveScore < bestScore)) {	// Other player's move (min score)
				bestMove = move;
				bestScore = moveScore;
				if(this.color == color)
					a = moveScore;
				else
					b = moveScore;
				first = false;
			}
			if(a >= b)
				break;
		}
		return new ScoreMove(bestScore, bestMove);
	}
	
	/**
	 * Returns the score of the given move made by the given color based upon
	 * searches to the specified depth.
	 * 
	 * @param m			the move to score
	 * @param depth		the max depth to search to
	 * @param color		the player who makes the move
	 * @return	the score of the move
	 */
	private int getScore(Move m, int depth, int color, int a, int b) {
		board.makeMove(m, color);
		depth--;		
		
		int score = 0;
		if(board.hasNetwork(oppositeColor(this.color))) {
			// System.out.println("\n\nFound losing board\n" + board);
			score = Scorer.MINSCORE - 10*depth;
		}
		else if(board.hasNetwork(this.color)) {
			// System.out.println("\n\nFound winning board\n" + board);
			score = Scorer.MAXSCORE + 10*depth;
		}
		else if(depth == 0 || Scorer.hasScore(board, color)) {
			score = Scorer.getScore(board, this.color);
		}
		else {
			score = chooseMove(depth, oppositeColor(color), a, b).score;
			Scorer.addScore(board, color, score);
		}
		//System.out.println("The score for this board is " + score);
		board.rollback();
		return score;

	}


	/**
	 * Records the specified move as a move by the opponent if it is legal and
	 * returns true. If the move is not legal, returns false and does not
	 * record the move.
	 * 
	 * @param m		the Move provided by the opponent
	 * @return	true if legal move, false otherwise
	 */
	public boolean opponentMove(Move m) {
		if(!board.isValidMove(m, oppositeColor(color)))
			return false;
		board.makeMove(m, oppositeColor(color));
		return true;
	}

	/**
	 * Forces this player to make the specified move if legal and returns true.
	 * If the move is not legal, returns false and does not record the move.
	 * 
	 * @param m		the Move forced to make
	 * @return	true if legal move, false otherwise
	 */
	public boolean forceMove(Move m) {
		if(!board.isValidMove(m, color))
			return false;
		board.makeMove(m, color);
		return true;
	}
	
	/**
	 * Returns the opposite of the supplied color. (i.e. white -> black and
	 * black -> white)
	 * 
	 * @param color		color to reverse
	 * @return	reversed color
	 */
	private int oppositeColor(int color) {
		if(color == Board.BLACK)
			return Board.WHITE;
		return Board.BLACK;
	}
	
	public static void main(String[] args) {
		
	}

}
