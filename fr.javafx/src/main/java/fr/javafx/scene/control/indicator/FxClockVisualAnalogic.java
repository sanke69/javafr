package fr.javafx.scene.control.indicator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import fr.javafx.behavior.Visual;

public class FxClockVisualAnalogic extends VBox implements Visual<FxClock> {

	private final FxClock control;

    final Group  	analogueClock;
    final Circle 	face;
    final Label  	brand;
    final Line   	hourHand;
    final Line   	minuteHand;
    final Line   	secondHand;
    final Line   	millisecondHand;
    final Circle 	spindle;
    final Group  	ticks;
    final Rotate 	hourRotate, minuteRotate, secondRotate, millisecondRotate;
    final Timeline 	hourTime, minuteTime, secondTime, millisecondTime;

    final Label 	digitalClock;
    final Timeline 	digitalTime;

    public FxClockVisualAnalogic(FxClock _control) {
    	super();
    	control = _control;

    	face              = buildClockFrame();
    	brand             = buildSubTitle();

    	hourHand          = buildHourHand();
	    hourHand.getTransforms().add(hourRotate = new Rotate());
    	minuteHand        = buildMinuteHand();
    	minuteHand.getTransforms().add(minuteRotate = new Rotate());
    	secondHand        = buildSecondHand();
    	secondHand.getTransforms().add(secondRotate = new Rotate());
    	millisecondHand   = buildMillisecondHand();
    	millisecondHand.getTransforms().add(millisecondRotate = new Rotate());
    	
    	spindle           = buildSpindle();
    	ticks             = buildTicks();
    	
    	analogueClock     = buildTicks();
    	analogueClock.getStylesheets().add(FxClock.defaultCss);
    	analogueClock.getChildren().addAll(face, brand, ticks, spindle, hourHand, minuteHand, secondHand, millisecondHand);

    	digitalClock      = buildDigitalClock();
    	digitalClock.getStylesheets().add(FxClock.defaultCss);

		hourTime = new Timeline(
				new KeyFrame(Duration.hours(12),
								new KeyValue(hourRotate.angleProperty(), hourRotate.angleProperty().doubleValue() + 360.0, Interpolator.LINEAR)));
	    hourTime.setCycleCount(Animation.INDEFINITE);
		minuteTime = new Timeline(
				new KeyFrame(Duration.minutes(60),
								new KeyValue(minuteRotate.angleProperty(), minuteRotate.angleProperty().doubleValue() + 360.0, Interpolator.LINEAR)));
	    minuteTime.setCycleCount(Animation.INDEFINITE);
		secondTime = new Timeline(
				new KeyFrame(Duration.seconds(60),
								new KeyValue(secondRotate.angleProperty(), secondRotate.angleProperty().doubleValue() + 360.0, Interpolator.LINEAR)));
	    secondTime.setCycleCount(Animation.INDEFINITE);
		millisecondTime = new Timeline(
				new KeyFrame(Duration.millis(1000),
								new KeyValue(millisecondRotate.angleProperty(), millisecondRotate.angleProperty().doubleValue() + 360.0, Interpolator.LINEAR)));
	    millisecondTime.setCycleCount(Animation.INDEFINITE);

		digitalTime = new Timeline(
				new KeyFrame(Duration.seconds(0), (ae) -> {
					Calendar calendar = GregorianCalendar.getInstance();
					String hourString   = calendar.get(Calendar.HOUR)   == 0 ? "12" : calendar.get(Calendar.HOUR) + "";
					String minuteString = calendar.get(Calendar.MINUTE) + "";
					String secondString = calendar.get(Calendar.SECOND) + "";
					String ampmString   = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
					digitalClock.setText(hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
				}),
				new KeyFrame(Duration.seconds(1)));
		digitalTime.setCycleCount(Animation.INDEFINITE);

    	setTimeCurrent();
    	startAnimations();

    	addGlowEffect();

    	setAlignment(Pos.CENTER);
    	getChildren().addAll(analogueClock, digitalClock);
    }

	@Override
	public FxClock getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {
		// nothing to do
	}

    protected Label buildSubTitle() {
        Label brand = new Label("o'Clock");
        brand.setId("brand");
        brand.layoutXProperty().bind(face.centerXProperty().subtract(brand.widthProperty().divide(2)));
        brand.layoutYProperty().bind(face.centerYProperty().add(face.radiusProperty().divide(2)));
        return brand;
	}
    
    protected Line buildHourHand() {
        Line hourHand = new Line(0, 0, 0, -50);
        hourHand.setTranslateX(100);   hourHand.setTranslateY(100);
        hourHand.setId("hourHand");
    	
        return hourHand;
    }
    protected Line buildMinuteHand() {
        final Line minuteHand = new Line(0, 0, 0, -75);
        minuteHand.setTranslateX(100); minuteHand.setTranslateY(100);
        minuteHand.setId("minuteHand");
    	
        return minuteHand;
    }
    protected Line buildSecondHand() {
        final Line secondHand = new Line(0, 15, 0, -88);
        secondHand.setTranslateX(100); secondHand.setTranslateY(100);
        secondHand.setId("secondHand");
    	
        return secondHand;
    }
    protected Line buildMillisecondHand() {
        final Line millisecondHand = new Line(0, 15, 0, -88);
        millisecondHand.setTranslateX(100); millisecondHand.setTranslateY(100);
        millisecondHand.setId("millisecondHand");
    	
        return millisecondHand;
    }

    protected Circle buildSpindle() {
        final Circle spindle = new Circle(100, 100, 5);
        spindle.setId("spindle");
    	return spindle;
	}
    
    protected Circle buildClockFrame() {
    	Circle face = new Circle(100, 100, 100);
    	face.setId("face");
    	return face;
	}
	protected Group buildTicks() {
	    Group ticks = new Group();
	    
		for(int i = 0; i < 12; i++) {
			Line tick = new Line(0, -83, 0, -93);
			tick.setTranslateX(100);
			tick.setTranslateY(100);
			tick.getStyleClass().add("tick");
			tick.getTransforms().add(new Rotate(i * (360.0 / 12)));
			ticks.getChildren().add(tick);
		}
		int N = 250, n = 25;
		for(int i = 0; i < N; i += N / n) {
			Line tick = new Line(0, -70, 0, -80);
			tick.setTranslateX(100);
			tick.setTranslateY(100);
			tick.getStyleClass().add("tick");
			tick.getTransforms().add(new Rotate(i * (360.0 / N)));
			ticks.getChildren().add(tick);
		}
		return ticks;
	}
	
	protected Label buildDigitalClock() {
		Label digitalClock = new Label();
        digitalClock.setId("digitalClock");
	    return digitalClock;
	}
	
	public void setTime(int _h, int _m, int _s, int _ms) {
	    final double seedMillisecondDegrees = _ms * (360.0 / 1000.0);
	    final double seedSecondDegrees      = (_s + seedMillisecondDegrees / 360.0) * (360.0 / 60.0);
	    final double seedMinuteDegrees      = (_m + seedSecondDegrees / 360.0) * (360.0 / 60.0);
	    final double seedHourDegrees        = (_h + seedMinuteDegrees / 360.0) * (360.0 / 12.0) ;

	    hourRotate.setAngle(seedHourDegrees);
	    minuteRotate.setAngle(seedMinuteDegrees);
	    secondRotate.setAngle(seedSecondDegrees);
	    millisecondRotate.setAngle(seedMillisecondDegrees);
	}
	
	public void setTimeCurrent() {
	    Calendar calendar                   = GregorianCalendar.getInstance();
	    final double seedMillisecond        = 0.0; //calendar.get(Calendar.MILLISECOND) * (360.0 / 1000.0);
	    final double seedSecondDegrees      = (calendar.get(Calendar.SECOND) + seedMillisecond / 360.0) * (360.0 / 60.0);
	    final double seedMinuteDegrees      = (calendar.get(Calendar.MINUTE) + seedSecondDegrees / 360.0) * (360.0 / 60.0);
	    final double seedHourDegrees        = (calendar.get(Calendar.HOUR)   + seedMinuteDegrees / 360.0) * (360.0 / 12.0) ;

	    hourRotate.setAngle(seedHourDegrees);
	    minuteRotate.setAngle(seedMinuteDegrees);
	    secondRotate.setAngle(seedSecondDegrees);
	    millisecondRotate.setAngle(seedMillisecond);
	}
	
	public void startAnimations() {
	    digitalTime.play();
	    millisecondTime.play();
	    secondTime.play();
	    minuteTime.play();
	    hourTime.play();
	}
	
	public void addGlowEffect() {
	    final Glow glow = new Glow();
	    analogueClock.setOnMouseEntered((me) -> analogueClock.setEffect(glow) );
	    analogueClock.setOnMouseExited((me) -> analogueClock.setEffect(null) );		
	}

	Stage stage;
	public void addExitOnClick() {
	    analogueClock.setOnMouseClicked((me) -> {
	          analogueClock.setMouseTransparent(true);
	          final FadeTransition fade = new FadeTransition(Duration.seconds(1.2), analogueClock);
	          fade.setOnFinished((ae) -> stage.close());
	          fade.setFromValue(1);
	          fade.setToValue(0);
	          fade.play();
	      });	
	}

}
