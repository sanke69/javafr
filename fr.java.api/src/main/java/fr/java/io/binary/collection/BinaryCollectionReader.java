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
 * @file     BinaryCollectionReader.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.io.binary.collection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

import fr.java.io.binary.BinaryReader;

public interface BinaryCollectionReader extends BinaryReader {

	public Optional<Integer> 	getMagicNumber();
	public Optional<Integer> 	getHeaderSize();

	public long     			getEntryLength();
	public long     			getEntryCount();

	public long     			getEntryIndex() throws IOException;
	public void     			setEntryIndex(long _current) throws IOException;

	public void 				next() throws IOException;
	public void 				prev() throws IOException;

	public int					readHeader() throws IOException;
	public ByteBuffer			readEntry() throws IOException;

	public byte[] 				readUnsafe() throws IOException;
	public byte[][] 			readUnsafe(int _count) throws IOException;

}
