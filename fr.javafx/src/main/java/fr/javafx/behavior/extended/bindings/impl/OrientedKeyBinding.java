package fr.javafx.behavior.extended.bindings.impl;

import javafx.event.EventType;
/*    */ import javafx.scene.control.Control;
/*    */ import javafx.scene.input.KeyCode;
/*    */ import javafx.scene.input.KeyEvent;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.extended.bindings.KeyBinding;
import fr.javafx.lang.enums.TernaryBoolean;

public abstract class OrientedKeyBinding<B extends Behavior<?>> extends SimpleKeyBinding<B> implements KeyBinding.Oriented<B> {
	private TernaryBoolean vertical = TernaryBoolean.FALSE;

	public OrientedKeyBinding(KeyCode _keyCode, String _actionName) {
		super(_keyCode, _actionName);
	}
	public OrientedKeyBinding(KeyCode _keyCode, EventType<KeyEvent> eventType, String _actionName) {
		super(_keyCode, eventType, _actionName);
	}

	public OrientedKeyBinding<B> vertical() {
		vertical = TernaryBoolean.TRUE;
		return this;
	}

	@Override
	public TernaryBoolean 		isVertical() {
		return vertical;
	}

	protected abstract boolean 	getVertical(Control _control);

	public int 					getSpecificity(Control paramControl, KeyEvent paramKeyEvent) {
		boolean bool = getVertical(paramControl);
		if (!this.vertical.equals(bool))
			return 0;

		int i = super.getSpecificity(paramControl, paramKeyEvent);

		if (i == 0)
			return 0;

		return this.vertical != TernaryBoolean.ANY ? i + 1 : i;
	}

	public String 				toString() {
		return "OrientedKeyBinding [code=" + getCode() 
					+ ", shift=" + getShift() 
					+ ", ctrl=" + getCtrl() 
					+ ", alt=" + getAlt() 
					+ ", meta=" + getMeta() 
					+ ", type=" + getType() 
					+ ", vertical=" + vertical
					+ ", action=" + getAction() + "]";
	}

}
