package fr.javafx.scene.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import fr.java.lang.TriConsumer;

import fr.javafx.lang.enums.TranslateBehavior;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class InnerPane extends Pane {

	private final ObjectProperty<TranslateBehavior>	translateBehaviorProperty		= new SimpleObjectProperty<>(TranslateBehavior.ALWAYS);
	private final BooleanProperty					translateToMinNodePosProperty	= new SimpleBooleanProperty(true);

	private boolean									manualReset;
	private double									minX, minY;

	public Predicate<Node> 							predicate;
	public TriConsumer<Node, Double, Double> 		consumer;
	public Function<Node, Double> 					getX, getY;
	
	public InnerPane() {
		super();

		translateToMinNodePosProperty . addListener((_obs, _old, _new) -> requestLayout());
		translateBehaviorProperty	  . addListener((_obs, _old, _new) -> requestLayout());
	}
	public void addExtraBehavior(Predicate<Node> _predicate,  
								 TriConsumer<Node, Double, Double> _updatePosition, 
								 Function<Node, Double> _getX, 
								 Function<Node, Double> _getY) {
		predicate = _predicate;
		consumer  = _updatePosition;
		getX      = _getX;
		getY      = _getY;
	}

	public final ObjectProperty<TranslateBehavior> 	translateBehaviorProperty() {
		return translateBehaviorProperty;
	}
	public BooleanProperty 							translateToMinNodePosProperty() {
		return translateToMinNodePosProperty;
	}

	public void 									resetTranslation() {
		if(manualReset)
			return;

		manualReset = true;
		try {
			layoutChildren();
		} finally {
			manualReset = false;
		}
	}

	@Override
	protected void 									layoutChildren() {
		super.layoutChildren();

		if(!translateToMinNodePosProperty.get()) {
			for(Node n : getChildrenUnmodifiable())
				if(n.isManaged()) {
					if(!n.layoutXProperty().isBound())
						n.setLayoutX(Math.max(0, n.getLayoutX()));
					if(!n.layoutYProperty().isBound())
						n.setLayoutY(Math.max(0, n.getLayoutY()));

					if(predicate != null && consumer != null)
						if(predicate.test(n))
							consumer.accept(n, n.getLayoutX(), null);
				}
			return;
		}

		List<Node> nodeList = new ArrayList<>();

		minX = Double.MAX_VALUE;
		minY = Double.MAX_VALUE;

		// search minX and minY of window nodes
		for(Node n : getChildrenUnmodifiable())
			if(n.isManaged()) {
				if(predicate != null) {
					if(predicate.test(n)) {
						nodeList.add(n);
						minX = Math.min(getX.apply(n), minX);
						minY = Math.min(getY.apply(n), minY);
					}
				} else {
					nodeList.add(n);
					minX = Math.min(n.getLayoutX(), minX);
					minY = Math.min(n.getLayoutY(), minY);
				}
			}

		boolean partOfSceneGraph = false;

		try {
			javafx.stage.Window w  = getScene().getWindow();
			partOfSceneGraph  = w != null;
		} catch(Exception ex) {}

		if(translateBehaviorProperty().get() == TranslateBehavior.ALWAYS || manualReset) {
			translateAllWindowsXY(minX, minY, nodeList);
		} else if(translateBehaviorProperty().get() == TranslateBehavior.IF_NECESSARY) {
			if(minX < 0 && getLayoutBounds().getWidth() > 0 && partOfSceneGraph)
				translateAllWindowsX(minX, nodeList);
			if(minY < 0 && getLayoutBounds().getHeight() > 0 && partOfSceneGraph)
				translateAllWindowsY(minY, nodeList);
		}

	}

	private void 									translateAllWindowsXY(double _xOffset, double _yOffset, List<Node> _nodeList) {
		for(Node n : _nodeList) {
			n.setLayoutX(n.getLayoutX() - _xOffset);
			n.setLayoutY(n.getLayoutY() - _yOffset);

			if(predicate != null && consumer != null)
				if(predicate.test(n))
					consumer.accept(n, n.getLayoutX(), n.getLayoutY());
		}
	}
	private void 									translateAllWindowsX(double _xOffset, List<Node> _nodeList) {
		for(Node n : _nodeList) {
			n.setLayoutX(n.getLayoutX() - _xOffset);

			if(predicate != null && consumer != null)
				if(predicate.test(n))
					consumer.accept(n, n.getLayoutX(), null);
		}
	}
	private void 									translateAllWindowsY(double _yOffset, List<Node> _nodeList) {
		for(Node n : _nodeList) {
			n.setLayoutX(n.getLayoutY() - _yOffset);

			if(predicate != null && consumer != null)
				if(predicate.test(n))
					consumer.accept(n, n.getLayoutX(), null);
		}
	}

}
