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

import fr.java.math.geometry.plane.Point2D;

public interface Point2DBufferX extends BufferX {

	public int 				capacity();

	public int 				position();
	public Point2DBufferX 	position(int _newPosition);

	public int 				limit();
    public Point2DBufferX 	limit(int newLimit);
    
    public Point2DBufferX 	mark();
    public Point2DBufferX 	reset();
    public Point2DBufferX 	clear();
    public Point2DBufferX 	rewind();
    public int 				remaining();

	public default boolean 	hasRemaining() { return remaining() > 0; }
	
	public Point2D   		get();
	public Point2D   		get(int _index);
	public Point2DBufferX 	get(Point2D.Editable[] _dst, int _offset, int _length);

	public Point2DBufferX 	put(Point2D _pt);
	public Point2DBufferX 	put(int _index, Point2D _pt);
	public Point2DBufferX 	put(Point2D[] _src, int _offset, int _length);

	public Buffer 			Buffer();
	public Point2D[] 		arrayCopy();

    public Point2DBufferX 	slice();
	public Point2DBufferX 	duplicate();

}
