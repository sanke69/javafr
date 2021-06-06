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
 * @file     BinaryFileWriter.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.io.file.binary;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import fr.java.io.binary.BinaryWriter;

public class BinaryFileWriter implements BinaryWriter {
	SeekableByteChannel sbc;
	ByteBuffer			buffer64;

	public BinaryFileWriter(Path _path) throws IOException {
		super();
		sbc      = Files.newByteChannel(_path, EnumSet.of(CREATE, APPEND, WRITE));
		buffer64 = ByteBuffer.allocate(8);
	}
	public BinaryFileWriter(Path _path, boolean _append) throws IOException {
		super();
		sbc      = Files.newByteChannel(_path, _append ? EnumSet.of(CREATE, APPEND, WRITE) : EnumSet.of(CREATE, TRUNCATE_EXISTING, WRITE));
		buffer64 = ByteBuffer.allocate(8);
	}

	public void open() throws IOException {}
	public void close() throws IOException { sbc.close(); }

	@Override
	public void write(byte[] _array) throws IOException {
		sbc.write(ByteBuffer.wrap(_array));
	}
	@Override
	public void write(ByteBuffer _bb) throws IOException {
		sbc.write(_bb);
	}

	@Override
	public void writeBoolean(boolean _b) throws IOException {
		buffer64.clear();
		buffer64.put((byte) (_b == true ? 1 : 0));
		buffer64.flip();
		sbc.write(buffer64);
	}
	@Override
	public void writeByte(byte _b) throws IOException {
		buffer64.clear();
		buffer64.put(_b);
		buffer64.flip();
		sbc.write(buffer64);
	}
	@Override
	public void writeShort(short _s) throws IOException {
		buffer64.clear();
		buffer64.putShort(_s);
		buffer64.flip();
		sbc.write(buffer64);
	}
	@Override
	public void writeInt(int _i) throws IOException {
		buffer64.clear();
		buffer64.putInt(_i);
		buffer64.flip();
		sbc.write(buffer64);
	}
	@Override
	public void writeLong(long _l) throws IOException {
		buffer64.clear();
		buffer64.putLong(_l);
		buffer64.flip();
		sbc.write(buffer64);
	}
	@Override
	public void writeFloat(float _f) throws IOException {
		buffer64.clear();
		buffer64.putFloat(_f);
		buffer64.flip();
		sbc.write(buffer64);
	}
	@Override
	public void writeDouble(double _d) throws IOException {
		buffer64.clear();
		buffer64.putDouble(_d);
		buffer64.flip();
		sbc.write(buffer64);
	}

}
