
public class EmptyTile extends Tile 
{

	// creates a new empty tile given 
	public EmptyTile(int gridX, int gridY)
	{
		super(gridX, gridY);
	}
	
	// the empty tile should do nothing on update
	@Override
	public void update(Grid grid) 
	{

	}

	// the empty tile should do nothing when something is on
	// top of it
	@Override
	public void hasOnTop(Piece piece) 
	{
		super.hasOnTop(piece);
	}

	// the empty tile can have objects move on top of it
	@Override
	public boolean canMoveOnto() 
	{
		return true;
	}

}
