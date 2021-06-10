package fr.javafx.temp.windows.skins;

import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;
import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.MutableWindowSkin;
import javafx.geometry.Bounds;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class FloatingWindowSkin extends Region implements MutableWindowSkin.Floating {
//	private static final int borderMagnet = 5;
//	private boolean RESIZE_BOTTOM;
//	private boolean RESIZE_RIGHT;
	
//	enum RESIZE_MODE { RESIZE, SCALE };

//	final WindowHeader header;
	final BorderPane   pane;
	final Pane         owner;
//	Node               content;
//	private boolean RESIZE_BOTTOM;
//	private boolean RESIZE_RIGHT;
	Bounds       bndDefault = null, bndCurrent = null; 
//	RESIZE_MODE  mode = RESIZE_MODE.RESIZE;

	boolean	     fullscreen;
	SimpleRectangle2D  lastKnownBounds;
	private MutableWindow window;

	public FloatingWindowSkin(MutableWindow _window) {
		super();
		owner   = null;
//		header  = new WindowHeader(_window);
		pane    = new BorderPane();
//		pane.setTop(header);
		/*
		if(_window.getHeader() != null)
			pane.setTop(_window.getHeader());
		pane.setCenter(_window.getContent());
		*/
		pane.setStyle("-fx-background-color: rgba(100, 100, 100, 1.0); -fx-border-width: 1; -fx-border-color: black");

		getChildren().add(pane);

		setLayoutX(50);
		setLayoutY(50);
	}

	@Override
	public void fill() {
		if(window.getHeader() != null)
			pane.setTop(window.getHeader());
		pane.setCenter(window.getContent());
	}
	@Override
	public void release() {
		pane.getChildren().removeAll(window.getHeader(), window.getContent());
	}

	public void maximize(boolean _enable) {
		fullscreen = _enable;
		
		if(fullscreen) {
			lastKnownBounds = new SimpleRectangle2D(
					layoutXProperty().doubleValue(),
					layoutYProperty().doubleValue(),
					widthProperty().doubleValue(),
					heightProperty().doubleValue());

			pane.setPrefSize(owner.widthProperty().doubleValue(), owner.heightProperty().doubleValue());
			pane.prefWidthProperty().bind(owner.widthProperty());
			pane.prefHeightProperty().bind(owner.heightProperty());
			layoutXProperty().set(0.0);
			layoutYProperty().set(0.0);
		} else {
			pane.prefWidthProperty().unbind();
			pane.prefHeightProperty().unbind();
			pane.setPrefSize(lastKnownBounds.getWidth(), lastKnownBounds.getHeight());
			layoutXProperty().set(lastKnownBounds.getMinX());
			layoutYProperty().set(lastKnownBounds.getMinY());
		}
	}

	public Pane getOwner() {
		return owner;
	}
	public Pane getFloatPane() {
		return owner;
	}
	
	public BorderPane getPane() {
		return pane;
	}
	@Override
	public MutableWindow getControl() {
		return window;
	}

	public void temp() {
//		pseudoClassStateChanged(FLOATING_PSEUDO_CLASS, this);
//		if(content != null) {
//			content.pseudoClassStateChanged(FLOATING_PSEUDO_CLASS, get());
//		}
	}
}
