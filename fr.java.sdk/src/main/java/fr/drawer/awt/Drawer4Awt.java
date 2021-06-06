package fr.drawer.awt;

import java.awt.image.BufferedImage;

import fr.java.draw.Drawer;
import fr.java.math.geometry.BoundingBox;

public interface Drawer4Awt extends Drawer {

	public void setDrawStyle(String string);

	// AWT Objects
	public void drawImage(BufferedImage _img, double _x, double _y);
	public void drawImage(BufferedImage _img, double _x, double _y, double _angle);
	public void drawImage(BufferedImage _img, BoundingBox.TwoDims _in, BoundingBox.TwoDims _out);

	public static java.awt.Color 		   awtColor(fr.java.draw.tools.Color _color) {
		return new java.awt.Color((float) (_color.toRGB().getRed()),
					              (float) (_color.toRGB().getGreen()),
					              (float) (_color.toRGB().getBlue()),
					              (float) (_color.getOpacity()));
	}
	public static java.awt.Color 		   awtColor(javafx.scene.paint.Color fx) {
		return new java.awt.Color((float) fx.getRed(),
					              (float) fx.getGreen(),
					              (float) fx.getBlue(),
					              (float) fx.getOpacity());
	}

}
