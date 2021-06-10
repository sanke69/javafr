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
package fr.java.file.archive;

import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Set;

import fr.java.lang.exceptions.AlreadyExistingEntry;

public interface ArchiverMulti extends Archiver {

	public Archiver addFile			(String _entryName, 	  Path      	_file) 								throws FileNotFoundException, AlreadyExistingEntry;
	public Archiver addFiles		(String _rootEntriesName, Set<Path> 	_files) 							throws FileNotFoundException, AlreadyExistingEntry;

	public Archiver addDirectory	(String _entryName, 	  Path   		_directory) 						throws FileNotFoundException, AlreadyExistingEntry;
	public Archiver addDirectories	(String _rootEntriesName, Set<Path> 	_directories) 						throws FileNotFoundException, AlreadyExistingEntry;
	
	public Archiver addDirectory	(String _entryName, 	  Path   		_directory,   FileFilter _filter) 	throws FileNotFoundException, AlreadyExistingEntry;
	public Archiver addDirectories	(String _rootEntriesName, Set<Path> 	_directories, FileFilter _filter) 	throws FileNotFoundException, AlreadyExistingEntry;

	public Archiver addInputStream	(String _entryName, 	  InputStream _inputStream) 						throws IOException, AlreadyExistingEntry;

}
