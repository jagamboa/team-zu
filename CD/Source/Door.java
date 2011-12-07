
public class Door extends Piece 
{
	// Constructor for Door
	public Door(int gridX, int gridY) 
	{
		super(gridX, gridY);
	}

	// A Door is not pushable
	@Override
	public boolean isPushable() 
	{
		return false;
	}

	// A Door does nothing when clicked
	@Override
	public void onClick(Grid grid, Player player) 
	{

	}

	// A Door is always on top of an Empty Tile
	// so this function does nothing
	@Override
	public void isOnTopOf(Tile tile) 
	{

	}

	// A Door does nothing when it touches another
	// piece.  (The player needs to be the one to touch it!)
	@Override
	public void touch(Piece pieceBeingTouched) 
	{

	}
	
	// Unlocks the door by setting it as "Destroyed" so that
	// this piece is removed from the grid
	public void unlock()
	{
		isDestroyed = true;
		SoundPlayer.doorOpenSFX.play();
	}

}