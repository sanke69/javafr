package fr.java.sdk.file.archive.multi;

import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.java.file.archive.ArchiverMulti;
import fr.java.lang.exceptions.AlreadyExistingEntry;
import fr.java.sdk.lang.tuples.SimplePair;
import fr.java.utils.LocalFiles;

abstract class AbstractArchiver implements ArchiverMulti {

	protected Map<String, Path>			fileMap;
	protected Map<String, InputStream>	streamMap;

	protected AbstractArchiver() {
		super();
		fileMap   = new HashMap<String, Path>();
		streamMap = new HashMap<String, InputStream>();
	}

	public Map<String, Path> 			fileMap() {
		return fileMap;
	}
	public Map<String, InputStream> 	streamMap() {
		return streamMap;
	}

	@Override
	public AbstractArchiver 			addFile(String _entryName, Path _file) throws FileNotFoundException, AlreadyExistingEntry {
		if(!LocalFiles.isExist(_file))
			throw new FileNotFoundException();

		if(fileMap.containsKey(_entryName))
			throw new AlreadyExistingEntry();

		fileMap.put(_entryName, _file);
		return this;
	}
	@Override
	public AbstractArchiver 			addFiles(String _rootEntriesName, Set<Path> _files) throws FileNotFoundException, AlreadyExistingEntry {
		for(Path f : _files) {
			Path filename   = f.getParent().relativize(f);
			Path foldername = Paths.get(_rootEntriesName);

			addFile(foldername.resolve(filename).toString(), f);
		}
		return this;
	}

	@Override
	public AbstractArchiver 			addDirectory(String _entryName, Path _dirPath) throws FileNotFoundException, AlreadyExistingEntry {
		return addDirectory(_entryName, _dirPath, null);
	}
	@Override
	public AbstractArchiver 			addDirectory(String _entryName, Path _dirPath, FileFilter _filter) throws FileNotFoundException, AlreadyExistingEntry {
		final Path foldername = Paths.get(_entryName);

		LocalFiles.concurrentWalk(_dirPath, _filter, (p) -> {
			Path from = p;
			Path to   = foldername.resolve(_dirPath.relativize(p));	

			try {
				addFile(to.toString(), from);
			} catch (FileNotFoundException e) {
				throw new UncheckedIOException(e);
			} catch (AlreadyExistingEntry e) {
				throw new UncheckedIOException(e.getMessage(), null);
			}
		});

		return this;
	}
	@Override
	public AbstractArchiver 			addDirectories(String _entryName, Set<Path> _directoryPathes) throws FileNotFoundException, AlreadyExistingEntry {
		return addDirectories(_entryName, _directoryPathes, null);
	}
	@Override
	public AbstractArchiver 			addDirectories(String _rootEntriesName, Set<Path> _directoryPathes, FileFilter _filter) throws FileNotFoundException, AlreadyExistingEntry {
		for(Path dir : _directoryPathes)
			addDirectory(_rootEntriesName, dir, _filter);
		return this;
	}

	public AbstractArchiver 			addInputStream(String _entryName, InputStream _inputStream) throws IOException, AlreadyExistingEntry {
		streamMap.put(_entryName, _inputStream);
		return this;
	}

	protected List<SimplePair<String, Path>> orderedContentList() {
		List<SimplePair<String, Path>> list = new LinkedList<SimplePair<String, Path>>();
		
		fileMap().entrySet().stream().forEach(e -> list.add(new SimplePair<String, Path>(e.getKey(), e.getValue())));
		
		Collections.sort(list, (e1, e2) -> {
			if(e1.getSecond().toFile().isDirectory() && e2.getSecond().toFile().isFile())
				return -1;
			if(e1.getSecond().toFile().isFile() && e2.getSecond().toFile().isDirectory())
				return 1;
			return e1.getSecond().compareTo(e2.getSecond());
		});

		return list;
	}

}
