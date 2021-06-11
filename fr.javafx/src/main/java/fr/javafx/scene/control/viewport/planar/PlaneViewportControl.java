package fr.javafx.scene.control.viewport.planar;

import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Viewport;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.plane.PlaneViewportAdapter;
import fr.java.maths.geometry.types.BoundingBoxes;
import fr.java.maths.geometry.types.Points;
import fr.javafx.scene.control.viewport.ViewportControl;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.value.ChangeListener;

public abstract class PlaneViewportControl<MODEL, COORD extends Coordinate.TwoDims> extends ViewportControl {

	public interface Plugin<MODEL, COORD extends Coordinate.TwoDims> extends ViewportControl.Plugin {

		@SuppressWarnings("unchecked")
		public default void setViewportControl(ViewportControl _vpControl) {
			if(_vpControl instanceof PlaneViewportControl<?,?>)
				setViewportControl((PlaneViewportControl<MODEL, COORD>) _vpControl);
		}
		public void 		setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl);

	}

	protected final ChangeListener<? super Number> dimListener = (_obs, _old, _new) -> {
		viewportProperty().fireValueChangedEvent();
		requestRefresh();
	};

	double previousFixedWidth  = USE_COMPUTED_SIZE;
	double previousFixedHeight = USE_COMPUTED_SIZE;

	public    PlaneViewportControl() {
		this(60, new PlaneViewportAdapter<MODEL, COORD>());
	}
	public    PlaneViewportControl(int _fps) {
		this(_fps, new PlaneViewportAdapter<MODEL, COORD>());
	}
	public    PlaneViewportControl(Viewport.TwoDims.Editable<MODEL, COORD> _viewport) {
		this(60, _viewport);
	}
	protected PlaneViewportControl(int _fps, Viewport.TwoDims.Editable<MODEL, COORD> _viewport) {
		super(_fps, _viewport);

		getViewport()    . setWindow( BoundingBoxes.of(this) );
		widthProperty()  . addListener(dimListener);
		heightProperty() . addListener(dimListener);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ReadOnlyJavaBeanObjectProperty<Viewport.TwoDims.Editable<MODEL, COORD>>	viewportProperty() {
		return (ReadOnlyJavaBeanObjectProperty<Viewport.TwoDims.Editable<MODEL, COORD>>) super.viewportProperty();
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public Viewport.TwoDims.Editable<MODEL, COORD> 									getViewport() {
		return (Viewport.TwoDims.Editable<MODEL, COORD>) super.getViewport();
	}

	@Override
	public final 	void reset() {
		getViewport().scaleView(1d, Points.zero2());

		viewportProperty().fireValueChangedEvent();
		requestRefresh();
	}

	public final 	void translate(double _dx, double _dy) {
		if(_dx == 0 && _dy == 0)
			return;

		getViewport().translateView(_dx, _dy);

		viewportProperty().fireValueChangedEvent();
		requestRefresh();
	}
	public final 	void translate(Coordinate.TwoDims _delta) {
		translate(_delta.getFirst(), _delta.getSecond());
	}

	public final 	void zoom(boolean _in, double _cx, double _cy) {
		final double K     = 0.1;
		final double scale = getViewport().getViewScale();

		getViewport().scaleView(scale * (1 + K * (_in ? - 1 : 1)), _cx, _cy);

		viewportProperty().fireValueChangedEvent();
		requestRefresh();
	}
	public final 	void zoom(boolean _in, Coordinate.TwoDims _delta) {
		zoom(_in, _delta.getFirst(), _delta.getSecond());
	}

	public void 							setFixedSize() {
		super.setFixedSize(previousFixedWidth, previousFixedHeight);
	}
	public void 							unsetFixedSize() {
		setFixedSize(previousFixedWidth, previousFixedHeight);
		super.unsetFixedSize();
	}

	@Override
	public final    void fitToContent() {
		if(getViewport() == null)
			return ;

		Dimension.TwoDims model = getViewport().getModelBounds();

		if(getFixedWidth() != model.getWidth() && getFixedHeight() != model.getHeight()) {
			previousFixedWidth  = getFixedWidth();
			previousFixedHeight = getFixedHeight();
		}
		setFixedSize(model.getWidth(), model.getHeight());

		reset();
	}
	@Override
	public final    void fitContentToControl() {
		if(getViewport() == null)
			return ;

		getViewport().fitModelToWindow();

		viewportProperty().fireValueChangedEvent();
		requestRefresh();
	}

}
