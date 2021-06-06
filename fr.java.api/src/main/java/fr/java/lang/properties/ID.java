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
 * @file     ID.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.lang.properties;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface ID extends Comparable<Object>, Serializable  {
	public static final Charset usedCharset = StandardCharsets.UTF_8;

	public boolean 	isNumeric();
	public boolean 	isAlphaNumeric();

	public byte[] 	toBytes();
	public short  	toShort() 	throws IllegalArgumentException;
	public int    	toInt() 	throws IllegalArgumentException;
	public long   	toLong() 	throws IllegalArgumentException;
	@Override
	public String 	toString() 	throws IllegalArgumentException;
	public String 	toHexString();

	@Override
    public int 	  	hashCode();
	@Override
    public boolean 	equals(Object _id);

	@Override
	public int    	compareTo(final Object o);

}
