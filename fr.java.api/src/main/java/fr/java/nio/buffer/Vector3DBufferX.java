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
package fr.java.nio.buffer;

import java.nio.Buffer;

import fr.java.math.geometry.space.Vector3D;

public interface Vector3DBufferX extends BufferX {

	public int 				capacity();

	public int 				position();
	public Vector3DBufferX 	position(int _newPosition);

	public int 				limit();
    public Vector3DBufferX 	limit(int _newLimit);

    public Vector3DBufferX 	mark();
    public Vector3DBufferX 	reset();
    public Vector3DBufferX 	clear();
    public Vector3DBufferX 	rewind();
    public int 				remaining();

	public default boolean 	hasRemaining() { return remaining() > 0; }

	public Vector3D   		get();
	public Vector3D   		get(int _index);
	public Vector3DBufferX 	get(Vector3D.Editable[] _dst, int _offset, int _length);

	public Vector3DBufferX 	put(Vector3D _pt);
	public Vector3DBufferX 	put(int _index, Vector3D _pt);
	public Vector3DBufferX 	put(Vector3D[] _src, int _offset, int _length);
	public Vector3DBufferX 	put(Vector3DBufferX _src);
	public Vector3DBufferX 	put(Buffer _src);

	public Buffer 			Buffer();
	public Vector3D[] 		arrayCopy();

    public Vector3DBufferX 	slice();
	public Vector3DBufferX 	duplicate();

}
