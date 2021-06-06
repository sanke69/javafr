package fr.javafx.behavior.extended.bindings.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.event.Event;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.extended.bindings.Bindings;

public class CombinedInputBindings<B extends Behavior<?>> implements Bindings<B> {

	private final List<Bindings<B>> bindings;

	public CombinedInputBindings(Bindings<B> a, Bindings<B> b, Bindings<B>... more) {
		bindings = new ArrayList<>();
		bindings.add(a);
		bindings.add(b);
		Collections.addAll(bindings, more);
	}

	@Override
	public void fire(Event event, B behavior) {
		for (Bindings<B> binding : bindings)
			binding.fire(event, behavior);
	}

}
