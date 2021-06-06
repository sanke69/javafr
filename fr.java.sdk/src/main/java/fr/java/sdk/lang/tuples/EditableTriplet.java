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
 * @file     Triplet.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.lang.tuples;

import fr.java.lang.tuples.Triplet;

public class EditableTriplet<A, B, C> implements Triplet.Editable<A, B, C> {
	private A first;
	private B second;
	private C third;

	public EditableTriplet(A _first, B _second, C _third) {
		super();
		first  = _first;
		second = _second;
		third  = _third;
	}

	@Override
	public void setFirst(A _first) {
		first = _first;
	}
	@Override
	public A getFirst() {
		return first;
	}

	@Override
	public void setSecond(B _second) {
		second = _second;
	}
	@Override
	public B getSecond() {
		return second;
	}

	@Override
	public void setThird(C _third) {
		third = _third;
	}
	@Override
	public C getThird() {
		return third;
	}

	@Override
	public int hashCode() {
		int hashFirst  = first  != null ? first.hashCode() : 0;
		int hashSecond = second != null ? second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}
	@Override
	public boolean equals(Object other) {
		if(other instanceof EditableTriplet) {
			EditableTriplet<?,?,?> otherPair = (EditableTriplet<?,?,?>) other;
			return ((this.first == otherPair.first || (this.first != null && otherPair.first != null && this.first.equals(otherPair.first)))
					&& (this.second == otherPair.second || (this.second != null && otherPair.second != null && this.second.equals(otherPair.second)))
					&& (this.third == otherPair.third || (this.third != null && otherPair.third != null && this.third.equals(otherPair.third))));
		}

		return false;
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ", " + third + ")";
	}

}
