package fr.java.maths.geometry.plane.projectors;

import java.util.function.BiFunction;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.tileable.TileSystem;

abstract class IdentityPlaneProjectorLevelable<COORD extends Coordinate.TwoDims> extends AbstractPlaneProjectorLevelable<COORD> {

	protected IdentityPlaneProjectorLevelable(BiFunction<Double, Double, COORD> _constructor) {
		super(_constructor);
	}
	protected IdentityPlaneProjectorLevelable(BoundingBox.TwoDims _kernel, TileSystem _tileSystem, BiFunction<Double, Double, COORD> _constructor) {
		super(_kernel, _tileSystem, _constructor);
	}

	@Override
	public COORD 				toKernel(Coordinate.TwoDims _view_point, int _level) {
		return newCoord(_view_point);
	}

	@Override
	public Coordinate.TwoDims 	toImage(COORD _model_point, int _level) {
		return _model_point;
	}

}
