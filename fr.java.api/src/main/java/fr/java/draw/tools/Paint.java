package fr.java.draw.tools;

public interface Paint {
	public static final Paint TRANSPARENT = null;

	public static Paint of(final Color _color) {
		return new Paint() {
			@Override public final Color toColor() 	{ return _color; }
		};
	}

	public Color toColor();

}
