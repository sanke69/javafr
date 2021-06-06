package fr.java.sdk.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import fr.java.nio.buffer.ByteBufferX;
import fr.java.nio.buffer.CharBufferX;
import fr.java.nio.buffer.DoubleBufferX;
import fr.java.nio.buffer.FloatBufferX;
import fr.java.nio.buffer.IntBufferX;
import fr.java.nio.buffer.LongBufferX;
import fr.java.nio.buffer.Point2DBufferX;
import fr.java.nio.buffer.Point3DBufferX;
import fr.java.nio.buffer.ShortBufferX;
import fr.java.nio.buffer.Vector3DBufferX;
import fr.java.sdk.nio.buffer.DelegatedByteBufferX;
import fr.java.sdk.nio.buffer.DelegatedCharBufferX;
import fr.java.sdk.nio.buffer.DelegatedDoubleBufferX;
import fr.java.sdk.nio.buffer.DelegatedFloatBufferX;
import fr.java.sdk.nio.buffer.DelegatedIntBufferX;
import fr.java.sdk.nio.buffer.DelegatedLongBufferX;
import fr.java.sdk.nio.buffer.DelegatedShortBufferX;
import fr.java.sdk.nio.buffer.Point2DDoubleBufferX;
import fr.java.sdk.nio.buffer.Point2DFloatBufferX;
import fr.java.sdk.nio.buffer.Point3DDoubleBufferX;
import fr.java.sdk.nio.buffer.Vector3DDoubleBufferX;

public class BuffersX {

	public static class Byte {

		public static ByteBufferX.Editable allocate(int _capacity) {
			return new DelegatedByteBufferX(_capacity);
		}
		public static ByteBufferX.Editable wrap(byte[] _wrapped) {
			return new DelegatedByteBufferX(_wrapped);
		}
		public static ByteBufferX.Editable wrap(ByteBuffer _buffer) {
			return new DelegatedByteBufferX(_buffer);
		}

	}
	public static class Char {

		public static CharBufferX allocate(int _capacity) {
			return new DelegatedCharBufferX(_capacity);
		}
		public static CharBufferX wrap(char[] _wrapped) {
			return new DelegatedCharBufferX(_wrapped);
		}
		public static CharBufferX wrap(CharBuffer _buffer) {
			return new DelegatedCharBufferX(_buffer);
		}

	}
	public static class Short {

		public static ShortBufferX allocate(int _capacity) {
			return new DelegatedShortBufferX(_capacity);
		}
		public static ShortBufferX wrap(short[] _wrapped) {
			return new DelegatedShortBufferX(_wrapped);
		}
		public static ShortBufferX wrap(ShortBuffer _buffer) {
			return new DelegatedShortBufferX(_buffer);
		}

	}
	public static class Int {

		public static IntBufferX allocate(int _capacity) {
			return new DelegatedIntBufferX(_capacity);
		}
		public static IntBufferX wrap(int[] _wrapped) {
			return new DelegatedIntBufferX(_wrapped);
		}
		public static IntBufferX wrap(IntBuffer _buffer) {
			return new DelegatedIntBufferX(_buffer);
		}

	}
	public static class Long {

		public static LongBufferX allocate(int _capacity) {
			return new DelegatedLongBufferX(_capacity);
		}
		public static LongBufferX wrap(long[] _wrapped) {
			return new DelegatedLongBufferX(_wrapped);
		}
		public static LongBufferX wrap(LongBuffer _buffer) {
			return new DelegatedLongBufferX(_buffer);
		}
		
	}
	public static class Float {

		public static FloatBufferX allocate(int _capacity) {
			return new DelegatedFloatBufferX(_capacity);
		}
		public static FloatBufferX wrap(float[] _wrapped) {
			return new DelegatedFloatBufferX(_wrapped);
		}
		public static FloatBufferX wrap(FloatBuffer _buffer) {
			return new DelegatedFloatBufferX(_buffer);
		}
		
	}
	public static class Double {

		public static DoubleBufferX allocate(int _capacity) {
			return new DelegatedDoubleBufferX(_capacity);
		}
		public static DoubleBufferX wrap(double[] _wrapped) {
			return new DelegatedDoubleBufferX(_wrapped);
		}
		public static DoubleBufferX wrap(DoubleBuffer _buffer) {
			return new DelegatedDoubleBufferX(_buffer);
		}
		
	}


	public static Point2DDoubleBufferX allocate2D(int _size) {
		return new Point2DDoubleBufferX(_size);
	}

	public static Point2DFloatBufferX wrap(FloatBuffer _buffer) {
		if(_buffer.capacity() % 2 != 0)
			return null;

		return new Point2DFloatBufferX(_buffer);
	}
	public static Point2DBufferX wrap2D(DoubleBuffer _buffer) {
		if(_buffer.capacity() % 2 != 0)
			return null;

		return new Point2DDoubleBufferX(_buffer);
	}

	public static Point3DDoubleBufferX allocate3D(int _size) {
		return new Point3DDoubleBufferX(_size);
	}
/*
	public static Point3DBuffer wrap(FloatBuffer _buffer) {
		if(_buffer.capacity() % 3 != 0)
			return null;

		return new Point3DBuffer.Float(_buffer);
	}
*/
	public static Point3DBufferX wrap3D(DoubleBuffer _buffer) {
		if(_buffer.capacity() % 3 != 0)
			return null;

		return new Point3DDoubleBufferX(_buffer);
	}

	public static Vector3DDoubleBufferX allocate(int _size) {
		return new Vector3DDoubleBufferX(_size);
	}
/*
	public static Point3DBuffer wrap(FloatBuffer _buffer) {
		if(_buffer.capacity() % 3 != 0)
			return null;

		return new Point3DBuffer.Float(_buffer);
	}
*/
	public static Vector3DBufferX wrap(DoubleBuffer _buffer) {
		if(_buffer.capacity() % 3 != 0)
			return null;

		return new Vector3DDoubleBufferX(_buffer);
	}

}
