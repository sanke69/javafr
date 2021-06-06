package fr.java.graph.impl.exceptions;

import fr.java.graph.GTNode;
import fr.java.lang.properties.ID;

public class AlreadyExistingEdgeException extends RuntimeException {
	private static final long serialVersionUID = 1772648211742502283L;

	public AlreadyExistingEdgeException() {
		super();
	}
	public AlreadyExistingEdgeException(String _msg) {
		super(_msg);
	}
	public AlreadyExistingEdgeException(ID _from, ID _to) {
		super("Alreading existing edge between " + _from + " and " + _to);
	}
	public AlreadyExistingEdgeException(GTNode _from, GTNode _to) {
		super("Alreading existing edge between " + _from.getValue().toString() + " and " + _to.getValue().toString());
	}

}
