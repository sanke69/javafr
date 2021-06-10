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
package fr.java.nio.buffer;

import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;

public interface ShortBufferX extends BufferX/*, Comparable<ShortBufferX>*/ {

	public interface Editable extends ShortBufferX {

	    public ShortBufferX		put(short s);
	    public ShortBufferX		put(int index, short s);
	    public ShortBufferX		put(short[] src);
	    public ShortBufferX		put(short[] src, int offset, int length);
	    public ShortBufferX		put(ShortBuffer src);

	}

    public short[] 			array() throws UnsupportedOperationException, ReadOnlyBufferException;

    public ByteOrder 		order();

    public ShortBufferX 	compact();
    public ShortBufferX 	slice();
    public ShortBufferX 	duplicate();
    public ShortBufferX 	asReadOnlyBuffer();

    public short 			get();
    public short 			get(int index);
    public ShortBufferX		get(short[] dst);
    public ShortBufferX		get(short[] dst, int offset, int length);

}
