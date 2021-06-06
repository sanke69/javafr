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
 * @file     FileObject.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.nio.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import fr.java.nio.file.FileX;

public class FileObject implements FileX {

	public static FileObject of(Path _path) {
		return new FileObject(_path);
	}

	private Path path;

	public FileObject(final String _path) {
		super();
		path = Paths.get(_path);
	}
	public FileObject(final Path _path) {
		super();
		path = _path;
	}
	public FileObject(final File _file) {
		super();
		path = _file.toPath();
	}
	public FileObject(final FileX _file) {
		super();
		path = _file.toPath();
	}
	protected FileObject(final FileObject _source) {
		super();
		path = _source.path;
	}

	@Override
	public boolean 				isExist() {
		return (path != null) ? Files.exists(path) : false;
	}
	@Override
	public boolean 				isExist(final String _child) {
		return (path != null) ? Files.exists(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isExist(final Path _child) {
		return (path != null) ? Files.exists(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isFile() {
		return (path != null) ? Files.isRegularFile(path) : false;
	}
	@Override
	public boolean 				isFile(final String _child) {
		return (path != null) ? Files.isRegularFile(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isFile(final Path _child) {
		return (path != null) ? Files.isRegularFile(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isDirectory() {
		return (path != null) ? Files.isDirectory(path) : false;
	}
	@Override
	public boolean 				isDirectory(final String _child) {
		return (path != null) ? Files.isDirectory(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isDirectory(final Path _child) {
		return (path != null) ? Files.isDirectory(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isRegularFile() {
		return (path != null) ? Files.isRegularFile(path) : false;
	}
	@Override
	public boolean 				isRegularFile(final String _child) {
		return (path != null) ? Files.isRegularFile(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isRegularFile(final Path _child) {
		return (path != null) ? Files.isRegularFile(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isLink() {
		return (path != null) ? Files.isSymbolicLink(path) : false;
	}
	@Override
	public boolean 				isLink(final String _child) {
		return (path != null) ? Files.isSymbolicLink(path.resolve(_child)) : false;
	}
	@Override
	public boolean 				isLink(final Path _child) {
		return (path != null) ? Files.isSymbolicLink(path.resolve(_child)) : false;
	}

	@Override
	public Path 				toPath() {
		return path;
	}
	@Override
	public File 				toFile() {
		if(path != null)
			return path.toFile();
		return null;
	}

	@Override
	public String 				getName() {
		return getFullFileName();
	}
	@Override
	public String 				getFileName() {
		return path.getFileName().toString().replaceFirst("[.][^.]+$", "");
	}
	@Override
	public String 				getExtension() {
		String filename = path.getFileName().toString();
		if(filename.lastIndexOf('.') != -1)
			return filename.substring(filename.lastIndexOf('.'));
		return "";
	}
	@Override
	public String 				getFullPath() {
		return path.toString();
	}
	@Override
	public String 				getFullFileName() {
		return path.getFileName().toString();
	}

	@Override
	public long 				getLastModifiedTime() {
		return path.toFile().lastModified();
	}

	@Override
	public FileX.Directory		getParent() {
		return DirectoryObject.of(path.getParent());
	}

	@Override
	public DirectoryObject		asDirectory() 	{ return isDirectory() ? new DirectoryObject(this) : null; }
	@Override
	public RegularFileObject 	asRegularFile() { return isRegularFile() ? new RegularFileObject(this) : null; }
	@Override
	public Link 		asLink()		{ return null; }

	@Override
	public FileX.Regular		touch() {
		if(isExist()) {
			toFile().setLastModified(System.currentTimeMillis());
			return new RegularFileObject(this);
		} else
			try {
				if(toFile().createNewFile())
					return new RegularFileObject(this);
				return null;
			} catch(IOException e) {
				return null;
			}
	}

	@Override
	public FileX.Directory		mkdir(final boolean _createParentIfNonExist) {
		if(toFile().exists() && toFile().isDirectory())
			return new DirectoryObject(this);
		else {
			if(!_createParentIfNonExist) {
				if(toFile().mkdir())
					return new DirectoryObject(this);
			} else {
				if(path.toFile().mkdirs())
					return new DirectoryObject(this);
			}
		}
		return null;
	}
	@Override
	public boolean 				cp(final Path _to, final boolean _overwrite) throws IOException {
		if(_overwrite)
			return Files.copy(path, _to, _overwrite ? StandardCopyOption.REPLACE_EXISTING : null) != null;
		else
			return Files.copy(path, _to) != null;
	}
	@Override
	public boolean 				mv(final Path _to, final boolean _overwrite) throws IOException {
		if(_overwrite) {
			return Files.move(path, _to, _overwrite ? StandardCopyOption.REPLACE_EXISTING : null) != null;
		} else {
			return Files.move(path, _to) != null;
		}
	}

	@Override
	public boolean 				rm() {
		try {
			Files.delete(path);
		} catch(NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", path.toString());
			return false;
		} catch(DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", path.toString());
			return false;
		} catch(IOException x) {
			System.err.println(x);
			return false;
		}
		return true;
	}

	@Override
	public void 				renameTo(final String newName) throws IOException {
		Files.move(path, path.resolveSibling(newName));
	}
	@Override
	public void 				moveTo(Path newDir) throws IOException {
		Files.move(path, newDir.resolve(path.getFileName())/* , StandardCopyOption.REPLACE_EXISTING */);
	}

	public int 					hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean 				equals(final Object _fo) {
		if(!(_fo instanceof FileObject))
			return false;
		return path.equals(((FileObject) _fo).toPath());
	}

	@Override
	public String				toString() {
		return "FILE: " + toPath().toString();
	}

}
