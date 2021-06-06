package fr.javafx.scene.control;

import java.util.function.Function;

import fr.java.sdk.math.Numbers;
import fr.javafx.behavior.AdvancedSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import javafx.scene.layout.Region;

public class ClampableSlider extends Slider {

	public interface Visual extends fr.javafx.behavior.Visual<ClampableSlider> {

		public Region getTickAxis();
		public Region getTrack();
		public Region getThumb();
		public Region getStartMark();
		public Region getStopMark();

	}

	private BooleanProperty 		 popupProperty;
	private Function<Double, String> popupFormatter;

    private BooleanProperty 		 startEnabled;
	private DoubleProperty			 startMarkValue;

	private BooleanProperty 		 stopEnabled;
	private DoubleProperty			 stopMarkValue;

	public ClampableSlider() {
		super();
	}
	public ClampableSlider(double min, double max, double value) {
		super(min, max, value);
	}

	@Override
	protected Skin<ClampableSlider> createDefaultSkin() {
		return new AdvancedSkin<ClampableSlider>(this, ClampableSliderVisual::new, ClampableSliderBehavior::new);
	}

	public final DoubleProperty 	startProperty() {
		if(startMarkValue == null) {
			startMarkValue = new DoublePropertyBase(0) {

				@Override
				public Object getBean() {
					return ClampableSlider.this;
				}

				@Override
				public String getName() {
					return "start";
				}

			};
		}
		return startMarkValue;
	}
	public final double 			getStart() {
		return startMarkValue == null ? 0 : startMarkValue.get();
	}
	public final void 				setStart(double value) {
		startProperty().set(value);
	}

	public final DoubleProperty 	stopProperty() {
		if(stopMarkValue == null) {
			stopMarkValue = new DoublePropertyBase(getMax()) {

				@Override
				public Object getBean() {
					return ClampableSlider.this;
				}

				@Override
				public String getName() {
					return "stop";
				}

			};
		}
		return stopMarkValue;
	}
	public final double 			getStop() {
		return stopMarkValue == null ? getMax() : stopMarkValue.get();
	}
	public final void 				setStop(double value) {
		stopProperty().set(value);
	}

	public final BooleanProperty 	startClampableProperty() {
        if (startEnabled == null) {
        	startEnabled = new SimpleBooleanProperty(this, "startClampable", false);
        }
        return startEnabled;
    }
	public final boolean 			isStartClampable() {
        return startEnabled == null ? false : startEnabled.get();
    }
    public final void 				setStartClampable(boolean value) {
    	startClampableProperty().set(value);
    }

	public final BooleanProperty 	stopClampableProperty() {
        if (stopEnabled == null) {
        	stopEnabled = new SimpleBooleanProperty(this, "stopClampable", false);
        }
        return stopEnabled;
    }
	public final boolean 			isStopClampable() {
        return stopEnabled == null ? false : stopEnabled.get();
    }
    public final void 				setStopClampable(boolean value) {
    	stopClampableProperty().set(value);
    }

	public final BooleanProperty 	usePopUpProperty() {
        if (popupProperty == null) {
        	popupProperty = new SimpleBooleanProperty(this, "usePopUp", false);
        }
        return popupProperty;
    }
	public final boolean 			usePopUp() {
        return popupProperty == null ? false : popupProperty.get();
    }
    public final void 				setPopUp(boolean _enabled) {
    	usePopUpProperty().set(_enabled);
    }

    public final void 				setPopUpFormatter(Function<Double, String> _format) {
    	popupFormatter = _format;
    }
    public final Function<Double, String> getPopUpFormatter() {
    	return popupFormatter != null ? popupFormatter : d -> "?" + d;
    }

	public void 					forceLayout() {
		setNeedsLayout(true);
		layout();
	}

    void 							adjustStart(double newValue) {
        // figure out the "value" associated with the specified position
        final double _min = getMin();
        final double _max = getMax();
        if (_max <= _min) return;
        newValue = newValue < _min ? _min : newValue;
        newValue = newValue > _max ? _max : newValue;

        setStart(snapValueToTicks(newValue));
    }
    void 							adjustStop(double newValue) {
        // figure out the "value" associated with the specified position
        final double _min = getMin();
        final double _max = getMax();
        if (_max <= _min) return;
        newValue = newValue < _min ? _min : newValue;
        newValue = newValue > _max ? _max : newValue;

        setStop(snapValueToTicks(newValue));
    }
    private double 					snapValueToTicks(double val) {
        double v = val;
        if (isSnapToTicks()) {
            double tickSpacing = 0;
            // compute the nearest tick to this value
            if (getMinorTickCount() != 0) {
                tickSpacing = getMajorTickUnit() / (Math.max(getMinorTickCount(),0)+1);
            } else {
                tickSpacing = getMajorTickUnit();
            }
            int prevTick = (int)((v - getMin())/ tickSpacing);
            double prevTickValue = (prevTick) * tickSpacing + getMin();
            double nextTickValue = (prevTick + 1) * tickSpacing + getMin();
            v = Numbers.nearest(prevTickValue, v, nextTickValue);
        }
        return Numbers.clamp(getMin(), v, getMax());
    }

}