package fr.java.plugins.lang.exceptions;

public class MissingDependencyException extends RuntimeException {
	private static final long serialVersionUID = -5486510878428091438L;

	public MissingDependencyException(String message, Object... args) {
        super(String.format(message, args));
    }

}
