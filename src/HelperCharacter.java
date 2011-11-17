
public class HelperCharacter extends Piece implements Playable
{
	private boolean isAlive;
	
	// Creates a new Helper Character, a secondary character
	// in which the player can control by selecting it with the mouse.
	// Functions the same as Girl except getting this piece to the Goal
	// does not allow the player to beat the level and this Piece being killed
	// does not force the player to restart the level.
	public HelperCharacter(int gridX, int gridY) 
	{
		super(gridX, gridY);
		
		isAlive = true;
	}
	
	@Override
	public void update(Grid grid)
	{
		super.update(grid);
		
		if (!isAlive)
			isDestroyed = true;
	}

	// A Helper Character is not pushable
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// Sets the current piece that the player controls
	// to this piece.
	@Override
	public void onClick(Grid grid, Player player) 
	{
		player.setCurrentPiece(this);
	}

	// Should function the same as the Girl except for the
	// conditions specified above the constructor.
	@Override
	public void isOnTopOf(Tile tile) 
	{
		if (tile instanceof Pit)
			die();
	}

	// Should function the same as the Girl except for the
	// conditions specified above the constructor.
	@Override
	public void touch(Piece pieceBeingTouched) 
	{
		// TODO Auto-generated method stub
		if (pieceBeingTouched instanceof Door)
		{
			if (keyCount > 0)
			{
				((Door)pieceBeingTouched).unlock();
				keyCount--;
			}
		}
		else if (pieceBeingTouched instanceof Key)
		{
			keyCount++;
		}
		else if (pieceBeingTouched instanceof Spikeball)
		{
			
		}
	}
	
	// Returns whether this helper character is still alive
	public boolean isAlive()
	{
		return isAlive;
	}
	
	// Kills this Helper Character
	public void die()
	{
		isAlive = false;
	}

}
