package fr.java.user.session.exceptions;

public class InvalidSessionParameter extends Exception {
	private static final long serialVersionUID = 3130060035911637124L;

	public InvalidSessionParameter() {
		super();
	}
	public InvalidSessionParameter(String _message) {
		super(_message);
	}
	public InvalidSessionParameter(String _message, Throwable _throwable) {
		super(_message, _throwable);
	}

}
