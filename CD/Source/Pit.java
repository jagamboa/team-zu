
public class Pit extends Tile implements Hazard
{
	// Constructs a new Pit Tile.
	public Pit(int gridX, int gridY) 
	{
		super(gridX, gridY);
	}

	// There are no updates necessary for this Tile
	@Override
	public void update(Grid grid) 
	{

	}

	// There are no changes to the Pit's state when something
	// moves on top of it.
	@Override
	public void hasOnTop(Piece piece) 
	{
		super.hasOnTop(piece);
	}

	// A Piece can move onto a Pit
	@Override
	public boolean canMoveOnto() 
	{
		return true;
	}

}
