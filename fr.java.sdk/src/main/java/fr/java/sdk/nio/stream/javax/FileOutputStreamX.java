package fr.java.sdk.nio.stream.javax;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import fr.java.nio.file.FileX;
import fr.java.nio.stream.OutputStreamX;

public class FileOutputStreamX implements OutputStreamX {
	final FileOutputStream delegated;

	public FileOutputStreamX(String _name) throws FileNotFoundException {
		super();
		delegated = new FileOutputStream(_name);
	}
	public FileOutputStreamX(String name, boolean append) throws FileNotFoundException {
		super();
		delegated = new FileOutputStream(name, append);
	}

	public FileOutputStreamX(File file) throws FileNotFoundException {
		super();
		delegated = new FileOutputStream(file, false);
	}
	public FileOutputStreamX(File file, boolean append) throws FileNotFoundException {
		super();
		delegated = new FileOutputStream(file, append);
	}

	public FileOutputStreamX(FileDescriptor fdObj) {
		super();
		delegated = new FileOutputStream(fdObj);
	}

	public FileOutputStreamX(FileX file) throws FileNotFoundException {
		super();
		delegated = new FileOutputStream(file.toFile(), false);
	}
	public FileOutputStreamX(FileX file, boolean append) throws FileNotFoundException {
		super();
		delegated = new FileOutputStream(file.toFile(), append);
	}

	public int hashCode() {
		return delegated.hashCode();
	}
	public boolean equals(Object obj) {
		return delegated.equals(obj);
	}
	public void flush() throws IOException {
		delegated.flush();
	}
	public String toString() {
		return delegated.toString();
	}
	public void write(int b) throws IOException {
		delegated.write(b);
	}
	public void write(byte[] b) throws IOException {
		delegated.write(b);
	}
	public void write(byte[] b, int off, int len) throws IOException {
		delegated.write(b, off, len);
	}
	public void close() throws IOException {
		delegated.close();
	}
	public final FileDescriptor getFD() throws IOException {
		return delegated.getFD();
	}
	public FileChannel getChannel() {
		return delegated.getChannel();
	}

}
