package fr.java.sdk.security.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import fr.java.sdk.security.AbstractKey;
import fr.java.security.SecurityKey;
import fr.java.security.enums.KeyAlgorithm;
import fr.java.security.enums.KeyEncryption;
import fr.java.security.enums.KeyFormat;
import fr.java.security.enums.KeyNature;
import fr.java.utils.LocalFiles;

public abstract class KeyFile extends AbstractKey {

	// Only for key in PKCS#8 DER
	public static KeyFile.Private 	loadRsaPrivateKey(Path _key_path, KeyFormat _format) {
		return new Private(null, KeyAlgorithm.RSA, _format, null, __loadRsaPrivateKey(_key_path, _format));
	}
	// Only for key in PKCS#8 DER
	public static KeyFile.Public 	loadRsaPublicKey(Path _key_path, KeyFormat _format) {
		PublicKey publicKey = null;

		try {
			byte[] key = null;

			switch(_format) {
			case PKCS_8_DER:	key = LocalFiles.getContentAsByteArrays(_key_path); break;
			case PKCS_8: 		key = __getKeyBytesFromPublicPEM( LocalFiles.getContentAsString(_key_path) ); break;
			default:			break;
			}

	        publicKey = KeyFactory.getInstance("RSA").generatePublic( new X509EncodedKeySpec(key) );
		} catch(Exception e) {
			e.printStackTrace();
		}

		return new Public(null, KeyAlgorithm.RSA, _format, null, publicKey);
	}

	public static class Private extends KeyFile implements SecurityKey.Private {
		PrivateKey  privateKey;

		public Private(Path _path, KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption) {
			super(_path, _type, _format, KeyNature.PRIVATE, _encryption);
			privateKey = null;
		}
		public Private(Path _path, KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption, PrivateKey _privateKey) {
			super(_path, _type, _format, KeyNature.PRIVATE, _encryption);
			privateKey = _privateKey;
		}

		@Override public PrivateKey				getKey() {
			if(privateKey == null)
				privateKey = __loadRsaPrivateKey(getPath(), getFormat());
			return privateKey;
		}

		@Override public KeyFile.Private 	asPrivate() { return this; }
		@Override public KeyFile.Public 	asPublic() { return null; }

	}
	public static class Public  extends KeyFile implements SecurityKey.Public {
		PublicKey  publicKey;

		public Public(Path _path, KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption) {
			super(_path, _type, _format, KeyNature.PUBLIC, _encryption);
			publicKey = null;
		}
		public Public(Path _path, KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption, PublicKey _publicKey) {
			super(_path, _type, _format, KeyNature.PUBLIC, _encryption);
			publicKey = _publicKey;
		}

		@Override public PublicKey				getKey() {
			if(publicKey == null)
				publicKey = __loadRsaPublicKey(getPath(), getFormat());
			return publicKey;
		}

		@Override public KeyFile.Private 	asPrivate() { return null; }
		@Override public KeyFile.Public 	asPublic() { return this; }

	}

	Path path;

	public KeyFile(Path _path, KeyAlgorithm _type, KeyFormat _format, KeyNature _nature, KeyEncryption _encryption) {
		super(_type, _format, _nature, _encryption);
		path = _path;
	}

	public Path getPath() {
		return path;
	}

	private static PrivateKey 	__loadRsaPrivateKey(Path _key_path, KeyFormat _format) {
		PrivateKey privateKey = null;
		try {
			byte[] key = null;

			switch(_format) {
			case PKCS_8_DER:	key 		= LocalFiles.getContentAsByteArrays(_key_path);
								privateKey 	= KeyFactory.getInstance("RSA").generatePrivate( new PKCS8EncodedKeySpec(key) );
								break;
			case PKCS_8: 		key 		= __getKeyBytesFromPrivatePEM( LocalFiles.getContentAsString(_key_path) );
								privateKey 	= KeyFactory.getInstance("RSA").generatePrivate( new PKCS8EncodedKeySpec(key) );
								break;
			default:			break;
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		return privateKey;
	}
	private static PublicKey 	__loadRsaPublicKey(Path _key_path, KeyFormat _format) {
		PublicKey publicKey = null;

		try {
			byte[] key = null;

			switch(_format) {
			case PKCS_8_DER:	key = LocalFiles.getContentAsByteArrays(_key_path); break;
			case PKCS_8: 		key = __getKeyBytesFromPublicPEM( LocalFiles.getContentAsString(_key_path) ); break;
			default:			break;
			}

	        publicKey = KeyFactory.getInstance("RSA").generatePublic( new X509EncodedKeySpec(key) );
		} catch(Exception e) {
			e.printStackTrace();
		}

		return publicKey;
	}

	private static byte[] 	 	__getKeyBytesFromPrivatePEM(String _keyContent) {
		String keyContent = _keyContent	.replace("-----BEGIN RSA PRIVATE KEY-----", "")
							            .replace("-----END RSA PRIVATE KEY-----", "")
							            .replaceAll("\\s", "");
		return Base64.getDecoder().decode(keyContent);
	}
	private static byte[] 	 	__getKeyBytesFromPublicPEM(String _keyContent) {
		String keyContent = _keyContent	.replace("-----BEGIN PUBLIC KEY-----", "")
							            .replace("-----END PUBLIC KEY-----", "")
							            .replaceAll("\\s", "");
		return Base64.getDecoder().decode(keyContent);
	}

	protected static KeyFile savePrivateKey(PrivateKey _key, Path _key_path) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(_key_path.toFile());
			fos.write(_key.getEncoded());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override public abstract KeyFile.Private 	asPrivate();
	@Override public abstract KeyFile.Public 	asPublic();

}
