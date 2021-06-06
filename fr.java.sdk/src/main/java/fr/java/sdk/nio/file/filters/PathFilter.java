package fr.java.sdk.nio.file.filters;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Predicate;

@FunctionalInterface
public interface PathFilter extends java.io.FileFilter, Predicate<Path> {

	@Override
	public default boolean accept(File _file) {
		return test(_file.toPath());
	}
	public default boolean accept(Path _path) {
		return accept(_path.toFile());
	}


}
