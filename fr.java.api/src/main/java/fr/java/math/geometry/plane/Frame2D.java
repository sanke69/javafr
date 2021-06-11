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

import fr.java.math.algebra.matrix.generic.Matrix44D;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.Frame;

public interface Frame2D extends Frame<Point2D, Vector2D> {

	public Point2D	getOrigin();
	public Vector2D	getXAxis();
	public Vector2D	getYAxis();

	public Matrix44D getModelMatrix();

	public void moveTo					(double _px, double _py);
	public void moveTo					(double _px, double _py, Frame2D _frame);
	public void moveTo					(Point2D _p);
	public void moveTo					(Point2D _p, Frame2D _frame);

	public void translate				(double _tx, double _ty);
	public void translate				(double _tx, double _ty, Frame2D _frame);
	public void translate				(Vector2D _d);
	public void translate				(Vector2D _d, Frame2D _frame);

	public void rotateOrigin			(double _a);
	public void rotateOrigin			(double _a, Frame2D _frame);
	public void rotateAxes				(double _a);
	public void rotateAxes				(double _a, Frame2D _frame);
	public void rotateOriginAndAxes		(double _a);
	public void rotateOriginAndAxes		(double _a, Frame2D _frame);

}
