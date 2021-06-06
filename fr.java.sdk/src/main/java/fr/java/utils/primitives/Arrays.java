package fr.java.utils.primitives;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Arrays {

    public static byte[]	allocateByteArray(int _size) {
        return new byte[_size];
    }
    public static short[]	allocateShortArray(int _size) {
        return new short[_size];
    }
    public static char[]	allocateCharArray(int _size) {
        return new char[_size];
    }
    public static int[]		allocateIntArray(int _size) {
        return new int[_size];
    }
    public static long[]	allocateLongArray(int _size) {
        return new long[_size];
    }
    public static float[]	allocateFloatArray(int _size) {
        return new float[_size];
    }
    public static double[]	allocateDoubleArray(int _size) {
        return new double[_size];
    }

	public static byte[] 	convertToByteArray(short[] _shorts) {
		byte[] ret = new byte[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (byte) _shorts[i];
		return ret;
	}
	public static byte[] 	convertToByteArray(short[] _shorts, Function<Short, Byte> _converter) {
		byte[] ret = new byte[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _shorts[i] );
		return ret;
	}
	public static byte[] 	convertToByteArray(int[] _ints) {
		byte[] ret = new byte[_ints.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (byte) _ints[i];
		return ret;
	}
	public static byte[] 	convertToByteArray(int[] _ints, Function<Integer, Byte> _converter) {
		byte[] ret = new byte[_ints.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _ints[i] );
		return ret;
	}
	public static byte[] 	convertToByteArray(long[] _longs) {
		byte[] ret = new byte[_longs.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (byte) _longs[i];
		return ret;
	}
	public static byte[] 	convertToByteArray(long[] _longs, Function<Long, Byte> _converter) {
		byte[] ret = new byte[_longs.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _longs[i] );
		return ret;
	}
	public static byte[] 	convertToByteArray(float[] _floats) {
		byte[] ret = new byte[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (byte) _floats[i];
		return ret;
	}
	public static byte[] 	convertToByteArray(float[] _floats, Function<Float, Byte> _converter) {
		byte[] ret = new byte[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _floats[i] );
		return ret;
	}
	public static byte[] 	convertToByteArray(double[] _doubles) {
		byte[] ret = new byte[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (byte) _doubles[i];
		return ret;
	}
	public static byte[] 	convertToByteArray(double[] _doubles, Function<Double, Byte> _converter) {
		byte[] ret = new byte[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _doubles[i] );
		return ret;
	}

	public static short[] 	convertToShortArray(byte[] _bytes) {
		short[] ret = new short[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (short) _bytes[i];
		return ret;
	}
	public static short[] 	convertToShortArray(byte[] _bytes, Function<Byte, Short> _converter) {
		short[] ret = new short[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _bytes[i] );
		return ret;
	}
	public static short[] 	convertToShortArray(int[] _ints) {
		short[] ret = new short[_ints.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (short) _ints[i];
		return ret;
	}
	public static short[] 	convertToShortArray(int[] _ints, Function<Integer, Short> _converter) {
		short[] ret = new short[_ints.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _ints[i] );
		return ret;
	}
	public static short[] 	convertToShortArray(long[] _longs) {
		short[] ret = new short[_longs.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (short) _longs[i];
		return ret;
	}
	public static short[] 	convertToShortArray(long[] _longs, Function<Long, Short> _converter) {
		short[] ret = new short[_longs.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _longs[i] );
		return ret;
	}
	public static short[] 	convertToShortArray(float[] _floats) {
		short[] ret = new short[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (short) _floats[i];
		return ret;
	}
	public static short[] 	convertToShortArray(float[] _floats, Function<Float, Short> _converter) {
		short[] ret = new short[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _floats[i] );
		return ret;
	}
	public static short[] 	convertToShortArray(double[] _doubles) {
		short[] ret = new short[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (short) _doubles[i];
		return ret;
	}
	public static short[] 	convertToShortArray(double[] _doubles, Function<Double, Short> _converter) {
		short[] ret = new short[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _doubles[i] );
		return ret;
	}

	public static int[] 	convertToIntArray(byte[] _bytes) {
		int[] ret = new int[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (int) _bytes[i];
		return ret;
	}
	public static int[] 	convertToIntArray(byte[] _bytes, Function<Byte, Integer> _converter) {
		int[] ret = new int[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _bytes[i] );
		return ret;
	}
	public static int[] 	convertToIntArray(short[] _shorts) {
		int[] ret = new int[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (int) _shorts[i];
		return ret;
	}
	public static int[] 	convertToIntArray(short[] _shorts, Function<Short, Integer> _converter) {
		int[] ret = new int[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _shorts[i] );
		return ret;
	}
	public static int[] 	convertToIntArray(long[] _longs) {
		int[] ret = new int[_longs.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (int) _longs[i];
		return ret;
	}
	public static int[] 	convertToIntArray(long[] _longs, Function<Long, Integer> _converter) {
		int[] ret = new int[_longs.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _longs[i] );
		return ret;
	}
	public static int[] 	convertToIntArray(float[] _floats) {
		int[] ret = new int[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (int) _floats[i];
		return ret;
	}
	public static int[] 	convertToIntArray(float[] _floats, Function<Float, Integer> _converter) {
		int[] ret = new int[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _floats[i] );
		return ret;
	}
	public static int[] 	convertToIntArray(double[] _doubles) {
		int[] ret = new int[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (int) _doubles[i];
		return ret;
	}
	public static int[] 	convertToIntArray(double[] _doubles, Function<Double, Integer> _converter) {
		int[] ret = new int[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _doubles[i] );
		return ret;
	}

	public static long[] 	convertToLongArray(byte[] _bytes) {
		long[] ret = new long[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (long) _bytes[i];
		return ret;
	}
	public static long[] 	convertToLongArray(byte[] _bytes, Function<Byte, Long> _converter) {
		long[] ret = new long[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _bytes[i] );
		return ret;
	}
	public static long[] 	convertToLongArray(short[] _shorts) {
		long[] ret = new long[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (long) _shorts[i];
		return ret;
	}
	public static long[] 	convertToLongArray(short[] _shorts, Function<Short, Long> _converter) {
		long[] ret = new long[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _shorts[i] );
		return ret;
	}
	public static long[] 	convertToLongArray(int[] _ints) {
		long[] ret = new long[_ints.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (long) _ints[i];
		return ret;
	}
	public static long[] 	convertToLongArray(int[] _ints, Function<Integer, Long> _converter) {
		long[] ret = new long[_ints.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _ints[i] );
		return ret;
	}
	public static long[] 	convertToLongArray(float[] _floats) {
		long[] ret = new long[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (long) _floats[i];
		return ret;
	}
	public static long[] 	convertToLongArray(float[] _floats, Function<Float, Long> _converter) {
		long[] ret = new long[_floats.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _floats[i] );
		return ret;
	}
	public static long[] 	convertToLongArray(double[] _doubles) {
		long[] ret = new long[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (long) _doubles[i];
		return ret;
	}
	public static long[] 	convertToLongArray(double[] _doubles, Function<Double, Long> _converter) {
		long[] ret = new long[_doubles.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _doubles[i] );
		return ret;
	}

	public static float[] 	convertToFloatArray(byte[] _bytes) {
		float[] ret = new float[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (float) _bytes[i];
		return ret;
	}
	public static float[] 	convertToFloatArray(byte[] _bytes, Function<Byte, Float> _converter) {
		float[] ret = new float[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _bytes[i] );
		return ret;
	}
	public static float[] 	convertToFloatArray(short[] _shorts) {
		float[] ret = new float[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (float) _shorts[i];
		return ret;
	}
	public static float[] 	convertToFloatArray(short[] _shorts, Function<Short, Float> _converter) {
		float[] ret = new float[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _shorts[i] );
		return ret;
	}
	public static float[] 	convertToFloatArray(int[] _ints) {
		float[] ret = new float[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = (float) _ints[i];
		return ret;
	}
	public static float[] 	convertToFloatArray(int[] _ints, Function<Integer, Float> _converter) {
		float[] ret = new float[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = _converter.apply( _ints[i] );
		return ret;
	}
	public static float[] 	convertToFloatArray(long[] _longs) {
		float[] ret = new float[_longs.length];
		for(int i = 0; i < _longs.length; i++)
			ret[i] = (float) _longs[i];
		return ret;
	}
	public static float[] 	convertToFloatArray(long[] _longs, Function<Long, Float> _converter) {
		float[] ret = new float[_longs.length];
		for(int i = 0; i < _longs.length; i++)
			ret[i] = _converter.apply( _longs[i] );
		return ret;
	}
	public static float[] 	convertToFloatArray(double[] _doubles) {
		float[] ret = new float[_doubles.length];
		for(int i = 0; i < _doubles.length; i++)
			ret[i] = (float) _doubles[i];
		return ret;
	}
	public static float[] 	convertToFloatArray(double[] _doubles, Function<Double, Float> _converter) {
		float[] ret = new float[_doubles.length];
		for(int i = 0; i < _doubles.length; i++)
			ret[i] = _converter.apply( _doubles[i] );
		return ret;
	}

	public static double[] 	convertToDoubleArray(byte[] _bytes) {
		double[] ret = new double[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (double) _bytes[i];
		return ret;
	}
	public static double[] 	convertToDoubleArray(byte[] _bytes, Function<Byte, Double> _converter) {
		double[] ret = new double[_bytes.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _bytes[i] );
		return ret;
	}
	public static double[] 	convertToDoubleArray(short[] _shorts) {
		double[] ret = new double[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = (double) _shorts[i];
		return ret;
	}
	public static double[] 	convertToDoubleArray(short[] _shorts, Function<Short, Double> _converter) {
		double[] ret = new double[_shorts.length];
		for(int i = 0; i < ret.length; i++)
			ret[i] = _converter.apply( _shorts[i] );
		return ret;
	}
	public static double[] 	convertToDoubleArray(int[] _ints) {
		double[] ret = new double[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = (double) _ints[i];
		return ret;
	}
	public static double[] 	convertToDoubleArray(int[] _ints, Function<Integer, Double> _converter) {
		double[] ret = new double[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = _converter.apply( _ints[i] );
		return ret;
	}
	public static double[] 	convertToDoubleArray(long[] _longs) {
		double[] ret = new double[_longs.length];
		for(int i = 0; i < _longs.length; i++)
			ret[i] = (double) _longs[i];
		return ret;
	}
	public static double[] 	convertToDoubleArray(long[] _longs, Function<Long, Double> _converter) {
		double[] ret = new double[_longs.length];
		for(int i = 0; i < _longs.length; i++)
			ret[i] = _converter.apply( _longs[i] );
		return ret;
	}
	public static double[] 	convertToDoubleArray(float[] _floats) {
		double[] ret = new double[_floats.length];
		for(int i = 0; i < _floats.length; i++)
			ret[i] = (double) _floats[i];
		return ret;
	}
	public static double[] 	convertToDoubleArray(float[] _floats, Function<Float, Double> _converter) {
		double[] ret = new double[_floats.length];
		for(int i = 0; i < _floats.length; i++)
			ret[i] = _converter.apply( _floats[i] );
		return ret;
	}


	/**
	 * COPY
	 */

	public static byte[]    copy(byte[] _bytes) {
		byte[] ret = new byte[_bytes.length];
		System.arraycopy(_bytes, 0, ret, 0, ret.length);
		return ret;
	}
	public static short[]   copy(short[] _shorts) {
		short[] ret = new short[_shorts.length];
		System.arraycopy(_shorts, 0, ret, 0, ret.length);
		return ret;
	}
	public static int[]     copy(int[] _ints) {
		int[] ret = new int[_ints.length];
		System.arraycopy(_ints, 0, ret, 0, ret.length);
		return ret;
	}
	public static long[]    copy(long[] _longs) {
		long[] ret = new long[_longs.length];
		System.arraycopy(_longs, 0, ret, 0, ret.length);
		return ret;
	}
	public static float[]   copy(float[] _floats) {
		float[] result = new float[_floats.length];
		System.arraycopy(_floats, 0, result, 0, _floats.length);
		return result;
	}
	public static double[]  copy(double[] _doubles) {
		double[] result = new double[_doubles.length];
		System.arraycopy(_doubles, 0, result, 0, _doubles.length);
		return result;
	}
	@SuppressWarnings("unchecked")
	public static <E> E[]  	copy(E[] _es) {
		E[] result = (E[]) new Object[_es.length];
		System.arraycopy(_es, 0, result, 0, _es.length);
		return result;
	}

	public static byte[]    reverseCopy(byte[] _bytes) {
		if(_bytes.length < 1)
			return _bytes;

		byte[] copy = new byte[_bytes.length];
		for(int i = 0; i <= _bytes.length / 2; i++) {
			byte temp = _bytes[i];
			copy[i] = _bytes[_bytes.length - i - 1];
			copy[_bytes.length - i - 1] = temp;
		}
		return copy;
	}
	public static short[]   reverseCopy(short[] _shorts) {
		if(_shorts.length < 1)
			return _shorts;

		short[] copy = new short[_shorts.length];
		for(int i = 0; i <= _shorts.length / 2; i++) {
			short temp = _shorts[i];
			copy[i] = _shorts[_shorts.length - i - 1];
			copy[_shorts.length - i - 1] = temp;
		}
		return copy;
	}
	public static int[]     reverseCopy(int[] _ints) {
		if(_ints.length < 1)
			return _ints;

		int[] copy = new int[_ints.length];
		for(int i = 0; i <= _ints.length / 2; i++) {
			int temp = _ints[i];
			copy[i] = _ints[_ints.length - i - 1];
			copy[_ints.length - i - 1] = temp;
		}
		return copy;
	}
	public static long[]    reverseCopy(long[] _longs) {
		if(_longs.length < 1)
			return _longs;

		long[] copy = new long[_longs.length];
		for(int i = 0; i <= _longs.length / 2; i++) {
			long temp = _longs[i];
			copy[i] = _longs[_longs.length - i - 1];
			copy[_longs.length - i - 1] = temp;
		}
		return copy;
	}
	public static float[]   reverseCopy(float[] _floats) {
		float[] copy = new float[_floats.length];
		for(int i = 0; i <= _floats.length / 2; i++) {
			float temp = _floats[i];
			copy[i] = _floats[_floats.length - i - 1];
			copy[_floats.length - i - 1] = temp;
		}
		return copy;
	}
	public static double[]  reverseCopy(double[] _doubles) {
		double[] copy = new double[_doubles.length];
		for(int i = 0; i <= _doubles.length / 2; i++) {
			double temp = _doubles[i];
			copy[i] = _doubles[_doubles.length - i - 1];
			copy[_doubles.length - i - 1] = temp;
		}
		return copy;
	}
	@SuppressWarnings("unchecked")
	public static <E> E[]   reverseCopy(E[] e) {
		E[] copy = (E[]) new Object[e.length];
		for(int i = 0; i <= e.length / 2; i++) {
			E temp = e[i];
			copy[i] = e[e.length - i - 1];
			copy[e.length - i - 1] = temp;
		}
		return copy;
	}

	/**
	 * BASIC OPERATOR
	 */

	public static byte 		min(byte[] in) {
		byte min = Byte.MAX_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] < min)
				min = in[i];
		return min;
	}
	public static short 	min(short[] in) {
		short min = Short.MAX_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] < min)
				min = in[i];
		return min;
	}
	public static int 		min(int[] in) {
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] < min)
				min = in[i];
		return min;
	}
	public static long 		min(long[] in) {
		long min = Integer.MAX_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] < min)
				min = in[i];
		return min;
	}
	public static float 	min(float[] in) {
		float min = Integer.MAX_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] < min)
				min = in[i];
		return min;
	}
	public static double 	min(double[] in) {
		double min = Integer.MAX_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] < min)
				min = in[i];
		return min;
	}

	public static int 		argMin(byte[] in) {
		int minIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] < in[minIdx])
				minIdx = i;
		return minIdx;
	}
	public static int 		argMin(short[] in) {
		int minIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] < in[minIdx])
				minIdx = i;
		return minIdx;
	}
	public static int 		argMin(int[] in) {
		int minIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] < in[minIdx])
				minIdx = i;
		return minIdx;
	}
	public static int 		argMin(long[] in) {
		int minIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] < in[minIdx])
				minIdx = i;
		return minIdx;
	}
	public static int 		argMin(float[] in) {
		int minIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] < in[minIdx])
				minIdx = i;
		return minIdx;
	}
	public static int 		argMin(double[] in) {
		int minIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] < in[minIdx])
				minIdx = i;
		return minIdx;
	}

	public static byte 		max(byte[] in) {
		byte max = Byte.MIN_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] > max)
				max = in[i];
		return max;
	}
	public static short 	max(short[] in) {
		short max = Short.MIN_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] > max)
				max = in[i];
		return max;
	}
	public static int 		max(int[] in) {
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] > max)
				max = in[i];
		return max;
	}
	public static long 		max(long[] in) {
		long max = Integer.MIN_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] > max)
				max = in[i];
		return max;
	}
	public static float 	max(float[] in) {
		float max = Integer.MIN_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] > max)
				max = in[i];
		return max;
	}
	public static double 	max(double[] in) {
		double max = Integer.MIN_VALUE;
		for(int i = 0; i < in.length; i++)
			if(in[i] > max)
				max = in[i];
		return max;
	}

	public static int 		argMax(byte[] in) {
		int maxIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] > in[maxIdx])
				maxIdx = i;
		return maxIdx;
	}
	public static int 		argMax(short[] in) {
		int maxIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] > in[maxIdx])
				maxIdx = i;
		return maxIdx;
	}
	public static int 		argMax(int[] in) {
		int maxIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] > in[maxIdx])
				maxIdx = i;
		return maxIdx;
	}
	public static int 		argMax(long[] in) {
		int maxIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] > in[maxIdx])
				maxIdx = i;
		return maxIdx;
	}
	public static int 		argMax(float[] in) {
		int maxIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] > in[maxIdx])
				maxIdx = i;
		return maxIdx;
	}
	public static int 		argMax(double[] in) {
		int maxIdx = 0;
		for(int i = 1; i < in.length; i++)
			if(in[i] > in[maxIdx])
				maxIdx = i;
		return maxIdx;
	}

	public static long 		sum(byte[] _bytes) {
		if(_bytes.length < 1)
			return 0;
		long ret = 0;
		for(int i = 0; i < _bytes.length; i++)
			ret += _bytes[i];
		return ret;
	}
	public static long 		sum(short[] _shorts) {
		if(_shorts.length < 1)
			return 0;
		long ret = 0;
		for(int i = 0; i < _shorts.length; i++)
			ret += _shorts[i];
		return ret;
	}
	public static long 		sum(int[] _ints) {
		if(_ints.length < 1)
			return 0;
		long ret = 0;
		for(int i = 0; i < _ints.length; i++)
			ret += _ints[i];
		return ret;
	}
	public static long 		sum(long[] _longs) {
		if(_longs.length < 1)
			return 0;
		long ret = 0;
		for(int i = 0; i < _longs.length; i++)
			ret += _longs[i];
		return ret;
	}
	public static double	sum(float[] _floats) {
		if(_floats.length < 1)
			return 0;
		double ret = 0;
		for(int i = 0; i < _floats.length; i++)
			ret += _floats[i];
		return ret;
	}
	public static double	sum(double[] _doubles) {
		if(_doubles.length < 1)
			return 0;
		double ret = 0;
		for(int i = 0; i < _doubles.length; i++)
			ret += _doubles[i];
		return ret;
	}

	public static long 		product(byte[] _bytes) {
		if(_bytes.length < 1)
			return 0;
		long ret = 1;
		for(int i = 0; i < _bytes.length; i++)
			ret *= (_bytes[i] & 0xFF);
		return ret;
	}
	public static long 		product(short[] _shorts) {
		if(_shorts.length < 1)
			return 0;
		long ret = 1;
		for(int i = 0; i < _shorts.length; i++)
			ret *= (_shorts[i] & 0xFF);
		return ret;
	}
	public static long 		product(int[] _ints) {
		if(_ints.length < 1)
			return 0;
		long ret = 1;
		for(int i = 0; i < _ints.length; i++)
			ret *= _ints[i];
		return ret;
	}
	public static long 		product(long[] _longs) {
		if(_longs.length < 1)
			return 0;
		long ret = 1;
		for(int i = 0; i < _longs.length; i++)
			ret *= _longs[i];
		return ret;
	}
	public static double	product(float[] _floats) {
		if(_floats.length < 1)
			return 0;
		double ret = 1;
		for(int i = 0; i < _floats.length; i++)
			ret *= _floats[i];
		return ret;
	}
	public static double	product(double[] _doubles) {
		if(_doubles.length < 1)
			return 0;
		double ret = 1;
		for(int i = 0; i < _doubles.length; i++)
			ret *= _doubles[i];
		return ret;
	}


	/**
	 * SUB-ARRAYS
	 */

	public static byte[]    subarray(byte[] _bytes, int _from, int _to) {
		assert (_from < _to);
		assert (_from < _bytes.length && _to < _bytes.length);

		byte[] subarray = new byte[_to - _from];
		System.arraycopy(_bytes, _from, subarray, 0, _to - _from);
		return subarray;
	}
	public static short[]   subarray(short[] _shorts, int _from, int _to) {
		assert (_from < _to);
		assert (_from < _shorts.length && _to < _shorts.length);

		short[] subarray = new short[_to - _from];
		System.arraycopy(_shorts, _from, subarray, 0, _to - _from);
		return subarray;
	}
	public static int[]     subarray(int[] _ints, int _from, int _to) {
		assert (_from < _to);
		assert (_from < _ints.length && _to < _ints.length);

		int[] subarray = new int[_to - _from];
		System.arraycopy(_ints, _from, subarray, 0, _to - _from);
		return subarray;
	}
	public static long[]    subarray(long[] _longs, int _from, int _to) {
		assert (_from < _to);
		assert (_from < _longs.length && _to < _longs.length);

		long[] subarray = new long[_to - _from];
		System.arraycopy(_longs, _from, subarray, 0, _to - _from);
		return subarray;
	}
	public static float[]   subarray(float[] _floats, int _from, int _to) {
		assert (_from < _to);
		assert (_from < _floats.length && _to < _floats.length);

		float[] subarray = new float[_to - _from];
		System.arraycopy(_floats, _from, subarray, 0, _to - _from);
		return subarray;
	}
	public static double[]  subarray(double[] _doubles, int _from, int _to) {
		assert (_from < _to);
		assert (_from < _doubles.length && _to < _doubles.length);

		double[] subarray = new double[_to - _from];
		System.arraycopy(_doubles, _from, subarray, 0, _to - _from);
		return subarray;
	}


	/**
	 * FLATTENING
	 */

	// Assume that _matrix is defined as TYPE[column][rows]
	public static byte[] 	flatten(byte[][] _matrix) {
		int h = _matrix.length;
		int w = _matrix[0].length;

		byte[] flattened = new byte[h * w];
		for(int j = 0; j < h; ++j)
			System.arraycopy(_matrix[j], 0, flattened, j * w, w);
		return flattened;
	}
	public static short[] 	flatten(short[][] _matrix) {
		int h = _matrix.length;
		int w = _matrix[0].length;

		short[] flattened = new short[h * w];
		for(int j = 0; j < h; ++j)
			System.arraycopy(_matrix[j], 0, flattened, j * w, w);
		return flattened;
	}
	public static int[] 	flatten(int[][] _matrix) {
		int h = _matrix.length;
		int w = _matrix[0].length;

		int[] flattened = new int[h * w];
		for(int j = 0; j < h; ++j)
			System.arraycopy(_matrix[j], 0, flattened, j * w, w);
		return flattened;
	}
	public static long[] 	flatten(long[][] _matrix) {
		int h = _matrix.length;
		int w = _matrix[0].length;

		long[] flattened = new long[h * w];
		for(int j = 0; j < h; ++j)
			System.arraycopy(_matrix[j], 0, flattened, j * w, w);
		return flattened;
	}
	public static float[] 	flatten(float[][] _matrix) {
		int h = _matrix.length;
		int w = _matrix[0].length;

		float[] flattened = new float[h * w];
		for(int j = 0; j < h; ++j)
			System.arraycopy(_matrix[j], 0, flattened, j * w, w);
		return flattened;
	}
	public static double[] 	flatten(double[][] _matrix) {
		int h = _matrix.length;
		int w = _matrix[0].length;

		double[] flattened = new double[h * w];
		for(int j = 0; j < h; ++j)
			System.arraycopy(_matrix[j], 0, flattened, j * w, w);
		return flattened;
	}

	// Assume that _matrix is defined as TYPE[slices][column][rows]
	public static byte[] 	flatten(byte[][][] _matrix) {
		int d = _matrix.length;
		int h = _matrix[0].length;
		int w = _matrix[0][0].length;

		byte[] flattened = new byte[d * h * w];
		for(int s = 0; s < d; ++s)
			for(int j = 0; j < h; ++j)
				System.arraycopy(_matrix[s][j], 0, flattened, s * (w * h) + j * w, w);
		return flattened;
	}
	public static short[] 	flatten(short[][][] _matrix) {
		int d = _matrix.length;
		int h = _matrix[0].length;
		int w = _matrix[0][0].length;

		short[] flattened = new short[d * h * w];
		for(int s = 0; s < d; ++s)
			for(int j = 0; j < h; ++j)
				System.arraycopy(_matrix[s][j], 0, flattened, s * (w * h) + j * w, w);
		return flattened;
	}
	public static int[] 	flatten(int[][][] _matrix) {
		int d = _matrix.length;
		int h = _matrix[0].length;
		int w = _matrix[0][0].length;

		int[] flattened = new int[d * h * w];
		for(int s = 0; s < d; ++s)
			for(int j = 0; j < h; ++j)
				System.arraycopy(_matrix[s][j], 0, flattened, s * (w * h) + j * w, w);
		return flattened;
	}
	public static long[] 	flatten(long[][][] _matrix) {
		int d = _matrix.length;
		int h = _matrix[0].length;
		int w = _matrix[0][0].length;

		long[] flattened = new long[d * h * w];
		for(int s = 0; s < d; ++s)
			for(int j = 0; j < h; ++j)
				System.arraycopy(_matrix[s][j], 0, flattened, s * (w * h) + j * w, w);
		return flattened;
	}
	public static float[] 	flatten(float[][][] _matrix) {
		int d = _matrix.length;
		int h = _matrix[0].length;
		int w = _matrix[0][0].length;

		float[] flattened = new float[d * h * w];
		for(int s = 0; s < d; ++s)
			for(int j = 0; j < h; ++j)
				System.arraycopy(_matrix[s][j], 0, flattened, s * (w * h) + j * w, w);
		return flattened;
	}
	public static double[] 	flatten(double[][][] _matrix) {
		int d = _matrix.length;
		int h = _matrix[0].length;
		int w = _matrix[0][0].length;

		double[] flattened = new double[d * h * w];
		for(int s = 0; s < d; ++s)
			for(int j = 0; j < h; ++j)
				System.arraycopy(_matrix[s][j], 0, flattened, s * (w * h) + j * w, w);
		return flattened;
	}

	public static int[]     combine(int[]... _ints) {
		int length = 0;
		for(int i = 0; i < _ints.length; i++)
			length += _ints[i].length;
		int[] ret = new int[length];
		int count = 0;
		for(int[] i : _ints)
			for(int j = 0; j < i.length; j++)
				ret[count++] = i[j];
		return ret;
	}
	public static long[]    combine(long[]... _longs) {
		int length = 0;
		for(int i = 0; i < _longs.length; i++)
			length += _longs[i].length;
		long[] ret = new long[length];
		int count = 0;
		for(long[] i : _longs)
			for(int j = 0; j < i.length; j++)
				ret[count++] = i[j];
		return ret;
	}
	public static float[]   combine(float[]... _floats) {
		int length = 0;
		for(int i = 0; i < _floats.length; i++)
			length += _floats[i].length;
		float[] ret = new float[length];
		int count = 0;
		for(float[] i : _floats)
			for(int j = 0; j < i.length; j++)
				ret[count++] = i[j];
		return ret;
	}
	public static double[]  combine(double[]... _doubles) {
		int length = 0;
		for(int i = 0; i < _doubles.length; i++)
			length += _doubles[i].length;
		double[] ret = new double[length];
		int count = 0;
		for(double[] i : _doubles)
			for(int j = 0; j < i.length; j++)
				ret[count++] = i[j];
		return ret;
	}
	@SuppressWarnings("unchecked")
	public static <E> E[]   combine(E[]... _arrays) {
		int length = 0;
		for(int i = 0; i < _arrays.length; i++)
			length += _arrays[i].length;
		E[] ret = (E[]) Array.newInstance(_arrays[0][0].getClass(), length);
		int count = 0;
		for(E[] i : _arrays)
			for(int j = 0; j < i.length; j++)
				ret[count++] = i[j];
		return ret;
	}

	public static int[]     toArrayInt(List<Integer> list) {
		int[] ret = new int[list.size()];
		for(int i = 0; i < list.size(); i++)
			ret[i] = list.get(i);
		return ret;
	}
	public static long[]    toArrayLong(List<Long> list) {
		long[] ret = new long[list.size()];
		for(int i = 0; i < list.size(); i++)
			ret[i] = list.get(i);
		return ret;
	}
	public static float[]   toArrayFloat(List<Float> list) {
		float[] ret = new float[list.size()];
		for(int i = 0; i < list.size(); i++)
			ret[i] = list.get(i);
		return ret;
	}
	public static double[]  toArrayDouble(List<Double> list) {
		double[] ret = new double[list.size()];
		for(int i = 0; i < list.size(); i++)
			ret[i] = list.get(i);
		return ret;

	}

	public static int 		getLength(Object _array) {
		if(_array instanceof byte[])
			return ((byte[]) _array).length;
		else if(_array instanceof short[])
			return ((short[]) _array).length;
		else if(_array instanceof int[])
			return ((int[]) _array).length;
		else if(_array instanceof long[])
			return ((long[]) _array).length;
		else if(_array instanceof float[])
			return ((float[]) _array).length;
		else if(_array instanceof double[])
			return ((double[]) _array).length;
		else
			throw new IllegalAccessError("Generic must be a Number");
	}
	
    public static int[] sizes(Object array){
        int nDimensions = 0;
        Class<?> c = array.getClass().getComponentType();
        while( c != null ){
            nDimensions++;
            c = c.getComponentType();
        }

        int[] shape = new int[nDimensions];
        Object current = array;
        for( int i=0; i<shape.length-1; i++ ){
            shape[i] = ((Object[])current).length;
            current = ((Object[])current)[0];
        }

        if(current instanceof Object[]) {
            shape[shape.length-1] = ((Object[])current).length;
        }
        else if(current instanceof double[]) {
            shape[shape.length-1] = ((double[])current).length;
        }
        else if(current instanceof float[]) {
            shape[shape.length-1] = ((float[])current).length;
        }
        else if(current instanceof long[]) {
            shape[shape.length-1] = ((long[])current).length;
        }
        else if(current instanceof int[]) {
            shape[shape.length-1] = ((int[])current).length;
        }
        else if(current instanceof byte[]) {
            shape[shape.length-1] = ((byte[])current).length;
        }
        else if(current instanceof char[]) {
            shape[shape.length-1] = ((char[])current).length;
        }
        else if( current instanceof boolean[] ){
            shape[shape.length-1] = ((boolean[])current).length;
        }
        else if( current instanceof short[] ){
            shape[shape.length-1] = ((short[])current).length;
        }
        else
            throw new IllegalStateException("Unknown array type");	//Should never happen
        return shape;
    }
    public static int[] flattenIntArray( Object intArray ){
        if( intArray instanceof int[] ) return (int[])intArray;

        LinkedList<Object> stack = new LinkedList<>();
        stack.push(intArray);

        int[] shape = sizes(intArray);
        int length = (int) product(shape);
        int[] flat = new int[length];
        int count = 0;

        while(!stack.isEmpty()){
            Object current = stack.pop();
            if( current instanceof int[] ){
            	int[] arr = (int[])current;
                for( int i=0; i<arr.length; i++ ) flat[count++] = arr[i];
            } else if(current instanceof Object[] ){
                Object[] o = (Object[])current;
                for( int i=o.length-1; i>=0; i-- ) stack.push(o[i]);
            } else throw new IllegalArgumentException("Base array is not int[]");
        }

        if( count != flat.length ) throw new IllegalArgumentException("Fewer elements than expected. Array is ragged?");
        return flat;
    }
    public static long[] flattenLongArray( Object intArray ){
        if( intArray instanceof long[] ) return (long[])intArray;

        LinkedList<Object> stack = new LinkedList<>();
        stack.push(intArray);

        int[] shape = sizes(intArray);
        int length = (int) product(shape);
        long[] flat = new long[length];
        int count = 0;

        while(!stack.isEmpty()){
            Object current = stack.pop();
            if( current instanceof long[] ){
            	long[] arr = (long[])current;
                for( int i=0; i<arr.length; i++ ) flat[count++] = arr[i];
            } else if(current instanceof Object[] ){
                Object[] o = (Object[])current;
                for( int i=o.length-1; i>=0; i-- ) stack.push(o[i]);
            } else throw new IllegalArgumentException("Base array is not long[]");
        }

        if( count != flat.length ) throw new IllegalArgumentException("Fewer elements than expected. Array is ragged?");
        return flat;
    }
    public static float[] flattenFloatArray( Object floatArray ){
        if( floatArray instanceof float[] ) return (float[])floatArray;

        LinkedList<Object> stack = new LinkedList<>();
        stack.push(floatArray);

        int[] shape = sizes(floatArray);
        int length = (int) product(shape);
        float[] flat = new float[length];
        int count = 0;

        while(!stack.isEmpty()){
            Object current = stack.pop();
            if( current instanceof float[] ){
                float[] arr = (float[])current;
                for( int i=0; i<arr.length; i++ ) flat[count++] = arr[i];
            } else if(current instanceof Object[] ){
                Object[] o = (Object[])current;
                for( int i=o.length-1; i>=0; i-- ) stack.push(o[i]);
            } else throw new IllegalArgumentException("Base array is not float[]");
        }

        if( count != flat.length ) throw new IllegalArgumentException("Fewer elements than expected. Array is ragged?");
        return flat;
    }
    public static double[] flattenDoubleArray( Object doubleArray ){
        if( doubleArray instanceof double[] ) return (double[])doubleArray;

        LinkedList<Object> stack = new LinkedList<>();
        stack.push(doubleArray);

        int[] shape = sizes(doubleArray);
        int length = (int) product(shape);
        double[] flat = new double[length];
        int count = 0;

        while(!stack.isEmpty()){
            Object current = stack.pop();
            if( current instanceof double[] ){
                double[] arr = (double[])current;
                for( int i=0; i<arr.length; i++ ) flat[count++] = arr[i];
            } else if(current instanceof Object[] ){
                Object[] o = (Object[])current;
                for( int i=o.length-1; i>=0; i-- ) stack.push(o[i]);
            } else throw new IllegalArgumentException("Base array is not double[]");
        }

        if( count != flat.length ) throw new IllegalArgumentException("Fewer elements than expected. Array is ragged?");
        return flat;
    }
/*
	public static int sum(List<Integer> _list) {
		if(_list.size() < 1)
			return 0;
		int ret = 0;
		for(int i = 0; i < _list.size(); i++)
			ret += _list.get(i);
		return ret;
	}*/

	public static int[] plus(int[] _ints, int _add) {
		int[] ret = new int[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = _ints[i] + _add;
		return ret;
	}
	public static int[] plus(int[] _ints, int[] _adds) {
		if(_ints.length != _adds.length)
			throw new IllegalArgumentException("Both arrays must have the same length");
		int[] ret = new int[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = _ints[i] + _adds[i];
		return ret;
	}

	public static double[] plus(double[] _values, double _add) {
		double[] ret = new double[_values.length];
		for(int i = 0; i < _values.length; i++)
			ret[i] = _values[i] + _add;
		return ret;
	}
	public static double[] plus(double[] _values, double[] _adds) {
		if(_values.length != _adds.length)
			throw new IllegalArgumentException("Both arrays must have the same length");
		double[] ret = new double[_values.length];
		for(int i = 0; i < _values.length; i++)
			ret[i] = _values[i] + _adds[i];
		return ret;
	}

	public static double[] minus(double[] _values, double _minus) {
		double[] ret = new double[_values.length];
		for(int i = 0; i < _values.length; i++)
			ret[i] = _values[i] - _minus;
		return ret;
	}
	public static double[] minus(double[] _values, double[] _minus) {
		if(_values.length != _minus.length)
			throw new IllegalArgumentException("Both arrays must have the same length");
		double[] ret = new double[_values.length];
		for(int i = 0; i < _values.length; i++)
			ret[i] = _values[i] - _minus[i];
		return ret;
	}

	public static int[] times(int[] _ints, int _mult) {
		int[] ret = new int[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = _ints[i] * _mult;
		return ret;
	}
	public static int[] times(int[] _ints, int[] _mults) {
		assert _ints.length == _mults.length : "_ints and _mults must be the same length";
		int[] ret = new int[_ints.length];
		for(int i = 0; i < _ints.length; i++)
			ret[i] = _ints[i] * _mults[i];
		return ret;
	}

	public static int dotProduct(List<Integer> xs, List<Integer> ys) {
		int result = 0;
		int n = xs.size();

		if(ys.size() != n)
			throw new IllegalArgumentException("Different array sizes");

		for(int i = 0; i < n; i++) {
			result += xs.get(i) * ys.get(i);
		}
		return result;
	}
	public static int dotProduct(int[] xs, int[] ys) {
		int result = 0;
		int n = xs.length;

		if(ys.length != n)
			throw new IllegalArgumentException("Different array sizes");

		for(int i = 0; i < n; i++) {
			result += xs[i] * ys[i];
		}
		return result;
	}

	public static long dotProductLong(List<Integer> xs, List<Integer> ys) {
		long result = 0;
		int n = xs.size();

		if(ys.size() != n)
			throw new IllegalArgumentException("Different array sizes");

		for(int i = 0; i < n; i++) {
			result += (long) xs.get(i) * ys.get(i);
		}
		return result;
	}
	public static long dotProductLong(int[] xs, int[] ys) {
		long result = 0;
		int n = xs.length;

		if(ys.length != n)
			throw new IllegalArgumentException("Different array sizes");

		for(int i = 0; i < n; i++) {
			result += (long) xs[i] * ys[i];
		}
		return result;
	}

	public static boolean equals(float[] data, double[] data2) {
		if(data.length != data2.length)
			return false;
		for(int i = 0; i < data.length; i++) {
			double equals = Math.abs(data2[i] - data[i]);
			if(equals > 1e-6)
				return false;
		}
		return true;
	}
	

	public static byte[] read(int length, DataInputStream dis) throws IOException {
		byte[] ret = new byte[length];
		for(int i = 0; i < length; i++)
			ret[i] = dis.readByte();
		return ret;
	}
	public static short[] readShort(int length, DataInputStream dis) throws IOException {
		short[] ret = new short[length];
		for(int i = 0; i < length; i++)
			ret[i] = dis.readShort();
		return ret;
	}
	public static int[] readInt(int length, DataInputStream dis) throws IOException {
		int[] ret = new int[length];
		for(int i = 0; i < length; i++)
			ret[i] = dis.readInt();
		return ret;
	}
	public static long[] readLong(int length, DataInputStream dis) throws IOException {
		long[] ret = new long[length];
		for(int i = 0; i < length; i++)
			ret[i] = dis.readInt();
		return ret;
	}
	public static float[] readFloat(int length, DataInputStream dis) throws IOException {
		float[] ret = new float[length];
		for(int i = 0; i < length; i++)
			ret[i] = dis.readFloat();
		return ret;
	}
	public static double[] readDouble(int length, DataInputStream dis) throws IOException {
		double[] ret = new double[length];
		for(int i = 0; i < length; i++)
			ret[i] = dis.readDouble();
		return ret;
	}

	public static void write(double[] data, DataOutputStream dos) throws IOException {
		for(int i = 0; i < data.length; i++)
			dos.writeDouble(data[i]);
	}
	public static void write(float[] data, DataOutputStream dos) throws IOException {
		for(int i = 0; i < data.length; i++)
			dos.writeFloat(data[i]);
	}
	

}
