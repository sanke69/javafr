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
 * @file     Timestamp.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.lang.properties;

public interface Timestamp  {
	public enum FORMAT { RtMaps, };

	public static final long S_IN_MS = 1000;
	public static final long M_IN_MS = 60 * S_IN_MS;
	public static final long H_IN_MS = 60 * M_IN_MS;
	public static final long D_IN_MS = 24 * H_IN_MS; 

	public Long toEpochMilli();

	public Timestamp 	plus(long _ms);
	public Timestamp 	plusEquals(long _ms);

	public Timestamp 	minus(long _ms);
	public Timestamp 	minusEquals(long _ms);

	public Long 		delta(Timestamp _timestamp);

	public boolean 		before(Timestamp _timestamp);
	public boolean 		after(Timestamp _timestamp);
	public boolean 		between(Timestamp _from, Timestamp _to);

	public Timestamp 	clone();

    public boolean 		equals(Object obj) ;

}
