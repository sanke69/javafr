package fr.java.patterns.listenable;

import java.util.concurrent.Executor;

public interface Vetoable<T>
{

    public void     addVetoer(Listener<T>  listener);

    public void     addListener(Listener<T>  listener, Executor executor);

    public void     removeListener(Listener<T>  listener);
}