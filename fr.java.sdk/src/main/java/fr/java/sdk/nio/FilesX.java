package fr.java.sdk.nio;

import java.io.File;
import java.nio.file.Path;

import fr.java.nio.file.FileX;
import fr.java.sdk.nio.file.DirectoryObject;
import fr.java.sdk.nio.file.FileObject;
import fr.java.sdk.nio.file.RegularFileObject;

public final class FilesX {

	public final static class Regular {

		public static FileX.Regular 	of(FileX _file) {
			return _file.isRegularFile() ? new RegularFileObject(_file) : null;
		}

	}

	public final static class Directory {

		public static FileX.Directory 	of(Path _path) {
			return _path.toFile().isDirectory() ? new DirectoryObject(_path) : null;
		}
		public static FileX.Directory 	of(FileX _file) {
			return _file.isDirectory() ? new DirectoryObject(_file) : null;
		}

	}

	public static FileX 				of(File _file) {
		return new FileObject(_file.toPath());
	}
	public static FileX 				of(Path _path) {
		return new FileObject(_path);
	}

}
