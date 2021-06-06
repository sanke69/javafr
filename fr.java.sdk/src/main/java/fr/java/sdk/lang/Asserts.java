package fr.java.sdk.lang;

public class Asserts {

	public static final void 	assertTrue(boolean _condition) {
		if(!_condition)
			throw new IllegalArgumentException("assertTrue:: A condition is not satisfied");
	}
	public static final void 	assertTrue(boolean _condition, String _errorMsg) {
		if(!_condition)
			throw new IllegalArgumentException(_errorMsg);
	}

	public static final void 	assertFalse(boolean _condition) {
		if(_condition)
			throw new IllegalArgumentException("assertFalse:: A condition is not satisfied");
	}
	public static final void 	assertFalse(boolean _condition, String _errorMsg) {
		if(_condition)
			throw new IllegalArgumentException(_errorMsg);
	}

}
