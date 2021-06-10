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
package fr.java.math.geometry.plane;

import fr.java.math.geometry.Ray;

public interface Ray2D extends Ray {
	
	public interface Editable extends Ray2D {

		public void set(Ray2D _ray);
		public void setOrigin(Point2D _pt);
		public void setDirection(Vector2D _dir);
		
	}

	public Point2D  getOrigin();
	public Vector2D getDirection();

	public Point2D  get(double _t);

}
