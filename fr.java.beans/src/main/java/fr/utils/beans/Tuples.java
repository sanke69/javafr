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
package fr.utils.beans;

import java.util.Objects;

import fr.java.lang.tuples.Pair;
import fr.java.lang.tuples.Triplet;

public class Tuples {

	public static final <A, B> Pair<A, B> 			of(final A _first, final B _second) {
		return new Pair<A, B>() {

			@Override public A 		getFirst()  { return _first; }
			@Override public B 		getSecond() { return _second; }

			@Override
			public int 				hashCode() {
				int hash = 7;
				hash = 53 * hash + Objects.hashCode(getFirst());
				hash = 51 * hash + Objects.hashCode(getSecond());
				// TODO:: if both have same weight, put same coef for hash
				return hash;
			}
			@Override
			public boolean  		equals(Object obj) {
				if(obj == null) {
					return false;
				}
				if(getClass() != obj.getClass()) {
					return false;
				}
				final Pair<?, ?> other = (Pair<?, ?>) obj;
				if(!Objects.equals(_first, other.getFirst())) {
					return false;
				}
				if(!Objects.equals(_second, other.getSecond())) {
					return false;
				}
				return true;
			}
			
			@Override
			public String 			toString() {
				return "(" + _first + ", " + _second + ")";
			}

		};
	}
	public static final <A, B> Pair.Editable<A, B> 			ofEditable(final A _first, final B _second) {
		return new Pair.Editable<A, B>() {
			A first  = _first;
			B second = _second;

			@Override public void 	setFirst(A _first) { first = _first; }
			@Override public A 		getFirst() { return first; }

			@Override public void 	setSecond(B _second) { second = _second; }
			@Override public B 		getSecond() { return second; }

			@Override
			public int 				hashCode() {
				int hash = 7;
				hash = 53 * hash + Objects.hashCode(this.first);
				hash = 51 * hash + Objects.hashCode(this.second);
				return hash;
			}
			@Override
			public boolean 			equals(Object obj) {
				if (obj == null) {
					return false;
				}
				if (getClass() != obj.getClass()) {
					return false;
				}
				final Pair<?, ?> other = (Pair<?, ?>) obj;
				if (!Objects.equals(this.first, other.getFirst())) {
					return false;
				}
				if (!Objects.equals(this.second, other.getSecond())) {
					return false;
				}
				return true;
			}

			@Override
			public String 			toString() {
				return "(" + first + ", " + second + ")";
			}

		};
	}

	public static final <A, B, C> Triplet<A, B, C> 	of(final A _first, final B _second, final C _third) {
		return new Triplet<A, B, C>() {

			@Override public A 		getFirst()  { return _first; }
			@Override public B 		getSecond() { return _second; }
			@Override public C 		getThird()  { return _third; }

			@Override
			public int 				hashCode() {
				int hash = 7;
				hash = 53 * hash + Objects.hashCode(getFirst());
				hash = 51 * hash + Objects.hashCode(getSecond());
				hash = 47 * hash + Objects.hashCode(getThird());
				return hash;
			}
			@Override
			public boolean  		equals(Object obj) {
				if (obj == null)
					return false;

				if (getClass() != obj.getClass())
					return false;

				final Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
				if (!Objects.equals(_first, other.getFirst()))
					return false;
				if (!Objects.equals(_second, other.getSecond()))
					return false;
				if (!Objects.equals(_third, other.getThird()))
					return false;

				return true;
			}
			
			@Override
			public String 			toString() {
				return "(" + _first + ", " + _second  + ", " + _third+ ")";
			}

		};
	}
	public static final <A, B, C> Triplet.Editable<A, B, C> 	ofEditable(final A _first, final B _second, final C _third) {
		return new Triplet.Editable<A, B, C>() {
			A first  = _first;
			B second = _second;
			C third  = _third;

			@Override public void 	setFirst(A _first) { first = _first; }
			@Override public A 		getFirst() { return first; }

			@Override public void 	setSecond(B _second) { second = _second; }
			@Override public B 		getSecond() { return second; }

			@Override public void 	setThird(C _third) { third = _third; }
			@Override public C 		getThird() { return third; }

			@Override
			public int 				hashCode() {
				int hash = 7;
				hash = 53 * hash + Objects.hashCode(first);
				hash = 51 * hash + Objects.hashCode(second);
				hash = 47 * hash + Objects.hashCode(third);
				return hash;
			}
			@Override
			public boolean 			equals(Object obj) {
				if (obj == null)
					return false;

				if (getClass() != obj.getClass())
					return false;

				final Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
				if (!Objects.equals(_first, other.getFirst()))
					return false;
				if (!Objects.equals(_second, other.getSecond()))
					return false;
				if (!Objects.equals(_third, other.getThird()))
					return false;

				return true;
			}

			@Override
			public String 			toString() {
				return "(" + first + ", " + second + ", " + third + ")";
			}

		};
	}

}
