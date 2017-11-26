package spaceinvaders;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Music {
    AudioPlayer MGP = AudioPlayer.player;
    AudioStream BGM;
    AudioData MD;
    Clip c;
	
	File mus;

	public Music(File file){
		mus = file;
	}
	
	 public void music() 
	    {       


	        ContinuousAudioDataStream loop = null;

	        try
	        {
	            //InputStream test = getClass().getResourceAsStream(mus);
	            AudioInputStream ais = AudioSystem.getAudioInputStream(mus);
	            
	            c = AudioSystem.getClip();
	            c.open(ais);
	            c.loop(100000);
	            c.start();
	            
	            //BGM = new AudioStream(test);
	            //AudioPlayer.player.start(BGM);
	            //MD = BGM.getData();
	            //loop = new ContinuousAudioDataStream(MD);
	        }
	        catch(FileNotFoundException e){
	            System.out.print(e.toString());
	        }
	        catch(IOException error)
	        {
	            System.out.print(error.toString());
	        } catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        MGP.start(loop);
	    }
    public void stop(){
        c.stop();
        c.close();
    }
}