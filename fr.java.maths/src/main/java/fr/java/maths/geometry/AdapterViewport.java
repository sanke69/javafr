package fr.java.maths.geometry;

import java.util.function.Function;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Projector;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.types.Points;
import fr.java.ui.Edges2D;

@Deprecated // Must be implemented !
public abstract class AdapterViewport<MODEL, COORD extends Coordinate, VP_BOUNDS extends BoundingBox, VP_COORD extends Coordinate> 
	implements Viewport.Editable<MODEL, COORD, VP_BOUNDS, VP_COORD> {

	protected MODEL								model;
	protected Function<MODEL, VP_BOUNDS>		getModelBounds;

	protected Projector<COORD, VP_COORD>		pModel2Bounds;
	protected Projector<VP_COORD, VP_COORD> 	pBounds2View;
	protected Projector<VP_COORD, VP_COORD> 	pWindow2View;

	protected Dimension							window;

	protected boolean 							intialized         = false;
	protected boolean 							autoCenter         = true, 
												notInDomainEnabled = true;

	protected Edges2D 							edges;
	protected double 							min_scale,
												max_scale;
	protected double 							view_scale;
	protected Point2D.Editable 					view_anchor;

	public AdapterViewport() {
		model				= null;
		getModelBounds 		= null;

		window				= null;

		min_scale			=   0.01;
		max_scale			= 100.0;
		edges				= null;

		view_anchor			= Points.zero2();
		view_scale			= 1.0;

		pModel2Bounds       = null;
		pBounds2View		= null;
		pWindow2View        = null;
	}
	public AdapterViewport(MODEL _model, 
								Function<MODEL, BoundingBox.TwoDims> _modelBounds,
								Projector<COORD, Coordinate.TwoDims> _modelProjector) {
		this();
	}
	public AdapterViewport(MODEL _model, 
								Function<MODEL, BoundingBox.TwoDims> _modelBounds,
								Projector<COORD, Coordinate.TwoDims> _modelProjector, 
								Dimension.TwoDims _window) {
		this();
	}
	/**
	 * 
	 * @param _model			an object that represent the source domain
	 * @param _modelFunction	a function that return a BoundingBox.TwoDims from a _model Object
	 * @param _modelProjector	a projector for source domain to view domain
	 * @param _window
	 */
	public AdapterViewport(MODEL _model, 
								Function<MODEL, BoundingBox.TwoDims> _modelFunction,
								Projector<COORD, Coordinate.TwoDims>	_modelProjector,  
								BoundingBox.TwoDims _window) {
		this();
	}

}
