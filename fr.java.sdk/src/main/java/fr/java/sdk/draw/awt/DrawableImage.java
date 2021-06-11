package fr.java.sdk.draw.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import fr.java.draw.Drawable;
import fr.java.draw.Drawer;

public class DrawableImage extends BufferedImage implements DrawableAreaAwt {
	Color 	BACKGROUND_COLOR 	= new Color(1, 1, 1, 1);

	private final Graphics 			 graphics;

	transient Drawer				 drawer;

	public DrawableImage(double width, double height) {
		super((int) width, (int) height, BufferedImage.TYPE_3BYTE_BGR);
		
		graphics = getGraphics();
		graphics.setColor(BACKGROUND_COLOR);
		graphics.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public Drawer getDrawer() {
		if(drawer == null)
			drawer = DrawerAwt.newDrawer(graphics);
		return drawer;
	}

	@Override
	public void add(Drawable _drawable) {
		
	}

	@Override
	public void remove(Drawable _drawable) {
		
	}

}
