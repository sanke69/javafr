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

/**
 * 
 * Convert a Coordinate in a planar system to an associated point on a particular tile
 * 
 * @author sanke
 *
 * @param <M_Point>
 * @param <T_Point>
 */
// TODO:: Must extends Projector...
public interface TileProjector<M_Point extends Coordinate.TwoDims, T_Point extends TileCoordinate> {

	public T_Point  toTile(M_Point _map, int _level);
	public M_Point	fromTile(T_Point _tile);

}
