package fr.java.maths.bounds;

import java.awt.image.BufferedImage;

import fr.java.patterns.geometry.Boundable;
import javafx.scene.layout.Region;

public class Boundables {
	
	public static final Boundable.TwoDims of(BufferedImage _image) {
		return new BoundableAdapter2D() {
			private static final long serialVersionUID = 1L;

			@Override public double getX() 		{ return 0; }
			@Override public double getY() 		{ return 0; }

			@Override public double getWidth() 	{ return _image.getWidth(); }
			@Override public double getHeight() { return _image.getHeight(); }

		};
	}

	public static final Boundable.TwoDims of(final Region _region) {
		return new BoundableAdapter2D() {
			private static final long serialVersionUID = 1L;

			@Override public double getX() 		{ return _region.getLayoutX(); }
			@Override public double getY() 		{ return _region.getLayoutY(); }

			@Override public double getWidth() 	{ return _region.getWidth(); }
			@Override public double getHeight() { return _region.getHeight(); }

		};
	}

}
