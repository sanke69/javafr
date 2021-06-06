package fr.javafx.temp;

import fr.javafx.stage.impl.goodies.NodeSpy;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ZoomableCanvas2 extends StackPane {
	private static final double KEYBOARD_MOVEMENT_DELTA = 0.05;

	// MEDIA VIEW
	private ScrollPane	scroller;
	private GridPane	holder;
	private Canvas		canvas;

	// MANAGE ZOOM PROPERTY AND TRANSLATION
	private DoubleProperty	zoomProperty;
	private double			cx, cy;
	private boolean			translate;

	public ZoomableCanvas2() {
		super();
		zoomProperty = new SimpleDoubleProperty(100);

		canvas   = new Canvas(800, 600);
		canvas.setMouseTransparent(true);
		canvas.setStyle("-fx-background-color: red;");

		holder   = new GridPane();
		holder.setAlignment(Pos.CENTER);
		holder.getChildren().add(canvas);

		scroller = new ScrollPane(holder);

		getChildren().addAll(scroller);

		setBinds();
		setListeners();

		addGrid();
	}

	public void addToHUD(Node _node) {
		getChildren().addAll(_node);
	}
	public void addToHUD(Node _node, Pos _align) {
		StackPane.setAlignment(_node, _align);
		getChildren().addAll(_node);
	}
	public void addLayer(Node _layout) {
		holder.getChildren().add(_layout);
	}
	public void addLayer(Node _layout, int _index) {
		holder.getChildren().add(_index, _layout);
	}
	
	public void setZoom(double _zoom) {
		zoomProperty.set(_zoom);
	}

	void setBinds() {
		holder.minWidthProperty().bind(Bindings.createDoubleBinding(() -> scroller.getViewportBounds().getWidth(), scroller.viewportBoundsProperty()));
		holder.minHeightProperty().bind(Bindings.createDoubleBinding(() -> scroller.getViewportBounds().getHeight(), scroller.viewportBoundsProperty()));
	}
	void setListeners() {
		zoomProperty.addListener((obs) -> {
			scroller.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scroller.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			canvas.setScaleX(zoomProperty.get() / 100.0);
			canvas.setScaleY(zoomProperty.get() / 100.0);
		});

		scroller.addEventFilter(ScrollEvent.ANY, (event) -> {
			if(!translate && event.isControlDown()) {
				if(event.getDeltaY() > 0) {
//					double newW = node.getImage().getWidth() * zoomProperty.get() * 1.1 / 100.0;
//					double newH = node.getImage().getHeight() * zoomProperty.get() * 1.1 / 100.0;
					zoomProperty.set(zoomProperty.get() * 1.1);

					canvas.setScaleX(zoomProperty.get() / 100.0);
					canvas.setScaleY(zoomProperty.get() / 100.0);
				} else if(event.getDeltaY() < 0) {
//					double newW = node.getImage().getWidth() * zoomProperty.get() * 1.1 / 100.0;
//					double newH = node.getImage().getHeight() * zoomProperty.get() * 1.1 / 100.0;
					zoomProperty.set(zoomProperty.get() / 1.1);

					canvas.setScaleX(zoomProperty.get() / 100.0);
					canvas.setScaleY(zoomProperty.get() / 100.0);
				}
			}
//			updateLabel(event.getX(), event.getY());
		});
		scroller.setOnKeyPressed((event) -> {
			switch(event.getCode()) {
			case UP:
				scroller.setVvalue(scroller.getVvalue() - KEYBOARD_MOVEMENT_DELTA);
				break;
			case RIGHT:
				scroller.setHvalue(scroller.getHvalue() + KEYBOARD_MOVEMENT_DELTA);
				break;
			case DOWN:
				scroller.setVvalue(scroller.getVvalue() + KEYBOARD_MOVEMENT_DELTA);
				break;
			case LEFT:
				scroller.setHvalue(scroller.getHvalue() - KEYBOARD_MOVEMENT_DELTA);
				break;
			case I:
				//label.setVisible(!label.isVisible());
				break;
			case SPACE:
				zoomProperty.set(100);
				canvas.setScaleX(1.0);
				canvas.setScaleY(1.0);
				scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
				scroller.setVbarPolicy(ScrollBarPolicy.NEVER);
				break;
			default:
				break;
			}
//			updateLabel(cx, cy);
		});
//		scroller.setOnMouseMoved((event) -> updateLabel(event.getX(), event.getY()));
		scroller.setOnDragDetected((event) -> {
			if(event.isMiddleButtonDown()) {
				translate = true;
				scroller.startFullDrag();
//				updateLabel(cx = event.getSceneX(), cy = event.getSceneY());
			}
		});
		scroller.setOnMouseDragOver((event) -> {
			if(translate) {
				scroller.setHvalue(scroller.getHvalue() - (event.getSceneX() - cx) / holder.getWidth());
				scroller.setVvalue(scroller.getVvalue() - (event.getSceneY() - cy) / holder.getHeight());
//				updateLabel(cx = event.getSceneX(), cy = event.getSceneY());
			}
		});
		scroller.setOnMouseDragReleased((event) -> translate = event.isMiddleButtonDown());
	}

    public void addGrid() {

        double w = canvas.getBoundsInLocal().getWidth();
        double h = canvas.getBoundsInLocal().getHeight();

        // don't catch mouse events
        canvas.setMouseTransparent(true);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.RED);
        gc.fillRect(0, 0, w, h);
       
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);

        // draw grid lines
        double offset = 50;
        for( double i=offset; i < w; i+=offset) {
            gc.strokeLine( i, 0, i, h);
            gc.strokeLine( 0, i, w, i);
        }

        canvas.toBack();
    }
}