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
 * @file     BinaryStreamCollectionReader.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.io.stream.binary.collections;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;

import fr.java.io.binary.collection.BinaryCollectionReader;
import fr.java.sdk.io.stream.binary.BinaryStreamReader;

public abstract class BinaryStreamCollectionReader extends BinaryStreamReader implements BinaryCollectionReader {
	protected long entryLength = -1;
	protected long count;

	protected BinaryStreamCollectionReader(InputStream _is) throws IOException {
		super(_is);

		if(getMagicNumber().isPresent()) {
			int readMagic = readInt();
			if(getMagicNumber().get() != readMagic)
				throw new RuntimeException("This BIN stream should start with the number " + getMagicNumber() + ", but is " + readMagic);

			if(getHeaderSize().isPresent()) {
				int headerSize = readHeader();
				if(getHeaderSize().get() != headerSize)
					throw new RuntimeException("This BIN stream should start with the number " + getMagicNumber() + ", but is " + readMagic);
			}
		} else
			try {
				count = getLength() / getEntryLength();
			} catch(Exception e) { count = -1; }
	}
	protected BinaryStreamCollectionReader(InputStream _is, int _entryLength) throws IOException {
		this(_is);
		entryLength = _entryLength;

		if(getMagicNumber().isPresent()) {
			int readMagic = readInt();
			if(getMagicNumber().get() != readMagic)
				throw new RuntimeException("This BIN stream should start with the number " + getMagicNumber() + ", but is " + readMagic);

			if(getHeaderSize().isPresent()) {
				int headerSize = readHeader();
				if(getHeaderSize().get() != headerSize)
					throw new RuntimeException("This BIN stream should start with the number " + getMagicNumber() + ", but is " + readMagic);
			}
		} else
			try {
				count = buffer.capacity() / getEntryLength();
			} catch(Exception e) { count = -1; }

	}
	protected BinaryStreamCollectionReader(final BinaryStreamCollectionReader _source) {
		super(_source);
		entryLength = _source.entryLength;
		count       = _source.count;
	}

	public long getEntryLength() {
		return entryLength;
	}
	public long getEntryCount() {
		return count;
	}

	@Override
	public long getEntryIndex() {
		return getHeaderSize().isPresent() ? (buffer.position() - getHeaderSize().get()) / getEntryLength() + 1 : buffer.position() / getEntryLength();
	}
	@Override
	public void setEntryIndex(long curr) {
		if(curr < 0 || curr > getEntryCount())
			throw new RuntimeException(curr + " is not in the range 0 to " + getEntryCount());
		buffer.position((int) (getHeaderSize().isPresent() ? getHeaderSize().get() + curr * getEntryLength() + 1 : curr * getEntryLength()));
	}

	public void next() {
		if(getEntryIndex() < getEntryCount())
			buffer.position(buffer.position() + (int) getEntryLength());
	}
	public void prev() {
		if(getEntryIndex() > 0)
			buffer.position(buffer.position() - (int) getEntryLength());
	}

	public byte[]   readUnsafe() {
		byte[] dat = new byte[(int) getEntryLength()];
		try { buffer.get(dat);
		} catch(BufferUnderflowException e) { return null; }
        return dat;
    }
	public byte[][] readUnsafe(int _count) {
        byte[][] out = new byte[_count][0];
        for(int i = 0; i < _count; i++){
            out[i] = new byte[(int) getEntryLength()];
			buffer.get(out[i]);
        }
        return out;
    }

	@Override
	public abstract BinaryStreamCollectionReader clone();

}
