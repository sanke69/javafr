package fr.javafx.scene.control.viewport.planar.implementations.tiles.minimap;

import fr.java.patterns.tiled.TileViewport;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.scene.control.viewport.planar.implementations.tiles.TileViewportControl;
import fr.javafx.scene.control.viewport.planar.utils.minimap.PlaneViewportMinimapControl;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;

public class TileViewportMinimapControl extends PlaneViewportMinimapControl {

	public TileViewportMinimapControl(TileViewportControl _viewport) {
		this(_viewport, null);
	}
	public TileViewportMinimapControl(TileViewportControl _viewport, Paint _model) {
		super(_viewport, _model);
	}

	@Override
	protected Skin<?> 			createDefaultSkin() {
		return new AdvancedSkin<TileViewportMinimapControl>(this, TileViewportMinimapVisual::new);
	}

	public TileViewport<?, ?> 	getViewport() {
		return (TileViewport<?, ?>) super.getViewport();
	}

}
