
public class PushableBlock extends Piece 
{

	// Constructs a new Pushable Block.  This Block
	// can be pushed by a player controllable piece.
	// If nothing is in the way of this Block's movement,
	// it can be pushed away from the player controllable piece
	// into the next grid slot.
	public PushableBlock(int gridX, int gridY) 
	{
		super(gridX, gridY);
	}

	// A Pushable Block is pushable
	@Override
	public boolean isPushable() 
	{
		return true;
	}

	// Pushable Blocks do nothing when clicked
	@Override
	public void onClick(Grid grid, Player player) 
	{

	}

	// When a Pushable Block is on top of a Pit the Block
	// should be destroyed
	@Override
	public void isOnTopOf(Tile tile) 
	{
		if (tile instanceof Pit)
			isDestroyed = true;
	}

	@Override
	public void touch(Piece pieceBeingTouched) 
	{
		if (pieceBeingTouched instanceof Glutton)
		{
			if (isBeingLaunched())
			{
				pieceBeingTouched.touch(this);
			}
		}
		

	}

}
