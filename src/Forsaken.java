import java.io.IOException;
import java.util.zip.DataFormatException;

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
	public static final int START_GAME_STATE = 3;
	
	// Button list
	private Sprite startButton; // Start game button
	private Sprite instructionButton; // Instruction button
	private Sprite editorButton; // Editor starter button
	private Sprite menuButton; // Button to go back to the menu
	private int state; // 0 for menu, 1 for instructions, 2 for editor, 3 for test level
	
	// Menu Sprites
	private Sprite startPicture; // The picture in the menu screen
	private Sprite instructions;  // The actual instructions shown on the instructions screen

	
	
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
		
		// load GUI
		startPicture = new Sprite(getImage("Art/picture.png", 255, 255, 255));
		instructions = new Sprite(getImage("Art/Instructions1.png", 255, 255, 255));

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
			editorButton.draw();
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
		else if (state == Forsaken.START_GAME_STATE) // Test level 
		{
			gameState.update();
			gameState.draw();
			
			menuButton.draw();
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
		if (state == Forsaken.START_GAME_STATE)
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
		startPicture.show();
		startButton.show();
		instructionButton.show();
		editorButton.show();
		state = Forsaken.MENU_STATE;
	}
	
	public void onClickInstruction()
	{
		canvas.background(155, 155, 152);
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		instructions.show();
		menuButton.position(453, 564);
		menuButton.show();
		state = Forsaken.INSTRUCTIONS_STATE;
	}
	
	public void onClickEditor()
	{
		canvas.background(155, 155, 152);
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		menuButton.position(25, 660);
		menuButton.show();
		state = Forsaken.EDITOR_STATE;
	}
	
	public void onClickStart()
	{
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		menuButton.position(750, 25);
		menuButton.show();
		
		try {
			gameState.loadLevel("Levels/SpikeTrapTest1");
		} catch (IOException e) {
			System.err.print(e.getMessage());
			System.exit(1);
		} catch (DataFormatException e) {
			System.err.print(e.getMessage());
			System.exit(1);
		}
		
		state = Forsaken.START_GAME_STATE;
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
	
}
