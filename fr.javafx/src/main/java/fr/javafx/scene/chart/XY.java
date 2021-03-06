/**
 * JavaFR
 * Copyright (C) 2007-?XYZ  Steve PECHBERTI <steve.pechberti@laposte.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.javafx.scene.chart;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public interface XY {

	public enum      Type {
		Scatter, Line, Area, StackedArea, Bar;
	}

	@FunctionalInterface
	public interface Symbol {

		public String path();

	}
	public enum      Symbols implements Symbol {
		diamond		("M5,0 L10,9 L5,18 L0,9 Z"),
		cross		("M2,0 L5,4 L8,0 L10,0 L10,2 L6,5 L10,8 L10,10 L8,10 L5,6 L2,10 L0,10 L0,8 L4,5 L0,2 L0,0 Z"),
		triangle	("M5,0 L10,8 L0,8 Z"),
		check		("M0,4 L2,4 L4,8 L7,0 L9,0 L4,11 Z");
		
		String path;
		
		Symbols(String _path) {
			path = _path;
		}

		public String path() { return path; }

	}

	public enum      Context {
		onXAxis, onYAxis, inPlotArea, outsideChart;
	}

	public enum      Constraint {
		NONE       (0),		// Do not allow the operation.
		HORIZONTAL (1),		// Allow the operation (such as pan / selection / zoom) only on horizontal (x) axis.
		VERTICAL   (2),		// Allow the operation (such as pan / selection / zoom) only on vertical (y) axis.
		BOTH       (3);		// Allow the operation (such as pan / selection / zoom) on either x or y axes.

		int bits;

		Constraint(int _value) {
			bits = _value;
		}

	    public boolean allowsHor() { return this == HORIZONTAL || this == BOTH; }
	    public boolean allowsVer() { return this == VERTICAL   || this == BOTH; }

	    public Constraint AND (Constraint _other) {
	    	switch(bits & _other.bits) {
	    	case 0  : return NONE;
	    	case 1  : return HORIZONTAL;
	    	case 2  : return VERTICAL;
	    	default : return BOTH;
	    	}
	    }
	    public Constraint OR  (Constraint _other) {
	    	switch(bits | _other.bits) {
	    	case 0  : return NONE;
	    	case 1  : return HORIZONTAL;
	    	case 2  : return VERTICAL;
	    	default : return BOTH;
	    	}
	    }
	    public Constraint XOR (Constraint _other) {
	    	switch(bits ^ _other.bits) {
	    	case 0  : return NONE;
	    	case 1  : return HORIZONTAL;
	    	case 2  : return VERTICAL;
	    	default : return BOTH;
	    	}
	    }

	}

	@FunctionalInterface
	public interface ConstraintStrategy {

		public static XY.ConstraintStrategy normal() {
			return new XY.ConstraintStrategy() {
				@Override
				public XY.Constraint getConstraint( XY.Context context ) {
					switch(context) {
					case onXAxis      : return XY.Constraint.HORIZONTAL;
					case onYAxis      : return XY.Constraint.VERTICAL;
					case inPlotArea   : return XY.Constraint.BOTH;
					case outsideChart :
					default            : return XY.Constraint.BOTH;
					}
				}
			};
		}
		public static XY.ConstraintStrategy ignoreOutsideChart() {
			return new XY.ConstraintStrategy() {
				@Override
				public XY.Constraint getConstraint( XY.Context context ) {
					switch(context) {
					case onXAxis      : return XY.Constraint.HORIZONTAL;
					case onYAxis      : return XY.Constraint.VERTICAL;
					case inPlotArea   : return XY.Constraint.BOTH;
					case outsideChart : 
					default           : return XY.Constraint.NONE;
					}
				}
			};
		}
		public static XY.ConstraintStrategy ignoreAxis() {
			return new XY.ConstraintStrategy() {
				@Override
				public XY.Constraint getConstraint( XY.Context context ) {
					switch(context) {
					case onXAxis      : return XY.Constraint.NONE;
					case onYAxis      : return XY.Constraint.NONE;
					case inPlotArea   : return XY.Constraint.BOTH;
					case outsideChart : 
					default           : return XY.Constraint.NONE;
					}
				}
			};
		}
		public static XY.ConstraintStrategy ignorePlotArea() {
			return new XY.ConstraintStrategy() {
				@Override
				public XY.Constraint getConstraint( XY.Context context ) {
					switch(context) {
					case onXAxis      : return XY.Constraint.HORIZONTAL;
					case onYAxis      : return XY.Constraint.VERTICAL;
					case inPlotArea   : return XY.Constraint.NONE;
					case outsideChart : 
					default           : return XY.Constraint.NONE;
					}
				}
			};
		}
		public static XY.ConstraintStrategy withConstraint(Constraint _format) {
			return new XY.ConstraintStrategy() {
				@Override
				public XY.Constraint getConstraint( XY.Context context ) {
					return _format;
				}
			};
		}

		Constraint getConstraint(XY.Context context);

	}

	public interface Axis<T> {
	
	    public interface Range {
//	        public static record Record(double lowerBound, double upperBound, double scale, String tickFormat, double tickSpacing) implements Range {}
	        public static class Record implements Range {
	        	double lowerBound, upperBound, scale, tickSpacing; String tickFormat;
	        	Record(double _lowerBound, double _upperBound, double _scale, String _tickFormat, double _tickSpacing) {
	        		lowerBound  = _lowerBound;
	        		upperBound  = _upperBound;
	        		scale       = _scale;
	        		tickFormat  = _tickFormat;
	        		tickSpacing = _tickSpacing;
	        	}

	        	public double lowerBound()  { return lowerBound; }
	        	public double upperBound()  { return upperBound; }
	        	public double scale()       { return scale; }
	        	public double tickSpacing() { return tickSpacing; }
	        	public String tickFormat()  { return tickFormat; }

	        }
	
	        public static Range  of(double lowerBound, double upperBound, double scale, String tickFormat) {
	        	return new Range.Record(lowerBound, upperBound, scale, tickFormat, 0d);
	        }
	        public static Range  of(double lowerBound, double upperBound, double scale, double tickSpacing) {
	        	return new Range.Record(lowerBound, upperBound, scale, "#.###", tickSpacing);
	        }
	
		    public double 		 	lowerBound();
		    public double 		 	upperBound();
		    public default double 	deltaBound() { return upperBound() - lowerBound(); }
		    public double 		 	scale();
		    public String 		 	tickFormat();
		    public double	 	 	tickSpacing();
		  
		}
	
		public interface Ticks {
		    static final int               DEFAULT_BASE        = 10;
		    static final SortedSet<Number> DEFAULT_MULTIPLIERS = new TreeSet<>(Arrays.asList(1d, 2.5, 5d));

			@FunctionalInterface
			public interface UnitSupplier {
			    double computeTickUnit(double referenceTickUnit);
			}

			public static abstract class Formatter<T> extends StringConverter<T> {
				@Override
				public T      	       fromString(String string)      { return null; }

				@Deprecated
				public abstract String numberToString(Number _value);
				public Number          stringToNumber(String _string) { return null; }
			}

		}

		public BooleanProperty 								autoRangeRoundingProperty();

		public ObjectProperty<XY.Axis.Ticks.UnitSupplier> 	tickUnitSupplierProperty();

		public default XY.Axis.Ticks.Formatter<T> 			getTickLabelFormatterXY() {
			return tickLabelFormatterPropertyXY().get();
		}
		public ObjectProperty<XY.Axis.Ticks.Formatter<T>> 	tickLabelFormatterPropertyXY();
	
	}

	public interface Series {

		static class Style {
			Color lineColor; Double lineWidth; String shape; Color inColor; Color outColor; Color fillColor;

			public Style(Color _lineColor, Double _lineWidth, String _shape, Color _inColor, Color _outColor, Color _fillColor) {
				lineColor = _lineColor;
				lineWidth = _lineWidth;
				shape     = _shape;
				inColor   = _inColor;
				outColor  = _outColor;
				fillColor = _fillColor;
			}
			public Style(String lineColor, Double lineWidth, String shape, String inColor, String outColor, String fillColor) {
				this(fromString(lineColor), lineWidth, shape, fromString(inColor), fromString(outColor), fromString(fillColor));
			}

			Color  lineColor() { return lineColor; }
			Double lineWidth() { return lineWidth; }
			String shape()     { return shape; }
			Color inColor()    { return inColor; }
			Color outColor()   { return outColor; }
			Color fillColor()  { return fillColor; }
			
			public String lineColorHex() { return String.format( "#%02X%02X%02X", (int)( lineColor.getRed() * 255 ), (int)( lineColor.getGreen() * 255 ), (int)( lineColor.getBlue() * 255 ) ); }
			public String lineColorRGB() { return String.format(Locale.ROOT, "rgba(%d, %d, %d, %.2f)", (int) (lineColor.getRed() * 255), (int) (lineColor.getGreen() * 255), (int) (lineColor.getBlue() * 255), lineColor.getOpacity()); }

			public String inColorHex()   { return String.format( "#%02X%02X%02X", (int)( inColor.getRed() * 255 ), (int)( inColor.getGreen() * 255 ), (int)( inColor.getBlue() * 255 ) ); }
			public String inColorHex4()  { return String.format( "#%02X%02X%02X%02X", (int)( inColor.getRed() * 255 ), (int)( inColor.getGreen() * 255 ), (int)( inColor.getBlue() * 255 ) , (int)( inColor.getOpacity() * 255 ) ); }
			public String inColorRGB()   { return String.format(Locale.ROOT, "rgba(%d, %d, %d, %.2f)", (int) (inColor.getRed() * 255), (int) (inColor.getGreen() * 255), (int) (inColor.getBlue() * 255), inColor.getOpacity()); }

			public String outColorHex()  { return String.format( "#%02X%02X%02X", (int)( outColor.getRed() * 255 ), (int)( outColor.getGreen() * 255 ), (int)( outColor.getBlue() * 255 ) ); }
			public String outColorHex4() { return String.format( "#%02X%02X%02X%02X", (int)( outColor.getRed() * 255 ), (int)( outColor.getGreen() * 255 ), (int)( outColor.getBlue() * 255 ), (int)( outColor.getOpacity() * 255 ) ); }
			public String outColorRGB()  { return String.format(Locale.ROOT, "rgba(%d, %d, %d, %.2f)", (int) (outColor.getRed() * 255), (int) (outColor.getGreen() * 255), (int) (outColor.getBlue() * 255), outColor.getOpacity()); }

			public String fillColorHex() { return String.format( "#%02X%02X%02X", (int)( fillColor.getRed() * 255 ), (int)( fillColor.getGreen() * 255 ), (int)( fillColor.getBlue() * 255 ) ); }
			public String fillColorRGB() { return String.format(Locale.ROOT, "rgba(%d, %d, %d, %.2f)", (int) (fillColor.getRed() * 255), (int) (fillColor.getGreen() * 255), (int) (fillColor.getBlue() * 255), fillColor.getOpacity()); }

			static Color fromString(String _webHex) {
				if(_webHex.charAt(0) == '#') {
					String sred   = _webHex.substring(1, 2);
					String sgreen = _webHex.substring(3, 4);
					String sblue  = _webHex.substring(5, 6);
			    	int    red    = Integer.parseInt(sred,16); 
			    	int    green  = Integer.parseInt(sgreen,16); 
			    	int    blue   = Integer.parseInt(sblue,16); 
			    	
			    	return Color.rgb(red, green, blue);
				}
				
				return Color.valueOf(_webHex);
			}
		
		}

	}

	public interface Chart<X, Y> {

		public void 												setStyle(XYChart.Series<X, Y> _series, Series.Style _style);

		// Chart Methods
		public String 												getTitle();
		public void 												setTitle(String value);
		public StringProperty 										titleProperty();
		
	    public Side 												getTitleSide();
	    public void 												setTitleSide(Side value);
	    public ObjectProperty<Side> 								titleSideProperty();
	
	    public boolean 												isLegendVisible();
	    public void 												setLegendVisible(boolean value);
	    public BooleanProperty 										legendVisibleProperty();
	
	    public Side 												getLegendSide();
	    public void 												setLegendSide(Side value);
	    public ObjectProperty<Side> 								legendSideProperty();
	
	    public boolean 												getAnimated();
	    public void 												setAnimated(boolean value);
	    public BooleanProperty 										animatedProperty();
	
	    public ObservableList<XYChart.Series<X,Y>> 					getData();
	    public void 												setData(ObservableList<XYChart.Series<X,Y>> value);
	    public ObjectProperty<ObservableList<XYChart.Series<X,Y>>> 	dataProperty();
	
	    public boolean 												getVerticalGridLinesVisible();
	    public void 												setVerticalGridLinesVisible(boolean value);
	    public BooleanProperty 										verticalGridLinesVisibleProperty();
	
	    public boolean 												isHorizontalGridLinesVisible();
	    public void 												setHorizontalGridLinesVisible(boolean value);
	    public BooleanProperty 										horizontalGridLinesVisibleProperty();
	
	    public boolean 												isAlternativeColumnFillVisible();
	    public void 												setAlternativeColumnFillVisible(boolean value);
	    public BooleanProperty 										alternativeColumnFillVisibleProperty();
	
	    public boolean 												isAlternativeRowFillVisible();
	    public void 												setAlternativeRowFillVisible(boolean value);
	    public BooleanProperty 										alternativeRowFillVisibleProperty();
	
	    public boolean 												isVerticalZeroLineVisible();
	    public void 												setVerticalZeroLineVisible(boolean value);
	    public BooleanProperty 										verticalZeroLineVisibleProperty();
	
	    public boolean 												isHorizontalZeroLineVisible();
	    public void 												setHorizontalZeroLineVisible(boolean value);
	    public BooleanProperty 										horizontalZeroLineVisibleProperty();
	
	    public List<CssMetaData<? extends Styleable, ?>> 			getCssMetaData();
	
		// XYChart Methods
	    public javafx.scene.chart.Axis<X> 							getXAxis();
	    public javafx.scene.chart.Axis<Y> 							getYAxis();
	
	}

	public interface ChartPlugin<X, Y> {

		public void 				setChartPane(XYChartPane<X, Y> xyChartPane);

		public default void 		layoutChildren() {}

		public ObservableList<Node> getChartChildren();

	}

    public static class SelectionEvent extends Event {
        private static final long serialVersionUID = 20200427055500L;

        Rectangle2D             selection;

        public SelectionEvent(Rectangle2D _selection) {
            super(ON_SELECTION);
            selection = _selection;
        }
        public SelectionEvent(Rectangle2D _selection, Object source, EventTarget target) {
            super(source, target, ON_SELECTION);
            selection = _selection;
        }

        public Rectangle2D             	getSelection() {
        	return selection;
        }

        @Override
        public SelectionEvent 			copyFor(Object newSource, EventTarget newTarget) {
            return (SelectionEvent) super.copyFor(newSource, newTarget);
        }

    }

    public static final EventType<SelectionEvent> ON_SELECTION = new EventType<>("ON_SELECTION");

	public static XY.Axis.Ticks.UnitSupplier       						defaultTickUnitSupplier() {
		return defaultTickUnitSupplier(XY.Axis.Ticks.DEFAULT_MULTIPLIERS);
	}
	public static XY.Axis.Ticks.UnitSupplier       						defaultTickUnitSupplier(SortedSet<Number> _multipliers) {
		return new XY.Axis.Ticks.UnitSupplier() {
		    private final double[] multipliers;

		    {
		        checkMultiplier(_multipliers);
		        multipliers = _multipliers.stream().mapToDouble(multiplier -> multiplier.doubleValue()).toArray();
		    }

		    void checkMultiplier(SortedSet<? extends Number> multipliers) {
		        Objects.requireNonNull(_multipliers, "The multipliers must not be null");
		        if(_multipliers.isEmpty())
		            throw new IllegalArgumentException("The set of multipliers must not be empty");

		        for(Number mult : multipliers)
		            if(mult.doubleValue() < 1 || mult.doubleValue() >= XY.Axis.Ticks.DEFAULT_BASE)
		                throw new IllegalArgumentException("The multiplier values must be in range [1, 10)");
		    }

		    @Override
		    public double computeTickUnit(double referenceTickUnit) {
		        if (referenceTickUnit <= 0)
		            throw new IllegalArgumentException("The reference tick unit must be a positive number");

		        int    exp        = (int) Math.floor(Math.log10(referenceTickUnit));
		        double factor     = referenceTickUnit / Math.pow(XY.Axis.Ticks.DEFAULT_BASE, exp);
		        double multiplier = 0;

		        int lastIndex = multipliers.length - 1;
		        if(factor > multipliers[lastIndex]) {
		            multiplier = multipliers[0];
		            exp++;
		        } else {
		            for(int i = lastIndex; i >= 0; i--)
		                if(factor <= multipliers[i])
		                    multiplier = multipliers[i];
		                else
		                    break;
		        }

		        return multiplier * Math.pow(XY.Axis.Ticks.DEFAULT_BASE, exp);
		    }

		};
	}

	public static <T> 					XY.Axis.Ticks.Formatter<T> 		defaultFormatter() {
		return new XY.Axis.Ticks.Formatter<T>() {

			@Override 
			public String 	toString(T _value) {
				if(_value instanceof Number)
					return numberToString((Number) _value);
				else
					return _value.toString();
			}
			@Override
			public T      	fromString(String _string) {
				return null;
			}

			@Override
			public String 	numberToString(Number _value) {
				return new DecimalFormat().format(_value);
			}

		};
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		defaultNumberFormatter() {
		return new XY.Axis.Ticks.Formatter<T>() {

			@Override 
			public String 	toString(T _value) {
				return numberToString( _value );
			}
			@Override
			public T      	fromString(String _string) {
				return null;
			}

			@Override
			public String 	numberToString(Number _value) {
				return new DecimalFormat( bestNumberFormat(_value.doubleValue()) ).format( _value );
			}

		};
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		defaultDateFormatter() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		return new XY.Axis.Ticks.Formatter<T>() {
			@Override public String 	toString(T value)             { return LocalDate.ofInstant( Instant.ofEpochMilli( value.longValue() ), ZoneId.systemDefault() ).format(formatter); }
			@Override public T      	fromString(String string)     { return null; }

			@Override public String 	numberToString(Number _value) { return new DecimalFormat().format( _value ); }
		};
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		defaultDateFormatter(String _format) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(_format);

		return new XY.Axis.Ticks.Formatter<T>() {
			@Override public String 	toString(T value)             { return LocalDate.ofInstant( Instant.ofEpochMilli( value.longValue() ), ZoneId.systemDefault() ).format(formatter); }
			@Override public T      	fromString(String string)     { return null; }

			@Override public String 	numberToString(Number _value) { return new DecimalFormat().format( _value ); }
		};
	}

	public static <T> 					XY.Axis.Ticks.Formatter<T> 		newFormatter(Function<T, String> _format) {
		return new XY.Axis.Ticks.Formatter<T>() {
			@Override public String 	toString(T value)             { return _format.apply( value ); }
			@Override public T      	fromString(String string)     { return null; }

			@Override public String 	numberToString(Number _value) { return new DecimalFormat().format( _value ); }
		};
	}

	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newNumberFormat(String _prefix, String _suffix) {
		return new XY.Axis.Ticks.Formatter<T>() {
	        private String       prefix = _prefix;
	        private String       suffix = _suffix;

			@Override
			public String 	toString(T _value) {
				return numberToString( _value );
			}
			@SuppressWarnings("unchecked")
			@Override
			public T      	fromString(String string) {
	            try {
	                int prefixLength = (prefix == null) ? 0 : prefix.length();
	                int suffixLength = (suffix == null) ? 0 : suffix.length();
	                
	                Number number = new DecimalFormat().parse(string.substring(prefixLength, string.length() - suffixLength));

	                return (T) number;

	            } catch (ParseException exc) { throw new IllegalArgumentException(exc); }
			}

			@Override
			public String 	numberToString(Number _number) {
	            String prefix = this.prefix == null ? "" : this.prefix;
	            String suffix = this.suffix == null ? "" : this.suffix;

	            return prefix + bestNumberRepresentation(_number) + suffix;
			}

	    };
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newNumberFormat(String _prefix, String _suffix, NumberFormat _format) {
		return new XY.Axis.Ticks.Formatter<T>() {
	        private NumberFormat format = _format;
	        private String       prefix = _prefix;
	        private String       suffix = _suffix;

			@Override
			public String 	toString(T _value) {
				return numberToString( _value );
			}
			@SuppressWarnings("unchecked")
			@Override
			public T      	fromString(String string) {
	            try {
	                int prefixLength = (prefix == null) ? 0 : prefix.length();
	                int suffixLength = (suffix == null) ? 0 : suffix.length();
	                
	                Number number = format.parse(string.substring(prefixLength, string.length() - suffixLength));

	                return (T) number;

	            } catch (ParseException exc) { throw new IllegalArgumentException(exc); }
			}

			@Override
			public String 	numberToString(Number _number) {
	            String prefix = this.prefix == null ? "" : this.prefix;
	            String suffix = this.suffix == null ? "" : this.suffix;

	            return prefix + bestNumberRepresentation(_number) + suffix;
			}

	    };
	}

	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newNumberFormat(String _format) {
		return new XY.Axis.Ticks.Formatter<T>() {
			@Override public String 	toString(T value)             { return numberToString( value ); }
			@Override public T      	fromString(String string)     { return null; }

			@Override public String 	numberToString(Number _value) { return new DecimalFormat(_format).format( _value ); }
		};
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newNumberFormat(StringProperty _formatProperty) {
		return new XY.Axis.Ticks.Formatter<T>() {
			@Override public String 	toString(T value)             { return numberToString( value ); }
			@Override public T      	fromString(String string)     { return null; }

			@Override public String 	numberToString(Number _value) { return new DecimalFormat( _formatProperty.get() ).format( _value ); }
		};
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newNumberFormat(Function<Number, String> _format) {
		return new XY.Axis.Ticks.Formatter<T>() {
			@Override public String 	toString(T _value)            { return numberToString( _value ); }
			@Override public T      	fromString(String string)     { return null; }

			@Override public String 	numberToString(Number _value) { return _format.apply( _value ); }
		};
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newNumberFormat(NumberFormat _format) {
		return newNumberFormat(null, null, _format);
	}

	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newIntegerFormat() {
		return newIntegerFormat(null, null);
	}
	public static <T extends Number> 	XY.Axis.Ticks.Formatter<T> 		newIntegerFormat(String _prefix, String _suffix) {
		return new XY.Axis.Ticks.Formatter<T>() {
	        private String       prefix = _prefix;
	        private String       suffix = _suffix;

			@Override
			public String 	toString(T _value) {
				return numberToString( _value );
			}
			@SuppressWarnings("unchecked")
			@Override
			public T      	fromString(String string) {
	            try {
	                int prefixLength = (prefix == null) ? 0 : prefix.length();
	                int suffixLength = (suffix == null) ? 0 : suffix.length();
	                
	                Number number = new DecimalFormat().parse(string.substring(prefixLength, string.length() - suffixLength));

	                return (T) number;

	            } catch (ParseException exc) { throw new IllegalArgumentException(exc); }
			}

			@Override
			public String 	numberToString(Number _number) {
	            String prefix = this.prefix == null ? "" : this.prefix;
	            String suffix = this.suffix == null ? "" : this.suffix;

	            return prefix + Integer.toString(_number.intValue()) + suffix;
			}

	    };
	}

    public static String bestNumberRepresentation(Number _value) {
    	if(_value instanceof Integer) {
    		return "" + (Integer) _value;
    	} else if(_value instanceof Long) {
    		return "" + (Long) _value;
    	} else if(_value instanceof Float) {
        	return new DecimalFormat( adjustedDoubleFormat((Float) _value) ).format(_value);
    	} else if(_value instanceof Double) {
        	return new DecimalFormat( adjustedDoubleFormat((Double) _value) ).format(_value);
    	}

    	return new DecimalFormat( bestNumberFormat(_value) ).format(_value);
    }
    public static String bestNumberFormat(Number _value) {
    	if(_value instanceof Integer) {
    		return "%d";
    	} else if(_value instanceof Long) {
    		return "%l";
    	} else if(_value instanceof Float) {
        	return adjustedDoubleFormat(((Float) _value).doubleValue());
    	} else if(_value instanceof Double) {
        	return adjustedDoubleFormat(((Double) _value).doubleValue());
    	}

    	return adjustedDoubleFormat(_value.doubleValue());
    }

    private static String adjustedDoubleFormat(double _value) {
        int log10 = (int) Math.floor(Math.log10(_value));
        boolean unitHasFraction = Math.rint(_value) != _value;
        if (log10 >= 1 && !unitHasFraction) {
            return "#,##0";
        }
        int fractDigitsCount = unitHasFraction ? Math.abs(log10) + 1 : Math.abs(log10);
        StringBuilder format = new StringBuilder("0");
        if (fractDigitsCount > 0) {
            format.append('.');
        }
        for (int i = 0; i < fractDigitsCount; i++) {
            format.append('0');
        }
        return format.toString();
    }

}
