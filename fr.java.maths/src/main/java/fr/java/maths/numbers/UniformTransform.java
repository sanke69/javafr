package fr.java.maths.numbers;

public class UniformTransform {
	public static final short  S_MIN = Short.MIN_VALUE, S_MAX = Short.MAX_VALUE;
	public static final int    I_MIN = Short.MIN_VALUE, I_MAX = Short.MAX_VALUE;
	public static final long   L_MIN = Short.MIN_VALUE, L_MAX = Short.MAX_VALUE;
//	public static final float  F_MIN = Short.MIN_VALUE, F_MAX = Short.MAX_VALUE;
	public static final float  F_MIN = 0f,              F_MAX = 1f;
//	public static final double D_MIN = Short.MIN_VALUE, D_MAX = Short.MAX_VALUE;
	public static final double D_MIN = 0d,              D_MAX = 1d;

	public static Short 	byteToShort(Byte _value) {
		return (short) (S_MIN + ((_value & 0xFF) / 255.0) * (S_MAX - S_MIN));
	}
	public static Byte 		shortToByte(Short _value) {
		return (byte) (((double) (_value - S_MIN) / (double) (S_MAX - S_MIN)) * 255.0);
	}

	public static Integer 	byteToInteger(Byte _value) {
		return (int) (I_MIN + ((_value & 0xFF) / 255.0) * (I_MAX - I_MIN));
	}
	public static Byte 		integerToByte(Integer _value) {
		return (byte) (((double) (_value - I_MIN) / (double) (I_MAX - I_MIN)) * 255.0);
	}

	public static Long 		byteToLong(Byte _value) {
		return (long) (L_MIN + ((_value & 0xFF) / 255.0) * (L_MAX - L_MIN));
	}
	public static Byte 		longToByte(Long _value) {
		return (byte) (((_value - L_MIN) * 255.0) / (L_MAX - L_MIN));
	}

	public static Float 	byteToFloat(Byte _value) {
		return (float) (F_MIN + ((_value & 0xFF) / 255.0) * (F_MAX - F_MIN));
	}
	public static Byte 		floatToByte(Float _value) {
		return (byte) (((_value - F_MIN) * 255.0) / (F_MAX - F_MIN));
	}

	public static Double 	byteToDouble(Byte _value) {
		return (double) (D_MIN + ((_value & 0xFF) / 255.0) * (D_MAX - D_MIN));
	}
	public static Byte 		doubleToByte(Double _value) {
		return (byte) (((_value - D_MIN) * 255.0) / (D_MAX - D_MIN));
	}

}
