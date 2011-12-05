
public class Girl extends Piece implements Playable
{
	private boolean isAlive;
	
	// Creates a new Girl object.
	// The Girl is the default piece that the player
	// controls.
	public Girl(int gridX, int gridY)
	{
		super(gridX, gridY);
		isAlive = true;
	}
	
	// returns whether the girl is alive
	public boolean isAlive()
	{
		return isAlive;
	}
	
	// kills the Girl
	public void die()
	{
		isAlive = false;
	}
	
	// The Girl is not pushable
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// When the Girl is clicked, it sets the player's currently
	// selected piece back to the girl
	@Override
	public void onClick(Grid grid, Player player) 
	{
		player.setCurrentPiece(this);
	}

	@Override
	public void isOnTopOf(Tile tile) 
	{
		if (tile instanceof Pit && !Forsaken.FROST)
		{
			die();
			SoundPlayer.fallingSFX.play();
		}
	}

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
			SoundPlayer.keyPickupSFX.play();
		}
	}

}
