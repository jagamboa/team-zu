import ucigame.*;


public class Tilesets 
{
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
	
	public static void initialize(Forsaken gameloop)
	{
		// initialize Sprites
		emptyTileSprite = new Sprite(gameloop.getImage("Art/Grass.png"));
		arrowPanelSprite = new Sprite(32, 32);
		arrowPanelSprite.addFrames(gameloop.getImage("Art/ArrowPanel.png"), 0,0, 32,0);
		arrowPanelSprite.framerate(1);
		goalSprite = new Sprite(gameloop.getImage("Art/Goal.png"));
		pitSprite = new Sprite(gameloop.getImage("Art/Pit.png"));
		
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
		
		boundarySprite = new Sprite(gameloop.getImage("Art/Boundary.png"));
		doorSprite = new Sprite(gameloop.getImage("Art/Door.png"));
		helperCharacterSprite = new Sprite(gameloop.getImage("Art/HelperCharacter.png"));
		keySprite = new Sprite(gameloop.getImage("Art/Key.png"));
		pushableBlockSprite = new Sprite(gameloop.getImage("Art/PushableBlock.png"));
		spikeballSprite = new Sprite(gameloop.getImage("Art/SpikeBall.png"));
		spikeTrapSprite = new Sprite(gameloop.getImage("Art/SpikeTrap.png"));
		
		nullPieceSprite = new Sprite(gameloop.getImage("Art/NullPiece.png"));
		rotateButtonSprite = new Sprite(gameloop.getImage("Art/RotateButton.png"));
	}
	
	public static void loadTileSet(int tileSet)
	{
		
	}
}
