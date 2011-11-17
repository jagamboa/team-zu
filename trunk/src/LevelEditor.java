import ucigame.*;

public class LevelEditor 
{
	public PlacementType mouseMode = null;
	
	private GameState editLevel;
	private GameState testLevel;
	
	private Sprite gridSquare;
	
	private final int paletteX = 10;
	private final int paletteY = 21;
	private final int numberOfPaletteTypes = 12;
	
	// Level Variables
	private int girlX = -1;
	private int girlY = -1;
	
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
	
	public LevelEditor(Forsaken gameloop)
	{
		editLevel = new GameState(gameloop);
		editLevel.loadEmptyLevel();
		
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
		
	}
	
	public void draw()
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
		Tilesets.arrowPanelSprite.position(paletteX * Grid.SQUARE_DIMENSIONS, paletteY * Grid.SQUARE_DIMENSIONS);
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
			objectToAdd = new ArrowPanel(column, row, Direction.Up);
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
			objectToAdd = new Spikeball(column, row, Direction.Up);
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
		

}
