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

import java.io.IOException;
import java.nio.InvalidMarkException;
import java.nio.ReadOnlyBufferException;

public interface BufferX {

	public boolean 	isDirect();					// Meaning direct access to memory, ie Native Pointer or CUDA Memory...
	public boolean 	isReadOnly();

	public boolean 	hasRemaining();
	public int 		remaining();

	public int 		capacity();

	public BufferX 	position(int newPosition) 	throws IllegalArgumentException;
	public int 		position();

	public BufferX 	limit(int newLimit)			throws IllegalArgumentException;
	public int 		limit();

	public BufferX 	mark() 						throws InvalidMarkException;
	public BufferX 	reset() 					throws IOException;

	public BufferX 	flip();
	public BufferX 	rewind();
	public BufferX 	clear();

	public boolean 	hasArray();
	public Object 	array() 					throws ReadOnlyBufferException, UnsupportedOperationException;
	public int 		arrayOffset() 				throws ReadOnlyBufferException, UnsupportedOperationException;

}
