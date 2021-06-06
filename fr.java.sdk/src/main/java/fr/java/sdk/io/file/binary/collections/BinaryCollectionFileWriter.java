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
 * @file     BinaryFileCollectionWriter.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.io.file.binary.collections;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import fr.java.io.binary.collection.BinaryCollectionWriter;
import fr.java.sdk.io.file.binary.BinaryFileWriter;

public abstract class BinaryCollectionFileWriter extends BinaryFileWriter implements BinaryCollectionWriter {
	int  MAGIC_NUMBER = 1369;

	Path 	path;
	boolean isOpen, readHeader;

	int 	entryLength;

	public BinaryCollectionFileWriter(Path _path, boolean _append) throws IOException {
		this(_path, false, 0, 1369, _append);
	}
	public BinaryCollectionFileWriter(Path _path, int _entryLength, boolean _append) throws IOException {
		this(_path, false, _entryLength, 1369, _append);
	}
	public BinaryCollectionFileWriter(Path _path, boolean _readHeader, int _entryLength, boolean _append) throws IOException {
		this(_path, _readHeader, _entryLength, 1369, _append);
	}
	public BinaryCollectionFileWriter(Path _path, boolean _readHeader, int _entryLength, int _magicNumber, boolean _append) throws IOException {
		super(_path, _append);
		isOpen 		 = false;
		path   		 = _path;
		readHeader   = _readHeader;
		entryLength  = _entryLength;
		MAGIC_NUMBER = _magicNumber;
	}

	public Path getPath() {
		return path;
	}

	public int getMagicNumber() {
		return MAGIC_NUMBER;
	}
	public long getHeaderSize() {
		return 0;
	}
	public long getEntryLength() {
		return entryLength;
	}

	public void open() throws IOException {
		isOpen = true;
		begin();
	}
	public void begin() throws IOException {
		if(getHeaderSize() > 0) {
			// Write Magix Number
			writeInt(getMagicNumber());
			writeHeader();
		}
	}

	@Override
	public void writeHeader() throws IOException {
		;
	}

	public void writeEntry(ByteBuffer _entry) throws IOException {
		if(_entry.remaining() != getEntryLength())
		write(_entry.array());
	}

	public void close() throws IOException {
		isOpen = false;
	}

}
