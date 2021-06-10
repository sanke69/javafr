package fr.java.maths.window;

import fr.java.math.geometry.Viewport;
import fr.java.math.window.Edges2D;

public class AutoCenterEdges2D implements Edges2D {
	Viewport.TwoDims<?, ?> viewport;

	public AutoCenterEdges2D(Viewport.TwoDims<?, ?> _viewport) {
		viewport = _viewport;
	}

	@Override public double getLeft()   {
		double mw = viewport.getModelBounds().getWidth();
		double ww = viewport.getWindow().getWidth();
		double vs = viewport.getViewScale();

		return ( mw * vs <= ww ) ? mw * vs / 2.f : ww / 2.f;
	}
	@Override public double getRight()  { return getLeft(); }
	@Override public double getTop()    {
		if(viewport.getModelBounds() == null)
			return 0;

		double mh = viewport.getModelBounds().getHeight();
		double wh = viewport.getWindow().getHeight();
		double vs = viewport.getViewScale();

		return ( mh * vs <= wh ) ? mh * vs / 2.f : wh / 2.f;
	}
	@Override public double getBottom() { return getTop(); }

}
