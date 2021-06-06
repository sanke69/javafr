package fr.java.patterns.listenable;

import java.util.concurrent.Executor;

public interface Listenable<T> {

	public <L extends Listener<T>> void addListener(L listener);
	public <L extends Listener<T>> void addListener(L listener, Executor executor);

//	public void addListener(Listener<T> listener);
//	public void addListener(Listener<T> listener, Executor executor);

	public void removeListener(Listener<T> listener);

}