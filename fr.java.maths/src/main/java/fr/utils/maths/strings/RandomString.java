package fr.utils.maths.strings;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {
	public static final String	upper		= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String	lower		= upper.toLowerCase(Locale.ROOT);
	public static final String	digits		= "0123456789";
	public static final String	alphanum	= upper + lower + digits;
	public static final String	symbols		= ".,;:!?*$€%+-/\\<>#{}[]()|-_çà@";

	private final Random	random;
	private final char[]	usedSymbols;
	private final char[]	buffer;

	public RandomString() {
		this(27);
	}
	public RandomString(int _length) {
		this(_length, new SecureRandom());
	}
	public RandomString(int _length, Random _random) {
		this(_length, _random, alphanum);
	}
	public RandomString(int _length, Random _random, String _symbols) {
		super();

		if(_length < 1) 			throw new IllegalArgumentException();
		if(_symbols.length() < 2) 	throw new IllegalArgumentException();

		random = Objects.requireNonNull(_random);
		usedSymbols = _symbols.toCharArray();
		buffer = new char[_length];
	}

	public String nextString() {
		for(int idx = 0; idx < buffer.length; ++idx)
			buffer[idx] = usedSymbols[random.nextInt(usedSymbols.length)];
		return new String(buffer);
	}

}