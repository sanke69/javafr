package fr.java.maths.geometry.plane.projectors;

import java.util.function.BiFunction;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Coordinates;
import fr.java.patterns.tileable.TileSystem;

abstract class TranslatePlaneProjectorLevelable<COORD extends Coordinate.TwoDims> extends AbstractPlaneProjectorLevelable<COORD> {

	protected TranslatePlaneProjectorLevelable(BiFunction<Double, Double, COORD> _constructor) {
		super(_constructor);
	}
	protected TranslatePlaneProjectorLevelable(BoundingBox.TwoDims _kernel, TileSystem _tileSystem, BiFunction<Double, Double, COORD> _constructor) {
		super(_kernel, _tileSystem, _constructor);
	}

	public abstract Coordinate.TwoDims	getAnchor();

	@Override
	public COORD 						toKernel(Coordinate.TwoDims _view_point, int _level) {
		return newCoord( Coordinates.plus(_view_point, getAnchor()) );
	}

	@Override
	public Coordinate.TwoDims 			toImage(COORD _model_point, int _level) {
		return Coordinates.minus(_model_point, getAnchor());
	}

}
