package fr.java.media.video;

import java.awt.image.BufferedImage;

import fr.java.media.MediaPlayer;
import fr.java.media.video.buffer.VideoFrame;
import javafx.scene.image.Image;

public interface VideoPlayer<T> extends MediaPlayer<VideoFrame<T>> {

	public interface Awt extends VideoPlayer<BufferedImage> {
		//public Media<? extends VideoFrame.Awt>    	getPlayable();
	}

	public interface Fx extends VideoPlayer<Image> {
		//public Media<? extends VideoFrame.Fx>    	getPlayable();
	}

	public int 							getVolume();
	public void 						setVolume(int volume);

	public boolean 						isMute();
	public void 						mute(boolean _enable);
	
}