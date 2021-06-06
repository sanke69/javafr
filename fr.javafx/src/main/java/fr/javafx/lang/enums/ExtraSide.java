package fr.javafx.lang.enums;

import javafx.scene.Cursor;

// cf. Side
public enum ExtraSide {
    NONE			(false, false, false, false),
    TOP				(true,  false, false, false),
    LEFT			(false, false, true,  false),
    BOTTOM			(false, true,  false, false),
    RIGHT			(false, false, false, true),
    TOP_LEFT		(true,  false, true,  false),
    TOP_RIGHT		(true,  false, false, true),
    BOTTOM_LEFT		(false, true,  true,  false),
    BOTTOM_RIGHT	(false, true,  false, true);

    private boolean resizeTop;
    private boolean resizeLeft;
    private boolean resizeBottom;
    private boolean resizeRight;

    public static ExtraSide of(boolean _top, boolean _bottom, boolean _left, boolean _right) {
        if (_left && !_top && !_bottom) {
            return ExtraSide.LEFT;
        } else if (_left && _top && !_bottom) {
        	return ExtraSide.TOP_LEFT;
        } else if (_left && !_top && _bottom) {
        	return ExtraSide.BOTTOM_LEFT;
        } else if (_right && !_top && !_bottom) {
        	return ExtraSide.RIGHT;
        } else if (_right && _top && !_bottom) {
            return ExtraSide.TOP_RIGHT;
        } else if (_right && !_top && _bottom) {
        	return ExtraSide.BOTTOM_RIGHT;
        } else if (_top && !_left && !_right) {
            return ExtraSide.TOP;
        } else if (_bottom && !_left && !_right) {
            return ExtraSide.BOTTOM;
        } else {
            return ExtraSide.NONE;
        }
    }
    
    private ExtraSide(boolean _top, boolean _bottom, boolean _left, boolean _right) {
    	resizeTop    = _top;
    	resizeLeft   = _left;
    	resizeBottom = _bottom;
    	resizeRight  = _right;
    }

    public boolean isTrue() {
    	return resizeTop || resizeBottom || resizeLeft || resizeRight;
    }
    public boolean isFalse() {
    	return !isTrue();
    }

    public boolean onTop() {
    	return resizeTop;
    }
    public boolean onBottom() {
    	return resizeBottom;
    }
    public boolean onLeft() {
    	return resizeLeft;
    }
    public boolean onRight() {
    	return resizeRight;
    }
    
    public Cursor getCursor() {
    	switch(this) {
		case TOP:			return Cursor.N_RESIZE;
		case TOP_LEFT:		return Cursor.NW_RESIZE;
		case TOP_RIGHT:		return Cursor.NE_RESIZE;
		case BOTTOM:		return Cursor.S_RESIZE;
		case BOTTOM_LEFT:	return Cursor.SW_RESIZE;
		case BOTTOM_RIGHT:	return Cursor.SE_RESIZE;
		case LEFT:			return Cursor.W_RESIZE;
		case RIGHT:			return Cursor.E_RESIZE;
		case NONE:			return Cursor.DEFAULT;
		default:			return Cursor.OPEN_HAND;
    	}
    }
    
}
