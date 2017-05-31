package handler;

import java.applet.AudioClip;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.Port;

import constants.RobotAudioLoader;

public class AudioHandler {

	private static AudioClip[] gameSounds;
	private static int numberOfSoundClips;
	
	public static boolean muted;
	
	public AudioHandler()
	{
		muted = false;
		numberOfSoundClips = RobotAudioLoader.class.getFields().length;
		gameSounds = new AudioClip[numberOfSoundClips];
		gameSounds[SOUND.MENU_MUSIC.ordinal()] = RobotAudioLoader.menuMusic;
		gameSounds[SOUND.ROBOT_LASER.ordinal()] = RobotAudioLoader.laserBlast;
		gameSounds[SOUND.EXIT.ordinal()] = RobotAudioLoader.exit;
		gameSounds[SOUND.MENU_SELECT.ordinal()] = RobotAudioLoader.select;
		
		init();
	}
		
	public static void playSound(SOUND sound)
	{
		if(sound.ordinal() >= gameSounds.length || muted)
			return;
		
		gameSounds[sound.ordinal()].play();
	}
	
	public static void loopSound(SOUND sound)
	{
		if(sound.ordinal() >= gameSounds.length || muted)
			return;
		
		gameSounds[sound.ordinal()].loop();
	}
	
	public static void stopSound(SOUND sound)
	{
		if(sound.ordinal() >= gameSounds.length)
			return;
		
		gameSounds[sound.ordinal()].stop();
	}
	
	public static void stopAllSound()
	{
		for(int i = 0; i < gameSounds.length; i++)
		{
			gameSounds[i].stop();
		}
	}
	
	public enum SOUND
	{
		MENU_MUSIC,
		ROBOT_LASER,
		EXIT,
		MENU_SELECT,
		POWERUP_PICKUP
	}
	
	/*
	 * Volume Control stuff i found online
	 */
	/**
	 * 
	 * @param value (a number between 0 and 1)
	 */
	
	private static ArrayList<Port> speakerPortsWithVolumeControl = new ArrayList<Port>();
	
	private void init()
	{
		try {
			Mixer.Info[] infos = AudioSystem.getMixerInfo(); 
			for (Mixer.Info info: infos) 
			{ 
				Mixer mixer = AudioSystem.getMixer(info); 
				if (mixer.isLineSupported(Port.Info.SPEAKER)) 
				{ 
					Port port;

					port = (Port)mixer.getLine(Port.Info.SPEAKER);

					port.open(); 
					if (port.isControlSupported(FloatControl.Type.VOLUME)) 
					{ 
						FloatControl volume = (FloatControl)port.getControl(FloatControl.Type.VOLUME);
//						System.out.println(info); 
//						System.out.println("- " + Port.Info.SPEAKER); 
//						System.out.println("  - " + volume); 
//					
						if(!speakerPortsWithVolumeControl.contains(port))
							speakerPortsWithVolumeControl.add(port);
					} 
					port.close(); 
				} 
				
			} 
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} 
	}
	
	public static void setVolume(float volumeLevel)
	{
		//System.out.println(speakerPortsWithVolumeControl.size());
		try {
			
			for(int i = 0; i < speakerPortsWithVolumeControl.size() ;i++)
			{
				Port port = speakerPortsWithVolumeControl.get(i);
				port.open();
				if(port.isControlSupported(FloatControl.Type.VOLUME))
				{
					FloatControl volume = (FloatControl)port.getControl(FloatControl.Type.VOLUME);
					volume.setValue(volumeLevel);
				}
				port.close();

			}
		} catch (LineUnavailableException e) {
			return;
		}
	}
	
	public static float getVolume()
	{
		try {

			for(int i = 0; i < speakerPortsWithVolumeControl.size() ;i++)
			{
				Port port = speakerPortsWithVolumeControl.get(i);
				port.open();
				if(port.isControlSupported(FloatControl.Type.VOLUME))
				{
					FloatControl volume = (FloatControl)port.getControl(FloatControl.Type.VOLUME);
					return volume.getValue();
				}
				port.close();

			}
		} catch (LineUnavailableException e) {
			return 0.0f;
		}
		
		return 0.0f;
	}
	
	
	
}
