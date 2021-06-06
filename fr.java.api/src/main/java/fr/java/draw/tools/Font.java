package fr.java.draw.tools;

import java.util.Optional;

public interface Font {

	public static Font of(double _size) {
		return new Font() {
			@Override public double 			getSize() { return _size; }
			@Override public Optional<String> 	getName() { return Optional.empty(); }
		};
	}
	public static Font of(String _name) {
		return new Font() {
			@Override public double 			getSize() { return 12; }
			@Override public Optional<String> 	getName() { return Optional.of(_name); }
		};
	}
	public static Font of(String _name, double _size) {
		return new Font() {
			@Override public double 			getSize() { return _size; }
			@Override public Optional<String> 	getName() { return Optional.of(_name); }
		};
	}

	public default double 	getSize() 		{ return 12; }
	public Optional<String> getName();

}
