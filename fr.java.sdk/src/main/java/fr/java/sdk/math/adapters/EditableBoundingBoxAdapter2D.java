package fr.java.sdk.math.adapters;

import fr.java.math.geometry.BoundingBox;

public class EditableBoundingBoxAdapter2D extends BoundingBoxAdapter2D implements BoundingBox.TwoDims.Editable {
	private static final long serialVersionUID = 1L;

	public EditableBoundingBoxAdapter2D() {
		super();
	}
	public EditableBoundingBoxAdapter2D(final double _x, final double _width, final double _y, final double _height) {
		super(_x, _y, _width, _height);
	}

	@Override public void 							set(double _x, double _y, double _width, double _height) {
		x = _x; y = _y; width = _width; height = _height;
	}

	@Override public void 							setX(double _x) 			{ x = _x; }
	@Override public void 							setMinX(double _x) 			{ setX(_x); }
	@Override public void 							setMaxX(double _x) 			{ x = _x - width; }
	@Override public void 							setCenterX(double _x) 		{ x = _x - width / 2d; }
	@Override public void 							setWidth(double _width) 	{ width = _width; }

	@Override public void 							setLeft(double _left) 		{ width += x - _left; x = _left; }
	@Override public void 							setRight(double _right) 	{ width  = _right - x; }

	@Override public void 							setY(double _y) 			{ setMinY(_y); }
	@Override public void 							setMinY(double _y) 			{ y = _y; }
	@Override public void 							setMaxY(double _y) 			{ y = _y - height; }
	@Override public void 							setCenterY(double _y) 		{ y = _y - height / 2d; }
	@Override public void 							setHeight(double _height) 	{ height = _height; }

	@Override public void 							setTop(double _top) 		{ height += y - _top; y = _top; }
	@Override public void 							setBottom(double _bottom) 	{ height  = _bottom - y; }

}
