package fr.javafx.behavior.extended.bindings;

import javafx.scene.input.MouseEvent;

import fr.javafx.behavior.Behavior;
import fr.javafx.lang.enums.TernaryBoolean;

public interface MouseBinding<B extends Behavior<?>> extends Binding<MouseEvent, B> {

	public interface Oriented<B extends Behavior<?>> extends MouseBinding<B> {

		public TernaryBoolean 	isVertical();

	}

}
