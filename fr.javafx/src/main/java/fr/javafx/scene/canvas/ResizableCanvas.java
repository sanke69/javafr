package fr.javafx.scene.canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Region;

public class ResizableCanvas extends Canvas {
	public DoubleProperty maxWidth, maxHeight;

    public ResizableCanvas() {
        this(0, 0);
    }
    public ResizableCanvas(double _maxWidth, double _maxHeight) {
    	super(_maxWidth, _maxHeight);

    	maxWidth  = new SimpleDoubleProperty(_maxWidth);
    	maxHeight = new SimpleDoubleProperty(_maxHeight);
    }
    public ResizableCanvas(Region _region) {
    	this(_region.getWidth(), _region.getHeight());

    	maxWidth  . bind(_region.widthProperty());
    	maxHeight . bind(_region.heightProperty());
    }

//    @Override public boolean 				isResizable() 				{ return true; }

	@Override public double 				minWidth(double _height) 	{ return 0; }
	@Override public double 				prefWidth(double _height) 	{ return minWidth(_height); }
	@Override public double 				maxWidth(double _height) 	{ return maxWidth.get(); }

	@Override public double 				minHeight(double _width) 	{ return 0; }
	@Override public double 				prefHeight(double _width) 	{ return minHeight(_width); }
	@Override public double 				maxHeight(double _width) 	{ return maxHeight.get(); }

	@Override
	public void 							resize(double _width, double _height) {
		if(maxWidth.isBound() || maxHeight.isBound())
			return ;

		if(isResizable()) {
			super.setWidth(_width);
			super.setHeight(_height);

			maxWidth  . set(_width);
			maxHeight . set(_height);
		}
	}

}
