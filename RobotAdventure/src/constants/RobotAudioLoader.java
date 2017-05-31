package constants;

import java.applet.AudioClip;

import utilities.Utility;

public class RobotAudioLoader {

	//
	private static final String audio = "audio/";
	/*
	 * Load Audio constants in here
	 */
	
	public static final AudioClip menuMusic = Utility.loadAudio(audio + "menu_music.wav");
	public static final AudioClip laserBlast = Utility.loadAudio(audio + "laser_blast.wav");
	public static final AudioClip exit = Utility.loadAudio(audio + "exit.wav");
	public static final AudioClip select = Utility.loadAudio(audio + "blip_select.wav");
}
