package fr.javafx.scene.control.viewport.planar.implementations.control;

import java.util.List;

import fr.java.math.geometry.plane.Viewport2D;
import fr.javafx.behavior.Visual;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ControlViewportVisual implements Visual<ControlViewportControl> {
	private final ControlViewportControl 	control;
	private final Pane 						window;

	public ControlViewportVisual(ControlViewportControl _control) {
		super();
		window = new Pane();
		window.setBorder(createBorder());
		window.setPrefSize(100, 100);

		control = _control;
		control . modelProperty().addListener((_obs, _old, _new) -> {
			window.getChildren().clear();
			if(_new != null) {
				window.getChildren().add(_new);

				window.setPrefSize(_new.getWidth(), _new.getHeight());
				window.setMinSize(_new.getWidth(), _new.getHeight());
				window.setMaxSize(_new.getWidth(), _new.getHeight());
			}
		});
		control . viewportProperty().addListener(e -> refresh());
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
		return null;
	}

	@Override
	public ControlViewportControl 	getSkinnable() {
		return control;
	}

	@Override
	public Node 					getNode() {
		return window;
	}

//	@Override
	public void 					refresh() {
		if(control.autofitContentToControl())
			control.fitContentToControl();
		else if(control.autofitToContent())
			control.fitToContent();

		update();
	}

	@Override
	public void 					dispose() {
		;
	}

	private void update() {
		Viewport2D<?, ?> vp = getSkinnable().getViewport();
		double s = vp.getViewScale();
		double x = vp.getViewAnchor().getFirst();
		double y = vp.getViewAnchor().getSecond();

		double dx = vp.getModelBounds().getX();
		double dy = vp.getModelBounds().getY();

		System.out.println(dx + "\t" + dy);

		double tx = - x * s - dx * s;
		double ty = - ( y + dy ) * s;
		double sx = s, sy = s;

		getSkinnable().getModel().setTranslateX	(tx);
		getSkinnable().getModel().setTranslateY	(ty);
		getSkinnable().getModel().setScaleX		(sx);
		getSkinnable().getModel().setScaleY		(sy);

		for(Node n : getSkinnable().getModel().getChildrenUnmodifiable()) {
			n.setTranslateX(- tx);
			n.setTranslateY(- ty);
		}

	}

	static Border createBorder() {
	    return new Border(
	            new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
	            new CornerRadii(3d), BorderStroke.THICK));
	}

}
