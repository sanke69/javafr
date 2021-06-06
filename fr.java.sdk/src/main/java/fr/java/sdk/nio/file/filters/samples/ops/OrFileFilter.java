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
 * @file     OrFileFilter.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.nio.file.filters.samples.ops;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrFileFilter implements FileFilter {

	private List<FileFilter> fileFilters;

	public OrFileFilter() {
		this(new ArrayList<FileFilter>());
	}

	public OrFileFilter(FileFilter... fileFilters) {
		this(Arrays.asList(fileFilters));
	}

	public OrFileFilter(List<FileFilter> fileFilters) {
		this.fileFilters = new ArrayList<>(fileFilters);
	}

	public OrFileFilter addFileFilter(FileFilter fileFilter) {
		fileFilters.add(fileFilter);

		return this;
	}

	public List<FileFilter> getFileFilters() {
		return Collections.unmodifiableList(fileFilters);
	}

	public boolean removeFileFilter(FileFilter fileFilter) {
		return fileFilters.remove(fileFilter);
	}

	public void setFileFilters(List<FileFilter> fileFilters) {
		this.fileFilters = new ArrayList<>(fileFilters);
	}

	@Override
	public boolean accept(File file) {
		if(this.fileFilters.size() == 0)
			return true;

		for(FileFilter fileFilter : this.fileFilters)
			if(fileFilter.accept(file))
				return true;

		return false;
	}

}
