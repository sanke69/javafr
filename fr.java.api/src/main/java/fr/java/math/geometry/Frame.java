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
package fr.java.math.geometry;

import java.io.Serializable;

import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberVector;

public interface Frame<PT extends Point, VEC extends NumberVector> extends Cloneable, Comparable<Object>/*, Comparable<Frame<PT, VEC>>*/, Serializable {

	public Frame<PT, VEC> 	getParentFrame();

	public NumberMatrix 	getModelMatrix();

	public PT 				getOrigin();

//	public void 			moveTo		(PT _position);
//	public void 			translate	(VEC _direction);
	
//	public void rotate		(VEC _vect_angle);

//	public void moveTo		(PT _position, Locate<PT, VEC> _in_locate);
//	public void translate	(VEC _vect_speed, Locate<PT, VEC> _in_locate);
//	public void rotate		(VEC _vect_angle, Locate<PT, VEC> _in_locate);

//	public PT 	transformPoint(PT _point);
//	public VEC 	transformVector(VEC _vector);

}
