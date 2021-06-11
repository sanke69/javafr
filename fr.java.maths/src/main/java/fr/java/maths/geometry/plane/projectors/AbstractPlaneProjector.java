package fr.java.maths.geometry.plane.projectors;

import java.util.function.BiFunction;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Projector;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.types.BoundingBoxes;

abstract class AbstractPlaneProjector<COORD extends Coordinate.TwoDims> 
			implements Projector.TwoDims<COORD, Coordinate.TwoDims> {

	private BoundingBox.TwoDims					K,  I;

	private BiFunction<Double, Double, COORD> 	constructor;

	protected AbstractPlaneProjector(BiFunction<Double, Double, COORD> _constructor) {
		this(BoundingBoxes.unit2(), BoundingBoxes.unit2(), _constructor);
	}
	protected AbstractPlaneProjector(BoundingBox.TwoDims _kernel, BoundingBox.TwoDims _image, BiFunction<Double, Double, COORD> _constructor) {
		super();
		K           = _kernel;
		I           = _image;
		constructor = _constructor;
	}

	public final COORD 							newCoord(double _x, double _y)      { return constructor.apply(_x, _y); }
	public final COORD 							newCoord(Coordinate.TwoDims _coord) { return constructor.apply(_coord.getFirst(), _coord.getSecond()); }

	public final void							setKernel(BoundingBox.TwoDims _kernel) {
		K = _kernel;
	}
	@Override public BoundingBox.TwoDims 		getKernel() 						{ return K; }

	public final void							setImage(BoundingBox.TwoDims _image) {
		I = _image;
	}
	@Override public BoundingBox.TwoDims 		getImage() 							{ return I; }

}
