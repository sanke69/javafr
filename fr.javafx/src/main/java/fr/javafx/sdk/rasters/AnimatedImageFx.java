package fr.javafx.sdk.rasters;

import java.awt.image.BufferedImage;

import fr.java.media.image.AnimatedImage;
import fr.java.media.image.ImageFormat;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

public class AnimatedImageFx extends Transition implements AnimatedImage<BufferedImage> {

	AnimatedImage<BufferedImage> animation;
	int							 index, duration;
	
	public AnimatedImageFx(AnimatedImage<BufferedImage> _animatedImage, int _totalMs) {
		super();
		
		animation = _animatedImage;
		duration  = _totalMs;

        setCycleCount(1);
        setCycleDuration(Duration.millis(duration));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(10);
        play();
	}
	
	
	@Override
	public BufferedImage data() {
		return animation.data(index);
	}

	@Override
	public ImageFormat format() {
		return animation.format();
	}

	@Override
	public int count() {
		return animation.count();
	}

	@Override
	public BufferedImage data(int _n) {
		return animation.data(_n);
	}

	@Override
	public int duration(int _n) {
		return animation.duration(_n);
	}

	@Override
	protected void interpolate(double k) {
        final int newIndex = Math.min((int) Math.floor(k * animation.count()), animation.count() - 1);

        if(index != newIndex)
            index = newIndex;
	}

}
