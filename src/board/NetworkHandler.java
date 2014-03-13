package board;

import player.Move;
import list.LinkedList;

/**
 * The NetworkHandler class handles finding completed networks, partial
 * networks, and network stats for scoring.
 * 
 * @author ________
 */
class NetworkHandler {

	/**
	 * Creates a new empty NetworkHandler.
	 */
	NetworkHandler() {
		
	}
	
	/**
	 * Updates internal configuration with the specified move for the
	 * specified color. Assumes that <code>m</code> is a valid move. Uses color
	 * as specified in Board.WHITE and Board.BLACK (assume the same for other
	 * methods).
	 * 
	 * @param m			Move to update with
	 * @param color		color of the player who made the move
	 */
	void makeMove(Move m, int color) {
		
	}
	
	/**
	 * Updates the internal configuration to remove the specified move for the
	 * specified color. Assumes that <code>m</code> was the last move and made
	 * by the specified player.
	 * 
	 * @param m			Move to remove
	 * @param color		color of the player who made the move
	 */
	void undoMove(Move m, int color) {
		
	}
	
	/**
	 * Returns true if the player of the specified color has a fully formed
	 * network (as defined in the README) and false otherwise.
	 * 
	 * @param color		color of the player
	 * @return	true if a network for the specified player exists, false otherwise
	 */
	boolean hasNetwork(int color) {
		return false;
	}
	
	/**
	 * Returns a <code>LinkedList</code> of the sizes of all the subnetworks
	 * for the specified color.
	 * 
	 * @param color		color of the player
	 * @return	a list of the sizes of all the subnetworks.
	 */
	LinkedList<Integer> getNetworkSizes(int color) {
		return null;
	}
	
	/**
	 * Returns the number of connections between two pieces of the specified
	 * color on the board
	 * 
	 * @param color		color of the player
	 * @return	the number of connections of the color color
	 */
	int getNumConnections(int color) {
		return -1;
	}
	
}
