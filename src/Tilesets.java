import ucigame.*;


public class Tilesets 
{
	// Tileset Codes
	public static final int busTileset = 0;
	public static final int gardenTileset = 1;
	public static final int houseTileset = 2;
	public static final int sunroomTileset = 3;
	public static final int libraryTileset = 4;
	public static final int twistedHallTileset = 5;
	public static final int basementTileset = 6;
	public static final int gluttonTileset = 7;
	
	// Sprite list
	public static Sprite emptyTileSprite;
	public static Sprite arrowPanelSprite;
	public static Sprite goalSprite;
	public static Sprite pitSprite;
	
	public static Sprite girlSprite;
	public static Sprite boundarySprite;
	public static Sprite doorSprite;
	public static Sprite helperCharacterSprite;
	public static Sprite keySprite;
	public static Sprite pushableBlockSprite;
	public static Sprite spikeballSprite;
	public static Sprite spikeTrapSprite;
	
	public static Sprite nullPieceSprite;
	public static Sprite rotateButtonSprite;
	
	// Gameloop
	// used for loading images
	private static Forsaken gameloop;
	
	public static void initialize(Forsaken gameloop)
	{
		Tilesets.gameloop = gameloop;
		
		// should be initialized with busTileset once the
		// tilesets are implemented
		Tilesets.loadTileset(-1);
		
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
		
		// initialize buttons for level editor
		nullPieceSprite = new Sprite(gameloop.getImage("Art/NullPiece.png"));
		rotateButtonSprite = new Sprite(gameloop.getImage("Art/RotateButton.png"));
	}
	
	public static void loadTileset(int tileset)
	{
		if (tileset == busTileset)
		{
			
		}
		else if (tileset == gardenTileset)
		{
			
		}
		else if (tileset == houseTileset)
		{
			
		}
		else if (tileset == sunroomTileset)
		{
			
		}
		else if (tileset == libraryTileset)
		{
			
		}
		else if (tileset == twistedHallTileset)
		{
			
		}
		else if (tileset == basementTileset)
		{
			
		}
		else if (tileset == gluttonTileset)
		{
			
		}
		else // default case, should be deleted once other tilesets are implemented
		{
			// initialize Sprites
			emptyTileSprite = new Sprite(gameloop.getImage("Art/Grass.png"));
			arrowPanelSprite = new Sprite(32, 32);
			arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), /*0,0,*/ 32,0);
			arrowPanelSprite.framerate(1);
			goalSprite = new Sprite(gameloop.getImage("Art/Goal.png"));
			pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
			
			boundarySprite = new Sprite(gameloop.getImage("Art/Boundary.png"));
			doorSprite = new Sprite(gameloop.getImage("Art/Door.png"));
			helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
			keySprite = new Sprite(gameloop.getImage("Art/Key.png"));
			pushableBlockSprite = new Sprite(gameloop.getImage("Art/PushableBlock.png"));
			spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
			spikeTrapSprite = new Sprite(gameloop.getImage("Art/SpikeTrap.png"));
		}
	}
}
