package fr.java.maths.window;

import fr.java.ui.Edges2D;

public class SimpleEdges2D implements Edges2D.Editable {
	double left, right, top, bottom;

	public SimpleEdges2D() {
		super();
		left = right = top = bottom = 0.0;
	}
	public SimpleEdges2D(double _left, double _right, double _top, double _bottom) {
		super();
		left   = _left;
		right  = _right;
		top    = _top;
		bottom = _bottom;
	}

	@Override
	public void set(Edges2D _edges) {
		left   = _edges.getLeft();
		right  = _edges.getRight();
		top    = _edges.getTop();
		bottom = _edges.getBottom();
	}

	@Override public void 	setLeft(double _left) 		{ left = _left; }
	@Override public double getLeft() 					{ return left; }
	@Override public void 	setRight(double _right) 	{ right = _right; }
	@Override public double getRight() 					{ return right; }
	@Override public void 	setTop(double _top) 		{ top = _top; }
	@Override public double getTop() 					{ return top; }
	@Override public void 	setBottom(double _bottom) 	{ bottom = _bottom; }
	@Override public double getBottom() 				{ return bottom; }

	@Override public String toString() 					{ return "[" + "L= " + left + ", R= " + right + "T= " + top + ", B= " + bottom + "]"; }

}
