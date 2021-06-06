package fr.java.sdk.security.files.pem;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import fr.java.lang.exceptions.IllegalConversionException;
import fr.java.sdk.security.AbstractCertificate;
import fr.java.sdk.security.AbstractKey;
import fr.java.security.SecurityCertificate;
import fr.java.security.SecurityKey;
import fr.java.security.SecurityKey.Public;
import fr.java.security.enums.CertificateFormat;
import fr.java.security.enums.KeyAlgorithm;
import fr.java.security.enums.KeyEncryption;
import fr.java.security.enums.KeyFormat;

public class PemObject {
	private byte[]  content;
	private PemType type;

	public PemObject(byte[]  _content, PemType _type) {
		super();
		content = _content;
		type    = _type;
	}

	public PemType 				getType()   { return type; }
	public byte[] 				getContent() { return content; }

	public SecurityKey.Private 	asPrivateKey() {
		if(type != PemType.RSA_PRIVATE_KEY)
			throw new IllegalConversionException();

		PrivateKey key;
		try {
			key = KeyFactory.getInstance("RSA").generatePrivate( new PKCS8EncodedKeySpec(content) );
		} catch(InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new IllegalConversionException();
		}
		return new AbstractKey.Private(KeyAlgorithm.RSA, KeyFormat.PKCS_8, KeyEncryption.NONE) {
			@Override public PrivateKey getKey() { return key; }
		};
	}
	public SecurityKey.Public 	asPublicKey() {
		if(type != PemType.RSA_PUBLIC_KEY)
			throw new IllegalConversionException();

		PublicKey key;
		try {
	        key = KeyFactory.getInstance("RSA").generatePublic( new X509EncodedKeySpec(content) );
		} catch(InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw new IllegalConversionException();
		}
		return new AbstractKey.Public(KeyAlgorithm.RSA, KeyFormat.PKCS_8, KeyEncryption.NONE) {
			@Override public PublicKey getKey() { return key; }
		};
	}
	public SecurityCertificate 	asCertificate() {
		if(type != PemType.CERTIFICATE)
			throw new IllegalConversionException();

		Certificate certificate;
		try {
			certificate = CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(content));
			if(!(certificate instanceof X509Certificate)) {
				throw new IllegalArgumentException("Certificate file does not contain an X509 certificate");
			}
		} catch(CertificateException e) {
			throw new IllegalConversionException();
		}
		return new AbstractCertificate(CertificateFormat.X509) {

			@Override
			public Certificate[] getCertificates() {
				return new Certificate[] { certificate };
			}

			@Override
			public Public getAssociatedKey() {
				return new AbstractKey.Public(KeyAlgorithm.RSA, KeyFormat.PKCS_8, KeyEncryption.NONE) {
					@Override public PublicKey getKey() { return  getCertificates()[0].getPublicKey(); }
				};
			}
			
		};
	}


}
