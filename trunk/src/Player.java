
public class Player 
{
	private Piece currentPiece;
	private Girl girl;
	
	// pass in as a parameter a Girl object that has already been
	// added to the game grid
	public Player(Girl girl)
	{
		this.girl = girl;
		currentPiece = girl;
		if (currentPiece.isDestroyed);
	}
	
	// returns the grid X coordinate of the current Piece
	public int getX()
	{
		if (currentPiece == null)
			return -1;
		else
			return currentPiece.getX();
	}
	
	// returns the grid Y coordinate of the current Piece
	public int getY()
	{
		if (currentPiece == null)
			return -1;
		else
			return currentPiece.getY();
	}
	
	// sets the current piece that the player can move
	// to the parameter "piece"
	public void setCurrentPiece(Piece piece)
	{
		if (piece instanceof Playable)
			currentPiece = piece;
	}
	
	// returns the currently selected piece
	public Piece getCurrentPiece()
	{
		return currentPiece;
	}
	
	// returns the player's Girl piece
	public Girl getGirl()
	{
		return girl;
	}
	
	// sets the reference to a Girl object
	public void setGirl(Girl girl)
	{
		this.girl = girl;
		currentPiece = girl;
	}
	
	// updates the player, if the current piece was deleted somehow then
	// it is reverted back to the Girl piece
	public void update()
	{
		if (currentPiece != null && currentPiece.isDestroyed)
			currentPiece = null;
	}
	
	// returns whether the girl is alive
	public boolean girlIsAlive()
	{
		return girl.isAlive();
	}
}
