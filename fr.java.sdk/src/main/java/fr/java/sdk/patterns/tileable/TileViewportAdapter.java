package fr.java.sdk.patterns.tileable;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Projector;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.BoundingBoxes;
import fr.java.maths.Coordinates;
import fr.java.maths.Points;
import fr.java.maths.geometry.plane.PlaneViewportAdapter;
import fr.java.maths.geometry.plane.PlaneViewportProjectors;
import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileProjector;
import fr.java.patterns.tileable.TileSystem;
import fr.java.patterns.tileable.TileViewport;

public class TileViewportAdapter<MODEL, COORD extends Coordinate.TwoDims> extends PlaneViewportAdapter<MODEL, COORD> implements TileViewport.Editable<MODEL, COORD> {

	class LevelUtils {
		private int	preferedLevel;
		double		lowFactor = 0.5, highFactor = 2.0;

		Function<Double, Integer> 			scale2level = s -> {
			double n = Math.log(s) / Math.log(0.5);
			int    lvl = (int) (getPrefLevel() - n);
			return (int) (lvl < getMinLevel() ? getMinLevel() : lvl > getMaxLevel() ? getMaxLevel() : lvl);
		};
		Function<Integer, Double> 			level2scale   = l -> Math.pow(0.5, getPrefLevel() - l);
		BiFunction<Integer, Double, Double> relScaleLevel = (l, s) -> s / level2scale.apply(l);
		BiFunction<Integer, Double, Double> absScaleLevel = (l, s) -> s * level2scale.apply(l);

		public LevelUtils() {
			super();
			lowFactor  = 0.5;
			highFactor = 2.0;
		}


		public void 	setPrefLevel(int _prefered_level) 						{ preferedLevel = _prefered_level; }
		public int 		getPrefLevel() 											{ return preferedLevel; }
		public int 		getMinLevel()  											{ return getTileSystem().minLevel(); }
		public int 		getMaxLevel()  											{ return getTileSystem().maxLevel(); }

		public double 	getMinScale()  											{ return lowFactor  * Math.pow(0.5, getPrefLevel() - getMinLevel()); }
		public double 	getMaxScale()  											{ return highFactor * Math.pow(0.5, getPrefLevel() - getMaxLevel()); }

		public double 	getScaleFor(int _level) 								{ return level2scale   . apply(_level); }
		public double 	getRelativeScaleFor(int _level, double _absolute_scale) { return relScaleLevel . apply(_level, _absolute_scale); }
		public double 	getAbsoluteScaleFor(int _level, double _relative_scale) { return absScaleLevel . apply(_level, _relative_scale); }
		public int    	getBestLevelAt(double _scale) 							{ return scale2level   . apply(_scale); }

		public int 		getMapLevel() {
			double max = getTileSystem().maxLevel();
			double vsc = Math.log( getViewScale() ) / Math.log(0.5);
			int    lvl = (int) (getPrefLevel() - vsc);

			return lvl < 0 ? 0 : lvl > max ? (int) max : lvl;
		}

		public double 	getMapScale() {
			return getMapScale( getMapLevel() );
		}
		public double 	getMapScale(int _mapLevel) {
			return getViewScale() / Math.pow(0.5, getPreferedMapLevel() - _mapLevel);
		}

	}

	private TileSystem																	tileSystem;
	private LevelUtils																	tileLevel;

	private Projector.TwoDims.Levelable<Coordinate.TwoDims, Coordinate.TwoDims>			pMap2View;
	private TileProjector<Coordinate.TwoDims, TileCoordinate> 							pMap2Tile;

	public TileViewportAdapter() {
		this(null, null, null, null, null);
	}
	public TileViewportAdapter(TileSystem _tileSystem) {
		this(null, null, null, _tileSystem, null);
	}
	public TileViewportAdapter(MODEL _model, Function<MODEL, BoundingBox.TwoDims> _bounder, Projector.Levelable<COORD, Coordinate.TwoDims> _projector, TileSystem _tileSystem) {
		this(_model, null, _projector, _tileSystem, null);
	}
	public TileViewportAdapter(MODEL _model, Function<MODEL, BoundingBox.TwoDims> _bounder, Projector.Levelable<COORD, Coordinate.TwoDims> _projector, TileSystem _tileSystem, Dimension.TwoDims _window) {
		super(_model, _bounder, _projector, _window);
		noAxisDistinction = true;

		setBoundToView( pMap2View  = PlaneViewportProjectors.newBoundsToView(this, Coordinates::of) );
		pMap2Tile  = PlaneViewportProjectors.newTileProjector(this, Coordinates::of, Coordinates::of);
		
		tileSystem = _tileSystem;
		tileLevel  = new LevelUtils();

		if( _bounder == null )
			setModelBounder( _dummy_ -> BoundingBoxes.of2D( getTileSystem().mapSize(getMapLevel()) ) );
	}
	public TileViewportAdapter(MODEL _model, Projector.Levelable<COORD, Coordinate.TwoDims> _projector, TileSystem _tileSystem) {
		this(_model, null, _projector, _tileSystem, null);
	}
	public TileViewportAdapter(MODEL _model, Projector.Levelable<COORD, Coordinate.TwoDims> _projector, TileSystem _tileSystem, Dimension.TwoDims _window) {
		this(_model, null, _projector, _tileSystem, _window);
	}

	@Override
	public final void 												setModelProjector(Projector.Levelable<COORD, Coordinate.TwoDims> _modelToMap) {
		super.setModelProjector( (Projector.Levelable<COORD, Coordinate.TwoDims>) _modelToMap );
	}

	@Override
	public final void 												setTileSystem(TileSystem _tileSystem) {
		tileSystem = _tileSystem;
	}
	@Override
	public final TileSystem 										getTileSystem() {
		return tileSystem;
	}

	@Override
	public final BoundingBox.TwoDims								getModelBounds() {
		return getMapBounds( tileLevel.preferedLevel );
	}

	@Override
	public final BoundingBox.TwoDims 								getViewBounds(boolean _withEdges) {
		double edgeTop      = !_withEdges ? 0 : getEdges().getTop();
		double edgeLeft     = !_withEdges ? 0 : getEdges().getLeft();
		double edgesWidth   = !_withEdges ? 0 : getEdges().getLeft() + getEdges().getRight();
		double edgesHeight  = !_withEdges ? 0 : getEdges().getTop()  + getEdges().getBottom();

		double modelWidth   = getMapBounds().getWidth();		// change from PlaneViewportAdapter
		double modelHeight  = getMapBounds().getHeight();		// change from PlaneViewportAdapter
		double viewScale    = getMapScale();					// change from PlaneViewportAdapter
		double windowWidth  = getWindow().getWidth();
		double windowHeight = getWindow().getHeight();

		if( scaledModelOverWindow() ) {
			return BoundingBoxes.of(- edgeLeft, 
									- edgeTop,
									+ edgesWidth	+ viewScale * modelWidth,
									+ edgesHeight	+ viewScale * modelHeight);
		} else {
			return BoundingBoxes.of(- edgeLeft,
									- edgeTop,
									+ edgesWidth  	+ windowWidth,
									+ edgesHeight 	+ windowHeight);
		}
	}

	@Override
	public final BoundingBox.TwoDims								getMapBounds() {
		return getMapBounds( getMapLevel() );
	}
	@Override
	public final BoundingBox.TwoDims								getMapBounds(int _level) {
		return BoundingBoxes.of2D(getTileSystem().mapSize(_level));
	}

	@Override
	public final int 												getMapLevel() {
		return tileLevel.getMapLevel();
	}
	@Override
	public final double 											getMapScale() {
		return tileLevel.getMapScale();
	}
	@Override
	public final double 											getMapScale(int _mapLevel) {
		return tileLevel.getMapScale(_mapLevel);
	}


	@Override
	public final void 												setPreferedMapLevel(int _prefered_level) {
		tileLevel.setPrefLevel( _prefered_level );
	}
	@Override
	public final int 												getPreferedMapLevel() { return tileLevel.getPrefLevel(); }

	@Override public final Coordinate.TwoDims						modelInMap(COORD _model_point) 								{ return getModelProjectorLvl().toImage(_model_point, getMapLevel()); }
	@Override public final Coordinate.TwoDims						modelInMap(COORD _model_point, int _level) 					{ return getModelProjectorLvl().toImage(_model_point, _level); }

	@Override public final COORD 									mapInModel(Coordinate.TwoDims _map_point) 					{ return getModelProjectorLvl().toKernel(_map_point, getMapLevel()); }
	@Override public final COORD 									mapInModel(Coordinate.TwoDims _map_point, int _level) 		{ return getModelProjectorLvl().toKernel(_map_point, _level); }

	@Override public final TileCoordinate 							modelInTile(COORD _model_point)   							{ return pMap2Tile.toTile(modelInMap(_model_point), getMapLevel()); }
	@Override public final TileCoordinate 							modelInTile(COORD _model_point, int _level) 				{ return pMap2Tile.toTile(modelInMap(_model_point, _level), _level); }

	@Override public final COORD 									tileInModel(TileCoordinate _tile_point)   					{ return mapInModel(pMap2Tile.fromTile(_tile_point), _tile_point.getLevel()); }

	@Override public final Coordinate.TwoDims 						windowInMap(Coordinate.TwoDims _cursor) 					{ return pMap2View.toKernel(windowInView(_cursor), getMapLevel()); }
	@Override public final Coordinate.TwoDims						windowInMap(Coordinate.TwoDims _cursor, int _level)			{ return pMap2View.toKernel(windowInView(_cursor), _level); }

	@Override public final Coordinate.TwoDims 						mapInWindow(Coordinate.TwoDims _map_point) 					{ return viewInWindow(pMap2View.toImage(_map_point, getMapLevel())); }
	@Override public final Coordinate.TwoDims 						mapInWindow(Coordinate.TwoDims _map_point, int _level) 		{ return viewInWindow(pMap2View.toImage(_map_point, _level)); }

	@Override public final TileCoordinate 							windowInTile(Coordinate.TwoDims _cursor)   					{ return pMap2Tile.toTile(windowInMap(_cursor), getMapLevel()); }
	@Override public final TileCoordinate 							windowInTile(Coordinate.TwoDims _cursor, int _level)		{ return pMap2Tile.toTile(windowInMap(_cursor), _level); }

	@Override public final Coordinate.TwoDims 						tileInWindow(TileCoordinate _tile_point)   					{ return viewInWindow(pMap2Tile.fromTile(_tile_point)); }

	@Override
	public final void		   										setCenter(COORD _center, int _level) {
		Coordinate.TwoDims center 	= modelInMap(_center, _level);

		Point2D 		  anchor    = Points.of(center).minus(window.getWidth() / 2d, window.getHeight() / 2d);
		double 			  scale     = getMapScale(_level);

//		updateView(scale, anchor);
		setViewScale(scale);
		setViewAnchor(anchor);
	}
	@Override
	public final void												setOptimizedFor(final Set<COORD> _model_coords) {
		if(getWindow() == null || getWindow().getWidth() == 0)
			return ;

		int     n          = 0;
		boolean inProgress = true;

		while(inProgress) {
			final int 			level  = n++;

			Set<Point2D> 		mapPts = _model_coords.stream()
													  .map((COORD c) -> Points.of( modelInMap(c, level) ))
													  .collect(Collectors.toSet());
			SimpleRectangle2D 	mapBox = new SimpleRectangle2D(mapPts);

			double 				MAP    = mapBox.getWidth();
			double 				WIN    = getWindow().getWidth();
			double 				Q      = WIN / MAP;

			if(Q > 0.5 && Q < 2) {
				double absScale = tileLevel.getAbsoluteScaleFor(level, Q);

				setViewScale	(absScale);

				double lvlScale = tileLevel.getScaleFor(level);
				double ratio    = (absScale - lvlScale) / lvlScale;

				Point2D anchor  = Points.of(mapBox.getMinX(), mapBox.getMinY()).times(1d + ratio);

				setViewAnchor	(anchor.times(-1));

				inProgress = false;
			}

			if(n >= tileSystem.maxLevel())
				inProgress = false;
		}
	}

	protected final Projector.Levelable<COORD, Coordinate.TwoDims>  getModelProjectorLvl() {
		return (Projector.Levelable<COORD, Coordinate.TwoDims>) super.getModelProjector();
	}

}
