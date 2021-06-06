package fr.java.utils.arrays;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import fr.java.utils.primitives.Arrays;

public class IntArrays {

	/**
	 * INT ARRAYS OPERATIONS
	 */
	public static int[]     empty() {
		return new int[0];
	}
	public static int[]     indexes(int _n) {
		int[] indexes = new int[_n];
		for(int i = 0; i < _n; ++i)
			indexes[i] = i;
		return indexes;
	}
	public static Integer[] Indexes(int _n) {
		Integer[] indexes = new Integer[_n];
		for(int i = 0; i < _n; ++i)
			indexes[i] = i;
		return indexes;
	}
	public static int[]     peak(int _at, int _length) {
		int[] vectPeak = new int[_length];
		vectPeak[_at] = 1;
		return vectPeak;
	}
	public static int[]     repeat(int _value, int _length) {
		int[] vectValue = new int[_length];
		java.util.Arrays.fill(vectValue, _value);
		return vectValue;
	}
	public static double[]  repeat(double _value, int _length) {
		double[] vectValue = new double[_length];
		java.util.Arrays.fill(vectValue, _value);
		return vectValue;
	}
	public static int[]     of(int... _values) {
		return _values;
	}
	public static int[]     randomPermutation(int size) {
		Random r = new Random();
		int[] result = new int[size];

		for(int j = 0; j < size; j++)
			result[j] = j + 1;

		for(int j = size - 1; j > 0; j--) {
			int k = r.nextInt(j);
			int temp = result[j];
			result[j] = result[k];
			result[k] = temp;
		}

		return result;
	}

	public static int[]     replace(int[] data, int index, int newValue) {
		int[] copy = Arrays.copy(data);
		copy[index] = newValue;
		return copy;
	}

	public static int[]     keep(int[] data, int... index) {
		if(index.length == data.length)
			return data;

		int[] ret = new int[index.length];
		int count = 0;
		for(int i = 0; i < data.length; i++)
			if(contains(index, i))
				ret[count++] = data[i];

		return ret;
	}
	public static int[]     remove(int[] data, int index) {
		if(data == null)
			return null;

		if(index >= data.length)
			throw new IllegalArgumentException("Unable to remove index " + index + " was >= data.length");
		if(data.length < 1)
			return data;
		if(index < 0)
			return data;

		int len = data.length;
		int[] result = new int[len - 1];
		System.arraycopy(data, 0, result, 0, index);
		System.arraycopy(data, index + 1, result, index, len - index - 1);
		return result;
	}
	public static int[]     remove(int[] data, int... index) {
		if(index.length >= data.length)
			throw new IllegalStateException("Illegal remove: indexes.length > data.length");

		int[] ret = new int[data.length - index.length];
		int count = 0;
		for(int i = 0; i < data.length; i++)
			if(!contains(index, i))
				ret[count++] = data[i];

		return ret;
	}

	public static boolean   isZero(int[] as) {
		for(int i = 0; i < as.length; i++)
			if(as[i] == 0)
				return true;
		return false;
	}
	public static boolean   isInverse(int[] first, int[] second) {
		int backWardCount = second.length - 1;
		for(int i = 0; i < first.length; i++) {
			if(first[i] != second[backWardCount--])
				return false;
		}
		return true;
	}
	public static boolean   allUnique(int[] toTest) {
		Set<Integer> set = new HashSet<>();
		for(int i : toTest)
			if(!set.contains(i))
				set.add(i);
			else
				return false;
		return true;
	}

	public static boolean   anyMore(int[] target, int[] test) {
		assert target.length == test.length : "Unable to compare: different sizes";
		for(int i = 0; i < target.length; i++) {
			if(target[i] > test[i])
				return true;
		}
		return false;
	}
	public static boolean   anyLess(int[] target, int[] test) {
		assert target.length == test.length : "Unable to compare: different sizes";
		for(int i = 0; i < target.length; i++) {
			if(target[i] < test[i])
				return true;
		}
		return false;
	}

	public static boolean contains(int[] array, int target) {
		for(int value : array)
			if(value == target)
				return true;
		return false;
	}

}
