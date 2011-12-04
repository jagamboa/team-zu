
public class Glutton extends Piece implements Hazard {

	private int health;
	private Direction currentDirection;
	private Piece[] adjacent;
	private long lastChangeTime;
	private long nextChangeTime;
	private final int MAX_CHANGE_TIME = 3000;
	
	public Glutton(int gridX, int gridY) 
	{
		// parameters:
		// x position, y position, move duration, launch_move duration
		// (smaller values = faster speed)
		super(gridX, gridY, 250, 80);
		
		health = 3;
		currentDirection = Direction.Down;
		nextChangeTime = RandomGen.nextInt(MAX_CHANGE_TIME);
		adjacent = new Piece[4];
		lastChangeTime = -1;
	}
	
	@Override
	public void update(Grid grid)
	{
		super.update(grid);
		
		if (health < 1)
		{
			isDestroyed = true;
			grid.setTileDown(new Goal(getX(), getY()));
			return;
		}
		
		adjacent[0] = grid.getPieceAt(getX(), getY() - 1);
		adjacent[1] = grid.getPieceAt(getX(), getY() + 1);
		adjacent[2] = grid.getPieceAt(getX() - 1, getY());
		adjacent[3] = grid.getPieceAt(getX() + 1, getY());
		
		for (int i = 0; i < 4; i++)
		{
			if (adjacent[i] != null && (adjacent[i] instanceof Playable))
			{
				if (Math.abs(adjacent[i].getPixelX() - super.getPixelX()) < 1.5 * Grid.SQUARE_DIMENSIONS
						&& Math.abs(adjacent[i].getPixelY() - super.getPixelY()) < 1.8 * Grid.SQUARE_DIMENSIONS)
				{
					((Playable)adjacent[i]).die();
				}
			}
			else if (adjacent[i] != null && (adjacent[i] instanceof PushableBlock))
			{
				if (Math.abs(adjacent[i].getPixelX() - super.getPixelX()) < 1.5 * Grid.SQUARE_DIMENSIONS
						&& Math.abs(adjacent[i].getPixelY() - super.getPixelY()) < 2 * Grid.SQUARE_DIMENSIONS)
				{
					if (adjacent[i].isBeingLaunched())
					{
						getHurt((PushableBlock)adjacent[i]);
					}
				}
			}
		}
		
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
					SoundPlayer.gluttonChargeSFX.play();
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
					SoundPlayer.gluttonChargeSFX.play();
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
					SoundPlayer.gluttonChargeSFX.play();
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
					SoundPlayer.gluttonChargeSFX.play();
				}
				else
				{
					break;
				}
			}
		}
		
		// random movement
		if (!isBeingLaunched())
		{
			if (lastChangeTime == -1)
				lastChangeTime = System.currentTimeMillis();
			
			if (currentDirection != null)
				grid.movePiece(this.getX(), this.getY(), currentDirection);
			
			long currentTime = System.currentTimeMillis();
			
			if (currentTime - lastChangeTime > nextChangeTime)
			{
				currentDirection = RandomGen.getRandomDirection();
				lastChangeTime = currentTime;
				nextChangeTime = RandomGen.nextInt(MAX_CHANGE_TIME);
				
				if (RandomGen.nextInt(4) < 1)
					SoundPlayer.gluttonLaughSFX.play();
			}
		}
	}
	
	@Override
	public int getPixelX()
	{
		return super.getPixelX() - 24;
	}
	
	@Override
	public int getPixelY()
	{
		return super.getPixelY() - 31;
	}

	// The Glutton can't be pushed
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// The Glutton does nothing when clicked
	@Override
	public void onClick(Grid grid, Player player) 
	{

	}

	// Dangerous tiles don't kill the Glutton
	@Override
	public void isOnTopOf(Tile tile) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void touch(Piece pieceBeingTouched) 
	{
		// TODO Auto-generated method stub
		currentDirection = RandomGen.getRandomDirection();
		
		if (pieceBeingTouched instanceof PushableBlock)
		{
			if (pieceBeingTouched.isBeingLaunched())
			{
				getHurt((PushableBlock)pieceBeingTouched);
			}
		}
	}
	
	private void getHurt(PushableBlock hit)
	{
		hit.isDestroyed = true;
		health--;
		if (health > 0)
		{
			SoundPlayer.gluttonHurtSFX.play();
		}
		else if (health == 0)
		{
			SoundPlayer.gluttonDeathSFX.play();
		}
	}

}
