
public class Square 
{
	private Tile tile;
	private Piece piece;
	private boolean isBeingMovedTo;
	private Piece pieceMoving;
	
	public Square(int gridX, int gridY)
	{
		tile = new EmptyTile(gridX, gridY);
		piece = null;
		pieceMoving = null;
	}
	
	// initializes the tile for this square
	// if the tile is not the EmptyTile
	public void setTile(Tile tile)
	{
		this.tile = tile;
	}
	
	// initializes a piece on this square
	public void setPiece(Piece piece)
	{
		this.piece = piece;
		isBeingMovedTo = false;
		pieceMoving = null;
	}
	
	// returns the tile on this square
	public Tile getTile()
	{
		return tile;
	}
	
	// returns the piece on this square.
	// if there is no piece then this function returns null;
	public Piece getPiece()
	{
		return piece;
	}
	
	// sets whether this Square is occupied
	public void setIsBeingMovedTo(Piece movingPiece, boolean isBeingMovedTo)
	{
		this.isBeingMovedTo = isBeingMovedTo;
		pieceMoving = movingPiece;
	}
	
	public Piece getPieceMovingToThis()
	{
		return pieceMoving;
	}
	
	public boolean isBeingMovedTo()
	{
		return isBeingMovedTo;
	}
	
	// returns whether it is possible for another piece to
	// move onto this square.  If there is already a piece on this
	// square then it is not possible to move on it.
	public boolean canMoveOnto()
	{
		return piece == null && tile.canMoveOnto();
	}
	
	// calls update on the piece (if there is one) and
	// the tile stored in this Square
	public void update(Grid grid)
	{
		tile.update(grid);
		tile.hasOnTop(piece);

		if (piece != null)
		{
			piece.update(grid);
			piece.isOnTopOf(tile);
			
			if (piece.isDestroyed())
			{
				if (piece.isMoving() || piece.isBeingLaunched())
				{
					grid.unclaimSquare(piece.getX(), piece.getY(), piece.getLastDirectionMoved());
				}
				else
				{
					grid.unclaimSquare(piece.getX(), piece.getY(), null);
				}
				
				piece = null;
			}
		}
	}
}
