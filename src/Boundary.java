
public class Boundary extends Piece 
{

	// Constructor for Boundary
	public Boundary(int gridX, int gridY) 
	{
		super(gridX, gridY);
	}

	// Boundaries are not pushable
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// Boundaries do nothing when clicked.
	@Override
	public void onClick(Grid grid, Player player) 
	{

	}

	// Boundaries are always placed on top of Empty Tiles
	// so they do nothing when on top of other tiles.
	@Override
	public void isOnTopOf(Tile tile) 
	{

	}

	// Boundaries do nothing when they touch other pieces
	@Override
	public void touch(Piece pieceBeingTouched) 
	{

	}

}
