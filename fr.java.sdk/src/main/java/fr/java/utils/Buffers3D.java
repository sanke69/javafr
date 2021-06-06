package fr.java.utils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.stream.Collectors;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.algebra.vectors.DoubleVector2D;
import fr.java.sdk.math.algebra.vectors.DoubleVector3D;
import fr.java.sdk.math.algebra.vectors.DoubleVector4D;
import fr.java.utils.primitives.Buffers;

public class Buffers3D extends Buffers {

	public static DoubleBuffer getDoubleBuffer(Point2D... _pts) {
		if(_pts[0] instanceof DoubleVector2D)
			return getDoubleBuffer(Arrays.asList(_pts).stream().map((pt) -> { return (DoubleVector2D) pt; }).collect(Collectors.toList()).toArray(new DoubleVector2D[0]));
		throw new NotYetImplementedException();
	}
	public static DoubleBuffer getDoubleBuffer(Point3D... _pts) {
		if(_pts[0] instanceof DoubleVector3D)
			return getDoubleBuffer(Arrays.asList(_pts).stream().map((pt) -> { return (DoubleVector3D) pt; }).collect(Collectors.toList()).toArray(new DoubleVector3D[0]));
		throw new NotYetImplementedException();
	}
	public static <PT extends DoubleVector2D> DoubleBuffer getDoubleBuffer(PT... _pts) {
		DoubleBuffer fb = allocateDoubleBuffer(2 * _pts.length);

		for(PT pt : _pts)
			fb.put(pt.getBuffer());
		fb.rewind();

		return fb;
	}
	public static <PT extends DoubleVector3D> DoubleBuffer getDoubleBuffer(PT... _pts) {
		DoubleBuffer fb = allocateDoubleBuffer(3 * _pts.length);

		for(PT pt : _pts)
			fb.put(pt.getBuffer());
		fb.rewind();

		return fb;
	}
	public static <PT extends DoubleVector4D> DoubleBuffer getDoubleBuffer(PT... _pts) {
		DoubleBuffer fb = allocateDoubleBuffer(4 * _pts.length);

		for(PT pt : _pts)
			fb.put(pt.getBuffer());
		fb.rewind();

		return fb;
	}

	public static IntBuffer   getIntBuffer(int... _is) {
		IntBuffer ib = IntBuffer.allocate(_is.length);
		for(int i : _is)
			ib.put(i);
		ib.rewind();
		return ib;
	}

	public static <T> Buffer asBuffer(T _pts) {

		if((Object) _pts instanceof byte[]) {
			byte[] bytes = (byte[]) (Object) _pts;
			ByteBuffer bb = ByteBuffer.allocate(bytes.length);
			bb.put(bytes);
			bb.rewind();
			return bb;

		} else if((Object) _pts instanceof short[]) {
			short[] shorts = (short[]) (Object) _pts;
			ShortBuffer sb = ShortBuffer.allocate(shorts.length);
			sb.put(shorts);
			sb.rewind();
			return sb;

		} else if((Object) _pts instanceof int[]) {
			int[] ints = (int[]) (Object) _pts;
			IntBuffer ib = IntBuffer.allocate(ints.length);
			ib.put(ints);
			ib.rewind();
			return ib;

		} else if((Object) _pts instanceof long[]) {
			long[] longs = (long[]) (Object) _pts;
			LongBuffer lb = LongBuffer.allocate(longs.length);
			lb.put(longs);
			lb.rewind();
			return lb;

		} else if((Object) _pts instanceof float[]) {
			float[] floats = (float[]) (Object) _pts;
			FloatBuffer fb = FloatBuffer.allocate(floats.length);
			fb.put(floats);
			fb.rewind();
			return fb;

		} else if((Object) _pts instanceof double[]) {
			double[] doubles = (double[]) (Object) _pts;
			DoubleBuffer db = DoubleBuffer.allocate(doubles.length);
			db.put(doubles);
			db.rewind();
			return db;

		} else if((Object) _pts instanceof DoubleVector2D[]) {
			DoubleVector2D[] vect2ds = (DoubleVector2D[]) (Object) _pts;
			DoubleBuffer db = DoubleBuffer.allocate(2 * vect2ds.length);
			for(DoubleVector2D vect : vect2ds)
				db.put((DoubleBuffer) vect.getBuffer());
			db.rewind();
			return db;

		} else if((Object) _pts instanceof DoubleVector3D[]) {
			DoubleVector3D[] vect3ds = (DoubleVector3D[]) (Object) _pts;
			DoubleBuffer db = DoubleBuffer.allocate(3 * vect3ds.length);
			for(DoubleVector3D vect : vect3ds)
				db.put((DoubleBuffer) vect.getBuffer());
			db.rewind();
			return db;

		} else if((Object) _pts instanceof DoubleVector4D[]) {
			DoubleVector4D[] vect4ds = (DoubleVector4D[]) (Object) _pts;
			DoubleBuffer db = DoubleBuffer.allocate(4 * vect4ds.length);
			for(DoubleVector4D vect : vect4ds)
				db.put((DoubleBuffer) vect.getBuffer());
			db.rewind();
			return db;

		} else if((Object) _pts instanceof Vector2D[]) {
			Vector2D[] pts = (Vector2D[]) _pts;
			if(pts[0] instanceof DoubleVector2D) {
				DoubleBuffer db = DoubleBuffer.allocate(2 * pts.length);
				for(Vector2D vect : pts)
					db.put((DoubleBuffer) ((DoubleVector2D) vect).getBuffer());
				db.rewind();
				return db;

			}
		} else if((Object) _pts instanceof Point2D[]) {
			Point2D[] pts = (Point2D[]) _pts;
			if(pts[0] instanceof DoubleVector2D) {
				DoubleBuffer db = DoubleBuffer.allocate(2 * pts.length);
				for(Point2D vect : pts)
					db.put((DoubleBuffer) ((DoubleVector2D) vect).getBuffer());
				db.rewind();
				return db;

			}
		} else if((Object) _pts instanceof Vector3D[]) {
			Vector3D[] pts = (Vector3D[]) _pts;
			if(pts[0] instanceof DoubleVector3D) {
				DoubleBuffer db = DoubleBuffer.allocate(3 * pts.length);
				for(Vector3D vect : pts)
					db.put((DoubleBuffer) ((DoubleVector3D) vect).getBuffer());
				db.rewind();
				return db;

			}
		} else if((Object) _pts instanceof Point3D[]) {
			Point3D[] pts = (Point3D[]) _pts;
			if(pts[0] instanceof DoubleVector3D) {
				DoubleBuffer db = DoubleBuffer.allocate(3 * pts.length);
				for(Point3D vect : pts)
					db.put((DoubleBuffer) ((DoubleVector3D) vect).getBuffer());
				db.rewind();
				return db;

			}
		}
		throw new IllegalArgumentException("_array is not an array of primitive type!");
	}

}
