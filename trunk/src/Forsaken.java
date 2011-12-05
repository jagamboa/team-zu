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
	
	public static boolean FROST = false;
	private int[] cheatCode;
	private int entryIndex = 0;
	
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
	private Sprite framedDoctor;
	private Sprite framedPolice;
	private Sprite framedFather;
	private Sprite bus;
	private Sprite frontporch;
	private Sprite insidehouse;
	private Sprite closetdoor;
	private Sprite room;
	private Sprite room1;
	private Sprite darkness;
	private Sprite hallway1;
	private Sprite hallway;
	private Sprite twistedhallway;
	private Sprite basement;
	private Sprite rottenhallway;
	private Sprite asylum;
	

	
	
	public void setup()
	{
		// initialize ucigame window
		window.size(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.title("Forsaken");
		window.showFPS();
		framerate(30);
		
		SoundPlayer.loadContent(this);
		Tilesets.initialize(this);
		
		// cheat code
		cheatCode = new int[10];
		cheatCode[0] = keyboard.UP;
		cheatCode[1] = keyboard.UP;
		cheatCode[2] = keyboard.DOWN;
		cheatCode[3] = keyboard.DOWN;
		cheatCode[4] = keyboard.LEFT;
		cheatCode[5] = keyboard.RIGHT;
		cheatCode[6] = keyboard.LEFT;
		cheatCode[7] = keyboard.RIGHT;
		cheatCode[8] = keyboard.B;
		cheatCode[9] = keyboard.A;
		
		// load Buttons
		startButton = makeButton("Start", getImage("Art/PlayButton.png", 255, 255, 255),389, 104);
		instructionButton = makeButton("Instruction", getImage("Art/InstructionButton.png", 255, 255, 255), 389, 104);
		editorButton  = makeButton("Editor", getImage("Art/EditorButton.png", 255, 255, 255), 389, 104);
		menuButton = makeButton("Menu", getImage("Art/MenuButton.png", 255, 255, 255), 149, 65);
		restartButton = makeButton("Restart", getImage("Art/Start.png", 255, 255, 255), 149, 65);
		
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
		room1 = new Sprite(getImage("Art/room1.jpg"));
		darkness = new Sprite(getImage("Art/darkness.jpg"));
		room = new Sprite(getImage("Art/room.jpg"));
		hallway1 = new Sprite(getImage("Art/hallway1.jpg"));
		hallway = new Sprite(getImage("Art/hallway.jpg"));
		twistedhallway = new Sprite(getImage("Art/twistedhallway.jpg"));
		basement = new Sprite(getImage("Art/basement.jpg"));
		rottenhallway = new Sprite(getImage("Art/rottenhallway.jpg"));
		asylum = new Sprite(getImage("Art/asylum.jpg"));
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
		framedDoctor = new Sprite(getImage("Art/framedDoctor.jpg"));
		framedDoctor.position(5, 515);
		framedPolice = new Sprite(getImage("Art/framedPolice.jpg"));
		framedPolice.position(5, 515);
		framedFather = new Sprite(getImage("Art/framedFather.jpg"));
		framedFather.position(5, 515);

		// Position buttons and GUI elements
		startPicture.position(400, 119);
		startButton.position(WINDOW_WIDTH/2 - 194, 300);
		instructionButton.position(WINDOW_WIDTH/2 - 194, 440);
		editorButton.position(WINDOW_WIDTH/2 - 194, 580);
		instructions.position(50, 0);
				
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
					canvas.putText("the goal.", 20, 500);
				}
			}
			else if (state == CUTSCENE_2)
			{

				if (next == 1)
				{
					frontporch.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Finally, I'm home", 350, 625);
				}
				else if (next == 2)
				{
					frontporch.draw();
					canvas.putText("*loud crashes and yelling*", 240, 625);
				}
				else if (next == 3)
				{
					frontporch.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Huh, what's that sound?", 250, 625);
					canvas.putText("It must be coming from the neighbors.", 250, 675);
				}
				else if (next == 4)
				{
					frontporch.draw();
					canvas.putText("*more loud crashes and yelling*", 210, 625);
				}
				else if (next == 5)
				{
					frontporch.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Wait, that's coming from inside!", 295, 625);
				}
				else if (next == 6)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("push the pots out", 20, 300);
					canvas.putText("of your way while", 20, 400);
					canvas.putText("avoiding the pits.", 20, 500);
					canvas.font("Arial", PLAIN,40);
					canvas.putText("*hint* Obstacles can be pushed", 20, 640);
					canvas.putText("into pits as well", 125,690);
				}
			}
			else if (state == CUTSCENE_3)
			{
				if (next == 1)
				{
					insidehouse.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Why is everything such a mess?", 290, 625);
				}
				else if (next == 2)
				{
					insidehouse.draw();
					canvas.putText("*whimpering from afar*", 265, 625);
				}
				else if (next == 3)
				{
					insidehouse.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("What's that? It sounds like it's... ", 300, 625);
					canvas.putText("coming from over there?", 300, 675);
				}
				else if (next == 4)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("push the furniture", 20, 300);
					canvas.putText("from multiple angles", 20, 400);
					canvas.putText("to grab the key and", 20, 500);
					canvas.putText("open the locked door", 20, 600);
				}
			}
			else if (state == CUTSCENE_4)
			{
				if (next == 1)
				{
					closetdoor.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Brother! Sister! What's going on?", 280, 625);
					canvas.putText("Why are you hiding in here?", 280, 675);
				}
				else if (next == 2)
				{
					closetdoor.draw();
					framedSiblings.draw();
					canvas.putText("Brother:", 450, 550);
					canvas.putText("Uncle's being scary!", 350, 625);
					canvas.putText("He started breaking things", 350, 675);
					canvas.putText("and chasing mom!", 350, 725);
				}
				else if (next == 3)
				{
					closetdoor.draw();
					canvas.putText("*screaming and yelling from upstairs*", 145, 625);
				}
				else if (next == 4)
				{
					closetdoor.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Get out of here, go get the police! Hurry!", 230, 625);
					canvas.putText("*girl runs upstairs towards the yelling*", 230, 675);
				}
				else if (next == 5)
				{
					room1.draw();
					framedUncle.draw();
					canvas.putText("Uncle:", 450, 550);
					canvas.putText("Purify, PURIFY!!!", 385, 625);
				}
				else if (next == 6)
				{
					room1.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Mom!", 450, 625);
					canvas.putText("*girl swings a vase at the uncle. she misses*", 185, 675);
					canvas.putText("*gets pushed aside, and falls unconscious*", 185, 725);
				}
				else if (next == 7)
				{
					darkness.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Ugh... what.. happene- oh no, mom...", 250, 625);
				}
				else if (next == 8)
				{
					room.draw();
					canvas.putText("*mom is dead on the floor, the uncle cleaning his knife*", 10, 625);
				}
				else if (next == 9)
				{
					room.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("I have to get out of here...", 310, 625);
					canvas.putText("*girl quietly moves towards door*", 280, 675);
					canvas.putText("*steps on broken vase*", 310, 725);
				}
				else if (next == 10)
				{
					room.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Shoot!", 450, 625);
					canvas.putText("*girl runs as fast as she can*", 280, 675);
				}
				else if (next == 11)
				{
					hallway1.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Ahh! What's going on?!", 300, 625);
					canvas.putText("Why is the house so... broken?", 300, 675);
				}
				else if (next == 12)
				{
					hallway1.draw();
					canvas.putText("*sound of banging on the door*", 220, 625);
				}
				else if (next == 13)
				{
					hallway1.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("I have to find a way downstairs...", 300, 625);
				}
				else if (next == 14)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("push blocks into pits", 20, 300);
					canvas.putText("and grab the key to", 20, 400);
					canvas.putText("open the locked door", 20, 500);
				}
			}
			else if (state == CUTSCENE_5)
			{
				if (next == 1)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Is that, the maid?!",290, 625);
					canvas.putText("Why are you chained to the floor?", 290, 675);
				}
				else if (next == 2)
				{
					hallway.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("Your uncle, he's... ", 345, 625);
					canvas.putText("He's gone insane!", 345, 675);
				}
				else if (next == 3)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("What's going on, do you know?", 300, 625);
					canvas.putText("Please, tell me...", 300, 675);
				}
				else if (next == 4)
				{
					hallway.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("Your uncle's been going to some cult", 240, 625);
					canvas.putText("meeting recently.  He came back today", 240, 675);
					canvas.putText("and he kept mumbling purify.", 240, 725);
				}
				else if (next == 5)
				{
					hallway.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("I thought he was just thinking out loud, but", 205, 625);
					canvas.putText("it became alarming when he picked up a", 205, 675);
					canvas.putText("knife and started chanting even louder!", 205, 725);
				}
				else if (next == 6)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("And what's wrong with the house,", 285, 625);
					canvas.putText("why's this all happening?!", 285, 675);
				}
				else if (next == 7)
				{
					hallway.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("My dear, you've gone complet-", 305, 625);
				}
				else if (next == 8)
				{
					hallway.draw();
					canvas.putText("*uncle barges in*", 285, 625);
				}
				else if (next == 9)
				{
					hallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Ahh! *girl runs into the library*", 295, 625);
				}
				else if (next == 10)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("dodge the moving", 20, 300);
					canvas.putText("bookshelves while", 20, 400);
					canvas.putText("grabbing both keys", 20, 500);
					canvas.putText("to reach the goal.", 20, 600);
				}
			}
			else if (state == CUTSCENE_6)
			{
				if (next == 1)
				{
					twistedhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("I have to get out of this place.", 300, 625);
					canvas.putText("Huh? Who's that?", 300, 675);
				}
				else if (next == 2)
				{
					twistedhallway.draw();
					framedDoctor.draw();
					canvas.putText("Man in White:", 400, 550);
					canvas.putText("...", 450, 625);
				}
				else if (next == 3)
				{
					twistedhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("I don't know who he is,", 265, 625);
					canvas.putText("but he sure doesn't look friendly...", 265, 675);
					canvas.putText("I better find a way to sneak past him.", 265, 725);
				}
				else if (next == 4)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("avoid the ghosts", 20, 300);
					canvas.putText("and men in white", 20, 400);
					canvas.font("Arial", PLAIN,40);
					canvas.putText("*hint* Arrow panels launch you", 20, 540);
					canvas.putText("until an obstacle is hit", 125, 590);
				}
			}
			else if (state == CUTSCENE_7)
			{
				if (next == 1)
				{
					basement.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Ugh... that hurt!", 355, 625);
				}
				else if (next == 2)
				{
					basement.draw();
					framedUncle.draw();
					canvas.putText("Uncle:", 450, 550);
					canvas.putText("Purify... PURIFY!", 365, 625);
				}
				else if (next == 3)
				{
					basement.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("I gotta get out of this basement...", 280, 625);
					canvas.putText("Why won't this door open?!", 280, 675);
					canvas.putText("*girl tries to open the locked door*", 280, 725);
				}
				else if (next == 4)
				{
					basement.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Wait, there must be an emergency", 275, 625);
					canvas.putText("key here somewhere!", 275, 675);
				}
				else if (next == 5)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("gather the keys", 20, 300);
					canvas.putText("and avoid the creatures", 20, 400);
					canvas.putText("to reach the goal", 20, 500);
				}
			}
			else if (state == CUTSCENE_8)
			{
				if (next == 1)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("The front door!", 360, 625);
				}
				else if (next == 2)
				{
					rottenhallway.draw();
					canvas.putText("*police officer kicks door open*", 220, 625);
				}
				else if (next == 3)
				{
					rottenhallway.draw();
					framedPolice.draw();
					canvas.putText("Police:", 450, 550);
					canvas.putText("Is anyone there?!", 370, 625);
				}
				else if (next == 4)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Oh thank heavens, get me out of her-", 260, 625);
				}
				else if (next == 5)
				{
					rottenhallway.draw();
					canvas.putText("*uncle jumps from second floor and kills police officer*", 20, 625);
				}
				else if (next == 6)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Ahh!", 445, 625);
				}
				else if (next == 7)
				{
					rottenhallway.draw();
					canvas.putText("*girl notices uncle's transformation*", 200, 625);
				}
				else if (next == 8)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("Oh... Oh god...", 360, 625);
				}
				else if (next == 9)
				{
					rottenhallway.draw();
					framedGlutton.draw();
					canvas.putText("Glutton:", 450, 550);
					canvas.putText("Now, you. I must purify you!", 320, 625);
				}
				else if (next == 10)
				{
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("To complete the level,", 20, 200);
					canvas.putText("use the arrow panels to", 20, 300);
					canvas.putText("launch blocks into the", 20, 400);
					canvas.putText("glutton to defeat him!!!", 20, 500);
				}
			}
			else if (state == CUTSCENE_9)
			{
				if (next == 1)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("It's... finally...over", 345, 625);
				}
				else if (next == 2)
				{
					rottenhallway.draw();
					framedGirl.draw();
					canvas.putText("Girl:", 450, 550);
					canvas.putText("I think I'm going to rest for a bit...", 290, 625);
				}
				else if (next == 3)
				{
					darkness.draw();
					framedFather.draw();
					canvas.putText("Father:", 450, 550);
					canvas.putText("How is she, doc?", 370, 625);
				}
				else if (next == 4)
				{
					asylum.draw();
					framedDoctor.draw();
					canvas.putText("Doctor:", 450, 550);
					canvas.putText("Well sir, I can't be quite sure...", 300, 625);
					canvas.putText("to put it bluntly, she's sure lost it.", 300, 675);
				}
				else if (next == 5)
				{
					asylum.draw();
					framedFather.draw();
					canvas.putText("Father:", 450, 550);
					canvas.putText("I don't understand, she's such an angel,", 235, 625);
					canvas.putText("how could she become such a monster", 235, 675);
					canvas.putText("and kill her own family...", 235, 725);
				}
				else if (next == 6)
				{
					asylum.draw();
					framedDoctor.draw();
					canvas.putText("Doctor:", 450, 550);
					canvas.putText("I'm sorry, sir.", 210, 625);
					canvas.putText("My condolences to your wife and children.", 210, 675);
				}
				else if (next == 7)
				{
					asylum.draw();
					framedFather.draw();
					canvas.putText("Father:", 450, 550);
					canvas.putText("Will she ever regain sanity?", 280, 625);
					canvas.putText("I have to know it wasn't the real her.", 280, 675);
					canvas.putText("It can't be.", 280, 725);
				}
				else if (next == 8)
				{
					asylum.draw();
					framedDoctor.draw();
					canvas.putText("Doctor:", 450, 550);
					canvas.putText("...", 470, 625);
				}
				else if (next == 9)
				{
					asylum.draw();
					framedMaid.draw();
					canvas.putText("Maid:", 450, 550);
					canvas.putText("Sir, I'm sorry. I've been here for many years", 200, 625);
					canvas.putText("and...I'm sorry, my dear,", 200, 675);
					canvas.putText("but she's gone completely mad.", 200, 725);
				}
				else if (next == 10)
				{
					darkness.draw();
					canvas.font("Arial", PLAIN, 70);
					canvas.putText("The End.", 250, 650);
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
			if (next != 14)
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
	}
	
	// Keyboard Input ///////////////////////////////////////////////////////////////////
	
	public void onKeyPress()
	{	
		if (showingGameplay())
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
		else if (state == MENU_STATE)
		{
			if (entryIndex < cheatCode.length && keyboard.isDown(cheatCode[entryIndex]))
			{
				entryIndex++;
				
				if (entryIndex == cheatCode.length)
				{
					FROST = !FROST;
					
					if (FROST)
						SoundPlayer.keyPickupSFX.play();
					else
						SoundPlayer.fallingSFX.play();
					
					entryIndex = 0;
				}
			}
			else if (entryIndex > 0 && keyboard.key() != cheatCode[entryIndex - 1])
			{
				entryIndex = 0;
			}
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
		
		if (FROST)
		{
			if (keyboard.isDown(keyboard.SHIFT))
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
		menuButton.position(530, 675);
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
		menuButton.position(55, 645);
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
