package fr.javafx.scene.control.viewport.planar.implementations.tiles;

import java.awt.image.BufferedImage;

import fr.drawer.fx.DrawerFx;
import fr.drawer.fx.tiles.TileDrawerFx;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.Viewport;
import fr.java.math.topology.Coordinate;
import fr.java.math.window.Edges2D;
import fr.java.patterns.tileable.TileProvider;
import fr.java.patterns.tileable.TileViewport;
import fr.java.sdk.patterns.geometry.Boundables;
import fr.java.sdk.patterns.tileable.TileViewportAdapter;
import fr.javafx.scene.canvas.ResizableCanvas;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.StackPane;

public class TileViewportControl<MODEL, COORD extends Coordinate.TwoDims> extends PlaneViewportControl<MODEL, COORD> {
	TileProvider<BufferedImage>	tileProvider;

	public TileViewportControl() {
		this(60, new TileViewportAdapter<>());
	}
	public TileViewportControl(TileViewport.Editable<MODEL, COORD> _viewport) {
		this(60, _viewport, null);
	}
	public TileViewportControl(TileProvider<BufferedImage> _tileProvider) {
		this(60, new TileViewportAdapter<>(), _tileProvider);
	}
	public TileViewportControl(TileViewport.Editable<MODEL, COORD> _viewport, TileProvider<BufferedImage> _tileProvider) {
		this(60, _viewport, _tileProvider);
	}
	protected TileViewportControl(int _fps, TileViewport.Editable<MODEL, COORD> _viewport) {
		this(_fps, _viewport, null);
	}
	protected TileViewportControl(int _fps, TileViewport.Editable<MODEL, COORD> _viewport, TileProvider<BufferedImage> _tileProvider) {
		super(_fps, _viewport);

		getViewport().setEdges	(new TileViewportEdges(_viewport));
		getViewport().setWindow	(Boundables.of(this));

		setTileProvider			(_tileProvider);
		updateViewScaleRange	(0.1, 5);
	}

	public Skin<?> 								createDefaultSkin() {
		return new TileViewportSkin();
	}

	@Override
	public TileViewport.Editable<MODEL, COORD> 	getViewport() {
		return (TileViewport.Editable<MODEL, COORD>) super.getViewport();
	}

	public void 								setTileProvider(TileProvider<BufferedImage> _tileProvider) {
		getViewport().setTileSystem	(tileProvider = _tileProvider);
	}
	public TileProvider<BufferedImage> 			getTileProvider() {
		return tileProvider;
	}

	public void 								refresh() {
		Skin<?> skin = getSkin();
		if(skin instanceof TileViewportControl<?,?>.TileViewportSkin)
			((TileViewportControl<?,?>.TileViewportSkin) skin).refresh();
	}

	protected void 								updateViewScaleRange(double _min, double _max) {
		if(getViewport() != null && getTileProvider() != null) {
			getViewport().setMinScale (_min * Math.pow(0.5, getViewport().getPreferedMapLevel()));
			getViewport().setMaxScale (_max * Math.pow(0.5, getViewport().getPreferedMapLevel() - getTileProvider().maxLevel()));
		}
	}



	class TileViewportEdges implements Edges2D {
		Viewport.TwoDims<?, ?> vp;

		TileViewportEdges(Viewport.TwoDims<?, ?> _vp) {
			super();
			vp = _vp;
		}

		@Override public double getLeft()   { return 0; }
		@Override public double getTop()    { return 0; }

		@Override public double getRight()  { return getLeft(); }
		@Override public double getBottom() { return getTop(); }

	}
	class TileViewportSkin  extends StackPane implements Skin<TileViewportControl<MODEL, COORD>> {
		private final ResizableCanvas	canvas;
		private final TileDrawerFx    	drawer;
	
		public TileViewportSkin() {
			super();
			canvas   = new ResizableCanvas();
			canvas . widthProperty()  . bind(TileViewportControl.this.widthProperty());
			canvas . heightProperty() . bind(TileViewportControl.this.heightProperty());
			
			drawer = new TileDrawerFx(canvas);

			setStyle("-fx-background-color: red;");
			getChildren().add(canvas);
		}
	
		@Override
		public TileViewportControl<MODEL, COORD> getSkinnable() {
			return TileViewportControl.this;
		}
	
		@Override
		public Node 					getNode() {
			return this;
		}
	
		public void 					refresh() {
			DrawerFx.clear(canvas.getGraphicsContext2D(), Colors.BLACK);
	
			drawer.drawTiles(getViewport(), getTileProvider());
		}
	
		@Override
		public void dispose() {
			;
		}
	
	}

}
