package fr.java.lang.exceptions;

import fr.java.patterns.valueable.ValueObject;

public class AlreadyExistingNodeException extends RuntimeException {
	private static final long serialVersionUID = 9216611355247614301L;

	public AlreadyExistingNodeException() {
		super();
	}
	public AlreadyExistingNodeException(ValueObject _nodeValue) {
		super(_nodeValue.toString());
	}

}
