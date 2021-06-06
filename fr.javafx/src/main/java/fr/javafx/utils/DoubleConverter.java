package fr.javafx.utils;

import java.util.function.Function;

public abstract class DoubleConverter<T> {

	public static final DoubleConverter<Double> 						identity() {
		return new DoubleConverter<Double>() {
		    public Double toDouble(Double _object)   { return _object; }
		    public Double fromDouble(Double _double) { return _double; }
		};
	}
	public static final DoubleConverter<String> 						toString(String _format) {
		return new DoubleConverter<String>() {
		    public Double toDouble(String _string)   { return Double.parseDouble(_string); }
		    public String fromDouble(Double _double) { return _double.toString(); }
		};
	}
	public static final <T extends Comparable<?>> DoubleConverter<T> 	of(Function<T, Double> _objectToDouble, Function<Double, T> _doubleToObject) {
		return new DoubleConverter<T>() {
		    public Double toDouble(T _object)        { return _objectToDouble.apply(_object); }
		    public T      fromDouble(Double _double) { return _doubleToObject.apply(_double); }
		};
	}


    /**
    * Converts the object provided into its double form.
    * Format of the returned double is defined by the specific converter.
    * @param object the object of type {@code T} to convert
    * @return a double representation of the object passed in.
    */
    public abstract Double toDouble(T _object);

    /**
    * Converts the double provided into an object defined by the specific converter.
    * Format of the double and type of the resulting object is defined by the specific converter.
    * @param double the {@code double} to convert
    * @return an object representation of the double passed in.
    */
    public abstract T fromDouble(Double _double);
   
}
