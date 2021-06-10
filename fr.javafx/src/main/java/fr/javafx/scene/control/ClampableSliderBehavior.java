package fr.javafx.scene.control;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.END;
import static javafx.scene.input.KeyCode.F4;
import static javafx.scene.input.KeyCode.HOME;
import static javafx.scene.input.KeyCode.KP_DOWN;
import static javafx.scene.input.KeyCode.KP_LEFT;
import static javafx.scene.input.KeyCode.KP_RIGHT;
import static javafx.scene.input.KeyCode.KP_UP;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

import java.util.ArrayList;
import java.util.List;

import fr.java.maths.Numbers;
import fr.javafx.behavior.Visual;
import fr.javafx.behavior.extended.BehaviorBase;
import fr.javafx.behavior.extended.bindings.KeyBinding;
import fr.javafx.behavior.extended.bindings.impl.OrientedKeyBinding;
import fr.javafx.behavior.extended.bindings.impl.SimpleKeyBinding;
import javafx.event.EventType;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class ClampableSliderBehavior extends BehaviorBase<ClampableSlider, ClampableSliderVisual, ClampableSliderBehavior> {
	
	public ClampableSliderBehavior(ClampableSlider slider, Visual<ClampableSlider> skin) {
		super(slider, (ClampableSliderVisual) skin);
		initialize();
	}
	public ClampableSliderBehavior(ClampableSlider slider, ClampableSliderVisual skin) {
		super(slider, skin);
		initialize();
	}

    private double 									preDragThumbPos;
    private Point2D 								dragStart;

    public void setBehaviorForTrack(boolean _enable) {
		Region track = getVisual().getTrack();
		Region thumb = getVisual().getThumb();

    	track.setOnMousePressed(me -> {
            if(!thumb.isPressed()) {
            	getVisual().getTrack().clicked(true);
                trackPress(me, (getControl().getOrientation() == Orientation.HORIZONTAL ? me.getX() : me.getY()) / getVisual().getTrack().length());
                getVisual().getTrack().clicked(false);
            }
        });

        track.setOnMouseDragged(me -> {
            if (!thumb.isPressed()) {
            	trackPress(me, (getControl().getOrientation() == Orientation.HORIZONTAL ? me.getX() : me.getY()) / getVisual().getTrack().length());
            }
        });
    }

    public void setBehaviorForCursor(boolean _enable) {
		Region cursor = getVisual().getThumb();

        cursor.setOnMousePressed(me -> {
            thumbPressed(me, 0.0f);
            dragStart       = cursor.localToParent(me.getX(), me.getY());
            preDragThumbPos = (getControl().getValue() - getControl().getMin()) / (getControl().getMax() - getControl().getMin());
        });
        cursor.setOnMouseReleased(me -> {
            thumbReleased(me);
        });
        cursor.setOnMouseDragged(me -> {
            Point2D cur     = cursor.localToParent(me.getX(), me.getY());
            double  dragPos = (getControl().getOrientation() == Orientation.HORIZONTAL) ? cur.getX() - dragStart.getX() : -(cur.getY() - dragStart.getY());
            thumbDragged(me, preDragThumbPos + dragPos / getVisual().getTrack().length());
        });
    }
    
	public void setBehaviorForStartMark(boolean _enable) {
		Region startMark = getVisual().getStartMark();

		startMark.setOnMousePressed(me -> {
			startPressed(me, 0.0f);
			dragStart       = startMark.localToParent(me.getX(), me.getY());
			preDragThumbPos = (getControl().getStart() - getControl().getMin()) / (getControl().getMax() - getControl().getMin());
		});
		startMark.setOnMouseReleased(me -> startReleased(me));
		startMark.setOnMouseDragged(me -> {
			Point2D cur     = startMark.localToParent(me.getX(), me.getY());
			double  dragPos = (getControl().getOrientation() == Orientation.HORIZONTAL) ? cur.getX() - dragStart.getX() : -(cur.getY() - dragStart.getY());
			startDragged(me, preDragThumbPos + dragPos / getVisual().getTrack().length());
		});
	}

	public void setBehaviorForStopMark(boolean _enable) {
		Region stopMark = getVisual().getStopMark();

		stopMark.setOnMousePressed(me -> {
			stopPressed(me, 0.0f);
			dragStart       = stopMark.localToParent(me.getX(), me.getY());
			preDragThumbPos = (getControl().getStop() - getControl().getMin()) / (getControl().getMax() - getControl().getMin());
		});
		stopMark.setOnMouseReleased(me -> stopReleased(me));
		stopMark.setOnMouseDragged(me -> {
			Point2D cur     = stopMark.localToParent(me.getX(), me.getY());
			double  dragPos = (getControl().getOrientation() == Orientation.HORIZONTAL) ? cur.getX() - dragStart.getX() : -(cur.getY() - dragStart.getY());
			stopDragged(me, preDragThumbPos + dragPos / getVisual().getTrack().length());
		});
	}
	
	public void initialize() {
       setBehaviorForTrack(true);
       setBehaviorForCursor(true);

       getControl().startClampableProperty() . addListener((_obs, _old, _new) -> setBehaviorForStartMark(_new));
       getControl().stopClampableProperty()  . addListener((_obs, _old, _new) -> setBehaviorForStopMark(_new));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	public void trackPress(MouseEvent e, double position) {
		// determine the percentage of the way between min and max
		// represented by this mouse event
		final Slider slider = getControl();
		// If not already focused, request focus
		if (!slider.isFocused())
			slider.requestFocus();
		if (slider.getOrientation().equals(Orientation.HORIZONTAL)) {
			slider.adjustValue(position * (slider.getMax() - slider.getMin()) + slider.getMin());
		} else {
			slider.adjustValue((1 - position) * (slider.getMax() - slider.getMin()) + slider.getMin());
		}
	}
	public void thumbPressed(MouseEvent e, double position) {
		// If not already focused, request focus
		final Slider slider = getControl();
		if (!slider.isFocused())
			slider.requestFocus();
		slider.setValueChanging(true);
	}
	public void thumbDragged(MouseEvent e, double position) {
		final Slider slider = getControl();
		slider.setValue(Numbers.clamp(slider.getMin(),
				(position * (slider.getMax() - slider.getMin())) + slider.getMin(), slider.getMax()));
	}
	public void thumbReleased(MouseEvent e) {
		final Slider slider = getControl();
		slider.setValueChanging(false);
		// RT-15207 When snapToTicks is true, slider value calculated in drag
		// is then snapped to the nearest tick on mouse release.
		slider.adjustValue(slider.getValue());
	}

	public void startPressed(MouseEvent e, double position) {
		// If not already focused, request focus
		final ClampableSlider slider = getControl();
		if (!slider.isFocused())
			slider.requestFocus();
	}
	public void startDragged(MouseEvent e, double position) {
		final ClampableSlider slider = getControl();
		slider.setStart(Numbers.clamp(slider.getMin(),
				(position * (slider.getMax() - slider.getMin())) + slider.getMin(), slider.getMax()));
	}
	public void startReleased(MouseEvent e) {
		final ClampableSlider slider = getControl();
		// RT-15207 When snapToTicks is true, slider value calculated in drag
		// is then snapped to the nearest tick on mouse release.
		slider.adjustStart(slider.getStart());
	}

	public void stopPressed(MouseEvent e, double position) {
		// If not already focused, request focus
		final ClampableSlider slider = getControl();
		if (!slider.isFocused())
			slider.requestFocus();
	}
	public void stopDragged(MouseEvent e, double position) {
		final ClampableSlider slider = getControl();
		slider.setStop(Numbers.clamp(slider.getMin(), (position * (slider.getMax() - slider.getMin())) + slider.getMin(), slider.getMax()));
	}
	public void stopReleased(MouseEvent e) {
		final ClampableSlider slider = getControl();
		// RT-15207 When snapToTicks is true, slider value calculated in drag
		// is then snapped to the nearest tick on mouse release.
		slider.adjustStop(slider.getStop());
	}

	void home() {
		final Slider slider = getControl();
		slider.adjustValue(slider.getMin());
	}

	void decrementValue() {
		final Slider slider = getControl();
		// RT-8634 If snapToTicks is true and block increment is less than
		// tick spacing, tick spacing is used as the decrement value.
		if (slider.isSnapToTicks()) {
			slider.adjustValue(slider.getValue() - computeIncrement());
		} else {
			slider.decrement();
		}

	}

	void end() {
		final Slider slider = getControl();
		slider.adjustValue(slider.getMax());
	}

	void incrementValue() {
		final Slider slider = getControl();
		// RT-8634 If snapToTicks is true and block increment is less than
		// tick spacing, tick spacing is used as the increment value.
		if (slider.isSnapToTicks()) {
			slider.adjustValue(slider.getValue() + computeIncrement());
		} else {
			slider.increment();
		}
	}

	// Used only if snapToTicks is true.
	double computeIncrement() {
		final Slider slider = getControl();
		double tickSpacing = 0;
		if (slider.getMinorTickCount() != 0) {
			tickSpacing = slider.getMajorTickUnit() / (Math.max(slider.getMinorTickCount(), 0) + 1);
		} else {
			tickSpacing = slider.getMajorTickUnit();
		}

		if (slider.getBlockIncrement() > 0 && slider.getBlockIncrement() < tickSpacing) {
			return tickSpacing;
		}

		return slider.getBlockIncrement();
	}

	public static class SliderKeyBinding<B extends BehaviorBase<?, ?, B>> extends OrientedKeyBinding<B> {
		public SliderKeyBinding(KeyCode code, String action) {
			super(code, action);
		}

		public SliderKeyBinding(KeyCode code, EventType<KeyEvent> type, String action) {
			super(code, type, action);
		}

		@Override
		public boolean getVertical(Control control) {
			return ((Slider) control).getOrientation() == Orientation.VERTICAL;
		}
	}

	static final <B extends BehaviorBase<?, ?, B>> List<KeyBinding<B>> sliderBindings() {
		List<KeyBinding<B>> SLIDER_BINDINGS = new ArrayList<KeyBinding<B>>();

		SLIDER_BINDINGS.add(new SimpleKeyBinding<B>(F4, "TraverseDebug").alt().ctrl().shift());

		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(LEFT, "DecrementValue"));
		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(KP_LEFT, "DecrementValue"));
		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(UP, "IncrementValue").vertical());
		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(KP_UP, "IncrementValue").vertical());
		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(RIGHT, "IncrementValue"));
		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(KP_RIGHT, "IncrementValue"));
		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(DOWN, "DecrementValue").vertical());
		SLIDER_BINDINGS.add(new SliderKeyBinding<B>(KP_DOWN, "DecrementValue").vertical());

		SLIDER_BINDINGS.add(new SimpleKeyBinding<B>(HOME, KeyEvent.KEY_RELEASED, "Home"));
		SLIDER_BINDINGS.add(new SimpleKeyBinding<B>(END, KeyEvent.KEY_RELEASED, "End"));
		
		return SLIDER_BINDINGS;
	}

	protected /* final */ String matchActionForEvent(KeyEvent e) {
		String action = super.matchActionForEvent(e);
		if (action != null) {
			if (e.getCode() == LEFT || e.getCode() == KP_LEFT) {
				if (getControl().getEffectiveNodeOrientation() == NodeOrientation.RIGHT_TO_LEFT) {
					action = getControl().getOrientation() == Orientation.HORIZONTAL ? "IncrementValue" : "DecrementValue";
				}
			} else if (e.getCode() == RIGHT || e.getCode() == KP_RIGHT) {
				if (getControl().getEffectiveNodeOrientation() == NodeOrientation.RIGHT_TO_LEFT) {
					action = getControl().getOrientation() == Orientation.HORIZONTAL ? "DecrementValue" : "IncrementValue";
				}
			}
		}
		return action;
	}

	@Override
	protected void callAction(String name) {
		switch(name) {
		case "Home"           : home(); return ;
		case "End"            : end(); return ;
		case "IncrementValue" : incrementValue(); return ;
		case "DecrementValue" : decrementValue(); return ;
		default               : super.callAction(name); return ;
		}
	}

}
