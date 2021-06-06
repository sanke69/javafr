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
 * @file     FileFilter.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.nio.file.filters;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.java.nio.file.FileX;
import fr.java.sdk.nio.file.DirectoryObject;
import fr.java.sdk.nio.file.FileObject;

public class PatternPathFilter implements PathFilter {
	protected Pattern include, exclude;
	protected boolean recursive;

	public PatternPathFilter(Pattern _include, Pattern _exclude) {
		include   = _include;
		exclude   = _exclude;
		recursive = false;
	}
	public PatternPathFilter(Pattern _include, Pattern _exclude, boolean _recursive) {
		include   = _include;
		exclude   = _exclude;
		recursive = _recursive;
	}

	@Override
	public boolean test(Path _path) {
		return isIncluded(new FileObject(_path));
	}

	public Collection<? extends FileX> getFilteredFiles(Path _path) {
		if(!new FileObject(_path).isDirectory())
			return null;

		Collection<? extends FileX> allChildren      = new DirectoryObject(_path).ls(recursive);
		List<FileX>       			filteredChildren = new ArrayList<FileX>();

		for(FileX fo : allChildren)
			if(isIncluded(fo))
				filteredChildren.add(fo);

		sortDirectoryFirst(filteredChildren);

		return filteredChildren;
	}

	protected void sortDirectoryFirst(List<? extends FileX> _list) {
		Collections.sort(_list, (e1, e2) -> {
			if(e1.isDirectory() && e2.isFile())
				return -1;
			if(e1.isFile() && e2.isDirectory())
				return 1;
			return e1.toPath().compareTo(e2.toPath());
		});
	}

	protected boolean isIncluded(FileX _fo) {
		if(include != null && exclude != null) {
			Matcher incl = include.matcher(_fo.toPath().toString());
			Matcher excl = exclude.matcher(_fo.toPath().toString());
			if(incl.find() && !excl.find())
				return true;
		} else if(include != null) {
			Matcher incl = include.matcher(_fo.toPath().toString());
			if(incl.find())
				return true;
		} else if(exclude != null) {
			Matcher excl = exclude.matcher(_fo.toPath().toString());
			if(!excl.find())
				return true;
		} else
			return true;
		return false;
	}

}
