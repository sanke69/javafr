package fr.java.draw.styles;

import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Pen;

public class LineStyle {
	double   width; // thickness
	double[] dashPattern;
	Brush    brush;

	public static LineStyle of(double _width, Brush _brush, double... _dashPattern) {
		LineStyle style = new LineStyle();
		style.width = _width;
		style.brush = _brush;
		
		if(_dashPattern != null)
			style.dashPattern = _dashPattern.clone();
		
		return style;
	}
	
	
	
	public LineStyle() {
		super();
		width = 1d;
	}
	
	public double 		getWidth() {
		return width;
	}

	public boolean 		hasDashPattern() {
		return dashPattern != null;
	}
	public double[] 	getDashPattern() {
		return dashPattern;
	}

	public Pen 			getPen() {
		return brush;
	}
	public Brush 		getBrush() {
		return brush;
	}
	
}
