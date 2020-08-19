package Controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Som {

	private Clip clip;    
	private AudioInputStream audioIn;
	
	public Som() {
	}

	public void playwav(File audio) {
		try {
			audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(audio)));                
			clip = AudioSystem.getClip();                
			clip.open(audioIn);    
			clip.start();    
			//clip.loop(Clip.LOOP_CONTINUOUSLY);//comente essa linha caso não deseje um loop    
		}
		catch (Exception ex) {
			ex.printStackTrace();    
		}
	}
}
