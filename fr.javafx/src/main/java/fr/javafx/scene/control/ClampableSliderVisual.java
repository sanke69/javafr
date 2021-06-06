package fr.javafx.scene.control;

import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.StringConverter;

import fr.javafx.behavior.extended.VisualBase;

public class ClampableSliderVisual extends VisualBase<ClampableSlider> implements ClampableSlider.Visual {
	private final StringConverter<Number> stringConverterWrapper = new StringConverter<Number>() {
		Slider slider = getSkinnable();

		@Override
		public String toString(Number object) {
			return (object != null) ? slider.getLabelFormatter().toString(object.doubleValue()) : "";
		}

		@Override
		public Number fromString(String string) {
			return slider.getLabelFormatter().fromString(string);
		}
	};

	private boolean showTickMarks;
	private double trackToTickGap = 2;

	private Track         track;
	private Thumb         cursor;
	private StackPane     cursor_start, cursor_stop;
	private NumberAxis    tickAxis;

	private Popup         popup;
	
	public ClampableSliderVisual(ClampableSlider slider) {
		super(slider);

		setShowTickMarks(getSkinnable().isShowTickMarks(), getSkinnable().isShowTickLabels());

		registerChangeListener(slider.minProperty(), 			"MIN");
		registerChangeListener(slider.maxProperty(), 			"MAX");
		registerChangeListener(slider.valueProperty(), 			"VALUE");
		registerChangeListener(slider.orientationProperty(), 	"ORIENTATION");
		registerChangeListener(slider.showTickMarksProperty(), 	"SHOW_TICK_MARKS");
		registerChangeListener(slider.showTickLabelsProperty(), "SHOW_TICK_LABELS");
		registerChangeListener(slider.majorTickUnitProperty(), 	"MAJOR_TICK_UNIT");
		registerChangeListener(slider.minorTickCountProperty(), "MINOR_TICK_COUNT");
		registerChangeListener(slider.labelFormatterProperty(), "TICK_LABEL_FORMATTER");
		registerChangeListener(slider.snapToTicksProperty(), 	"SNAP_TO_TICKS");

		registerChangeListener(slider.startClampableProperty(), "ENABLE_START");
		registerChangeListener(slider.startProperty(), 			"START");
		registerChangeListener(slider.stopClampableProperty(), 	"ENABLE_STOP");
		registerChangeListener(slider.stopProperty(), 			"STOP");
		
		slider.usePopUpProperty().addListener((_obs, _old, _new) -> {
			if(_new) {
				if(popup == null)
					popup = new Popup();
				popup.attachTo(getSkinnable());
			} else {
				if(popup != null)
					popup.detachFrom(getSkinnable());
			}
		});
		slider.usePopUpProperty().set(true);
	}

	@Override
	public void 						handleControlPropertyChanged(String p) {
		super.handleControlPropertyChanged(p);

		ClampableSlider slider = getSkinnable();

		switch(p) {
		case "ORIENTATION" : 			if (showTickMarks && tickAxis != null)
											tickAxis.setSide(slider.getOrientation() == Orientation.VERTICAL ? Side.RIGHT : (slider.getOrientation() == null) ? Side.RIGHT : Side.BOTTOM);
										getSkinnable().requestLayout();
										break;
		case "VALUE" :					positionThumb(getTrack().clicked()); break;
		case "START" :					positionThumb(false);
										positionStart(false);
										break;
		case "ENABLE_START" :			populate(); break;
		case "STOP" :					positionThumb(false);
										positionStop(false);
										break;
		case "ENABLE_STOP" :			populate(); break;
		case "MIN" :					if (showTickMarks && tickAxis != null)
											tickAxis.setLowerBound(slider.getMin());
										getSkinnable().requestLayout();
										break;
		case "MAX" :					if (showTickMarks && tickAxis != null)
											tickAxis.setUpperBound(slider.getMax());
										getSkinnable().requestLayout();
										break;
		case "SHOW_TICK_MARKS" :
		case "SHOW_TICK_LABELS" :		setShowTickMarks(slider.isShowTickMarks(), slider.isShowTickLabels());
										break;
		case "MAJOR_TICK_UNIT" :		if (tickAxis != null) {
											tickAxis.setTickUnit(slider.getMajorTickUnit());
											getSkinnable().requestLayout();
										}
										break;
		case "MINOR_TICK_COUNT" :		if (tickAxis != null) {
											tickAxis.setMinorTickCount(Math.max(slider.getMinorTickCount(), 0) + 1);
											getSkinnable().requestLayout();
										}
										break;
		case "TICK_LABEL_FORMATTER" :	if (tickAxis != null)
											if (slider.getLabelFormatter() == null) {
												tickAxis.setTickLabelFormatter(null);
											} else {
												tickAxis.setTickLabelFormatter(stringConverterWrapper);
												tickAxis.requestAxisLayout();
											}
										break;
		case "SNAP_TO_TICKS" :			slider.adjustValue(slider.getValue()); break;
		}

	}

	@Override
	public final Track 					getTrack() {
		if (track == null)
			track = new Track();
		return track;
	}
	@Override
	public final Thumb 					getThumb() {
		if (cursor == null)
			cursor = new Thumb();
		return cursor;
	}
	@Override
	public final Region 				getStartMark() {
		if (cursor_start == null) {
			cursor_start = new Thumb("thumb-start");
			cursor_start.setStyle("-fx-background-color: green;");
		}
		return cursor_start;
	}
	@Override
	public final Region 				getStopMark() {
		if (cursor_stop == null) {
			cursor_stop = new Thumb("thumb-stop");
			cursor_stop.setStyle("-fx-background-color: red;");
		}
		return cursor_stop;
	}
	@Override
	public final NumberAxis 			getTickAxis() {
		return tickAxis;
	}

	void 								positionThumb(final boolean animate) {
		ClampableSlider s = getSkinnable();

		if (s.getValue() > s.getMax())
			return;

		boolean horizontal = s.getOrientation() == Orientation.HORIZONTAL;

		final double endX = (horizontal) ? getTrack().start() + (((getTrack().length() * ((s.getValue() - s.getMin()) / (s.getMax() - s.getMin()))) - getThumb().getWidth() / 2)) : getThumb().getX();
		final double endY = (horizontal) ? getThumb().getY() : getSkinnable().snappedTopInset() + getTrack().length() - (getTrack().length() * ((s.getValue() - s.getMin()) / (s.getMax() - s.getMin())));

		if (animate) {
			final double startX = getThumb().getLayoutX();
			final double startY = getThumb().getLayoutY();
			Transition transition = new Transition() {
				{ setCycleDuration(Duration.millis(200)); }

				@Override
				protected void interpolate(double frac) {
					if (!Double.isNaN(startX))
						getThumb().setLayoutX(startX + frac * (endX - startX));
					if (!Double.isNaN(startY))
						getThumb().setLayoutY(startY + frac * (endY - startY));
				}
			};
			transition.play();
		} else {
			getThumb().setLayoutX(endX);
			getThumb().setLayoutY(endY);
		}

	}
	void 								positionStart(final boolean animate) {
		ClampableSlider s = getSkinnable();

		if (s.getStart() > s.getMax())
			return;

		boolean horizontal = s.getOrientation() == Orientation.HORIZONTAL;

		final double endX = (horizontal)
				? getTrack().start()
						+ (((getTrack().length() * ((s.getStart() - s.getMin()) / (s.getMax() - s.getMin())))
								- getThumb().getWidth() / 2))
				: getThumb().getX();
		final double endY = (horizontal) ? getThumb().getY()
				: getSkinnable().snappedTopInset() + getTrack().length()
						- (getTrack().length() * ((s.getStart() - s.getMin()) / (s.getMax() - s.getMin())));

		if (animate) {
			final double startX = getStartMark().getLayoutX();
			final double startY = getStartMark().getLayoutY();
			Transition transition = new Transition() {
				{
					setCycleDuration(Duration.millis(200));
				}

				@Override
				protected void interpolate(double frac) {
					if (!Double.isNaN(startX))
						getStartMark().setLayoutX(startX + frac * (endX - startX));
					if (!Double.isNaN(startY))
						getStartMark().setLayoutY(startY + frac * (endY - startY));
				}
			};
			transition.play();
		} else {
			getStartMark().setLayoutX(endX);
			getStartMark().setLayoutY(endY);
		}
	}
	void 								positionStop(final boolean animate) {
		ClampableSlider s = getSkinnable();

		if (s.getStop() > s.getMax())
			return;

		boolean horizontal = s.getOrientation() == Orientation.HORIZONTAL;

		final double endX = (horizontal)
				? getTrack().start()
						+ (((getTrack().length() * ((s.getStop() - s.getMin()) / (s.getMax() - s.getMin())))
								- getThumb().getWidth() / 2))
				: getThumb().getX();
		final double endY = (horizontal) ? getThumb().getY()
				: getSkinnable().snappedTopInset() + getTrack().length()
						- (getTrack().length() * ((s.getStop() - s.getMin()) / (s.getMax() - s.getMin())));

		if (animate) {
			final double startX = getStopMark().getLayoutX();
			final double startY = getStopMark().getLayoutY();
			Transition transition = new Transition() {
				{
					setCycleDuration(Duration.millis(200));
				}

				@Override
				protected void interpolate(double frac) {
					if (!Double.isNaN(startX))
						getStopMark().setLayoutX(startX + frac * (endX - startX));
					if (!Double.isNaN(startY))
						getStopMark().setLayoutY(startY + frac * (endY - startY));
				}
			};
			transition.play();
		} else {
			getStopMark().setLayoutX(endX);
			getStopMark().setLayoutY(endY);
		}
	}

	public void 						layoutChildren(final double x, final double y, final double w, final double h) {
		final ClampableSlider s = getSkinnable();

		getThumb().resize(s.snapSizeX(getThumb().prefWidth(-1)), s.snapSizeY(getThumb().prefHeight(-1)));

		if (getStartMark() != null)
			getStartMark().resize(getThumb().getWidth() - 2, getThumb().getHeight() - 2);

		if (getStopMark() != null)
			getStopMark().resize(getThumb().getWidth() - 2, getThumb().getHeight() - 2);

		double trackRadius = getTrack().getBackground() == null ? 0 : getTrack().getBackground().getFills().size() > 0 ? getTrack().getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius() : 0;

		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			double tickLineHeight    = (showTickMarks) ? tickAxis.prefHeight(-1) : 0;
			double trackHeight       = s.snapSizeX(getTrack().prefHeight(-1));
			double trackAreaHeight   = Math.max(trackHeight, getThumb().getHeight());
			double totalHeightNeeded = trackAreaHeight + ((showTickMarks) ? trackToTickGap + tickLineHeight : 0);
			double startY            = y + ((h - totalHeightNeeded) / 2);

			getTrack().length(s.snapSizeX(w - getThumb().getWidth()));
			getTrack().start(s.snapPositionX(x + (getThumb().getWidth() / 2)));

			double trackTop = (int) (startY + ((trackAreaHeight - trackHeight) / 2));
			getThumb().getY((int) (startY + ((trackAreaHeight - getThumb().getHeight()) / 2)));

			positionThumb(false);
			if (getSkinnable().isStartClampable())
				positionStart(false);
			if (getSkinnable().isStopClampable())
				positionStop(false);

			getTrack().resizeRelocate((int) (getTrack().start() - trackRadius), trackTop, (int) (getTrack().length() + trackRadius + trackRadius), trackHeight);

			// layout tick line
			if (showTickMarks) {
				tickAxis.setLayoutX(getTrack().start());
				tickAxis.setLayoutY(trackTop + trackHeight + trackToTickGap);
				tickAxis.resize(getTrack().length(), tickLineHeight);
				tickAxis.requestAxisLayout();
			} else {
				if (tickAxis != null) {
					tickAxis.resize(0, 0);
					tickAxis.requestAxisLayout();
				}
				tickAxis = null;
			}
		} else {
			double tickLineWidth    = (showTickMarks) ? tickAxis.prefWidth(-1) : 0;
			double trackWidth       = s.snapSizeX(getTrack().prefWidth(-1));
			double trackAreaWidth   = Math.max(trackWidth, getThumb().getWidth());
			double totalWidthNeeded = trackAreaWidth + ((showTickMarks) ? trackToTickGap + tickLineWidth : 0);
			double startX           = x + ((w - totalWidthNeeded) / 2);

			getTrack().length(s.snapSizeY(h - getThumb().getHeight()));
			getTrack().start(s.snapPositionY(y + (getThumb().getHeight() / 2)));

			double trackLeft = (int) (startX + ((trackAreaWidth - trackWidth) / 2));
			getThumb().setX((int) (startX + ((trackAreaWidth - getThumb().getWidth()/* thumbWidth */) / 2)));

			positionThumb(false);
			positionStart(false);
			positionStop(false);

			getTrack().resizeRelocate(trackLeft, (int) (getTrack().start() - trackRadius), trackWidth, (int) (getTrack().length() + trackRadius + trackRadius));

			// layout tick line
			if (showTickMarks) {
				tickAxis.setLayoutX(trackLeft + trackWidth + trackToTickGap);
				tickAxis.setLayoutY(getTrack().start());
				tickAxis.resize(tickLineWidth, getTrack().length());
				tickAxis.requestAxisLayout();
			} else {
				if (tickAxis != null) {
					tickAxis.resize(0, 0);
					tickAxis.requestAxisLayout();
				}
				tickAxis = null;
			}
		}
	}

	protected double 					computeMinWidth(double height) {
		final Slider s = getSkinnable();

		if (s.getOrientation() == Orientation.HORIZONTAL)
			return (leftMarging() + getTrack().minLength() + getThumb().minWidth(-1) + rightMarging());
		else
			return (leftMarging() + getThumb().prefWidth(-1) + rightMarging());
	}
	protected double 					computeMinHeight(double width) {
		final Slider s = getSkinnable();

		if (s.getOrientation() == Orientation.HORIZONTAL) {
			double axisHeight = showTickMarks ? (tickAxis.prefHeight(-1) + trackToTickGap) : 0;
			return topMarging() + getThumb().prefHeight(-1) + axisHeight + bottomMarging();
		} else {
			return topMarging() + getTrack().minLength() + getThumb().prefHeight(-1) + bottomMarging();
		}
	}

	protected double 					computePrefWidth(double height) {
		final Slider s = getSkinnable();
		if (s.getOrientation() == Orientation.HORIZONTAL) {
			if (showTickMarks) {
				return Math.max(140, tickAxis.prefWidth(-1));
			} else {
				return 140;
			}
		} else {
			double axisWidth = showTickMarks ? (tickAxis.prefWidth(-1) + trackToTickGap) : 0;
			return leftMarging() + Math.max(getThumb().prefWidth(-1), getTrack().prefWidth(-1)) + axisWidth
					+ rightMarging();
		}
	}
	protected double 					computePrefHeight(double width) {
		final Slider s = getSkinnable();
		if (s.getOrientation() == Orientation.HORIZONTAL) {
			return topMarging() + Math.max(getThumb().prefHeight(-1), getTrack().prefHeight(-1))
					+ ((showTickMarks) ? (trackToTickGap + tickAxis.prefHeight(-1)) : 0) + bottomMarging();
		} else {
			if (showTickMarks) {
				return Math.max(140, tickAxis.prefHeight(-1));
			} else {
				return 140;
			}
		}
	}

	protected double 					computeMaxWidth(double height) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return Double.MAX_VALUE;
		} else {
			return getSkinnable().prefWidth(-1);
		}
	}
	protected double 					computeMaxHeight(double width) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return getSkinnable().prefHeight(width);
		} else {
			return Double.MAX_VALUE;
		}
	}

	private void 						populate() {
		getChildren().clear();

		if (getSkinnable().isStartClampable() && getSkinnable().isStopClampable())
			getChildren().addAll(getTrack(), getStartMark(), getStopMark(), getThumb());
		else if (getSkinnable().isStartClampable())
			getChildren().addAll(getTrack(), getStartMark(), getThumb());
		else if (getSkinnable().isStopClampable())
			getChildren().addAll(getTrack(), getStopMark(), getThumb());
		else
			getChildren().addAll(getTrack(), getThumb());

		getSkinnable().requestLayout();
	}

	private void 						setShowTickMarks(boolean ticksVisible, boolean labelsVisible) {
		showTickMarks = (ticksVisible || labelsVisible);
		Slider slider = getSkinnable();
		if (showTickMarks) {
			if (tickAxis == null) {
				tickAxis = new NumberAxis();
				tickAxis.setAutoRanging(false);
				tickAxis.setSide(slider.getOrientation() == Orientation.VERTICAL ? Side.RIGHT : (slider.getOrientation() == null) ? Side.RIGHT : Side.BOTTOM);
				tickAxis.setUpperBound(slider.getMax());
				tickAxis.setLowerBound(slider.getMin());
				tickAxis.setTickUnit(slider.getMajorTickUnit());
				tickAxis.setTickMarkVisible(ticksVisible);
				tickAxis.setTickLabelsVisible(labelsVisible);
				tickAxis.setMinorTickVisible(ticksVisible);
				tickAxis.setMinorTickCount(Math.max(slider.getMinorTickCount(), 0) + 1);
//              if (slider.getLabelFormatter() != null) {
				tickAxis.setTickLabelFormatter(stringConverterWrapper);
//              }
				getChildren().clear();
				getChildren().addAll(tickAxis, getTrack(), getStartMark(), getStopMark(), getThumb());
			} else {
				tickAxis.setTickLabelsVisible(labelsVisible);
				tickAxis.setTickMarkVisible(ticksVisible);
				tickAxis.setMinorTickVisible(ticksVisible);
			}
		} else {
			populate();
		}

		getSkinnable().requestLayout();
	}


	public class 	Popup extends javafx.stage.Popup {
		static final int offset	= 30;

		final Label label;

		public Popup() {
			super();
			label = new Label();
			getContent().add(label);
		}

		public void attachTo(final Slider _slider) {
			_slider.addEventFilter(MouseEvent.MOUSE_ENTERED, onMouseEntered(_slider));
			_slider.addEventFilter(MouseEvent.MOUSE_EXITED,  onMouseExited(_slider));
			_slider.addEventFilter(MouseEvent.MOUSE_MOVED,   onMouseMoved(_slider));
		}
		public void detachFrom(final Slider _slider) {
			_slider.removeEventFilter(MouseEvent.MOUSE_ENTERED, onMouseEntered(_slider));
			_slider.removeEventFilter(MouseEvent.MOUSE_EXITED,  onMouseExited(_slider));
			_slider.removeEventFilter(MouseEvent.MOUSE_MOVED,   onMouseMoved(_slider));
		}

		private EventHandler<? super MouseEvent> onMouseEntered(Slider _slider) {
			return e -> show(_slider, e.getScreenX(), e.getScreenY() + offset);
		}
		private EventHandler<? super MouseEvent> onMouseExited(Slider _slider) {
			return e -> hide();
		}
		private EventHandler<? super MouseEvent> onMouseMoved(Slider _slider) {
			return e -> {
				NumberAxis axis = (NumberAxis) _slider.lookup(".axis");
				StackPane track = (StackPane) _slider.lookup(".track");
				if(_slider.showTickMarksProperty().get()) {
					Point2D locationInAxis = axis.sceneToLocal(e.getSceneX(), e.getSceneY());
					boolean isHorizontal   = _slider.getOrientation() == Orientation.HORIZONTAL;
					double  mouseX         = isHorizontal ? locationInAxis.getX() : locationInAxis.getY();
					double  value          = axis.getValueForDisplay(mouseX).doubleValue();
					if(value >= _slider.getMin() && value <= _slider.getMax()) {
						label.setText(getSkinnable().getPopUpFormatter().apply(value));
					} else {
						label.setText("Value: ---");
					}

				} else {
					Point2D locationInAxis = track.sceneToLocal(e.getSceneX(), e.getSceneY());
					double  mouseX         = locationInAxis.getX();
					double  trackLength    = track.getWidth();
					double  percent        = mouseX / trackLength;
					double  value          = _slider.getMin() + ((_slider.getMax() - _slider.getMin()) * percent);
					if(value >= _slider.getMin() && value <= _slider.getMax()) {
						label.setText(getSkinnable().getPopUpFormatter().apply(value));
					} else {
						label.setText("Value: ---");
					}
				}
				setAnchorX(e.getScreenX());
				setAnchorY(e.getScreenY() + offset);
			};
		}

	}

	public class 	Thumb extends StackPane {

		public Thumb() {
			super();
			getStyleClass().setAll("thumb");
			setAccessibleRole(AccessibleRole.THUMB);
		}

		public Thumb(String _extraClass) {
			super();
			getStyleClass().setAll("thumb", _extraClass);
		}

		public void setX(double _x) {
			super.setLayoutX(_x);
		}

		public double getX() {
			return super.getLayoutX();
		}

		public void getY(double _y) {
			super.setLayoutX(_y);
		}

		public double getY() {
			return super.getLayoutY();
		}

		@Override
		public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
			switch (attribute) {
			case VALUE:
				if (getAccessibleRole() == AccessibleRole.THUMB)
					return getSkinnable().getValue();
				else
					return super.queryAccessibleAttribute(attribute, parameters);
			default:
				return super.queryAccessibleAttribute(attribute, parameters);
			}
		}

	}
	public class 	Track extends StackPane {
		double start, length;
		boolean clicked;

		public Track() {
			super();
			getStyleClass().setAll("track");
		}

		public void clicked(boolean _true) {
			clicked = _true;
		}

		public boolean clicked() {
			return clicked;
		}

		public void start(double _s) {
			start = _s;
		}

		public double start() {
			return start;
		}

		public double minLength() {
			return 2 * getThumb().prefWidth(-1);
		}

		public void length(double _l) {
			length = _l;
			setPrefSize(length, Region.USE_COMPUTED_SIZE);
		}

		public double length() {
			return length;
		}

	}

}
