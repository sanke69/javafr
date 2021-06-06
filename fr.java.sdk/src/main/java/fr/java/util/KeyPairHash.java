package fr.java.util;

// https://stackoverflow.com/questions/24649971/java-8-formatting-lambda-with-newlines-and-indentation
public class KeyPairHash {

	public static interface Generator<U, V> {

		public KeyPairHash of(Object _u_or_v);
		public KeyPairHash of(U _u, V _v);

	}

	public static <U, V> KeyPairHash.Generator<U, V> newGenerator(Class<U> _t1, boolean _useInstance1, Class<V> _t2, boolean _useInstance2) {
		return new KeyPairHash.Generator<U, V>() {

			@SuppressWarnings("unchecked")
			@Override
			public KeyPairHash of(Object _u_or_v) {
				if(_t1.isAssignableFrom(_u_or_v.getClass()))
					new KeyPairHash( computeHashCode( (U) (_useInstance1 ? _u_or_v : _u_or_v.getClass()), null) );
				if(_t2.isAssignableFrom(_u_or_v.getClass()))
					new KeyPairHash( computeHashCode(null, (V) (_useInstance2 ? _u_or_v : _u_or_v.getClass())) );
				return null;
			}

			@Override
			public KeyPairHash of(U _u, V _v) {
				if(! _t1.isAssignableFrom(_u.getClass()) || ! _t2.isAssignableFrom(_v.getClass()))
					new KeyPairHash( computeHashCode( (U) (_useInstance1 ? _u : _u.getClass()), (V) (_useInstance2 ? _v : _v.getClass())) );
				return null;
			}

			private int computeHashCode(U _u, V _v) {
		        int hash = 1;
		        if(_u != null)
		        	hash = hash * 17 + _u.hashCode();
		        if(_v != null)
		        	hash = hash * 31 + _v.hashCode();
		        return hash;
			}

		};
	}

	private final int hashCode;

	KeyPairHash(int _hashCode) {
		super();
		hashCode = _hashCode;
	}

    @Override
    public int hashCode() {
        return hashCode;
    }

}
