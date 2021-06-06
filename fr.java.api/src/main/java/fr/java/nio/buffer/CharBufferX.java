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
 * @file     CharBufferX.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.nio.buffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

public interface CharBufferX extends BufferX, Comparable<CharBuffer>, CharSequence, Readable {

	public interface Editable extends CharBufferX, Appendable {

	    public CharBufferX 	put(char c) throws ReadOnlyBufferException;
	    public CharBufferX 	put(int index, char c);
	    public CharBufferX 	put(char[] src);
	    public CharBufferX 	put(char[] src, int offset, int length);
	    public CharBufferX 	put(CharBuffer src);
	    public CharBufferX 	put(String src) throws BufferOverflowException, ReadOnlyBufferException;
	    public CharBufferX 	put(String src, int start, int end) throws BufferOverflowException, IndexOutOfBoundsException, ReadOnlyBufferException;

	}

    public char[] 			array() throws UnsupportedOperationException, ReadOnlyBufferException;

    public ByteOrder 		order();

    public CharBufferX 		compact() throws ReadOnlyBufferException;
    public CharBufferX 		slice();
    public CharBufferX 		duplicate();
    public CharBufferX 		asReadOnlyBuffer();

    public char 			get() throws BufferUnderflowException;
    public char 			get(int index);
    public CharBufferX 		get(char[] dst);
    public CharBufferX 		get(char[] dst, int offset, int length);

}
