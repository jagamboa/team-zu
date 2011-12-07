import java.util.Random;

public class RandomGen 
{

	private static Random rand = new Random();
	
	public static Direction getRandomDirection()
	{
		int direction = rand.nextInt(5);
		
		if (direction == 0)
			return Direction.Up;
		else if (direction == 1)
			return Direction.Down;
		else if (direction == 2)
			return Direction.Right;
		else if (direction == 3)
			return Direction.Left;
		else
			return null;
	}
	
	public static int nextInt(int range)
	{
		return rand.nextInt(range);
	}

}
