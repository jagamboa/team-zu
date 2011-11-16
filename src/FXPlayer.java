import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class FXPlayer
{	
	private File soundFile;
    private AudioFormat audioFormat;
    private DataLine.Info info;
    private ThreadGroup FXThreads;
    private final int MAX_THREADS = 10;
	
	
	public FXPlayer(String filename)
	{		
		FXThreads = new ThreadGroup("FXThreads");
		soundFile = new File(filename);
		
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		audioFormat = audioStream.getFormat();
		
		info = new DataLine.Info(SourceDataLine.class, audioFormat);
	}
	
	public void play()
	{	
		if (FXThreads.activeCount() < MAX_THREADS)
			new SoundFX().start();
	}

	private class SoundFX extends Thread
	{	
		// should be large enough to read the largest .wav file
	    private final int BUFFER_SIZE = 239660;
	    private AudioInputStream audioStream;
	    private  SourceDataLine sourceLine;
		
		public SoundFX()
		{
			super(FXThreads, "FX");
			
			try {
				audioStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        try {
	            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
	            sourceLine.open(audioFormat);
	        } catch (LineUnavailableException e) {
	            e.printStackTrace();
	            System.exit(1);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	        
	        sourceLine.start();
		}
		
		@Override
		public void run() 
		{   
	        int nBytesRead = 0;
	        byte[] abData = new byte[BUFFER_SIZE];
	        while (nBytesRead != -1) {
	            try {
	                nBytesRead = audioStream.read(abData, 0, abData.length);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            if (nBytesRead >= 0) {
	                @SuppressWarnings("unused")
	                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
	            }
	        }
	        
	        sourceLine.drain();
	        sourceLine.close();
		}
		
	}
}
