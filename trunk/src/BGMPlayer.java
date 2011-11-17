import ucigame.*;

public class BGMPlayer 
{
	private Sound music;
	
	public BGMPlayer(String filename, Ucigame ucigame)
	{
		music = ucigame.getSound(filename);
	}
	
	public void play()
	{
		music.loop();
	}
	
	public void stop()
	{
		music.stop();
	}
}
