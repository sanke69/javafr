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
 * @file     Versions.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.jvm.properties.version;

import java.util.regex.Pattern;

import fr.java.lang.properties.Version;
import fr.java.lang.properties.Version.BuildType;

public class Versions {

	public static enum Matching {
		EQUAL, EQUIVALENT, COMPATIBLE, GREATER_OR_EQUAL;
	}

	public static Version of(final Integer _major, final Integer _minor, final Integer _patch, final Integer _build, final String _codename) {
		Version version =  new VersionImpl(_major, _minor, _patch, _build, _codename, null);
		return version;
	}
	public static Version of(final Integer _major, final Integer _minor, final Integer _patch, final Integer _build, final String _codename, final BuildType _buildType) {
		Version version =  new VersionImpl(_major, _minor, _patch, _build, _codename, _buildType);
		return version;
	}
	public static Version parse(final String str) {
		Version result = Versions.valueOf(str);
		return result;
	}
	public static Version valueOf(final String str) {
		VersionImpl version =  new VersionImpl();
		String[] tokens = str.split(Pattern.quote("."));
/*
		switch(tokens.length) {
		case 4  : 	version.codename = Optional.of(tokens[3]);
		case 3  : 	if(tokens[2].contains("-")) {
						String[] tokens2 = tokens[2].split(Pattern.quote("-"));
						version.codename  = Optional.of(tokens2[1]);
						version.build    = Optional.of(Integer.valueOf(tokens2[0]));
					} else
						version.build   = Optional.of(Integer.valueOf(tokens[2]));
		case 2  :	version.minor   = Integer.valueOf(tokens[1]);
		case 1  : 	version.major   = Integer.valueOf(tokens[0]);
				  	break;
		default : 	throw new IllegalArgumentException("Bad version: " + str);			
		}
*/
		return version;
	}

}
