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
 * @file     Vector.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.tensor;

import java.text.NumberFormat;

public interface Vector extends Tensor {

	public static interface Editable extends Vector {

		public void 			set(final Vector _vector);

	}

	// TENSOR METHODS
	@Override
	public default Nature 		getNature() 					{ return Nature.VECTOR; }
	@Override
	public default Nature 		getNature(boolean _simplified) {
		if(!_simplified)
			return getNature();
		return getCapacity() == 1 ? Nature.SCALAR : Nature.VECTOR;
	}

	@Override
	public default int[] 		getShape() 						{ return new int[] { getCapacity() }; }

	@Override
	public default int	 		getSliceCapacity(int _depth) 	{ return getCapacity(); }
	@Override
	public default int[] 		getSliceShape(int... _slice) 	{ return new int[] { getCapacity() }; } 
	@Override
	public default int 			getSliceOffset(int... _slice) 	{ return 0; }

	public boolean 				isValid();

	public boolean 				isEqual(final Vector _vector);
	public boolean 				isDifferent(final Vector _vector);

	@Override
	public Vector				clone();

	public String 				toString(final NumberFormat _nf);

}
