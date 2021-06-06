package fr.javafx.scene.control.viewport.planar.implementations.control;

import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Viewport2D;
import fr.java.sdk.math.BoundingBoxes;
import fr.java.sdk.math.Dimensions;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.geometry.plane.PlaneViewportAdapter;
import fr.java.sdk.math.geometry.plane.PlaneViewportProjectors;
import fr.java.sdk.math.window.SimpleEdges2D;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.behavior.Visual;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class ControlViewportControl extends PlaneViewportControl {
	protected ObjectProperty<Control> modelProperty = new SimpleObjectProperty<Control>(null);

	public ControlViewportControl() {
		this(60, new PlaneViewportAdapter<Control, Point2D>());
	}
	public ControlViewportControl(int _fps, Viewport.TwoDims.Editable<Control, Point2D> _viewport) {
		super(_fps, _viewport);

		getViewport().setModelBounder	(m -> BoundingBoxes.of(0, 0, m.getWidth(), m.getHeight()));
		getViewport().setModelProjector	( PlaneViewportProjectors.newLinear(getViewport(), Points::of, Point2D::getX, Point2D::getY) );

		getViewport().setEdges			(new SimpleEdges2D());

		modelProperty().addListener((_obs, _old, _new) -> {
			Dimension.TwoDims window = Dimensions.of(getModel().getWidth(), getModel().getHeight());

			getViewport().setModel(_new);
			getViewport().setWindow(window);

//			setSize(window.getWidth(), window.getHeight());
		});
	}
	public ControlViewportControl(int _width, int _height) {
		this();
		setFixedSize(_width, _height);
	}

	@Override
	protected Skin<ControlViewportControl> 			createDefaultSkin() {
		return new AdvancedSkin<ControlViewportControl> (this,
														ControlViewportVisual::new);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Viewport2D.Editable<Control, Point2D> getViewport() {
		return (Viewport2D.Editable<Control, Point2D>) super.getViewport();
	}

	public ObjectProperty<Control> 					modelProperty() {
		return modelProperty;
	}
	public void 									setModel(Control _model) {
		if(_model == null) {
			modelProperty.set( null );
			return ;
		}

		modelProperty.set(_model);
		Platform.runLater(() -> viewportProperty().fireValueChangedEvent());
	}
	public Control 									getModel() {
		return modelProperty.get();
	}

	// TODO:: Optimize
	public void 									refresh() {
		Skin<?> skin = getSkin();

		if(skin instanceof AdvancedSkin<?>) {
			Visual<?> visual = ((AdvancedSkin<?>) skin).getVisual();
			if(visual instanceof ControlViewportVisual) {
				((ControlViewportVisual) visual).refresh();
			}	
		}
	}

}
