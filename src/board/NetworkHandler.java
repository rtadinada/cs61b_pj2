package board;

import java.util.Iterator;

import player.Move;
import util.LinkedList;

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
	GamePiece [] pieces;
	LinkedList<Integer> blackIndices;
	LinkedList<Integer> whiteIndices;
	LinkedList<Integer> networkSizes;

	NetworkHandler() 
	{
		blackIndices = new LinkedList<Integer>();
		whiteIndices= new LinkedList<Integer>();
		networkSizes = new LinkedList<Integer>();
		pieces = new GamePiece[77];
		for(int i = 0; i<=76; i++)
		{
			GamePiece newPiece = new GamePiece(i/10, i%10, 0);
			pieces[i] = newPiece;
		}

	}

	//***************************************************************
	//*PRETEND THAT X IS ROW AND Y IS COLUMN						*
	//*IT IS NOT TECHNICALLY CORRECT IN TRADITIONAL THINKING		*
	//*BUT OTHERWISE I WILL GET HELLA CONFUSED						*
	//*LOVE, RHETT													*
	//***************************************************************
	/**
	 * Updates internal configuration with the specified move for the
	 * specified color. Assumes that <code>m</code> is a valid move. Uses color
	 * as specified in Board.WHITE and Board.BLACK (assume the same for other
	 * methods).
	 * 
	 * @param m			Move to update with
	 * @param color		color of the player who made the move
	 */
	public static void main (String [] args)
	{
		NetworkHandler n = new NetworkHandler();
		System.out.println(n);
		n.makeMove(new Move(1, 1), Board.WHITE);
		System.out.println("Added white piece to 1, 1: \n" + n);
		n.makeMove(new Move(3, 3), Board.WHITE);
		System.out.println("Added white piece to 3, 3: \n" + n);
		System.out.println("The list of white locations: " + n.whiteIndices);
		System.out.println("The white piece at (1, 1) should have a white neighbor at the bottom right: \n" + n.pieces[11]);
		System.out.println("The white piece at (3, 3) should have a white neighbor at the top left: \n" + n.pieces[33]);
		n.makeMove(new Move(2, 2), Board.BLACK);
		System.out.println("Added a new black piece in between the two: \n" + n);
		System.out.println("The white piece at (1, 1) should have a black neighbor at the bottom right: \n" + n.pieces[11]);
		System.out.println("The white piece at (3, 3) should have a black neighbor at the top left: \n" + n.pieces[33]);
		System.out.println("The black piece at (2, 2) should have white neighbors to the top left and bottom right: \n" + n.pieces[22]);
		n.makeMove(new Move(1, 3, 2, 2), Board.BLACK);
		System.out.println("Move the black piece out of the way to (1, 3): \n" + n);
		System.out.println("The white piece at (1, 1) should have a black neighbor below and a white neighbor in the bottom right: \n" + n.pieces[11]);
		System.out.println("The white piece at (3, 3) should have a white neighbor at the top right and a black neighbor to the left: \n" + n.pieces[33]);
		System.out.println("Hmm, the black piece seems to have moved to (2, 1) instead: \n" + n.pieces[21]);
		n.makeMove(new Move(1, 3, 2, 1), Board.BLACK);
		System.out.println("Trying to move to (1, 3) again: \n" + n);
		n.makeMove(new Move(5, 3, 3, 3), Board.WHITE);
		System.out.println("Didn't move anywhere.");
		System.out.println("Trying to move white piece at (3, 3) to (5, 3) (right two): \n" + n);
		System.out.println("Moved down two instead");
		n.makeMove(new Move(6, 4, 1, 1), Board.WHITE);
		System.out.println("Trying to move white piece at (1, 1) to (6, 4): \n" + n);
		System.out.println("Moved to (1, 6) instead. I think somewhere you said x2 and x1 instead of x1 and y1");
		System.out.println("Checking to see if there is a white piece at (1, 6): " + n.pieces[16]);
		System.out.println("Seems not.");
		System.out.println("Checking to see if there is a white piece at (6, 4), where we intended to move it: " + n.pieces[64]);
		System.out.println("Seems not.");

		/*
		TESTS FOR hasNetwork:
		*/

		NetworkHandler n1 = new NetworkHandler();
		n1.makeMove(new Move(1, 1), Board.WHITE);
		n1.makeMove(new Move(3, 3), Board.WHITE);
		n1.makeMove(new Move(3, 1), Board.WHITE);
		n1.makeMove(new Move(5, 3), Board.WHITE);
		n1.makeMove(new Move(0, 2), Board.WHITE);
		n1.makeMove(new Move(7, 5), Board.WHITE);
		System.out.println("Testing white networks: \n" + n1);
		System.out.println("Neighbors of white piece at (0, 2): \n" + n1.pieces[2]);
		System.out.println("Neighbors of white piece at (1, 1): \n" + n1.pieces[11]);
		System.out.println("Neighbors of white piece at (3, 3): \n" + n1.pieces[33]);
		System.out.println("Neighbors of white piece at (3, 1): \n" + n1.pieces[31]);
		System.out.println("Neighbors of white piece at (5, 3): \n" + n1.pieces[53]);
		System.out.println("Neighbors of white piece at (7, 5): \n" + n1.pieces[75]);


		System.out.println("Testing row versus column major: ");
		System.out.println("Trying (2, 0) for (0, 2): \n" + n1.pieces[20]);
		System.out.println("Trying (1, 3) for (3, 1): \n" + n1.pieces[13]);
		System.out.println("Printing out all neighbors of piece at (3, 1): ");
		GamePiece g = n1.pieces[13];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (g.pointers[y][x] == null) {
					System.out.println("At (" + x + ", " + y + "): " + null);
				} else {
					System.out.println("At (" + x + ", " + y + "): " + g.pointers[y][x].color);
				}
					
			}
		}
		System.out.println("Trying (3, 5) for (5, 3): \n" + n1.pieces[35]);
		System.out.println("Trying (5, 7) for (7, 5): \n" + n1.pieces[57]);

		System.out.println("White has a network, should be true: \n" + n1.hasNetwork(Board.WHITE));

		NetworkHandler rhett = new NetworkHandler();
		rhett.makeMove(new Move(0, 1), Board.WHITE);
		rhett.makeMove(new Move(1, 0), Board.BLACK);
		rhett.makeMove(new Move(0, 2), Board.WHITE);
		rhett.makeMove(new Move(1, 1), Board.BLACK);
		rhett.makeMove(new Move(0, 4), Board.WHITE);
		rhett.makeMove(new Move(3, 1), Board.BLACK);
		rhett.makeMove(new Move(0, 5), Board.WHITE);
		rhett.makeMove(new Move(3, 4), Board.BLACK);
		rhett.makeMove(new Move(7, 1), Board.WHITE);
		rhett.makeMove(new Move(1, 4), Board.BLACK);
		rhett.makeMove(new Move(7, 2), Board.WHITE);
		rhett.makeMove(new Move(1, 7), Board.BLACK);
		
		System.out.println(rhett);
		System.out.println("Printing out black piece at (1, 0) \n" + rhett.pieces[1]);
		System.out.println("Printing out black piece at (1, 1) \n" + rhett.pieces[11]);
		System.out.println("Printing out black piece at (3, 1) \n" + rhett.pieces[13]);
		System.out.println("Printing out black piece at (3, 4) \n" + rhett.pieces[43]);
		System.out.println("Printing out black piece at (1, 4) \n" + rhett.pieces[41]);
		System.out.println("Printing out black piece at (1, 7) \n" + rhett.pieces[71]);
		System.out.println("There should be a black network \n" + rhett.hasNetwork(Board.BLACK));


		NetworkHandler ajeya = new NetworkHandler();
		ajeya.makeMove(new Move(0, 1), Board.WHITE);
		ajeya.makeMove(new Move(1, 0), Board.BLACK);
		ajeya.makeMove(new Move(1, 3), Board.WHITE);
		System.out.println("Board: \n" + ajeya);
		System.out.println("There is no white network: " + ajeya.hasNetwork(Board.WHITE));
	}

	public String toString() {
		String str = "";
		for (int i = 0; i <= 76; i++) {
			int x = i%10;
			int y = i/10;
			if (x < 8 && y < 8) {
				GamePiece piece = pieces[i];
				if (x == 0) {
					str += " --- --- --- --- --- --- --- ---\n|";
				}
				if (piece.color == Board.BLACK) {
					str += " B |";
				}
				if (piece.color == Board.WHITE) {
					str += " W |";
				}
				else {
					str += "   |";
				}
				if (x == 7) {
					str += "\n";
				}
 			}
		}
		return str + "   |\n --- --- --- --- --- --- --- ---";

	}
	//***************************************************************
	//*PRETEND THAT X IS ROW AND Y IS COLUMN						*
	//*IT IS NOT TECHNICALLY CORRECT IN TRADITIONAL THINKING		*
	//*BUT OTHERWISE I WILL GET HELLA CONFUSED						*
	//*LOVE, RHETT													*
	//***************************************************************


	//This method returns a -1 for start, 0 for not inGoal, and 1 for end
	@SuppressWarnings("unused")
	private int inGoal(int color, int row, int col)
	{
		if(color==Board.BLACK)
		{
			if(row ==0) return -1;
			if (row==7) return 1;
			return 0;
		}
		else if (color == Board.WHITE)
		{
			if (col ==0) return -1;
			if (col==7) return 1;
			return 0;
		}
		return 0;
	}
	void makeMove(Move m, int color) 
	{
		if(m.moveKind == Move.ADD)
		{
			Integer ind = m.y1*10+m.x1;
			pieces[ind].color = color;
			if(color==(Board.BLACK))
			{
				blackIndices.add(ind);
			}
			else if(color==(Board.WHITE))
			{
				whiteIndices.add(ind);
			}
			else
				System.out.println("Neither black nor white: Whassup?");

			addPiece(pieces[ind]);
		}
		else if(m.moveKind == Move.STEP)
		{
			//Delete the previous pointers!!!
			int oldx = m.x2;
			int oldy = m.y2;
			int ind = oldy*10+oldx;
			pieces[ind].color=0;
			if(color==(Board.BLACK))
			{
				Iterator<Integer> blackIterator = blackIndices.iterator();
				while(blackIterator.hasNext())
				{
					Integer toRem = blackIterator.next();
					if(toRem==ind)
					{ blackIterator.remove(); //Pulls the old piece out; it get put back in recursively
					break;
					}
				}
			}
			else if(color == Board.WHITE)
			{
				Iterator<Integer> whiteIterator = whiteIndices.iterator();
				while(whiteIterator.hasNext())
				{
					Integer toRem = whiteIterator.next();
					if(toRem==ind)
					{ 
						whiteIterator.remove(); //Pulls the old piece out; it get put back in recursively
						break;
					}
				}
			}
			GamePiece toMove = pieces[ind];
			for (int row = 0; row<3;row++)
			{
				for(int col = 0; col<3; col++)
				{
					GamePiece affectedNeighbor = toMove.pointers[row][col];
					if(affectedNeighbor ==null)
						continue;
					affectedNeighbor.pointers[2-row][2-col] = toMove.pointers[2-row][2-col]; //Erasing the moved piece from the 
				}											//pointers of those it pointed to
			}
			Move nm = new Move(m.x1, m.y1);
			makeMove(nm, color); // This will trigger the Add, rather than Step, functionality.
		}
	}
	private void addPiece(GamePiece added)
	{
		Iterator<Integer> blackIterator = blackIndices.iterator();
		while(blackIterator.hasNext())
		{
			GamePiece potentialNeighbor = pieces[blackIterator.next()];
			setNeighbors(added, potentialNeighbor);
		}
		Iterator<Integer> whiteIterator = whiteIndices.iterator();
		while(whiteIterator.hasNext())
		{
			GamePiece potentialNeighbor = pieces[whiteIterator.next()];
			setNeighbors(added, potentialNeighbor);
		}
		for(int row = 0; row<3; row++)
		{
			for(int col = 0; col<3; col++)
			{
				GamePiece currNeighbor = added.pointers[row][col];
				if(currNeighbor == null)
					continue;
				currNeighbor.pointers[2-row][2-col] = added;				
				//				if(currNeighbor.connectedEnd&&currNeighbor.color==added.color)
				//					added.connectedEnd= true;
				//				if (currNeighbor.connectedStart&&currNeighbor.color==added.color)
				//					added.connectedStart = true;
			}
		}
		//updateBooleans(added);
	}
	//	private void undoBooleans(GamePiece removed)
	//	{
	//		if(removed ==null||(removed.connectedEnd==false&&removed.connectedStart==false))
	//			return;
	//		for(int x = 0; x<3; x++)
	//			for (int y = 0; y<3; y++)
	//			{
	//				GamePiece currNeighbor = removed.pointers[x][y];
	//				if(currNeighbor)
	//			}
	//				
	//	}
	//	private void updateBooleans(GamePiece added)
	//	{
	//		if(added ==null)
	//			return;
	//		if(added.connectedStart||added.connectedEnd)
	//		{
	//			for(int x = 0; x<3; x++)
	//			{
	//				for(int y = 0; y<3; y++)
	//				{
	//					GamePiece currNeighbor = added.pointers[x][y];
	//					if(currNeighbor == null||(added.connectedStart==currNeighbor.connectedStart&&added.connectedEnd==currNeighbor.connectedEnd))
	//						continue;
	//					currNeighbor.connectedEnd=added.connectedEnd;
	//					currNeighbor.connectedStart=added.connectedStart;
	//					updateBooleans(currNeighbor);
	//				}
	//			}
	//		}
	//	}
	private void setNeighbors(GamePiece added, GamePiece potentialNeighbor)
	{
		int row = added.row;
		int col = added.col;
		int prow = potentialNeighbor.row;
		int pcol = potentialNeighbor.col;
		if(prow == row)
		{
			if (pcol<col &&added.distance(potentialNeighbor)<added.distance(added.pointers[1][0]))
				added.pointers[1][0] = potentialNeighbor;
			else if(pcol>col&&added.distance(potentialNeighbor)<added.distance(added.pointers[1][2]))
				added.pointers[1][2]= potentialNeighbor;
		}
		else if(pcol == col)
		{
			if(prow<row&added.distance(potentialNeighbor)<added.distance(added.pointers[0][1]))
				added.pointers[0][1]= potentialNeighbor;
			else if (prow>row&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][1]))
				added.pointers[2][1] = potentialNeighbor;
		}
		else if((prow-row)/(pcol-col) == 1)
		{
			if(pcol<col&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][0]))
				added.pointers[0][0]= potentialNeighbor;
			else if(pcol>col&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][2]))
				added.pointers[2][2] = potentialNeighbor;
		}
		else if((prow-row)/(pcol-col)==-1)
		{
			if(pcol<col&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][0]))
				added.pointers[2][0] = potentialNeighbor;
			else if(pcol>col&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][2]))
				added.pointers[0][2] = potentialNeighbor;
		}
	}
	/**
	 * Updates the internal configuration to remove the specified move for the
	 * specified color. Assumes that <code>m</code> was the last move and made
	 * by the specified player.
	 * 
	 * @param m			Move to remove
	 * @param color		color of the player who made the move
	 */
	void undoMove(Move m, int color) 
	{
		if(m.moveKind==Move.ADD)
		{
			if(color == Board.BLACK)
				blackIndices.remove(0);//It's assumed that m was the last move8
			else if(color==Board.WHITE)//The LLs are defacto sorted in order of
				whiteIndices.remove(0);//Most to least recent
			// int i = m.x1*10 + m.y1;
			// pieces[i] = new GamePiece();
		}
		else if(m.moveKind==Move.STEP)
		{
			Move newMove = new Move(m.x2, m.y2, m.x1, m.y1);
			makeMove(newMove, color);
		}
	}

	/**
	 * Returns true if the player of the specified color has a fully formed
	 * network (as defined in the README) and false otherwise.
	 * 
	 * @param color		color of the player
	 * @return	true if a network for the specified player exists, false otherwise
	 */
	boolean hasNetwork(int color) {
		int start;
		int step;
		if (color == Board.BLACK) {
			start = 1;
			step = 1; //The black goals on the top are 1, 2,...7
		} else {
			start = 10;
			step = 10; // The white goals on the left are 10, 20...70
		}
		for (int i = start; i <= step*7; i += step) {
			GamePiece currPiece = pieces[i];
			// System.out.println("Looking at the piece at " + i + " on the game board: \n" + currPiece);
			if (currPiece != null && currPiece.color == color) {
				int distance = numToEndGoal(currPiece, 1, -1, -1);
				if (distance >= 5) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	* Returns how many pieces it takes to connected the piece to the end.
	*@param piece: the game piece we are checking from
	*@param currDist: the length of the current path this piece is part of
	*@param prevDirection: the direction you were going last time, represented as a number
		* x + 3y - this gives a unique index for each of the pointer directions, 0 through 8
	*@param fromDirection: the direction pointing piece to the neighbor that called the method on piece,
	* so it avoids checking backwards
	*
	* IMPORTANT ASSSUMPTION: this will only ever be called on a piece in the color's starting goal, so 
	* we can ignore other pieces in the start goal, since you can only have one piece per goal per network.
	* This means we are essentially looking at the board with the first row and column erased
	*/
	private int numToEndGoal(GamePiece piece, int currDist, int prevDirection, int fromDirection) {
		if (inGoal(piece.color, piece.row, piece.col) == 1) {
			// System.out.println("We've reached something on the end goal; return the distance built up so far: " + currDist);
			return currDist;
		}
		piece.visited = true;
		// System.out.println("Just visited the piece at (" + piece.col + ", " + piece.row + "): " + piece.visited);
		int currMax = -1;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if ((x + 3*y != prevDirection) && (x + 3*y != fromDirection)) {
					// System.out.println("Checking for neighbors in (" + x + ", " + y + ") direction: ");
					GamePiece neighbor = piece.pointers[y][x];
					if ((neighbor != null) && (neighbor.row > 0) && (neighbor.col > 0) && !(neighbor.visited) && (neighbor.color == piece.color)) {
						// System.out.println("The neighbor: \n" + neighbor);
						// System.out.println("It should not have been visited yet: " + neighbor.visited);
						int distFromNeighbor = numToEndGoal(neighbor, currDist + 1, x + 3*y, (2 - x) + 3*(2 - y));
						// System.out.println("The distance from this neighbor to the end goal is " + distFromNeighbor);
						if (distFromNeighbor > currMax) {
							// System.out.println("This is larger than the longest distance we have so far, setting the currMax to this value.");
							currMax = distFromNeighbor;
						}	
					}
					else {
						// System.out.println("The neighbor: \n" + neighbor + "\nis nonexistent or the wrong color.");
					}	
				}
			}
		}
		// System.out.println("We've checked everything; this call is about to return: the maximum distance to the end goal from here is " + currMax);
		piece.visited = false;
		return currMax;	
	}
	

	/**
	 * Returns a <code>LinkedList</code> of the sizes of all the subnetworks
	 * for the specified color.
	 * 
	 * @param color		color of the player
	 * @return	a list of the sizes of all the subnetworks.
	 */
	LinkedList<Integer> getNetworkSizes(int color) {
		networkSizes = new LinkedList<Integer>();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				int i;
				if (color == Board.BLACK) {
					i = 10*y + x; // Go row-by-row with Black (1, 2, 3...10, 11, 12...)
				} else {
					i = 10*x + y; //Go column-by-column with White (10, 20, 30,...1, 11, 21...)
				}
				GamePiece piece = pieces[i];
				if (piece != null && !(piece.visited) && piece.color == color) {
					networkSizes.add(maxConnectionLength(piece, color, 0));
				}
			}
		}
		return networkSizes;
	}

	private int maxConnectionLength(GamePiece piece, int color, int numSoFar) {
		int maxLength = numSoFar;
		piece.visited = true;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				GamePiece neighbor = piece;
			}
		}
		return -1;
	}
	/**
	 * Returns the number of connections between two pieces of the specified
	 * color on the board
	 * 
	 * @param color		color of the player
	 * @return	the number of connections of the color color
	 */

}
class GamePiece
{
	protected GamePiece [][] pointers;
	//	protected boolean connectedStart;
	//	protected boolean connectedEnd;
	protected int row;
	protected int col;
	protected int color;
	protected boolean visited;
	GamePiece(int r, int c, int colr)
	{
		row = r;
		col = c;
		color = colr;
		pointers = new GamePiece[3][3];
		visited = false;
	}
	GamePiece()
	{
		this(-1,-1,-1);
	}


	public int distance (GamePiece other)
	{
		if(other==null)
			return Integer.MAX_VALUE;
		return Math.abs(this.row+this.col-other.row-other.col);
	}

	public String toString() {
		String str = "";
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (x == 1 && y == 1) {
					if (color == Board.WHITE) {
						str += " W ";
					} else if (color == Board.BLACK) {
						str += " B ";
					} else {
						str += " X ";
					}

				}
				else {
					GamePiece neighbor = pointers[y][x];
					if (neighbor == null) {
						str += " X ";
					} else if (neighbor.color == Board.BLACK) {
						str += " B ";
					} else if (neighbor.color == Board.WHITE) {
						str += " W ";
					}
				}
				if (x == 2) {
					str += "\n";
				}
			}
		}
		return str;
	}
}
