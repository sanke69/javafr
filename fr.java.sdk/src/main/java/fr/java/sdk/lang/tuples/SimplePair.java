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
 * @file     Pair.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.lang.tuples;

import java.util.Objects;

import fr.java.lang.tuples.Pair;

public class SimplePair<A, B> implements Pair.Editable<A, B> {
	private A first;
	private B second;

	public SimplePair(A _first, B _second) {
		super();
		first = _first;
		second = _second;
	}

    @Override
	public void setFirst(A first) {
		this.first = first;
	}
    @Override
	public A getFirst() {
		return first;
	}

    @Override
	public void setSecond(B second) {
		this.second = second;
	}
    @Override
	public B getSecond() {
		return second;
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.first);
        hash = 53 * hash + Objects.hashCode(this.second);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimplePair<?, ?> other = (SimplePair<?, ?>) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}

}