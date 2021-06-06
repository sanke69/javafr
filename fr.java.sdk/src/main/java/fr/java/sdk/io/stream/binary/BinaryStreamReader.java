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
 * @file     BinaryStreamReader.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.io.stream.binary;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import fr.java.io.binary.BinaryReader;
import fr.java.utils.Streams;

public abstract class BinaryStreamReader implements BinaryReader {
	public ByteBuffer buffer;

	protected BinaryStreamReader(InputStream _is) throws IOException {
		super();
		buffer = Streams.readFully(_is);
	}
	protected BinaryStreamReader(final BinaryStreamReader _source) {
		super();
		buffer = ByteBuffer.allocate(_source.buffer.capacity());

		int pos   = _source.buffer.position();
		int limit = _source.buffer.limit();
		_source.buffer.clear();

		buffer.put(_source.buffer);
		buffer.position(pos);
		buffer.limit(limit);

		_source.buffer.position(pos);
		_source.buffer.limit(limit);
	}

	public long getLength() {
		return buffer.capacity();
	}

	public long getPosition() {
		return buffer.position();
	}
	public void setPosition(long _position) {
		buffer.position((int) _position);
	}

	public int    read() {
		return (int) buffer.get();
	}
	public int    read(byte[] _array) {
		buffer.get(_array);
		return _array.length;
	}
	public int    read(byte[] _array, int _off, int _len) {
		buffer.get(_array, _off, _len);
		return _array.length;
	}
	@Override
	public int    read(ByteBuffer _bb) throws IOException {// TODO
//		int prevPosition = buffer.position(), prevLimit = buffer.limit();
		_bb.put(buffer);
		return 0;
	}

	@Override
	public abstract BinaryStreamReader clone();

	public byte   readByte() {
		return buffer.get();
	}
	public short  readShort() {
		return buffer.getShort();
	}
	public int    readInt() {
		return buffer.getInt();
	}
	public long   readLong() {
		return buffer.getLong();
	}
	public float  readFloat() {
		return buffer.getFloat();
	}
	public double readDouble() {
		return buffer.getDouble();
	}

}