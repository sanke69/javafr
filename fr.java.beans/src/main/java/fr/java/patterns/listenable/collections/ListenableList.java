package fr.java.patterns.listenable.collections;

import java.util.List;
import java.util.concurrent.Executor;

import fr.java.patterns.listenable.Listenable;
import fr.java.patterns.listenable.listeners.ListListener;

@Deprecated
public interface ListenableList<T> extends Listenable<List<T>>, List<T> { //, Iterable<T>
/*
	public void addListener(Listener<List<T>> listener);
	public void addListener(Listener<List<T>> listener, Executor executor);

	public void removeListener(Listener<List<T>> listener);
*/
	public void addListener(ListListener<T> listener);
	public void addListener(ListListener<T> listener, Executor executor);

	public void removeListener(ListListener<T> listener);

}
