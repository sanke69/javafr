package fr.java.maths.algebra;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.ByteTensor;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.tensor.FloatTensor;
import fr.java.math.algebra.tensor.IntTensor;
import fr.java.math.algebra.tensor.LongTensor;
import fr.java.math.algebra.tensor.ShortTensor;
import fr.java.math.tensor.Tensor;
import fr.java.maths.algebra.tensors.ByteArrayTensor;
import fr.java.maths.algebra.tensors.DoubleArrayTensor;
import fr.java.maths.algebra.tensors.FloatArrayTensor;
import fr.java.maths.algebra.tensors.IntArrayTensor;
import fr.java.maths.algebra.tensors.LongArrayTensor;
import fr.java.maths.algebra.tensors.ShortArrayTensor;

public class Tensors {

	public static final class Utils {

		public static int[] 		toPrimitiveArray(final Integer... _shape) {
			return Arrays.stream(_shape).mapToInt(i -> i).toArray();
		}
		public static int[] 		toPrimitiveArray(final List<Integer> _shape) {
			return _shape.stream().mapToInt(i -> i).toArray();
		}

		public static Integer[] 	toArray(final int... _shape) {
			return Arrays.stream(_shape).boxed().toArray(Integer[]::new);
		}
		public static Integer[] 	toArray(final List<Integer> _shape) {
			return _shape.toArray(new Integer[0]);
		}

		public static List<Integer> toList(final int... _shape) {
			return Collections.unmodifiableList( Arrays.stream(_shape).boxed().collect(Collectors.toList()) );
		}
		public static List<Integer> toList(final Integer... _shape) {
			return Collections.unmodifiableList( Arrays.stream(_shape).collect(Collectors.toList()) );
		}
		
		public static int 			getCapacity(final int... _shape) {
			return Arrays.stream(_shape).reduce(1, (a, b) -> a * b);
		}
		public static int 			getCapacity(final List<Integer> _shape) {
			return _shape.stream().reduce(1, (a, b) -> a * b);
		}


		public static int[]			getMinibatchShape(final int _count, final int... _shape) {
			int[] minibatchShape = new int[_shape.length + 1];
			
			minibatchShape[0] = _count;
			for(int i = 0; i < _shape.length; ++i)
				minibatchShape[i + 1] = _shape[i];

			return minibatchShape;
		}
		
		public static int[]			getSliceCapacities(final int... _shape) {
			int[] capacities = new int[_shape.length];

			capacities[_shape.length - 1] = 1;
			for(int i = _shape.length - 1; i-- > 0;)
				capacities[i] = capacities[i+1] * _shape[i+1];

			return capacities;
		}

	}

	public static final class Print {

		public static String 			toString(NumberTensor _tensor) {
			StringBuilder sb = new StringBuilder();

			sb.append(_tensor.getNature() + "\t" + printDimension(_tensor) + "\n");
			sb.append(printValues(_tensor) + "\n");

			return sb.toString();
		}

		public static String 						printDimension(NumberTensor _tensor) {
			StringBuilder sb = new StringBuilder();

			int[] dims = _tensor.getShape();
			
			sb.append("[ ");
			for(int i = 0; i < dims.length - 1; ++i)
				sb.append(dims[i] + ", ");
			sb.append(dims[dims.length - 1]);
			sb.append(" ]");
			
			return sb.toString();
		}
		private static String 						printValues(NumberTensor _tensor) {
			return printValues(_tensor, "", (int[]) null);
		}
		private static String 						printValues(NumberTensor _tensor, String _tab, int... subCoords) {
			StringBuilder sb  = new StringBuilder();
			String        tab = _tab;

			int[] dims = _tensor.getShape();

			if(dims.length == 1)
				return printValues1D(_tensor, "", (int[]) null);

			if(subCoords != null && subCoords.length == dims.length - 1) {
				return printValues1D(_tensor, tab, subCoords);
			} else {
				int   newSubCoordsSize = subCoords != null ? subCoords.length + 1 : 1;
				int[] newSubCoords     = new int[newSubCoordsSize];

				if(subCoords != null)
					for(int i = 0; i < subCoords.length; ++i)
						newSubCoords[i] = subCoords[i];

				int sliceDim = dims[newSubCoordsSize - 1];

				tab += "\t";

				sb.append(_tab + "[" + "\n");
				for(int i = 0; i < sliceDim; ++i) {
					newSubCoords[newSubCoordsSize - 1] = i;
					sb.append(printValues(_tensor, tab, newSubCoords) + (i == sliceDim - 1 ? "" : ",") + "\n");
				}
				sb.append(_tab + "]");
			}
			
			return sb.toString();
		}
		// TODO:: Depending on Primitive, adapt the Value Rendering...
		private static String 			printValues1D(NumberTensor _tensor, String _tab, int... _subCoords) {
			StringBuilder sb = new StringBuilder();

			int       N      = _tensor.getShape().length;
			int       lastN  = _tensor.getShape()[N - 1];
			int[]     coords = new int[N];

			for(int i = 0; i < N - 1; ++i)
				coords[i] = _subCoords[i];

			sb.append(_tab + "[ ");
			for(int i = 0; i < lastN - 1; ++i) {
				coords[N-1] = i;
				sb.append(_tensor.getNumber(coords) + ", ");
			}
			coords[N-1] = lastN - 1;
			sb.append(_tensor.getNumber(coords));
			sb.append(" ]");

			return sb.toString();
		}

	}
	
	
	
	public static final class Byte {

		public static ByteTensor 	create(int _capacity) {
			return new ByteArrayTensor(true, _capacity);
		}
		public static ByteTensor 	create(int... _slice) {
			return new ByteArrayTensor(true, _slice);
		}
		public static ByteTensor 	create(Integer[] _slice) {
			return new ByteArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}
		public static ByteTensor 	create(List<Integer> _slice) {
			return new ByteArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}

		public static ByteTensor 	allocate(byte[] _array) {
			return new ByteArrayTensor(_array, true, _array.length);
		}
		public static ByteTensor 	allocate(byte[] _array, int... _slice) {
			return new ByteArrayTensor(_array, true, _slice);
		}
		public static ByteTensor 	allocate(byte[] _array, Integer[] _slice) {
			return new ByteArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}
		public static ByteTensor 	allocate(byte[] _array, List<Integer> _slice) {
			return new ByteArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}

		public static ByteTensor 	wrap(byte[] _array) {
			return new ByteArrayTensor(_array, false, null);
		}
		public static ByteTensor 	wrap(byte[] _array, int... _slice) {
			return new ByteArrayTensor(_array, false, _slice);
		}
		public static ByteTensor 	wrap(byte[] _array, Integer[] _slice) {
			return new ByteArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}
		public static ByteTensor 	wrap(byte[] _array, List<Integer> _slice) {
			return new ByteArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}

		public static ByteTensor 	of(byte[][] _array) {
			throw new NotYetImplementedException();
		}
		public static ByteTensor 	of(byte[][][] _array) {
			throw new NotYetImplementedException();
		}

	}
	
	public static final class Short {

		public static ShortTensor create(final int _capacity) {
			return new ShortArrayTensor(true, _capacity);
		}
		public static ShortTensor create(final int... _slice) {
			return new ShortArrayTensor(true, _slice);
		}
		public static ShortTensor create(final Integer[] _slice) {
			return new ShortArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}
		public static ShortTensor create(final List<Integer> _slice) {
			return new ShortArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}

		public static ShortTensor allocate(final short[] _array) {
			return new ShortArrayTensor(_array, true, null);
		}
		public static ShortTensor allocate(final short[] _array, final int... _slice) {
			return new ShortArrayTensor(_array, true, _slice);
		}
		public static ShortTensor allocate(final short[] _array, final Integer[] _slice) {
			return new ShortArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}
		public static ShortTensor allocate(final short[] _array, final List<Integer> _slice) {
			return new ShortArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}

		public static ShortTensor wrap(final short[] _array) {
			return new ShortArrayTensor(_array, false, null);
		}
		public static ShortTensor wrap(final short[] _array, final int... _slice) {
			return new ShortArrayTensor(_array, false, _slice);
		}
		public static ShortTensor wrap(final short[] _array, final Integer[] _slice) {
			return new ShortArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}
		public static ShortTensor wrap(final short[] _array, final List<Integer> _slice) {
			return new ShortArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}

		public static ShortTensor 	of(short[][] _array) {
			throw new NotYetImplementedException();
		}
		public static ShortTensor 	of(short[][][] _array) {
			throw new NotYetImplementedException();
		}

	}
	
	public static final class Int {

		public static IntTensor create(final int _capacity) {
			return new IntArrayTensor(true, Utils.toPrimitiveArray(_capacity));
		}
		public static IntTensor create(final int... _slice) {
			return new IntArrayTensor(true, _slice);
		}
		public static IntTensor create(final Integer[] _slice) {
			return new IntArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}
		public static IntTensor create(final List<Integer> _slice) {
			return new IntArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}

		public static IntTensor allocate(final int[] _array) {
			return new IntArrayTensor(_array, true, _array.length);
		}
		public static IntTensor allocate(final int[] _array, final int... _slice) {
			return new IntArrayTensor(_array, true, _slice);
		}
		public static IntTensor allocate(final int[] _array, final Integer[] _slice) {
			return new IntArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}
		public static IntTensor allocate(final int[] _array, final List<Integer> _slice) {
			return new IntArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}

		public static IntTensor wrap(final int[] _array) {
			return new IntArrayTensor(_array, false, _array.length);
		}
		public static IntTensor wrap(final int[] _array, final int... _slice) {
			return new IntArrayTensor(_array, false, _slice);
		}
		public static IntTensor wrap(final int[] _array, final Integer[] _slice) {
			return new IntArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}
		public static IntTensor wrap(final int[] _array, final List<Integer> _slice) {
			return new IntArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}

		public static IntTensor 	of(int[][] _array) {
			throw new NotYetImplementedException();
		}
		public static IntTensor 	of(int[][][] _array) {
			throw new NotYetImplementedException();
		}

	}
	
	public static final class Long {

		public static LongTensor create(final int _capacity) {
			return new LongArrayTensor(true, _capacity);
		}
		public static LongTensor create(final int... _slice) {
			return new LongArrayTensor(true, _slice);
		}
		public static LongTensor create(final Integer[] _slice) {
			return new LongArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}
		public static LongTensor create(final List<Integer> _slice) {
			return new LongArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}

		public static LongTensor allocate(final long[] _array) {
			return new LongArrayTensor(_array, true, _array.length);
		}
		public static LongTensor allocate(final long[] _array, final int... _slice) {
			return new LongArrayTensor(_array, true, _slice);
		}
		public static LongTensor allocate(final long[] _array, final Integer[] _slice) {
			return new LongArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}
		public static LongTensor allocate(final long[] _array, final List<Integer> _slice) {
			return new LongArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}

		public static LongTensor wrap(final long[] _array) {
			return new LongArrayTensor(_array, false, _array.length);
		}
		public static LongTensor wrap(final long[] _array, final int... _slice) {
			return new LongArrayTensor(_array, false, _slice);
		}
		public static LongTensor wrap(final long[] _array, final Integer[] _slice) {
			return new LongArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}
		public static LongTensor wrap(final long[] _array, final List<Integer> _slice) {
			return new LongArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}

		public static LongTensor 	of(long[][] _array) {
			throw new NotYetImplementedException();
		}
		public static LongTensor 	of(long[][][] _array) {
			throw new NotYetImplementedException();
		}

	}
	
	public static final class Float {

		public static FloatTensor create(final int _capacity) {
			return new FloatArrayTensor(true, _capacity);
		}
		public static FloatTensor create(final int... _slice) {
			return new FloatArrayTensor(true, _slice);
		}
		public static FloatTensor create(final Integer[] _slice) {
			return new FloatArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}
		public static FloatTensor create(final List<Integer> _slice) {
			return new FloatArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}

		public static FloatTensor allocate(final float[] _array) {
			return new FloatArrayTensor(_array, true, _array.length);
		}
		public static FloatTensor allocate(final float[] _array, final int... _slice) {
			return new FloatArrayTensor(_array, true, _slice);
		}
		public static FloatTensor allocate(final float[] _array, final Integer[] _slice) {
			return new FloatArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}
		public static FloatTensor allocate(final float[] _array, final List<Integer> _slice) {
			return new FloatArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}

		public static FloatTensor wrap(final float[] _array) {
			return new FloatArrayTensor(_array, false, null);
		}
		public static FloatTensor wrap(final float[] _array, final int... _slice) {
			return new FloatArrayTensor(_array, false, _slice);
		}
		public static FloatTensor wrap(final float[] _array, final Integer[] _slice) {
			return new FloatArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}
		public static FloatTensor wrap(final float[] _array, final List<Integer> _slice) {
			return new FloatArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}

		public static FloatTensor 	of(float[][] _array) {
			throw new NotYetImplementedException();
		}
		public static FloatTensor 	of(float[][][] _array) {
			throw new NotYetImplementedException();
		}

	}
	
	public static final class Double {

		public static DoubleTensor.Editable create(final int _capacity) {
			return new DoubleArrayTensor(true, _capacity);
		}
		public static DoubleTensor.Editable create(final int... _slice) {
			return new DoubleArrayTensor(true, _slice);
		}
		public static DoubleTensor.Editable create(final Integer[] _slice) {
			return new DoubleArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}
		public static DoubleTensor.Editable create(final List<Integer> _slice) {
			return new DoubleArrayTensor(true, Utils.toPrimitiveArray(_slice));
		}

		public static DoubleTensor.Editable allocate(final double[] _array) {
			return new DoubleArrayTensor(_array, true, null);
		}
		public static DoubleTensor.Editable allocate(final double[] _array, final int... _slice) {
			return new DoubleArrayTensor(_array, true, _slice);
		}
		public static DoubleTensor.Editable allocate(final double[] _array, final Integer[] _slice) {
			return new DoubleArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}
		public static DoubleTensor.Editable allocate(final double[] _array, final List<Integer> _slice) {
			return new DoubleArrayTensor(_array, true, Utils.toPrimitiveArray(_slice));
		}

		public static DoubleTensor.Editable wrap(final double[] _array) {
			return new DoubleArrayTensor(_array, false, null);
		}
		public static DoubleTensor.Editable wrap(final double[] _array, final int... _slice) {
			return new DoubleArrayTensor(_array, false, _slice);
		}
		public static DoubleTensor.Editable wrap(final double[] _array, final Integer[] _slice) {
			return new DoubleArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}
		public static DoubleTensor.Editable wrap(final double[] _array, final List<Integer> _slice) {
			return new DoubleArrayTensor(_array, false, Utils.toPrimitiveArray(_slice));
		}

		public static DoubleTensor 	of(double[][] _array) {
			throw new NotYetImplementedException();
		}
		public static DoubleTensor 	of(double[][][] _array) {
			throw new NotYetImplementedException();
		}

	}
	
	public static boolean assertIdenticalShapes(NumberTensor... _tensors) {
		NumberTensor first = _tensors[0];
		for(NumberTensor tensor : _tensors) {
			assert(first.getSliceDepth() == tensor.getSliceDepth()) : "Tensor shapes are not identical";
			for(int i = 1; i < first.getSliceDepth(); ++i)
				assert(first.getSliceDimension(i) == tensor.getSliceDimension(i)) : "Tensor shapes are not identical";
		}
		return true;
	}
	public static boolean assertIdenticalShapes(Collection<? extends NumberTensor> _tensors) {
		NumberTensor first = _tensors.iterator().next();
		for(NumberTensor tensor : _tensors) {
			assert(first.getSliceDepth() == tensor.getSliceDepth()) : "Tensor shapes are not identical";
			for(int i = 1; i < first.getSliceDepth(); ++i)
				assert(first.getSliceDimension(i) == tensor.getSliceDimension(i)) : "Tensor shapes are not identical";
		}
		return true;
	}

	public static NumberTensor  minibatches(Collection<? extends NumberTensor> _tensors) {
		assert Tensors.assertIdenticalShapes(_tensors) : "Tensor shapes are not identical";

		NumberTensor first = _tensors.iterator().next();

		int tensorSize  = first.getCapacity();
		int nbSlices    = _tensors.size();

		int[] minibatchShape = Utils.getMinibatchShape(_tensors.size(), first.getShape());

		Buffer minibatchData;
		switch(_tensors.iterator().next().getPrimitive()) {
		case BYTE:		minibatchData  = ByteBuffer.wrap(new byte[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((ByteBuffer) minibatchData).put((ByteBuffer) t.getBuffer());
						return Byte.allocate(((ByteBuffer) minibatchData).array(), minibatchShape);
		case SHORT:		minibatchData  = ShortBuffer.wrap(new short[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((ShortBuffer) minibatchData).put((ShortBuffer) t.getBuffer());
						return Short.allocate(((ShortBuffer) minibatchData).array(), minibatchShape);
		case INTEGER:	minibatchData  = IntBuffer.wrap(new int[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((IntBuffer) minibatchData).put((IntBuffer) t.getBuffer());
						return Int.allocate(((IntBuffer) minibatchData).array(), minibatchShape);
		case LONG:		minibatchData  = LongBuffer.wrap(new long[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((LongBuffer) minibatchData).put((LongBuffer) t.getBuffer());
						return Long.allocate(((LongBuffer) minibatchData).array(), minibatchShape);
		case FLOAT:		minibatchData  = FloatBuffer.wrap(new float[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((FloatBuffer) minibatchData).put((FloatBuffer) t.getBuffer());
						return Float.allocate(((FloatBuffer) minibatchData).array(), minibatchShape);
		case DOUBLE:	minibatchData  = DoubleBuffer.wrap(new double[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((DoubleBuffer) minibatchData).put((DoubleBuffer) t.getBuffer());
						return Double.allocate(((DoubleBuffer) minibatchData).array(), minibatchShape);
		default:		throw new IllegalAccessError("Generic must be a Number");		
		}
	}
	public static NumberTensor  minibatches(NumberTensor... _tensors) {
		assert Tensors.assertIdenticalShapes(_tensors) : "Tensor shapes are not identical";

		int tensorSize  = _tensors[0].getCapacity();
		int nbSlices    = _tensors.length;

		int[] minibatchShape = Utils.getMinibatchShape(_tensors.length, _tensors[0].getShape());

		Buffer minibatchData;
		switch(_tensors[0].getPrimitive()) {
		case BYTE:		minibatchData  = ByteBuffer.wrap(new byte[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors) {
							t.getBuffer().rewind();
							((ByteBuffer) minibatchData).put((ByteBuffer) t.getBuffer());
						}
						return Byte.allocate(((ByteBuffer) minibatchData).array(), minibatchShape);
		case SHORT:		minibatchData  = ShortBuffer.wrap(new short[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((ShortBuffer) minibatchData).put((ShortBuffer) t.getBuffer());
						return Short.allocate(((ShortBuffer) minibatchData).array(), minibatchShape);
		case INTEGER:	minibatchData  = IntBuffer.wrap(new int[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((IntBuffer) minibatchData).put((IntBuffer) t.getBuffer());
						return Int.allocate(((IntBuffer) minibatchData).array(), minibatchShape);
		case LONG:		minibatchData  = LongBuffer.wrap(new long[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((LongBuffer) minibatchData).put((LongBuffer) t.getBuffer());
						return Long.allocate(((LongBuffer) minibatchData).array(), minibatchShape);
		case FLOAT:		minibatchData  = FloatBuffer.wrap(new float[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((FloatBuffer) minibatchData).put((FloatBuffer) t.getBuffer());
						return Float.allocate(((FloatBuffer) minibatchData).array(), minibatchShape);
		case DOUBLE:	minibatchData  = DoubleBuffer.wrap(new double[nbSlices * tensorSize]);
						for(NumberTensor t : _tensors)
							((DoubleBuffer) minibatchData).put((DoubleBuffer) t.getBuffer());
						return Double.allocate(((DoubleBuffer) minibatchData).array(), minibatchShape);
		default:		throw new IllegalAccessError("Generic must be a Number");		
		}
	}

	public static NumberTensor  stack(Collection<? extends NumberTensor> _tensors) {
		/*
		assert Tensors.assertIdenticalShapes(_tensors) : "Tensor shapes are not identical";

		Tensor first = _tensors.iterator().next();

		int tensorSize  = first.getCapacity();
		int nbSlices    = 0;

		for(Tensor tensor : _tensors)
			nbSlices += tensor.getDimensionSize(0);

		ArrayList<Integer> stackShape = new ArrayList<Integer>(_tensors.iterator().next().getShape());
		stackShape.set(0, nbSlices);

		Buffer stackData;
		switch(_tensors.iterator().next().getPrimitive()) {
		case BYTE:		stackData  = ByteBuffer.wrap(new byte[nbSlices * tensorSize]);
						for(Tensor t : _tensors)
							((ByteBuffer) stackData).put((ByteBuffer) t.getBuffer());
						return Byte.allocate(((ByteBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case SHORT:		stackData  = ShortBuffer.wrap(new short[nbSlices * tensorSize]);
						for(Tensor t : _tensors)
							((ShortBuffer) stackData).put((ShortBuffer) t.getBuffer());
						return Short.allocate(((ShortBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case INTEGER:	stackData  = IntBuffer.wrap(new int[nbSlices * tensorSize]);
						for(Tensor t : _tensors)
							((IntBuffer) stackData).put((IntBuffer) t.getBuffer());
						return Int.allocate(((IntBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case LONG:		stackData  = LongBuffer.wrap(new long[nbSlices * tensorSize]);
						for(Tensor t : _tensors)
							((LongBuffer) stackData).put((LongBuffer) t.getBuffer());
						return Long.allocate(((LongBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case FLOAT:		stackData  = FloatBuffer.wrap(new float[nbSlices * tensorSize]);
						for(Tensor t : _tensors)
							((FloatBuffer) stackData).put((FloatBuffer) t.getBuffer());
						return Float.allocate(((FloatBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case DOUBLE:	stackData  = DoubleBuffer.wrap(new double[nbSlices * tensorSize]);
						for(Tensor t : _tensors)
							((DoubleBuffer) stackData).put((DoubleBuffer) t.getBuffer());
						return Double.allocate(((DoubleBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		default:		throw new IllegalAccessError("Generic must be a Number");		
		}
		*/
		return null;
	}
	public static NumberTensor  stack(NumberTensor... _tensors) {
		assert Tensors.assertIdenticalShapes(_tensors) : "Tensor shapes are not identical";

		int subTensorSize = _tensors[0].getSliceCapacity(1); //Arrays.asList(_tensors[0].getShape()).stream().reduce(1, (a, b) -> a * b);
		int nbSlices      = 0;

		for(Tensor tensor : _tensors)
			nbSlices += tensor.getSliceDimension(0);

		List<Integer> shape = Arrays.stream(_tensors[0].getShape()).boxed().collect(Collectors.toList());
		ArrayList<Integer> stackShape = new ArrayList<Integer>(shape);
		stackShape.set(0, nbSlices);

		Buffer stackData;
		switch(_tensors[0].getPrimitive()) {
		case BYTE:		stackData  = ByteBuffer.wrap(new byte[nbSlices * subTensorSize]);
						for(Tensor t : _tensors)
							((ByteBuffer) stackData).put((ByteBuffer) t.getBuffer());
						return Byte.allocate(((ByteBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case SHORT:		stackData  = ShortBuffer.wrap(new short[nbSlices * subTensorSize]);
						for(Tensor t : _tensors)
							((ShortBuffer) stackData).put((ShortBuffer) t.getBuffer());
						return Short.allocate(((ShortBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case INTEGER:	stackData  = IntBuffer.wrap(new int[nbSlices * subTensorSize]);
						for(Tensor t : _tensors)
							((IntBuffer) stackData).put((IntBuffer) t.getBuffer());
						return Int.allocate(((IntBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case LONG:		stackData  = LongBuffer.wrap(new long[nbSlices * subTensorSize]);
						for(Tensor t : _tensors)
							((LongBuffer) stackData).put((LongBuffer) t.getBuffer());
						return Long.allocate(((LongBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case FLOAT:		stackData  = FloatBuffer.wrap(new float[nbSlices * subTensorSize]);
						for(Tensor t : _tensors)
							((FloatBuffer) stackData).put((FloatBuffer) t.getBuffer());
						return Float.allocate(((FloatBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		case DOUBLE:	stackData  = DoubleBuffer.wrap(new double[nbSlices * subTensorSize]);
						for(Tensor t : _tensors)
							((DoubleBuffer) stackData).put((DoubleBuffer) t.getBuffer());
						return Double.allocate(((DoubleBuffer) stackData).array(), stackShape.toArray(new Integer[0]));
		default:		throw new IllegalAccessError("Generic must be a Number");		
		}
	}

	public static NumberTensor  repeat(NumberTensor _tensor, int _repeat) {
		int tensorSize  = _tensor.getCapacity();
		int nbSlices    = _repeat;

		List<Integer> shape = Arrays.stream(_tensor.getShape()).boxed().collect(Collectors.toList());
		ArrayList<Integer> repeatShape = new ArrayList<Integer>();
		repeatShape.add(_repeat);
		repeatShape.addAll(shape);

		Buffer repeatedData;
		switch(_tensor.getPrimitive()) {
		case BYTE:		repeatedData  = ByteBuffer.wrap(new byte[nbSlices * tensorSize]);
						for(int i = 0; i < _repeat; ++i)
							((ByteBuffer) repeatedData).put((ByteBuffer) _tensor.getBuffer());
						return Byte.allocate(((ByteBuffer) repeatedData).array(), repeatShape.toArray(new Integer[0]));
		case SHORT:		repeatedData  = ShortBuffer.wrap(new short[nbSlices * tensorSize]);
						for(int i = 0; i < _repeat; ++i)
							((ShortBuffer) repeatedData).put((ShortBuffer) _tensor.getBuffer());
						return Short.allocate(((ShortBuffer) repeatedData).array(), repeatShape.toArray(new Integer[0]));
		case INTEGER:	repeatedData  = IntBuffer.wrap(new int[nbSlices * tensorSize]);
						for(int i = 0; i < _repeat; ++i)
							((IntBuffer) repeatedData).put((IntBuffer) _tensor.getBuffer());
						return Int.allocate(((IntBuffer) repeatedData).array(), repeatShape.toArray(new Integer[0]));
		case LONG:		repeatedData  = LongBuffer.wrap(new long[nbSlices * tensorSize]);
						for(int i = 0; i < _repeat; ++i)
							((LongBuffer) repeatedData).put((LongBuffer) _tensor.getBuffer());
						return Long.allocate(((LongBuffer) repeatedData).array(), repeatShape.toArray(new Integer[0]));
		case FLOAT:		repeatedData  = FloatBuffer.wrap(new float[nbSlices * tensorSize]);
						for(int i = 0; i < _repeat; ++i)
							((FloatBuffer) repeatedData).put((FloatBuffer) _tensor.getBuffer());
						return Float.allocate(((FloatBuffer) repeatedData).array(), repeatShape.toArray(new Integer[0]));
		case DOUBLE:	repeatedData  = DoubleBuffer.wrap(new double[nbSlices * tensorSize]);
						for(int i = 0; i < _repeat; ++i)
							((DoubleBuffer) repeatedData).put((DoubleBuffer) _tensor.getBuffer());
						return Double.allocate(((DoubleBuffer) repeatedData).array(), repeatShape.toArray(new Integer[0]));
		default:		throw new IllegalAccessError("Generic must be a Number");		
		}
	}

}
