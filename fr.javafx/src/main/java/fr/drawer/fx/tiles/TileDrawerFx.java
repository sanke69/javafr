package fr.drawer.fx.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.drawer.fx.DrawerFx;
import fr.java.data.Data;
import fr.java.draw.TileDrawer;
import fr.java.maths.Coordinates;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileProvider;
import fr.java.patterns.tileable.TileViewport;
import fr.javafx.utils.FxImageUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileDrawerFx extends DrawerFx implements TileDrawer {

	public TileDrawerFx(Canvas _canvas) {
		super(_canvas);
	}
	public TileDrawerFx(DrawerFx _draweranvas) {
		super(_draweranvas);
	}

	public void 				drawTiles(TileViewport<?, ?> _tileViewport, TileProvider<BufferedImage> _tileProvider) {
		if(_tileViewport.getWindow() == null)
			return ;

		if(_tileViewport.scaledModelOverWindow())
			drawPartialMap(canvas, _tileViewport, _tileProvider);
		else
			drawFullMap(canvas, _tileViewport, _tileProvider);
	}

	private void 				drawFullMap(Canvas _canvas, TileViewport<?, ?> _tileViewport, TileProvider<BufferedImage> _tileProvider) {
		GraphicsContext gc         = _canvas.getGraphicsContext2D();

		double         X           = _tileViewport.getViewAnchor().getFirst();
		double         Y           = _tileViewport.getViewAnchor().getSecond();
		double         W           = _tileViewport.getWindow().getWidth();
		double         H           = _tileViewport.getWindow().getHeight();

		int            LVL         = _tileViewport.getMapLevel();
		double         S           = _tileViewport.getMapScale();

		int            tsz         = _tileProvider.tileSize(LVL);
		int            NB_TILES    = (int) Math.min( (Math.max(W, H) / tsz) + 1, _tileProvider.tileCount(LVL) );

		for(int J = 0; J < NB_TILES; ++J)
			for(int I = 0; I < NB_TILES; ++I)
				gc.drawImage(getTile(_tileProvider, LVL, I, J),
					  	 	 0d,  0d,  tsz,  tsz,
					  	 	 X + S * I * tsz, Y + S * J * tsz, S * tsz, S * tsz);
	}
	private void 				drawPartialMap(Canvas _canvas, TileViewport<?, ?> _tileViewport, TileProvider<BufferedImage> _tileProvider) {
		GraphicsContext gc         = _canvas.getGraphicsContext2D();

		double         X           = _tileViewport.getViewAnchor().getFirst();
		double         Y           = _tileViewport.getViewAnchor().getSecond();
		double         W           = _tileViewport.getWindow().getWidth();
		double         H           = _tileViewport.getWindow().getHeight();

		int            LVL         = _tileViewport.getMapLevel();
		double         S           = _tileViewport.getMapScale();

		int            tsz         = _tileProvider.tileSize(LVL);
		long           NB_TILES    = _tileProvider.tileCount(LVL);
		int            NB_TILES_X  = (int) Math.min((int) (W / tsz), NB_TILES) + 1;
		int            NB_TILES_Y  = (int) Math.min((int) (H / tsz), NB_TILES) + 1;

		TileCoordinate tlTile      = _tileViewport.windowInTile(Coordinates.of(0, 0), LVL);

		for(int j = 0; j < NB_TILES_Y + 1; ++j)
			for(int i = 0; i < NB_TILES_X + 1; ++i) {
				int I = tlTile.getI() + i;
				int J = tlTile.getJ() + j;

				if(I < NB_TILES && J < NB_TILES)
					gc.drawImage(getTile(_tileProvider, LVL, I, J),
						  	 	 0d,  0d,  tsz,  tsz,
						  	 	 X + S * I * tsz, Y + S * J * tsz, S * tsz, S * tsz);
			}
	}

	protected Image 			getTile(TileProvider<BufferedImage> _tileProvider, int _level, int _i, int _j) {
		Data<TileCoordinate, BufferedImage> tile = _tileProvider.get(_level, _i, _j);
		BufferedImage                       bimg = tile.getContent();
		
		if(bimg != null)
			return FxImageUtils.toFXImage(bimg, null);

		if(tile instanceof Data.Async) {
			Data.Async<TileCoordinate, BufferedImage> atile = (Data.Async<TileCoordinate, BufferedImage>) tile;
//			System.out.println(_i + "x" + _j + "@" + _level + "\t" + atile.isLoading() + " / " + atile.isLoaded());
			if(atile.hasError())
				return FxImageUtils.toFXImage(errorTile(_level, _i, _j), null);
			if(atile.isLoading())
				return FxImageUtils.toFXImage(loadingTile(_level, _i, _j), null);
		}

		return FxImageUtils.toFXImage(noTile(_level, _i, _j), null);
	}

	protected int 				tileSize(int _level) {
		return 256;
	}

	protected BufferedImage 	noTile(int _level, int _i, int _j) {
		// IF NO TILE FOUND
		String        header    = "TILE NOT FOUND";
		String        message   = _i + "x" + _j + "@" + _level;

		BufferedImage tileImage = new BufferedImage(tileSize(_level), tileSize(_level), BufferedImage.TYPE_INT_RGB);
		Graphics2D    graphics  = tileImage.createGraphics();
		int           msgWidth  = graphics.getFontMetrics().stringWidth(message),
					  hdrWidth  = graphics.getFontMetrics().stringWidth(header);
		int           msgHeight = graphics.getFontMetrics().getHeight(),
					  hdrHeight = graphics.getFontMetrics().getHeight();

		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, tileSize(_level), tileSize(_level));
		graphics.setColor(new Color(240, 240, 240));
		graphics.fillRect(1, 1, tileSize(_level) - 2, tileSize(_level) - 2);
		graphics.setColor(Color.BLACK);
		graphics.drawString(header, (tileSize(_level) - hdrWidth) / 2, (tileSize(_level) - hdrHeight) / 3);
		graphics.drawString(message, (tileSize(_level) - msgWidth) / 2, 2 * (tileSize(_level) - msgHeight) / 3);

		return tileImage;
	}

	protected BufferedImage 	loadingTile(int _level, int _i, int _j) {
		// IF NO TILE FOUND
		String        header    = "TILE LOADING";
		String        message   = _i + "x" + _j + "@" + _level;

		BufferedImage tileImage = new BufferedImage(tileSize(_level), tileSize(_level), BufferedImage.TYPE_INT_RGB);
		Graphics2D    graphics  = tileImage.createGraphics();
		int           msgWidth  = graphics.getFontMetrics().stringWidth(message),
					  hdrWidth  = graphics.getFontMetrics().stringWidth(header);
		int           msgHeight = graphics.getFontMetrics().getHeight(),
					  hdrHeight = graphics.getFontMetrics().getHeight();

		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, tileSize(_level), tileSize(_level));
		graphics.setColor(new Color(240, 240, 240));
		graphics.fillRect(1, 1, tileSize(_level) - 2, tileSize(_level) - 2);
		graphics.setColor(Color.BLACK);
		graphics.drawString(header, (tileSize(_level) - hdrWidth) / 2, (tileSize(_level) - hdrHeight) / 3);
		graphics.drawString(message, (tileSize(_level) - msgWidth) / 2, 2 * (tileSize(_level) - msgHeight) / 3);

		return tileImage;
	}

	protected BufferedImage 	errorTile(int _level, int _i, int _j) {
		// IF NO TILE FOUND
		String        header    = "TILE ERROR";
		String        message   = _i + "x" + _j + "@" + _level;

		BufferedImage tileImage = new BufferedImage(tileSize(_level), tileSize(_level), BufferedImage.TYPE_INT_RGB);
		Graphics2D    graphics  = tileImage.createGraphics();
		int           msgWidth  = graphics.getFontMetrics().stringWidth(message),
					  hdrWidth  = graphics.getFontMetrics().stringWidth(header);
		int           msgHeight = graphics.getFontMetrics().getHeight(),
					  hdrHeight = graphics.getFontMetrics().getHeight();

		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, tileSize(_level), tileSize(_level));
		graphics.setColor(new Color(240, 240, 240));
		graphics.fillRect(1, 1, tileSize(_level) - 2, tileSize(_level) - 2);
		graphics.setColor(Color.BLACK);
		graphics.drawString(header, (tileSize(_level) - hdrWidth) / 2, (tileSize(_level) - hdrHeight) / 3);
		graphics.drawString(message, (tileSize(_level) - msgWidth) / 2, 2 * (tileSize(_level) - msgHeight) / 3);

		return tileImage;
	}

}
