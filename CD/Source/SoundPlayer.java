import ucigame.*;

public class SoundPlayer 
{
	public static FXPlayer arrowPanelSFX;
	public static FXPlayer blockPushSFX;
	public static FXPlayer doorOpenSFX;
	public static FXPlayer keyPickupSFX;
	public static FXPlayer fallingSFX;
	public static FXPlayer trapChargeSFX;
	public static FXPlayer gluttonChargeSFX;
	public static FXPlayer gluttonDeathSFX;
	public static FXPlayer gluttonHurtSFX;
	public static FXPlayer gluttonLaughSFX;
	
	private static BGMPlayer grandWaltzBGM;
	private static BGMPlayer hopefulUnderstandingBGM;
	private static BGMPlayer cutsceneBGM;
	
	public static int currentBGM = -1;
	
	// BGM codes
	private static final int numberOfSongs = 3;
	public static final int grandWaltz = 0;
	public static final int hopefulUnderstanding = 1;
	public static final int cutscene = 2;
	
	public static void loadContent(Ucigame ucigame)
	{
		arrowPanelSFX = new FXPlayer("Sound/ArrowPanel.wav");
		blockPushSFX = new FXPlayer("Sound/PushableBlock_Move.wav");
		doorOpenSFX = new FXPlayer("Sound/DoorOpen.wav");
		keyPickupSFX = new FXPlayer("Sound/KeyPickup.wav");
		fallingSFX = new FXPlayer("Sound/Falling.wav");
		trapChargeSFX = new FXPlayer("Sound/TrapCharge.wav");
		gluttonChargeSFX = new FXPlayer("Sound/Glutton_Charge.wav");
		gluttonDeathSFX = new FXPlayer("Sound/Glutton_Death.wav");
		gluttonHurtSFX = new FXPlayer("Sound/Glutton_Hurt.wav");
		gluttonLaughSFX = new FXPlayer("Sound/Glutton_Laugh.wav");
		
		grandWaltzBGM = new BGMPlayer("Sound/Grand_Waltz.mp3", ucigame);
		hopefulUnderstandingBGM = new BGMPlayer("Sound/Hopeful_Understanding.mp3", ucigame);
		cutsceneBGM = new BGMPlayer("Sound/Hopeful_Understanding.mp3", ucigame);
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
		else if (bgmCode == cutscene)
		{
			cutsceneBGM.play();
			currentBGM = cutscene;
		}
	}
	
	public static void stopAll()
	{
		grandWaltzBGM.stop();
		hopefulUnderstandingBGM.stop();
		cutsceneBGM.stop();
	}
	
	public static String getBGMName()
	{
		if (currentBGM == grandWaltz)
		{
			return "Grand Waltz";
		}
		else if (currentBGM == hopefulUnderstanding)
		{
			return "Hopeful Understanding";
		}
		else if (currentBGM == cutscene)
		{
			return "Cutscene";
		}
		else
		{
			return "None";
		}
	}
	
	public static void nextBGM()
	{
		currentBGM++;
		
		if (currentBGM >= numberOfSongs)
			currentBGM = 0;
		
		playBGM(currentBGM);
	}
	
	public static void prevBGM()
	{
		currentBGM--;
		
		if (currentBGM < 0)
			currentBGM = numberOfSongs - 1;
		
		playBGM(currentBGM);
	}
}
