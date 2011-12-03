import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.DataFormatException;

import ucigame.*;

public class GameState 
{
	private Grid grid;
	private Player player;
	private Forsaken gameloop;
	private boolean showingCutscene;
	private String loadedLevel;
	private String levelName;
	


	// initializes the game state with the gameloop so that the
	// game state can signal the gameloop when it is time to change
	// scenes.
	public GameState(Forsaken gameloop)
	{
		this.gameloop = gameloop;
		showingCutscene = false;
	}
	
	// sets a Tile down onto this grid
	public void setTileDown(Tile tile)
	{
		grid.setTileDown(tile);
	}
	
	// gets the Tile at (x, y)
	public Tile getTileAt(int x, int y)
	{
		return grid.getTileAt(x, y);
	}
	
	// sets a Piece down onto this grid
	public void setPieceDown(Piece piece, int column, int row)
	{
		if (piece == null)
			grid.erasePiece(column, row);
		else
			grid.setPieceDown(piece);
	}
	
	// gets the Piece at (x, y)
	public Piece getPieceAt(int x, int y)
	{
		return grid.getPieceAt(x, y);
	}
	
	// returns the Girl's X coordinate
	public int getGirlX()
	{
		return player.getGirl().getX();
	}
	
	// returns the girl's Y coordinate
	public int getGirlY()
	{
		return player.getGirl().getY();
	}
	
	// returns the girl
	public Girl getGirl()
	{
		return player.getGirl();
	}
	
	// returns the name of the level
	public String getLevelName()
	{
		return levelName;
	}
	
	// writes the level into a file called "fileOut"
	public void writeLevel(String fileOut) throws IOException, DataFormatException
	{
		FileWriter outWriter = new FileWriter(fileOut);
		
		IO.begin();
		
		outWriter.write(IO.version);
		
		outWriter.write(Grid.WIDTH);
		outWriter.write(Grid.HEIGHT);
		
		outWriter.write(SoundPlayer.currentBGM);
		
		for (int x = 0; x < Grid.WIDTH; x++)
			for (int y = 0; y < Grid.HEIGHT; y++)
			{
				IO.writePiece(outWriter, grid.getPieceAt(x, y));
				IO.writeTile(outWriter, grid.getTileAt(x, y));
			}
		
		outWriter.close();
	}
	
	// loads a level stored in a file "fileIn"
	// files are stored in .txt format
	public void loadLevel(String fileIn) throws IOException, DataFormatException
	{
//		loadTestLevel();
		
		loadedLevel = fileIn;
		levelName = fileIn.substring(fileIn.lastIndexOf('/') + 1, fileIn.length());
		
		grid = new Grid();
		
		FileReader inReader = new FileReader(fileIn);
		
		IO.begin();
		
		int version = inReader.read();
		
		if (version != IO.version)
			throw new DataFormatException("File Version mismatch!\nFile version: " 
					+ version + ", Expected version: " + IO.version);
		
		int width = inReader.read();
		int height = inReader.read();
		
		int bgm = inReader.read();
		
		if (width != Grid.WIDTH || height != Grid.HEIGHT)
			throw new DataFormatException("Grid dimentions don't match! input = (" + width + 
					", " + height + "); expected = (" + Grid.WIDTH + ", " + Grid.HEIGHT + ")");
		
		for (int x = 0; x < Grid.WIDTH; x++)
			for (int y = 0; y < Grid.HEIGHT; y++)
			{
				grid.setPieceDown(IO.readPiece(inReader, x, y));
				grid.setTileDown(IO.readTile(inReader, x, y));
			}
		
		player = new Player(IO.getGirl());
		Tilesets.girlSprite.play("downStand");
		
		SoundPlayer.playBGM(bgm);
	}
	
	public void loadEmptyLevel()
	{
		grid = new Grid();
	}
	
	// Creates a copy of this GameState and returns it
	public GameState copy(Forsaken gameloop) throws IOException, DataFormatException
	{
		GameState gs = new GameState(gameloop);
		
		String tempFileName = "Levels/temp/temp_" + System.currentTimeMillis();
		
		writeLevel(tempFileName);
		
		gs.loadLevel(tempFileName);
		
		new File(tempFileName).deleteOnExit();
		
		return gs;
	}
	
	private void loadTestLevel()
	{
		grid = new Grid();
		
		grid.setTileDown(new ArrowPanel(20, 5, Direction.Down));
		grid.setTileDown(new Goal(0, 0));
		grid.setTileDown(new Pit(0, 23));
		
		grid.setPieceDown(new Spikeball(5, 5, Direction.Left));
		grid.setPieceDown(new SpikeTrap(5, 9));
		
		grid.setPieceDown(new HelperCharacter(2, 8));
		grid.setPieceDown(new HelperCharacter(27, 6));
		grid.setPieceDown(new HelperCharacter(18, 4));
		
		grid.setPieceDown(new Key(2, 0));
		grid.setPieceDown(new Key(2, 15));
		grid.setPieceDown(new Key(4, 9));
		grid.setPieceDown(new Key(6, 9));
		grid.setPieceDown(new Key(4, 7));
		grid.setPieceDown(new Key(14, 9));
		grid.setPieceDown(new Key(18, 5));
		grid.setPieceDown(new Key(25, 20));
		grid.setPieceDown(new Key(7, 1));
		
		grid.setPieceDown(new Door(5, 10));
		grid.setPieceDown(new Door(3, 17));
		grid.setPieceDown(new Door(6, 17));
		grid.setPieceDown(new Door(17, 19));
		grid.setPieceDown(new Door(5, 8));
		grid.setPieceDown(new Door(9, 14));
		grid.setPieceDown(new Door(14, 8));
		grid.setPieceDown(new Door(7, 11));
		grid.setPieceDown(new Door(11, 15));
		grid.setPieceDown(new Door(25, 13));
		grid.setPieceDown(new Door(0, 1));
		grid.setPieceDown(new Door(1, 0));
		
//		grid.setTileDown(new ArrowPanel(5, 3, Direction.Down));
//		grid.setTileDown(new ArrowPanel(5, 20, Direction.Right));
//		grid.setTileDown(new ArrowPanel(6, 20, Direction.Right));
//		grid.setTileDown(new ArrowPanel(20, 20, Direction.Up));
//		grid.setTileDown(new ArrowPanel(20, 3, Direction.Left));
//		grid.setTileDown(new Goal(12, 12));
//		grid.setTileDown(new Pit(0, 7));
//		grid.setTileDown(new Pit(8, 9));
////		grid.setTileDown(new Pit(6, 20));
//		
//		grid.setPieceDown(new Boundary(2, 2));
//		grid.setPieceDown(new Door(5, 11));
//		grid.setPieceDown(new HelperCharacter(3, 2));
//		grid.setPieceDown(new Key(4, 4));
//		grid.setPieceDown(new PushableBlock(8, 2));
//		grid.setPieceDown(new PushableBlock(11, 2));
//		grid.setPieceDown(new Spikeball(6, 2, Direction.Down));
//		grid.setPieceDown(new SpikeTrap(8, 8));
////		grid.setPieceDown(new Spikeball(1, 11, Direction.Right));
//		
//		Girl girl = new Girl(0,0);
		
		Girl girl = new Girl(20, 20);
		grid.setPieceDown(girl);
		
		player = new Player(girl);
		
		SoundPlayer.playBGM(SoundPlayer.hopefulUnderstanding);
		
//		try {
//			writeLevel("Levels/LotsOfKeys");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DataFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	// This function is called when the arrow keys are pressed.
	// Moves the piece that the player currently controls.
	public void move(Direction d)
	{
		// return if the player is currently controlling no piece
		if (player.getCurrentPiece() == null)
			return;
		
		boolean wasMoving = player.getCurrentPiece().isMoving();
		Direction prevDirection = player.getCurrentPiece().getLastDirectionMoved();
		
		grid.movePiece(player.getX(), player.getY(), d);
		
		if (player.getCurrentPiece() instanceof Girl)
		{
			Girl girl = (Girl)player.getCurrentPiece();
			
			if (girl.getLastDirectionMoved() == Direction.Up)
			{
				Tilesets.girlSprite.play("upMove");
				
				if (!wasMoving || prevDirection != Direction.Up)
				{
					Tilesets.girlSprite.setToFrame(12);
				}
			}
			else if (girl.getLastDirectionMoved() == Direction.Down)
			{
				Tilesets.girlSprite.play("downMove");
				
				if (!wasMoving || prevDirection != Direction.Down)
				{
					Tilesets.girlSprite.setToFrame(0);
				}
			}
			else if (girl.getLastDirectionMoved() == Direction.Left)
			{	
				Tilesets.girlSprite.play("leftMove");
				
				if (!wasMoving || prevDirection != Direction.Left)
				{
					Tilesets.girlSprite.setToFrame(4);
				}
			}
			else if (girl.getLastDirectionMoved() == Direction.Right)
			{	
				Tilesets.girlSprite.play("rightMove");
				
				if (!wasMoving || prevDirection != Direction.Right)
				{
					Tilesets.girlSprite.setToFrame(8);
				}
			}
		}
	}
	
	// This function is called when the player clicks the
	// left mouse button.  It takes in the x and y coordinates
	// of the click and either advances the cut scene or
	// attempts to select a piece, depending on the current
	// game state.
	public void onClick(int x, int y)
	{
		if (showingCutscene)
		{
			// TODO
			// advance cutscene
		}
		else
		{
			Piece clickedPiece = grid.getPieceAt(x / Grid.SQUARE_DIMENSIONS, y / Grid.SQUARE_DIMENSIONS);
			
			if (clickedPiece != null)
				clickedPiece.onClick(grid, player);
		}
	}
	
	// Updates the game state, including the player and the grid.
	// Also checks if the player is in a goal state or if the player
	// lost the level.
	public void update()
	{
		Girl girl = player.getGirl();
		
		if (girl.isBeingLaunched())
		{
			Tilesets.girlSprite.play("spin");
		}
		else if (girl.getLastDirectionMoved() == Direction.Up)
		{
			//girlSprite.setToFrame(12);
			
			if (!girl.isMoving())
				Tilesets.girlSprite.play("upStand");
		}
		else if (girl.getLastDirectionMoved() == Direction.Down)
		{
			//girlSprite.setToFrame(0);
			
			if (!girl.isMoving())
				Tilesets.girlSprite.play("downStand");
		}
		else if (girl.getLastDirectionMoved() == Direction.Left)
		{
			//girlSprite.setToFrame(4);
			
			if (!girl.isMoving())
				Tilesets.girlSprite.play("leftStand");
		}
		else if (girl.getLastDirectionMoved() == Direction.Right)
		{
			Tilesets.girlSprite.setToFrame(8);
			
			if (!girl.isMoving())
				Tilesets.girlSprite.play("rightStand");
		}
		
		grid.update();
		player.update();
		
		if (grid.isGoalState())
		{
			gameloop.nextGamestate();
		}
		
		if (!player.girlIsAlive())
		{
			// TODO
			// reset level
			restart();
		}
		
		if (grid.getPieceAt(player.getGirl().getX(), player.getGirl().getY()) == null)
		{
			//throw new NullPointerException();
			
			// error, this shouldn't happen
			// TODO:  fix this bug
			
			restart();
		}
	}
	
	public void restart()
	{
		try {
			loadLevel(loadedLevel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// draws each tile and piece in the grid and the player
	public void draw()
	{
		// draw tiles
		for (int x = 0; x < Grid.WIDTH; x++)
		{
			for (int y = 0; y < Grid.HEIGHT; y++)
			{
				Tile tile = grid.getTileAt(x, y);
				
				if (tile instanceof EmptyTile)
				{
					Tilesets.emptyTileSprite.position(tile.getPixelX(), tile.getPixelY());
					Tilesets.emptyTileSprite.draw();
				}
				else if (tile instanceof ArrowPanel)
				{
					Tilesets.arrowPanelSprite.position(tile.getPixelX(), tile.getPixelY());
					
					if (((ArrowPanel)tile).getDirection() == Direction.Down)
						Tilesets.arrowPanelSprite.rotate(180);
					else if (((ArrowPanel)tile).getDirection() == Direction.Left)
						Tilesets.arrowPanelSprite.rotate(270);
					else if (((ArrowPanel)tile).getDirection() == Direction.Right)
						Tilesets.arrowPanelSprite.rotate(90);
					
					Tilesets.arrowPanelSprite.draw();
				}
				else if (tile instanceof Goal)
				{
					Tilesets.goalSprite.position(tile.getPixelX(), tile.getPixelY());
					Tilesets.goalSprite.draw();
				}
				else if (tile instanceof Pit)
				{
					Tilesets.pitSprite.position(tile.getPixelX(), tile.getPixelY());
					Tilesets.pitSprite.draw();
				}
			}
		}
		
		// draw pieces
		for (int x = 0; x < Grid.WIDTH; x++)
		{
			for (int y = 0; y < Grid.HEIGHT; y++)
			{
				Piece piece = grid.getPieceAt(x, y);
				
				if (piece == null)
				{
					continue;
				}
				else if (piece instanceof Girl)
				{	
					Tilesets.girlSprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.girlSprite.draw();
				}
				else if (piece instanceof Boundary)
				{
					Tilesets.boundarySprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.boundarySprite.draw();
				}
				else if (piece instanceof Door)
				{
					Tilesets.doorSprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.doorSprite.draw();
				}
				else if (piece instanceof HelperCharacter)
				{
					Tilesets.helperCharacterSprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.helperCharacterSprite.draw();
				}
				else if (piece instanceof Key)
				{
					Tilesets.keySprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.keySprite.draw();
				}
				else if (piece instanceof PushableBlock)
				{
					Tilesets.pushableBlockSprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.pushableBlockSprite.draw();
				}
				else if (piece instanceof Spikeball)
				{
					Tilesets.spikeballSprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.spikeballSprite.draw();
				}
				else if (piece instanceof SpikeTrap)
				{
					Tilesets.spikeTrapSprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.spikeTrapSprite.draw();
				}
				else if (piece instanceof Glutton)
				{
					Tilesets.gluttonSprite.position(piece.getPixelX(), piece.getPixelY());
					Tilesets.gluttonSprite.draw();
				}
			}
		}
	}
}
