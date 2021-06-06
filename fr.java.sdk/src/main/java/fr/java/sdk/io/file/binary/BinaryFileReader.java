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
 * @file     BinaryFileReader.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.io.file.binary;

import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import fr.java.io.binary.BinaryReader;

public class BinaryFileReader implements BinaryReader, AutoCloseable {
	Path 				path;
	SeekableByteChannel sbc;
	ByteBuffer			buffer64;

	protected BinaryFileReader(Path _path) throws IOException {
		super();
		sbc      = Files.newByteChannel(path = _path, EnumSet.of(READ));
		buffer64 = ByteBuffer.allocate(8);
	}
	protected BinaryFileReader(final BinaryFileReader _source) throws IOException {
		super();
		sbc      = Files.newByteChannel(path = _source.path, EnumSet.of(READ));
		buffer64 = ByteBuffer.allocate(8);

		sbc.position(_source.sbc.position());
	}

	public void open() throws IOException {}
	public void close() throws IOException { sbc.close(); }

//	@Override
	public Path 	getPath() {
		return path;
	}

	@Override
	public long 	getLength() throws IOException {
		return sbc.size();
	}

	@Override
	public long 	getPosition() throws IOException {
		return sbc.position();
	}
	@Override
	public void 	setPosition(long _position) throws IOException {
		sbc.position(_position);
	}

	@Override
	public int 		read() throws IOException {
		return readByte();
	}
	@Override
	public int 		read(byte[] _array) throws IOException {
		ByteBuffer customBuffer = ByteBuffer.wrap(_array);
		return sbc.read(customBuffer);
	}
	@Override
	public int 		read(byte[] _array, int _offset, int _length) throws IOException {
		ByteBuffer customBuffer = ByteBuffer.wrap(_array, _offset, _length);
		return sbc.read(customBuffer);
	}
	@Override
	public int 		read(ByteBuffer _bb) throws IOException {
		return sbc.read(_bb);
	}

	@Override
	public byte 	readByte() throws IOException {
		buffer64.clear();
		buffer64.limit(1);
		sbc.read(buffer64);
		buffer64.flip();
		return buffer64.get();
	}
	public byte 	readUnsignedByte() throws IOException {
		buffer64.clear();
		buffer64.limit(1);
		sbc.read(buffer64);
		buffer64.flip();
		return (byte) (buffer64.get() & 0xFF);
	}
	@Override
	public short 	readShort() throws IOException {
		buffer64.clear();
		buffer64.limit(2);
		sbc.read(buffer64);
		buffer64.flip();
		return buffer64.getShort();
	}
	@Override
	public int 		readInt() throws IOException {
		buffer64.clear();
		buffer64.limit(4);
		sbc.read(buffer64);
		buffer64.flip();
		return buffer64.getInt();
	}
	@Override
	public long 	readLong() throws IOException {
		buffer64.clear();
		buffer64.limit(8);
		sbc.read(buffer64);
		buffer64.flip();
		return buffer64.getLong();
	}
	@Override
	public float 	readFloat() throws IOException {
		buffer64.clear();
		buffer64.limit(4);
		sbc.read(buffer64);
		buffer64.flip();
		return buffer64.getFloat();
	}
	@Override
	public double 	readDouble() throws IOException {
		buffer64.clear();
		buffer64.limit(8);
		sbc.read(buffer64);
		buffer64.flip();
		return buffer64.getDouble();
	}

	@Override
	public BinaryFileReader clone() {
		try {
			return new BinaryFileReader(this);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}