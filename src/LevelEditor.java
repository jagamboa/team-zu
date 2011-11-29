import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ucigame.Sprite;

public class LevelEditor 
{
	public PlacementType mouseMode = null;
	public Direction mouseDirection = Direction.Up;
	
	private Forsaken gameloop;
	
	private GameState editLevel;
	private GameState testLevel;
	
	private Sprite gridSquare;
	
	private final int paletteX = 10;
	private final int paletteY = 21;
	private final int numberOfPaletteTypes = 12;
	
	// Level Variables
	private int girlX = -1;
	private int girlY = -1;
	private boolean testing = false;
	
	// Palette Buttons
	private Sprite arrowPanelButton;
	private Sprite boundaryButton;
	private Sprite doorButton;
	private Sprite emptyTileButton;
	private Sprite girlButton;
	private Sprite goalButton;
	private Sprite helperCharacterButton;
	private Sprite keyButton;
	private Sprite pitButton;
	private Sprite pushableBlockButton;
	private Sprite spikeballButton;
	private Sprite spikeTrapButton;
	private Sprite nullPieceButton;
	
	// Other Button
	private Sprite rotateButton;
	private Sprite optionsButton;
	private Sprite testButton;
	private Sprite loadButton;
	private Sprite saveButton;
	private Sprite backButton;
	
	public LevelEditor(Forsaken gameloop)
	{
		this.gameloop = gameloop;
		
		editLevel = new GameState(gameloop);
		editLevel.loadEmptyLevel();
		
		SoundPlayer.playBGM(SoundPlayer.grandWaltz);
		
		gridSquare = new Sprite(gameloop.getImage("Art/GridSquare.png"));
		
		// initialize Palette Buttons
		arrowPanelButton = gameloop.makeButton("LvlEdArrowPanelButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		arrowPanelButton.position(paletteX * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
		emptyTileButton = gameloop.makeButton("LvlEdEmptyTileButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		emptyTileButton.position((paletteX + 1) * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
		goalButton = gameloop.makeButton("LvlEdGoalButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		goalButton.position((paletteX + 2) * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
		pitButton = gameloop.makeButton("LvlEdPitButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		pitButton.position((paletteX + 3) * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
		
		boundaryButton = gameloop.makeButton("LvlEdBoundaryButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		boundaryButton.position(paletteX * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		doorButton = gameloop.makeButton("LvlEdDoorButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		doorButton.position((paletteX + 1) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		girlButton = gameloop.makeButton("LvlEdGirlButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		girlButton.position((paletteX + 2) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		helperCharacterButton = gameloop.makeButton("LvlEdHelperCharacterButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		helperCharacterButton.position((paletteX + 3) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		keyButton = gameloop.makeButton("LvlEdKeyButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		keyButton.position((paletteX + 4) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		pushableBlockButton = gameloop.makeButton("LvlEdPushableBlockButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		pushableBlockButton.position((paletteX + 5) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		spikeballButton = gameloop.makeButton("LvlEdSpikeballButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		spikeballButton.position((paletteX + 6) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		spikeTrapButton = gameloop.makeButton("LvlEdSpikeTrapButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		spikeTrapButton.position((paletteX + 7) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		nullPieceButton = gameloop.makeButton("LvlEdNullPieceButton", gameloop.getImage("Art/LvlEdPaletteButton.png"),
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		nullPieceButton.position((paletteX + 8) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
		
		
		// Initialize Other Buttons
		rotateButton = gameloop.makeButton("LvlEdRotateButton", gameloop.getImage("Art/LvlEdPaletteButton.png"), 
				Grid.SQUARE_DIMENSIONS, Grid.SQUARE_DIMENSIONS);
		rotateButton.position((paletteX - 1.5) * Grid.SQUARE_DIMENSIONS, (paletteY + 0.5) * Grid.SQUARE_DIMENSIONS);
		optionsButton = gameloop.makeButton("LvlEdOptionsButton", gameloop.getImage("Art/LvlEdOptionsButton.png"),
				Grid.SQUARE_DIMENSIONS * 3, Grid.SQUARE_DIMENSIONS);
		optionsButton.position( 20 * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS - 5);
		testButton = gameloop.makeButton("LvlEdTestButton", gameloop.getImage("Art/LvlEdTestButton.png"),
				Grid.SQUARE_DIMENSIONS * 3, Grid.SQUARE_DIMENSIONS);
		testButton.position(20 * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS + 5);
		loadButton = gameloop.makeButton("LvlEdLoadButton", gameloop.getImage("Art/LvlEdLoadButton.png"),
				Grid.SQUARE_DIMENSIONS * 3, Grid.SQUARE_DIMENSIONS);
		loadButton.position(0.5 * Grid.SQUARE_DIMENSIONS, (paletteY + 1.5) * Grid.SQUARE_DIMENSIONS);
		saveButton = gameloop.makeButton("LvlEdSaveButton", gameloop.getImage("Art/LvlEdSaveButton.png"),
				Grid.SQUARE_DIMENSIONS * 3, Grid.SQUARE_DIMENSIONS);
		saveButton.position(4.5 * Grid.SQUARE_DIMENSIONS, (paletteY + 1.5) * Grid.SQUARE_DIMENSIONS);
		backButton = gameloop.makeButton("LvlEdBackButton", gameloop.getImage("Art/LvlEdBackButton.png"),
				Grid.SQUARE_DIMENSIONS * 3, Grid.SQUARE_DIMENSIONS);
		backButton.position(0.5 * Grid.SQUARE_DIMENSIONS, (paletteY + 1.5) * Grid.SQUARE_DIMENSIONS);
		
	}
	
	public void draw()
	{
		if (testing)
		{
			backButton.draw();
		}
		else
		{
			editLevel.draw();
			
			// draw grid overlay
			for (int x = 0; x < Grid.WIDTH; x++)
				for (int y = 0; y < Grid.HEIGHT; y++)
				{
					gridSquare.position(x * Grid.SQUARE_DIMENSIONS, y * Grid.SQUARE_DIMENSIONS);
					gridSquare.draw();
				}
			
			// draw GUI
			
			// TODO: draw HUD menu bar
			
			// Draw Palette
			double rotation = 0;
			
			if (mouseDirection == Direction.Up)
				rotation = 0;
			else if (mouseDirection == Direction.Right)
				rotation = 90;
			else if (mouseDirection == Direction.Down)
				rotation = 180;
			else if (mouseDirection == Direction.Left)
				rotation = 270;
			
			Tilesets.arrowPanelSprite.position(paletteX * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
			Tilesets.arrowPanelSprite.rotate(rotation);
			Tilesets.arrowPanelSprite.draw();
			Tilesets.emptyTileSprite.position((paletteX + 1) * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
			Tilesets.emptyTileSprite.draw();
			Tilesets.goalSprite.position((paletteX + 2) * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
			Tilesets.goalSprite.draw();
			Tilesets.pitSprite.position((paletteX + 3) * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
			Tilesets.pitSprite.draw();
			
			Tilesets.boundarySprite.position(paletteX * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.boundarySprite.draw();
			Tilesets.doorSprite.position((paletteX + 1) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.doorSprite.draw();
			Tilesets.girlSprite.position((paletteX + 2) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.girlSprite.draw();
			Tilesets.helperCharacterSprite.position((paletteX + 3) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.helperCharacterSprite.draw();
			Tilesets.keySprite.position((paletteX + 4) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.keySprite.draw();
			Tilesets.pushableBlockSprite.position((paletteX + 5) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.pushableBlockSprite.draw();
			Tilesets.spikeballSprite.position((paletteX + 6) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.spikeballSprite.draw();
			Tilesets.spikeTrapSprite.position((paletteX + 7) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.spikeTrapSprite.draw();
			Tilesets.nullPieceSprite.position((paletteX + 8) * Grid.SQUARE_DIMENSIONS, (paletteY + 1) * Grid.SQUARE_DIMENSIONS);
			Tilesets.nullPieceSprite.draw();
			Tilesets.rotateButtonSprite.position((paletteX - 1.5) * Grid.SQUARE_DIMENSIONS, (paletteY + 0.5) * Grid.SQUARE_DIMENSIONS);
			Tilesets.rotateButtonSprite.draw();
			
			// Draw Pallete Buttons
			arrowPanelButton.draw();
			boundaryButton.draw();
			doorButton.draw();
			emptyTileButton.draw();
			girlButton.draw();
			goalButton.draw();
			helperCharacterButton.draw();
			keyButton.draw();
			pitButton.draw();
			pushableBlockButton.draw();
			spikeballButton.draw();
			spikeTrapButton.draw();
			nullPieceButton.draw();
			
			// draw other buttons
			rotateButton.draw();
			optionsButton.draw();
			testButton.draw();
			loadButton.draw();
			saveButton.draw();
		}
	}
	
	public void onClick(int x, int y)
	{
		int column = x / Grid.SQUARE_DIMENSIONS;
		int row = y / Grid.SQUARE_DIMENSIONS;
		
		// If the click is out of bounds, do nothing
		if (column > Grid.WIDTH - 1 || row > Grid.HEIGHT - 1
				|| column < 0 || row < 0)
		{
			return;
		}
		
		Object objectToAdd = null;
		
		if (mouseMode == PlacementType.ArrowPanel)
		{
			objectToAdd = new ArrowPanel(column, row, mouseDirection);
		}
		else if (mouseMode == PlacementType.Boundary)
		{
			objectToAdd = new Boundary(column, row);
		}
		else if (mouseMode == PlacementType.Door)
		{
			objectToAdd = new Door(column, row);
		}
		else if (mouseMode == PlacementType.EmptyTile)
		{
			objectToAdd = new EmptyTile(column, row);
		}
		else if (mouseMode == PlacementType.Girl)
		{
			objectToAdd = new Girl(column, row);
			
			if (editLevel.getPieceAt(girlX, girlY) instanceof Girl)
			{
				editLevel.setPieceDown(null, girlX, girlY);
			}
			
			girlX = column;
			girlY = row;
		}
		else if (mouseMode == PlacementType.Goal)
		{
			objectToAdd = new Goal(column, row);
		}
		else if (mouseMode == PlacementType.HelperCharacter)
		{
			objectToAdd = new HelperCharacter(column, row);
		}
		else if (mouseMode == PlacementType.Key)
		{
			objectToAdd = new Key(column, row);
		}
		else if (mouseMode == PlacementType.Pit)
		{
			objectToAdd = new Pit(column, row);
		}
		else if (mouseMode == PlacementType.PushableBlock)
		{
			objectToAdd = new PushableBlock(column, row);
		}
		else if (mouseMode == PlacementType.Spikeball)
		{
			objectToAdd = new Spikeball(column, row, mouseDirection);
		}
		else if (mouseMode == PlacementType.SpikeTrap)
		{
			objectToAdd = new SpikeTrap(column, row);
		}
		else if (mouseMode == PlacementType.nullPiece)
		{
			objectToAdd = null;
		}
		else
		{
			return;
		}
		
		if (objectToAdd == null || objectToAdd instanceof Piece)
		{
			editLevel.setPieceDown((Piece)objectToAdd, column, row);
		}
		else if (objectToAdd instanceof Tile)
		{
			editLevel.setTileDown((Tile)objectToAdd);
		}
	}

	public void displayOptions() 
	{
		// TODO Auto-generated method stub
		
	}

	public GameState testLevel() 
	{
		if (!girlPlaced())
		{
        	JOptionPane.showMessageDialog(gameloop, "No Girl in Level!", "Error", JOptionPane.ERROR_MESSAGE, null);
        	return null;
		}
		
		// TODO Auto-generated method stub
		try {
			testLevel = editLevel.copy(gameloop);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		testing = true;
		showEditButtons(false);
		showTestButtons(true);
		
		return testLevel;
	}

	public void loadLevel() 
	{	
		//Create a file chooser
		final JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/Levels");
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(gameloop);
		
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fc.getSelectedFile();
            
            if (!file.exists())
            {	
            	JOptionPane.showMessageDialog(gameloop, "File \"" + file.getAbsolutePath() + "\" does not exist!", 
            			"Error", JOptionPane.ERROR_MESSAGE, null);
            	while(!file.delete());
            }
            
            
            // Load the File
            try 
            {
				editLevel.loadLevel(file.getAbsolutePath());
			} 
            catch (IOException e) 
            {
            	JOptionPane.showMessageDialog(gameloop, "Error reading File", "Error", JOptionPane.ERROR_MESSAGE, null);
			} 
            catch (DataFormatException e) 
            {
            	JOptionPane.showMessageDialog(gameloop, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
			}
            
            girlX = editLevel.getGirlX();
            girlY = editLevel.getGirlY();
        } 
	}

	public void saveLevel() 
	{
		if (!girlPlaced())
		{
        	JOptionPane.showMessageDialog(gameloop, "No Girl in Level!", "Error", JOptionPane.ERROR_MESSAGE, null);
        	return;
		}
		
		//Create a file chooser
		final JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/Levels");
		
		//In response to a button click:
		int returnVal = fc.showSaveDialog(gameloop);
		
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fc.getSelectedFile();
            
            if (file.exists())
            {
            	int response = JOptionPane.showOptionDialog(gameloop, "File exists, are you sure you wish to overwrite?",
            					"File Exists", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
            					null, null, null);
            	
            	if (response != JOptionPane.YES_OPTION)
            	{
            		return;
            	}
            }
            
            
            // Save the File
            try 
            {
				editLevel.writeLevel(file.getAbsolutePath());
			} 
            catch (IOException e) 
            {
            	JOptionPane.showMessageDialog(gameloop, "Error writing File", "Error", JOptionPane.ERROR_MESSAGE, null);
			} 
            catch (DataFormatException e) 
            {
            	JOptionPane.showMessageDialog(gameloop, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
			}
        } 
	}
	
	public void endTest() 
	{
		// TODO Auto-generated method stub
		testing = false;
		
		showEditButtons(true);
		showTestButtons(false);
	}
		
	
	private boolean girlPlaced()
	{
		return editLevel.getPieceAt(girlX, girlY) instanceof Girl;
	}
	
	private void showEditButtons(boolean show)
	{
		if (show)
		{
			arrowPanelButton.show();
			boundaryButton.show();
			doorButton.show();
			emptyTileButton.show();
			girlButton.show();
			goalButton.show();
			helperCharacterButton.show();
			keyButton.show();
			pitButton.show();
			pushableBlockButton.show();
			spikeballButton.show();
			spikeTrapButton.show();
			nullPieceButton.show();
			rotateButton.show();
			optionsButton.show();
			testButton.show();
			loadButton.show();
			saveButton.show();
		}
		else
		{
			arrowPanelButton.hide();
			boundaryButton.hide();
			doorButton.hide();
			emptyTileButton.hide();
			girlButton.hide();
			goalButton.hide();
			helperCharacterButton.hide();
			keyButton.hide();
			pitButton.hide();
			pushableBlockButton.hide();
			spikeballButton.hide();
			spikeTrapButton.hide();
			nullPieceButton.hide();
			rotateButton.hide();
			optionsButton.hide();
			testButton.hide();
			loadButton.hide();
			saveButton.hide();
		}
	}
	
	private void showTestButtons(boolean show)
	{
		if (show)
		{
			backButton.show();
		}
		else
		{
			backButton.hide();
		}
	}

}
