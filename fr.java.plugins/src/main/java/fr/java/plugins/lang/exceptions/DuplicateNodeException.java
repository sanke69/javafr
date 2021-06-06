package fr.java.plugins.lang.exceptions;

public class DuplicateNodeException extends RuntimeException {
	private static final long serialVersionUID = -6740879767589782982L;

	public DuplicateNodeException(String message, Object... args) {
        super(String.format(message, args));
    }

    public DuplicateNodeException(String message) {
        super(message);
    }

    public DuplicateNodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
