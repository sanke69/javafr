package fr.java.sdk.nio.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.nio.file.FileX;
import fr.java.sdk.nio.file.filters.samples.DirectoryFileFilter;
import fr.java.sdk.nio.file.filters.samples.NoExtensionFileFilter;
import fr.java.sdk.nio.file.filters.samples.ops.AndFileFilter;
import fr.java.sdk.nio.file.filters.samples.ops.NotFileFilter;

public class DirectoryObject extends FileObject implements FileX.Directory {

	public static DirectoryObject of(Path _path) {
		return new DirectoryObject(_path);
	}
	public static DirectoryObject of(FileX _file) {
		return new DirectoryObject(_file);
	}

	public DirectoryObject(String _path) {
		super(_path);
		if(!isDirectory())
			throw new IllegalArgumentException();
	}
	public DirectoryObject(Path _path) {
		super(_path);
		if(!isDirectory())
			throw new IllegalArgumentException();
	}
	public DirectoryObject(FileX _file) {
		super(_file);
		if(!isDirectory())
			throw new IllegalArgumentException();
	}

	@Override
	public DirectoryObject		mkdir(final Path _child, final boolean _createParentIfNonExist) throws IOException {
		Path makePath = toPath().resolve(_child);

		if(makePath.toFile().mkdirs())
			return new DirectoryObject(makePath);
		if(makePath.toFile().isDirectory())
			return new DirectoryObject(makePath);
//			throw new IOException();
		return null;
	}

	@Override
	public Set<? extends FileX> getChildren(final boolean _recursive) {
		if(!isExist() || isFile())
			return new HashSet<FileX>();

		File     root        = toPath().toFile();
		String[] directories = root.list((current, name) -> new File(current, name).isDirectory());
		String[] files       = root.list((current, name) -> new File(current, name).isFile());

		Set<FileX> children = new HashSet<FileX>();
		for(String dir : directories) {
			children.add(new FileObject(toPath().resolve(dir)));
			if(_recursive)
				children.addAll(new DirectoryObject(toPath().resolve(dir)).ls(true));
		}
		for(String file : files)
			children.add(new FileObject(toPath().resolve(file)));

		return children;
	}
	@Override
	public FileX 				getChild(final Path _path, final boolean _createIfMissing) {
		if(toPath().resolve(_path).toFile().exists())
			return new FileObject(toPath().resolve(_path));
		
		if(_createIfMissing)
			return addChild(new FileObject(toPath().resolve(_path)));

		return null;
	}
	public FileX 				getChild(final String _path) {
		return getChild(_path, false);
	}
	public FileX 				getChild(final String _path, final boolean _createIfMissing) {
		if(toPath().resolve(_path).toFile().exists())
			return new FileObject(toPath().resolve(_path));
		
		if(_createIfMissing)
			return addChild(new FileObject(toPath().resolve(_path)));

		return null;
	}

	@Override
	public FileX 				addChild(final FileX _fo) {
		throw new NotYetImplementedException();
//			return FileX.isChild(this, _fo) ? (_fo.touch() ? _fo : null) : null;
	}

	@Override
	public Set<? extends FileX> ls(final FileFilter _ff, final boolean _recursive, final int _maxLevel) {
		if(!isExist())
			return new HashSet<FileObject>();
		if(isFile())
			return new HashSet<FileObject>();
		
		Comparator<? super FileX> sort = (c, n) -> {
			if(c.isDirectory() && n.isFile())
				return -1;
			if(c.isFile() && n.isDirectory())
				return 1;
			return c.getFullFileName().compareTo(n.getFullFileName());
		};
		
		try {
			if(_recursive)
				return Files.find(toPath(), _maxLevel, (p, bfa) -> _ff == null ? true : _ff.accept(p.toFile()))
							.map((f) -> new FileObject(f))
							.sorted(sort)
							.collect(Collectors.toSet());
			else
				return Arrays.asList(toPath().toFile().listFiles(_ff))
							.parallelStream()
							.map((f) -> new FileObject(f))
							.sorted(sort)
							.collect(Collectors.toSet());
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override public Set<? extends FileX> ls() 																					{ return ls(null, false, 0); }
	@Override public Set<? extends FileX> ls(final boolean _recursive)															{ return ls(null, _recursive, Integer.MAX_VALUE); }
	@Override public Set<? extends FileX> ls(final FileFilter _ff)																{ return ls(_ff, false, 0); }
	@Override public Set<? extends FileX> ls(final FileFilter _ff, final boolean _recursive)									{ return ls(_ff, _recursive, Integer.MAX_VALUE); }
	@Override public Set<? extends FileX> lsDirs()																				{ return ls(new DirectoryFileFilter(), false, 0); }
	@Override public Set<? extends FileX> lsDirs(final boolean _recursive)														{ return ls(new DirectoryFileFilter(), _recursive, Integer.MAX_VALUE); }
	@Override public Set<? extends FileX> lsDirs(final FileFilter _ff)															{ return ls(new AndFileFilter(new DirectoryFileFilter(), _ff), false, 0); }
	@Override public Set<? extends FileX> lsDirs(final FileFilter _ff, final boolean _recursive)								{ return ls(new AndFileFilter(new DirectoryFileFilter(), _ff), _recursive, Integer.MAX_VALUE); }
	@Override public Set<? extends FileX> lsDirs(final FileFilter _ff, final boolean _recursive, final int _maxLevel)			{ return ls(new AndFileFilter(new DirectoryFileFilter(), _ff), _recursive, _maxLevel); }
	@Override public Set<? extends FileX> lsFiles()																				{ return ls(new NotFileFilter(new DirectoryFileFilter()), false, 0); }
	@Override public Set<? extends FileX> lsFiles(final boolean _recursive)														{ return ls(new NotFileFilter(new DirectoryFileFilter()), _recursive, Integer.MAX_VALUE); }
	@Override public Set<? extends FileX> lsFiles(final FileFilter _ff)															{ return ls(new AndFileFilter(new NotFileFilter(new DirectoryFileFilter()), _ff), false, 0); }
	@Override public Set<? extends FileX> lsFiles(final FileFilter _ff, final boolean _recursive) 								{ return ls(new AndFileFilter(new NotFileFilter(new DirectoryFileFilter()), _ff), _recursive, Integer.MAX_VALUE); }
	@Override public Set<? extends FileX> lsFiles(final FileFilter _ff, final boolean _recursive, final int _maxLevel) 			{ return ls(new AndFileFilter(new NotFileFilter(new DirectoryFileFilter()), _ff), _recursive, _maxLevel); }

	@Override
	public FileX 				find(final String _childName, final boolean _recursive) {
		try {
			Collection<FileObject> candidates = Files.find(toPath(), _recursive ? Integer.MAX_VALUE : 1, (p, bfa) -> p.getFileName().toString().compareToIgnoreCase(_childName) == 0)
														.map((f) -> new FileObject(f))
														.collect(Collectors.toList());
			return candidates.size() == 1 ? candidates.iterator().next() : null;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public FileX 				find(final FileFilter _ff, final boolean _recursive) {
		if(_ff == null)
			throw new NullPointerException();

		try {
			Collection<FileObject> candidates = Files	.find(toPath(), _recursive ? 999 : 1, (p, bfa) -> _ff.accept(p.toFile()))
														.map((f) -> new FileObject(f))
														.collect(Collectors.toList());

			if(_ff instanceof NoExtensionFileFilter) {
				if(candidates.size() != 2)
					return null;
	
				Iterator<FileObject> itor = candidates.iterator();
				itor.next();
				return itor.next();
			} else
				if(_ff instanceof DirectoryFileFilter)
					candidates.remove(this);

			return candidates.size() == 1 ? candidates.iterator().next() : null;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Set<? extends FileX> findAll(final FileFilter _ff, final boolean _recursive) {
		try {
			return Files.find(toPath(), _recursive ? Integer.MAX_VALUE : 1, (p, bfa) -> _ff == null ? true : _ff.accept(p.toFile()))
					.map((f) -> new FileObject(f))
					.sorted((c, n) -> {
						if(c.isDirectory() && n.isFile())
							return -1;
						if(c.isFile() && n.isDirectory())
							return 1;
						return c.getFullFileName().compareTo(n.getFullFileName());
					})
					.collect(Collectors.toSet());
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RegularFileObject		touch(final Path _child) throws IOException {
		Path makePath = toPath().resolve(_child);

		if(makePath.toFile().isFile())
			return new RegularFileObject(makePath);
		if(makePath.toFile().createNewFile())
			return new RegularFileObject(makePath);
		throw new IOException();
	}

	@Override
	public boolean 				rm(final Path _child) {
		Path rmPath = toPath().resolve(_child);

		try {
			Files.delete(rmPath);
		} catch(NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", rmPath.toString());
			return false;
		} catch(DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", rmPath.toString());
			return false;
		} catch(IOException x) {
			System.err.println(x);
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return"DIR: " + toPath().toString();
	}

}
	