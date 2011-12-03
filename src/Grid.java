
public class Grid 
{
	// constants
	public static final int WIDTH = 24;
	public static final int HEIGHT = 20;
	public static final int SQUARE_DIMENSIONS = 32;
	
	// private variables
	private Square[][] grid = new Square[WIDTH][HEIGHT];
	private boolean goalState;
	
	// this is the Boundary Piece that Pieces bump into
	// when they reach the edges of the Grid
	private Boundary outerWall;
	
	public Grid()
	{
		for (int x = 0; x < WIDTH; x++)
		{
			for (int y = 0; y < HEIGHT; y++)
			{
				grid[x][y] = new Square(x, y);
			}
		}
		
		goalState = false;
		outerWall = new Boundary(-1, -1);
	}
	
	// sets down a specified tile at location (x,y)
	public void setTileDown(Tile tile)
	{
		grid[tile.getX()][tile.getY()].setTile(tile);
	}
	
	// sets down a specified piece at location (x,y)
	public void setPieceDown(Piece piece)
	{
		if (piece != null)
			grid[piece.getX()][piece.getY()].setPiece(piece);
	}
	
	// erases the Piece at (x, y)
	public void erasePiece(int x, int y)
	{
		grid[x][y].setPiece(null);
	}
	
	// returns the tile at location (x,y)
	public Tile getTileAt(int x, int y)
	{
		if (x < 0 || y < 0 || x >= Grid.WIDTH || y >= Grid.HEIGHT)
			return null;
		else
			return grid[x][y].getTile();
	}
	
	// returns the piece at location (x,y)
	public Piece getPieceAt(int x, int y)
	{
		if (x < 0 || y < 0 || x >= Grid.WIDTH || y >= Grid.HEIGHT)
			return outerWall;
		else
		{
			return grid[x][y].getPiece();
		}
	}
	
	// returns whether the grid is in a goal state
	public boolean isGoalState()
	{
		return goalState;
	}
	
	// Sets the goal state to true.  To be called by the Goal Tile.
	public void setGoalState()
	{
		goalState = true;
	}
	
	
	// calls update on every square of the grid
	public void update()
	{
		// update each square
		for (int x = 0; x < WIDTH; x++)
		{
			for (int y = 0; y < HEIGHT; y++)
			{
				grid[x][y].update(this);
			}
		}
		
		// check for pieces that moved and move them to the appropriate
		// grid location
		for (int x = 0; x < WIDTH; x++)
		{
			for (int y = 0; y < HEIGHT; y++)
			{
				Piece piece = grid[x][y].getPiece();
				if (piece != null)
				{
					if (piece.getX() != x || piece.getY() != y)
					{
						Piece pushedPiece = grid[piece.getX()][piece.getY()].getPiece();
						
						if (pushedPiece != null)
						{
							grid[pushedPiece.getX()][pushedPiece.getY()].setPiece(pushedPiece);
						}
						
						grid[piece.getX()][piece.getY()].setPiece(piece);
						grid[x][y].setPiece(null);
						
						// update the changed squares
//						if (pushedPiece != null)
//							grid[pushedPiece.getX()][pushedPiece.getY()].update(this);
//						grid[piece.getX()][piece.getY()].update(this);
//						grid[x][y].update(this);
					}
				}
			}
		}
	}
	
	// used to move the piece currently controlled by the player
	// in direction d
	public void movePiece(int pieceX, int pieceY, Direction d)
	{
		// if indices are out of the grid then return
		if (pieceX < 0 || pieceX > Grid.WIDTH - 1 || pieceY < 0 || pieceY > Grid.HEIGHT - 1)
			return;
		
		// if the piece can't move right now then return
		if (grid[pieceX][pieceY].getPiece() == null || !grid[pieceX][pieceY].getPiece().canMove())
			return;
		
		// if the piece is standing on top of an arrow panel then don't accept other directional
		// input
		Tile t = grid[pieceX][pieceY].getTile();
		if (t instanceof ArrowPanel && ((ArrowPanel)t).getDirection() != d)
			return;
		
		// if this piece is being launched then don't accept any other directional inputs
		if (grid[pieceX][pieceY].getPiece().isBeingLaunched()
				&& grid[pieceX][pieceY].getPiece().getLaunchedDirection() != d)
			return;
			
		if (grid[pieceX][pieceY].getPiece() instanceof Glutton)
		{
			moveGlutton(pieceX, pieceY, d);
			return;
		}
		
		if (canMoveToSquare(pieceX, pieceY, d))
		{
			claimSquare(pieceX, pieceY, d);
			grid[pieceX][pieceY].getPiece().move(d);
		}
		else
		{
			Piece p = getPieceInWay(pieceX, pieceY, d);
			
			if (p != null)
			{
				grid[pieceX][pieceY].getPiece().touch(p);
				
				// REWORK THIS:  make it so that if two pieces are being launched in the
				// same direction then they don't collide
				//
				// only allow Girl and HelperCharacters to push blocks and pick up keys
				if ((grid[pieceX][pieceY].getPiece() instanceof Playable))
				{
					if (p.isPushable() && canMoveToSquare(p.getX(), p.getY(), d))
					{
						if (!grid[pieceX][pieceY].getPiece().isBeingLaunched()
								&& !(grid[p.getX()][p.getY()].getTile() instanceof ArrowPanel))
						{
							claimSquare(p.getX(), p.getY(), d);
							grid[pieceX][pieceY].getPiece().move(d);
							p.move(d);
							SoundPlayer.blockPushSFX.play();
						}
						else if (!p.isBeingLaunched() || p.getLaunchedDirection() != grid[pieceX][pieceY].getPiece().getLaunchedDirection())
						{
							grid[pieceX][pieceY].getPiece().stop();
						}
					}
					else if (p instanceof Key || p instanceof Hazard)
					{
						grid[pieceX][pieceY].getPiece().move(d);
					}
					else
					{
						grid[pieceX][pieceY].getPiece().stop();
					}
				}
				else
				{
					grid[pieceX][pieceY].getPiece().stop();
				}
			}
			else
			{	
				Piece pieceMovingInWay = getPieceMovingInWay(pieceX, pieceY, d);
				
				if (pieceMovingInWay != null)
				{
					if (grid[pieceX][pieceY].getPiece() instanceof Playable && pieceMovingInWay instanceof Hazard)
					{
						grid[pieceX][pieceY].getPiece().move(d);
					}
					else if (pieceMovingInWay instanceof Playable && grid[pieceX][pieceY].getPiece() instanceof Hazard)
					{
						grid[pieceX][pieceY].getPiece().move(d);
					}
					else
					{
						grid[pieceX][pieceY].getPiece().touch(pieceMovingInWay);
						grid[pieceX][pieceY].getPiece().stop();
					}
				}
				else
				{	
					grid[pieceX][pieceY].getPiece().touch(outerWall);
					grid[pieceX][pieceY].getPiece().stop();
				}
			}
		}
	}

	// Special case for moving the glutton piece
	private void moveGlutton(int pieceX, int pieceY, Direction d)
	{
		int x = pieceX;
		int y = pieceY;
		
		if (d == Direction.Up)
			y--;
		else if (d == Direction.Down)
			y++;
		else if (d == Direction.Left)
			x--;
		else if (d == Direction.Right)
			x++;
		
		if (canMoveToSquare(x, y, d))
		{
			claimSquare(pieceX, pieceY, d);
			grid[pieceX][pieceY].getPiece().move(d);
		}
		else
		{
			Piece p = getPieceInWay(x, y, d);
			
			if (p != null)
			{
				grid[pieceX][pieceY].getPiece().touch(p);
				grid[pieceX][pieceY].getPiece().stop();
				
				if (p instanceof Playable)
					grid[pieceX][pieceY].getPiece().move(d);
			}
			else
			{	
				Piece pieceMovingInWay = getPieceMovingInWay(pieceX, pieceY, d);
				
				if (pieceMovingInWay != null)
				{
					if (pieceMovingInWay instanceof Playable)
					{
						grid[pieceX][pieceY].getPiece().move(d);
					}
					else
					{
						grid[pieceX][pieceY].getPiece().touch(pieceMovingInWay);
						grid[pieceX][pieceY].getPiece().stop();
					}
				}
				else
				{	
					grid[pieceX][pieceY].getPiece().touch(outerWall);
					grid[pieceX][pieceY].getPiece().stop();
				}
			}
		}
	}
	
	// Returns true if a piece (starting at initX,initY) can move one space in the
	// direction of movement.  Called right before a movement is going to take place
	private boolean canMoveToSquare(int initX, int initY, Direction directionOfMovement)
	{
		if (directionOfMovement == Direction.Up)
		{
			if (initY - 1 < 0 || grid[initX][initY - 1].isBeingMovedTo())
			{
				return false;
			}
			
			return grid[initX][initY - 1].canMoveOnto();
		}
		else if (directionOfMovement == Direction.Down)
		{
			if (initY + 1 > HEIGHT - 1 || grid[initX][initY + 1].isBeingMovedTo())
			{
				return false;
			}
			
			return grid[initX][initY + 1].canMoveOnto();
		}
		else if (directionOfMovement == Direction.Left)
		{
			if (initX - 1 < 0 || grid[initX - 1][initY].isBeingMovedTo())
			{
				return false;
			}
			
			return grid[initX - 1][initY].canMoveOnto();
		}
		else if (directionOfMovement == Direction.Right)
		{
			if (initX + 1 > WIDTH - 1 || grid[initX + 1][initY].isBeingMovedTo())
			{
				return false;
			}
			
			return grid[initX + 1][initY].canMoveOnto();
		}
		else
		{
			return false;
		}
	}
	
	// Returns the piece that is blocking the piece at (initX,initY) from moving
	// given the direction that piece is trying to move.  Returns null if there is
	// no piece in front of the moving piece or if the moving piece is trying to 
	// move off the grid.  This function is used to determine if the player is 
	// trying to push a piece.
	private Piece getPieceInWay(int initX, int initY, Direction directionOfMovement)
	{
		if (initX < 0 || initY < 0 || initX > Grid.WIDTH || initY > Grid.HEIGHT)
			return null;
		
		if (directionOfMovement == Direction.Up)
		{
			if (initY - 1 < 0)
				return null;
			if (grid[initX][initY - 1].canMoveOnto())
				return null;
			
			return grid[initX][initY - 1].getPiece();
		}
		else if (directionOfMovement == Direction.Down)
		{
			if (initY + 1 > HEIGHT - 1)
				return null;
			if (grid[initX][initY + 1].canMoveOnto())
				return null;
			
			return grid[initX][initY + 1].getPiece();
		}
		else if (directionOfMovement == Direction.Left)
		{
			if (initX - 1 < 0)
				return null;
			if (grid[initX - 1][initY].canMoveOnto())
				return null;
			
			return grid[initX - 1][initY].getPiece();
		}
		else if (directionOfMovement == Direction.Right)
		{
			if (initX + 1 > WIDTH - 1)
				return null;
			if (grid[initX + 1][initY].canMoveOnto())
				return null;
			
			return grid[initX + 1][initY].getPiece();
		}
		else
		{
			return null;
		}
	}
	
	private Piece getPieceMovingInWay(int initX, int initY, Direction directionOfMovement)
	{
		if (directionOfMovement == Direction.Up)
		{
			if (initY - 1 < 0 || !grid[initX][initY - 1].canMoveOnto())
				return null;
			
			return grid[initX][initY - 1].getPieceMovingToThis();
		}
		else if (directionOfMovement == Direction.Down)
		{
			if (initY + 1 > HEIGHT - 1 || !grid[initX][initY + 1].canMoveOnto())
				return null;
			
			return grid[initX][initY + 1].getPieceMovingToThis();
		}
		else if (directionOfMovement == Direction.Left)
		{
			if (initX - 1 < 0 || !grid[initX - 1][initY].canMoveOnto())
				return null;

			return grid[initX - 1][initY].getPieceMovingToThis();
		}
		else if (directionOfMovement == Direction.Right)
		{
			if (initX + 1 > WIDTH - 1 || !grid[initX + 1][initY].canMoveOnto())
				return null;
			
			return grid[initX + 1][initY].getPieceMovingToThis();
		}
		else
		{
			return null;
		}
	}
	
	private void claimSquare(int initX, int initY, Direction directionOfMovement)
	{
		if (directionOfMovement == Direction.Up)
		{
			if (!(initY - 1 < 0) && !grid[initX][initY - 1].isBeingMovedTo()
					&& grid[initX][initY - 1].canMoveOnto())
			{
				grid[initX][initY - 1].setIsBeingMovedTo(grid[initX][initY].getPiece(), true);
			}
		}
		else if (directionOfMovement == Direction.Down)
		{
			if (!(initY + 1 > HEIGHT - 1) && !grid[initX][initY + 1].isBeingMovedTo()
					&& grid[initX][initY + 1].canMoveOnto())
			{
				grid[initX][initY + 1].setIsBeingMovedTo(grid[initX][initY].getPiece(), true);
			}
		}
		else if (directionOfMovement == Direction.Left)
		{
			if (!(initX - 1 < 0) && !grid[initX - 1][initY].isBeingMovedTo()
					&& grid[initX - 1][initY].canMoveOnto())
			{
				grid[initX - 1][initY].setIsBeingMovedTo(grid[initX][initY].getPiece(), true);
			}
		}
		else if (directionOfMovement == Direction.Right)
		{
			if (!(initX + 1 > WIDTH - 1) && !grid[initX + 1][initY].isBeingMovedTo()
					&& grid[initX + 1][initY].canMoveOnto())
			{
				grid[initX + 1][initY].setIsBeingMovedTo(grid[initX][initY].getPiece(), true);
			}
		}
	}
	
}
