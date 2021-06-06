package fr.javafx.behavior.extended.bindings.impl;

import java.util.Objects;
import java.util.function.Consumer;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.KeyEvent;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.extended.bindings.Binding;

public abstract class AbstractBinding<E extends Event, B extends Behavior<?>> implements Binding<E, B> {

	private final EventType<E> eventType;
	private final Consumer<B>  action;

	protected AbstractBinding(EventType<E> _eventType, Consumer<B> _action) {
		super();
		Objects.requireNonNull(_eventType, "Event type cannot be null");
		Objects.requireNonNull(_action, "Action cannot be null");

		eventType = _eventType;
		action    = _action;
	}

	/**
	 * Checks if this binding can be fired as a result of an event.
	 *
	 * @param event the event to check
	 *
	 * @return true if this binding can be fired, false if not
	 */
	protected abstract boolean	match(E event);

	public final void 			fireIfMatches(E event, B behavior) {
		if (match(event))
			action.accept(behavior);
	}

	public final EventType<E> 	getEventType() {
		return eventType;
	}

	public final Consumer<B> 	getAction() {
		return action;
	}

	@Override
	public boolean 				equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		Binding<?, ?> that = (Binding<?, ?>) obj;

		return this.eventType.equals(that.getEventType()) && this.action.equals(that.getAction());
	}

	@Override
	public int 					hashCode() {
		return Objects.hash(eventType, action);
	}

	/**
	 * Abstract base class for binding builders.
	 *
	 * @param <E>  the type of events that built bindings should fire on
	 * @param <BB> the type of the behavior that built bindings should call
	 * @param <B>  the type of the built bindings
	 */
	protected abstract static class Builder<E extends Event, BB extends Behavior<?>, B extends AbstractBinding<E, BB>> {

		protected EventType<E> eventType;
		protected Consumer<BB> action;

		/**
		 * Sets the event type to fire on.
		 *
		 * @param eventType the event type to fire on
		 *
		 * @return this builder
		 */
		public Builder<E, BB, B> onEvent(EventType<E> eventType) {
			this.eventType = eventType;
			return this;
		}

		/**
		 * Sets the action to run when the key binding fires. This <b>must</b> be
		 * specified before calling {@link #build()}.
		 *
		 * @param action the action to run when the key binding fires
		 *
		 * @return this builder
		 */
		public Builder<E, BB, B> withAction(Consumer<BB> action) {
			this.action = action;
			return this;
		}

		/**
		 * Builds and returns the binding.
		 *
		 * @return a new binding based on the values given to this builder
		 */
		public abstract B build();

	}

}
