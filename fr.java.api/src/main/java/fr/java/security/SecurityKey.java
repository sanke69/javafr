package fr.java.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import fr.java.lang.tuples.Pair;
import fr.java.security.enums.KeyAlgorithm;
import fr.java.security.enums.KeyEncryption;
import fr.java.security.enums.KeyFormat;
import fr.java.security.enums.KeyNature;
import fr.java.security.exceptions.ConversionNotSupported;
import fr.java.security.exceptions.GenerationNotSupported;

public interface SecurityKey {

	public interface Generator {
		public void					setSeed(byte[] _bytes);

		public SecurityKey.Private	generate(int _keySize, KeyAlgorithm _type, KeyFormat _format) throws GenerationNotSupported, NoSuchAlgorithmException;
		public SecurityKey.Private	generate(int _keySize, KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption, String _password) throws GenerationNotSupported, NoSuchAlgorithmException;
		public SecurityKey.Public 	generate(SecurityKey.Private _privateKey) throws GenerationNotSupported, NoSuchAlgorithmException;

		public Pair<SecurityKey.Private, SecurityKey.Public>
									generatePair(int _keySize, KeyAlgorithm _type, KeyFormat _format) throws GenerationNotSupported, NoSuchAlgorithmException;
	}
	public interface Converter {
		public SecurityKey 			convert(SecurityKey _key, KeyFormat _newFormat) throws ConversionNotSupported;
	}

	public interface Private extends SecurityKey {
		@Override public PrivateKey	getKey();
	}
	public interface Public  extends SecurityKey {
		@Override public PublicKey	getKey();
	}

	public default boolean 	isPrivate() 		{ return getNature() == KeyNature.PRIVATE; }
	public Private 			asPrivate();
	public default boolean 	isPublic() 			{ return getNature() == KeyNature.PUBLIC; }
	public Public 			asPublic();
	
	public KeyAlgorithm 	getType();
	public KeyFormat 		getFormat();
	public KeyNature 		getNature();
	public KeyEncryption 	getEncryption();

	public Key				getKey();

}
