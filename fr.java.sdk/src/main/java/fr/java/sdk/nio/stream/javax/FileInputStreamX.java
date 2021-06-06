package fr.java.sdk.nio.stream.javax;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.java.nio.file.FileX;
import fr.java.nio.stream.InputStreamX;

public class FileInputStreamX extends FileInputStream implements InputStreamX {

	public FileInputStreamX(String name) throws FileNotFoundException {
		super(name);
	}

	public FileInputStreamX(File file) throws FileNotFoundException {
		super(file);
	}

	public FileInputStreamX(FileDescriptor fdObj) {
		super(fdObj);
	}

	public FileInputStreamX(FileX file) throws FileNotFoundException {
		super(file.toFile());
	}

}
