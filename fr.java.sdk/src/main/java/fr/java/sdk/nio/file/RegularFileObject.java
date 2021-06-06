package fr.java.sdk.nio.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.java.nio.file.FileX;
import fr.java.nio.file.FileX.Regular;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;
import fr.java.sdk.nio.stream.javax.FileInputStreamX;
import fr.java.sdk.nio.stream.javax.FileOutputStreamX;

public class RegularFileObject extends FileObject implements FileX.Regular {

	public RegularFileObject(Path _path) /*throws IOException*/ {
		super(_path);
	}
	public RegularFileObject(FileX _file) {
		super(_file);
	}

	@Override
	public long 				getSize() {
		if(toPath() != null)
			try {
				return Files.size(toPath());
			} catch(IOException e) {
				e.printStackTrace();
			}
		return -1L;
	}

	@Override
	public InputStream 			getInputStream() throws FileNotFoundException {
		if(toPath() != null)
			return new FileInputStream(toPath().toFile());
		return null;
	}
	@Override
	public InputStreamX 		getInputStreamX() throws FileNotFoundException {
		if(toPath() != null)
			return new FileInputStreamX(toPath().toFile());
		return null;
	}

	@Override
	public OutputStream 		getOutputStream() throws FileNotFoundException {
		if(toPath() != null)
			return new FileOutputStream(toPath().toFile());
		return null;
	}
	@Override
	public OutputStreamX 		getOutputStreamX() throws FileNotFoundException {
		if(toPath() != null)
			return new FileOutputStreamX(toPath().toFile());
		return null;
	}

	@Override
	public String toString() {
		return"REG: " + toPath().toString();
	}

}
