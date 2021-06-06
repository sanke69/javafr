package fr.java.sdk.math.geometry.plane.projectors;

import java.util.function.BiFunction;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.tileable.TileSystem;
import fr.java.sdk.math.Coordinates;

public abstract class LinearPlaneProjectorLevelable<COORD extends Coordinate.TwoDims> extends AbstractPlaneProjectorLevelable<COORD> {

	public LinearPlaneProjectorLevelable(BiFunction<Double, Double, COORD> _constructor) {
		super(_constructor);
	}
	public LinearPlaneProjectorLevelable(BoundingBox.TwoDims _kernel, TileSystem _tileSystem, BiFunction<Double, Double, COORD> _constructor) {
		super(_kernel, _tileSystem, _constructor);
	}

	@Override
	public COORD 				toKernel(Coordinate.TwoDims _img, int _level) {
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

		return newCoord(x, y);
	};

	@Override
	public Coordinate.TwoDims 	toImage(COORD _ker, int _level) {
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

}
