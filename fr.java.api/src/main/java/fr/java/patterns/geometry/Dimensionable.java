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

import fr.java.math.geometry.Dimension;

public interface Dimensionable {

	public interface TwoDims extends Dimensionable {

		public double getWidth();
		public double getHeight();

	}

	public interface ThreeDims extends Dimensionable.TwoDims {

		public double getDepth();

	}

	public static Dimensionable.TwoDims of(final Dimension.TwoDims _bbox) {
		return new Dimensionable.TwoDims() {

			@Override public double getWidth()  { return _bbox.getWidth(); }
			@Override public double getHeight() { return _bbox.getHeight(); }

		};
	}

	public static Dimensionable.ThreeDims of(final Dimension.ThreeDims _bbox) {
		return new Dimensionable.ThreeDims() {

			@Override public double getWidth()  { return _bbox.getWidth(); }
			@Override public double getHeight() { return _bbox.getHeight(); }
			@Override public double getDepth()  { return _bbox.getDepth(); }

		};
	}

}
