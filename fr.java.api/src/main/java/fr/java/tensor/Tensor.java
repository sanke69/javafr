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
 * @file     Tensor.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.tensor;

import java.io.Serializable;
import java.nio.Buffer;
import java.util.stream.BaseStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotSupportedException;

public interface Tensor extends Comparable<Object>, Serializable {

	public enum Nature { UNKNOWN, SCALAR, VECTOR, MATRIX, CUBE, TESSERACT, HYPERCUBE, OVER_HYPERCUBE };

	public interface Editable extends Tensor {
		
	}

	// META Access
	public boolean 				isDirect();

	public Primitive 			getPrimitive();

	public Nature     			getNature();
	public Nature     			getNature(boolean _simplified);

	// DIMENSION Access
	public int[]				getShape();
	public int					getCapacity();

	public default int		  	getSliceDepth() 											{ return getShape().length; }
	public default int		  	getSliceDimension(final int _depth) 						{ return getShape()[_depth]; }
	public default int		  	getSliceDimension(final int _depth, boolean _inverseOrder) 	{ return getShape()[_inverseOrder ? getSliceDepth() - 1 - _depth : _depth]; }
	public int	 				getSliceOffset(final int... _slice);
	public int[] 				getSliceShape(final int... _slice);
	public int					getSliceCapacity(final int _depth);
	public default int	 		getSliceCapacity(final int... _slice)						{ return getSliceCapacity(_slice.length); }
	
	// DATA Access
	public Tensor     			getSliceView(final int... _slice);
	public Tensor     			getSliceCopy(final int... _slice);

	public void    				reshape(final int... _shape);

	// STORAGE Access
	public Object				getArray()		throws NotSupportedException;
	public Buffer				getBuffer()		throws NotSupportedException;
	public BaseStream<?,?> 		getStream()		throws NotSupportedException;

	// JAVA METHODS
	public Tensor				clone() throws NotSupportedException, CloneNotSupportedException;

}
