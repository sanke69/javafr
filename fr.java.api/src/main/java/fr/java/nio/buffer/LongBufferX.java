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
 * @file     LongBufferX.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.nio.buffer;

import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;

public interface LongBufferX extends BufferX {

	public interface Editable extends LongBufferX {
	
		public LongBufferX 		put(long l);
		public LongBufferX 		put(int index, long l);
		public LongBufferX 		put(long[] src);
		public LongBufferX 		put(long[] src, int offset, int length);
		public LongBufferX 		put(LongBuffer src);
	
	}

	public long[] 			array() throws UnsupportedOperationException, ReadOnlyBufferException;
	
	public ByteOrder 		order();
	
	public LongBufferX 		compact();
	public LongBufferX 		slice();
	public LongBufferX 		duplicate();
	public LongBufferX 		asReadOnlyBuffer();
	
	public long 			get();
	public long 			get(int index);
	public LongBufferX 		get(long[] dst);
	public LongBufferX 		get(long[] dst, int offset, int length);

}
