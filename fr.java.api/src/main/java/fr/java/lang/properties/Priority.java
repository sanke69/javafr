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
package fr.java.lang.properties;

public interface Priority /*extends Comparable<Object>*/ {
	public static final Priority Highest = Basics.Highest;
	public static final Priority Higher  = Basics.Higher;
	public static final Priority High    = Basics.High;
	public static final Priority Normal  = Basics.Normal;
	public static final Priority Low     = Basics.Low;
	public static final Priority Lower   = Basics.Lower;
	public static final Priority Lowest  = Basics.Lowest;

	public enum Basics implements Priority {
		Highest	( 999),
		Higher	( 100),
		High	(  10),
		Normal	(   0),
		Low		( -10),
		Lower	(-100),
		Lowest	(-999);
		
		int level;
	
		private Basics(int _level) {
			level = _level;
		}
	
		@Override
		public int getLevel() {
			return level;
		}
	
		public boolean isHigher(Priority _other) {
			return level - _other.getLevel() > 0;
		}
		public boolean isLower(Priority _other) {
			return level - _other.getLevel() < 0;
		}
	
		public int compareTo(Priority _other) {
			if(_other instanceof Priority)
				return level - ((Priority) _other).getLevel();
			return (int) Double.NaN;
		}
	
	}

	public int getLevel();

	public boolean isLower(Priority _other);
	public boolean isHigher(Priority _other);

	public int compareTo(Priority _other);

}
