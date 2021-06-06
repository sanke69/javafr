package fr.javafx.scene.control.viewport.planar.utils.info;

import fr.java.math.geometry.Viewport;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.scene.control.viewport.utils.info.ViewportInfoControl;
import javafx.scene.control.Skin;

public class PlaneViewportInfoControl extends ViewportInfoControl {

	public PlaneViewportInfoControl(final PlaneViewportControl _control) {
		super(_control.getViewport());
	}
	public PlaneViewportInfoControl(final Viewport.TwoDims<?, ?> _viewport) {
		super(_viewport);
	}

	@Override
	public Skin<? extends PlaneViewportInfoControl> createDefaultSkin() {
		return new AdvancedSkin<PlaneViewportInfoControl>(this, PlaneViewportInfoVisual::new);
	}

	public void					  					setViewport(Viewport<?, ?, ?, ?> _viewport) {
		if( ! ( _viewport instanceof Viewport.TwoDims ) )
			throw new IllegalArgumentException();
		
		setViewport((Viewport.TwoDims<?, ?>) _viewport);
	}
	public void					  					setViewport(Viewport.TwoDims<?, ?> _viewport) {
		super.setViewport(_viewport);
	}
	public Viewport.TwoDims<?, ?>  					getViewport() {
		return (Viewport.TwoDims<?, ?>) super.getViewport();
	}

}
