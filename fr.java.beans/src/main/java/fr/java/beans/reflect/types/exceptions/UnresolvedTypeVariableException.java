package fr.java.beans.reflect.types.exceptions;

import java.lang.reflect.TypeVariable;

public class UnresolvedTypeVariableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final TypeVariable<?> tv;
	
	public UnresolvedTypeVariableException(TypeVariable<?> tv) {
		super("An exact type is requested, but the type contains a type variable that cannot be resolved.\n" +
				"   Variable: " + tv.getName() + " from " + tv.getGenericDeclaration() + "\n" +
				"   Hint: This is usually caused by trying to get an exact type when a generic method who's type parameters are not given is involved.");
		this.tv = tv;
	}
	
	public TypeVariable<?> getTypeVariable() {
		return tv;
	}
}
