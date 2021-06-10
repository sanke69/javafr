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
package fr.java.media.shape;

import java.nio.Buffer;
import java.nio.ReadOnlyBufferException;

import fr.java.math.geometry.space.Point3D;
import fr.java.media.Media;
import fr.java.media.MediaFormat;
import fr.java.nio.buffer.BufferX;
import fr.java.nio.buffer.Point3DBufferX;

public interface DotCloud extends Media<Point3D[]>, Point3DBufferX {

	public static DotCloud 	wrap(Point3DBufferX _ptBuffer) {
		return new DotCloud() {

			public boolean isWrapper() { return true; }
			public Object  getWrapped() { return _ptBuffer; }

			@Override
			public Point3D[] data() {
				return null;
			}
			@Override
			public MediaFormat format() {
				return null;
			}

			@Override
			public boolean isDirect() {
				return _ptBuffer.isDirect();
			}
			@Override
			public boolean isReadOnly() {
				return _ptBuffer.isReadOnly();
			}
		
			@Override
			public int capacity() {
				return _ptBuffer.capacity();
			}

			@Override
			public Point3D[] asArray() {
				return _ptBuffer.arrayCopy();
			}

			@Override
			public Point3D get() {
				return _ptBuffer.get();
			}

			@Override
			public Point3D get(int _index) {
				return _ptBuffer.get(_index);
			}

			@Override
			public Point3DBufferX get(Point3D.Editable[] _dst, int _offset, int _length) {
				return _ptBuffer.get(_dst, _offset, _length);
			}

			@Override
			public Point3DBufferX put(Point3D _pt) {
				return _ptBuffer.put(_pt);
			}

			@Override
			public Point3DBufferX put(int _index, Point3D _pt) {
				return _ptBuffer.put(_index, _pt);
			}

			@Override
			public Point3DBufferX put(Point3D[] _src, int _offset, int _length) {
				return _ptBuffer.put(_src, _offset, _length);
			}

			@Override
			public Point3DBufferX put(java.nio.Buffer _src) {
				return _ptBuffer.put(_src);
			}

			@Override
			public Point3DBufferX put(Point3DBufferX _src) {
				return _ptBuffer.put(_src);
			}

			@Override
			public Buffer Buffer() {
				return _ptBuffer.Buffer();
			}
			
			@Override
			public Point3D[] arrayCopy() {
				return _ptBuffer.arrayCopy();
			}

			@Override
			public Point3DBufferX slice() {
				return _ptBuffer.slice();
			}

			@Override
			public Point3DBufferX duplicate() {
				return _ptBuffer.duplicate();
			}

			@Override
			public int position() {
				return _ptBuffer.position();
			}

			@Override
			public Point3DBufferX position(int _newPosition) {
				return _ptBuffer.position(_newPosition);
			}

			@Override
			public int limit() {
				return _ptBuffer.limit();
			}

			@Override
			public Point3DBufferX limit(int _newLimit) {
				return _ptBuffer.limit(_newLimit);
			}

			@Override
			public Point3DBufferX mark() {
				return _ptBuffer.mark();
			}

			@Override
			public Point3DBufferX reset() {
				return _ptBuffer.reset();
			}

			@Override
			public Point3DBufferX clear() {
				return _ptBuffer.clear();
			}

			@Override
			public Point3DBufferX rewind() {
				return _ptBuffer.rewind();
			}

			@Override
			public int remaining() {
				return _ptBuffer.remaining();
			}

			@Override
			public BufferX flip() {
				return _ptBuffer.flip();
			}
			@Override
			public boolean hasArray() {
				return _ptBuffer.hasArray();
			}
			@Override
			public Object array() throws ReadOnlyBufferException, UnsupportedOperationException {
				return _ptBuffer.array();
			}
			@Override
			public int arrayOffset() throws ReadOnlyBufferException, UnsupportedOperationException {
				return _ptBuffer.arrayOffset();
			}

		};
	}

	public default boolean 	isPlayable() { return false; }

	public int 				capacity();

	public Point3D 			get();
	public Point3D 			get(int _index);

	public Point3D[] 		asArray();

	public boolean 			isWrapper();
	public Object 			getWrapped();

}
