package fr.java.sdk.security.files.pem;

import java.nio.file.Path;
import java.util.Base64;

import fr.java.utils.LocalFiles;

public class PemReader {
	public static final String RSA_PRIVATE_KEY_HEADER  = "-----BEGIN RSA PRIVATE KEY-----";
	public static final String RSA_PRIVATE_KEY_FOOTER  = "-----END RSA PRIVATE KEY-----";

	public static final String PUBLIC_KEY_HEADER       = "-----BEGIN PUBLIC KEY-----";
	public static final String PUBLIC_KEY_FOOTER       = "-----END PUBLIC KEY-----";

	public static final String X509_CERTIFICATE_HEADER = "-----BEGIN CERTIFICATE-----";
	public static final String X509_CERTIFICATE_FOOTER = "-----END CERTIFICATE-----";

	public static PemObject readPemFile(Path _file) {
		byte[] content = LocalFiles.getContentAsByteArrays(_file);
		String ascii   = new String(content);

		if(ascii.contains(RSA_PRIVATE_KEY_HEADER)) {
			content = __getRsaPrivateKeyBytes(ascii);
			return new PemObject(content, PemType.RSA_PRIVATE_KEY);
		} else if(ascii.contains(PUBLIC_KEY_HEADER)) {
			content = __getRsaPublicKeyBytes(ascii);
			return new PemObject(content, PemType.RSA_PUBLIC_KEY);
		} else if(ascii.contains(X509_CERTIFICATE_HEADER)) {
			content = __getCertificateBytes(ascii);
			return new PemObject(content, PemType.CERTIFICATE);
		} else
			throw new IllegalArgumentException();
	}

	private static byte[] 	__getRsaPrivateKeyBytes(String _keyContent) {
		String keyContent = _keyContent	.replace(RSA_PRIVATE_KEY_HEADER, "")
							            .replace(RSA_PRIVATE_KEY_FOOTER, "")
							            .replaceAll("\\s", "");
		return Base64.getDecoder().decode(keyContent);
	}
	private static byte[] 	__getRsaPublicKeyBytes(String _keyContent) {
		String keyContent = _keyContent	.replace(PUBLIC_KEY_HEADER, "")
							            .replace(PUBLIC_KEY_FOOTER, "")
							            .replaceAll("\\s", "");
		return Base64.getDecoder().decode(keyContent);
	}

	private static byte[] 	__getCertificateBytes(String _certContent) {
		String keyContent = _certContent.replace(X509_CERTIFICATE_HEADER, "")
							            .replace(X509_CERTIFICATE_FOOTER, "")
							            .replaceAll("\\s", "");
		return Base64.getDecoder().decode(keyContent);
	}

}
