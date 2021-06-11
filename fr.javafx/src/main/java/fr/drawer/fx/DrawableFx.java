package fr.drawer.fx;

import fr.java.draw.Drawable;
import fr.java.draw.DrawableArea;
import javafx.scene.canvas.GraphicsContext;

public interface DrawableFx extends Drawable {

	public default void draw(DrawableArea _area) {
//		throw new WorngAccessExcption();
	}
	public void draw(GraphicsContext _gc);

}
