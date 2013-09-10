package com.yuan.common.media;

import java.awt.Toolkit;
import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.yuan.common.util.SystemTool;

public class AudioPlayer {
	
	/**
	 * 播放数字音频
	 * @param midiFile String
	 */
	public static void playMidi(String midiFile){
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(MidiSystem.getSequence(new File(midiFile)));
			sequencer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 播放模拟音频, 例如au,wav,mp3等
	 * @param sampledFile String
	 */
	public static void playSampled(String sampledFile){
		new SampledPlayer(sampledFile).start();
	}
	
	public static void beep(){
		Toolkit.getDefaultToolkit().beep();
	}
	
	public static void main(String args[])throws Exception{
		String f = SystemTool.getAppPath(AudioPlayer.class) + File.separator + "thanks.au";
		playSampled(f);
//		playMidi(f);
		System.out.println("===============");
	}

}

class SampledPlayer extends Thread{
	
	private String sampledFile;
	
	public SampledPlayer(String sampledFile){
		this.sampledFile = sampledFile;
	}
	
	public void run(){
		try {
			AudioInputStream aiStream = AudioSystem.getAudioInputStream(new File(sampledFile));
			AudioFormat baseFormat = aiStream.getFormat();
			SourceDataLine sourceDataLine = getLine(baseFormat);
			if(sourceDataLine == null){
				AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
				aiStream = AudioSystem.getAudioInputStream(decodedFormat, aiStream);
				sourceDataLine = getLine(decodedFormat);
			}
			sourceDataLine.start();
			final int BUFSIZE = 64000;
			byte[] audioData = new byte[BUFSIZE];
			int inBytes = 0;
			while(inBytes != -1){
				inBytes = aiStream.read(audioData, 0, BUFSIZE);
				if(inBytes >= 0){
					sourceDataLine.write(audioData, 0, inBytes);
				}
			}
			sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private SourceDataLine getLine(AudioFormat audioFormat){
		SourceDataLine line = null;
		try {
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			if((info.getFormats().length > 0) && (info.getFormats()[0].getSampleSizeInBits() > 0)){
				line = (SourceDataLine)AudioSystem.getLine(info);
				line.open(audioFormat);
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return line;
	}
	
}
