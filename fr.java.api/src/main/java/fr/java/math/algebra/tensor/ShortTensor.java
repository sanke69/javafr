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
 * @file     ShortTensor.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.algebra.tensor;

import java.nio.ShortBuffer;

import fr.java.lang.enums.Primitive;
import fr.java.math.algebra.NumberTensor;

public interface ShortTensor extends NumberTensor {

	@Override
	public default Primitive 	getPrimitive() {
		return Primitive.SHORT;
	}

	@Override
	public short[]				getArray();
	@Override
	public ShortBuffer			getBuffer();

	public short				getValue(final long _index);
	public short				getValue(final int... _coords);

	@Override
	public ShortTensor			getSliceView(final int... _slice);
	@Override
	public ShortTensor  		getSliceCopy(final int... _slice);

	public void     			setValue(final short _value, final long _index);
	public void					setValue(final short _value, final int... _coords);

	public void					setSlice(final ShortTensor _tensor, final int... _slice);

}
