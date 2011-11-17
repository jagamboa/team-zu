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
	
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 768;
	
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
				
		gameState = new GameState(this);
		try {
			gameState.loadLevel("Levels/SpikeTrapTest1");
		} catch (IOException e) {
			System.err.print(e.getMessage());
			System.exit(1);
		} catch (DataFormatException e) {
			System.err.print(e.getMessage());
			System.exit(1);
		}
	}
	
	public void draw()
	{	
		canvas.clear();
		
		if (state == 0) // Front screen
		{
			startPicture.draw();
			startButton.draw();
			instructionButton.draw();
			editorButton.draw();
		}
		else if (state == 1) // Instruction Screen
		{
			instructions.draw();
			menuButton.draw();
		}
		else if (state == 2) // Editor stage
		{
			menuButton.draw();
		}
		else if (state == 3) // Test level 
		{
			gameState.update();
			gameState.draw();
			
			menuButton.draw();
		}
	}
	
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
	
	public void onClickMenu()
	{
		state = 0;
		canvas.background(155, 155, 152);
		menuButton.hide();
		instructions.hide();
		startPicture.show();
		startButton.show();
		instructionButton.show();
		editorButton.show();
	}
	
	public void onClickInstruction()
	{
		state = 1;
		canvas.background(155, 155, 152);
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		instructions.show();
		menuButton.position(453, 564);
		menuButton.show();
	}
	
	public void onClickEditor()
	{
		state = 2;
		canvas.background(155, 155, 152);
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		menuButton.position(25, 660);
		menuButton.show();
	}
	
	public void onClickStart()
	{
		state = 3;
		startButton.hide();
		instructionButton.hide();
		editorButton.hide();
		menuButton.position(750, 25);
		menuButton.show();
	}
	
	public void onMousePressed()
	{
		gameState.onClick(mouse.x(), mouse.y());
	}
}
