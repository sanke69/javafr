package fr.javafx.behavior.extended.bindings;

import java.util.List;

import javafx.scene.control.Control;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.extended.bindings.impl.SimpleKeyBinding;
import fr.javafx.behavior.extended.bindings.impl.SimpleKeyBinding.KeyBindingBuilder;
import fr.javafx.lang.enums.TernaryBoolean;

public interface KeyBinding<B extends Behavior<?>> extends Binding<KeyEvent, B> {

	public interface Oriented<B extends Behavior<?>> extends KeyBinding<B> {

		public TernaryBoolean 	isVertical();

	}

	public static <B extends Behavior<?>> KeyBindingBuilder<B> builder() {
		return new SimpleKeyBinding.KeyBindingBuilder<B>();
	}

	public int 					getSpecificity(Control _control, KeyEvent _keyEvent);

	public TernaryBoolean 		isShortcut();	// Imply Ctrl|Meta

	public TernaryBoolean 		getShift();
	public TernaryBoolean 		getCtrl();	// Used as shortcut on Windows System
	public TernaryBoolean 		getAlt();
	public TernaryBoolean 		getMeta();	// Used as shortcut on Apple System

	public List<KeyCombination>	asKeyCombination();

}
