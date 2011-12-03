import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.DataFormatException;


public class IO 
{
	// version number
	public static final int version = 2;
	
	// piece codes
	public static final int nullPiece = 0;
	public static final int boundary = 1;
	public static final int door = 2;
	public static final int girl = 3;
	public static final int helperCharacter = 4;
	public static final int key = 5;
	public static final int pushableBlock = 6;
	public static final int spikeball = 7;
	public static final int spikeTrap = 8;
	public static final int glutton = 9;
	
	// tile codes
	public static final int emptyTile = 0;
	public static final int arrowPanel = 1;
	public static final int goalTile = 2;
	public static final int pit = 3;
	
	// direction codes
	public static final int up = 0;
	public static final int down = 1;
	public static final int left = 2;
	public static final int right = 3;
	
	private static boolean processedGirl = false;
	private static Girl readGirl = null;
	
	private static boolean processedGlutton = false;
	private static Glutton readGlutton = null;
	
	// begins a write or read
	public static void begin()
	{
		processedGirl = false;
		readGirl = null;
		processedGlutton = false;
		readGlutton = null;
	}
	
	public static void writePiece(FileWriter outWriter, Piece p) throws IOException, DataFormatException
	{
		if (p == null)
		{
			outWriter.write(IO.nullPiece);
		}
		else if (p instanceof Boundary)
		{
			outWriter.write(IO.boundary);
		}
		else if (p instanceof Door)
		{
			outWriter.write(IO.door);
		}
		else if (p instanceof Girl)
		{
			if (!processedGirl)
			{
				outWriter.write(IO.girl);
				processedGirl = true;
			}
			else
			{
				throw new DataFormatException("A Girl Piece has already been placed on this Grid.");
			}
		}
		else if (p instanceof HelperCharacter)
		{
			outWriter.write(IO.helperCharacter);
		}
		else if (p instanceof Key)
		{
			outWriter.write(IO.key);
		}
		else if (p instanceof PushableBlock)
		{
			outWriter.write(IO.pushableBlock);
		}
		else if (p instanceof Spikeball)
		{
			outWriter.write(IO.spikeball);
			
			Direction d = ((Spikeball)p).getInitialDirection();
			
			if (d == Direction.Up)
				outWriter.write(IO.up);
			else if (d == Direction.Down)
				outWriter.write(IO.down);
			else if (d == Direction.Left)
				outWriter.write(IO.left);
			else if (d == Direction.Right)
				outWriter.write(IO.right);
			else
				throw new DataFormatException("Unrecognized Direction: " + d);
		}
		else if (p instanceof SpikeTrap)
		{
			outWriter.write(IO.spikeTrap);
		}
		else if (p instanceof Glutton)
		{
			outWriter.write(IO.glutton);
		}
		else
		{
			throw new DataFormatException("Unrecognized Piece: " + p);
		}
	}
	
	public static void writeTile(FileWriter outWriter, Tile t) throws IOException, DataFormatException
	{
		if (t == null)
		{
			throw new DataFormatException("Tile is null");
		}
		else if (t instanceof EmptyTile)
		{
			outWriter.write(IO.emptyTile);
		}
		else if (t instanceof ArrowPanel)
		{
			outWriter.write(IO.arrowPanel);
			
			Direction d = ((ArrowPanel)t).getDirection();
			
			if (d == Direction.Up)
				outWriter.write(IO.up);
			else if (d == Direction.Down)
				outWriter.write(IO.down);
			else if (d == Direction.Left)
				outWriter.write(IO.left);
			else if (d == Direction.Right)
				outWriter.write(IO.right);
			else
				throw new DataFormatException("Unrecognized Direction: " + d);
		}
		else if (t instanceof Goal)
		{
			outWriter.write(IO.goalTile);
		}
		else if (t instanceof Pit)
		{
			outWriter.write(IO.pit);
		}
		else
		{
			throw new DataFormatException("Unrecognized Tile: " + t);
		}
	}
	
	public static Piece readPiece(FileReader inReader, int x, int y) throws IOException, DataFormatException
	{
		int code = inReader.read();
		
		if (code == IO.nullPiece)
		{
			return null;
		}
		else if (code == IO.boundary)
		{
			return new Boundary(x, y);
		}
		else if (code == IO.door)
		{
			return new Door(x, y);
		}
		else if (code == IO.girl)
		{
			if (!processedGirl)
			{
				readGirl = new Girl(x, y);
				return readGirl;
			}
			else
				throw new DataFormatException("A Girl Piece has already been read from this file");
		}
		else if (code == IO.helperCharacter)
		{
			return new HelperCharacter(x, y);
		}
		else if (code == IO.key)
		{
			return new Key(x, y);
		}
		else if (code == IO.pushableBlock)
		{
			return new PushableBlock(x, y);
		}
		else if (code == IO.spikeball)
		{
			Direction d;
			
			code = inReader.read();
			
			if (code == IO.up)
				d = Direction.Up;
			else if (code == IO.down)
				d = Direction.Down;
			else if (code == IO.left)
				d = Direction.Left;
			else if (code == IO.right)
				d = Direction.Right;
			else
				throw new DataFormatException("Unrecognized Direction Code in input file: " + code);
			
			return new Spikeball(x, y, d);
		}
		else if (code == IO.spikeTrap)
		{
			return new SpikeTrap(x, y);
		}
		else if (code == IO.glutton)
		{
			if (!processedGlutton)
			{
				readGlutton = new Glutton(x, y);
				return readGlutton;
			}
			else
				throw new DataFormatException("A Glutton Piece has already been read from this file");
		}
		else
		{
			throw new DataFormatException("Unrecognized Piece Code in input file: " + code);
		}
	}
	
	public static Tile readTile(FileReader inReader, int x, int y) throws IOException, DataFormatException
	{
		int code = inReader.read();
		
		if (code == IO.emptyTile)
		{
			return new EmptyTile(x, y);
		}
		else if (code == IO.arrowPanel)
		{
			Direction d;
			
			code = inReader.read();
			
			if (code == IO.up)
				d = Direction.Up;
			else if (code == IO.down)
				d = Direction.Down;
			else if (code == IO.left)
				d = Direction.Left;
			else if (code == IO.right)
				d = Direction.Right;
			else
				throw new DataFormatException("Unrecognized Direction Code in input file: " + code);
			
			return new ArrowPanel(x, y, d);
		}
		else if (code == IO.goalTile)
		{
			return new Goal(x, y);
		}
		else if (code == IO.pit)
		{
			return new Pit(x, y);
		}
		else
		{
			throw new DataFormatException("Unrecognized Tile Code in input file: " + code);
		}
	}
	
	public static Girl getGirl()
	{
		Girl girl = readGirl;
		readGirl = null;
		return girl;
	}
	
	public static Glutton getGlutton()
	{
		return readGlutton;
	}
}
