package player;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 *  
 *  @author ________
 */
public class MachinePlayer extends Player {

	/**
	 * Constructs a machine player of specified color and variable search
	 * depth. Color is either Board.BLACK (0) or Board.WHITE (1). White
	 * has the first move.
	 * 
	 * @param color		the color of this MachinePlayer; 0 black, 1 white
	 */
	public MachinePlayer(int color) {
		
	}

	/**
	 * Constructs a machine player of specified color and search depth. Color
	 * is either Board.BLACK (0) or Board.WHITE (1). White has the first move.
	 * 
	 * @param color			the color of this MachinePlayer; 0 black, 1 white
	 * @param searchDepth	the maximum search depth for <code>chooseMove</code>
	 */
	public MachinePlayer(int color, int searchDepth) {
		
	}

	
	/**
	 * Returns a new <code>Move</code> by this player.  Internally records the
	 * move (update the internal game board) as a move by this player.
	 * 
	 * @return	the Move chosen by this player
	 */
	public Move chooseMove() {
		return new Move();
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
		return false;
	}

	/**
	 * Forces this player to make the specified move if legal and returns true.
	 * If the move is not legal, returns false and does not record the move.
	 * 
	 * @param m		the Move forced to make
	 * @return	true if legal move, false otherwise
	 */
	public boolean forceMove(Move m) {
		return false;
	}

}
