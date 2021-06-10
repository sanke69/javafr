package fr.java.maths.adapters;

import fr.java.math.geometry.BoundingBox;

public class EditableBoundingBoxAdapter3D extends BoundingBoxAdapter3D implements BoundingBox.ThreeDims.Editable {
	private static final long serialVersionUID = 1L;

	public EditableBoundingBoxAdapter3D() {
		super();
	}
	public EditableBoundingBoxAdapter3D(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
		super(_x, _y, _z, _width, _height, _depth);
	}

	@Override public double 						getX() 						{ return x; }
	@Override public double 						getY() 						{ return y; }
	@Override public double 						getZ() 						{ return z; }

	@Override public double 						getWidth() 					{ return width; }
	@Override public double 						getHeight() 				{ return height; }
	@Override public double 						getDepth() 					{ return depth; }

	@Override public void 							set(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
		x = _x; y = _y; z = _z; width = _width; height = _height; depth = _depth;
	}

	@Override public void 							setX(double _x) 			{ x = _x; }
	@Override public void 							setMinX(double _x) 			{ setX(_x); }
	@Override public void 							setMaxX(double _x) 			{ x = _x - width; }
	@Override public void 							setCenterX(double _x) 		{ x = _x - width / 2d; }
	@Override public void 							setWidth(double _width) 		{ width = _width; }

	@Override public void 							setLeft(double _left) 		{ width += x - _left; x = _left; }
	@Override public void 							setRight(double _right) 	{ width  = _right - x; }

	@Override public void 							setY(double _y) 			{ setMinY(_y); }
	@Override public void 							setMinY(double _y) 			{ y = _y; }
	@Override public void 							setMaxY(double _y) 			{ y = _y - height; }
	@Override public void 							setCenterY(double _y) 		{ y = _y - height / 2d; }
	@Override public void 							setHeight(double _height) 	{ height = _height; }

	@Override public void 							setTop(double _top) 		{ height += y - _top; y = _top; }
	@Override public void 							setBottom(double _bottom) 	{ height  = _bottom - y; }

	@Override public void 							setZ(double _z) 			{ setMinZ(_z); }
	@Override public void 							setMinZ(double _z) 			{ z = _z; }
	@Override public void 							setMaxZ(double _z) 			{ z = _z - depth; }
	@Override public void 							setCenterZ(double _z) 		{ z = _z - depth / 2d; }
	@Override public void 							setDepth(double _depth) 	{ depth = _depth; }

	@Override public void 							setFront(double _front) 	{ depth += z - _front; z = _front; }
	@Override public void 							setBack(double _back) 		{ depth  = _back - z; }

}
