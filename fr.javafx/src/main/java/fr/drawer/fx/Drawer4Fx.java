package fr.drawer.fx;

import fr.java.draw.Drawer;
import fr.java.math.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public interface Drawer4Fx extends Drawer {

	public void setDrawStyle(String string);

	// JavaFX Objects
	public void drawImage(Image _img, double _x, double _y);
	public void drawImage(Image _img, double _x, double _y, double _angle);
	public void drawImage(Image _img, BoundingBox.TwoDims _in, BoundingBox.TwoDims _out);
//	public void drawImage(Image _img, double _x, double _y, double _w, double _h);

	public void drawShape(Shape _shape);
	public void drawPath(Path _path);

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
	public static javafx.scene.paint.Color fxColor(fr.java.draw.tools.Color _color) {
		return new javafx.scene.paint.Color((float) _color.toRGB().getRed(),
							                (float) _color.toRGB().getGreen(),
							                (float) _color.toRGB().getBlue(),
							                (float) _color.getOpacity());
	}
	public static javafx.scene.paint.Color fxColor(java.awt.Color awt) {
		return new javafx.scene.paint.Color((float) awt.getRed(),
							                (float) awt.getGreen(),
							                (float) awt.getBlue(),
							                (float) awt.getAlpha());
	}

}
