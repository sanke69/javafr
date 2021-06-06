package fr.java.sdk.security;

import fr.java.security.SecurityKey;
import fr.java.security.enums.KeyAlgorithm;
import fr.java.security.enums.KeyEncryption;
import fr.java.security.enums.KeyFormat;
import fr.java.security.enums.KeyNature;

public abstract class AbstractKey implements SecurityKey {


	public static abstract class Private extends AbstractKey implements SecurityKey.Private {

		public Private(KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption) {
			super(_type, _format, KeyNature.PRIVATE, _encryption);
		}

		@Override public AbstractKey.Private 	asPrivate() { return this; }
		@Override public AbstractKey.Public 	asPublic() { return null; }

	}
	public static abstract class Public  extends AbstractKey implements SecurityKey.Public {

		public Public(KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption) {
			super(_type, _format, KeyNature.PRIVATE, _encryption);
		}

		@Override public AbstractKey.Private 	asPrivate() { return null; }
		@Override public AbstractKey.Public 	asPublic() { return this; }

	}

	KeyAlgorithm    type;
	KeyFormat   	format;
	KeyNature   	nature;
	KeyEncryption 	encryption;

	protected AbstractKey(KeyAlgorithm _type, KeyFormat _format, KeyNature _nature, KeyEncryption _encryption) {
		super();
		type       = _type;
		format     = _format;
		nature     = _nature;
		encryption = _encryption;
	}


	protected final void 		setType(KeyAlgorithm _algorithm) {
		type = _algorithm;
	}
	@Override
	public final KeyAlgorithm 	getType() {
		return type;
	}
	protected final void 		setFormat(KeyFormat _format) {
		format = _format;
	}
	@Override
	public final KeyFormat 		getFormat() {
		return format;
	}
	protected final void 		setNature(KeyNature _nature) {
		nature = _nature;
	}
	@Override
	public final KeyNature 		getNature() {
		return nature;
	}
	protected final void 		setEncryption(KeyEncryption _encryption) {
		encryption = _encryption;
	}
	@Override
	public final KeyEncryption 	getEncryption() {
		return encryption;
	}

}
