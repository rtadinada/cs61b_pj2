package board;

import java.util.Iterator;

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
	LinkedList<GamePiece> blackPieces;
	LinkedList<GamePiece> whitePieces;
	NetworkHandler() 
	{
		blackPieces = new LinkedList<GamePiece>();
		whitePieces = new LinkedList<GamePiece>();
		
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
		
	}
	//***************************************************************
	//*PRETEND THAT X IS ROW AND Y IS COLUMN						*
	//*IT IS NOT TECHNICALLY CORRECT IN TRADITIONAL THINKING		*
	//*BUT OTHERWISE I WILL GET HELLA CONFUSED						*
	//*LOVE, RHETT													*
	//***************************************************************
	void makeMove(Move m, int color) 
	{
		if(m.moveKind == Move.ADD)
		{
			GamePiece added = new GamePiece(m.x1, m.x2, color);
			if(color==(Board.BLACK))
				blackPieces.add(added);
			else if(color==(Board.WHITE))
				whitePieces.add(added);
			else
				System.out.println("Neither black nor white: Whassup?");
			addPiece(added);
		}
		else if(m.moveKind == Move.STEP)
		{
			//Delete the previous pointers!!!
			int oldx = m.x2;
			int oldy = m.y2;
			GamePiece toMove = null;
			if(color==(Board.BLACK))
			{
				Iterator<GamePiece> blackIterator = blackPieces.iterator();
				while(blackIterator.hasNext())
				{
					toMove = blackIterator.next();
					if(toMove.row == oldx&&toMove.col ==oldy)
					{ blackIterator.remove(); //Pulls the old piece out; it get put back in recursively
						break;
					}
				}
			}
			else if(color == Board.WHITE)
			{
				Iterator<GamePiece> whiteIterator = whitePieces.iterator();
				while(whiteIterator.hasNext())
				{
					toMove = whiteIterator.next();
					if(toMove.row == oldx&&toMove.col ==oldy)
					{
						whiteIterator.remove(); //Pulls the old piece out; it get put back in recursively
						break;
					}
				}
			}
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
			Move nm = new Move(m.x1, m.x2);
			makeMove(nm, color); // This will trigger the Add, rather than Step, functionality.
		}
	}
	private void addPiece(GamePiece added)
	{
		Iterator<GamePiece> blackIterator = blackPieces.iterator();
		while(blackIterator.hasNext())
		{
			GamePiece potentialNeighbor = blackIterator.next();
			setNeighbors(added, potentialNeighbor);
		}
		Iterator<GamePiece> whiteIterator = whitePieces.iterator();
		while(whiteIterator.hasNext())
		{
			GamePiece potentialNeighbor = whiteIterator.next();
			setNeighbors(added, potentialNeighbor);
		}
		for(int x = 0; x<3; x++)
			for(int y = 0; y<3; y++)
			{
				GamePiece currNeighbor = added.pointers[x][y];
				if(currNeighbor == null)
					continue;
				currNeighbor.pointers[2-x][2-y] = added;
			}
	}
	private void setNeighbors(GamePiece added, GamePiece potentialNeighbor)
	{
		int x = added.row;
		int y = added.col;
		int px = potentialNeighbor.row;
		int py = potentialNeighbor.col;
		if(px == x)
			if (px<x &&added.distance(potentialNeighbor)<added.distance(added.pointers[1][0]))
				added.pointers[1][0] = potentialNeighbor;
			else if(px>x&&added.distance(potentialNeighbor)<added.distance(added.pointers[1][2]))
				added.pointers[1][2]= potentialNeighbor;
		if(py == y)
			if(py<y&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][1]))
				added.pointers[0][1]= potentialNeighbor;
			else if (py>y&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][1]))
				added.pointers[2][1] = potentialNeighbor;
		if((py-y)/(px-x) == 1)
			if(px<x&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][0]))
				added.pointers[0][0]= potentialNeighbor;
			else if(px>x&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][2]))
				added.pointers[2][2] = potentialNeighbor;
		if((py-y)/(px-x)==-1)
			if(px<x&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][0]))
				added.pointers[2][0] = potentialNeighbor;
			else if(px>x&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][2]))
				added.pointers[0][2] = potentialNeighbor;
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
class GamePiece
{
	protected GamePiece [][] pointers;
	protected boolean connectedStart;
	protected boolean connectedEnd;
	protected int row;
	protected int col;
	protected int color;
	GamePiece(int r, int c, int colr)
	{
		row = r;
		col = c;
		color = colr;
		pointers = new GamePiece[3][3];
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
}

