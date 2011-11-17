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
	static int stage; // 0 for menu, 1 for instructions, 2 for editor, 3 for test level
	
	// Button list
	private Sprite start; // Start game button
	private Sprite instruction; // Instruction button
	private Sprite editor; // Editor starter button
	private Sprite menu; // Button to go back to the menu
	
	// Sprite list
	private Sprite startPicture; // The picture in the menu screen
	private Sprite instructions;  // The actual instructions shown on the instructions screen
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
		stage = 0;
		showingCutscene = false;
		
		// initialize Sprites
		startPicture = new Sprite(gameloop.getImage("Art/picture.png", 255, 255, 255));
		start = gameloop.makeButton("Start", gameloop.getImage("Art/start.png", 255, 255, 255), 180, 51);
		instruction = gameloop.makeButton("Instruction", gameloop.getImage("Art/instructions.png", 255, 255, 255), 378, 51);
		editor  = gameloop.makeButton("Editor", gameloop.getImage("Art/editor.png", 255, 255, 255), 204, 51);
		menu = gameloop.makeButton("Menu", gameloop.getImage("Art/Menu.png", 255, 255, 255), 169, 51);
		instructions = new Sprite(gameloop.getImage("Art/Instructions1.png", 255, 255, 255));
		emptyTileSprite = new Sprite(gameloop.getImage("Art/Grass.png"));
		arrowPanelSprite = new Sprite(gameloop.getImage("Art/ArrowPanel.png"), 32, 32);
		arrowPanelSprite.addFrame(gameloop.getImage("Art/ArrowPanel.png"), 32, 0);
		arrowPanelSprite.framerate(1);
		goalSprite = new Sprite(gameloop.getImage("Art/Goal.png"));
		pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
		
		girlSprite = new Sprite(gameloop.getImage("Art/Girl.png"));
		boundarySprite = new Sprite(gameloop.getImage("Art/Boundary.png", 255, 255, 255));
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
		
		grid.setTileDown(new ArrowPanel(5, 3, Direction.Down));
		grid.setTileDown(new ArrowPanel(5, 20, Direction.Right));
		grid.setTileDown(new ArrowPanel(6, 20, Direction.Right));
		grid.setTileDown(new ArrowPanel(20, 20, Direction.Up));
		grid.setTileDown(new ArrowPanel(20, 3, Direction.Left));
		grid.setTileDown(new Goal(12, 12));
		grid.setTileDown(new Pit(0, 7));
		grid.setTileDown(new Pit(8, 9));
//		grid.setTileDown(new Pit(6, 20));
		
		grid.setPieceDown(new Boundary(2, 2));
		grid.setPieceDown(new Door(5, 11));
		grid.setPieceDown(new HelperCharacter(3, 2));
		grid.setPieceDown(new Key(4, 4));
		grid.setPieceDown(new PushableBlock(8, 2));
		grid.setPieceDown(new PushableBlock(11, 2));
		grid.setPieceDown(new Spikeball(6, 2, Direction.Down));
		grid.setPieceDown(new SpikeTrap(8, 8));
//		grid.setPieceDown(new Spikeball(1, 11, Direction.Right));
		
		Girl girl = new Girl(0,0);
		grid.setPieceDown(girl);
		
		player = new Player(girl);
		
		SoundPlayer.playBGM(SoundPlayer.grandWaltz);
		
//		try {
//			writeLevel("Levels/SpikeTrapTest1");
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
		grid.movePiece(player.getX(), player.getY(), d);
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
		if (stage == 0) // Front screen
		{
			menu.hide();
			instructions.hide();
			gameloop.canvas.clear();
			gameloop.canvas.background(155, 155, 152);
			startPicture.position(400, 119);
			startPicture.show();
			startPicture.draw();
			start.position(422, 384);
			start.show();
			start.draw();
			instruction.position(323, 474);
			instruction.show();
			instruction.draw();
			editor.position(410, 564);
			editor.show();
			editor.draw();
		}
		else if (stage == 1) // Instruction Screen
		{
			start.hide();
			instruction.hide();
			editor.hide();
			gameloop.canvas.clear();
			instructions.position(537 - instructions.width()/2, 200);
			instructions.show();
			instructions.draw();
			menu.position(453, 564);
			menu.show();
			menu.draw();
		}
		else if (stage == 2) // Editor stage
		{
			start.hide();
			instruction.hide();
			editor.hide();
			gameloop.canvas.clear();
			menu.position(25, 660);
			menu.show();
			menu.draw();
		}
		else if (stage == 3) // Test level 
		{
			System.out.println("stage is " + stage);
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

			menu.position(750, 25);
			menu.show();
			menu.draw();
		}
	}
}
