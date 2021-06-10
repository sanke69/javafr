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
package fr.java.math.algebra.tensor;

import java.nio.ByteBuffer;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotSupportedException;
import fr.java.math.algebra.NumberTensor;

public interface BitTensor extends NumberTensor {

	public interface Editable extends BitTensor {

		public void     			setValue(final boolean _value, final int _index);
		public void     			setValue(final boolean _value, final int... _coords);

		public void					setSlice(final BitTensor _tensor, final int... _slice);

	}

	@Override
	public default Primitive 	getPrimitive() {
		return Primitive.BOOLEAN;
	}

	@Override
	public byte[]				getArray()		throws NotSupportedException;;
	@Override
	public ByteBuffer			getBuffer()		throws NotSupportedException;;

	public boolean     			isTrue(final int _index);
	public boolean     			isTrue(final int... _coords);

	public boolean     			isFalse(final int _index);
	public boolean     			isFalse(final int... _coords);

	public boolean     			getValue(final int _index);
	public boolean     			getValue(final int... _coords);

	@Override
	public BitTensor  			getSliceView(final int... _slice);
	@Override
	public BitTensor  			getSliceCopy(final int... _slice);

}
