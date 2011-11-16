import ucigame.*;

public class SoundPlayer 
{
	public static FXPlayer arrowPanelSFX;
	
	private static BGMPlayer grandWaltzBGM;
	private static BGMPlayer hopefulUnderstandingBGM;
	
	public static int currentBGM = -1;
	
	// BGM codes
	public static final int grandWaltz = 0;
	public static final int hopefulUnderstanding = 1;
	
	public static void loadContent(Ucigame ucigame)
	{
		arrowPanelSFX = new FXPlayer("Sound/ArrowPanel.wav");
		
		grandWaltzBGM = new BGMPlayer("Sound/Grand_Waltz.mp3", ucigame);
		hopefulUnderstandingBGM = new BGMPlayer("Sound/Hopeful_Understanding.mp3", ucigame);
	}
	
	public static void playBGM(int bgmCode)
	{
		stopAll();
		
		if (bgmCode == grandWaltz)
		{
			grandWaltzBGM.play();
			currentBGM = grandWaltz;
		}
		else if (bgmCode == hopefulUnderstanding)
		{
			hopefulUnderstandingBGM.play();
			currentBGM = hopefulUnderstanding;
		}
	}
	
	private static void stopAll()
	{
		grandWaltzBGM.stop();
		hopefulUnderstandingBGM.stop();
	}
}
