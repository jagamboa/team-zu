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
	
	public static final boolean DEBUG = true;
	
	//gamestate codes
	public static final int MENU_STATE = 0;
	public static final int INSTRUCTIONS_STATE = 1;
	public static final int CREDITS = 2;
	public static final int EDITOR_STATE = 3;
	public static final int TEST_LEVEL_STATE = 4;
	public static final int EDITOR_OPTIONS = 5;
	public static final int CUTSCENE_1 = 10;
	public static final int LEVEL_1 = 11;
	public static final int CUTSCENE_2 = 12;
	public static final int LEVEL_2 = 13;
	public static final int CUTSCENE_3 = 14;
	public static final int LEVEL_3 = 15;
	public static final int CUTSCENE_4 = 16;
	public static final int LEVEL_4 = 17;
	public static final int CUTSCENE_5 = 18;
	public static final int LEVEL_5 = 19;
	public static final int CUTSCENE_6 = 20;
	public static final int LEVEL_6 = 21;
	public static final int CUTSCENE_7 = 22;
	public static final int LEVEL_7 = 23;
	public static final int CUTSCENE_8 = 24;
	public static final int LEVEL_8 = 25;
	public static final int CUTSCENE_9 = 26;
	
	// current gamestate
	private int state;
	private boolean loaded;
	private boolean spacePressed;
	private int next = 0;
	
	// Button list
	private Sprite startButton; // Start game button
	private Sprite instructionButton; // Instruction button
	private Sprite editorButton; // Editor starter button
	private Sprite menuButton; // Button to go back to the menu
	private Sprite restartButton;
	
	// Menu Sprites
	private Sprite startPicture; // The picture in the menu screen
	private Sprite instructions;  // The actual instructions shown on the instructions screen
	
	//HUD sprites
	private Sprite levelName;
	private Sprite keyCounter;
	
	//Cut-scene sprites/backgrounds
	private Sprite framedGirl;
	private Sprite framedDriver;
	private Sprite framedMaid;
	private Sprite framedSiblings;
	private Sprite framedUncle;
	private Sprite framedGlutton;
	private Sprite bus;
	private Sprite frontporch;
	private Sprite insidehouse;
	private Sprite closetdoor;
	private Sprite hallway;
	private Sprite twistedhallway;
	private Sprite basement;
	private Sprite rottenhallway;
	private Sprite frontporch2;
	

	
	
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
		startButton = makeButton("Start", getImage("Art/PlayButton.png", 255, 255, 255), 226, 43);
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
		
		//load Cut-scene sprites/backgrounds
		bus = new Sprite(getImage("Art/bus.jpg"));
		frontporch = new Sprite(getImage("Art/frontporch.jpg"));
		insidehouse = new Sprite(getImage("Art/insidehouse.jpg"));
		closetdoor = new Sprite(getImage("Art/closetdoor.jpg"));
		hallway = new Sprite(getImage("Art/hallway.jpg"));
		twistedhallway = new Sprite(getImage("Art/twistedhallway.jpg"));
		basement = new Sprite(getImage("Art/basement.jpg"));
		rottenhallway = new Sprite(getImage("Art/rottenhallway.jpg"));
		frontporch2 = new Sprite(getImage("Art/frontporch2.jpg"));
		framedGirl = new Sprite(getImage("Art/framedgirl.jpg"));
		framedGirl.position(5, 515);
		framedDriver = new Sprite(getImage("Art/frameddriver.jpg"));
		framedDriver.position(5, 515);
		framedMaid = new Sprite(getImage("Art/framedMaid.jpg"));
		framedMaid.position(5, 515);
		framedSiblings = new Sprite(getImage("Art/framedSiblings.jpg"));
		framedSiblings.position(5, 515);
		framedUncle = new Sprite(getImage("Art/framedUncle.jpg"));
		framedUncle.position(5, 515);
		framedGlutton = new Sprite(getImage("Art/framedGlutton.jpg"));
		framedGlutton.position(5, 515);

		// Position buttons and GUI elements
		startPicture.position(400, 119);
		startButton.position(422, 384);
		instructionButton.position(323, 474);
		editorButton.position(410, 564);
		instructions.position(537 - instructions.width()/2, 200);
				
		// Create GameState
		gameState = new GameState(this);
		
		// Create Level Editor
		levelEditor = new LevelEditor(this);
		
		// Show start menu
		onClickMenu();
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
		else if (state == Forsaken.EDITOR_OPTIONS)
		{
			levelEditor.draw();
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
		else if (showingGameplay())
		{
			try
			{
				gameState.update();
				gameState.draw();
				
				menuButton.draw();
				restartButton.draw();
				
				levelName.draw();
				levelName.font("Arial", PLAIN, 20, 0,0,0);
				levelName.putText("Level Name: " + gameState.getLevelName(), 5, 20); //second "" for name
				keyCounter.draw();
				keyCounter.font("Arial", PLAIN, 20, 0,0,0);
				keyCounter.putText("x " + gameState.getGirl().getKeyCount(), 30, 20); //second "" for key count	
			}
			catch (java.lang.Throwable t)
			{
	        	JOptionPane.showMessageDialog(this, "Error Occurred! Resetting Level", "Error", JOptionPane.ERROR_MESSAGE, null);
	        	gameState.restart();
			}
		}
		else if (showingCutscene())
		{
			canvas.font("Arial", PLAIN, 30);
			// TODO: draw cutscene
			if (state == CUTSCENE_1)
			{
				if (next == 1)
				{
					bus.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("*yawn* Ah, what time is it?", 320, 625);
					canvas.putText("Am I home yet?", 320, 675);
				}
				else if (next == 2)
				{
					bus.draw();
					framedDriver.draw();
					canvas.putText("Bus Driver:", 400, 550);
					canvas.putText("Last stop!", 405, 625);
				}
				else if (next == 3)
				{
					bus.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Home sweet home.", 350, 625);
				}
				else if (next == 4)
				{
					bus.draw();
					framedDriver.draw();
					canvas.putText("Bus Driver:", 400, 550);
					canvas.putText("Sorry about the luggage miss,", 290, 625);
					canvas.putText("just push it aside on your way out.", 290, 675);

				}
				else if (next == 5)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("push the luggage out", 20, 300);
					canvas.putText("of your way to reach", 20, 400);
					canvas.putText("the goal", 20, 500);
				}
			}
			else if (state == CUTSCENE_2)
			{

				if (next == 1)
				{
					frontporch.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Finally, I'm home", 320, 625);
				}
				else if (next == 2)
				{
					frontporch.draw();
					canvas.putText("*loud crashes and yelling*", 320, 625);
				}
				else if (next == 3)
				{
					frontporch.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Huh, what's that sound?", 320, 625);
					canvas.putText("It must be coming from the neighbors.", 320, 675);
				}
				else if (next == 4)
				{
					frontporch.draw();
					canvas.putText("*more loud crashes and yelling*", 320, 625);
				}
				else if (next == 5)
				{
					frontporch.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Wait, that's coming from inside!", 320, 625);
				}
				else if (next == 6)
				{
					//instructions
				}
			}
			else if (state == CUTSCENE_3)
			{
				if (next == 1)
				{
					insidehouse.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Why is everything such a mess?", 320, 625);
				}
				else if (next == 2)
				{
					insidehouse.draw();
					canvas.putText("*whimpering from afar*", 320, 625);
				}
				else if (next == 3)
				{
					insidehouse.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("What's that? It sounds like it's... ", 320, 625);
					canvas.putText("coming from over there?", 320, 675);
				}
				else if (next == 4)
				{
					//instructions
				}
			}
			else if (state == CUTSCENE_4) //?????
			{
				if (next == 1)
				{
					closetdoor.draw();
				}
				else if (next == 2)
				{
					closetdoor.draw();
				}
				else if (next == 3)
				{
					closetdoor.draw();
				}
				else if (next == 4)
				{
					closetdoor.draw();
				}
				else if (next == 5)
				{
					closetdoor.draw();
				}
				else if (next == 6)
				{
					//instructions
				}
			}
			else if (state == CUTSCENE_5)
			{
				if (next == 1)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Is that, the maid?! Why are you chained to the floor?", 320, 625);
				}
				else if (next == 2)
				{
					hallway.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 3)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 4)
				{
					hallway.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 5)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 6)
				{
					hallway.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 7)
				{
					hallway.draw();
					canvas.putText("", 320, 625);
				}
				else if (next == 8)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 9)
				{
					//instructions
				}
			}
			else if (state == CUTSCENE_6)
			{
				if (next == 1)
				{
					twistedhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 2)
				{
					twistedhallway.draw();
					//man in white
					canvas.putText("Man in White:", 400, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 3)
				{
					twistedhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 4)
				{
					//instructions
				}
			}
			else if (state == CUTSCENE_7)
			{
				if (next == 1)
				{
					basement.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 2)
				{
					basement.draw();
					framedUncle.draw();
					canvas.putText("Uncle:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 3)
				{
					basement.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 4)
				{
					basement.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 5)
				{
					//instructions
				}
			}
			else if (state == CUTSCENE_8)
			{
				if (next == 1)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 2)
				{
					rottenhallway.draw();
					canvas.putText("", 320, 625);
				}
				else if (next == 3)
				{
					rottenhallway.draw();
					//police
					canvas.putText("Police:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 4)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 5)
				{
					rottenhallway.draw();
					canvas.putText("", 320, 625);
				}
				else if (next == 6)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 7)
				{
					rottenhallway.draw();
					canvas.putText("", 320, 625);
				}
				else if (next == 8)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 9)
				{
					rottenhallway.draw();
					framedGlutton.draw();
					canvas.putText("Glutton:", 450, 550);
					canvas.putText("", 320, 625);
				}
				else if (next == 10)
				{
					//instructions
				}
			}
			else if (state == CUTSCENE_9) //?????
			{
				if (next == 1)
				{
					frontporch2.draw();
				}
				else if (next == 2)
				{
					frontporch2.draw();
				}
				else if (next == 3)
				{
					frontporch2.draw();
				}
				else if (next == 4)
				{
					frontporch2.draw();
				}
				else if (next == 5)
				{
					frontporch2.draw();
				}
			}
			
		}
	}
	
	public boolean showingCutscene()
	{
		return state >= CUTSCENE_1 && state % 2 == 0 && loaded;
	}
	
	public boolean showingGameplay()
	{
		return ((state >= LEVEL_1 && state % 2 == 1) || state == TEST_LEVEL_STATE) && loaded;
	}
	
	public void nextGamestate()
	{
		if (state == TEST_LEVEL_STATE)
		{
			onClickLvlEdBackButton();
			return;
		}
		
		loaded = false;
		
		if (state == MENU_STATE)
			state = CUTSCENE_1;
		else if (state == CUTSCENE_9)
		{
			onClickMenu();
			return;
		}
		else
			state++;
		
		SoundPlayer.stopAll();
		
		if (state % 2 == 0)
		{
			SoundPlayer.playBGM(SoundPlayer.cutscene);
			startNextCutscene();
		}
		else
			loadNextLevel();
		
		loaded = true;
	}
	
	private void loadNextLevel()
	{
		try
		{
			if (state == LEVEL_1)
			{
				gameState.loadLevel("Levels/Level 1 - Bus");
			}
			else if (state == LEVEL_2)
			{
				gameState.loadLevel("Levels/Level 2 - Garden");
			}
			else if (state == LEVEL_3)
			{
				gameState.loadLevel("Levels/Level 3 - House");
			}
			else if (state == LEVEL_4)
			{
				gameState.loadLevel("Levels/Level 4 - Sunroom");
			}
			else if (state == LEVEL_5)
			{
				gameState.loadLevel("Levels/Level 5 - Library");
			}
			else if (state == LEVEL_6)
			{
				gameState.loadLevel("Levels/Level 6 - Twisted Hall");
			}
			else if (state == LEVEL_7)
			{
				gameState.loadLevel("Levels/Level 7 - Basement");
			}
			else if (state == LEVEL_8)
			{
				gameState.loadLevel("Levels/Level 8 - Glutton");
			}
		}
		catch (Exception e)
		{
        	JOptionPane.showMessageDialog(this, "Error Loading Level!\nReturning to Main Menu", "Error", JOptionPane.ERROR_MESSAGE, null);
        	onClickMenu();
		}
	}
	
	private void startNextCutscene()
	{
		if (state == CUTSCENE_1)
		{
			if (next != 5)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_2)
		{
			if (next != 6)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_3)
		{
			if (next != 4)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_4)
		{
			if (next != 5)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_5)
		{
			if (next != 9)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_6)
		{
			if (next != 4)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_7)
		{
			if (next != 5)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_8)
		{
			if (next != 10)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
		}
		else if (state == CUTSCENE_9)
		{
			if (next != 5)
			{
				next++;
			}
			else
			{
				nextGamestate();
				next = 0;
			}
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
		
		if (keyboard.isDown(keyboard.SPACE))
		{
			if (showingCutscene())
			{
				if (!spacePressed)
				{
					startNextCutscene();
					spacePressed = true;
				}
			}
		}
		
		if (DEBUG)
		{
			if (keyboard.isDown(keyboard.PERIOD))
			{
				if (showingGameplay() && state != TEST_LEVEL_STATE)
					nextGamestate();
			}
		}
	}
	
	public void onKeyRelease()
	{
		if (!keyboard.isDown(keyboard.SPACE))
		{
			spacePressed = false;
		}
	}
	
	// Mouse Input //////////////////////////////////////////////////////////////////////
	
	public void onMousePressed()
	{
		if (showingGameplay())
			gameState.onClick(mouse.x(), mouse.y());
		else if (state == Forsaken.EDITOR_STATE)
			levelEditor.onClick(mouse.x(), mouse.y());
		else if (showingCutscene())
		{
			// TODO: advance cutscene text
			startNextCutscene();
		}
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
		levelEditor.hide();
		menuButton.hide();
		instructions.hide();
		restartButton.hide();
		levelName.hide();
		startPicture.show();
		startButton.show();
		instructionButton.show();
		editorButton.show();
		state = Forsaken.MENU_STATE;
		SoundPlayer.stopAll();
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
		levelEditor.show();
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
		
		nextGamestate();
	}
	
	public void onClickRestart()
	{
		gameState.restart();
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
	
	public void onClickLvlEdGluttonButton()
	{
		levelEditor.mouseMode = PlacementType.glutton;
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
		state = EDITOR_OPTIONS;
		menuButton.hide();
	}
	
	public void onClickLvlEdTestButton()
	{
		loaded = false;
		GameState testLevel = levelEditor.testLevel();
		
		if (testLevel != null)
		{
			state = TEST_LEVEL_STATE;
			gameState = testLevel;
			menuButton.hide();
			loaded = true;
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
		if (state == TEST_LEVEL_STATE)
		{
			levelEditor.endTest();
			state = EDITOR_STATE;
			menuButton.show();
		}
		else if (state == EDITOR_OPTIONS)
		{
			levelEditor.hideOptions();
			state = EDITOR_STATE;
			menuButton.show();
		}
	}
	
	public void onClickLvlEdLeftMusicToggleButton()
	{
		SoundPlayer.prevBGM();
	}
	
	public void onClickLvlEdRightMusicToggleButton()
	{
		SoundPlayer.nextBGM();
	}
	
	public void onClickLvlEdLeftSpriteToggleButton()
	{
		Tilesets.prevTileset();
	}
	
	public void onClickLvlEdRightSpriteToggleButton()
	{
		Tilesets.nextTileset();
	}
	
}
