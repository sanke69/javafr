package fr.javafx.io.mouse;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

@Deprecated
public class MouseStatus {
	private DoubleProperty	xScreen	= new SimpleDoubleProperty(0.0);
	private DoubleProperty	yScreen	= new SimpleDoubleProperty(0.0);
	
	private DoubleProperty	xScene	= new SimpleDoubleProperty(0.0);
	private DoubleProperty	yScene	= new SimpleDoubleProperty(0.0);

	private DoubleProperty	xNode	= new SimpleDoubleProperty(0.0);
	private DoubleProperty	yNode	= new SimpleDoubleProperty(0.0);
	
	public MouseStatus() {
		super();
	}

	public void setScreenX(double _x) {
		xScreen.set(_x);
	}
	public double getScreenX() { return xScreen.get(); }
	public DoubleProperty xScreenProperty() { return xScreen; }

	public void setScreenY(double _y) {
		yScreen.set(_y);
	}
	public double getScreenY() { return yScreen.get(); }
	public DoubleProperty yScreenProperty() { return yScreen; }

	public void setSceneX(double _x) {
		xScene.set(_x);
	}
	public double getSceneX() { return xScene.get(); }
	public DoubleProperty xSceneProperty() { return xScene; }

	public void setSceneY(double _y) {
		yScene.set(_y);
	}
	public double getSceneY() { return yScene.get(); }
	public DoubleProperty ySceneProperty() { return yScene; }


	public void setX(double _x) { xNode.set(_x); }
	public double getX() { return xNode.get(); }
	public DoubleProperty xProperty() { return xNode; }

	public void setY(double _y) { yNode.set(_y); }
	public double getY() { return yNode.get(); }
	public DoubleProperty yProperty() { return yNode; }

	public String toString() { return "x= (" + xScreen.get() + "|" + xScene.get() + "|" + xNode.get() + "), y= (" + yScreen.get() + "|" + yScene.get() + "|" + yNode.get() + ")"; }

}
