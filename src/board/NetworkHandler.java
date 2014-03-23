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
	GamePiece [] pieces;
	LinkedList<Integer> blackIndices;
	LinkedList<Integer> whiteIndices;
	NetworkHandler() 
	{
		blackIndices = new LinkedList<Integer>();
		whiteIndices= new LinkedList<Integer>();
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
			Integer ind = m.x1*10+m.y1;
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
			int ind = oldx*10+oldy;
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
			Move nm = new Move(m.x1, m.x2);
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
		for(int x = 0; x<3; x++)
		{
			for(int y = 0; y<3; y++)
			{
				GamePiece currNeighbor = added.pointers[x][y];
				if(currNeighbor == null)
					continue;
				currNeighbor.pointers[2-x][2-y] = added;				
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
		int x = added.row;
		int y = added.col;
		int px = potentialNeighbor.row;
		int py = potentialNeighbor.col;
		if(px == x)
		{
			if (px<x &&added.distance(potentialNeighbor)<added.distance(added.pointers[1][0]))
				added.pointers[1][0] = potentialNeighbor;
			else if(px>x&&added.distance(potentialNeighbor)<added.distance(added.pointers[1][2]))
				added.pointers[1][2]= potentialNeighbor;
		}
		else if(py == y)
		{
			if(py<y&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][1]))
				added.pointers[0][1]= potentialNeighbor;
			else if (py>y&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][1]))
				added.pointers[2][1] = potentialNeighbor;
		}
		else if((py-y)/(px-x) == 1)
		{
			if(px<x&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][0]))
				added.pointers[0][0]= potentialNeighbor;
			else if(px>x&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][2]))
				added.pointers[2][2] = potentialNeighbor;
		}
		else if((py-y)/(px-x)==-1)
		{
			if(px<x&&added.distance(potentialNeighbor)<added.distance(added.pointers[2][0]))
				added.pointers[2][0] = potentialNeighbor;
			else if(px>x&&added.distance(potentialNeighbor)<added.distance(added.pointers[0][2]))
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
				blackIndices.remove(0);//It's assumed that m was the last move
			else if(color==Board.WHITE)//The LLs are defacto sorted in order of
				whiteIndices.remove(0);//Most to least recent
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
	int getNumConnections(int color) 
	{



		return -1;
	}

}
class GamePiece
{
	protected GamePiece [][] pointers;
	//	protected boolean connectedStart;
	//	protected boolean connectedEnd;
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
