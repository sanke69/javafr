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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import fr.java.io.binary.BinaryWriter;
import fr.java.lang.enums.AccessMode;

public class BinaryFileWriterRAF extends RandomAccessFile implements BinaryWriter {
	boolean isOpen = false;
	Path 	path;

	public BinaryFileWriterRAF(File _name) throws FileNotFoundException {
		super(_name, AccessMode.ReadWrite.posix());
	}
	public BinaryFileWriterRAF(Path _name) throws FileNotFoundException {
		super(_name.toFile(), AccessMode.ReadWrite.posix());
	}
	public BinaryFileWriterRAF(File _name, String _mode) throws FileNotFoundException {
		super(_name, _mode);
	}
	public BinaryFileWriterRAF(Path _name, String _mode) throws FileNotFoundException {
		super(_name.toFile(), _mode);
	}

	public Path getPath() {
		return path;
	}

	public void open() throws IOException {
		isOpen = true;
	}
	public void close() throws IOException {
		super.close();
		isOpen = false;
	}

	@Override
	public void write(ByteBuffer _bb) throws IOException {
		byte[] tmp = new byte[_bb.remaining()];
		_bb.slice().get(tmp);
		super.write(tmp);
	}

	@Override
	public void writeByte(byte _b) throws IOException {
		super.writeByte(_b);
	}
	@Override
	public void writeShort(short _s) throws IOException {
		super.writeShort(_s);
	}

}
