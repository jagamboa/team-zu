
public abstract class Piece 
{
	// the grid (x,y) coordinates
	private int gridX;
	private int gridY;
	
	// the actual pixel (x,y) coordinates
	private int pixelX;
	private int pixelY;
	
	// Used to determine when this piece is destroyed.
	// This is set by classes derived from this class
	protected boolean isDestroyed;
	
	// Used to keep track of how many Keys this piece has
	protected int keyCount;
	
	// variables used for movement animation
	private boolean isMoving;
	private Direction lastDirectionMoved;
	private long timeMoveStart;
	private long currentMoveTime;
	private int pixelMovement;
	private Direction launchedDirection = null;
	
	// the amount of time in milliseconds 
	// it takes for the piece to move one Square
	private double moveDuration;
	
	private double pieceMoveDuration = 200;
	
	private double launchSpeed = 100;
	
	public Piece(int gridX, int gridY)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		
		pixelX = gridX * Grid.SQUARE_DIMENSIONS;
		pixelY = gridY * Grid.SQUARE_DIMENSIONS;
		
		isDestroyed = false;
		
		keyCount = 0;
		
		isMoving = false;
		lastDirectionMoved = null;
		timeMoveStart = 0;
		currentMoveTime = 0;
		
		moveDuration = pieceMoveDuration;
	}
	
	public Piece(int gridX, int gridY, int pieceMoveDuration, int launchSpeed)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		
		pixelX = gridX * Grid.SQUARE_DIMENSIONS;
		pixelY = gridY * Grid.SQUARE_DIMENSIONS;
		
		isDestroyed = false;
		
		keyCount = 0;
		
		isMoving = false;
		lastDirectionMoved = Direction.Down;
		timeMoveStart = 0;
		currentMoveTime = 0;
		
		this.pieceMoveDuration = pieceMoveDuration;
		this.launchSpeed = launchSpeed;
		
		moveDuration = pieceMoveDuration;
	}
	
	// Moves the piece in direction d (if it's not already moving in another
	// direction).  This function should be called AFTER checking whether it
	// is ok to actually move to that space.
	public void move(Direction d)
	{
		// if you're already moving then don't move again
		// instead set that move to the next move if accepting more
		// moves
		if (isMoving)
		{	
			return;
		}
		
		// if you're in the middle of a launch then don't take move commands
		// in another direction
		if (launchedDirection != null && launchedDirection != d)
			return;
		
		// begin motion in direction d
		lastDirectionMoved = d;
		isMoving = true;
		timeMoveStart = System.currentTimeMillis();
	}
	
	// If the piece is moving then update the movement animation.
	// If the movement is finished then end the animation and update
	// the grid location.
	// This function should be OVERRIDDEN to include the updates specific to
	// the particular piece.  (be sure to call this function afterwards with a
	// super.update(grid) function call)
	public void update(Grid grid)
	{	
		if (isMoving)
		{	
			currentMoveTime = System.currentTimeMillis();
			
			pixelMovement = (int)Math.ceil((Math.min(1, (currentMoveTime - timeMoveStart)/moveDuration) * Grid.SQUARE_DIMENSIONS));
			//pixelMovement *= 10;
			
			// update pixel position
			if (lastDirectionMoved == Direction.Up)
			{
				pixelY = gridY * Grid.SQUARE_DIMENSIONS - pixelMovement;
			}
			else if (lastDirectionMoved == Direction.Down)
			{
				pixelY = gridY * Grid.SQUARE_DIMENSIONS + pixelMovement;
			}
			else if (lastDirectionMoved == Direction.Left)
			{
				pixelX = gridX * Grid.SQUARE_DIMENSIONS - pixelMovement;
			}
			else if (lastDirectionMoved == Direction.Right)
			{
				pixelX = gridX * Grid.SQUARE_DIMENSIONS + pixelMovement;
			}
			
			// move to next grid space if move is over
			if (currentMoveTime >= (timeMoveStart + moveDuration))
			{
				if (lastDirectionMoved == Direction.Up)
				{
					gridY--;
					//pixelY = gridY * Grid.SQUARE_DIMENSIONS;
				}
				else if (lastDirectionMoved == Direction.Down)
				{
					gridY++;
					//pixelY = gridY * Grid.SQUARE_DIMENSIONS;
				}
				else if (lastDirectionMoved == Direction.Left)
				{
					gridX--;
					//pixelX = gridX * Grid.SQUARE_DIMENSIONS;
				}
				else if (lastDirectionMoved == Direction.Right)
				{
					gridX++;
					//pixelX = gridX * Grid.SQUARE_DIMENSIONS;
				}
				
				isMoving = false;
			}
		}
		else if (launchedDirection != null)
		{
			grid.movePiece(gridX, gridY, launchedDirection);
		}
	}
	
	// returns the piece's x location on the grid
	public int getX()
	{
		return gridX;
	}
	
	// returns the piece's y location on the grid
	public int getY()
	{
		return gridY;
	}
	
	// returns the piece's pixel x location
	// used for drawing
	public int getPixelX()
	{
		return pixelX;
	}
	
	// returns the piece's pixel y location
	// used for drawing
	public int getPixelY()
	{
		return pixelY;
	}
	
	// returns the last direction this piece moved
	public Direction getLastDirectionMoved()
	{
		return lastDirectionMoved;
	}
	
	// returns whether this piece is moving
	public boolean isMoving()
	{
		return isMoving;
	}
	
	// returns whether or not this peice has
	// been destroyed
	public boolean isDestroyed()
	{
		return isDestroyed;
	}
	
	// returns the number of keys this piece is holding
	public int getKeyCount()
	{
		return keyCount;
	}
	
	// returns whether this piece can accept a move
	public boolean canMove()
	{
		return !isMoving;
	}
	
	// launches the piece in direction d, the piece will
	// continue to move until it hits a solid object
	public void launch(Direction d)
	{
		launchedDirection = d;
		moveDuration = launchSpeed;
	}
	
	// stops a launched piece
	public void stop()
	{
		launchedDirection = null;
		moveDuration = pieceMoveDuration;
	}
	
	// returns true if the piece was stopped in the middle of
	// a launch.
	public boolean isBeingLaunched()
	{
		return launchedDirection != null;
	}
	
	public Direction getLaunchedDirection()
	{
		return launchedDirection;
	}
	
	// this function returns whether the player can
	// push this piece
	public abstract boolean isPushable();
	
	// this function is called when this piece is clicked
	// performing a specialized click function
	public abstract void onClick(Grid grid, Player player);
	
	// Passes in the tile that this piece is on top of so that
	// any necessary actions can take place.
	public abstract void isOnTopOf(Tile tile);
	
	// Called when this piece tries to move into another piece
	public abstract void touch(Piece pieceBeingTouched);
}
