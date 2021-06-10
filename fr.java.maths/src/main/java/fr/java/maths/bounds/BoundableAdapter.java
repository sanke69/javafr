package fr.java.maths.bounds;

import java.text.NumberFormat;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.geometry.Boundable;

public abstract class BoundableAdapter implements Boundable {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean 		isEmpty() {
		return false;
	}

	@Override
	public boolean 		contains(Coordinate _pt) {
		return false;
	}
	@Override
	public boolean 		contains(BoundingBox _bb) {
		return false;
	}

	@Override
	public boolean 		intersects(BoundingBox _bb) {
		return false;
	}
	@Override
	public String 		toString(NumberFormat _nf) {
		return null;
	}

}