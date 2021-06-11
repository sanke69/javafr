package fr.java.maths.geometry.plane;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import fr.java.lang.QuintiFunction;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Projector;
import fr.java.math.geometry.Viewport;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.types.BoundingBoxes;
import fr.java.maths.geometry.types.Coordinates;
import fr.java.patterns.tiled.TileCoordinate;
import fr.java.patterns.tiled.TileProjector;
import fr.java.patterns.tiled.TileSystem;
import fr.java.patterns.tiled.TileViewport;

public class PlaneViewportProjectors {

	// Others to Plane
	public static <C extends Coordinate.TwoDims> 
	Projector<C, Coordinate.TwoDims> 							newIdentity(Viewport.TwoDims<?, C> _viewport, BiFunction<Double, Double, C> _constructor, Function<C, Double> _getX, Function<C, Double> _getY) {

		return new Projector<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 						{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 							{ return _viewport.getViewBounds(); }

			@Override public C 						toKernel(Coordinate.TwoDims _img) 	{ return _constructor.apply(_img.getFirst(), _img.getSecond()); }
			@Override public Coordinate.TwoDims 	toImage(C _ker) 					{ return Coordinates.of( _getX.apply(_ker), _getY.apply(_ker) ); }

		};

	}
	public static <C extends Coordinate.TwoDims> 
	Projector<C, Coordinate.TwoDims> 							newLinear(Viewport.TwoDims<?, C> _viewport, BiFunction<Double, Double, C> _constructor, Function<C, Double> _getX, Function<C, Double> _getY) {

		return new Projector<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 						{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 							{ return _viewport.getViewBounds(); }

			@Override
			public C 								toKernel(Coordinate.TwoDims _img) {
				final double 	x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								w_ker  = getKernel().getWidth(),
								h_ker  = getKernel().getHeight(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								w_img  = getImage().getWidth(),
								h_img  = getImage().getHeight(),
								x_img  = _img.getFirst(),
								y_img  = _img.getSecond(),
								rx     = w_ker / w_img,
								ry     = h_ker / h_img;

				double x = x0_ker + (x_img - x0_img) * rx;
				double y = y0_ker + (y_img - y0_img) * ry;

				return _constructor.apply(x, y);
			};
			@Override
			public Coordinate.TwoDims 				toImage(C _ker) {
				final double 	sx     = getImage().getWidth()  / getKernel().getWidth(),
								sy     = getImage().getHeight() / getKernel().getHeight(),
								x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								x_ker  = _getX.apply( _ker ),
								y_ker  = _getY.apply( _ker );

				double x = x0_img + (x_ker - x0_ker) * sx;
				double y = y0_img + (y_ker - y0_ker) * sy;

				return Coordinates.of(x, y);
			};

		};

	}

	// PlaneViewport
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newBoundsToView(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		final boolean inverseYAxis = true;
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 						{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 							{ return _viewport.getViewBounds( false ); }

			@Override public C 						toKernel(Coordinate.TwoDims _img) {
				double scale = _viewport.getViewScale();

				double kx = _img.getFirst() / scale + getKernel().getMinX();
				double ky = ( inverseYAxis ? getKernel().getMaxY() - _img.getSecond() / scale : _img.getSecond() / scale + getKernel().getMinY() );

				return _constructor.apply( kx, ky );
			}
			@Override public Coordinate.TwoDims toImage(C _ker) {
				double scale = _viewport.getViewScale();

				double ix = (_ker.getFirst() - getKernel().getMinX()) * scale;
				double iy = ( inverseYAxis ? getKernel().getMaxY() - _ker.getSecond() : _ker.getSecond() - getKernel().getMinY() ) * scale;
				return Coordinates.of( ix, iy );
			}

		};
	}
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newWindowToView(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 		{ return BoundingBoxes.of( _viewport.getWindow() ); }
			@Override public BoundingBox.TwoDims 	getImage() 			{ return _viewport.getViewBounds( false ); }

			@Override public C 						toKernel(Coordinate.TwoDims _view_point) {
				Coordinate.TwoDims result = Coordinates.plus(_view_point, getAnchor());
				return _constructor.apply( result.getFirst(), result.getSecond() );
			}
			
			@Override public Coordinate.TwoDims 	toImage(C _model_point) {
				return Coordinates.minus(_model_point, getAnchor());
			}

			Coordinate.TwoDims 						getAnchor() 		{ return _viewport.getViewAnchor(); }

		};
	}

	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newIdentity(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 		{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 			{ return _viewport.getViewBounds(); }

			@Override public C 						toKernel(Coordinate.TwoDims _view_point) {
				return _constructor.apply( _view_point.getFirst(), _view_point.getSecond() );
			}
			
			@Override public Coordinate.TwoDims 	toImage(C _model_point) {
				return _model_point;
			}

		};
	}
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newLinearDeprecated(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 		{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 			{ return _viewport.getViewBounds(); }

			@Override public C 						toKernel(Coordinate.TwoDims _img) {
				final double 	x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								w_ker  = getKernel().getWidth(),
								h_ker  = getKernel().getHeight(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								w_img  = getImage().getWidth(),
								h_img  = getImage().getHeight(),

								rx     = w_ker / w_img,
								ry     = h_ker / h_img,

								x_img  = _img.getFirst(),
								y_img  = _img.getSecond();

				double x = x0_ker + (x_img - x0_img) * rx;
				double y = y0_ker + (y_img - y0_img) * ry;

				return _constructor.apply(x, y);
			};
			@Override public Coordinate.TwoDims 	toImage(C _ker) {
				final double 	sx     = getImage().getWidth()  / getKernel().getWidth(),
								sy     = getImage().getHeight() / getKernel().getHeight(),
								x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								x_ker  = _ker.getFirst(),
								y_ker  = _ker.getSecond();

				double x = x0_img + (x_ker - x0_ker) * sx;
				double y = y0_img + (y_ker - y0_ker) * sy;

				return Coordinates.of(x, y);
			};

		};
	}
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newNormalize(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 		{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 			{ return _viewport.getViewBounds(); }

			@Override public C 						toKernel(Coordinate.TwoDims _img) {
				double rx = getKernel().getWidth()  / getImage().getWidth(),
					   ry = getKernel().getHeight() / getImage().getHeight();
				double x  = getKernel().getMinX() + (_img.getFirst()  - getImage().getMinX()) * rx;
				double y  = getKernel().getMinY() + (_img.getSecond() - getImage().getMinY()) * ry;

				return _constructor.apply(x, y);
			};
			@Override public Coordinate.TwoDims 	toImage(C _ker) {
				double sx = getImage().getWidth()  / getKernel().getWidth(),
					   sy = getImage().getHeight() / getKernel().getHeight();
				double x  = getImage().getMinX() + (_ker.getFirst()  - getKernel().getMinX()) * sx;
				double y  = getImage().getMinY() + (_ker.getSecond() - getKernel().getMinY()) * sy;

				return Coordinates.of(x, y);
			};

		};
	}
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newLinearDeprecated(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor, boolean _inverseYAxis) {
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 		{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 			{ return _viewport.getViewBounds(); }

			@Override public C 						toKernel(Coordinate.TwoDims _img) {
				final double 	x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								w_ker  = getKernel().getWidth(),
								h_ker  = getKernel().getHeight(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								y1_img = getImage().getMaxY(),
								w_img  = getImage().getWidth(),
								h_img  = getImage().getHeight(),

								rx     = w_ker / w_img,
								ry     = h_ker / h_img,

								x_img  = _img.getFirst(),
								y_img  = _img.getSecond();

				double x = x0_ker + (x_img - x0_img) * rx;
				double y = y0_ker + (_inverseYAxis ? (y1_img - y_img) : (y_img - y0_img)) * ry;

				return _constructor.apply(x, y);
			};
			@Override public Coordinate.TwoDims 	toImage(C _ker) {
				final double 	sx     = getImage().getWidth()  / getKernel().getWidth(),
								sy     = getImage().getHeight() / getKernel().getHeight(),
								x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								y1_ker = getKernel().getMaxY(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								x_ker  = _ker.getFirst(),
								y_ker  = _ker.getSecond();

				double x = x0_img + (x_ker - x0_ker) * sx;
				double y = y0_img + (_inverseYAxis ? (y1_ker - y_ker) : (y_ker - y0_ker)) * sy;

				return Coordinates.of(x, y);
			};

		};
	}
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newNormalize(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor, boolean _inverseYAxis) {
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 		{ return _viewport.getModelBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 			{ return _viewport.getViewBounds(); }

			@Override public C 						toKernel(Coordinate.TwoDims _img) {
				double rx = getKernel().getWidth()  / getImage().getWidth(),
					   ry = getKernel().getHeight() / getImage().getHeight();
				double x  = getKernel().getMinX() + (_img.getFirst()  - getImage().getMinX()) * rx;
				double y  = getKernel().getMinY() + (_inverseYAxis ? (getImage().getMaxY() - _img.getSecond()) : (_img.getSecond() - getImage().getMinY())) * ry;

				return _constructor.apply(x, y);
			};
			@Override public Coordinate.TwoDims 	toImage(C _ker) {
				double sx = getImage().getWidth()  / getKernel().getWidth(),
					   sy = getImage().getHeight() / getKernel().getHeight();
				double x  = getImage().getMinX() + (_ker.getFirst()  - getKernel().getMinX()) * sx;
				double y  = getImage().getMinY() + (_inverseYAxis ? (getKernel().getMaxY() - _ker.getSecond()) : (_ker.getSecond() - getKernel().getMinY())) * sy;

				return Coordinates.of(x, y);
			};

		};
	}

	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims<C, Coordinate.TwoDims> 					newTranslate(Viewport.TwoDims<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		return new Projector.TwoDims<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 		{ return BoundingBoxes.of( _viewport.getWindow() ); }
			@Override public BoundingBox.TwoDims 	getImage() 			{ return _viewport.getViewBounds( false ); }

			@Override public C 						toKernel(Coordinate.TwoDims _view_point) {
				Coordinate.TwoDims result = Coordinates.plus(_view_point, getAnchor());
				return _constructor.apply( result.getFirst(), result.getSecond() );
			}

			@Override
			public Coordinate.TwoDims 				toImage(C _model_point) {
				return Coordinates.minus(_model_point, getAnchor());
			}

			Coordinate.TwoDims  					getAnchor() 		{ return _viewport.getViewAnchor(); }

		};
	}

	// PlaneViewportLevelable / TileViewport
	public static <C extends Coordinate> 
	Projector.Levelable<C, Coordinate.TwoDims> 					newLinearLevelable(BoundingBox.TwoDims _model, TileSystem _tileSystem, BiFunction<Double, Double, C> _constructor, Function<C, Double> _getX, Function<C, Double> _getY, Supplier<Integer> _getLevel) {
		return new Projector.Levelable<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims getKernel() 			{ return _model; }
			@Override public BoundingBox.TwoDims getImage() 			{ return getImage( _getLevel.get() ); }
			@Override public BoundingBox.TwoDims getImage(int _level) 	{ int sz = (int) _tileSystem.mapSize( _level ); return BoundingBoxes.of(0, 0, sz, sz); }

			@Override
			public C 							toKernel(Coordinate.TwoDims _img) {
				return toKernel(_img, _getLevel.get());
			}
			@Override
			public C 							toKernel(Coordinate.TwoDims _img, int _level) {
				final double 	x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								w_ker  = getKernel().getWidth(),
								h_ker  = getKernel().getHeight(),
								x0_img = getImage( _level ).getMinX(), 
								y0_img = getImage( _level ).getMinY(),
								w_img  = getImage( _level ).getWidth(),
								h_img  = getImage( _level ).getHeight(),
								x_img  = _img.getFirst(),
								y_img  = _img.getSecond(),
								rx     = w_ker / w_img,
								ry     = h_ker / h_img;
			
				double x = x0_ker + (x_img - x0_img) * rx;
				double y = y0_ker + (y_img - y0_img) * ry;
			
				return _constructor.apply(x, y);
			}
			@Override
			public Coordinate.TwoDims 			toImage(C _ker){
				final double 	sx     = getImage().getWidth()  / getKernel().getWidth(),
								sy     = getImage().getHeight() / getKernel().getHeight(),
								x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								x_ker  = _getX.apply( _ker ),
								y_ker  = _getY.apply( _ker );

				double x = x0_img + (x_ker - x0_ker) * sx;
				double y = y0_img + (y_ker - y0_ker) * sy;

				return Coordinates.of(x, y);
			}
			@Override
			public Coordinate.TwoDims 			toImage(C _ker, int _level) {
				final double 	sx     = getImage().getWidth()  / getKernel().getWidth(),
								sy     = getImage().getHeight() / getKernel().getHeight(),
								x0_ker = getKernel().getMinX(), 
								y0_ker = getKernel().getMinY(),
								x0_img = getImage().getMinX(), 
								y0_img = getImage().getMinY(),
								x_ker  = _getX.apply( _ker ),
								y_ker  = _getY.apply( _ker );
		
				double x = x0_img + (x_ker - x0_ker) * sx;
				double y = y0_img + (y_ker - y0_ker) * sy;
		
				return Coordinates.of(x, y);
			}

		};
	}

	@Deprecated // Prefer ViewToMap
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims.Levelable<C, Coordinate.TwoDims> 			newBoundsToView(TileViewport<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		return new Projector.TwoDims.Levelable<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 			{ return _viewport.getMapBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 				{ return _viewport.getViewBounds( false ); }
			@Override public BoundingBox.TwoDims 	getImage(int _level) 	{ return null; }

			@Override public C 						toKernel(Coordinate.TwoDims _img) {
				double scale = _viewport.getMapScale();

				return _constructor.apply(_img.getFirst() / scale, _img.getSecond() / scale);
			}
			@Override public C 						toKernel(Coordinate.TwoDims _img, int _level) {
				double  scale = _viewport.getMapScale(_level);

				return _constructor.apply(_img.getFirst() / scale, _img.getSecond() / scale);
			}

			@Override public Coordinate.TwoDims 	toImage(Coordinate.TwoDims _ker) {
				double scale = _viewport.getMapScale();

				return Coordinates.of( _ker.getFirst() * scale, _ker.getSecond() * scale );
			}
			@Override public Coordinate.TwoDims 	toImage(Coordinate.TwoDims _ker, int _level) {
				double scale = _viewport.getMapScale(_level);

				return Coordinates.of( _ker.getFirst() * scale, _ker.getSecond() * scale );
			}

		};
	}
	@Deprecated // Prefer ViewToMap
	public static <C extends Coordinate.TwoDims> 
	Projector.TwoDims.Levelable<C, Coordinate.TwoDims> 			newMapToView(TileViewport<?, ?> _viewport, BiFunction<Double, Double, C> _constructor) {
		return new Projector.TwoDims.Levelable<C, Coordinate.TwoDims>() {

			@Override public BoundingBox.TwoDims 	getKernel() 			{ return _viewport.getMapBounds(); }
			@Override public BoundingBox.TwoDims 	getImage() 				{ return _viewport.getViewBounds( false ); }
			@Override public BoundingBox.TwoDims 	getImage(int _level) 	{ return null; }

			@Override public C 						toKernel(Coordinate.TwoDims _img) {
				double scale = _viewport.getMapScale();

				return _constructor.apply(_img.getFirst() / scale, _img.getSecond() / scale);
			}
			@Override
			public C 								toKernel(Coordinate.TwoDims _img, int _level) {
				double  scale = _viewport.getMapScale(_level);

				return _constructor.apply(_img.getFirst() / scale, _img.getSecond() / scale);
			}

			@Override
			public Coordinate.TwoDims 				toImage(Coordinate.TwoDims _ker) {
				double scale = _viewport.getMapScale();

				return Coordinates.of( _ker.getFirst() * scale, _ker.getSecond() * scale );
			}
			@Override
			public Coordinate.TwoDims 				toImage(Coordinate.TwoDims _ker, int _level) {
				double scale = _viewport.getMapScale(_level);

				return Coordinates.of( _ker.getFirst() * scale, _ker.getSecond() * scale );
			}

		};
	}

	public static <C extends Coordinate.TwoDims, T extends TileCoordinate> 
	TileProjector<C, T> 										newTileProjector(TileViewport<?, ?> _viewport, BiFunction<Double, Double, C> _constructor, QuintiFunction<Double, Double, Integer, Integer, Integer, T> _constructor2) {
		return new TileProjector<C, T>() {

			@Override public C						fromTile(T _tile) {
				double TSZ = _viewport.getTileSystem().tileSize(_tile.getLevel());

				double X   = _tile.getI() * TSZ + _tile.getX();
				double Y   = _tile.getJ() * TSZ + _tile.getY();

				return _constructor.apply(X, Y);
			}

			@Override
			public T 								toTile(C _map_point, int _level) {
				double  TSZ = _viewport.getTileSystem().tileSize(_level);

				int     I   = (int) (_map_point.getFirst()  / TSZ);
				int     J   = (int) (_map_point.getSecond() / TSZ);
				double  X   = _map_point.getFirst()  - I * TSZ;
				double  Y   = _map_point.getSecond() - J * TSZ;

				return _constructor2.apply(X, Y, I, J, _level);
			}

		};
	}

}
