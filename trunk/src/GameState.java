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
	
	// Sprite list
	private Sprite emptyTileSprite;
	private Sprite arrowPanelSprite;
	private Sprite goalSprite;
	private Sprite pitSprite;
	
	private Sprite girlSprite;
	private Sprite boundarySprite;
	private Sprite doorSprite;
	private Sprite helperCharacterSprite;
	private Sprite keySprite;
	private Sprite pushableBlockSprite;
	private Sprite spikeballSprite;

	// initializes the game state with the gameloop so that the
	// game state can signal the gameloop when it is time to change
	// scenes.
	public GameState(Forsaken gameloop)
	{
		this.gameloop = gameloop;
		showingCutscene = false;
		
		// initialize Sprites
		emptyTileSprite = new Sprite(gameloop.getImage("Art/Grass.png"));
		arrowPanelSprite = new Sprite(32, 32);
		arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), 0,0, 32,0);
		arrowPanelSprite.framerate(1);
		goalSprite = new Sprite(gameloop.getImage("Art/Goal.png"));
		pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
		
		girlSprite = new Sprite(30, 35);
		girlSprite.addFrames(gameloop.getImage("Art/belarus2small.png"), 0,0, 30,0, 60,0, 90,0,
																			0,35, 30,35, 60,35, 90,35,
																			0,70, 30,70, 60,70, 90,70,
																			0,105, 30,105, 60,105, 90,105);
		
		girlSprite.defineSequence("spin", 0, 4, 12, 8);
		
		girlSprite.defineSequence("upStand", 12);
		girlSprite.defineSequence("downStand", 0);
		girlSprite.defineSequence("leftStand", 4);
		girlSprite.defineSequence("rightStand", 8);
		
		girlSprite.defineSequence("upMove", 12, 13, 14, 15);
		girlSprite.defineSequence("downMove", 0, 1, 2, 3);
		girlSprite.defineSequence("leftMove", 4, 5, 6, 7);
		girlSprite.defineSequence("rightMove", 8, 9, 10, 11);
		
		girlSprite.framerate(7);
		//girlSprite = new Sprite(gameloop.getImage("Art/Girl.png"));
		boundarySprite = new Sprite(gameloop.getImage("Art/Boundary.png"));
		doorSprite = new Sprite(gameloop.getImage("Art/Door.png"));
		helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
		keySprite = new Sprite(gameloop.getImage("Art/Key.png"));
		pushableBlockSprite = new Sprite(gameloop.getImage("Art/PushableBlock.png"));
		spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
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
		
		SoundPlayer.playBGM(bgm);
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
		boolean wasMoving = player.getCurrentPiece().isMoving();
		Direction prevDirection = player.getCurrentPiece().getLastDirectionMoved();
		
		grid.movePiece(player.getX(), player.getY(), d);
		
		if (player.getCurrentPiece() instanceof Girl)
		{
			Girl girl = (Girl)player.getCurrentPiece();
			
			if (girl.getLastDirectionMoved() == Direction.Up)
			{
				girlSprite.play("upMove");
				
				if (!wasMoving || prevDirection != Direction.Up)
				{
					girlSprite.setToFrame(12);
				}
			}
			else if (girl.getLastDirectionMoved() == Direction.Down)
			{
				girlSprite.play("downMove");
				
				if (!wasMoving || prevDirection != Direction.Down)
				{
					girlSprite.setToFrame(0);
				}
			}
			else if (girl.getLastDirectionMoved() == Direction.Left)
			{	
				girlSprite.play("leftMove");
				
				if (!wasMoving || prevDirection != Direction.Left)
				{
					girlSprite.setToFrame(4);
				}
			}
			else if (girl.getLastDirectionMoved() == Direction.Right)
			{	
				girlSprite.play("rightMove");
				
				if (!wasMoving || prevDirection != Direction.Right)
				{
					girlSprite.setToFrame(8);
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
			girlSprite.play("spin");
		}
		else if (girl.getLastDirectionMoved() == Direction.Up)
		{
			//girlSprite.setToFrame(12);
			
			if (!girl.isMoving())
				girlSprite.play("upStand");
		}
		else if (girl.getLastDirectionMoved() == Direction.Down)
		{
			//girlSprite.setToFrame(0);
			
			if (!girl.isMoving())
				girlSprite.play("downStand");
		}
		else if (girl.getLastDirectionMoved() == Direction.Left)
		{
			//girlSprite.setToFrame(4);
			
			if (!girl.isMoving())
				girlSprite.play("leftStand");
		}
		else if (girl.getLastDirectionMoved() == Direction.Right)
		{
			girlSprite.setToFrame(8);
			
			if (!girl.isMoving())
				girlSprite.play("rightStand");
		}
		
		grid.update();
		player.update();
		
		if (grid.isGoalState())
		{
			// advance to next level
			try {
				loadLevel("Levels/SpikeTrapTest1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (!player.girlIsAlive())
		{
			// TODO
			// reset level
			try {
				loadLevel("Levels/SpikeTrapTest1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
					emptyTileSprite.position(tile.getPixelX(), tile.getPixelY());
					emptyTileSprite.draw();
				}
				else if (tile instanceof ArrowPanel)
				{
					arrowPanelSprite.position(tile.getPixelX(), tile.getPixelY());
					
					if (((ArrowPanel)tile).getDirection() == Direction.Down)
						arrowPanelSprite.rotate(180);
					else if (((ArrowPanel)tile).getDirection() == Direction.Left)
						arrowPanelSprite.rotate(270);
					else if (((ArrowPanel)tile).getDirection() == Direction.Right)
						arrowPanelSprite.rotate(90);
					
					arrowPanelSprite.draw();
				}
				else if (tile instanceof Goal)
				{
					goalSprite.position(tile.getPixelX(), tile.getPixelY());
					goalSprite.draw();
				}
				else if (tile instanceof Pit)
				{
					pitSprite.position(tile.getPixelX(), tile.getPixelY());
					pitSprite.draw();
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
					girlSprite.position(piece.getPixelX(), piece.getPixelY());
					girlSprite.draw();
				}
				else if (piece instanceof Boundary)
				{
					boundarySprite.position(piece.getPixelX(), piece.getPixelY());
					boundarySprite.draw();
				}
				else if (piece instanceof Door)
				{
					doorSprite.position(piece.getPixelX(), piece.getPixelY());
					doorSprite.draw();
				}
				else if (piece instanceof HelperCharacter)
				{
					helperCharacterSprite.position(piece.getPixelX(), piece.getPixelY());
					helperCharacterSprite.draw();
				}
				else if (piece instanceof Key)
				{
					keySprite.position(piece.getPixelX(), piece.getPixelY());
					keySprite.draw();
				}
				else if (piece instanceof PushableBlock)
				{
					pushableBlockSprite.position(piece.getPixelX(), piece.getPixelY());
					pushableBlockSprite.draw();
				}
				else if (piece instanceof Spikeball || piece instanceof SpikeTrap)
				{
					spikeballSprite.position(piece.getPixelX(), piece.getPixelY());
					spikeballSprite.draw();
				}
			}
		}
	}
}
