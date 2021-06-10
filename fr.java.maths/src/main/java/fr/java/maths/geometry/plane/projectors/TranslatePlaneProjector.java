package fr.java.maths.geometry.plane.projectors;

import java.util.function.BiFunction;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Coordinates;

abstract class TranslatePlaneProjector<COORD extends Coordinate.TwoDims> extends AbstractPlaneProjector<COORD> {

	protected TranslatePlaneProjector(BiFunction<Double, Double, COORD> _constructor) {
		super(_constructor);
	}
	protected TranslatePlaneProjector(BoundingBox.TwoDims _kernel, BoundingBox.TwoDims _image, BiFunction<Double, Double, COORD> _constructor) {
		super(_kernel, _image, _constructor);
	}

	public abstract Coordinate.TwoDims	getAnchor();

	@Override
	public COORD 						toKernel(Coordinate.TwoDims _view_point) {
		return newCoord( Coordinates.plus(_view_point, getAnchor()) );
	}

	@Override
	public Coordinate.TwoDims 			toImage(COORD _model_point) {
		return Coordinates.minus(_model_point, getAnchor());
	}

}
