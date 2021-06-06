package fr.java.plugins.lang.exceptions;

public class CircularDependencyException extends RuntimeException {
	private static final long serialVersionUID = 2752065536564958181L;

	public CircularDependencyException(String message, Object... args) {
        super(String.format(message, args));
    }

    public CircularDependencyException(String message) {
        super(message);
    }

    public CircularDependencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
