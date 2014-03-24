package player;

import board.Board;
import list.LinkedList;
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
		int maxDepth = searchDepth;
		if(maxDepth == VARDEPTH) {
			if(board.getNumPieces(color) == 10)		// Step move
				maxDepth = 3;
			else									// Add move
				maxDepth = 5;
		}
		
		Move m = chooseMove(maxDepth, color).move;
		board.makeMove(m, color);
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
	private ScoreMove chooseMove(int depth, int color) {
		Move bestMove = new Move();
		int bestScore = Integer.MIN_VALUE;
		LinkedList<Move> validMoves = board.getValidMoves(color);
		System.out.println("Valid moves for this color are " + validMoves);
		for(Move move : board.getValidMoves(color)) {
			int moveScore = getScore(move, depth, color);
			if(bestScore == Integer.MIN_VALUE || 
					(this.color == color && moveScore > bestScore) ||					// This player's move (max score)
					(oppositeColor(this.color) == color && moveScore < bestScore)) {	// Other player's move (min score)
				bestMove = move;
				bestScore = moveScore;
			}
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
	private int getScore(Move m, int depth, int color) {
		board.makeMove(m, color);
		depth--;
		
		int score = 0;
		if(board.hasNetwork(oppositeColor(this.color)))
			score = Scorer.MINSCORE;
		else if(board.hasNetwork(this.color))
			score = Scorer.MAXSCORE;
		else if(depth == 0)
			score = Scorer.getScore(board, this.color);
		else {
			score = chooseMove(depth, oppositeColor(color)).score;
		}
		
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

}
