package fr.java.draw.tools;

public interface Pen {

	public static Pen of(final double _width, final Paint _paint) {
		return new Pen() {
			@Override public final double getWidth() 	{ return _width; }
			@Override public final Paint  getPaint() 	{ return _paint; }
		};
	}

	public default Color    toColor()    { return getPaint().toColor(); }

	public double 			getWidth();
	public Paint 			getPaint();

}
