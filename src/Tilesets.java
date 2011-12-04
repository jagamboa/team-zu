import ucigame.*;


public class Tilesets 
{
	// Tileset Codes
	public static final int defaultTileset = -1;
	public static final int busTileset = 0;
	public static final int gardenTileset = 1;
	public static final int houseTileset = 2;
	public static final int sunroomTileset = 3;
	public static final int libraryTileset = 4;
	public static final int twistedHallTileset = 5;
	public static final int basementTileset = 6;
	public static final int gluttonTileset = 7;
	
	public static int currentTileset;
	private static final int numberOfTilesets = 9;
	
	// Sprite list
	public static Sprite emptyTileSprite;
	public static Sprite arrowPanelSprite;
	public static Sprite goalSprite;
	public static Sprite pitSprite;
	
	public static Sprite girlSprite;
	public static Sprite gluttonSprite;
	public static Sprite boundarySprite;
	public static Sprite doorSprite;
	public static Sprite helperCharacterSprite;
	public static Sprite keySprite;
	public static Sprite pushableBlockSprite;
	public static Sprite spikeballSprite;
	public static Sprite spikeTrapSprite;
	
	public static Sprite nullPieceSprite;
	public static Sprite rotateButtonSprite;
	public static Sprite gluttonButtonSprite;
	
	// Gameloop
	// used for loading images
	private static Forsaken gameloop;
	
	public static void initialize(Forsaken gameloop)
	{
		Tilesets.gameloop = gameloop;
		
		// should be initialized with busTileset once the
		// tilesets are implemented
		Tilesets.loadTileset(defaultTileset);
		
		// initialize girl sprite
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
		girlSprite.play("downStand");
		
		// initialize Glutton sprite
		gluttonSprite = new Sprite(80, 94);
		gluttonSprite.addFrames(gameloop.getImage("Art/glutton_large.png"), 0,0);
		
		// initialize buttons for level editor
		nullPieceSprite = new Sprite(gameloop.getImage("Art/NullPiece.png"));
		rotateButtonSprite = new Sprite(gameloop.getImage("Art/RotateButton.png"));
		gluttonButtonSprite = new Sprite(gameloop.getImage("Art/gluttonLvlEdButton.png"));
	}
	
	public static void loadTileset(int tileset)
	{
		currentTileset = tileset;
		
		if (tileset == busTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/1floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/1goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/1boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/Door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/Key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/1block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/SpikeTrap.png"));
		}
		else if (tileset == gardenTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/2floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/2goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/2pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/2boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/Door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/Key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/2block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/SpikeTrap.png"));
		}
		else if (tileset == houseTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/3floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/3goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/3boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/3door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/3key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/3block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/SpikeTrap.png"));
		}
		else if (tileset == sunroomTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/4floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/4goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/4pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/4boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/4door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/4key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/4block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/SpikeTrap.png"));
		}
		else if (tileset == libraryTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/5floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/5goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/5pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/5boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/5door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/5key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/5block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/5direct.png"));
		}
		else if (tileset == twistedHallTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/6floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/6goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/6boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/Door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/Key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/6block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/6indirect.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/6direct.png"));
		}
		else if (tileset == basementTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/7floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/7goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/7pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/7boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/7door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/7key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/7block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/7indirect.png", 255, 255, 255));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/7direct.png"));
		}
		else if (tileset == gluttonTileset)
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/8floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/8goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/8boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/Door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/Key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/8block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/SpikeTrap.png"));
		}
		else // default case, should be deleted once other tilesets are implemented
		{
			emptyTileSprite = new Sprite(gameloop.getImage("Art/7floor.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/7goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/7pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/7boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/7door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/7key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/7block.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/7indirect.png", 255, 255, 255));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/7direct.png"));
		}
	}
	
	public static String getName()
	{
		if (currentTileset == busTileset)
		{
			return "Bus";
		}
		else if (currentTileset == gardenTileset)
		{
			return "Garden";
		}
		else if (currentTileset == houseTileset)
		{
			return "House";
		}
		else if (currentTileset == sunroomTileset)
		{
			return "Sunroom";
		}
		else if (currentTileset == libraryTileset)
		{
			return "Library";
		}
		else if (currentTileset == twistedHallTileset)
		{
			return "Twisted Hall";
		}
		else if (currentTileset == basementTileset)
		{
			return "Basement";
		}
		else if (currentTileset == gluttonTileset)
		{
			return "Glutton";
		}
		else
		{
			return "Default";
		}
	}
	
	public static void nextTileset()
	{
		currentTileset++;
		
		if (currentTileset >= numberOfTilesets)
			currentTileset = 0;
		
		loadTileset(currentTileset);
	}
	
	public static void prevTileset()
	{
		currentTileset--;
		
		if (currentTileset < 0)
			currentTileset = numberOfTilesets - 1;
		
		loadTileset(currentTileset);
	}
}
