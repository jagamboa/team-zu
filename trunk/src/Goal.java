
public class Goal extends Tile 
{
	private boolean goalState;
	
	// Creates a new Goal with the goal state set to false.
	// This is the Tile that the player must stand on in order
	// to complete the level.
	public Goal(int gridX, int gridY) 
	{
		super(gridX, gridY);
		
		goalState = false;
	}
	
	// Sets the grid's goal state to true if this Tile's goal
	// state is true.
	@Override
	public void update(Grid grid) 
	{
		if (goalState)
			grid.setGoalState();
	}

	// If the Girl is on top of this piece then
	// the game is in a goal state.
	@Override
	public void hasOnTop(Piece piece) 
	{
		if (piece instanceof Girl)
			goalState = true;
		
		super.hasOnTop(piece);
	}

	// Pieces can move on top of the Goal Tile
	@Override
	public boolean canMoveOnto() 
	{
		return true;
	}

}
