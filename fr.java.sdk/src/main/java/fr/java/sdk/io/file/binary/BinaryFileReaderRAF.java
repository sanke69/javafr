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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import fr.java.io.binary.BinaryReader;
import fr.java.lang.enums.AccessMode;

public /*abstract*/ class BinaryFileReaderRAF extends RandomAccessFile implements BinaryReader {
	Path path;

	protected BinaryFileReaderRAF(Path name) throws FileNotFoundException {
		super(name.toFile(), AccessMode.ReadOnly.posix());
		path = name;
	}

	@Override
	public long getLength() throws IOException {
		return length();
	}

	public long getPosition() throws IOException {
		return getFilePointer();
	}
	public void setPosition(long _position) throws IOException {
		seek(_position);
	}

	@Override
	public int read(ByteBuffer bb) throws IOException {
		byte[] readBytes = new byte[bb.remaining()];
		read(readBytes);
		bb.put(readBytes);
		return readBytes.length;
	}

	@Override
	public BinaryFileReaderRAF clone() {
		try {
			BinaryFileReaderRAF deepCopy = new BinaryFileReaderRAF(path);
			deepCopy.setPosition(getPosition());
			return deepCopy;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}