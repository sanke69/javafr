package fr.javafx.scene.chart.axis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

import fr.javafx.scene.chart.XY;
import fr.javafx.scene.chart.XY.Axis.Ticks.Formatter;

public final class LogarithmicAxis extends XYValueAxis {
	private static final int DEFAULT_LOGARITHM_BASE = 10;
	private static final int DEFAULT_TICK_COUNT     = 9;

	private final DoubleProperty logarithmBase = new SimpleDoubleProperty(LogarithmicAxis.this, "logarithmBase",
			DEFAULT_LOGARITHM_BASE) {
		@Override
		protected void invalidated() {
			if (get() <= 1) {
				throw new IllegalArgumentException("logarithmBase must be grater than 1");
			}
			invalidateRange();
			requestAxisLayout();
		}
	};
	public LogarithmicAxis() {
		this(null);
	}
	public LogarithmicAxis(String axisLabel) {
		setLabel(axisLabel);
		setMinorTickCount(DEFAULT_TICK_COUNT);
	}
	public LogarithmicAxis(String axisLabel, double lowerBound, double upperBound) {
		super(axisLabel, lowerBound, upperBound);
		setMinorTickCount(DEFAULT_TICK_COUNT);
	}

	public void 				setLogarithmBase(double value) {
		logarithmBase.set(value);
	}
	public double 				getLogarithmBase() {
		return logarithmBase.get();
	}
	public DoubleProperty 		logarithmBaseProperty() {
		return logarithmBase;
	}

	@Override
	public Number 				getValueForDisplay(double displayPosition) {
		double upperBoundLog = log(getUpperBound());
		double lowerBoundLog = log(getLowerBound());
		double logScaleLength = upperBoundLog - lowerBoundLog;
		if (getSide().isVertical()) {
			double height = getHeight();
			return pow(lowerBoundLog + ((height - displayPosition) / height) * logScaleLength);
		}
		return pow(lowerBoundLog + ((displayPosition / getWidth()) * logScaleLength));
	}
	@Override
	public double 				getDisplayPosition(Number value) {
		double upperBoundLog  = log(getUpperBound());
		double lowerBoundLog  = log(getLowerBound());
		double logScaleLength = upperBoundLog - lowerBoundLog;
		double valueLogOffset = log(value.doubleValue()) - lowerBoundLog;

		if (getSide().isVertical())
			return (1 - valueLogOffset / logScaleLength) * getHeight();

		return (valueLogOffset / logScaleLength) * getWidth();
	}

	@Override
	protected List<Number> 		calculateTickValues(double length, Object range) {
		XY.Axis.Range rangeImpl = (XY.Axis.Range) range;
		if (rangeImpl.lowerBound() >= rangeImpl.upperBound()) {
			return Arrays.asList(rangeImpl.lowerBound());
		}
		List<Number> tickValues = new ArrayList<>();
		double exp = Math.ceil(log(rangeImpl.lowerBound()));
		for (double tickValue = pow(exp); tickValue <= rangeImpl.upperBound(); tickValue = pow(++exp)) {
			tickValues.add(tickValue);
		}
		return tickValues;
	}
	@Override
	protected List<Number> 		calculateMinorTickMarks() {
		if (getMinorTickCount() <= 0) {
			return Collections.emptyList();
		}

		List<Number> minorTickMarks = new ArrayList<>();
		double lowerBound = getLowerBound();
		double upperBound = getUpperBound();
		double exp = Math.floor(log(lowerBound));

		for (double majorTick = pow(exp); majorTick < upperBound; majorTick = pow(++exp)) {
			double nextMajorTick = pow(exp + 1);
			double minorUnit = (nextMajorTick - majorTick) / getMinorTickCount();
			for (double minorTick = majorTick + minorUnit; minorTick < nextMajorTick; minorTick += minorUnit) {
				if (minorTick >= lowerBound && minorTick <= upperBound) {
					minorTickMarks.add(minorTick);
				}
			}
		}
		return minorTickMarks;
	}

	@Override
	protected Object 			autoRange(double minValue, double maxValue, double length, double labelSize) {
		return computeRange(minValue, maxValue, length, labelSize);
	}
	@Override
	protected XY.Axis.Range 	computeRange(double min, double max, double axisLength, double labelSize) {
		double minRounded = min;
		double maxRounded = max;

		if (isAutoRanging() && isAutoRangeRounding()) {
			minRounded = min <= 0 ? 1 : pow(Math.floor(log(min)));
			maxRounded = pow(Math.ceil(log(max)));
		}
		double newScale = calculateNewScale(axisLength, minRounded, maxRounded);
		return XY.Axis.Range.of(minRounded, maxRounded, newScale, "0.######");
	}

	private double 				log(double value) {
		if (value <= 0)
			return Double.NaN;

		return Math.log(value) / Math.log(getLogarithmBase());
	}
	private double 				pow(double value) {
		return Math.pow(getLogarithmBase(), value);
	}

}
