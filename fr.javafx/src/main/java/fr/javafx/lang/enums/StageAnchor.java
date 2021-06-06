package fr.javafx.lang.enums;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import fr.javafx.io.screen.Screens;
import fr.javafx.stage.StageExt;

public enum StageAnchor {
	SCREEN_CENTER,
	SCREEN_TOP,
	SCREEN_BOTTOM,
	SCREEN_LEFT,
	SCREEN_RIGHT,
	SCREEN_BOTTOM_LEFT,
	SCREEN_BOTTOM_RIGHT,
	SCREEN_TOP_LEFT, 
	SCREEN_TOP_RIGHT;

	public static void setPosition(StageExt _stage, StageAnchor _anchor) {
		setPosition(_stage, _anchor, null);
	}
	public static void setPosition(StageExt _stage, StageAnchor _anchor, Screen _screen) {
		double w = _stage.getWidth();
		double h = _stage.getHeight();
		
        Rectangle2D screenBounds = (_screen == null ? Screens.getActiveScreen() : _screen).getVisualBounds();
        double x, y;

		switch(_anchor) {
		case SCREEN_CENTER:
	        x = screenBounds.getMinX() + (screenBounds.getWidth() - w) / 2.0;
	        y = screenBounds.getMinY() + (screenBounds.getHeight() - h) / 2.0;
	        _stage.setTopLeft(y, x);
			break;
		case SCREEN_TOP:
	        x = screenBounds.getMinX() + (screenBounds.getWidth() - w) / 2.0;
	        y = 0;
	        _stage.setTopLeft(y, x);
			break;
		case SCREEN_LEFT:
	        x = 0;
	        y = screenBounds.getMinY() + (screenBounds.getHeight() - h) / 2.0;
	        _stage.setTopLeft(y, x);
			break;
		case SCREEN_RIGHT:
	        x = screenBounds.getMinX() + screenBounds.getWidth();
	        y = screenBounds.getMinY() + (screenBounds.getHeight() - h) / 2.0;
	        _stage.setTopRight(y, x);
			break;
		case SCREEN_BOTTOM:
	        x = screenBounds.getMinX() + (screenBounds.getWidth() - w) / 2.0;
	        y = screenBounds.getMinY() + screenBounds.getHeight();
	        _stage.setBottomLeft(y, x);
			break;
		case SCREEN_BOTTOM_LEFT:
	        x = 0;
	        y = screenBounds.getMinY() + screenBounds.getHeight();
	        _stage.setBottomLeft(y, x);
			break;
		case SCREEN_BOTTOM_RIGHT:
	        x = screenBounds.getMinX() + screenBounds.getWidth();
	        y = screenBounds.getMinY() + screenBounds.getHeight();
	        _stage.setBottomRight(y, x);
			break;
		case SCREEN_TOP_LEFT:
	        x = 0;
	        y = 0;
	        _stage.setTopLeft(y, x);
	        break;
		case SCREEN_TOP_RIGHT:
	        x = screenBounds.getMinX() + screenBounds.getWidth();
	        y = 0;
	        _stage.setTopRight(y, x);
			break;
		default:
			break;
		
		}
	}

}
