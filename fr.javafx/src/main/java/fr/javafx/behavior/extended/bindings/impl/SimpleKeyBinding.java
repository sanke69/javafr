package fr.javafx.behavior.extended.bindings.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.event.EventType;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.extended.bindings.KeyBinding;
import fr.javafx.lang.enums.TernaryBoolean;

public class SimpleKeyBinding<B extends Behavior<?>> extends AbstractBinding<KeyEvent, B> implements KeyBinding<B> {
	private static final Consumer<? extends Behavior<?>> magicConsumer = b -> {};

	final static class Record {
		TernaryBoolean  shift    = TernaryBoolean.FALSE;
		TernaryBoolean  ctrl     = TernaryBoolean.FALSE;
		TernaryBoolean  alt      = TernaryBoolean.FALSE;
		TernaryBoolean  meta     = TernaryBoolean.FALSE;
		TernaryBoolean  shortcut = TernaryBoolean.FALSE;

		KeyCode 		code;

		Record(KeyCode _keyCode) {
			super();
			code =_keyCode;
		}

		KeyCombination asKeyCombination() {
			return new KeyCodeCombination(	code,
											shift.asModifier(),
											ctrl.asModifier(),
											alt.asModifier(),
											meta.asModifier(),
											shortcut.asModifier());
		}

	}

	private final Record				unique;
	private final List<KeyCombination> 	keyCombinations; // the bound key combinations

	private String 						actionName;

	public SimpleKeyBinding(KeyCode _keyCode, String _actionName) {
		this(_keyCode, null, KeyEvent.ANY, _actionName, null);
	}
	public SimpleKeyBinding(KeyCode _keyCode, EventType<KeyEvent> _eventType, String _actionName) {
		this(_keyCode, null, _eventType, _actionName, null);
	}
	public SimpleKeyBinding(KeyCode _keyCode, EventType<KeyEvent> _eventType, Consumer<B> _action) {
		this(_keyCode, null, _eventType, null, _action);
	}
	public SimpleKeyBinding(Collection<KeyCombination> _keyCombinations, EventType<KeyEvent> _eventType, Consumer<B> _action) {
		this(null, _keyCombinations, _eventType, null, _action);
	}

	SimpleKeyBinding(KeyCode _keyCode, Collection<KeyCombination> _keyCombinations, EventType<KeyEvent> _eventType, String _actionName, Consumer<B> _action) {
		super(_eventType, _action != null ? _action : (Consumer<B>) magicConsumer);

		if(_eventType == KeyEvent.KEY_TYPED)
			throw new IllegalArgumentException("KEY_TYPED events do not trigger key combinations");

		if(_keyCode != null) {
			unique          = new Record(_keyCode);
			keyCombinations = Collections.EMPTY_LIST;

			if(_actionName != null)
				actionName = _actionName;
			else if(_action == null)
				throw new NullPointerException();

			return ;
		}

		if(_keyCombinations != null && !_keyCombinations.isEmpty()) {
			unique = null;
			for (KeyCombination keyCombination : _keyCombinations)
				Objects.requireNonNull(keyCombination, "Null key combination found");
			keyCombinations = List.copyOf(_keyCombinations);

			if(_actionName != null)
				actionName = _actionName;
			else if(_action == null)
				throw new NullPointerException();

			return ;
		}

		throw new NullPointerException();
	}

	public final EventType<KeyEvent> 	getType() {
		return getEventType();
	}

	public final String 				getActionName() {
		return actionName;
	}

	/**
	 * KEY
	**/
	public final KeyCode 				getCode() {
		return unique.code;
	}

	/**
	 * MODIFIERS
	**/
	public int 							getSpecificity(Control _control, KeyEvent _keyEvent) {
		int i = 0;
		if ((unique.code != null) && (unique.code != _keyEvent.getCode()))
			return 0;
		i = 1;
		if (!getShift().equals(_keyEvent.isShiftDown()))
			return 0;
		if (getShift() != TernaryBoolean.ANY)
			i++;
		if (!unique.ctrl.equals(_keyEvent.isControlDown()))
			return 0;
		if (unique.shift != TernaryBoolean.ANY)
			i++;
		if (!unique.alt.equals(_keyEvent.isAltDown()))
			return 0;
		if (unique.shift != TernaryBoolean.ANY)
			i++;
		if (!unique.meta.equals(_keyEvent.isMetaDown()))
			return 0;
		if (unique.shift != TernaryBoolean.ANY)
			i++;
		if ((getEventType() != null) && (getEventType() != _keyEvent.getEventType()))
			return 0;
		i++;

		return i;
	}

	public SimpleKeyBinding<B> 			shift() {
		return shift(TernaryBoolean.TRUE);
	}
	public SimpleKeyBinding<B> 			shift(TernaryBoolean _paramOptionalBoolean) {
		unique.shift = _paramOptionalBoolean;
		return this;
	}

	public final TernaryBoolean 		getShift() {
		return unique.shift;
	}

	public SimpleKeyBinding<B> 			ctrl() {
		return ctrl(TernaryBoolean.TRUE);
	}
	public SimpleKeyBinding<B> 			ctrl(TernaryBoolean _paramOptionalBoolean) {
		unique.ctrl = _paramOptionalBoolean;
		return this;
	}

	public final TernaryBoolean 		getCtrl() {
		return unique.ctrl;
	}

	public SimpleKeyBinding<B> 			alt() {
		return alt(TernaryBoolean.TRUE);
	}
	public SimpleKeyBinding<B> 			alt(TernaryBoolean _paramOptionalBoolean) {
		unique.alt = _paramOptionalBoolean;
		return this;
	}

	public final TernaryBoolean 		getAlt() {
		return unique.alt;
	}

	public SimpleKeyBinding<B> 			meta() {
		return meta(TernaryBoolean.TRUE);
	}
	public SimpleKeyBinding<B> 			meta(TernaryBoolean _paramOptionalBoolean) {
		unique.meta = _paramOptionalBoolean;
		return this;
	}

	public final TernaryBoolean 		getMeta() {
		return unique.meta;
	}

	public SimpleKeyBinding<B> 			shortcut() {
		return shortcut(TernaryBoolean.TRUE);
	}
	public SimpleKeyBinding<B> 			shortcut(TernaryBoolean _paramOptionalBoolean) {
		unique.shortcut = _paramOptionalBoolean;
		return this;
	}

	public final TernaryBoolean 		isShortcut() {
		return unique.shortcut;
	}

	public List<KeyCombination>			asKeyCombination() {
		if(unique != null)
			return Arrays.asList(unique.asKeyCombination());
		else
			return keyCombinations;
	}

	public boolean 						match(KeyEvent event) {
		EventType<KeyEvent> eventType = getEventType();
		return (eventType == KeyEvent.ANY
					|| eventType.equals(event.getEventType()))
				&& (asKeyCombination().isEmpty()
					|| asKeyCombination().stream().anyMatch(kc -> kc.match(event)));
	}

	public String 						toString() {
		return "KeyBinding [code=" + unique.code + ", shift=" + unique.shift + ", ctrl=" + unique.ctrl + ", alt=" + unique.alt
				+ ", meta=" + unique.meta + ", type=" + this.getEventType() + ", action=" + this.actionName + "]";
	}

	@Override
	public boolean 						equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		if (!super.equals(obj))
			return false;

		SimpleKeyBinding<?> that = (SimpleKeyBinding<?>) obj;

		List<KeyCombination> thisAsKC = asKeyCombination();
		List<KeyCombination> thatAsKC = that.asKeyCombination();

		return thisAsKC.equals(thatAsKC);
	}

	@Override
	public int 							hashCode() {
		return Objects.hash(super.hashCode(), asKeyCombination());
	}

	public static final class KeyBindingBuilder<B extends Behavior<?>> extends Builder<KeyEvent, B, SimpleKeyBinding<B>> {

		private final Collection<KeyCombination> keyCombinations = new ArrayList<>();

		public KeyBindingBuilder() {
			super();
			onEvent(KeyEvent.KEY_PRESSED);
		}

		public KeyBindingBuilder<B> withKey(KeyCode keyCode) {
			keyCombinations.add(new KeyCodeCombination(keyCode));
			return this;
		}
		public KeyBindingBuilder<B> withKey(KeyCode key, KeyCombination.Modifier... modifiers) {
			keyCombinations.add(new KeyCodeCombination(key, modifiers));
			return this;
		}

		@Override
		public KeyBindingBuilder<B> onEvent(EventType<KeyEvent> eventType) {
			super.onEvent(eventType);
			return this;
		}

		@Override
		public KeyBindingBuilder<B> withAction(Consumer<B> action) {
			super.withAction(action);
			return this;
		}

		@Override
		public SimpleKeyBinding<B> build() {
			return new SimpleKeyBinding<>(keyCombinations, eventType, action);
		}

	}

}
