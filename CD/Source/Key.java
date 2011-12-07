
public class Key extends Piece 
{
	
	// Constructor for a Key Piece.  A Key Piece
	// is a Piece that represents a Key.
	// Once the key is touched by a player controlled
	// piece, this piece is removed from the Grid
	public Key(int gridX, int gridY) 
	{
		super(gridX, gridY);
	}

	// Keys are not pushable
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// Keys do nothing when clicked
	@Override
	public void onClick(Grid grid, Player player) 
	{
		
	}

	// Keys do nothing when on top of other Tiles
	@Override
	public void isOnTopOf(Tile tile) 
	{
		
	}

	// Keys do nothing when they touch other pieces
	// (Players have to touch it!)
	@Override
	public void touch(Piece pieceBeingTouched) 
	{
		
	}

}
