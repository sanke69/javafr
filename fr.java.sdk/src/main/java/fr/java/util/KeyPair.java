package fr.java.util;

public class KeyPair {

	public static interface Generator<U, V> {

		public KeyPair of(Object _u_or_v);
		public KeyPair of(U _u, V _v);

	}

	public static <U, V> KeyPair.Generator<U, V> newGenerator(Class<U> _t1, boolean _useInstance1, Class<V> _t2, boolean _useInstance2) {
		return new KeyPair.Generator<U, V>() {

			@SuppressWarnings("unchecked")
			@Override
			public KeyPair of(Object _u_or_v) {
				if(_t1.isAssignableFrom(_u_or_v.getClass()))
					new KeyPair( computeHashCode( (U) (_useInstance1 ? _u_or_v : _u_or_v.getClass()), null) );
				if(_t2.isAssignableFrom(_u_or_v.getClass()))
					new KeyPair( computeHashCode(null, (V) (_useInstance2 ? _u_or_v : _u_or_v.getClass())) );
				return null;
			}

			@Override
			public KeyPair of(U _u, V _v) {
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

	KeyPair(int _hashCode) {
		super();
		hashCode = _hashCode;
	}

    @Override
    public int hashCode() {
        return hashCode;
    }

}
