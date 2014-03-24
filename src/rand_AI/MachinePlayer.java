package rand_AI;

import board.Board;
import list.LinkedList;
import player.*;
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
		//An AI that plays randomly
		LinkedList<Move> possibleMoves = board.getValidMoves(color);
		System.out.println("Possible moves: " + possibleMoves);
		int numMoves = possibleMoves.size();
		int i = (int) (Math.random()*(numMoves));
		Move m = possibleMoves.get(i);
		board.makeMove(m, color);
		return m;	
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
		if(color == Board.WHITE)
			return Board.BLACK;
		return Board.WHITE;
	}

}
