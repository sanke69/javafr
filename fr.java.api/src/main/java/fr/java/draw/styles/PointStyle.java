package fr.java.draw.styles;

import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.draw.tools.Pen;

public class PointStyle {
	double    size;
	PointSkin skin;
	Brush     brush;
	
	public PointStyle() {
		super();
		size  = 1d;
		skin  = PointSkin.Dot;
		brush = Brush.of(Colors.BLACK);
	}

	public double 		getSize() {
		return size;
	}
	public PointSkin 	getSkin() {
		return skin;
	}

	public Pen 			getPen() {
		return brush;
	}
	public Brush 		getBrush() {
		return brush;
	}

	
}
