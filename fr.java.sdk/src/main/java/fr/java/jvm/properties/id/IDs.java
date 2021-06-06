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
 * @file     IDs.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.jvm.properties.id;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import fr.java.lang.properties.ID;
import fr.java.sdk.math.stats.randoms.MT19937;
import fr.java.utils.Bytes;
import fr.java.utils.strings.RandomString;

public class IDs {

	private static final Random       mt      = new MT19937(369);
	private static final RandomString rndUtf8 = new RandomString(64, mt);

	public static ID newBytes(byte[] _id) {
		return new IDImpl(_id);
	}
	public static ID newShort(short _id) {
		return new IDImpl(_id);
	}
	public static ID newInt(int _id) {
		return new IDImpl(_id);
	}
	public static ID newLong(long _id) {
		return new IDImpl(_id);
	}
	public static ID newUTF8(String _utf8) {
		return new IDImpl(_utf8);
	}
	public static ID newUTF8(String _utf8, boolean _hexValue) {
		if(_hexValue)
			return new IDImpl(Bytes.decodeHex(_utf8, StandardCharsets.UTF_8));
		return new IDImpl(_utf8);
	}

	public static ID random() {
		return random(64);
	}
	public static ID random(int _nbBytes) {
		byte[] rand = new byte[_nbBytes];
		mt.nextBytes(rand);
		return new IDImpl(rand);
	}
	public static ID randomInt() {
		return new IDImpl(mt.nextInt());
	}
	public static ID randomLong() {
		return new IDImpl(Math.abs(mt.nextLong()));
	}
	public static ID randomUTF8() {
		return new IDImpl(rndUtf8.nextString());
	}

	public static String toString(ID[] _ids) {
		return Arrays.asList(_ids).stream().map(id -> id.toString()).reduce("", (a,  b) -> a + ", " + b, (a, b) -> a + ", " + b);
	}
	public static String toString(Collection<ID> _ids) {
		return _ids.stream().map(id -> id.toString()).reduce("", (a,  b) -> a + ", " + b, (a, b) -> a + ", " + b);
	}

}
