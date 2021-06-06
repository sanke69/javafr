package fr.javafx.scene.control.viewport.planar.implementations.tiles.info;

import fr.java.patterns.tileable.TileProvider;
import fr.java.patterns.tileable.TileViewport;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.scene.control.viewport.planar.implementations.tiles.TileViewportControl;
import fr.javafx.scene.control.viewport.planar.utils.info.PlaneViewportInfoControl;
import javafx.scene.control.Skin;

public class TileViewportInfoControl extends PlaneViewportInfoControl {

	TileViewportControl control;
	TileProvider<?> 	tileProvider;

	public TileViewportInfoControl(final TileViewportControl _control) {
		this(_control.getViewport(), _control.getTileProvider());
		control = _control;
	}
	public TileViewportInfoControl(final TileViewport<?, ?> _viewport, final TileProvider<?> _provider) {
		super(_viewport);
		tileProvider  = _provider;
	}

	@Override
	public Skin<TileViewportInfoControl> 		createDefaultSkin() {
		return new AdvancedSkin<TileViewportInfoControl>(this, TileViewportInfoVisual::new);
	}

	public TileViewport<?, ?>  					getTileViewport() {
		if(control != null)
			return control.getViewport();
		if(getViewport() instanceof TileViewport)
			return (TileViewport<?, ?>) getViewport();
		return null;
	}
	public TileProvider<?>  					getTileProvider() {
		if(control != null)
			return control.getTileProvider();
		return tileProvider;
	}

}
