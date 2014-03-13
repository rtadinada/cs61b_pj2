package board;

import player.Move;
import list.LinkedList;

/**
 * The Board class holds the game state and provides useful information about
 * the current configuration.
 * 
 * @author ________
 */
public class Board {
	
	// Color representation
	public static final int BLACK = 0;
	public static final int WHITE = 1;

	/**
	 * Constructs an empty Board.
	 */
	public Board() {
		
	}
	
	/**
	 * Updates the board's configuration with the supplied move made by the
	 * supplied color. Assumes that <code>m</code> is a valid move.
	 * 
	 * @param m			Move to update board with
	 * @param color		color of the player who made the move
	 */
	public void makeMove(Move m, int color) {
		
	}
	
	
	/**
	 * Rolls the board back one move.
	 */
	public void rollback() {
		
	}
	
	/**
	 * Returns the number of pieces on the board for the specified color.
	 * 
	 * @param color		color of the player
	 * @return	the number of pieces of the specified color
	 */
	public int getNumPieces(int color) {
		return -1;
	}
	
	
	/**
	 * Returns true if the player of the specified color has a fully formed
	 * network (as defined in the README) and false otherwise.
	 * 
	 * @param color		color of the player
	 * @return	true if a network for the specified player exists, false otherwise
	 */
	public boolean hasNetwork(int color) {
		return false;
	}
	
	/**
	 * Returns a <code>LinkedList</code> of the sizes of all the subnetworks
	 * for the specified color.
	 * 
	 * @param color		color of the player
	 * @return	a list of the sizes of all the subnetworks.
	 */
	public LinkedList<Integer> getNetworkSizes(int color) {
		return null;
	}
	
	/**
	 * Returns the number of connections between two pieces of the specified
	 * color on the board
	 * 
	 * @param color		color of the player
	 * @return	the number of connections of the color color
	 */
	public int getNumConnections(int color) {
		return -1;
	}
	
	/**
	 * Returns true if m is a valid move for the specified color, false
	 * otherwise.
	 * 
	 * @param m			the move to test
	 * @param color		color of the player
	 * @return	true if valid move, false otherwise
	 */
	public boolean isValidMove(Move m, int color) {
		return false;
	}
	
	/**
	 * Provides a <code>LinkedList</code> of all the valide possible moves for
	 * the given player.
	 * 
	 * @param color		color of the player
	 * @return	a list of all the valid moves
	 */
	public LinkedList<Move> getValidMoves(int color) {
		return null;
	}
}
