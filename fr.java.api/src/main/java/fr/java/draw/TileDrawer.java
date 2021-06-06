package fr.java.draw;

import java.awt.image.BufferedImage;

import fr.java.patterns.tileable.TileProvider;
import fr.java.patterns.tileable.TileViewport;

public interface TileDrawer extends Drawer {

	public void drawTiles(TileViewport<?, ?> _tileViewport, TileProvider<BufferedImage> _tileProvider);

}
