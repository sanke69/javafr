package fr.javafx.scene.control.overlay;

import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

import fr.javafx.behavior.Visual;

@Deprecated
public class FxCrosshairVisualDefault extends Group implements Visual<FxCrosshair> {
	private final FxCrosshair control;

	final Line	hLine = new Line(0, 0, 1, 0);
	final Line	vLine = new Line(0, 0, 0, 1);

	EventHandler<? super MouseEvent> onEntering = (me) -> setVisible(true);
	EventHandler<? super MouseEvent> onLeaving  = (me) -> setVisible(false);
	EventHandler<? super MouseEvent> onMotion   = (me) -> {		
		if(me.getY() < ((Region) getSkinnable().getParent()).getPrefHeight())
			hLine.setEndY(me.getY());
		if(me.getX() < ((Region) getSkinnable().getParent()).getPrefWidth())
			vLine.setEndX(me.getX());
	};

	Consumer<Region> attachToParent = (parent) -> {
		parent.requestFocus();
		hLine.endXProperty().bind(parent.prefWidthProperty());

		vLine.endYProperty().bind(parent.prefHeightProperty());

		parent.addEventHandler(MouseEvent.MOUSE_ENTERED,    onEntering);
		parent.addEventHandler(MouseEvent.MOUSE_EXITED,     onLeaving);
		parent.addEventHandler(MouseEvent.MOUSE_MOVED,      onMotion);
	};
	Consumer<Region> detachFromParent = (parent) -> {
		hLine.endXProperty().unbind();
		vLine.endXProperty().unbind();

		parent.removeEventHandler(MouseEvent.MOUSE_ENTERED, onEntering);
		parent.removeEventHandler(MouseEvent.MOUSE_EXITED,  onLeaving);
		parent.removeEventHandler(MouseEvent.MOUSE_MOVED,   onMotion);
	};
	
	public FxCrosshairVisualDefault(FxCrosshair _control) {
		super();
		control = _control;

		hLine.startYProperty().bind(hLine.endYProperty());
		vLine.startXProperty().bind(vLine.endXProperty());

		getChildren().addAll(hLine, vLine);

		if(control.getParent() != null)
			attachToParent.accept((Region) control.getParent());
		
		control.parentProperty().addListener((_obs, _old, _new) -> {
			if(_old != null)
				if(_old instanceof Region)
					detachFromParent.accept((Region) _old);

			if(_new != null)
				if(_new instanceof Region)
					attachToParent.accept((Region) _new);
		});
	}

	@Override
	public FxCrosshair getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

}
