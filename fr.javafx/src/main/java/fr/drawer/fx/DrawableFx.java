package fr.drawer.fx;

import fr.java.patterns.drawable.Drawable;
import fr.java.patterns.drawable.DrawableArea;
import javafx.scene.canvas.GraphicsContext;

public interface DrawableFx extends Drawable {

	public default void draw(DrawableArea _area) {
//		throw new WorngAccessExcption();
	}
	public void draw(GraphicsContext _gc);

}
