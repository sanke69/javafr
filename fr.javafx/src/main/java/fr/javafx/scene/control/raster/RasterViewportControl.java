package fr.javafx.scene.control.raster;

import java.awt.image.BufferedImage;

import fr.drawer.fx.raster.RasterDrawer;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.window.Edges2D;
import fr.java.rasters.XRaster;
import fr.java.sdk.math.BoundingBoxes;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.geometry.plane.PlaneViewportAdapter;
import fr.java.sdk.math.geometry.plane.PlaneViewportProjectors;
import fr.java.sdk.patterns.geometry.Boundables;
import fr.javafx.scene.canvas.ResizableCanvas;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.utils.FxImageUtils;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class RasterViewportControl extends PlaneViewportControl<XRaster, Point2D> {
	protected ObjectProperty<XRaster>	rasterProperty;
	protected ObjectProperty<Color> 	backgroundColor;

	public RasterViewportControl() {
		super(60, new PlaneViewportAdapter<XRaster, Point2D>());

		rasterProperty  = new SimpleObjectProperty<XRaster>();
//		backgroundColor = new SimpleObjectProperty<Color>(Color.BLACK);
		backgroundColor = new SimpleObjectProperty<Color>( new Color(69/255.0,169/255.0,69/255.0,0.85) );

		getViewport().setModelBounder	( raster -> raster != null ? raster : BoundingBoxes.of(0, 0, 1, 1) );
		getViewport().setModelProjector	( PlaneViewportProjectors.newIdentity(getViewport(), Points::of, Point2D::getX, Point2D::getY) );

		getViewport().setEdges			( new RasterViewportEdges( getViewport() ) );
		getViewport().setWindow			( Boundables.of(this) );
		viewportProperty().fireValueChangedEvent();

		rasterProperty . addListener((_obs, _old, _new) -> {
			getViewport().setModel( _new );
			viewportProperty().fireValueChangedEvent();
		});
	}
	public RasterViewportControl(int _width, int _height) {
		this();
		setFixedSize(_width, _height);
		setBackgroundColor(Color.MAGENTA);
	}
	public RasterViewportControl(XRaster _raster) {
		this();
		setRaster(_raster);
		setBackgroundColor(Color.WHITE);
	}
	public RasterViewportControl(XRaster _raster, Color _background) {
		this();
		setRaster(_raster);
		setBackgroundColor(_background);
	}
	public RasterViewportControl(Image _img) {
		this();
		BufferedImage img = FxImageUtils.fromFXImage​( _img, (BufferedImage) new BufferedImage((int) _img.getWidth(), (int) _img.getHeight(), BufferedImage.TYPE_3BYTE_BGR));
		setRaster(img);
		setBackgroundColor(Color.WHITE);
	}
	public RasterViewportControl(BufferedImage _img) {
		this();
		setRaster(_img);
		setBackgroundColor(Color.WHITE);
	}
	public RasterViewportControl(NumberTensor _tensor) {
		this();
		setRaster(_tensor);
		setBackgroundColor(Color.WHITE);
	}

	@Override
	protected Skin<?> 				createDefaultSkin() {
		return new RasterViewportSkin();
	}

	public ObjectProperty<XRaster> 	rasterProperty() {
		return rasterProperty;
	}
	public XRaster 					getRaster() {
		return rasterProperty.get();
	}
	public void 					setRaster(Object _object) {
		XRaster raster = XRaster.Collection.newXRaster(_object);

		if(_object == null) {
			rasterProperty.set( null );
			requestRefresh();
			return ;
		}

		rasterProperty.set(raster);
		requestRefresh();
		Platform.runLater(() -> viewportProperty().fireValueChangedEvent());
	}

	public ObjectProperty<Color> 	backgroundColorProperty() {
		return backgroundColor;
	}
	public void 					setBackgroundColor(Color _backgroundColor) {
		backgroundColor.set( _backgroundColor );
	}
	public Color 					getBackgroundColor() {
		return backgroundColor.get();
	}

	public void 					refresh() {
		Skin<?> skin = getSkin();
		if( skin instanceof RasterViewportSkin)
			((RasterViewportSkin) skin).refresh();
	}

	class RasterViewportEdges implements Edges2D {
		Viewport.TwoDims<?, ?> vp;

		RasterViewportEdges(Viewport.TwoDims<?, ?> _vp) {
			super();
			vp = _vp;
		}

		@Override public double getLeft()   {
			if(vp.getModelBounds() == null)
				return 0;

			double mw = vp.getModelBounds().getWidth();
			double ww = vp.getWindow().getWidth();
			double vs = vp.getViewScale();

			return ( mw * vs <= ww ) ? mw * vs / 2.f : ww / 2.f;
		}
		@Override public double getTop()    {
			if(vp.getModelBounds() == null)
				return 0;

			double mh = vp.getModelBounds().getHeight();
			double wh = vp.getWindow().getHeight();
			double vs = vp.getViewScale();

			return ( mh * vs <= wh ) ? mh * vs / 2.f : wh / 2.f;
		}

		@Override public double getRight()  { return getLeft(); }
		@Override public double getBottom() { return getTop(); }

	}

	class RasterViewportSkin  extends StackPane implements Skin<RasterViewportControl> {
		private final ResizableCanvas canvas;
	
		public RasterViewportSkin() {
			super();
			canvas = new ResizableCanvas();
			canvas . widthProperty()  . bind(RasterViewportControl.this.widthProperty());
			canvas . heightProperty() . bind(RasterViewportControl.this.heightProperty());

			setStyle("-fx-background-color: red;");
			getChildren().add(canvas);
		}

		@Override
		public RasterViewportControl 						getSkinnable() {
			return RasterViewportControl.this;
		}
	
		@Override
		public Region		 								getNode() {
			return this;
		}
	
		public void 										refresh() {
			RasterDrawer 
			rasterDrawer = RasterDrawer.Factory.create( getRaster() );

			if( getBackgroundColor() != null)
				rasterDrawer . clear(canvas, getBackgroundColor());
			else
				rasterDrawer . clear(canvas, Color.WHITE);

			if(autofitContentToControl())
				fitContentToControl();
			else if(autofitToContent())
				fitToContent();
	
			if(getRaster() != null)
				rasterDrawer.paint(canvas, getViewport());
		}
	
		@Override
		public void 										dispose() {
			;
		}
	
	}

}
