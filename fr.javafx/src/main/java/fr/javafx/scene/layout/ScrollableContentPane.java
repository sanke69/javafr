package fr.javafx.scene.layout;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public class ScrollableContentPane extends ContentPane {
	static final private double ZOOM_MIN 				= 5;
	static final private double ZOOM_MAX 				= 2000; 
	private static final double KEYBOARD_MOVEMENT_DELTA = 0.05;

	private final DoubleProperty 	zoomProperty;
	private boolean					translate;
	private double					cx, cy;

	private ScrollPane 				scrollablePane;
	private final Pane				zoomGroup;
	private final Scale				scaleTransform;

	public ScrollableContentPane() {
		super();
		zoomProperty      = new SimpleDoubleProperty(100.0);
		scaleTransform    = new Scale(zoomProperty.get() / 100.0, zoomProperty.get() / 100.0, 0, 0);

		zoomGroup         = new Pane();
		zoomGroup.getTransforms().add(scaleTransform);

		scrollablePane = new ScrollPane();
		scrollablePane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollablePane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollablePane.setContent(zoomGroup);

		getChildren().add(scrollablePane);

		setBindsAndListeners();
	}
	public ScrollableContentPane(Pane _content) {
		this();
		setContentPane(_content);
	}

	public void setContentPane(Pane _node) {
		super.setContentPane(_node);
		zoomGroup.getChildren().clear();
		zoomGroup.getChildren().add(getContentPane());
	}

	public final DoubleProperty hvalueProperty() {
        return scrollablePane.vvalueProperty();
    }
	public final DoubleProperty vvalueProperty() {
        return scrollablePane.vvalueProperty();
    }
	
	public DoubleProperty 	zoomProperty() {
		return zoomProperty;
	}
	public double 			getZoomProperty() {
		return zoomProperty.doubleValue();
	}
	public void 			setZoomProperty(double _zoom) {
		zoomProperty.set(_zoom);
	}
	
	void setBindsAndListeners() {
		scrollablePane.prefWidthProperty().bind(widthProperty());
		scrollablePane.prefHeightProperty().bind(heightProperty());
		
		scaleTransform.xProperty().bind(zoomProperty.divide(100.0));
		scaleTransform.yProperty().bind(zoomProperty.divide(100.0));

		addEventFilter(ScrollEvent.ANY, (event) -> {
			if(!translate && event.isControlDown()) {
				double newZoom = zoomProperty.get() * (event.getDeltaY() > 0 ? 1.1 : 0.9);
				zoomProperty.set(newZoom > ZOOM_MAX ? ZOOM_MAX : newZoom < ZOOM_MIN ? ZOOM_MIN : newZoom);
			}
		});

		setOnDragDetected((event) -> {
			if(event.isMiddleButtonDown()) {
				cx = event.getSceneX();
				cy = event.getSceneY();
				translate = true;
				startFullDrag();
			}
		});
		setOnMouseDragReleased((event) -> {
			if(!event.isMiddleButtonDown()) {
				translate = false;
			}
		});

		setOnMouseDragOver((event) -> {
			double  dx = event.getX(),
					dy = event.getY();;

			if(translate) {
				double W = getWidth();
				double H = getHeight();

				double h = scrollablePane.getHvalue();
				double v = scrollablePane.getVvalue();

				scrollablePane.setHvalue(h - (dx - cx) / W * (scrollablePane.getHmax() - scrollablePane.getHmin()));
				scrollablePane.setVvalue(v - (dy - cy) / H * (scrollablePane.getVmax() - scrollablePane.getVmin()));

				cx = dx;
				cy = dy;
			}

		});

		setOnKeyPressed((event) -> {
			switch(event.getCode()) {
			case UP    : scrollablePane.setVvalue(scrollablePane.getVvalue() - KEYBOARD_MOVEMENT_DELTA); break;
			case RIGHT : scrollablePane.setHvalue(scrollablePane.getHvalue() + KEYBOARD_MOVEMENT_DELTA); break;
			case DOWN  : scrollablePane.setVvalue(scrollablePane.getVvalue() + KEYBOARD_MOVEMENT_DELTA); break;
			case LEFT  : scrollablePane.setHvalue(scrollablePane.getHvalue() - KEYBOARD_MOVEMENT_DELTA); break;
			case SPACE : zoomProperty.set(100.0); break;
			default    : break;
			}
		});

	}

}