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
 * @file     Ray3D.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry.space;

import fr.java.math.geometry.Ray;

public interface Ray3D extends Ray {
	
	public interface Editable extends Ray3D {

		public void set(Ray3D _ray);
		public void setOrigin(Point3D _pt);
		public void setDirection(Vector3D _dir);
		
	}

	public Point3D  getOrigin();
	public Vector3D getDirection();

	public Point3D  get(double _t);

}
