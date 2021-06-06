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
 * @file     Version.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.lang.properties;

import java.io.Serializable;

public interface Version extends Serializable, Comparable<Version> {

	public static final char SEPARATOR = '.';

	public static enum BuildType {
		UNDEFINED		(0),
		TEST			(10),
		ALPHA			(20),
		BETA			(30),
		SNAPSHOT		(100),
		NIGHTLY_BUILD	(200),
		RELEASED		(300);

		int value;

		private BuildType(int _value) {
			value = _value;
		}
		
		public int value() {
			return value;
		}

	}

	public int getMajor();
	public int getMinor();
	public int getPatch();
	public int getBuild();

	public String getName();
	public BuildType getStatus();
	public String getDate();

	public boolean isCompatibleWith(final Version other);
	public boolean isGreaterThan(final Version other);
	public boolean isGreaterOrEqualTo(final Version other);
	public boolean isEquivalentTo(final Version other);

	@Override
	public int hashCode();

	@Override
	public boolean equals(final Object obj);

	@Override
	public int compareTo(final Version obj);

	@Override
	public String toString();

}