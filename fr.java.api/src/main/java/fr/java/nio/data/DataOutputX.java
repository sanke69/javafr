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
package fr.java.nio.data;

import java.io.DataOutput;
import java.io.IOException;

public interface DataOutputX extends DataOutput {

	void write(int b) throws IOException;
	void write(byte b[]) throws IOException;
	void write(byte b[], int off, int len) throws IOException;

	void writeBoolean(boolean v) throws IOException;
	void writeByte(int v) throws IOException;
	void writeShort(int v) throws IOException;
	void writeChar(int v) throws IOException;
	void writeInt(int v) throws IOException;
	void writeLong(long v) throws IOException;
	void writeFloat(float v) throws IOException;
	void writeDouble(double v) throws IOException;

	void writeBytes(String s) throws IOException;
	void writeChars(String s) throws IOException;
	void writeUTF(String s) throws IOException;

}
