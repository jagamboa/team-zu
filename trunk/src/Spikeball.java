
public class Spikeball extends Piece implements Hazard
{
	private Direction direction;
	private Direction initialDirection;
	private Piece[] adjacent;
	
	// A Spikeball piece is a moving hazard.  It is initialized with an
	// initial direction of motion and will continue moving in that direction
	// until it hits a Square onto which it cannot move.  It will then reverse it's
	// movement direction and start moving again.
	public Spikeball(int gridX, int gridY, Direction initialDirection) 
	{
		super(gridX, gridY, 200, 100);
		
		this.initialDirection = initialDirection;
		direction = initialDirection;
		
		adjacent = new Piece[4];
	}

	@Override
	public void update(Grid grid)
	{	
		if (!isBeingLaunched())
			grid.movePiece(this.getX(), this.getY(), direction);
		else
			direction = getLaunchedDirection();
		
		super.update(grid);
		
		adjacent[0] = grid.getPieceAt(getX(), getY() - 1);
		adjacent[1] = grid.getPieceAt(getX(), getY() + 1);
		adjacent[2] = grid.getPieceAt(getX() - 1, getY());
		adjacent[3] = grid.getPieceAt(getX() + 1, getY());
		
		for (int i = 0; i < 4; i++)
		{
			if (adjacent[i] != null && (adjacent[i] instanceof Playable) && !Forsaken.FROST)
			{
				if (Math.abs(adjacent[i].getPixelX() - getPixelX()) < Grid.SQUARE_DIMENSIONS
						&& Math.abs(adjacent[i].getPixelY() - getPixelY()) < Grid.SQUARE_DIMENSIONS)
				{
					((Playable)adjacent[i]).die();
				}
			}
		}
		
		Tile tileInPath;
		
		if (direction == Direction.Up)
			tileInPath = grid.getTileAt(getX(), getY() - 1);
		else if (direction == Direction.Down)
			tileInPath = grid.getTileAt(getX(), getY() + 1);
		else if (direction == Direction.Left)
			tileInPath = grid.getTileAt(getX() - 1, getY());
		else
			tileInPath = grid.getTileAt(getX() + 1, getY());
		
		if (tileInPath instanceof Pit)
		{
			reflectDirection();
		}
	}
	
	// A Spikeball is not pushable
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// A Spikeball does nothing when clicked
	@Override
	public void onClick(Grid grid, Player player) 
	{

	}

	// A Spikeball will be destroyed when it is on
	// top of a Pit
	@Override
	public void isOnTopOf(Tile tile) 
	{
		if (tile instanceof Pit)
		{
			isDestroyed = true;
			SoundPlayer.fallingSFX.play();
		}
	}

	// When a Spikeball touches a player controllable piece,
	// that piece will die.  The Spikeball will also flip it's
	// movement direction.
	@Override
	public void touch(Piece pieceBeingTouched) 
	{
		// TODO Piece death
		if (pieceBeingTouched instanceof Playable && !Forsaken.FROST)
		{
			((Playable)pieceBeingTouched).die();
		}
		
		reflectDirection();
	}
	
	public Direction getInitialDirection()
	{
		return initialDirection;
	}
	
	// turns the Spikeball around
	private void reflectDirection()
	{
		if (direction == Direction.Up)
		{
			direction = Direction.Down;
		}
		else if (direction == Direction.Down)
		{
			direction = Direction.Up;
		}
		else if (direction == Direction.Right)
		{
			direction = Direction.Left;
		}
		else if (direction == Direction.Left)
		{
			direction = Direction.Right;
		}
	}
	

}
