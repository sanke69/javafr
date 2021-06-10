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
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;

public interface FloatBufferX extends BufferX {

	public interface Editable extends FloatBufferX {

	    public FloatBufferX 	put(float f);
	    public FloatBufferX 	put(int index, float f);
	    public FloatBufferX 	put(float[] src);
	    public FloatBufferX 	put(float[] src, int offset, int length);
	    public FloatBufferX 	put(FloatBuffer src);

	}

	public float[] 			array() throws UnsupportedOperationException, ReadOnlyBufferException;

	public ByteOrder 		order();

	public FloatBufferX 	compact();
	public FloatBufferX 	slice();
	public FloatBufferX 	duplicate();
	public FloatBufferX 	asReadOnlyBuffer();

	public float 			get();
	public float 			get(int index);
	public FloatBufferX		get(float[] dst);
	public FloatBufferX		get(float[] dst, int offset, int length);

}
