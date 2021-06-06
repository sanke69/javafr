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
 * @file     IntBufferX.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.nio.buffer;

import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ReadOnlyBufferException;

public interface IntBufferX extends BufferX {

	public interface Editable extends IntBufferX {

	    public IntBufferX 		put(int i);
	    public IntBufferX 		put(int index, int i);
	    public IntBufferX 		put(int[] src);
	    public IntBufferX 		put(int[] src, int offset, int length);
	    public IntBufferX 		put(IntBuffer src);

	}

    public int[] 			array() throws UnsupportedOperationException, ReadOnlyBufferException;
   
    public ByteOrder 		order();
   
    public IntBufferX 		compact();
    public IntBufferX 		slice();
    public IntBufferX 		duplicate();
    public IntBufferX 		asReadOnlyBuffer();

    public int 				get();
    public int 				get(int index);
    public IntBufferX 		get(int[] dst);
    public IntBufferX 		get(int[] dst, int offset, int length);

}
