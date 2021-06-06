package fr.javafx.behavior.extended.bindings;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiPredicate;

import javafx.event.Event;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.extended.bindings.utils.CombinedInputBindings;
import fr.javafx.behavior.extended.bindings.utils.DefaultInputBindings;

public interface Bindings<B extends Behavior<?>> {

	/**
	 * Creates a new set of input bindings with no filter.
	 *
	 * @param bindings the bindings
	 */
	static <B extends Behavior<?>> Bindings<B> of(Collection<? extends Binding<?, B>> bindings) {
		return new DefaultInputBindings<>(bindings);
	}

	/**
	 * Creates a new set of input bindings with no filter.
	 *
	 * @param bindings the bindings
	 */
	@SafeVarargs
	static <B extends Behavior<?>> Bindings<B> of(Binding<?, B>... bindings) {
		return new DefaultInputBindings<>(Set.of(bindings));
	}

	/**
	 * Creates a new set of input bindings.
	 *
	 * @param filter   an event filter to use. If an event is fired that does not
	 *                 pass this filter, then no bindings will fire even if they
	 *                 match that event
	 * @param bindings the bindings
	 */
	static <B extends Behavior<?>> Bindings<B> of(BiPredicate<? super Event, B> filter, Collection<? extends Binding<?, B>> bindings) {
		return new DefaultInputBindings<>(filter, bindings);
	}

	/**
	 * Creates a new set of input bindings.
	 *
	 * @param filter   an event filter to use. If an event is fired that does not
	 *                 pass this filter, then no bindings will fire even if they
	 *                 match that event
	 * @param bindings the bindings
	 */
	@SafeVarargs
	static <B extends Behavior<?>> Bindings<B> of(BiPredicate<? super Event, B> filter, Binding<?, B>... bindings) {
		return new DefaultInputBindings<>(filter, Set.of(bindings));
	}

	/**
	 * Combines multiple input bindings.
	 *
	 * @param first  the first set of bindings
	 * @param second the second set of bindings
	 * @param more   optional, extra bindings to combine
	 */
	static <B extends Behavior<?>> Bindings<B> combine(Bindings<B> first, Bindings<B> second, @SuppressWarnings("unchecked") Bindings<B>... more) {
		return new CombinedInputBindings<B>(first, second, more);
	}

	/**
	 * Fires the bindings in response to an event.
	 *
	 * @param event    the event that was fired
	 * @param behavior the behavior on which to fire the bindings
	 */
	void fire(Event event, B behavior);

}
