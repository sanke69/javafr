package fr.java.patterns.geometry;

import fr.java.math.geometry.BoundingBox;

public interface Boundable extends BoundingBox {

	public static enum Anchor {
		CENTER,
		NORTH, SOUTH, EAST, WEST,
		NORTH_WEST, NORTH_EAST,
		SOUTH_WEST, SOUTH_EAST,
	}

	public interface OneDim extends Boundable, BoundingBox.OneDim {

		public default double 		getX(Anchor _anchor) {
			switch(_anchor) {
			default:			
			case CENTER:		return getCenterX();

			case NORTH:
			case WEST:
			case NORTH_WEST:
			case SOUTH_WEST:	return getMinX();

			case SOUTH:
			case EAST:
			case NORTH_EAST:
			case SOUTH_EAST:	return getMaxX();
			}
		}

	}

	public interface TwoDims extends Boundable.OneDim, BoundingBox.TwoDims {

		@Override
		public default double 		getX(Anchor _anchor) {
			switch(_anchor) {
			default:	
			case NORTH:
			case SOUTH:
			case CENTER:		return getCenterX();

			case EAST:
			case NORTH_EAST:
			case SOUTH_EAST:	return getMaxX();

			case WEST:
			case NORTH_WEST:
			case SOUTH_WEST:	return getMinX();
			}
		}

		public default double 		getY(Anchor _anchor) {
			switch(_anchor) {
			default:	
			case EAST:
			case WEST:
			case CENTER:		return getCenterY();

			case NORTH:
			case NORTH_WEST:
			case NORTH_EAST:	return getMinY();

			case SOUTH:
			case SOUTH_WEST:
			case SOUTH_EAST:	return getMaxY();
			}
		}

	}

	public interface ThreeDims extends Boundable.TwoDims, BoundingBox.ThreeDims {

		public default double 		getZ(Anchor _anchor) {
			switch(_anchor) {
			default:	
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
			case CENTER:
			case NORTH_EAST:
			case NORTH_WEST:
			case SOUTH_EAST:
			case SOUTH_WEST:	return getMaxZ();
			}
		}

	}

}
