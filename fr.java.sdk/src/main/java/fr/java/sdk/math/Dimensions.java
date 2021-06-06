package fr.java.sdk.math;

import java.text.NumberFormat;

import fr.java.math.geometry.Dimension;

public class Dimensions {

	public static Dimension.OneDim 				of(final double _width) {
    	return new Dimension.OneDim() {
			private static final long serialVersionUID = 1L;

			@Override public double 						getWidth() 								{ return _width; }

    		@Override public Dimension.OneDim.Editable 		clone() 								{ return ofEditable(getWidth()); }

    		@Override public String 						toString() 								{ return "[" + getWidth() + "]"; }
    		@Override public String 						toString(NumberFormat _nf) 				{ return "[" + _nf.format( getWidth() ) + "]"; }
    	};
	}
	public static Dimension.OneDim.Editable 	ofEditable(final double _width) {
    	return new Dimension.OneDim.Editable() {
			private static final long serialVersionUID = 1L;
			double width = _width;

			@Override public void 							set(double _w) 							{ width = _w; }

			@Override public void 							setWidth(double _w) 					{ width = _w; }
			@Override public double 						getWidth() 								{ return width; }

    		@Override public Dimension.OneDim.Editable 		clone() 								{ return ofEditable(getWidth()); }

    		@Override public String 						toString() 								{ return "[" + getWidth() + "]"; }
    		@Override public String 						toString(NumberFormat _nf) 				{ return "[" + _nf.format( getWidth() ) + "]"; }
    	};
	}

	public static Dimension.TwoDims 			of(final double _width, final double _height) {
    	return new Dimension.TwoDims() {
			private static final long serialVersionUID = 1L;

			@Override public double 						getWidth() 								{ return _width; }
			@Override public double 						getHeight() 							{ return _height; }

    		@Override public Dimension.TwoDims.Editable 	clone() 								{ return ofEditable(getWidth(), getHeight()); }

    		@Override public String 						toString() 								{ return "[" + getWidth() + "x" + getHeight() + "]"; }
    		@Override public String 						toString(NumberFormat _nf) 				{ return "[" + _nf.format( getWidth() ) + "x" + _nf.format( getHeight() ) + "]"; }
    	};
	}
	public static Dimension.TwoDims.Editable 	ofEditable(final double _width, final double _height) {
    	return new Dimension.TwoDims.Editable() {
			private static final long serialVersionUID = 1L;
			double width = _width, height = _height;

			@Override public void 							set(double _w, double _h) 				{ width = _w; height = _h; }

			@Override public void 							setWidth(double _w) 					{ width = _w; }
			@Override public double 						getWidth() 								{ return width; }
			@Override public void 							setHeight(double _h) 					{ height = _h; }
			@Override public double 						getHeight() 							{ return height; }

    		@Override public Dimension.TwoDims.Editable 	clone() 								{ return ofEditable(getWidth(), getHeight()); }

    		@Override public String 						toString() 								{ return "[" + getWidth() + "x" + getHeight() + "]"; }
    		@Override public String 						toString(NumberFormat _nf) 				{ return "[" + _nf.format( getWidth() ) + "x" + _nf.format( getHeight() ) + "]"; }
    	};
	}

	public static Dimension.ThreeDims 			of(final double _width, final double _height, final double _depth) {
    	return new Dimension.ThreeDims() {
			private static final long serialVersionUID = -7666648297833413344L;

			@Override public double 						getWidth() 								{ return _width; }
			@Override public double 						getHeight() 							{ return _height; }
			@Override public double 						getDepth() 								{ return _depth; }

    		@Override public Dimension.ThreeDims.Editable 	clone() 								{ return ofEditable(getWidth(), getHeight(), getDepth()); }

    		@Override public String 						toString() 								{ return "[" + getWidth() + "x" + getHeight() + "x" + getDepth() + "]"; }
    		@Override public String 						toString(NumberFormat _nf) 				{ return "[" + _nf.format( getWidth() ) + "x" + _nf.format( getHeight() )  + "x" + _nf.format( getDepth() ) + "]"; }
    	};
	}
	public static Dimension.ThreeDims.Editable 	ofEditable(final double _width, final double _height, final double _depth) {
    	return new Dimension.ThreeDims.Editable() {
			private static final long serialVersionUID = 1L;
			double width = _width, height = _height, depth = _depth;

			@Override public void 							set(double _w, double _h, double _d) 	{ width = _w; height = _h; depth = _d; }

			@Override public void 							setWidth(double _w) 					{ width = _w; }
			@Override public double 						getWidth() 								{ return width; }
			@Override public void 							setHeight(double _h) 					{ height = _h; }
			@Override public double 						getHeight() 							{ return height; }
			@Override public void 							setDepth(double _d) 					{ depth = _d; }
			@Override public double 						getDepth() 								{ return depth; }

    		@Override public Dimension.ThreeDims.Editable 	clone() 								{ return ofEditable(getWidth(), getHeight(), getDepth()); }

    		@Override public String 						toString() 								{ return "[" + getWidth() + "x" + getHeight() + "x" + getDepth() + "]"; }
    		@Override public String 						toString(NumberFormat _nf) 				{ return "[" + _nf.format( getWidth() ) + "x" + _nf.format( getHeight() )  + "x" + _nf.format( getDepth() ) + "]"; }
    	};
	}

	public static final Dimension.TwoDims		scaled(Dimension.TwoDims _bbox, double _scale) {
		return of(_scale * _bbox.getWidth(), _scale * _bbox.getHeight());
	}
	public static final Dimension.TwoDims		scaled(Dimension.TwoDims _bbox, double _scalex, double _scaley) {
		return of(_scalex * _bbox.getWidth(), _scaley * _bbox.getHeight());
	}

}