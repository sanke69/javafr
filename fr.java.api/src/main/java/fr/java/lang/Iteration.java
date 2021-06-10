/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.lang;

import java.util.Iterator;

// TODO:: Is this still requested ? Move to SDK!
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
