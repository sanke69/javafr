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
 * @file     Files.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import fr.java.nio.file.FileX;
import fr.java.sdk.nio.file.DirectoryObject;
import fr.java.sdk.nio.file.filters.samples.DirectoryFileFilter;
import fr.java.sdk.nio.file.filters.samples.ops.AndFileFilter;
import fr.java.sdk.nio.file.filters.samples.ops.NotFileFilter;
import fr.java.utils.Preconditions;
import fr.java.utils.Streams;

public class LocalFiles {

	public static boolean 			isExist(Path _path) {
		return Files.exists(_path);
	}

	public static boolean 			isFile(Path _path) {
		return Files.isRegularFile(_path);
	}
	public static boolean 			isDirectory(Path _path) {
		return Files.isDirectory(_path);
	}
	public static boolean 			isLink(Path _path) {
		return Files.isSymbolicLink(_path);
	}

	public static boolean isUtf8File(Path _path) {
		try(FileInputStream fis  = new FileInputStream(_path.toFile())) {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"))) {
				String line = br.readLine();
				return line.startsWith("\uFEFF");
			}
		} catch(FileNotFoundException e) {
			return false;
		} catch(IOException e) {
			return false;
		}
	}

	public static boolean 			isBinaryFile(Path _path) throws FileNotFoundException, IOException {
	    FileInputStream in = new FileInputStream(_path.toFile());
	    int size = in.available();
	    if(size > 1024) size = 1024;
	    byte[] data = new byte[size];
	    in.read(data);
	    in.close();

	    int ascii = 0;
	    int other = 0;

	    for(int i = 0; i < data.length; i++) {
	        byte b = data[i];
	        if( b < 0x09 ) return true;

	        if( b == 0x09 || b == 0x0A || b == 0x0C || b == 0x0D ) ascii++;
	        else if( b >= 0x20  &&  b <= 0x7E ) ascii++;
	        else other++;
	    }

	    if( other == 0 ) return false;

	    return 100 * other / (ascii + other) > 95;
	}

	public static Path 				getParent(Path _path) {
		return _path.getParent();
	}

	public static String 			getFullPath(Path _path) {
		return _path.toString();
	}
	public static String 			getFullFileName(Path _path) {
		return _path.getFileName().toString();
	}
	public static String 			getFileName(Path _path) {
		return _path.getFileName().toString().replaceFirst("[.][^.]+$", "");
	}
	public static String 			getExtension(Path _path) {
		String filename = _path.getFileName().toString();
		if(filename.lastIndexOf('.') != -1)
			return filename.substring(filename.lastIndexOf('.'));
		return "";
	}

	public static long 				getSizeInBytes(Path _path) throws IOException {
		Preconditions.nonNull(_path);
		if(_path != null)
			throw new NullPointerException();
		return java.nio.file.Files.size(_path);
	}

	public static int 				getFileCount(Path _path, boolean _recursive) {
		int count = 0;
		for(File file : _path.toFile().listFiles()) {
			if(file.isFile())
				count++;

			if(file.isDirectory() && _recursive)
				count += getFileCount(file.toPath(), _recursive);
		}
		return count;
	}
	public static int 				getFileCount(Path _path, boolean _onlyFile, boolean _recursive) {
		int count = 0;
		for(File file : _path.toFile().listFiles()) {
			if(file.isFile())
				count++;

			if(file.isDirectory() && _recursive)
				count += getFileCount(file.toPath(), _recursive);
		}
		return count;
	}

	public static void 				setLastModifiedTime(Path _path, long _timestamp) throws IOException {
		_path.toFile().setLastModified(_timestamp);
	}
	public static long 				getLastModifiedTime(Path _path) throws IOException {
		return _path.toFile().lastModified();
	}

	public static Path 				find(Path _path, String _childName) throws IOException {
		return find(_path, _childName, false);
	}
	public static Path 				find(Path _path, String _childName, boolean _recursive) throws IOException {
		Collection<Path> candidates = 
				Files.find(_path, _recursive ? Integer.MAX_VALUE : 1, (p, bfa) -> p.getFileName().toString().compareToIgnoreCase(_childName) == 0)
					 .collect(Collectors.toList());

		return candidates.size() == 1 ? candidates.iterator().next() : null;
	}

	public static Stream<Path> 		list(Path _path) throws IOException {
		return java.nio.file.Files.list(_path);
	}

	public static Collection<Path> 	ls(Path _path) throws IOException {
		return ls(_path, null, 1);
	}
	public static Collection<Path> 	ls(Path _path, FileFilter _ff) throws IOException {
		return ls(_path, _ff, 1);
	}
	public static Collection<Path> 	ls(Path _path, int _maxLevel) throws IOException {
		return ls(_path, null, _maxLevel);
	}
	public static Collection<Path> 	ls(Path _path, FileFilter _ff, int _maxLevel) throws IOException {
		if(!isExist(_path) || isFile(_path))
			return null;

		Comparator<Path> sort = (a, b) -> {
			if(isDirectory(a) && isFile(b))
				return -1;
			if(isFile(a) && isDirectory(b))
				return 1;
			return getFullFileName(a).compareTo(getFullFileName(b));
		};

		return Files.find(_path, _maxLevel, (p, bfa) -> _ff == null ? true : _ff.accept(p.toFile()))
					.sorted(sort)
					.collect(Collectors.toList());
	}

	public static Collection<Path> 	lsDirs(Path _path) throws IOException {
		return ls(_path, new DirectoryFileFilter(), Integer.MAX_VALUE);
	}
	public static Collection<Path> 	lsDirs(Path _path, FileFilter _ff) throws IOException {
		return ls(_path, new AndFileFilter(new DirectoryFileFilter(), _ff), Integer.MAX_VALUE);
	}
	public static Collection<Path> 	lsDirs(Path _path, int _maxSubLevel) throws IOException {
		return ls(_path, new DirectoryFileFilter(), _maxSubLevel);
	}
	public static Collection<Path> 	lsDirs(Path _path, FileFilter _ff, int _maxSubLevel) throws IOException {
		return ls(_path, new AndFileFilter(new DirectoryFileFilter(), _ff), _maxSubLevel);
	}

	public static Collection<Path> 	lsFiles(Path _path) throws IOException {
		return ls(_path, new NotFileFilter(new DirectoryFileFilter()), Integer.MAX_VALUE);
	}
	public static Collection<Path> 	lsFiles(Path _path, FileFilter _ff) throws IOException {
		return ls(_path, new AndFileFilter(new NotFileFilter(new DirectoryFileFilter()), _ff), Integer.MAX_VALUE);
	}
	public static Collection<Path> 	lsFiles(Path _path, int _maxSubLevel) throws IOException {
		return ls(_path, new NotFileFilter(new DirectoryFileFilter()), _maxSubLevel);
	}
	public static Collection<Path> 	lsFiles(Path _path, FileFilter _ff, int _maxSubLevel) throws IOException {
		return ls(_path, new AndFileFilter(new NotFileFilter(new DirectoryFileFilter()), _ff), _maxSubLevel);
	}

	public static boolean 			touch(Path _path) throws IOException {
		if(isExist(_path))
			return _path.toFile().setLastModified(System.currentTimeMillis());
		else
			return _path.toFile().createNewFile();
	}
	
	public static FileX.Directory	mkdir(Path _path) throws IOException {
		return mkdir(_path, false);
	}
	public static FileX.Directory	mkdir(Path _path, boolean _createParentIfNonExist) throws IOException {
		if(isExist(_path))
			if(isDirectory(_path))
				return new DirectoryObject(_path);

		if(!_createParentIfNonExist) {
			if(_path.toFile().mkdir())
				return new DirectoryObject(_path);
		} else {
			if(_path.toFile().mkdirs())
				return new DirectoryObject(_path);
		}

		return null;
	}

	public static void 				cp(Path _src, Path _dst) throws IOException {
		cp(_src, _dst, false, true, true);
	}
	public static void 				cp(Path _src, Path _dst, boolean _overwrite) throws IOException {
		cp(_src, _dst, _overwrite, true, true);
	}
	public static void 				cp(Path _src, Path _dst, boolean _overwrite, boolean _preserve_attributes, boolean _follow_symlinks) throws IOException {
		// Options: 
		//  - LinkOption.NOFOLLOW_LINKS
		//  - StandardCopyOption.COPY_ATTRIBUTES
		//  - StandardCopyOption.REPLACE_EXISTING

		final List<CopyOption> optList = new ArrayList<CopyOption>();
		if(_overwrite)
			optList.add(StandardCopyOption.REPLACE_EXISTING);
		if(_preserve_attributes)
			optList.add(StandardCopyOption.COPY_ATTRIBUTES);
		if(!_follow_symlinks)
			optList.add(LinkOption.NOFOLLOW_LINKS);

		CopyOption[] opts = new CopyOption[optList.size()];
		optList.toArray(opts);

		java.nio.file.Files.copy(_src, _dst, opts);
	}

	public static void 				copyFile(final Path src, final Path dest) throws IOException {
		if(!isFile(src))
			throw new IOException("Not a file: " + src);
		if(dest.toFile().isDirectory())
			throw new IOException("is a Folder" + dest);

		try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(src.toFile()))) {
			try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest.toFile(), false))) {
				Streams.copyStream(in, out, 1024);
			}
		}
		dest.toFile().setLastModified(src.toFile().lastModified());
	}

	public static void 				copyFolder(final Path _src, final Path _dst) throws IOException {
		copyFolder(_src, _dst, true, false, null);
	}
	public static void 				copyFolder(final Path _src, final Path _dst, final boolean _includeSubDirs) throws IOException {
		copyFolder(_src, _dst, _includeSubDirs, false, null);
	}
	public static void 				copyFolder(final Path _src, final Path _dst, final boolean _includeSubDirs, final boolean _onlyNew) throws IOException {
		copyFolder(_src, _dst, _includeSubDirs, _onlyNew, null);
	}
	public static void 				copyFolder(final Path _src, final Path _dst, final boolean _includeSubDirs, final boolean _onlyNew, final FileFilter _ff) throws IOException {
		if(!isDirectory(_src))
			throw new IOException(_src + " is not a directory");
		if(isFile(_dst))
			throw new IOException(_dst + "already exist and is a file");
		if(!isExist(_dst) && mkdir(_dst, true) == null)
			throw new IOException(_dst + "does not exist or can't be created");

		for(Path fd : ls(_src, _ff, _includeSubDirs ? Integer.MAX_VALUE : 0)) {
			if(isDirectory(fd) && _includeSubDirs) {
				copyFolder(fd, _dst.resolve(_src.relativize(fd)), _includeSubDirs, _onlyNew, _ff);
				continue;
			}

			Path dstPath = _dst.resolve(_src.relativize(fd));
			if(_onlyNew && isFile(dstPath) && (getLastModifiedTime(dstPath) > getLastModifiedTime(fd)))
				continue;

			copyFile(fd, dstPath);
		}
		setLastModifiedTime(_dst, getLastModifiedTime(_src));
	}

	public static void 				synchronizeFolders(final Path src, final Path dest) throws IOException {
		synchronizeFolders(src, dest, null);
	}
	public static void 				synchronizeFolders(final Path _src, final Path _dst, final FileFilter _ff) throws IOException {
		if(!isDirectory(_src))
			throw new IOException(_src + " is not a directory");
		if(isFile(_dst))
			throw new IOException(_dst + "already exist and is a file");
		if(!isExist(_dst) && mkdir(_dst, true) == null)
			throw new IOException(_dst + "does not exist or can't be created");

		for(Path srcPath : ls(_src, _ff)) {
			Path dstPath = _dst.resolve(_src.relativize(srcPath));

			if(isDirectory(srcPath)) {
				if(srcPath == _src)
					continue;

				if(isFile(dstPath) && !rm(dstPath))
					throw new IOException("can't delete file " + dstPath + " to set it as folder");

				synchronizeFolders(srcPath, _dst.resolve(_src.relativize(srcPath)), _ff);
				continue;
			}

			if(compareFiles(srcPath.toFile(), dstPath.toFile()))
				continue;

			copyFile(srcPath, dstPath);
		}

		for(Path dstPath : ls(_dst, _ff)) {
			Path srcPath = _src.resolve(_dst.relativize(dstPath));

			if(isExist(srcPath))
				continue;

			if(isDirectory(dstPath)) {
//				if(isDirectory(dstPath) && !emptyFolder(dstPath.toFile()))
//					throw new IOException("can't delete non-empty folder" + dstPath);
				if(!rmdir(dstPath, true))
					throw new IOException("can't delete folder" + dstPath);
			} else
				rm(dstPath);
		}

		setLastModifiedTime(_dst, getLastModifiedTime(_src));
	}

	public static void 				mv(Path _src, Path _dst) throws IOException {
		mv(_src, _dst, false);
	}
	public static void 				mv(Path _src, Path _dst, boolean _overwrite) throws IOException {
		// Options: 
		//  - StandardCopyOption.REPLACE_EXISTING
		if(_overwrite)
			java.nio.file.Files.move(_src, _dst, StandardCopyOption.REPLACE_EXISTING);
		else
			java.nio.file.Files.move(_src, _dst, (CopyOption[]) null);
	}

	public static boolean 			rm(Path _path) throws IOException {
		if(isDirectory(_path))
			return false;

		Files.delete(_path);
		return true;
	}
	public static boolean 			rmdir(Path _path) throws IOException {
		return rmdir(_path, false);
	}
	public static boolean 			rmdir(Path _path, boolean _evenIfNotEmpty) throws IOException {
		if(!isDirectory(_path))
			return false;

		lsFiles(_path, Integer.MAX_VALUE).stream()
										 .forEach(p -> { try {
											 rm(p) ;
										 } catch(IOException _e) {
											 _e.printStackTrace();
										 }});
		lsDirs(_path, Integer.MAX_VALUE).stream()
										 .forEach(p -> { if(p != _path) try {
											 rmdir(p, true) ;
										 } catch(IOException _e) {
											 _e.printStackTrace();
										 }});
		Files.delete(_path);
		return true;
	}



	public static List<String> 		readAscii(Path file, boolean ignoreComments) throws IOException {
		if (!file.toFile().exists() || !file.toFile().isFile()) {
			return new ArrayList<>();
		}

		List<String> lines = new ArrayList<>();

		BufferedReader reader = null;
		try {
	        reader = new BufferedReader(new FileReader(file.toFile()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (ignoreComments && !line.startsWith("#") && !lines.contains(line)) {
					lines.add(line);
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return lines;
	}

	public static void 				writeAscii(Path _file, String _content) throws IOException {
		writeAscii(_file, Arrays.asList(_content), false);
	}
	public static void 				writeAscii(Path _file, Collection<String> _lines) throws IOException {
		writeAscii(_file, _lines, false);
	}
	
	public static void 				writeAscii(Path _file, String _content, boolean _overwrite) throws IOException {
		writeAscii(_file, Arrays.asList(_content), _overwrite);
	}
    public static void 				writeAscii(Path _file, Collection<String> _lines, boolean _overwrite) throws IOException {
    	if(_file.toFile().exists() && !_overwrite)
    		throw new IOException("File already exists");
    	
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(_file.toFile()))) {
            for(String line : _lines)
                writer.write(line + "\n");
        }
//		Files.write(_file, Arrays.asList(_lines), Charset.forName("UTF-8"));
//		Files.write(_file, _lines, Charset.forName("UTF-8"));
    }

    public static OutputStream		readBinary(Path _path) throws FileNotFoundException {
    	FileInputStream 		fis  = new FileInputStream(_path.toFile());
    	ByteArrayOutputStream 	baos = new ByteArrayOutputStream(); 

    	return baos;
    }

	public static void 				writeBinary(Path _path, byte[] _content) throws IOException {
		Files.write(_path, _content);
	}
	public static void 				writeBinary(Path _path, InputStream _is) throws IOException {
		try(FileOutputStream fos = new FileOutputStream(_path.toFile())) {
			byte[] buffer = new byte[4_048];
			while(_is.available() > 0) {
				int nbRead = _is.read(buffer, 0, buffer.length);
				fos.write(buffer, 0, nbRead);
			}
		}
	}



	public static void main(String[] args) throws IOException {
		Path test = Paths.get("/home/sanke/TEST");
		Path from = test.resolve("from/");
		Path to   = test.resolve("to/");
/*
		mkdir(from);
		String[] files = new String[] { "A", "B", "C", "D", "E" };
		for(String d : files) {
			Path dir = from.resolve(d);
			mkdir(dir);
			for(String f : files)
				touch(dir.resolve(f));
		}
		*/
		synchronizeFolders(from, to);
	}
	
	



	public static URL 				getURLForMedia(String _partialPath) {
		URL url = null;
		if((url = getURLForFile(_partialPath)) != null)
			return url;
		if((url = getURLForResource(_partialPath)) != null)
			return url;

		return null;
	}
	public static URL 				getURLForFile(String _filename) {
		try {
			if(new File(_filename).exists())
				return new URL("file://" + _filename);

			String filename = _filename.codePointAt(0) == '/' ? _filename.substring(1) : _filename;

			for(Path path : Resources.searchPaths()) {
				File f = new File(path + filename);
				if(f.exists())
					return new URL("file://" + path + filename);
			}
		} catch(MalformedURLException e) { e.printStackTrace(); }

		return null;
	}
	public static URL 				getURLForResource(String _partialPath) {
		URL url = LocalFiles.class.getResource(_partialPath);

		if(url == null)
			url = LocalFiles.class.getResource("." + _partialPath);
		if(url == null)
			url = LocalFiles.class.getResource("/" + _partialPath);
		if(url == null)
			url = LocalFiles.class.getResource("./" + _partialPath);
		if(url == null)
			url = LocalFiles.class.getResource("../" + _partialPath);
		if(url == null)
			url = LocalFiles.class.getClassLoader().getResource(_partialPath);
		if(url == null)
			url = LocalFiles.class.getClassLoader().getResource("." + _partialPath);
		if(url == null)
			url = LocalFiles.class.getClassLoader().getResource("/" + _partialPath);
		if(url == null)
			url = LocalFiles.class.getClassLoader().getResource("./" + _partialPath);
		if(url == null)
			url = LocalFiles.class.getClassLoader().getResource("../" + _partialPath);

		return url;
	}
	public static URL 				getURLForResource(Class<?> _class, String _partialPath) {
		URL url = _class.getResource(_partialPath);

		if(url == null)
			url = _class.getResource(_partialPath.substring(1));
		if(url == null)
			url = _class.getResource("." + _partialPath);
		if(url == null)
			url = _class.getResource("/" + _partialPath);
		if(url == null)
			url = _class.getResource("./" + _partialPath);
		if(url == null)
			url = _class.getResource("../" + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource(_partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("." + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("/" + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("./" + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("../" + _partialPath);
/*
		if(url == null) {
			String pckDir   = _class.getPackage().getName().replace(".", "/");
			String resource = pckDir + "/" + _partialPath;
			
			for(URL Url : ((URLClassLoader) (_class.getClassLoader())).getURLs())
				System.out.println(Url);
			
			String p = ClassLoader.getSystemResource(resource).toExternalForm();
			
			url = getURLForResource(_class, resource);
		}
*/
		return url;
	}


	public static InputStream 		getContent(File _file) {
		return getContent(_file.toPath());
	}
	public static byte[] 			getContentAsByteArrays(File _file) {
		return getContentAsByteArrays(_file.toPath());
	}
	public static String 			getContentAsString(File _file) {
		return getContentAsString(_file.toPath());
	}

    public static InputStream 		getContent(Path _path) {
		try {
			return new FileInputStream(_path.toFile());
		} catch(Exception e) {
			URL    url  = getURLForFile(_path.toString());
			String path = url.getPath();
			try {
				return new FileInputStream(new File(path));
			} catch(Exception e2) {
				return null;
			}
		}
    }
    public static byte[]      		getContentAsByteArrays(Path _path) {
    	byte[] bytes;
		try {
			FileInputStream fis = new FileInputStream(_path.toFile());
	        DataInputStream dis = new DataInputStream(fis);
	        bytes = new byte[dis.available()];
	        dis.readFully(bytes);
	        dis.close();
		} catch(IOException e) { return null; }

        return bytes;
	}
	public static String      		getContentAsString(Path _path) {
		byte[] data = getContentAsByteArrays(_path);
		if(data == null) return null;
		return new String(data);
		
//		char delimiter = '\n';
//		return Arrays.asList(new String(data).split("\\s*" + delimiter + "\\s*"));
	}

    public static InputStream 		getContent(Class<?> _class, String _path) {
		return getContent( getURLForResource(_class, _path) );
    }
    public static byte[]      		getContentAsByteArrays(Class<?> _class, String _path) {
		return getContentAsByteArrays( getURLForResource(_class, _path) );
	}
	public static String      		getContentAsString(Class<?> _class, String _path) {
		return getContentAsString( getURLForResource(_class, _path) );
	}

	public static InputStream 		getContent(URL _url) {
		InputStream is = null;
		try { is = _url.openStream(); } catch(Exception e) { is = null; }
		return is;
	}
	public static byte[]      		getContentAsByteArrays(URL _url) {
		InputStream is = getContent(_url);
		if(is == null) return null;
		byte[] buffer;
		try {
			buffer = new byte[is.available()];
			is.read(buffer, 0, buffer.length);
			return buffer;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String      		getContentAsString(URL _url) {
		byte[] data = getContentAsByteArrays(_url);
		if(data == null) return null;
		return new String(data);
	}




	/**
	 * Delete a file or recursively delete a folder.
	 *
	 * @param fileOrFolder
	 * @return true, if successful
	 */
	public static boolean 			delete(File fileOrFolder) {
		boolean success = false;
		if (fileOrFolder.isDirectory()) {
			File [] files = fileOrFolder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						success |= delete(file);
					} else {
						success |= file.delete();
					}
				}
			}
		}
		success |= fileOrFolder.delete();

		return success;
	}



	public static boolean 			emptyFolder(final File folder) {
		if(!folder.isDirectory())
			return true;

		File[] files = folder.listFiles();
		boolean result = true;
		for(File file : files) {
			if(file.isDirectory()) {
				if(emptyFolder(file))
					result &= file.delete();
				else
					result = false;
			} else
				result &= file.delete();
		}
		return result;
	}

	public static boolean 			compareFiles(final File file1, final File file2) {
		if(!file1.isFile() || !file2.isFile())
			return false;
		if(!file1.getName().equals(file2.getName()))
			return false;
		if(file1.length() != file2.length())
			return false;
		return compareFileDates(new Date(file1.lastModified()), new Date(file2.lastModified()));
	}

	public static boolean 			compareFileDates(final Date date1, final Date date2) {
		if((date1 == null) || (date2 == null))
			return false;

		Calendar cldr = Calendar.getInstance(Locale.ENGLISH);
		cldr.setTime(date1);
		cldr.set(Calendar.MILLISECOND, 0);
		long dt1 = cldr.getTimeInMillis();
		cldr.setTime(date2);
		cldr.set(Calendar.MILLISECOND, 0);
		long dt2 = cldr.getTimeInMillis();
		return dt1 == dt2;
	}

	public static boolean 			isResourceExists(final URL url) {
		File file = url2file(url);
		if(file != null)
			return file.canRead();

		if("jar".equalsIgnoreCase(url.getProtocol()))
			return isJarResourceExists(url);

		return isUrlResourceExists(url);
	}

	private static boolean 			isUrlResourceExists(final URL url) {
		try {
			InputStream is = url.openStream();
			try {
				is.close();
			} catch(IOException ioe) {
				// ignore
			}
			return true;
		} catch(IOException ioe) {
			return false;
		}
	}

	private static boolean 			isJarResourceExists(final URL url) {
		try {
			String urlStr = url.toExternalForm();
			int p = urlStr.indexOf("!/");
			if(p == -1)
				return false;

			URL fileUrl = new URL(urlStr.substring(4, p));
			File file = url2file(fileUrl);
			if(file == null) // this is non-local JAR file URL
				return isUrlResourceExists(url);

			if(!file.canRead())
				return false;

			if(p == urlStr.length() - 2)// URL points to the root entry of JAR file
				return true;

			JarFile jarFile = new JarFile(file);
			try {
				return jarFile.getEntry(urlStr.substring(p + 2)) != null;
			} finally {
				jarFile.close();
			}
		} catch(IOException ioe) {
			return false;
		}
	}

	public static InputStream 		getResourceInputStream(final URL url) throws IOException {
		File file = url2file(url);
		if(file != null)
			return new BufferedInputStream(new FileInputStream(file));

		if(!"jar".equalsIgnoreCase(url.getProtocol()))
			return url.openStream();

		String urlStr = url.toExternalForm();
		if(urlStr.endsWith("!/"))
			//JAR URL points to a root entry
			throw new FileNotFoundException(url.toExternalForm());

		int p = urlStr.indexOf("!/");
		if(p == -1)
			throw new MalformedURLException(url.toExternalForm());

		String path = urlStr.substring(p + 2);
		file = url2file(new URL(urlStr.substring(4, p)));
		if(file == null) // non-local JAR file URL
			return url.openStream();

		try(JarFile jarFile = new JarFile(file)) {
			ZipEntry entry = jarFile.getEntry(path);
			if(entry == null) {
				throw new FileNotFoundException(url.toExternalForm());
			}
			InputStream in = jarFile.getInputStream(entry);
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Streams.copyStream(in, out, 1024);
				return new ByteArrayInputStream(out.toByteArray());
			} finally {
				in.close();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static File 				url2file(final URL url) {
		String prot = url.getProtocol();
		if("jar".equalsIgnoreCase(prot)) {
			if(url.getFile().endsWith("!/")) {
				String urlStr = url.toExternalForm();
				try {
					return url2file(new URL(urlStr.substring(4, urlStr.length() - 2)));
				} catch(MalformedURLException mue) {
					// ignore
				}
			}
			return null;
		}
		if(!"file".equalsIgnoreCase(prot))
			return null;

		try {
			return new File(URLDecoder.decode(url.getFile(), "UTF-8"));
		} catch(UnsupportedEncodingException e) {
			return new File(URLDecoder.decode(url.getFile()));
		}
	}

	public static URL 				file2url(final File file) throws MalformedURLException {
		try {
			return file.getCanonicalFile().toURI().toURL();
		} catch(MalformedURLException mue) {
			throw mue;
		} catch(IOException ioe) {
			throw new MalformedURLException("file2url Failed: " + new Object[] { file, ioe });
		}
	}

	
	
	

	public static Collection<Path> 	actOnDirectories(Path _path) {
		try {
			java.nio.file.Files.walkFileTree(_path, new FileVisitor<Path>() {
		        private Pattern pattern = Pattern.compile("^(.*?)");

		        @Override
		        public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes atts) throws IOException {
		            boolean matches = pattern.matcher(path.toString()).matches();
		            return (matches)? FileVisitResult.CONTINUE:FileVisitResult.SKIP_SUBTREE;
		        }

		        @Override
		        public FileVisitResult visitFile(Path path, BasicFileAttributes mainAtts) throws IOException {
		            //boolean matches = pattern.matcher(path.toString()).matches();
		            return FileVisitResult.CONTINUE;
		        }

		        @Override
		        public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
		            return FileVisitResult.CONTINUE;
		        }

		        @Override
		        public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
		            exc.printStackTrace();
		            return path.equals(_path) ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
		        }
		    });

		} catch(IOException ioe) {

		}

		return null;
	}

    private static class RecursiveWalk extends RecursiveAction {
        private static final long serialVersionUID = 6913234076030245489L;
        private final Path dir;

        private FileFilter filter;
        private Consumer<Path> 	action;

        public RecursiveWalk(Path dir, FileFilter _filter, Consumer<Path> 	_action) {
            this.dir = dir;
            filter = _filter;
            action = _action;
        }

        @Override
        protected void compute() {
            final List<RecursiveWalk> walks = new ArrayList<>();
            try {
                java.nio.file.Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (!dir.equals(RecursiveWalk.this.dir)) {
                            RecursiveWalk w = new RecursiveWalk(dir, filter, action);
                            w.fork();
                            walks.add(w);

                            return FileVisitResult.SKIP_SUBTREE;
                        } else {
                            return FileVisitResult.CONTINUE;
                        }
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    	if(filter == null)
                    		action.accept(file);
                    	else if(filter.accept(file.toFile()))
                    		action.accept(file);

                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (RecursiveWalk w : walks) {
                w.join();
            }
        }
    }
    public static void concurrentWalk(Path _dir, FileFilter _filter, Consumer<Path> 	_action) {
        RecursiveWalk w = new RecursiveWalk(_dir, _filter, _action);
        ForkJoinPool p = new ForkJoinPool();
        p.invoke(w);
    }

}
