package fr.java.lang;

import java.util.Iterator;

public abstract class Iteration<Type> implements Iterator<Type>, Iterable<Type> {

    private static class SimpleIteration<Type> extends Iteration<Type> {

        SimpleIteration(Iterator<Type> iter) {
            _iter = iter;
        }

        @Override
        public boolean hasNext() {
            return _iter.hasNext();
        }

        @Override
        public Type next() {
            return _iter.next();
        }

        private Iterator<Type> _iter;
    }

    public static <Type> Iteration<Type> iteration(Iterable<Type> iterable) {
        return new SimpleIteration<>(iterable.iterator());
    }

    static <Type> Iteration<Type> iteration(Iterator<Type> it) {
        return new SimpleIteration<>(it);
    }

    @Override
    public Iterator<Type> iterator() {
        return this;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

}
