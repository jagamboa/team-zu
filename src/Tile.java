
public abstract class Tile 
{
	// the grid (x,y) coordinates
	private int gridX;
	private int gridY;
	
	// the actual pixel (x,y) coordinates
	private int pixelX;
	private int pixelY;
	
	protected Piece lastPieceOnThis;
	
	public Tile(int gridX, int gridY)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		
		pixelX = gridX * Grid.SQUARE_DIMENSIONS;
		pixelY = gridY * Grid.SQUARE_DIMENSIONS;
	}
	
	// returns the tile's x location on the grid
	public int getX()
	{
		return gridX;
	}
	
	// returns the tile's y location on the grid
	public int getY()
	{
		return gridY;
	}
	
	// returns the tile's pixel x location
	// used for drawing
	public int getPixelX()
	{
		return pixelX;
	}
	
	// returns the tile's pixel y location
	// used for drawing
	public int getPixelY()
	{
		return pixelY;
	}
	
	// performs any updates necessary for this Tile
	// that should be performed every frame
	public abstract void update(Grid grid);
	
	// Passes in the Piece that this Tile has on top of it so that
	// any necessary actions can take place.
	// This function should be overridden by subclasses (be sure to
	// call "super.hasOnTop(piece)" at the end of the overridden version
	// of this function.)
	public void hasOnTop(Piece piece)
	{
		lastPieceOnThis = piece;
	}
	
	// this function returns whether a piece can
	// move on top of this tile
	public abstract boolean canMoveOnto();
	
}
