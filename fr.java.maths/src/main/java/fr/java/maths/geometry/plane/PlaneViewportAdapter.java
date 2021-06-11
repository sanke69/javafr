package fr.java.maths.geometry.plane;

import java.text.DecimalFormat;
import java.util.function.Function;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Projector;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.types.BoundingBoxes;
import fr.java.maths.geometry.types.Coordinates;
import fr.java.maths.geometry.types.Dimensions;
import fr.java.maths.geometry.types.Points;
import fr.java.maths.window.SimpleEdges2D;
import fr.java.ui.Edges2D;
import fr.utils.maths.jMath;

public class PlaneViewportAdapter<MODEL, COORD extends Coordinate> implements Viewport.TwoDims.Editable<MODEL, COORD> {

	protected MODEL															model;
	protected Function<MODEL, BoundingBox.TwoDims>							modelBounder;
	protected Dimension.TwoDims 											window;

	protected Projector	<COORD, Coordinate.TwoDims>							pModel2Bounds;
	protected Projector.TwoDims	<Coordinate.TwoDims, Coordinate.TwoDims> 	pBounds2View;
	protected Projector.TwoDims	<Coordinate.TwoDims, Coordinate.TwoDims> 	pWindow2View;

	protected boolean 														intialized         = false;
	protected boolean 														autoCenter         = true;
	protected boolean 														noAxisDistinction  = true;

	protected Edges2D 														edges;
	protected double 														min_scale,
																			max_scale;
	protected double 														view_scale, x_scale, y_scale;
	protected Point2D.Editable 												view_anchor;

	public PlaneViewportAdapter() {
		model				= null;
		modelBounder 		= null;
		pModel2Bounds       = null;

		window				= null;

		min_scale			=   0.01;
		max_scale			= 100.0;
		edges				= null;

		view_anchor			= Points.zero2();
		view_scale			= 1d;
		x_scale             = 1d;
		y_scale             = 1d;

		pBounds2View		= PlaneViewportProjectors.newBoundsToView(this, Coordinates::of);
		pWindow2View        = PlaneViewportProjectors.newWindowToView(this, Coordinates::of);
	}
	public PlaneViewportAdapter(MODEL _model, 
								Function<MODEL, BoundingBox.TwoDims> _modelBounds,
								Projector<COORD, Coordinate.TwoDims> _modelProjector) {
		this();
		setModel( _model, _modelBounds, _modelProjector );
	}
	public PlaneViewportAdapter(MODEL _model, 
								Function<MODEL, BoundingBox.TwoDims> _modelBounds,
								Projector<COORD, Coordinate.TwoDims> _modelProjector, 
								Dimension.TwoDims _window) {
		this();
		setModel( _model, _modelBounds, _modelProjector );
		if(_window != null)
			setWindow(BoundingBoxes.of(_window));
	}
	/**
	 * 
	 * @param _model			an object that represent the source domain
	 * @param _modelFunction	a function that return a BoundingBox.TwoDims from a _model Object
	 * @param _modelProjector	a projector for source domain to view domain
	 * @param _window
	 */
	public PlaneViewportAdapter(MODEL _model, 
								Function<MODEL, BoundingBox.TwoDims> _modelFunction,
								Projector<COORD, Coordinate.TwoDims>	_modelProjector,  
								BoundingBox.TwoDims _window) {
		this();
		setModel( _model, _modelFunction, _modelProjector );
		setWindow( _window );
	}

	@Override
	public final void 									setModel(MODEL _model) {
		model         = _model;
	}
	@Override
	public final MODEL									getModel() {
		return model;
	}

	@Override
	public final void 									setModelBounder(Function<MODEL, BoundingBox.TwoDims> _modelBounder) {
		modelBounder = _modelBounder;
	}
	@Override
	public final Function<MODEL, BoundingBox.TwoDims> 	getModelBounder() {
		return modelBounder;
	}

	@Override
	public final void 									setModelProjector(Projector<COORD, Coordinate.TwoDims> _modelProjector) {
		pModel2Bounds  = _modelProjector;
	}
	@Override
	public final Projector<COORD, Coordinate.TwoDims>	getModelProjector() {
		return pModel2Bounds;
	}

	@Override
	public final void 									setModel(MODEL _model, Function<MODEL, BoundingBox.TwoDims> _getModelBounds, Projector<COORD, Coordinate.TwoDims> _modelProjector) {
		model          = _model;
		modelBounder = _getModelBounds;
		pModel2Bounds  = _modelProjector;
	}
	@Override
	public final void 									setModel(MODEL _model, Function<MODEL, BoundingBox.TwoDims> _getModelBounds) {
		model         = _model;
		modelBounder = _getModelBounds;
	}

	public final void 									setWindow(Dimension.TwoDims _window) { 
		window = _window;
	}
	@Override
	public final Dimension.TwoDims						getWindow() {
		return window;
	}

	@Override
	public BoundingBox.TwoDims							getModelBounds() {
		if(model != null && modelBounder != null)
			return modelBounder.apply( model );
		else if(modelBounder != null)
			return modelBounder.apply( null );
		return null;
	}
	@Override
	public BoundingBox.TwoDims							getModelBounds(boolean _visible) {
		if(!_visible)
			return getModelBounds();
		return noAxisDistinction ? getVisibleModelBoundsNoDistinction() : getVisibleModelBounds();
	}
	private BoundingBox.TwoDims 						getVisibleModelBounds() {
		BoundingBox.TwoDims    model  = getModelBounds();
		Dimension.TwoDims      window = getWindow();

		Coordinate.TwoDims     va     = getViewAnchor();
		double                 vs     = getViewScale();
		double                 ivs    = 1d / getViewScale();

		double                 modelL, modelR, modelT, modelB;

		if( scaledModelOverWindowX() ) {
			modelL = va.getFirst()  * ivs;
			modelR = modelL + window.getWidth()  * ivs;

			modelL = modelL < model.getMinX() ? model.getMinX() : modelL;
			modelR = modelR > model.getMaxX() ? model.getMaxX() : modelR;
		} else {
			double deltaL = 0, deltaR = 0;

			if(va.getFirst() < 0)
				deltaL = - va.getFirst();
			else if(va.getFirst() > window.getWidth() - model.getWidth() * vs)
				deltaR = va.getFirst() + model.getWidth() * vs - window.getWidth();

			modelL = model.getMinX() + deltaL / vs;
			modelR = model.getMaxX() - deltaR / vs;
		}
		if( scaledModelOverWindowY() ) {
			modelT = va.getSecond() * ivs;
			modelB = modelT + window.getHeight() * ivs;

			modelT = modelT < model.getMinY() ? model.getMinY() : modelT;
			modelB = modelB > model.getMaxY() ? model.getMaxY() : modelB;

		} else {
			double deltaT = 0, deltaB = 0;

			if(va.getSecond() < 0)
				deltaT = - va.getSecond();
			else if(va.getSecond() > window.getHeight() - model.getHeight() * vs)
				deltaB = va.getSecond() + model.getHeight() * vs - window.getHeight();

			modelT = model.getMinY() + deltaT / vs;
			modelB = model.getMaxY() - deltaB / vs;

		}

		return BoundingBoxes.fromBounds(modelL, modelT, modelR, modelB);
	}
	private BoundingBox.TwoDims 						getVisibleModelBoundsNoDistinction() {
		BoundingBox.TwoDims    model  = getModelBounds();
		Dimension.TwoDims      window = getWindow();

		Coordinate.TwoDims     va     = getViewAnchor();
		double                 vs     = getViewScale();
		double                 ivs    = 1d / getViewScale();

		BoundingBox.TwoDims    bbox   = null;
		double                 modelL, modelR, modelT, modelB;

		if( scaledModelOverWindow() ) {
			modelL = va.getFirst()  * ivs;
			modelT = va.getSecond() * ivs;
			modelR = modelL + window.getWidth()  * ivs;
			modelB = modelT + window.getHeight() * ivs;

			modelL = modelL < model.getMinX() ? model.getMinX() : modelL;
			modelR = modelR > model.getMaxX() ? model.getMaxX() : modelR;
			modelT = modelT < model.getMinY() ? model.getMinY() : modelT;
			modelB = modelB > model.getMaxY() ? model.getMaxY() : modelB;

			bbox = BoundingBoxes.fromBounds(modelL, modelT, modelR, modelB);

		} else {
			double deltaL = 0, deltaR = 0, deltaT = 0, deltaB = 0;

			if(va.getFirst() < 0)
				deltaL = - va.getFirst();
			else if(va.getFirst() > window.getWidth() - model.getWidth() * vs)
				deltaR = va.getFirst() + model.getWidth() * vs - window.getWidth();

			if(va.getSecond() < 0)
				deltaT = - va.getSecond();
			else if(va.getSecond() > window.getHeight() - model.getHeight() * vs)
				deltaB = va.getSecond() + model.getHeight() * vs - window.getHeight();

			modelL = model.getMinX() + deltaL / vs;
			modelR = model.getMaxX() - deltaR / vs;
			modelT = model.getMinY() + deltaT / vs;
			modelB = model.getMaxY() - deltaB / vs;

			bbox = BoundingBoxes.fromBounds(modelL, modelT, modelR, modelB);
		}

		return bbox;
	}

	@Override
	public final BoundingBox.TwoDims 					getViewBounds() {
		return getViewBounds(true);
	}
	@Override
	public BoundingBox.TwoDims 							getViewBounds(boolean _withEdges) {
		return _withEdges ? getEdgedViewBounds() : getNoEdgedViewBounds();
	}
	private BoundingBox.TwoDims 						getNoEdgedViewBounds() {
		return noAxisDistinction ? 
				BoundingBoxes.fromBounds(0, 0, 
										scaledModelOverWindow() ? getViewScale() * getModelBounds().getWidth()  : getWindow().getWidth(), 
										scaledModelOverWindow() ? getViewScale() * getModelBounds().getHeight() : getWindow().getHeight())
				:
				BoundingBoxes.fromBounds(0, 0, 
										scaledModelOverWindowX() ? getViewScale() * getModelBounds().getWidth()  : getWindow().getWidth(), 
										scaledModelOverWindowY() ? getViewScale() * getModelBounds().getHeight() : getWindow().getHeight());
	}
	private BoundingBox.TwoDims 						getEdgedViewBounds() {
		double viewL        = - getEdges().getLeft(), 
			   viewR 		= + getEdges().getRight()	
			    			  + (((noAxisDistinction && scaledModelOverWindow()) || scaledModelOverWindowX()) ? getViewScale() * getModelBounds().getWidth() : getWindow().getWidth()), 
			   viewT        = - getEdges().getTop(), 
			   viewB 		= + getEdges().getBottom()	
			   				  + (((noAxisDistinction && scaledModelOverWindow()) || scaledModelOverWindowY()) ? getViewScale() * getModelBounds().getHeight() : getWindow().getHeight());

		return BoundingBoxes.fromBounds(viewL, viewT, viewR, viewB);
	}

	@Override
	public final void									setMinScale(double _minScale) 			{ min_scale = _minScale; }
	@Override
	public final double 								getMinScale() 							{ return min_scale; }
	@Override
	public final void				 					setMaxScale(double _maxScale) 			{ max_scale = _maxScale; }
	@Override
	public final double 								getMaxScale() 							{ return max_scale; }

	@Override
	public final void 									setEdges(Edges2D _edges) {
		edges = _edges;
	}
	@Override
	public final Edges2D 								getEdges() {
		if (edges == null)
			edges = new SimpleEdges2D();
		return edges;
	}

	@Override
	public final double 								getViewScale() {
		return view_scale;
	}
	@Override
	public double 										getViewRatioX() {
		return x_scale;
	}
	@Override
	public double 										getViewRatioY() {
		return y_scale;
	}

	@Override
	public final Point2D 								getViewAnchor() {
		return view_anchor;
	}

	@Override
	public final void 									fitWindowToModel() {
		double tx = 0, ty = 0;

		if(autoCenter) {
			tx = (getModelBounds().getWidth()  - getWindow().getWidth())  / 2.f;
			ty = (getModelBounds().getHeight() - getWindow().getHeight()) / 2.f;
		}

		setViewScale(1d);
		setViewAnchor(-tx, -ty);
	}
	@Override
	public final void 									fitModelToWindow() {
		double mw    = getModelBounds().getWidth();
		double mh    = getModelBounds().getHeight();
		double ww    = getWindow().getWidth();
		double wh    = getWindow().getHeight();

		double tx    = 0;
		double ty    = 0;
		double scale = ww != 0 ? Math.min(ww / mw, wh / mh) : 1.0;

		if(autoCenter) {
			tx = - (scale * mw - ww) / 2.f;
			ty = - (scale * mh - wh) / 2.f;
		}

		setViewScale(scale);
		setViewAnchor(tx, ty);
	}

	@Override
	public final void 									translateView(double _dx, double _dy) {
		setViewAnchor( noAxisDistinction ? 
						internalTranslateViewNoDistinction(_dx, _dy) 
						: 
						internalTranslateView(_dx, _dy) );
	}
	@Override
	public final void 									translateView(Coordinate.TwoDims _delta) {
		setViewAnchor( noAxisDistinction ? 
						internalTranslateViewNoDistinction(_delta.getFirst(), _delta.getSecond()) 
						: 
						internalTranslateView(_delta.getFirst(), _delta.getSecond()) );
	}
	private Coordinate.TwoDims							internalTranslateView(double _dx, double _dy) {
		double minX = getViewBounds().getMinX();
		double minY = getViewBounds().getMinY();

		double _tx  = getViewAnchor().getX() + _dx;
		double _ty  = getViewAnchor().getY() + _dy;

		if (scaledModelOverWindowX()) {
			double maxX = - ( getViewBounds().getMaxX() - getWindow().getWidth() );
			_tx = _tx < maxX ? maxX : _tx > - minX ? - minX : _tx;
		} else {
			double maxX = getViewBounds().getMaxX() - getModelBounds().getWidth() * getViewScale();
			_tx = _tx < minX ? minX : _tx > maxX ? maxX : _tx;
		}

		if (scaledModelOverWindowY()) {
			double maxY = - ( getViewBounds().getMaxY() - getWindow().getHeight() );
			_ty = _ty < maxY ? maxY : _ty > - minY ? - minY : _ty;
		} else {
			double maxY = getViewBounds().getMaxY() - getModelBounds().getHeight() * getViewScale();
			_ty = _ty < minY ? minY : _ty > maxY ? maxY : _ty;
		}

		return Coordinates.of(_tx, _ty);
	}
	private Coordinate.TwoDims 							internalTranslateViewNoDistinction(double _dx, double _dy) {
		double minX = getViewBounds().getMinX();
		double minY = getViewBounds().getMinY();

		double _tx  = getViewAnchor().getX() + _dx;
		double _ty  = getViewAnchor().getY() + _dy;

		if (scaledModelOverWindow()) {
			double maxX = - ( getViewBounds().getMaxX() - getWindow().getWidth() );
			double maxY = - ( getViewBounds().getMaxY() - getWindow().getHeight() );

			_tx = _tx < maxX ? maxX : _tx > - minX ? - minX : _tx;
			_ty = _ty < maxY ? maxY : _ty > - minY ? - minY : _ty;
		} else {
			Dimension.TwoDims modelBoundsInView = Dimensions.scaled(getModelBounds(), getViewScale());
			double maxX = getViewBounds().getMaxX() - modelBoundsInView.getWidth();
			double maxY = getViewBounds().getMaxY() - modelBoundsInView.getHeight();

			_tx = _tx < minX ? minX : _tx > maxX ? maxX : _tx;
			_ty = _ty < minY ? minY : _ty > maxY ? maxY : _ty;
		}

		return Coordinates.of(_tx, _ty);
	}

	@Override
	public final void 									scaleView(double _scale, double _cx, double _cy) {
		if (_scale == getViewScale() 
			&& _cx == getViewAnchor().getX()
			&& _cy == getViewAnchor().getY())
			return;

		double previousScale = getViewScale();
		double scale         = jMath.clamp(_scale, getMinScale(), getMaxScale()); // _scale < getMinScale() ? getMinScale() : _scale > getMaxScale() ? getMaxScale() : _scale;

		setViewScale(scale);
		
		double ratio = (scale - previousScale) / previousScale;

		Coordinate.TwoDims 
		nxtAnchor = noAxisDistinction ? 
						internalScaleViewNoDistinction(ratio, _cx, _cy) 
						: 
						internalScaleView(ratio, _cx, _cy);

		setViewAnchor(nxtAnchor.getFirst(), nxtAnchor.getSecond());
/*
		_cx = (1d + ratio) * getViewAnchor().getFirst()  
			- ratio        * (autoCenter ? _cx : getWindow().getWidth()  / 2d);
		_cy = (1d + ratio) * getViewAnchor().getSecond() 
			- ratio        * (autoCenter ? _cy : getWindow().getHeight() / 2d);

		double minX = getViewBounds().getMinX();
		double minY = getViewBounds().getMinY();

		if (scaledModelOverWindowX()) {
			double maxX = - ( getViewBounds().getMaxX() - getWindow().getWidth() );
			_cx = _cx < maxX ? maxX : _cx > - minX ? - minX : _cx;
		} else {
			Dimension.TwoDims modelBoundsInView = Dimensions.scaled(getModelBounds(), getViewScale());
			double maxX = getViewBounds().getMaxX() - modelBoundsInView.getWidth();
			_cx = _cx < minX ? minX : _cx > maxX ? maxX : _cx;
		}
		if (scaledModelOverWindowY()) {
			double maxY = - ( getViewBounds().getMaxY() - getWindow().getHeight() );
			_cy = _cy < maxY ? maxY : _cy > - minY ? - minY : _cy;
		} else {
			Dimension.TwoDims modelBoundsInView = Dimensions.scaled(getModelBounds(), getViewScale());
			double maxY = getViewBounds().getMaxY() - modelBoundsInView.getHeight();
			_cy = _cy < minY ? minY : _cy > maxY ? maxY : _cy;
		}
/*
		if (scaledModelOverWindow()) {
			double maxX = - ( getViewBounds().getMaxX() - getWindow().getWidth() );
			double maxY = - ( getViewBounds().getMaxY() - getWindow().getHeight() );

			_cx = _cx < maxX ? maxX : _cx > - minX ? - minX : _cx;
			_cy = _cy < maxY ? maxY : _cy > - minY ? - minY : _cy;
		} else {
			Dimension.TwoDims modelBoundsInView  = Dimensions.scaled(getModelBounds(), getViewScale());

			double maxX = getViewBounds().getMaxX() - modelBoundsInView.getWidth();
			double maxY = getViewBounds().getMaxY() - modelBoundsInView.getHeight();

			_cx = _cx < minX ? minX : _cx > maxX ? maxX : _cx;
			_cy = _cy < minY ? minY : _cy > maxY ? maxY : _cy;
		}

		setViewAnchor(_cx, _cy);
*/
	}
	@Override
	public final void 									scaleView(double _scale, Coordinate.TwoDims _anchor) {
		scaleView(_scale, _anchor.getFirst(), _anchor.getSecond());
	}
	private Coordinate.TwoDims							internalScaleView(double _ratio, double _cx, double _cy) {
		_cx = (1d + _ratio) * getViewAnchor().getFirst()  
			- _ratio        * (autoCenter ? _cx : getWindow().getWidth()  / 2d);
		_cy = (1d + _ratio) * getViewAnchor().getSecond() 
			- _ratio        * (autoCenter ? _cy : getWindow().getHeight() / 2d);

		double minX = getViewBounds().getMinX();
		double minY = getViewBounds().getMinY();

		if (scaledModelOverWindowX()) {
			double maxX = - ( getViewBounds().getMaxX() - getWindow().getWidth() );
			_cx = _cx < maxX ? maxX : _cx > - minX ? - minX : _cx;
		} else {
			Dimension.TwoDims modelBoundsInView = Dimensions.scaled(getModelBounds(), getViewScale());
			double maxX = getViewBounds().getMaxX() - modelBoundsInView.getWidth();
			_cx = _cx < minX ? minX : _cx > maxX ? maxX : _cx;
		}
		if (scaledModelOverWindowY()) {
			double maxY = - ( getViewBounds().getMaxY() - getWindow().getHeight() );
			_cy = _cy < maxY ? maxY : _cy > - minY ? - minY : _cy;
		} else {
			Dimension.TwoDims modelBoundsInView = Dimensions.scaled(getModelBounds(), getViewScale());
			double maxY = getViewBounds().getMaxY() - modelBoundsInView.getHeight();
			_cy = _cy < minY ? minY : _cy > maxY ? maxY : _cy;
		}

		return Coordinates.of(_cx, _cy);
	}
	private Coordinate.TwoDims							internalScaleViewNoDistinction(double _ratio, double _cx, double _cy) {
		_cx = (1d + _ratio) * getViewAnchor().getFirst()  
			- _ratio        * (autoCenter ? _cx : getWindow().getWidth()  / 2d);
		_cy = (1d + _ratio) * getViewAnchor().getSecond() 
			- _ratio        * (autoCenter ? _cy : getWindow().getHeight() / 2d);

		double minX = getViewBounds().getMinX();
		double minY = getViewBounds().getMinY();

		if(scaledModelOverWindow()) {
			double maxX = - ( getViewBounds().getMaxX() - getWindow().getWidth() );
			double maxY = - ( getViewBounds().getMaxY() - getWindow().getHeight() );

			_cx = _cx < maxX ? maxX : _cx > - minX ? - minX : _cx;
			_cy = _cy < maxY ? maxY : _cy > - minY ? - minY : _cy;
		} else {
			Dimension.TwoDims modelBoundsInView  = Dimensions.scaled(getModelBounds(), getViewScale());

			double maxX = getViewBounds().getMaxX() - modelBoundsInView.getWidth();
			double maxY = getViewBounds().getMaxY() - modelBoundsInView.getHeight();

			_cx = _cx < minX ? minX : _cx > maxX ? maxX : _cx;
			_cy = _cy < minY ? minY : _cy > maxY ? maxY : _cy;
		}

		return Coordinates.of(_cx, _cy);
	}

	@Override 
	public final Coordinate.TwoDims 					modelInBounds(COORD _model_point)				 { return pModel2Bounds.toImage(_model_point); }

	@Override 
	public final COORD 									boundsInModel(Coordinate.TwoDims _bounds_point)	 { return pModel2Bounds.toKernel(_bounds_point); }
	@Override
	public final Coordinate.TwoDims 					boundsInView(Coordinate.TwoDims _bounds_point) 	 { return pBounds2View.toImage(_bounds_point); }

	@Override
	public final Coordinate.TwoDims 					viewInBounds(Coordinate.TwoDims _view_point) 	 { return pBounds2View.toKernel(_view_point); }

	@Override
	public final Coordinate.TwoDims 					viewInWindow(Coordinate.TwoDims _view_point)	 { return pWindow2View.toKernel(_view_point); }
	@Override
	public final Coordinate.TwoDims						windowInView(Coordinate.TwoDims _cursor)		 { return pWindow2View.toImage(_cursor); }

	@Override
	public String 										toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#.##");

		sb.append( getModel() + "  --  (" + view_anchor.toString(df) + " : x" + df.format(getViewScale()) + ")  -->  " + getWindow() );

		return sb.toString();
	}

	protected final void								setBoundToView(Projector.TwoDims<Coordinate.TwoDims, Coordinate.TwoDims> _pBounds2View) {
		pBounds2View = _pBounds2View;
	}
	protected final void								setWindowToView(Projector.TwoDims<Coordinate.TwoDims, Coordinate.TwoDims> _pWindow2View) {
		pWindow2View = _pWindow2View;
	}

	protected final void 								setViewScale(double _scale) {
		view_scale = _scale;
	}

	protected final void 								setViewAnchor(double _tx, double _ty) {
		view_anchor.set( _tx, _ty );
	}
	protected final void 								setViewAnchor(Coordinate.TwoDims _anchor) {
		view_anchor.set( _anchor.getFirst(), _anchor.getSecond() );
	}

}
