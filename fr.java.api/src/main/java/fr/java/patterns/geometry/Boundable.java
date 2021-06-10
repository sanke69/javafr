/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
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
