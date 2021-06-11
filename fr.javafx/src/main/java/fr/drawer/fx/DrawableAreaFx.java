package fr.drawer.fx;

import fr.java.draw.DrawableArea;
import javafx.scene.canvas.GraphicsContext;

public interface DrawableAreaFx extends DrawableArea {

	public Drawer4Fx 	   getDrawer();
	public GraphicsContext getGraphics();

}
