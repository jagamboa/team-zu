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
	
	public void setup()
	{
		// initialize ucigame window
		window.size(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.title("Forsaken");
		window.showFPS();
		framerate(50);
		
		SoundPlayer.loadContent(this);
				
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
		gameState.update();
		gameState.draw();
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
	public void onClickInstruction()
	{
		GameState.stage = 1;
	}
	
	public void onClickEditor()
	{
		GameState.stage = 2;
	}
	
	public void onClickStart()
	{
		GameState.stage = 3;
	
	}
	
	public void onClickMenu()
	{
		GameState.stage = 0;
	
	}
	
	public void onMousePressed()
	{
		gameState.onClick(mouse.x(), mouse.y());
	}
}
