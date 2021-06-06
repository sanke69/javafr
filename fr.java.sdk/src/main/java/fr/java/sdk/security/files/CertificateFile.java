package fr.java.sdk.security.files;

import java.io.InputStream;
import java.nio.file.Path;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

import fr.java.sdk.security.AbstractCertificate;
import fr.java.sdk.security.AbstractKey;
import fr.java.security.SecurityKey;
import fr.java.security.enums.CertificateFormat;
import fr.java.security.enums.KeyAlgorithm;
import fr.java.security.enums.KeyEncryption;
import fr.java.security.enums.KeyFormat;
import fr.java.utils.LocalFiles;

public class CertificateFile extends AbstractCertificate {

	Path				path;
	Certificate[]		certificates;

	public CertificateFile(Path _path) {
		super(CertificateFormat.X509_DER);
		path     = _path;
	}
	public CertificateFile(Path _path, CertificateFormat _format) {
		super(_format);
		path     = _path;
	}

	public Path 				getPath() {
		return path;
	}

	public Certificate[]		getCertificates() {
		if(certificates != null)
			return certificates;

		switch(getFormat()) {
		case X509_DER:	return certificates = loadX509DERCertificates(getPath());
		case X509:
		case PKCS_7:
		case PKCS_12:
		case REQUEST:
		default:		throw new IllegalArgumentException();
		}
	}

	private static byte[] 	 	__getContent(String _certContent) {
		String keyContent = _certContent	.replace("-----BEGIN CERTIFICATE-----", "")
							            .replace("-----END CERTIFICATE-----", "")
							            .replaceAll("\\s", "");
		return Base64.getDecoder().decode(keyContent);
	}

	protected Certificate 	loadX509DERCertificate(Path _certificate_path) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream        is = LocalFiles.getContent(_certificate_path);
			
			return cf.generateCertificate(is);
		} catch(CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected Certificate[] 	loadX509DERCertificates(Path _certificate_path) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream        is = LocalFiles.getContent(_certificate_path);

            return cf.generateCertificates(is).toArray(new Certificate[0]);
		} catch(CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public SecurityKey.Public getAssociatedKey() {
		return new AbstractKey.Public(KeyAlgorithm.RSA, KeyFormat.PKCS_8_DER, KeyEncryption.NONE) {
			@Override
			public PublicKey getKey() {
				return certificates[0].getPublicKey();
			}
		};
	}

}
