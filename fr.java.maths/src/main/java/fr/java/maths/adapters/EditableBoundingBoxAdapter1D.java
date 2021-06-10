package fr.java.maths.adapters;

import fr.java.math.geometry.BoundingBox;

public class EditableBoundingBoxAdapter1D extends BoundingBoxAdapter1D implements BoundingBox.OneDim.Editable {
	private static final long serialVersionUID = 1L;

	public EditableBoundingBoxAdapter1D() {
		super();
	}
	public EditableBoundingBoxAdapter1D(final double _x, final double _width) {
		super();
	}

	@Override public void 							set(double _x, double _width) {
		x = _x; width = _width;
	}

	@Override public void 							setX(double _x) 			{ x = _x; }
	@Override public void 							setMinX(double _x) 			{ setX(_x); }
	@Override public void 							setMaxX(double _x) 			{ x = _x - width; }
	@Override public void 							setCenterX(double _x) 		{ x = _x - width / 2d; }
	@Override public void 							setWidth(double _width) 	{ width = _width; }

	@Override public void 							setLeft(double _left) 		{ width += x - _left; x = _left; }
	@Override public void 							setRight(double _right) 	{ width  = _right - x; }

}
