package fr.java.sdk.draw.tools;

import fr.java.draw.Drawer;
import fr.java.draw.tools.Brush;

public class DrawUtils {

	public static void drawArrowH(Drawer _drawer, double _x0, double _y0, double _x1, double _y1, Brush _brush) {
		_drawer.drawLine(_x0, _y0, _x1,		_y1, 	 _brush);
		_drawer.drawLine(_x1, _y0, _x1 - 5,	_y1 + 5, _brush);
		_drawer.drawLine(_x1, _y0, _x1 - 5,	_y1 - 5, _brush);
	}
	public static void drawArrowV(Drawer _drawer, double _x0, double _y0, double _x1, double _y1, Brush _brush) {
		_drawer.drawLine(_x0, _y0, _x1, 	_y1, 	 _brush);
		_drawer.drawLine(_x0, _y0, _x1 + 5,	_y1 + 5, _brush);
		_drawer.drawLine(_x0, _y0, _x1 - 5,	_y1 + 5, _brush);
		
	}
/*
	public static Dimension.TwoDims getDimension(String s, Font _font) {
		javafx.scene.text.Font font = null;
		if(_font.getName().isPresent())
			font = new javafx.scene.text.Font(_font.getName().get(), _font.getSize());
		else
			font = new javafx.scene.text.Font(_font.getSize());

	    Text text = new Text(s);
	    text.setFont(font);
	    Bounds tb = text.getBoundsInLocal();
	    Rectangle stencil = new Rectangle(
	        tb.getMinX(), tb.getMinY(), tb.getWidth(), tb.getHeight()
	    );

	    Shape intersection = Shape.intersect(text, stencil);

	    Bounds ib = intersection.getBoundsInLocal();
	    
	    return Dimensions.of(ib.getWidth(), ib.getHeight());
	}
	*/
	public static String getFormattedValue(double _d) {
		return String.format("%1.2f", _d);
	}
	
}
