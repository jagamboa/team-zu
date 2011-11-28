import java.io.IOException;
import java.util.zip.DataFormatException;

import javax.swing.JOptionPane;

import ucigame.*;

// TODO
// Add different scenes
// Add menu
// A nextScene() function which advances the game to the next scene
public class Forsaken extends Ucigame
{
	GameState gameState;
	LevelEditor levelEditor;
	
	public static final int WINDOW_WIDTH = 768;
	public static final int WINDOW_HEIGHT = 768;
	
	public static final int MENU_STATE = 0;
	public static final int INSTRUCTIONS_STATE = 1;
	public static final int EDITOR_STATE = 2;
	public static final int TEST_LEVEL_STATE = 3;
	public static final int START_GAME_STATE = 4;
	
	// Button list
	private Sprite startButton; // Start game button
	private Sprite instructionButton; // Instruction button
	private Sprite editorButton; // Editor starter button
	private Sprite menuButton; // Button to go back to the menu
	private Sprite restartButton;
	private int state; // 0 for menu, 1 for instructions, 2 for editor, 3 for test level
	
	// Menu Sprites
	private Sprite startPicture; // The picture in the menu screen
	private Sprite instructions;  // The actual instructions shown on the instructions screen
	
	//HUD sprites
	private Sprite levelName;
	private Sprite keyCounter;

	
	
	public void setup()
	{
		// initialize ucigame window
		window.size(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.title("Forsaken");
		window.showFPS();
		framerate(50);
		
		SoundPlayer.loadContent(this);
		Tilesets.initialize(this);
		
		
		// load Buttons
		startButton = makeButton("Start", getImage("Art/start.png", 255, 255, 255), 180, 51);
		instructionButton = makeButton("Instruction", getImage("Art/instructions.png", 255, 255, 255), 378, 51);
		editorButton  = makeButton("Editor", getImage("Art/editor.png", 255, 255, 255), 204, 51);
		menuButton = makeButton("Menu", getImage("Art/Menu.png", 255, 255, 255), 169, 51);
		restartButton = makeButton("Restart", getImage("Art/start.png", 255, 255, 255), 180, 51);
		
		// load GUI
		startPicture = new Sprite(getImage("Art/picture.png", 255, 255, 255));
		instructions = new Sprite(getImage("Art/Instructions1.png", 255, 255, 255));
		
		//load HUD
		levelName = new Sprite(getImage("Art/levelName.png", 255, 255, 255));
		keyCounter = new Sprite(getImage("Art/keyCounter.png", 255, 255, 255));

		// Position buttons and GUI elements
		startPicture.position(400, 119);
		startButton.position(422, 384);
		instructionButton.position(323, 474);
		editorButton.position(410, 564);
		instructions.position(537 - instructions.width()/2, 200);
		
		// Show start menu
		onClickMenu();
				
		// Create GameState
		gameState = new GameState(this);
		
		// Create Level Editor
		levelEditor = new LevelEditor(this);
	}
	
	public void draw()
	{	
		canvas.clear();
		
		if (state == Forsaken.MENU_STATE) // Front screen
		{
			startPicture.draw();
			startButton.draw();
			instructionButton.draw();
			editorButton.draw();;
		}
		else if (state == Forsaken.INSTRUCTIONS_STATE) // Instruction Screen
		{
			instructions.draw();
			menuButton.draw();
		}
		else if (state == Forsaken.EDITOR_STATE) // Editor stage
		{
			levelEditor.draw();
			menuButton.draw();
		}
		else if (state == Forsaken.TEST_LEVEL_STATE)
		{
			try
			{
				gameState.update();
				gameState.draw();
				
				levelEditor.draw();
			}
			catch (java.lang.Throwable t)
			{
	        	JOptionPane.showMessageDialog(this, "Error Testing Level!", "Error", JOptionPane.ERROR_MESSAGE, null);
	        	onClickLvlEdBackButton();
			}
		}
		else if (state == Forsaken.START_GAME_STATE) // Test level 
		{
			gameState.update();
			gameState.draw();
			
			menuButton.draw();
			restartButton.draw();
			
			levelName.draw();
			levelName.font("Arial", PLAIN, 20, 0,0,0);
			levelName.putText("Level Name: " + "Circle of Doom", 5, 20); //second "" for name
			keyCounter.draw();
			keyCounter.font("Arial", PLAIN, 20, 0,0,0);
			keyCounter.putText("x " + gameState.getGirl().getKeyCount(), 30, 20); //second "" for key count
			
		}
	}
	
	// Keyboard Input ///////////////////////////////////////////////////////////////////
	
	public void onKeyPress()
	{	
		if (keyboard.isDown(keyboard.UP))
		{
			gameState.move(Direction.Up);
		}
		if (keyboard.isDown(keyboard.DOWN))
		{
			gameState.move(Direction.Down);
		}
		if (keyboard.isDown(keyboard.LEFT))
		{
			gameState.move(Direction.Left);
		}
		if (keyboard.isDown(keyboard.RIGHT))
		{
			gameState.move(Direction.Right);
		}
	}
	
	// Mouse Input //////////////////////////////////////////////////////////////////////
	
	public void onMousePressed()
	{
		if (state == Forsaken.START_GAME_STATE || state == Forsaken.TEST_LEVEL_STATE)
			gameState.onClick(mouse.x(), mouse.y());
		else if (state == Forsaken.EDITOR_STATE)
			levelEditor.onClick(mouse.x(), mouse.y());
	}
	
	public void onMouseDragged()
	{
		if (state == Forsaken.EDITOR_STATE)
			levelEditor.onClick(mouse.x(), mouse.y());
	}
	
	
	// Button functions //////////////////////////////////////////////////////////
	
	public void onClickMenu()
	{
		canvas.background(155, 155, 152);
		menuButton.hide();
		instructions.hide();
		restartButton.hide();
		levelName.hide();
		startPicture.show();
		startButton.show();
		instructionButton.show();
		editorButton.show();
		state = Forsaken.MENU_STATE;
		SoundPlayer.playBGM(SoundPlayer.grandWaltz);
	}
	
	public void onClickInstruction()
	{
		canvas.background(155, 155, 152);
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		restartButton.hide();
		levelName.hide();
		keyCounter.hide();
		instructions.show();
		menuButton.position(453, 564);
		menuButton.show();
		state = Forsaken.INSTRUCTIONS_STATE;
	}
	
	public void onClickEditor()
	{
		canvas.background(155, 155, 152);
		startButton.hide();
		restartButton.hide();
		instructionButton.hide();
		editorButton.hide();
		levelName.hide();
		keyCounter.hide();
		menuButton.position(25, 660);
		menuButton.show();
		state = Forsaken.EDITOR_STATE;
	}
	
	public void onClickStart()
	{
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		menuButton.position(15, 660);
		menuButton.show();
		restartButton.position(575, 660);
		restartButton.show();

		levelName.position(200, 650);
		levelName.show();
		keyCounter.position(210, 680);
		keyCounter.show();
		
		
		try {
			gameState.loadLevel("Levels/ArrowCircle");
		} catch (IOException e) {
			System.err.print(e.getMessage());
			System.exit(1);
		} catch (DataFormatException e) {
			System.err.print(e.getMessage());
			System.exit(1);
		}
		
		state = Forsaken.START_GAME_STATE;
	}
	
	public void onClickRestart()
	{
		onClickStart();
	}
	
	// Level Editor Palette Buttons
	public void onClickLvlEdArrowPanelButton()
	{
		levelEditor.mouseMode = PlacementType.ArrowPanel;
	}
	
	public void onClickLvlEdBoundaryButton()
	{
		levelEditor.mouseMode = PlacementType.Boundary;
	}
	
	public void onClickLvlEdDoorButton()
	{
		levelEditor.mouseMode = PlacementType.Door;
	}
	
	public void onClickLvlEdEmptyTileButton()
	{
		levelEditor.mouseMode = PlacementType.EmptyTile;
	}
	
	public void onClickLvlEdGirlButton()
	{
		levelEditor.mouseMode = PlacementType.Girl;
	}
	
	public void onClickLvlEdGoalButton()
	{
		levelEditor.mouseMode = PlacementType.Goal;
	}
	
	public void onClickLvlEdHelperCharacterButton()
	{
		levelEditor.mouseMode = PlacementType.HelperCharacter;
	}
	
	public void onClickLvlEdKeyButton()
	{
		levelEditor.mouseMode = PlacementType.Key;
	}
	
	public void onClickLvlEdPitButton()
	{
		levelEditor.mouseMode = PlacementType.Pit;
	}
	
	public void onClickLvlEdPushableBlockButton()
	{
		levelEditor.mouseMode = PlacementType.PushableBlock;
	}
	
	public void onClickLvlEdSpikeballButton()
	{
		levelEditor.mouseMode = PlacementType.Spikeball;
	}
	
	public void onClickLvlEdSpikeTrapButton()
	{
		levelEditor.mouseMode = PlacementType.SpikeTrap;
	}
	
	public void onClickLvlEdNullPieceButton()
	{
		levelEditor.mouseMode = PlacementType.nullPiece;
	}
	
	public void onClickLvlEdRotateButton()
	{
		if (levelEditor.mouseDirection == Direction.Up)
		{
			levelEditor.mouseDirection = Direction.Right;
		}
		else if (levelEditor.mouseDirection == Direction.Right)
		{
			levelEditor.mouseDirection = Direction.Down;
		}
		else if (levelEditor.mouseDirection == Direction.Down)
		{
			levelEditor.mouseDirection = Direction.Left;
		}
		else if (levelEditor.mouseDirection == Direction.Left)
		{
			levelEditor.mouseDirection = Direction.Up;
		}
	}
	
	public void onClickLvlEdOptionsButton()
	{
		levelEditor.displayOptions();
	}
	
	public void onClickLvlEdTestButton()
	{
		GameState testLevel = levelEditor.testLevel();
		
		if (testLevel != null)
		{
			state = TEST_LEVEL_STATE;
			gameState = testLevel;
			menuButton.hide();
		}
	}
	
	public void onClickLvlEdLoadButton()
	{
		levelEditor.loadLevel();
	}
	
	public void onClickLvlEdSaveButton()
	{
		levelEditor.saveLevel();
	}
	
	public void onClickLvlEdBackButton()
	{
		levelEditor.endTest();
		state = EDITOR_STATE;
		menuButton.show();
	}
	
}
