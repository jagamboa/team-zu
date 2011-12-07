
public class ArrowPanel extends Tile 
{

	private Direction direction;
	
	// Constructor for an Arrow Panel which takes in the Direction
	// it's pointing
	public ArrowPanel(int gridX, int gridY, Direction direction) 
	{
		super(gridX, gridY);
		
		this.direction = direction;
	}

	// The Arrow Panel has nothing to update
	@Override
	public void update(Grid grid) 
	{

	}

	
	// If a piece is on top of the Arrow Panel then
	// that piece should be launched in the direction
	// of this Arrow Panel
	@Override
	public void hasOnTop(Piece piece) 
	{
		if (piece != null)
		{
			if (piece != lastPieceOnThis)
			{
				SoundPlayer.arrowPanelSFX.play();
				
				if (piece instanceof Glutton)
					Tilesets.gluttonSprite.play("spin");
			}
			
			piece.launch(direction);
		}
		
		super.hasOnTop(piece);
	}

	// Pieces can move onto an Arrow Panel
	@Override
	public boolean canMoveOnto() 
	{
		return true;
	}
	
	// Returns the direction this Arrow Panel is facing.
	// Used for drawing the sprite with the correct rotation.
	public Direction getDirection()
	{
		return direction;
	}

}
