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
 * @file     BinaryCollectionFileReader.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.io.file.binary.collections;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import fr.java.io.binary.collection.BinaryCollectionReader;
import fr.java.sdk.io.file.binary.BinaryFileReader;

public abstract class BinaryCollectionFileReader extends BinaryFileReader implements BinaryCollectionReader {
	protected long   			entryLength = -1;
	final protected ByteBuffer 	entryByteBuffer;

	protected BinaryCollectionFileReader(final Path name) throws IOException {
		super(name);

		if(getMagicNumber().isPresent()) {
			int readMagic = readMagicNumber();
			if(getMagicNumber().get().intValue() != readMagic)
				throw new RuntimeException("This BIN file " + name + " should start with the number " + getMagicNumber().get() + ", but is " + readMagic);

			if(getHeaderSize().isPresent()) {
				int headerSize = readHeader();
				if(getHeaderSize().get().intValue() != headerSize)
					throw new RuntimeException("This BIN file " + name + " should have a header of " + getHeaderSize().get() + ", but is " + headerSize);
			}
		}

		entryByteBuffer = ByteBuffer.allocateDirect((int) getEntryLength());
	}
	protected BinaryCollectionFileReader(final Path name, final int _entryLength) throws IOException {
		super(name);
		entryLength = _entryLength;

		if(getMagicNumber().isPresent()) {
			int readMagic = readMagicNumber();
			if(getMagicNumber().get() != readMagic)
				throw new RuntimeException("This BIN file " + name + " should start with the number " + getMagicNumber() + ", but is " + readMagic);

			if(getHeaderSize().isPresent()) {
				int headerSize = readHeader();
				if(getHeaderSize().get() != headerSize)
					throw new RuntimeException("This BIN file " + name + " should start with the number " + getMagicNumber() + ", but is " + readMagic);
			}
		}
	
		entryByteBuffer = ByteBuffer.allocateDirect(_entryLength);
	}
	protected BinaryCollectionFileReader(final BinaryCollectionFileReader _binaryFileCollectionReader) throws IOException {
		super(_binaryFileCollectionReader);
		entryLength     = _binaryFileCollectionReader.entryLength;
		entryByteBuffer = ByteBuffer.allocateDirect((int) entryLength);
	}

	@Override
	public abstract BinaryCollectionFileReader clone();

	public int readMagicNumber() throws IOException {
		return readInt();
	}

	@Override
	public long getEntryLength() {
		return entryLength;
	}
	@Override
	public long getEntryCount() {
		try {
			if(!getHeaderSize().isPresent())
				return getLength() / entryLength;
			else
				return (getLength() - getHeaderSize().get()) / getEntryLength();
		} catch(IOException e) { return -1; }
	}

	@Override
	public long getEntryIndex() {
		try {
			return getHeaderSize().isPresent() ? (getPosition() - getHeaderSize().get()) / getEntryLength() : getPosition() / getEntryLength();
		} catch(IOException e) { e.printStackTrace();return -1; }
	}
	@Override
	public void setEntryIndex(long curr) {
		try {
			if(curr < 0 || curr > getEntryCount())
				throw new RuntimeException(curr + " is not in the range 0 to " + getEntryCount());
			setPosition(getHeaderSize().isPresent() ? getHeaderSize().get() + curr * getEntryLength() : curr * getEntryLength());
		} catch(IOException e) { throw new RuntimeException(e); }
	}

	@Override
	public void next() throws IOException {
		if(getEntryIndex() < getEntryCount()) {
			setPosition(getPosition() + (int) getEntryLength());
		}
	}
	@Override
	public void prev() throws IOException {
		if(getEntryIndex() > 0) {
			setPosition(getPosition() - getEntryLength());
		}
	}

	@Override
	public ByteBuffer 	readEntry() throws IOException {
		entryByteBuffer.clear();
		if(read(entryByteBuffer) == -1)
			return null;
		entryByteBuffer.rewind();
        return entryByteBuffer;
    }
	@Override
	public byte[] 		readUnsafe() throws IOException {
        byte[] out = new byte[(int) getEntryLength()];
		if(read(out) == -1)
			return null;
        return out;
    }
	@Override
	public byte[][] 	readUnsafe(int _count) throws IOException {
        byte[][] out = new byte[_count][(int) getEntryLength()];
        for(int i = 0; i < _count; i++){
//            out[i] = new byte[(int) getEntryLength()];
            read(out[i]);
        }
        return out;
    }

}