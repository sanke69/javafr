package fr.utils.maths;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import fr.java.lang.exceptions.NotYetImplementedException;

public class Bytes {

	public static class HexaEncoder {

		/**
		 * Used building output as Hex
		 */
		private static final char[] DIGITS = {
				'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};

		/**
		 * Converts an array of bytes into an array of characters representing the hexidecimal values of each byte in order.
		 * The returned array will be double the length of the passed array, as it takes two characters to represent any
		 * given byte.
		 *
		 * @param data
		 * a byte[] to convert to Hex characters
		 * @return A char[] containing hexidecimal characters
		  */
		public static char[] encodeHex(byte[] data) {

			int l = data.length;

			char[] out = new char[l << 1];

			// two characters form the hex value.
			for(int i = 0, j = 0; i < l; i++) {
				out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
				out[j++] = DIGITS[0x0F & data[i]];
			}

			return out;
		}

		/**
		 * Converts an array of characters representing hexidecimal values into an
		 * array of bytes of those same values. The returned array will be half the
		 * length of the passed array, as it takes two characters to represent any
		 * given byte. An exception is thrown if the passed char array has an odd
		 * number of elements.
		 *
		* @param data An array of characters containing hexidecimal digits
		 * @return A byte array containing binary data decoded from
		* the supplied char array.
		* @throws DecoderException Thrown if an odd number or illegal of characters
		 * is supplied
		*/
		public static byte[] decodeHex(char[] data) {

			int len = data.length;

			if((len & 0x01) != 0) {
				throw new IllegalArgumentException("Odd number of characters.");
			}

			byte[] out = new byte[len >> 1];
			// two characters form the hex value.
			for(int i = 0, j = 0; j < len; i++) {
				int f = toDigit(data[j], j) << 4;
				j++;
				f = f | toDigit(data[j], j);
				j++;
				out[i] = (byte) (f & 0xFF);
			}

			return out;
		}

		/**
	      * Converts a String or an array of bytes into an array of characters representing the
	      * hexidecimal values of each byte in order. The returned array will be
	      * double the length of the passed String or array, as it takes two characters to
	      * represent any given byte.
	      *
	      * @param object a String, or byte[] to convert to Hex characters
	      * @return A char[] containing hexidecimal characters
	      * @throws EncoderException Thrown if the given object is not a String or byte[]
	      * @see #encodeHex(byte[])
	      */
	     public Object encode(Object object) {
	         try {
	             byte[] byteArray = object instanceof String  ? ((String ) object).getBytes() : (byte[]) object;
	             return encodeHex(byteArray);
	         } catch (ClassCastException  e) {
	             throw new IllegalArgumentException(e.getMessage());
	         }
	     }

		/**
	      * Converts an array of bytes into an array of bytes for the characters representing the
	      * hexidecimal values of each byte in order. The returned array will be
	      * double the length of the passed array, as it takes two characters to
	      * represent any given byte.
	      *
	      * @param array a byte[] to convert to Hex characters
	      * @return A byte[] containing the bytes of the hexidecimal characters
	      * @see #encodeHex(byte[])
	      */
	     public byte[] encode(byte[] array) {
	         return new String (encodeHex(array)).getBytes();
	     }

	 	/**
	       * Converts a String or an array of character bytes representing hexidecimal values into an
	       * array of bytes of those same values. The returned array will be half the
	       * length of the passed String or array, as it takes two characters to represent any
	       * given byte. An exception is thrown if the passed char array has an odd
	       * number of elements.
	       *
	       * @param object A String or, an array of character bytes containing hexidecimal digits
	       * @return A byte array containing binary data decoded from
	       * the supplied byte array (representing characters).
	       * @throws DecoderException Thrown if an odd number of characters is supplied
	       * to this function or the object is not a String or char[]
	       * @see #decodeHex(char[])
	       */
	      public Object decode(Object  object)  {
	          try {
	              char[] charArray = object instanceof String ? ((String) object).toCharArray() : (char[]) object;
	              return decodeHex(charArray);
	          } catch (ClassCastException  e) {
	              throw new IllegalArgumentException(e.getMessage());
	          }
	      }

		/**
	      * Converts an array of character bytes representing hexidecimal values into an
	      * array of bytes of those same values. The returned array will be half the
	      * length of the passed array, as it takes two characters to represent any
	      * given byte. An exception is thrown if the passed char array has an odd
	      * number of elements.
	      *
	      * @param array An array of character bytes containing hexidecimal digits
	      * @return A byte array containing binary data decoded from
	      * the supplied byte array (representing characters).
	      * @throws DecoderException Thrown if an odd number of characters is supplied
	      * to this function
	      * @see #decodeHex(char[])
	      */
	     public byte[] decode(byte[] array) {
	         return decodeHex(new String (array).toCharArray());
	     }

	 	/**
	 	 * Converts a hexadecimal character to an integer.
	 	 *
	 	  * @param ch A character to convert to an integer digit
	 	  * @param index The index of the character in the source
	 	 * @return An integer
	 	  * @throws DecoderException Thrown if ch is an illegal hex character
	 	  */
	 	protected static int toDigit(char ch, int index)  {
	 		int digit = Character.digit(ch, 16);
	 		if(digit == -1) {
	 			throw new IllegalArgumentException("Illegal hexadecimal charcter " + ch + " at index " + index);
	 		}
	 		return digit;
	 	}

	}


	public static byte[] decodeHex(String data, Charset _charset) {
		if(_charset != StandardCharsets.UTF_8)
			throw new NotYetImplementedException();

		int len = data.length();

		if((len & 0x01) != 0) {
			throw new IllegalArgumentException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];
		// two characters form the hex value.
		for(int i = 0, j = 0; j < len; i++) {
			int f = HexaEncoder.toDigit(data.charAt(j), j) << 4;
			j++;
			f = f | HexaEncoder.toDigit(data.charAt(j), j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}
	
	private static final class FastByteArrayOutputStream extends ByteArrayOutputStream {
		/**
		 * Writes the contents of the internal buffer to the given array
		 * starting at the given offset. Assumes the array has space to hold
		 * count bytes.
		 */
		void writeTo(byte[] b, int off) {
			System.arraycopy(buf, 0, b, off, count);
		}
	}

	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static byte[] 	toByteArray(short[] _shorts) {
		int times = Short.SIZE / Byte.SIZE;
		byte[] bytes = new byte[_shorts.length * times];
		for(int i = 0; i < _shorts.length; i++) {
			ByteBuffer.wrap(bytes, i * times, times).putShort(_shorts[i]);
		}
		return bytes;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static byte[] 	toByteArray(int[] _ints) {
		int times = Integer.SIZE / Byte.SIZE;
		byte[] bytes = new byte[_ints.length * times];
		for(int i = 0; i < _ints.length; i++) {
			ByteBuffer.wrap(bytes, i * times, times).putInt(_ints[i]);
		}
		return bytes;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static byte[] 	toByteArray(long[] _longs) {
		int times = Long.SIZE / Byte.SIZE;
		byte[] bytes = new byte[_longs.length * times];
		for(int i = 0; i < _longs.length; i++) {
			ByteBuffer.wrap(bytes, i * times, times).putLong(_longs[i]);
		}
		return bytes;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static byte[] 	toByteArray(float[] _floats) {
		int times = Float.SIZE / Byte.SIZE;
		byte[] bytes = new byte[_floats.length * times];
		for(int i = 0; i < _floats.length; i++) {
			ByteBuffer.wrap(bytes, i * times, times).putFloat(_floats[i]);
		}
		return bytes;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static byte[] 	toByteArray(double[] _doubles) {
		int times = Double.SIZE / Byte.SIZE;
		byte[] bytes = new byte[_doubles.length * times];
		for(int i = 0; i < _doubles.length; i++) {
			ByteBuffer.wrap(bytes, i * times, times).putDouble(_doubles[i]);
		}
		return bytes;
	}

	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static short[] 	toShortArray(byte[] _bytes) {
/**/
		short[] shorts = new short[_bytes.length / Short.BYTES];
		ByteBuffer.wrap(_bytes).asShortBuffer().get(shorts);
/*/
		int times = Short.SIZE / Byte.SIZE;
		short[] shorts = new short[_bytes.length / times];
		for(int i = 0; i < shorts.length; i++) {
			shorts[i] = ByteBuffer.wrap(_bytes, i * times, times)/ *.order(ByteOrder.LITTLE_ENDIAN)* /.getShort();
		}
/**/
		return shorts;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static int[] 	toIntArray(byte[] _bytes) {
/**/
		int[] ints = new int[_bytes.length / Integer.BYTES];
		ByteBuffer.wrap(_bytes).asIntBuffer().get(ints);
/*/
		int times = Integer.SIZE / Byte.SIZE;
		int[] ints = new int[_bytes.length / times];
		for(int i = 0; i < ints.length; i++) {
			ints[i] = ByteBuffer.wrap(_bytes, i * times, times).getInt();
		}
/**/
		return ints;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static long[] 	toLongArray(byte[] _bytes) {
/**/
		long[] longs = new long[_bytes.length / Long.BYTES];
		ByteBuffer.wrap(_bytes).asLongBuffer().get(longs);
/*/
		int times = Long.SIZE / Byte.SIZE;
		long[] longs = new long[_bytes.length / times];
		for(int i = 0; i < longs.length; i++) {
			longs[i] = ByteBuffer.wrap(_bytes, i * times, times).getLong();
		}
/**/
		return longs;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static float[] 	toFloatArray(byte[] _bytes) {
/**/
		float[] floats = new float[_bytes.length / Float.BYTES];
		ByteBuffer.wrap(_bytes).asFloatBuffer().get(floats);
/*/
		int times = Float.SIZE / Byte.SIZE;
		float[] floats = new float[_bytes.length / times];
		for(int i = 0; i < floats.length; i++) {
			floats[i] = ByteBuffer.wrap(_bytes, i * times, times).getFloat();
		}
/**/
		return floats;
	}
	/**
	 * Preserve the raw information, just made a primitive conversion
	 */
	public static double[] 	toDoubleArray(byte[] _bytes) {
/**/
		double[] doubles = new double[_bytes.length / Double.BYTES];
		ByteBuffer.wrap(_bytes).asDoubleBuffer().get(doubles);
/*/
		int times = Double.SIZE / Byte.SIZE;
		double[] doubles = new double[_bytes.length / times];
		for(int i = 0; i < doubles.length; i++) {
			doubles[i] = ByteBuffer.wrap(_bytes, i * times, times).getDouble();
		}
/**/
		return doubles;
	}

	public static short 	swap(short value) {
		int b1 = value & 0xff;
		int b2 = (value >> 8) & 0xff;

		return (short) (b1 << 8 | b2 << 0);
	}
	public static int 		swap(int value) {
		int b1 = (value >> 0) & 0xff;
		int b2 = (value >> 8) & 0xff;
		int b3 = (value >> 16) & 0xff;
		int b4 = (value >> 24) & 0xff;

		return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
	}
	public static long 		swap(long value) {
		long b1 = (value >> 0) & 0xff;
		long b2 = (value >> 8) & 0xff;
		long b3 = (value >> 16) & 0xff;
		long b4 = (value >> 24) & 0xff;
		long b5 = (value >> 32) & 0xff;
		long b6 = (value >> 40) & 0xff;
		long b7 = (value >> 48) & 0xff;
		long b8 = (value >> 56) & 0xff;

		return b1 << 56 | b2 << 48 | b3 << 40 | b4 << 32 | b5 << 24 | b6 << 16 | b7 << 8 | b8 << 0;
	}
	public static float 	swap(float value) {
		int intValue = Float.floatToIntBits(value);
		intValue = swap(intValue);
		return Float.intBitsToFloat(intValue);
	}
	public static double 	swap(double value) {
		long longValue = Double.doubleToLongBits(value);
		longValue = swap(longValue);
		return Double.longBitsToDouble(longValue);
	}

	public static void 		swap(short[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = swap(array[i]);
	}
	public static void 		swap(int[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = swap(array[i]);
	}
	public static void 		swap(long[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = swap(array[i]);
	}
	public static void 		swap(float[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = swap(array[i]);
	}
	public static void 		swap(double[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = swap(array[i]);
	}

	public static void memset(byte[] bytes, int value) {
//		Arrays.fill(bytes, (byte)value);
		byte b = (byte) value;
		for(int i = 0; i < bytes.length; i++)
			bytes[i] = b;
	}

	public static int makeInt(byte b1, byte b2) {
		return ((b1 << 0) & 0x000000FF) +
				((b2 << 8) & 0x0000FF00);
	}
	public static int makeInt(byte b1, byte b2, byte b3, byte b4) {
		return ((b1 << 0) & 0x000000FF) +
				((b2 << 8) & 0x0000FF00) +
				((b3 << 16) & 0x00FF0000) +
				((b4 << 24) & 0xFF000000);
	}
	public static int makeInt(byte[] bytes) {
		int value = 0;
		for(int i = 0; i < bytes.length; ++i) {
			value = value << 8;
			value += bytes[i] & 0xff;
		}
		return value;
	}

	public static byte[] makeByte2(int i) {
		byte[] b = {
				(byte) (i & 0x000000FF),
				(byte) ((i >> 8) & 0x000000FF)
		};
		return b;
	}
	public static byte[] makeByte4(int i) {
		byte[] b = {
				(byte) (i & 0x000000FF),
				(byte) ((i >> 8) & 0x000000FF),
				(byte) ((i >> 16) & 0x000000FF),
				(byte) ((i >> 24) & 0x000000FF)
		};
		return b;
	}

	
	public static short[] asShorts(byte[] _buffer) {
		assert(_buffer.length % Short.BYTES == 0);

		short[] result = new short[_buffer.length / Short.BYTES];
		ByteBuffer buffer = ByteBuffer.wrap(_buffer);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.asShortBuffer().get(result);
/*		
		ByteBuffer buffer = ByteBuffer.wrap(_buffer);
		for(int i = 0; i < result.length; ++i)
			result[i] = buffer.getShort();
*/
		return result;
	}
	public static int[] asInts(byte[] _buffer) {
		assert(_buffer.length % Integer.BYTES == 0);

		int[] result = new int[_buffer.length / Integer.BYTES];
		ByteBuffer.wrap(_buffer).asIntBuffer().get(result);
/*
		ByteBuffer buffer = ByteBuffer.wrap(_buffer);
		for(int i = 0; i < result.length; ++i)
			result[i] = buffer.getInt();
*/
		return result;
	}
	public static long[] asLongs(byte[] _buffer) {
		assert(_buffer.length % Long.BYTES == 0);

		long[]     result = new long[_buffer.length / Long.BYTES];
		ByteBuffer.wrap(_buffer).asLongBuffer().get(result);
/*
		ByteBuffer buffer = ByteBuffer.wrap(_buffer);
		for(int i = 0; i < result.length; ++i)
			result[i] = buffer.getShort();
*/
		return result;
	}
	
	public static float[] asFloats(byte[] _buffer) {
		assert(_buffer.length % Float.BYTES == 0);

		float[]    result = new float[_buffer.length / Float.BYTES];
		ByteBuffer buffer = ByteBuffer.wrap(_buffer);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.asFloatBuffer().get(result);

		return result;
	}
	public static double[] asDoubles(byte[] _buffer) {
		assert(_buffer.length % Double.BYTES == 0);

		double[]   result = new double[_buffer.length / Double.BYTES];
		ByteBuffer.wrap(_buffer).asDoubleBuffer().get(result);
/*
		ByteBuffer buffer = ByteBuffer.wrap(_buffer);
		for(int i = 0; i < result.length; ++i)
			result[i] = buffer.getShort();
*/
		return result;
	}

	public static long copy(InputStream from, OutputStream to) throws IOException {
	    byte[] buf = new byte[0x1000]; // 4K
	    long total = 0;
	    while (true) {
	      int r = from.read(buf);
	      if (r == -1) {
	        break;
	      }
	      to.write(buf, 0, r);
	      total += r;
	    }
	    return total;
	  }
	  
	public static byte[] toByteArray_fast(InputStream in) {
		int    expectedSize = -1;
		byte[] result = null;
		try {
			expectedSize = in.available();

			byte[] bytes = new byte[expectedSize];
			int remaining = expectedSize;
			while(remaining > 0) {
				int off = expectedSize - remaining;
				int read = in.read(bytes, off, remaining);
				if(read == -1)
					// end of stream before reading expectedSize bytes
					// just return the bytes read so far
					return Arrays.copyOf(bytes, off);
				remaining -= read;
			}
	
			// bytes is now full
			int b = in.read();
			if(b == -1) {
				return bytes;
			}

			// the stream was longer, so read the rest normally
			FastByteArrayOutputStream out = new FastByteArrayOutputStream();
			out.write(b); // write the byte we read when testing for end of stream
			copy(in, out);
	
			result = new byte[bytes.length + out.size()];
			System.arraycopy(bytes, 0, result, 0, bytes.length);
			out.writeTo(result, bytes.length);

		} catch(IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static byte[] toByteArray(InputStream inputStream) {
		try {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte buffer[] = new byte[8192];
	        while (true) {
	            int read = inputStream.read(buffer);
	            if (read == -1) {
	                break;
	            }
	            baos.write(buffer, 0, read);
	        }
	        return baos.toByteArray();
		} catch(IOException e) {
			throw new IllegalArgumentException(e);
		}
	    }
}
