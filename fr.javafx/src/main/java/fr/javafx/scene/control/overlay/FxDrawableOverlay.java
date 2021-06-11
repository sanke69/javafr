package fr.javafx.scene.control.overlay;

import fr.drawer.fx.Drawer4Fx;
import fr.java.draw.Drawable;
import fr.java.draw.DrawableArea;
import fr.javafx.scene.canvas.DrawableCanvas;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class FxDrawableOverlay extends StackPane implements DrawableArea {
	Region         overlayed;
	DrawableCanvas overlay;

	public FxDrawableOverlay() {
		super();
	}
	public FxDrawableOverlay(Region _overlayed) {
		this();
		setOverlayed(_overlayed);
	}

	public void		 				setOverlayed(Region _overlayed) {
		overlayed = _overlayed;
		overlay   = new DrawableCanvas(_overlayed.getWidth(), _overlayed.getHeight()) {{
			refreshColor = Color.TRANSPARENT;

			overlayed.widthProperty().addListener((_obs, _old, _new) -> setWidth(_new.doubleValue()));
			overlayed.heightProperty().addListener((_obs, _old, _new) -> setHeight(_new.doubleValue()));

			setMouseTransparent(true);
		}};
		getChildren().addAll(overlayed, overlay);
	}

	@Override
	public Drawer4Fx 				getDrawer() {
		return overlay.getDrawer();
	}

	@Override
	public void 					add(Drawable _drawable) {
		overlay.add(_drawable);
	}
	@Override
	public void 					remove(Drawable _drawable) {
		overlay.remove(_drawable);
	}

}
