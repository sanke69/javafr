package fr.java.sdk.lang;

public class SoftPtr<C> {
	public static final <U> SoftPtr<U> empty()    { return new SoftPtr<U>(); }
	public static final <U> SoftPtr<U> of(U _ref) { return new SoftPtr<U>(_ref); }

	C ref;

	public SoftPtr() { }
	public SoftPtr(C _c) {
		ref = _c;
	}

	public C get() {
		return ref;
	}
	public C getOrElse(C _default) {
		return ref != null ? ref : _default;
	}
	public C set(C _c) {
		ref = _c;
		return ref;
	}

	public boolean isPresent() {
		return ref != null;
	}
	public boolean isEmpty() {
		return ref == null;
	}

};
