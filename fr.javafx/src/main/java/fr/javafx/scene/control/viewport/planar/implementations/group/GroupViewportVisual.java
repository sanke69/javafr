package fr.javafx.scene.control.viewport.planar.implementations.group;

import java.util.List;

import fr.java.math.geometry.plane.Viewport2D;
import fr.javafx.behavior.Visual;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class GroupViewportVisual implements Visual<GroupViewportControl> {
	private final GroupViewportControl 	control;
	private final AnchorPane 			pane;

	public GroupViewportVisual(GroupViewportControl _control) {
		super();
		pane = new AnchorPane();

		control = _control;
		control . modelProperty().addListener((_obs, _old, _new) -> {
			pane.getChildren().clear();

			if(_new != null) {
				pane.getChildren().add(_new);
			}

		});
		control . viewportProperty().addListener(e -> {
			refresh();
		});
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
		return null;
	}

	@Override
	public GroupViewportControl 	getSkinnable() {
		return control;
	}

	@Override
	public Node 					getNode() {
		return pane;
	}

	public void 					refresh() {
		if(control.autofitContentToControl())
			control.fitContentToControl();
		else if(control.autofitToContent())
			control.fitToContent();

		// TODO:: 
		System.out.println("GroupViewportVisual:: on refresh...");
		updateAll();
 	}

	@Override
	public void 					dispose() {
		;
	}

	private void updateAll() {
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
}
