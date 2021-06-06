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
 * @file     FileX.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.nio.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.patterns.visitable.Treeable;

public interface FileX extends Treeable<Path, FileX> {

	public interface Regular extends FileX {

		public long 						getSize();

		public InputStream					getInputStream() throws FileNotFoundException;
		public InputStreamX					getInputStreamX() throws FileNotFoundException;

		public OutputStream 				getOutputStream() throws FileNotFoundException;
		public OutputStreamX 				getOutputStreamX() throws FileNotFoundException;

	}
	public interface Directory extends FileX {

		public Set<? extends FileX> 		getChildren(final boolean _recursive);
//		@Override public FileX 				getChild(final Path _path, final boolean _createIfMissing);
		public default FileX 				getChild(final String _path) 															{ return getChild(Paths.get(_path), false); }
//		public default FileX 				getChild(final String _path, final boolean _createIfMissing) 							{ return getChild(Paths.get(_path), _createIfMissing); }

		@Override public default FileX 		addChild(final FileX _filex)															{ return asDirectory() != null ? asDirectory().addChild(_filex) : null; }

		public default 			 Regular 	addFile(String _file) throws IOException 												{ return touch(Paths.get(_file)); }
		public default 			 Directory 	addDirectory(String _subDir) throws IOException											{ return mkdir(_subDir, false); }
		@Override public default FileX 		removeChild(final FileX _filex)															{ return asDirectory() != null ? asDirectory().removeChild(_filex) : null; }

		public Set<? extends FileX> 		ls(final FileFilter _ff, final boolean _recursive, final int _maxLevel);

		public Set<? extends FileX> 		ls();
		public Set<? extends FileX> 		ls(final boolean _recursive);
		public Set<? extends FileX> 		ls(final FileFilter _ff);
		public Set<? extends FileX> 		ls(final FileFilter _ff, final boolean _recursive);
		public Set<? extends FileX> 		lsDirs();
		public Set<? extends FileX> 		lsDirs(final boolean _recursive);
		public Set<? extends FileX> 		lsDirs(final FileFilter _ff);
		public Set<? extends FileX> 		lsDirs(final FileFilter _ff, final boolean _recursive);
		public Set<? extends FileX> 		lsDirs(final FileFilter _ff, final boolean _recursive, final int _maxLevel);
		public Set<? extends FileX> 		lsFiles();
		public Set<? extends FileX> 		lsFiles(final boolean _recursive);
		public Set<? extends FileX> 		lsFiles(final FileFilter _ff);
		public Set<? extends FileX> 		lsFiles(final FileFilter _ff, final boolean _recursive);
		public Set<? extends FileX> 		lsFiles(final FileFilter _ff, final boolean _recursive, final int _maxLevel);

		public default FileX 				find(final String _childName) 															{ return find(_childName, false); }
		public FileX 						find(final String _childName, final boolean _recursive);
		public default FileX 				find(final FileFilter _ff) 																{ return find(_ff, false); }
		public FileX 						find(final FileFilter _ff, final boolean _recursive);
		public default Set<? extends FileX> findAll(final FileFilter _ff)															{ return findAll(_ff, true); }
		public Set<? extends FileX> 		findAll(final FileFilter _ff, final boolean _recursive);

		public Regular						touch(final Path _child) throws IOException;
		public default Regular 				touch(final String _child) throws IOException 											{ return touch(Paths.get(_child)); }

		public Directory					mkdir(final Path _child, final boolean _createParentIfNonExist) throws IOException;
		public default Directory 			mkdir(final Path _child) throws IOException 											{ return mkdir(_child, false); }
		public default Directory 			mkdir(final String _child) throws IOException											{ return mkdir(Paths.get(_child), false); }
		public default Directory 			mkdir(final String _child, final boolean _createParentIfNonExist) throws IOException	{ return mkdir(Paths.get(_child), _createParentIfNonExist); }

		public boolean 						rm(final Path _child);
		public default boolean 				rm(final String _child)																	{ return rm(Paths.get(_child)); }

	}
	public interface Link extends FileX {
		
	}

	@Override public default boolean 				isLeaf() 																				{ return isDirectory() ? false : true; }

	@Override public default String 				getName() 																				{ return getFileName(); }
	@Override public default Path 					getData() 																				{ return toPath(); }

	@Override public FileX.Directory				getParent();
	@Override public default Set<? extends FileX> 	getChildren() 																			{ return isDirectory() ? asDirectory().getChildren(false) : null; }
	public default FileX 							getChild(final String _path, boolean _createIfMissing) 									{ return isDirectory() ? asDirectory().getChild(_path, _createIfMissing) : null; }
	@Override public default FileX 					getChild(final Path _path, boolean _createIfMissing) 									{ return isDirectory() ? asDirectory().getChild(_path, _createIfMissing) : null; }

	@Override public default FileX 					addChild(final FileX _filex)															{ return isDirectory() ? asDirectory().addChild(_filex) : null; }
	@Override public default FileX 					removeChild(final FileX _filex)															{ return isDirectory() ? asDirectory().removeChild(_filex) : null; }

	@Override public default boolean 				add(final FileX e) 																		{ throw new IllegalAccessError(); }
	@Override public default boolean 				remove(final Object o) 																	{ throw new IllegalAccessError(); }

	public boolean 									isExist();
	public boolean 									isExist(final String _child);
	public boolean 									isExist(final Path _child);
	public boolean 									isFile();
	public boolean 									isFile(final String _child);
	public boolean 									isFile(final Path _child);
	public boolean 									isDirectory();
	public boolean 									isDirectory(final String _child);
	public boolean 									isDirectory(final Path _child);
	public boolean 									isRegularFile();
	public boolean 									isRegularFile(final String _child);
	public boolean 									isRegularFile(final Path _child);
	public boolean 									isLink();
	public boolean 									isLink(final String _child);
	public boolean 									isLink(final Path _child);

	public Directory								asDirectory();
	public Regular 									asRegularFile();
	public Link 									asLink();

	public Path 									toPath();
	public File 									toFile();

	public String 									getFileName();
	public String 									getExtension();
	public String 									getFullPath();
	public String 									getFullFileName();
	
	public long 									getLastModifiedTime();

	public Regular 									touch();
	public Directory								mkdir(final boolean _createParentIfNonExist);
	public default Directory						mkdir() 																				{ return mkdir(false); }
	public default Directory						mkdirs() 																				{ return mkdir(true); }

	public boolean 									cp(final Path _to, final boolean _overwrite) throws IOException;
	public boolean 									mv(final Path _to, final boolean _overwrite) throws IOException;

	public boolean 									rm();

	public void 									renameTo(final String newName) throws IOException;
	public void 									moveTo(final Path newDir) throws IOException;

	public static boolean 							isChild(final FileX _child, final FileX _parent) 										{ return isChild(_parent.toPath(), _child.toPath()); }
	public static boolean 							isChild(final Path _child, final Path _parent) 											{
/**/
	    Path parent = _parent.normalize();
	    System.out.println(parent.toString() + " = " + _child.startsWith(parent));
	    return _child.startsWith(parent);
/*/
	    return _child.startsWith(_parent.normalize());
/**/
	}

}
