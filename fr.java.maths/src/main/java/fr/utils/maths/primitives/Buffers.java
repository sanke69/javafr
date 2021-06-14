package fr.utils.maths.primitives;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.function.Function;

public class Buffers {

    public static ByteBuffer	allocateByteBuffer(int _size) {
        return ByteBuffer.allocateDirect(_size).order(ByteOrder.nativeOrder());
    }
    public static ShortBuffer	allocateShortBuffer(int _size) {
        return allocateByteBuffer(_size << 1).asShortBuffer();
    }
    public static CharBuffer	allocateCharBuffer(int _size) {
        return allocateByteBuffer(_size << 1).asCharBuffer();
    }
    public static IntBuffer		allocateIntBuffer(int _size) {
        return allocateByteBuffer(_size << 2).asIntBuffer();
    }
    public static LongBuffer	allocateLongBuffer(int _size) {
        return allocateByteBuffer(_size << 3).asLongBuffer();
    }
    public static FloatBuffer	allocateFloatBuffer(int _size) {
        return allocateByteBuffer(_size << 2).asFloatBuffer();
    }
    public static DoubleBuffer	allocateDoubleBuffer(int _size) {
        return allocateByteBuffer(_size << 3).asDoubleBuffer();
    }

	public static ByteBuffer 	convertToByteBuffer(Buffer _buffer) {
		if(_buffer instanceof ByteBuffer)
			return (ByteBuffer) _buffer;
		else if(_buffer instanceof ShortBuffer)
			return convertToByteBuffer((ShortBuffer) _buffer);
		else if(_buffer instanceof IntBuffer)
			return convertToByteBuffer((IntBuffer) _buffer);
		else if(_buffer instanceof LongBuffer)
			return convertToByteBuffer((LongBuffer) _buffer);
		else if(_buffer instanceof FloatBuffer)
			return convertToByteBuffer((FloatBuffer) _buffer);
		else if(_buffer instanceof DoubleBuffer)
			return convertToByteBuffer((DoubleBuffer) _buffer);
		throw new IllegalArgumentException();
	}
	public static ByteBuffer 	convertToByteBuffer(ShortBuffer _buffer) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put((byte) _buffer.get());
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(ShortBuffer _buffer, Function<Short, Byte> _converter) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put(_converter.apply( _buffer.get() ));
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(IntBuffer _buffer) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put((byte) _buffer.get());
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(IntBuffer _buffer, Function<Integer, Byte> _converter) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put(_converter.apply( _buffer.get() ));
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(LongBuffer _buffer) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put((byte) _buffer.get());
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(LongBuffer _buffer, Function<Long, Byte> _converter) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put(_converter.apply( _buffer.get() ));
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(FloatBuffer _buffer) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put((byte) _buffer.get());
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(FloatBuffer _buffer, Function<Float, Byte> _converter) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put(_converter.apply( _buffer.get() ));
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(DoubleBuffer _buffer) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put((byte) _buffer.get());
		return byteBuffer;
	}
	public static ByteBuffer 	convertToByteBuffer(DoubleBuffer _buffer, Function<Double, Byte> _converter) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			byteBuffer.put(_converter.apply( _buffer.get() ));
		return byteBuffer;
	}

	public static ShortBuffer 	convertToShortBuffer(Buffer _buffer) {
		if(_buffer instanceof ShortBuffer)
			return (ShortBuffer) _buffer;
		else if(_buffer instanceof ByteBuffer)
			return convertToShortBuffer((ByteBuffer) _buffer);
		else if(_buffer instanceof IntBuffer)
			return convertToShortBuffer((IntBuffer) _buffer);
		else if(_buffer instanceof LongBuffer)
			return convertToShortBuffer((LongBuffer) _buffer);
		else if(_buffer instanceof FloatBuffer)
			return convertToShortBuffer((FloatBuffer) _buffer);
		else if(_buffer instanceof DoubleBuffer)
			return convertToShortBuffer((DoubleBuffer) _buffer);
		throw new IllegalArgumentException();
	}
	public static ShortBuffer 	convertToShortBuffer(ByteBuffer _buffer) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put((short) _buffer.get());
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(ByteBuffer _buffer, Function<Byte, Short> _converter) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put(_converter.apply( _buffer.get() ));
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(IntBuffer _buffer) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put((short) _buffer.get());
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(IntBuffer _buffer, Function<Integer, Short> _converter) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put(_converter.apply( _buffer.get() ));
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(LongBuffer _buffer) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put((short) _buffer.get());
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(LongBuffer _buffer, Function<Long, Short> _converter) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put(_converter.apply( _buffer.get() ));
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(FloatBuffer _buffer) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put((short) _buffer.get());
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(FloatBuffer _buffer, Function<Float, Short> _converter) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put(_converter.apply( _buffer.get() ));
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(DoubleBuffer _buffer) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put((short) _buffer.get(i));
		return shortBuffer;
	}
	public static ShortBuffer 	convertToShortBuffer(DoubleBuffer _buffer, Function<Double, Short> _converter) {
		ShortBuffer shortBuffer = ShortBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			shortBuffer.put(_converter.apply( _buffer.get() ));
		return shortBuffer;
	}

	public static IntBuffer 	convertToIntBuffer(Buffer _buffer) {
		if(_buffer instanceof IntBuffer)
			return (IntBuffer) _buffer;
		else if(_buffer instanceof ByteBuffer)
			return convertToIntBuffer((ByteBuffer) _buffer);
		else if(_buffer instanceof ShortBuffer)
			return convertToIntBuffer((ShortBuffer) _buffer);
		else if(_buffer instanceof LongBuffer)
			return convertToIntBuffer((LongBuffer) _buffer);
		else if(_buffer instanceof FloatBuffer)
			return convertToIntBuffer((FloatBuffer) _buffer);
		else if(_buffer instanceof DoubleBuffer)
			return convertToIntBuffer((DoubleBuffer) _buffer);
		throw new IllegalArgumentException();
	}
	public static IntBuffer 	convertToIntBuffer(ByteBuffer _buffer) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put((int) _buffer.get());
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(ByteBuffer _buffer, Function<Byte, Integer> _converter) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put(_converter.apply( _buffer.get() ));
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(ShortBuffer _buffer) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put((int) _buffer.get());
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(ShortBuffer _buffer, Function<Short, Integer> _converter) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put(_converter.apply( _buffer.get() ));
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(LongBuffer _buffer) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put((int) _buffer.get());
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(LongBuffer _buffer, Function<Long, Integer> _converter) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put(_converter.apply( _buffer.get() ));
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(FloatBuffer _buffer) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put((int) _buffer.get());
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(FloatBuffer _buffer, Function<Float, Integer> _converter) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put(_converter.apply( _buffer.get() ));
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(DoubleBuffer _buffer) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put((int) _buffer.get());
		return intBuffer;
	}
	public static IntBuffer 	convertToIntBuffer(DoubleBuffer _buffer, Function<Double, Integer> _converter) {
		IntBuffer intBuffer = IntBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			intBuffer.put(_converter.apply( _buffer.get() ));
		return intBuffer;
	}

	public static LongBuffer 	convertToLongBuffer(Buffer _buffer) {
		if(_buffer instanceof LongBuffer)
			return (LongBuffer) _buffer;
		else if(_buffer instanceof ByteBuffer)
			return convertToLongBuffer((ByteBuffer) _buffer);
		else if(_buffer instanceof ShortBuffer)
			return convertToLongBuffer((ShortBuffer) _buffer);
		else if(_buffer instanceof IntBuffer)
			return convertToLongBuffer((IntBuffer) _buffer);
		else if(_buffer instanceof FloatBuffer)
			return convertToLongBuffer((FloatBuffer) _buffer);
		else if(_buffer instanceof DoubleBuffer)
			return convertToLongBuffer((DoubleBuffer) _buffer);
		throw new IllegalArgumentException();
	}
	public static LongBuffer 	convertToLongBuffer(ByteBuffer _buffer) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put((long) _buffer.get());
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(ByteBuffer _buffer, Function<Byte, Long> _converter) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put(_converter.apply( _buffer.get() ));
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(ShortBuffer _buffer) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put((long) _buffer.get());
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(ShortBuffer _buffer, Function<Short, Long> _converter) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put(_converter.apply( _buffer.get() ));
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(IntBuffer _buffer) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put((long) _buffer.get());
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(IntBuffer _buffer, Function<Integer, Long> _converter) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put(_converter.apply( _buffer.get() ));
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(FloatBuffer _buffer) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put((long) _buffer.get());
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(FloatBuffer _buffer, Function<Float, Long> _converter) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put(_converter.apply( _buffer.get() ));
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(DoubleBuffer _buffer) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put((long) _buffer.get());
		return longBuffer;
	}
	public static LongBuffer 	convertToLongBuffer(DoubleBuffer _buffer, Function<Double, Long> _converter) {
		LongBuffer longBuffer = LongBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			longBuffer.put(_converter.apply( _buffer.get() ));
		return longBuffer;
	}

	public static FloatBuffer 	convertToFloatBuffer(Buffer _buffer) {
		if(_buffer instanceof FloatBuffer)
			return (FloatBuffer) _buffer;
		else if(_buffer instanceof ByteBuffer)
			return convertToFloatBuffer((ByteBuffer) _buffer);
		else if(_buffer instanceof ShortBuffer)
			return convertToFloatBuffer((ShortBuffer) _buffer);
		else if(_buffer instanceof IntBuffer)
			return convertToFloatBuffer((IntBuffer) _buffer);
		else if(_buffer instanceof LongBuffer)
			return convertToFloatBuffer((LongBuffer) _buffer);
		else if(_buffer instanceof DoubleBuffer)
			return convertToFloatBuffer((DoubleBuffer) _buffer);
		throw new IllegalArgumentException();
	}
	public static FloatBuffer 	convertToFloatBuffer(ByteBuffer _buffer) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put((float) _buffer.get());
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(ByteBuffer _buffer, Function<Byte, Float> _converter) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put(_converter.apply( _buffer.get() ));
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(ShortBuffer _buffer) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put((float) _buffer.get());
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(ShortBuffer _buffer, Function<Short, Float> _converter) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put(_converter.apply( _buffer.get() ));
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(IntBuffer _buffer) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put((float) _buffer.get());
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(IntBuffer _buffer, Function<Integer, Float> _converter) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put(_converter.apply( _buffer.get() ));
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(LongBuffer _buffer) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put((float) _buffer.get());
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(LongBuffer _buffer, Function<Long, Float> _converter) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put(_converter.apply( _buffer.get() ));
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(DoubleBuffer _buffer) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put((float) _buffer.get());
		return floatBuffer;
	}
	public static FloatBuffer 	convertToFloatBuffer(DoubleBuffer _buffer, Function<Double, Float> _converter) {
		FloatBuffer floatBuffer = FloatBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			floatBuffer.put(_converter.apply( _buffer.get() ));
		return floatBuffer;
	}

	public static DoubleBuffer 	convertToDoubleBuffer(Buffer _buffer) {
		if(_buffer instanceof DoubleBuffer)
			return (DoubleBuffer) _buffer;
		else if(_buffer instanceof ByteBuffer)
			return convertToDoubleBuffer((ByteBuffer) _buffer);
		else if(_buffer instanceof ShortBuffer)
			return convertToDoubleBuffer((ShortBuffer) _buffer);
		else if(_buffer instanceof IntBuffer)
			return convertToDoubleBuffer((IntBuffer) _buffer);
		else if(_buffer instanceof LongBuffer)
			return convertToDoubleBuffer((LongBuffer) _buffer);
		else if(_buffer instanceof FloatBuffer)
			return convertToDoubleBuffer((FloatBuffer) _buffer);
		throw new IllegalArgumentException();
	}
	public static DoubleBuffer 	convertToDoubleBuffer(ByteBuffer _buffer) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put((double) _buffer.get());
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(ByteBuffer _buffer, Function<Byte, Double> _converter) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put(_converter.apply( _buffer.get() ));
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(ShortBuffer _buffer) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put((double) _buffer.get());
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(ShortBuffer _buffer, Function<Short, Double> _converter) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put(_converter.apply( _buffer.get() ));
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(IntBuffer _buffer) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put((double) _buffer.get());
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(IntBuffer _buffer, Function<Integer, Double> _converter) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put(_converter.apply( _buffer.get() ));
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(LongBuffer _buffer) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put((double) _buffer.get());
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(LongBuffer _buffer, Function<Long, Double> _converter) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put(_converter.apply( _buffer.get() ));
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(FloatBuffer _buffer) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put((double) _buffer.get());
		return doubleBuffer;
	}
	public static DoubleBuffer 	convertToDoubleBuffer(FloatBuffer _buffer, Function<Float, Double> _converter) {
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(_buffer.capacity());
		for(int i = 0; i < _buffer.capacity(); ++i)
			doubleBuffer.put(_converter.apply( _buffer.get() ));
		return doubleBuffer;
	}

	@Deprecated
	public static ByteBuffer 	asByteBuffer(Buffer _buffer) {
		if(_buffer instanceof ByteBuffer) {
			return  (ByteBuffer) _buffer;

		} else if(_buffer instanceof ShortBuffer) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity() * 2);
			byteBuffer.asShortBuffer().put((ShortBuffer) _buffer);
			return byteBuffer;

		} else if(_buffer instanceof IntBuffer) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity() * 4);
			byteBuffer.asIntBuffer().put((IntBuffer) _buffer);
			return byteBuffer;

		} else if(_buffer instanceof LongBuffer) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity() * 8);
			byteBuffer.asLongBuffer().put((LongBuffer) _buffer);
			return byteBuffer;
			
		} else if(_buffer instanceof FloatBuffer) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity() * 4);
			byteBuffer.asFloatBuffer().put((FloatBuffer) _buffer);
			return byteBuffer;
			
		} else if(_buffer instanceof DoubleBuffer) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(_buffer.capacity() * 8);
			byteBuffer.asDoubleBuffer().put((DoubleBuffer) _buffer);
			return byteBuffer;
		}
		
		throw new IllegalArgumentException();
	}
	@Deprecated
	public static ShortBuffer 	asShortBuffer(Buffer _buffer) {
		ByteBuffer  byteBuffer = asByteBuffer(_buffer);
		ShortBuffer shortBuffer = ShortBuffer.allocate(byteBuffer.capacity() / 2);
		shortBuffer.put(byteBuffer.asShortBuffer());
		
		return shortBuffer;
	}
	@Deprecated
	public static IntBuffer 	asIntBuffer(Buffer _buffer) {
		return asByteBuffer(_buffer).asIntBuffer();
	}
	@Deprecated
	public static LongBuffer 	asLongBuffer(Buffer _buffer) {
		return asByteBuffer(_buffer).asLongBuffer();
	}
	@Deprecated
	public static FloatBuffer 	asFloatBuffer(Buffer _buffer) {
		return asByteBuffer(_buffer).asFloatBuffer();
	}
	@Deprecated
	public static DoubleBuffer 	asDoubleBuffer(Buffer _buffer) {
		ByteBuffer   byteBuffer = asByteBuffer(_buffer);
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(byteBuffer.capacity() / 8);
		doubleBuffer.put(byteBuffer.asDoubleBuffer());
		
		return doubleBuffer;
	}

}
