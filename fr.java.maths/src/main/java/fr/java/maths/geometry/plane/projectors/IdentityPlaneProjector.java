package fr.java.maths.geometry.plane.projectors;

import java.util.function.BiFunction;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;

public class IdentityPlaneProjector<COORD extends Coordinate.TwoDims> extends AbstractPlaneProjector<COORD> {

	public IdentityPlaneProjector(BiFunction<Double, Double, COORD> _constructor) {
		super(_constructor);
	}
	public IdentityPlaneProjector(BoundingBox.TwoDims _kernel, BoundingBox.TwoDims _image, BiFunction<Double, Double, COORD> _constructor) {
		super(_kernel, _image, _constructor);
	}

	@Override
	public COORD 				toKernel(Coordinate.TwoDims _view_point) {
		return newCoord(_view_point);
	}
	
	@Override
	public Coordinate.TwoDims 	toImage(COORD _model_point) {
		return _model_point;
	}

}
