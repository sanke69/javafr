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
 * @file     Seekable.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.nio;

import java.io.IOException;

public interface Seekable extends Peekable {

	public int indexOf(byte   _magicWord) throws IOException;
	public int indexOf(byte   _magicWord, int _maxDepth) throws IOException;
	public int indexOf(byte[] _magicWord) throws IOException;
	public int indexOf(byte[] _magicWord, int _maxDepth) throws IOException;
	public int indexOf(short  _magicWord) throws IOException;
	public int indexOf(short  _magicWord, int _maxDepth) throws IOException;
	public int indexOf(int    _magicWord) throws IOException;
	public int indexOf(int    _magicWord, int _maxDepth) throws IOException;
	public int indexOf(long   _magicWord) throws IOException;
	public int indexOf(long   _magicWord, int _maxDepth) throws IOException;

}
