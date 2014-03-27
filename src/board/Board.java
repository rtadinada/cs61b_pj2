package board;

import player.Move;
import util.LinkedList;

/**
 * The Board class holds the game state and provides useful information about
 * the current configuration.
 * 
 * @author ________
 */
public class Board {
	
	// Color representation
	public static final int BLACK = -1;
	public static final int WHITE = 1;
	int numBlack;
	int numWhite;
	int[][] board;
	LinkedList<Move> moves;
	NetworkHandler networks = new NetworkHandler();

	/**
	 * Constructs an empty Board.
	 */
	public Board() {
		board = new int[8][8];
		numBlack = 0;
		numWhite = 0;
		moves = new LinkedList<Move>();
	}

	/**
	* Constructs a board from an array,
	* solely for the purposes of testing.
	*/

	private Board(int[][] b) {
		board = new int[8][8];
		numBlack = 0;
		numWhite = 0;
		moves = new LinkedList<Move>();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				int tile = b[y][x];
				if (tile == BLACK) {
					numBlack ++;
				} else if (tile == WHITE) {
					numWhite ++;
				}
				board[y][x] = tile;
			}
		}
	}

	/**
	* String representation:
	*/

	public String toString() {
		String str = "";
		for (int y = 0; y < 8; y++) {
			str += "\n --- --- --- --- --- --- --- --- \n";
			for (int x = 0; x < 8; x++) {
				if (x == 0) {
					str += "|";
				}
				int tile = board[y][x];
				if (tile == BLACK) {
					str += " B |";
				} else if (tile == WHITE) {
					str += " W |";
				} else if (inCorner(x, y)) {
					str += " X |";
				} else {
					str += "   |";
				}
			}
		}
		str += "\n --- --- --- --- --- --- --- --- \n";
		return str;
	}
	
	public boolean equals(Object o) {
		Board b = (Board)o;
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				if(board[y][x] != b.board[y][x])
					return false;
			}
		}
		return true;
	}
	
	public int hashCode() {
		int hashCode = 0;
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++)
				hashCode += 3*hashCode + board[y][x] + 1;
		}
		return hashCode;
	}
	
	/**
	 * Updates the board's configuration with the supplied move made by the
	 * supplied color. Assumes that <code>m</code> is a valid move.
	 * 
	 * @param m			Move to update board with
	 * @param color		color of the player who made the move
	 */
	public void makeMove(Move m, int color) {
		if (m.moveKind == Move.QUIT) {
			return;
		}
		if (m.moveKind == Move.STEP) {
			board[m.y2][m.x2] = 0;
		} 
		board[m.y1][m.x1] = color;
		if (m.moveKind == Move.ADD) {
			incNum(color);
		}
		moves.add(m);
		networks.makeMove(m, color);
	}

	private void incNum(int color) {
		// Increments the number of pieces of "color" on board
		if (color == WHITE) {
			numWhite++;
		} else {
			numBlack++;
		}
	}
		
	
	/**
	 * Rolls the board back one move.
	 */
	public void rollback() {
		Move lastMove;
		lastMove = moves.get(moves.size() - 1);		
		if (lastMove.moveKind == Move.QUIT) {
			return;
		}
		

		int pieceColor = board[lastMove.y1][lastMove.x1];
		if (lastMove.moveKind == Move.STEP) {
			board[lastMove.y2][lastMove.x2] = board[lastMove.y1][lastMove.x1];
		}
		if (lastMove.moveKind == Move.ADD) {
			if (pieceColor == BLACK) {
				numBlack--;
			} else {
				numWhite--;
			}
		}
		board[lastMove.y1][lastMove.x1] = 0;
		moves.remove(moves.size() - 1);
		networks.undoMove(lastMove, pieceColor);
	}
	
	/**
	 * Returns the number of pieces on the board for the specified color.
	 * 
	 * @param color		color of the player
	 * @return	the number of pieces of the specified color
	 */
	public int getNumPieces(int color) {
		if (color == BLACK) {
			return numBlack;
		}
		return numWhite;
	}
	
	/**
	 * Returns the number of pieces in the goal of the given color.
	 * 
	 * @param color		color to get number for
	 * @return	number of pieces in goal row/column
	 */
	public int getNumGoalPieces(int color) {
		int num = 0;
		if (color == BLACK) {
			for(int x = 1; x < 7; x++) {
				if(isOccupied(x, 0))
					num++;
				if(isOccupied(x, 7))
					num++;
			}
		}
		else {
			for(int y = 1; y < 7; y++) {
				if(isOccupied(0, y))
					num++;
				if(isOccupied(7, y))
					num++;
			}
		}
		return num;
	}
	
	
	/**
	 * Returns true if the player of the specified color has a fully formed
	 * network (as defined in the README) and false otherwise.
	 * 
	 * @param color		color of the player
	 * @return	true if a network for the specified player exists, false otherwise
	 */
	public boolean hasNetwork(int color) {
		return networks.hasNetwork(color);
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
		if (m.moveKind == Move.QUIT) {
				return true;
		}
		if (m.moveKind == Move.ADD) {
			return isValidAdd(m, color);
		}
		return isValidStep(m, color);	
	}

	/**
	* PRIVATE METHODS TO ASSIST isValidMove
	* The names are fairly self-explanatory
	*/

	private boolean isOccupied(int x, int y) {
		return board[y][x] != 0;
	}

	private boolean inCorner(int x, int y) {
		return (((x == 0) || (x == 7)) && ((y == 0) || (y == 7)));
	}

	private boolean inGoal(int x, int y, int color) {
		if (color == BLACK) {
			return ((y == 0 || y == 7) && !(inCorner(x, y)));
		}
		return ((x == 0 || x == 7) && !(inCorner(x, y)));
	}

	private boolean canMoveFrom(int x, int y, int color) {
		return board[y][x] == color;
	}


	private boolean hasNeighbor(int x, int y, int color) {
		for (int j = Math.max(0, y - 1); j <= Math.min(7, y + 1); j++) {
			for (int i = Math.max(0, x - 1); i <= Math.min(7, x + 1); i++) {
				if (!((i == x) && (j == y)) && board[j][i] == color) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean threeInARow(int x, int y, int color) {
		int numAdj = 0;
		for (int j = Math.max(0, y - 1); j <= Math.min(7, y + 1); j++) {
			for (int i = Math.max(0, x - 1); i <= Math.min(7, x + 1); i++) {
				if (!(i == x && j == y) && (board[j][i] == color)) {
					if (hasNeighbor(i, j, color)) {
						return true;
					}
					numAdj++;				
				}
			}
		}
		return (numAdj > 1);
	}

	/**
	* isValidStep checks for a valid step move
	* it has to temporarily remove the piece to accurately check for 
	* validity
	*/

	private boolean isValidStep(Move m, int color) {
		if (!(getNumPieces(color) == 10)
			|| !(canMoveFrom(m.x2, m.y2, color))
			|| inCorner(m.x1, m.y1)
			|| inGoal(m.x1, m.y1, -color)
			|| isOccupied(m.x1, m.y1)) {
			return false;
		}
		board[m.y2][m.x2] = 0; // Temporarily removes the piece from the place it is moving from
		boolean threeInARow = threeInARow(m.x1, m.y1, color);
		board[m.y2][m.x2] = color; //Sets it back
		return !threeInARow;
	}

	/**
	* isValidAdd checks for a valid add move
	*/

	private boolean isValidAdd(Move m, int color) {
		return ((getNumPieces(color) < 10)
				&& !(isOccupied(m.x1, m.y1))
				&& !(inCorner(m.x1, m.y1))
				&& !(inGoal(m.x1, m.y1, -color))
				&& !(threeInARow(m.x1, m.y1, color)));
	}
	
	/**
	 * Provides a <code>LinkedList</code> of all the possible moves for
	 * the given player.
	 * 
	 * @param color		color of the player
	 * @return	a list of all the valid moves
	 */

	public LinkedList<Move> getValidMoves(int color) {
		if (getNumPieces(color) < 10) {
			return getValidAdd(color);
		}
		return getValidStep(color);
	}

	/**
	* PRIVATE METHODS TO ASSIST getValidMoves
	*/

	private LinkedList<Move> getValidAdd(int color) {
		// Returns a list of all valid add moves on the current board
		LinkedList<Move> moves = new LinkedList<Move>();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				Move m = new Move(x, y);
				if (isValidAdd(m, color)) {
					moves.add(m);
				}
			}
		}
		return moves;
	}

	private LinkedList<Integer> listOfPieces(int color) {
		//Returns a list of coordinates containing a piece of this color
		//coordinates are represented here as a 2-digit number (x, y) --> (x*10 + y)
		LinkedList<Integer> coords = new LinkedList<Integer>();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board[y][x] == color) {
					coords.add(new Integer(10*x + y));
				}
			}
		}
		return coords;
	}

	private LinkedList<Move> getValidStep(int color) {
		// Returns a list of all valid step moves on the current board
		LinkedList<Integer> stepFrom = listOfPieces(color);
		LinkedList<Move> moves = new LinkedList<Move>();
		for (int orig: stepFrom) {
			int x = orig/10;
			int y = orig%10;
			for (int j = 0; j < 8; j++) {
				for (int i = 0; i < 8; i++) {
					Move m = new Move(i, j, x, y);
					if (isValidStep(m, color)) {
						moves.add(m);
					}
				}
			}
		}
		return moves;
	}

	public static void main(String[] args) {
		Board b = new Board();
		System.out.println(b);

		int[][] arr = new int[8][8];
		arr[2][0] = WHITE;
		arr[3][1] = WHITE;
		arr[3][3] = WHITE;
		arr[1][3] = WHITE;
		arr[3][5] = WHITE;
		arr[0][5] = BLACK;
		arr[2][1] = BLACK;
		arr[4][1] = BLACK;
		arr[6][1] = BLACK;
		arr[3][6] = BLACK;
		Board b2 = new Board(arr);
		System.out.println(b2);

		System.out.println("Squares (0, 0), (7, 0), (0, 7) and (7, 7) are all corners: " + b2.inCorner(0, 0) + ", " +  
																						   b2.inCorner(7, 0) + ", " +
																						   b2.inCorner(0, 7) + ", " + 
																						   b2.inCorner(7, 0));

		System.out.println("Square (3, 1) is occupied, should be true: " + b2.isOccupied(3, 1));
		System.out.println("Square (5, 5) is not occupied, should be false: " + b2.isOccupied(5, 5));

		System.out.println("Square (0, 4) is in the white goal, should be true: " + b2.inGoal(0, 4, WHITE));
		System.out.println("Square (0, 4) is not in the black goal, should be false: " + b2.inGoal(0, 4, BLACK));
		System.out.println("Square (5, 0) is in the black goal, should be true: " + b2.inGoal(5, 0, BLACK));
		System.out.println("Square (5, 0) is not in the white goal, should be false: " + b2.inGoal(5, 0, WHITE));

		System.out.println("The number of black pieces is 5: " + b2.getNumPieces(BLACK));
		System.out.println("The number of white pieces is 5: " + b2.getNumPieces(WHITE));

		System.out.println("Square (4, 3) has a white neighbor, should be true: " + b2.hasNeighbor(4, 3, WHITE));
		System.out.println("Square (4, 3) does not have a black neighbor, should be false: " + b2.hasNeighbor(4, 3, BLACK));

		System.out.println("Adding white piece at (0, 4) is not valid, should be false:  " + b2.isValidMove(new Move(0, 4), WHITE));
		System.out.println("Adding white piece at (0, 0) is not valid, should be false:  " + b2.isValidMove(new Move(0, 0), WHITE));
		System.out.println("Adding white piece at (5, 0) is not valid, should be false:  " + b2.isValidMove(new Move(5, 0), WHITE));
		System.out.println("Adding white piece at (6, 3) is not valid, should be false:  " + b2.isValidMove(new Move(6, 3), WHITE));
		System.out.println("Adding white piece at (2, 4) is not valid, should be false:  " + b2.isValidMove(new Move(2, 4), WHITE));

		System.out.println("Adding black piece at (7, 3) is not valid, should be false:  " + b2.isValidMove(new Move(7, 3), BLACK));
		System.out.println("Adding black piece at (0, 7) is not valid, should be false:  " + b2.isValidMove(new Move(0, 7), BLACK));
		System.out.println("Adding black piece at (2, 3) is not valid, should be false:  " + b2.isValidMove(new Move(2, 3), BLACK));

		System.out.println("Adding white piece at (5, 5) is valid, should be true:  " + b2.isValidMove(new Move(5, 5), WHITE));
		System.out.println("Adding white piece at (3, 6) is valid, should be true:  " + b2.isValidMove(new Move(3, 6), WHITE));
		System.out.println("Adding white piece at (6, 6) is valid, should be true:  " + b2.isValidMove(new Move(6, 6), WHITE));
		System.out.println("Adding white piece at (7, 4) is valid, should be true:  " + b2.isValidMove(new Move(7, 4), WHITE));

		System.out.println("Adding black piece at (5, 5) is valid, should be true:  " + b2.isValidMove(new Move(5, 5), BLACK));
		System.out.println("Adding black piece at (3, 4) is valid, should be true:  " + b2.isValidMove(new Move(3, 4), BLACK));
		System.out.println("Adding black piece at (6, 4) is valid, should be true:  " + b2.isValidMove(new Move(6, 4), BLACK));
		System.out.println("Adding black piece at (4, 7) is valid, should be true:  " + b2.isValidMove(new Move(4, 7), BLACK));

		System.out.println("Trying to move the black piece at (5, 0) somewhere else is not valid, should be false: " + b2.isValidMove(new Move(5, 5, 5, 0), BLACK));
		System.out.println("Trying to move the white piece at (1, 3) somewhere else is not valid, should be false: " + b2.isValidMove(new Move(5, 5, 1, 3), BLACK));

		System.out.println("List of all valid moves for white: " + b2.getValidMoves(WHITE));
		System.out.println("List of all valid moves for black: " + b2.getValidMoves(BLACK));

		b2.makeMove(new Move(5, 5), WHITE);
		b2.makeMove(new Move(3, 4), BLACK);
		b2.makeMove(new Move(3, 6), WHITE);
		b2.makeMove(new Move(6, 4), BLACK);
		b2.makeMove(new Move(6, 6), WHITE);
		b2.makeMove(new Move(4, 7), BLACK);
		b2.makeMove(new Move(7, 4), WHITE);
		b2.makeMove(new Move(2, 7), BLACK);
		b2.makeMove(new Move(7, 5), WHITE);
		b2.makeMove(new Move(1, 0), BLACK);

		System.out.println("Added pieces until both WHITE and BLACK have enough to make step moves: \n" + b2);
		System.out.println("Number of white pieces should be 10: " + b2.getNumPieces(WHITE));
		System.out.println("Number of black pieces should be 10: " + b2.getNumPieces(BLACK));

		System.out.println("Moving white piece at (3, 3) to (5, 1) is valid, should be true:  " + b2.isValidMove(new Move(5, 1, 3, 3), WHITE));
		System.out.println("Testing tricky three-in-a-rows:");
		System.out.println("Moving black piece at (1, 2) to (1, 1) is valid, should be true:  " + b2.isValidMove(new Move(1, 1, 1, 2), BLACK));
		System.out.println("Moving white piece at (3, 1) to (3, 2) is valid, should be true:  " + b2.isValidMove(new Move(3, 2, 3, 1), WHITE));
		System.out.println("Moving white piece at (3, 1) to (5, 1) is valid, should be true:  " + b2.isValidMove(new Move(5, 1, 3, 1), WHITE));
		System.out.println("Moving white piece at (3, 3) to (4, 4) is not valid, should be false:  " + b2.isValidMove(new Move(4, 4, 3, 3), WHITE));
		System.out.println("Moving black piece at (6, 3) to (4, 6) is valid, should be true:  " + b2.isValidMove(new Move(4, 6, 6, 3), BLACK));
		System.out.println("Moving white piece at (3, 1) to (3, 2) is valid, should be true:  " + b2.isValidMove(new Move(1, 1, 1, 2), WHITE));
		System.out.println("Moving white piece at (3, 1) to (3, 2) is valid, should be true:  " + b2.isValidMove(new Move(1, 1, 1, 2), WHITE));
		System.out.println("Moving white piece at (3, 1) to (3, 2) is valid, should be true:  " + b2.isValidMove(new Move(1, 1, 1, 2), WHITE));
		System.out.println("Moving white piece at (3, 1) to (3, 2) is valid, should be true:  " + b2.isValidMove(new Move(1, 1, 1, 2), WHITE));

		System.out.println("Moving black piece from (1, 4) to (2, 4) is valid, should be true:  " + b2.isValidMove(new Move(2, 4, 1, 4), BLACK));
		System.out.println("Moving white piece from (5, 5) to (5, 7) is not valid, should be false:  " + b2.isValidMove(new Move(5, 7, 5, 5), WHITE));
		System.out.println("No black piece at (4, 4), cannot move it, should be false:  " + b2.isValidMove(new Move(1, 1, 4, 4), BLACK));
		System.out.println("No black piece at (3, 6), cannot move it, should be false:  " + b2.isValidMove(new Move(1, 1, 3, 6), BLACK));
		System.out.println("No white piece at (4, 1), cannot move it, should be false:  " + b2.isValidMove(new Move(5, 1, 4, 1), WHITE));
		System.out.println("Cannot move white piece at (3, 3) back to (3, 3), should be false:  " + b2.isValidMove(new Move(3, 3, 3, 3), WHITE));
		System.out.println("Cannot move black piece at (1, 2) back to (1, 2), should be false:  " + b2.isValidMove(new Move(1, 2, 1, 2), BLACK));

		System.out.println("List of all valid moves for white: " + b2.getValidMoves(WHITE));
		System.out.println("List of all valid moves for black: " + b2.getValidMoves(BLACK));

		b2.makeMove(new Move(1, 1, 1, 2), BLACK);
		System.out.println("Move black piece from (1, 2) to (1, 1): " + b2);
		b2.rollback();
		System.out.println("Rollback: " + b2);
	}
}
