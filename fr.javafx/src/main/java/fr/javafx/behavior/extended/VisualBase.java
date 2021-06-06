package fr.javafx.behavior.extended;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import fr.javafx.behavior.Visual;
import fr.javafx.behavior.extended.listeners.LambdaMultiplePropertyChangeListenerHandler;
import fr.javafx.behavior.extended.listeners.MultiplePropertyChangeListenerHandler;

public class VisualBase<C extends Control> implements Visual<C>, Skin<C> {
	private final C 									control;
	private final VisualParent							delegate;

	private MultiplePropertyChangeListenerHandler 		changeListenerHandler;
	private LambdaMultiplePropertyChangeListenerHandler lambdaChangeListenerHandler;

	protected VisualBase(C _control) {
		super();
		control  = _control;
		delegate = new VisualParent();
		
	}

	@Override
	public final C 									getSkinnable() {
		return control;
	}
	@Override
	public Node 									getNode() {
		return delegate;
	}
	public ObservableList<Node> 					getChildren() {
		return delegate.getChildren();
	}

	public void 									dispose() {
		if (changeListenerHandler != null)
			changeListenerHandler.dispose();
	}

	protected final void 							registerChangeListener(ObservableValue<?> property, String reference) {
		if (changeListenerHandler == null)
			changeListenerHandler = new MultiplePropertyChangeListenerHandler(str -> {
				handleControlPropertyChanged(str);
				return null;
			});

		changeListenerHandler.registerChangeListener(property, reference);
	}
	protected final void 							unregisterChangeListener(ObservableValue<?> property) {
		if (changeListenerHandler == null)
			return;

		changeListenerHandler.unregisterChangeListener(property);
	}


	public void 					layoutChildren(final double _x, final double _y, final double _width, final double _height) { delegate.layout(); }

	public void 					resizeRelocate(double _x, double _y, double _width, double _height) 						{ delegate.resizeRelocate(_x, _y, _width, _height); }

	protected double 				computeMinWidth(double height) 																{ return delegate.computeMinWidth(height); }
	protected double 				computeMinHeight(double width) 																{ return delegate.computeMinWidth(width); }

	protected double 				computePrefWidth(double height) 															{ return delegate.computeMinWidth(height); }
	protected double 				computePrefHeight(double width) 															{ return delegate.computeMinWidth(width); }

	protected double 				computeMaxWidth(double height) 																{ return delegate.computeMinWidth(height); }
	protected double 				computeMaxHeight(double width) 																{ return delegate.computeMaxHeight(width); }

	
	
	
	
	
	
	
	
	
	
	
	
	

	protected double 			leftMarging() {
		return getSkinnable().getInsets().getLeft() + getSkinnable().getPadding().getLeft();
	}
	protected double 			rightMarging() {
		return getSkinnable().getInsets().getRight() + getSkinnable().getPadding().getRight();
	}
	protected double 			topMarging() {
		return getSkinnable().getInsets().getTop() + getSkinnable().getPadding().getTop();
	}
	protected double 			bottomMarging() {
		return getSkinnable().getInsets().getBottom() + getSkinnable().getPadding().getBottom();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
	protected final void 							registerChangeListener(ObservableValue<?> property, Consumer<ObservableValue<?>> consumer) {
		if (lambdaChangeListenerHandler == null) {
			lambdaChangeListenerHandler = new LambdaMultiplePropertyChangeListenerHandler();
		}
		lambdaChangeListenerHandler.registerChangeListener(property, consumer);
	}
	protected final Consumer<ObservableValue<?>> 	unregisterChangeListeners(ObservableValue<?> property) {
		if (lambdaChangeListenerHandler == null) {
			return null;
		}
		return lambdaChangeListenerHandler.unregisterChangeListeners(property);
	}
*/
	public void 									handleControlPropertyChanged(String propertyReference) {
		;
	}

	class VisualParent extends Parent {

		public ObservableList<Node> getChildren() {
			return super.getChildren();
		}

		public void 				resizeRelocate(double _x, double _y, double _width, double _height) {
			final double x = getSkinnable().snappedLeftInset();
			final double y = getSkinnable().snappedTopInset();
			final double w = getSkinnable().snapSizeX(_width)  - _x - getSkinnable().snappedRightInset();
			final double h = getSkinnable().snapSizeY(_height) - _y - getSkinnable().snappedBottomInset();

			VisualBase.this.layoutChildren(x, y, w, h);
		}

		public double 				computeMinWidth(double height) 	{ return super.computeMinWidth(height); }
		public double 				computeMinHeight(double width) 	{ return super.computeMinHeight(width); }

		protected double 			computePrefWidth(double height) { return super.computePrefWidth(height); }
		protected double 			computePrefHeight(double width) { return super.computePrefHeight(width); }

		protected double 			computeMaxWidth(double height) 	{ return Double.MAX_VALUE; }
		protected double 			computeMaxHeight(double width) 	{ return Double.MAX_VALUE; }

	}

}
