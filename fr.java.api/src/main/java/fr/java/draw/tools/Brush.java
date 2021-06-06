package fr.java.draw.tools;

public interface Brush extends Pen, Paint {

	public static Brush of(final Color _color) {
		return new Brush() {
			@Override public final Color   toColor()    { return _color; }

			@Override public final double 	getWidth() 	 { return 0d; }
			@Override public final Paint  	getPaint() 	 { return null; }
			@Override public final Paint 	getPaintIn() { return Paint.of(_color); }

		};
	}
	public static Brush of(final Paint _paint) {
		return new Brush() {
			@Override public final Color   toColor()    { return _paint.toColor(); }

			@Override public final double 	getWidth() 	 { return 0d; }
			@Override public final Paint  	getPaint() 	 { return null; }
			@Override public final Paint 	getPaintIn() { return _paint; }

		};
	}
	public static Brush of(final double _width, final Color _color) {
		return new Brush() {
			@Override public final Color   toColor()    { return _color; }

			@Override public final double 	getWidth() 	 { return _width; }
			@Override public final Paint  	getPaint() 	 { return Paint.of(_color); }
			@Override public final Paint 	getPaintIn() { return null; }

		};
	}
	public static Brush of(final double _width, final Paint _paint) {
		return new Brush() {
			@Override public final Color   toColor()    { return _paint.toColor(); }

			@Override public final double 	getWidth() 	 { return _width; }
			@Override public final Paint  	getPaint() 	 { return _paint; }
			@Override public final Paint 	getPaintIn() { return null; }

		};
	}
	public static Brush of(final double _width, final Color _out, final Color _in) {
		return new Brush() {
			@Override public final Color   toColor()    { return _out; }

			@Override public final double 	getWidth() 	 { return _width; }
			@Override public final Paint  	getPaint() 	 { return Paint.of(_out); }
			@Override public final Paint 	getPaintIn() { return Paint.of(_in); }

		};
	}
	public static Brush of(final double _width, final Paint _out, final Paint _in) {
		return new Brush() {
			@Override public final Color   toColor()    { return _out.toColor(); }

			@Override public final double 	getWidth() 	 { return _width; }
			@Override public final Paint  	getPaint() 	 { return _out; }
			@Override public final Paint 	getPaintIn() { return _in; }

		};
	}

	@Override
	default Color toColor() { return Pen.super.toColor(); }

	public Paint getPaintIn();
	

}
