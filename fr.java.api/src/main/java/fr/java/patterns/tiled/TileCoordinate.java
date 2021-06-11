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
package fr.java.patterns.tiled;

import fr.java.math.topology.Coordinate;

public interface TileCoordinate extends Coordinate.Cartesian2D {

	public static interface Editable extends TileCoordinate, Coordinate.Cartesian2D.Editable {

		public void setLevel(int _level);

		public void setI(int _i);
		public void setJ(int _j);

	}

	public int getLevel();

	public int getI();
	public int getJ();

}
