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
 * @file     DataInputX.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.nio.data;

import java.io.DataInput;
import java.io.IOException;

public interface DataInputX extends DataInput {

	void 	readFully(byte b[]) throws IOException;
	void 	readFully(byte b[], int off, int len) throws IOException;

	int 	skipBytes(int n) throws IOException;

	boolean readBoolean() throws IOException;
	byte 	readByte() throws IOException;
	int 	readUnsignedByte() throws IOException;
	short 	readShort() throws IOException;
	int 	readUnsignedShort() throws IOException;
	char 	readChar() throws IOException;
	int 	readInt() throws IOException;
	long 	readLong() throws IOException;
	float 	readFloat() throws IOException;
	double 	readDouble() throws IOException;

	String 	readLine() throws IOException;
	String 	readUTF() throws IOException;

}
