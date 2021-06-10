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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;

public interface ByteBufferX extends BufferX {

	public interface Editable extends ByteBufferX {

		public ByteBufferX 		put(byte b);
		public ByteBufferX 		put(int index, byte b);
		public ByteBufferX 		put(byte[] src);
		public ByteBufferX 		put(byte[] src, int offset, int length);
		public ByteBufferX 		put(ByteBuffer src);
		public ByteBufferX 		put(ByteBufferX src);

		public ByteBufferX 		putChar(char value);
		public ByteBufferX 		putChar(int index, char value);
		public ByteBufferX 		putShort(short value);
		public ByteBufferX 		putShort(int index, short value);
		public ByteBufferX 		putInt(int value);
		public ByteBufferX 		putInt(int index, int value);
		public ByteBufferX 		putFloat(float value);
		public ByteBufferX 		putFloat(int index, float value);
		public ByteBufferX 		putLong(long value);
		public ByteBufferX 		putLong(int index, long value);
		public ByteBufferX 		putDouble(double value);
		public ByteBufferX 		putDouble(int index, double value);

	}

	public byte[] 			array() throws UnsupportedOperationException, ReadOnlyBufferException;

	public ByteOrder 		order();
	public ByteBufferX 		order(ByteOrder bo);

	public ByteBufferX 		compact();
	public ByteBufferX 		slice();
	public ByteBufferX 		duplicate();
	public ByteBufferX 		asReadOnlyBuffer();

	public byte 			get();
	public byte 			get(int index);
	public ByteBufferX 		get(byte[] dst);
	public ByteBufferX 		get(byte[] dst, int offset, int length);

	public char 			getChar();
	public char 			getChar(int index);
	public short 			getShort();
	public short 			getShort(int index);
	public int 				getInt();
	public int 				getInt(int index);
	public long 			getLong();
	public long 			getLong(int index);
	public float 			getFloat();
	public float 			getFloat(int index);
	public double 			getDouble();
	public double 			getDouble(int index);

	public CharBuffer 		asCharBuffer();
	public ShortBuffer 		asShortBuffer();
	public IntBuffer 		asIntBuffer();
	public LongBuffer 		asLongBuffer();
	public FloatBuffer 		asFloatBuffer();
	public DoubleBuffer 	asDoubleBuffer();

}
