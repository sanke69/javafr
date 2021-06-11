package fr.javafx.scene.control.viewport.planar.implementations.group;

import java.util.function.Function;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Viewport2D;
import fr.java.maths.geometry.plane.PlaneViewportAdapter;
import fr.java.maths.geometry.plane.PlaneViewportProjectors;
import fr.java.maths.geometry.types.BoundingBoxes;
import fr.java.maths.geometry.types.Points;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.behavior.Visual;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

public class GroupViewportControl extends PlaneViewportControl {

	protected ObjectProperty<Group>	modelProperty   = new SimpleObjectProperty<Group>(null);
	protected ObjectProperty<Color>	backgroundColor = new SimpleObjectProperty<Color>(new Color(69/255.0, 169/255.0, 69/255.0, 0.85));

	protected final Function<Group, BoundingBox.TwoDims> groupBounds = (group) -> {
		double x = group.getBoundsInLocal().getMinX(), 
			   y = group.getBoundsInLocal().getMinY(),
			   w = group.getBoundsInLocal().getWidth(), 
			   h = group.getBoundsInLocal().getHeight();

		return BoundingBoxes.of(x, y, w, h);
	};

	public GroupViewportControl() {
		this(60, new PlaneViewportAdapter<Group, Point2D>());
	}
	public GroupViewportControl(int _fps, Viewport.TwoDims.Editable<Group, Point2D> _viewport) {
		super(_fps, _viewport);

		getViewport().setModelBounder   ( groupBounds);
		getViewport().setModelProjector	( PlaneViewportProjectors.newLinear(getViewport(), Points::of, Point2D::getX, Point2D::getY) );

		getViewport().setWindow			( BoundingBoxes.of(this) );

		modelProperty().addListener((_obs, _old, _new) -> getViewport().setModel(_new));
	}
	public GroupViewportControl(int _width, int _height) {
		this();
		setFixedSize(_width, _height);
	}

	@Override
	protected Skin<GroupViewportControl> 	createDefaultSkin() {
		return new AdvancedSkin<GroupViewportControl> (this,
														GroupViewportVisual::new);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Viewport2D.Editable<Group, Point2D> 	getViewport() {
		return (Viewport2D.Editable<Group, Point2D>) super.getViewport();
	}

	public ObjectProperty<Color> 					backgroundColorProperty() {
		return backgroundColor;
	}
	public void 									setBackgroundColor(Color _backgroundColor) {
		backgroundColor.set(_backgroundColor);
	}
	public Color 									getBackgroundColor() {
		return backgroundColor.get();
	}

	public ObjectProperty<Group> 					modelProperty() {
		return modelProperty;
	}
	public void 									setModel(Group _model) {
		if(_model == null) {
			modelProperty.set( null );
			return ;
		}

		modelProperty.set(_model);
		Platform.runLater(() -> viewportProperty().fireValueChangedEvent());
	}
	public Group 									getModel() {
		return modelProperty.get();
	}

	// TODO:: Optimize
	public void 									refresh() {
		Skin<?> skin = getSkin();

		if(skin instanceof AdvancedSkin<?>) {
			Visual<?> visual = ((AdvancedSkin<?>) skin).getVisual();
			if(visual instanceof GroupViewportVisual) {
				((GroupViewportVisual) visual).refresh();
			}	
		}
	}

}
