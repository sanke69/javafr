package fr.javafx.io.mouse;

import javafx.scene.input.MouseEvent;

public class DragContext {
	public double x, dx, x_anchor;
	public double y, dy, y_anchor;

	public void setAnchor(double _x, double _y) {
        x_anchor = _x;
        y_anchor = _y;
	}
	public void translateAnchor(double _x_offset, double _y_offset) {
        x_anchor += _x_offset;
        y_anchor += _y_offset;
	}

	public void setMouse(MouseEvent _me) {
		setMouse(_me, false);
	}
	public void setMouse(MouseEvent _me, boolean _useScreenCoordinate) {
		if(_useScreenCoordinate) {
	        x = _me.getScreenX();
	        y = _me.getScreenY();
		} else {
	        x = _me.getSceneX();
	        y = _me.getSceneY();
		}
	}
	public void setMouse(double _x, double _y) {
        x = _x;
        y = _y;
	}

	public void deltaMouse(MouseEvent _me) {
        x = _me.getSceneX();
        y = _me.getSceneY();
	}
	public void deltaMouse(MouseEvent _me, boolean _useScreenCoordinate) {
		if(_useScreenCoordinate) {
	        dx = _me.getScreenX() - x;
	        dy = _me.getScreenY() - y;
		} else {
	        dx = _me.getSceneX()  - x;
	        dy = _me.getSceneY()  - y;
		}
	}
	public void deltaMouse(double _x, double _y) {
        dx = x - _x;
        dy = y - _y;
	}

	public double anchorX() {
		return x_anchor;
	}
	public double anchorY() {
		return y_anchor;
	}

	public double mouseX() {
		return x;
	}
	public double mouseY() {
		return y;
	}

	public double deltaMouseX() {
		return dx;
	}
	public double deltaMouseY() {
		return dy;
	}

}