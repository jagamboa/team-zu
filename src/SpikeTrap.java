
public class SpikeTrap extends Piece implements Hazard
{

	public SpikeTrap(int gridX, int gridY) 
	{
		super(gridX, gridY, 100);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Grid grid)
	{
		super.update(grid);
		
		if (!isBeingLaunched())
		{
			// check above
			for (int y = getY() - 1; y > 0; y--)
			{
				if (grid.getPieceAt(getX(), y) == null)
				{
					if (grid.getTileAt(getX(), y) instanceof Pit)
						break;
					else
						continue;
				}
				else if (grid.getPieceAt(getX(), y) instanceof Playable)
				{
					launch(Direction.Up);
				}
				else
				{
					break;
				}
			}
			
			// check below
			for (int y = getY() + 1; y < Grid.HEIGHT - 1; y++)
			{
				if (grid.getPieceAt(getX(), y) == null)
				{
					if (grid.getTileAt(getX(), y) instanceof Pit)
						break;
					else
						continue;
				}
				else if (grid.getPieceAt(getX(), y) instanceof Playable)
				{
					launch(Direction.Down);
				}
				else
				{
					break;
				}
			}
			
			// check left
			for (int x = getX() - 1; x > 0; x--)
			{
				if (grid.getPieceAt(x, getY()) == null)
				{
					if (grid.getTileAt(x, getY()) instanceof Pit)
						break;
					else
						continue;
				}
				else if (grid.getPieceAt(x, getY()) instanceof Playable)
				{
					launch(Direction.Left);
				}
				else
				{
					break;
				}
			}
			
			// check right
			for (int x = getX() + 1; x < Grid.WIDTH - 1; x++)
			{
				if (grid.getPieceAt(x, getY()) == null)
				{
					if (grid.getTileAt(x, getY()) instanceof Pit)
						break;
					else
						continue;
				}
				else if (grid.getPieceAt(x, getY()) instanceof Playable)
				{
					launch(Direction.Right);
				}
				else
				{
					break;
				}
			}
		}
	}
	
	// A SpikeTrap is not pushable
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// A SpikeTrap does nothing when Clicked
	@Override
	public void onClick(Grid grid, Player player) 
	{

	}

	// If a SpikeTrap is on top of a Pit then this
	// SpikeTrap is destroyed
	@Override
	public void isOnTopOf(Tile tile) 
	{
		if (tile instanceof Pit)
		{
			isDestroyed = true;
		}
	}

	// This should kill any playable characters it touches
	@Override
	public void touch(Piece pieceBeingTouched) 
	{
		if (pieceBeingTouched instanceof Playable)
		{
			((Playable)pieceBeingTouched).die();
		}
	}

}
