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
package fr.java.file.archive;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface Archive extends Closeable, Iterable<Archive.Entry> {
	static final int BUFFER_SIZE = 4_194_304; //131072; // 8192;

	public interface Entry {
		public String 			getName();
		public InputStream 		getContent() throws IOException;

		public boolean			isFile();
		public boolean			isDirectory();
	}

	public Path 				getPath();
	public default InputStream 	getStream() throws IOException {
		if(getPath() != null)
			return new FileInputStream(getPath().toFile());
		return null;
	}

	public default void 		saveEntry(Entry _entry, Path _dst) throws IOException {
		try(FileOutputStream fos = new FileOutputStream(_dst.toFile())) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int    read;
			while((read = _entry.getContent().read(buffer)) > 0)
				fos.write(buffer, 0, read);
		}
	}

}
