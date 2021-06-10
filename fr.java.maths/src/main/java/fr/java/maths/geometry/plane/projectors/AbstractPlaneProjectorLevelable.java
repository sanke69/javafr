package fr.java.maths.geometry.plane.projectors;

import java.util.function.BiFunction;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Projector;
import fr.java.math.topology.Coordinate;
import fr.java.maths.BoundingBoxes;
import fr.java.patterns.tileable.TileSystem;

abstract class AbstractPlaneProjectorLevelable<COORD extends Coordinate.TwoDims> 
	extends AbstractPlaneProjector<COORD>
	implements Projector.TwoDims.Levelable<COORD, Coordinate.TwoDims> {

	protected TileSystem tileSystem;
	
	protected AbstractPlaneProjectorLevelable(BiFunction<Double, Double, COORD> _constructor) {
		super(null, null, _constructor);
	}
	protected AbstractPlaneProjectorLevelable(BoundingBox.TwoDims _kernel, TileSystem _tileSystem, BiFunction<Double, Double, COORD> _constructor) {
		super(_kernel, null, _constructor);
		tileSystem = _tileSystem;
	}

	public abstract int 				getLevel();

	public final void 					setTileSystem(TileSystem _tileSystem) 	{ tileSystem = _tileSystem; }
	final public TileSystem 			getTileSystem() 						{ return tileSystem; }

	@Override
	public BoundingBox.TwoDims 			getImage() 								{ 
		return getImage( getLevel() );
	}
	public final BoundingBox.TwoDims 	getImage(int _level) 					{ 
		long mapSize = getTileSystem().mapSize(_level); 
		return BoundingBoxes.of(0, 0, mapSize, mapSize);
	}

	@Override
	public COORD 						toKernel(Coordinate.TwoDims _img) { 
		return toKernel(_img, getLevel());
	}

	@Override
	public Coordinate.TwoDims 			toImage(COORD _ker) { 
		return toImage(_ker, getLevel());
	}

}
