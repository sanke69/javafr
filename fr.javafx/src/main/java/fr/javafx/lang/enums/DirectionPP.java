package fr.javafx.lang.enums;

import javafx.geometry.NodeOrientation;

/**
 * Specifies the direction of traversal.
 */
@Deprecated
public enum DirectionPP {

    UP(false),
    DOWN(true),
    LEFT(false),
    RIGHT(true),
    NEXT(true),
    NEXT_IN_LINE(true), // Like NEXT, but does not traverse into the current parent
    PREVIOUS(false);
    private final boolean forward;

    DirectionPP(boolean forward) {
        this.forward = forward;
    }

    public boolean isForward() {
        return forward;
    }

    /**
     * Returns the direction with respect to the node's orientation. It affect's only arrow keys however, so it's not
     * an error to ignore this call if handling only next/previous traversal.
     * @param orientation
     * @return
     */
    public DirectionPP getDirectionForNodeOrientation(NodeOrientation orientation) {
        if (orientation == NodeOrientation.RIGHT_TO_LEFT) {
            switch (this) {
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
            }
        }
        return this;
    }
    /*
    public com.sun.javafx.scene.traversal.Direction asSun() {
    	switch(this) {
		case DOWN: 			return com.sun.javafx.scene.traversal.Direction.DOWN;
		case LEFT: 			return com.sun.javafx.scene.traversal.Direction.LEFT;
		case NEXT: 			return com.sun.javafx.scene.traversal.Direction.NEXT;
		case NEXT_IN_LINE: 	return com.sun.javafx.scene.traversal.Direction.NEXT_IN_LINE;
		case PREVIOUS: 		return com.sun.javafx.scene.traversal.Direction.PREVIOUS;
		case RIGHT: 		return com.sun.javafx.scene.traversal.Direction.RIGHT;
		case UP: 			return com.sun.javafx.scene.traversal.Direction.UP;
		default:			throw new IllegalArgumentException();
    	}
    }
    */
    
}
