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
package fr.java.patterns.composite;

import java.util.Set;

import fr.java.lang.tuples.Pair;

public interface Component {

	public interface Single extends Component {
	
		public default boolean 					hasParent()   { return getParent() != null; }
		public Composite 						getParent();
	
	}
	public interface Double extends Component {
	
		public default boolean 					hasParent()   { return getParent() != null && !getParent().isEmpty(); }
		public Pair<Composite, Composite> 		getParent();
	
	}
	public interface Multi extends Component {
	
		public default boolean 					hasParent()   { return getParent() != null && !getParent().isEmpty(); }
		public Set<Composite>					getParent();
	
	}

	public default boolean 	hasParent()   { return getParent() != null; }
	public Object 			getParent();

}
