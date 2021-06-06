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
 * @file     NameFileFilter.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.nio.file.filters.samples;

import java.io.File;
import java.io.FileFilter;

public class NameFileFilter implements FileFilter {

	public static NameFileFilter isEquals(String _name) {
		return new NameFileFilter(_name, Mode.isEqual);
	}
	public static NameFileFilter contains(String _sequence) {
		return new NameFileFilter(_sequence, Mode.contains);
	}
	public static NameFileFilter withPrefix(String _prefix) {
		return new NameFileFilter(_prefix, Mode.withPrefix);
	}
	public static NameFileFilter withSuffix(String _suffix) {
		return new NameFileFilter(_suffix, Mode.withSuffix);
	}

	public static enum Mode {
		withPrefix, withSuffix, contains, isEqual;
	}

    private String pattern;
    private Mode   mode;

    public NameFileFilter(String _pattern) {
    	super();
    	pattern = _pattern;
    	mode    = Mode.isEqual;
    }
    public NameFileFilter(String _pattern, Mode _mode) {
    	super();
    	pattern = _pattern;
    	mode    = _mode;
    }

    @Override
    public boolean accept(File file) {
    	switch(mode) {
		case contains:		return file.getName().contains(pattern);
		case isEqual:		return file.getName().equalsIgnoreCase(pattern);
		case withPrefix:	return file.getName().indexOf(pattern) == 0;
		case withSuffix:	return file.getName().indexOf(pattern) == file.getName().length() - pattern.length();
		default:			return false;
    	}
        
    }

}
