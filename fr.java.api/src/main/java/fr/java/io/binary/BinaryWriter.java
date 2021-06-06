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
 * @file     BinaryWriter.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.io.binary;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.RandomAccess;

public interface BinaryWriter extends RandomAccess {

	public default void open() throws IOException {}
	public default void close() throws IOException {}

	public void write(byte[] _array) throws IOException;
	public void write(ByteBuffer _bb) throws IOException;

	public void	writeBoolean(boolean v) throws IOException;
	public void writeByte(byte _b) throws IOException;
	public void writeShort(short _s) throws IOException;
	public void writeInt(int _i) throws IOException;
	public void writeLong(long _l) throws IOException;
	public void writeFloat(float _f) throws IOException;
	public void writeDouble(double _d) throws IOException;
/*
    public void writeByte(int v) throws IOException;
    public void writeShort(int v) throws IOException;
    public void writeChar(int v) throws IOException;
//    public void writeInt(int v) throws IOException;
//    public void writeLong(long v) throws IOException;
//    public void writeFloat(float v) throws IOException;
//    public void writeDouble(double v) throws IOException;
    public void writeBytes(String s) throws IOException;
    public void writeChars(String s) throws IOException;
    public void writeUTF(String str) throws IOException;
*/
}
