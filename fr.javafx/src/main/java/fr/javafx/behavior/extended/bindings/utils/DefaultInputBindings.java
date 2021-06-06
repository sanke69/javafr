package fr.javafx.behavior.extended.bindings.utils;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiPredicate;

import javafx.event.Event;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.extended.bindings.Binding;
import fr.javafx.behavior.extended.bindings.Bindings;

public final class DefaultInputBindings<B extends Behavior<?>> implements Bindings<B> {

	private final BiPredicate<? super Event, B> filter;
	private final Set<? extends Binding<?, B>>  bindings;

	public DefaultInputBindings(Collection<? extends Binding<?, B>> bindings) {
		this((e, b) -> true, bindings);
	}
	public DefaultInputBindings(BiPredicate<? super Event, B> _filter, Collection<? extends Binding<?, B>> _bindings) {
		super();
		filter   = _filter;
		bindings = Set.copyOf(_bindings);
	}

	@Override
	public void fire(Event event, B behavior) {
		if (filter.test(event, behavior))
			bindings.stream()
					.filter(b -> b.getEventType().equals(event.getEventType()))
					.forEach(b -> ((Binding<Event, B>) b).fireIfMatches(event, behavior));
	}

}
