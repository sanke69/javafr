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
package fr.java.ui;

public interface Edges2D {

	public static interface Editable extends Edges2D {

		public void set(Edges2D _edges);

		public void setLeft(double _left);
		public void setRight(double _right);
		public void setTop(double _top);
		public void setBottom(double _bottom);

	}

	public default double getHorizontal() { return getLeft() + getRight(); }
	public default double getVertical()   { return getTop()  + getBottom(); }

	public double getLeft();
	public double getRight();
	public double getTop();
	public double getBottom();

}
