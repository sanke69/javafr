package fr.javafx.scene.control.viewport.planar.utils.minimap;

import fr.java.math.geometry.Viewport;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.behavior.Visual;
import fr.javafx.scene.control.AbstractFxControlWithFPS;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;

public class PlaneViewportMinimapControl extends AbstractFxControlWithFPS {
	PlaneViewportControl<?, ?>				control;

	private final ReadOnlyObjectProperty<? extends Viewport.TwoDims<?, ?>> viewportProperty;

	private final ObjectProperty<Paint> modelPaintProperty;
	private final ObjectProperty<Paint> windowPaintProperty;

	public PlaneViewportMinimapControl(PlaneViewportControl<?, ?> _viewport) {
		this(_viewport, null);
	}
	public PlaneViewportMinimapControl(PlaneViewportControl<?, ?> _viewport, Paint _model) {
		super(60);

		control				= _viewport;
		viewportProperty    = control.viewportProperty();

		modelPaintProperty  = new SimpleObjectProperty<Paint>(_model);
		windowPaintProperty = new SimpleObjectProperty<Paint>();

		viewportProperty.addListener(e -> requestRefresh());
	}

	@Override
	protected Skin<?> 							createDefaultSkin() {
		return new AdvancedSkin<PlaneViewportMinimapControl>(this, PlaneViewportMinimapVisual::new, PlaneViewportMinimapBehavior::new);
	}

	// TODO:: Optimize
	public void 									refresh() {
		Skin<?> skin = getSkin();

		if(skin instanceof AdvancedSkin<?>) {
			Visual<?> visual = ((AdvancedSkin<?>) skin).getVisual();
			if(visual instanceof PlaneViewportMinimapVisual)
				((PlaneViewportMinimapVisual) visual).refresh();
		}
	}

	public Viewport.TwoDims<?, ?> 				getViewport() {
		return viewportProperty.get();
	}

	public ObjectProperty<Paint> 				modelPaintProperty() {
		return modelPaintProperty;
	}
	public void 								setModelPaint(Paint _paint) {
		modelPaintProperty.set(_paint);
		requestRefresh();
	}
	public Paint 								getModelPaint() {
		return modelPaintProperty.get();
	}

	public ObjectProperty<Paint> 				windowPaintProperty() {
		return windowPaintProperty;
	}
	public void 								setWindowPaint(Paint _paint) {
		windowPaintProperty.set(_paint);
		requestRefresh();
	}
	public Paint 								getWindowPaint() {
		return windowPaintProperty.get();
	}

}
