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

import fr.java.math.geometry.space.Point3D;

public interface Point3DBufferX extends BufferX {

	public int 				capacity();

	public int 				position();
	public Point3DBufferX 	position(int _newPosition);

	public int 				limit();
    public Point3DBufferX 	limit(int _newLimit);

    public Point3DBufferX 	mark();
    public Point3DBufferX 	reset();
    public Point3DBufferX 	clear();
    public Point3DBufferX 	rewind();
    public int 				remaining();

	public default boolean 	hasRemaining() { return remaining() > 0; }

	public Point3D   		get();
	public Point3D   		get(int _index);
	public Point3DBufferX 	get(Point3D.Editable[] _dst, int _offset, int _length);

	public Point3DBufferX 	put(Point3D _pt);
	public Point3DBufferX 	put(int _index, Point3D _pt);
	public Point3DBufferX 	put(Point3D[] _src, int _offset, int _length);
	public Point3DBufferX 	put(Point3DBufferX _src);
	public Point3DBufferX 	put(Buffer _src);

	public Buffer 			Buffer();
	public Point3D[] 		arrayCopy();

    public Point3DBufferX 	slice();
	public Point3DBufferX 	duplicate();

}
