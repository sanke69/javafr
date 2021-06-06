package fr.java.sdk.security.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import fr.java.security.enums.CertificateFormat;
import fr.java.security.enums.KeyFormat;

public class KeyStoreFile {
	Path		ksFile;
	KeyStore	ks;

	public static void main(String[] args) {
		String derKeyFile   = "/tmp/userKeyStore/.private/steve.pechberti.der";
		String derCertFile  = "/tmp/userKeyStore/.private/steve.pechberti.crt.der";
		String jksFile 		= "/tmp/userKeyStore/.private/keyStoreFile";
		
		KeyFile.Private keyFile  = KeyFile.loadRsaPrivateKey(Paths.get(derKeyFile), KeyFormat.PKCS_8_DER);
		CertificateFile certFile = new CertificateFile(Paths.get(derCertFile), 
												CertificateFormat.X509_DER);
		
		createAndInitialize(Paths.get(jksFile), "storePass", "dftKey", keyFile, "keyPass", certFile);
		load(Paths.get(jksFile), "storePass");
	}

	public static KeyStoreFile createAndInitialize(Path _storeFile, String _storePswd, String _firstAlias, KeyFile.Private _keyFile, String _keyPswd, CertificateFile _certFile) {
		try {
			KeyStoreFile 	jks   = create(_storeFile, _storePswd);
			PrivateKey      pk    = _keyFile.getKey();
			Certificate[]   certs = _certFile.getCertificates();

			jks.addKey(_firstAlias, pk, _keyPswd, certs);
			jks.save(_storePswd);

			return jks;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static KeyStoreFile createAndInitialize(Path _storeFile, String _storePswd, String _firstAlias, PrivateKey _key, String _keyPswd, Certificate _certificate) {
		try {
			KeyStoreFile jks   = create(_storeFile, _storePswd);

			jks.addKey(_firstAlias, _key, _keyPswd, _certificate);
			jks.save(_storePswd);

			return jks;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static KeyStoreFile createAndInitialize(Path _storeFile, String _storePswd, String _firstAlias, PrivateKey _key, String _keyPswd, Certificate[] _certificates) {
		try {
			KeyStoreFile jks   = create(_storeFile, _storePswd);

			jks.addKey(_firstAlias, _key, _keyPswd, _certificates);
			jks.save(_storePswd);

			return jks;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static KeyStoreFile create(Path _jksFile, String _storePWD) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");

			ks.load(null, _storePWD.toCharArray());
			ks.store(new FileOutputStream(_jksFile.toFile()), _storePWD.toCharArray());
			ks.load(new FileInputStream(_jksFile.toFile()), _storePWD.toCharArray());

			return new KeyStoreFile(_jksFile, ks);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static KeyStoreFile load(Path _jksFile, String _storePWD) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			ks.load(new FileInputStream(_jksFile.toFile()), _storePWD.toCharArray());

			return new KeyStoreFile(_jksFile, ks);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private KeyStoreFile(final Path _store, KeyStore _keyStore) {
		super();
		ksFile = _store;
		ks = _keyStore;
	}

	public void 		addKey(String _alias, PrivateKey _key, String _keyPWD, Certificate _certs) {
		try {
			ks.setKeyEntry(_alias, _key, _keyPWD.toCharArray(), new Certificate[] { _certs });
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void 		addKey(String _alias, PrivateKey _key, String _keyPWD, Certificate[] _certs) {
		try {
			ks.setKeyEntry(_alias, _key, _keyPWD.toCharArray(), _certs);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void 		addKey(String _storePWD, String _alias, PublicKey _key, String _keyPWD) {
		try {
			ks.setKeyEntry(_alias, _key, _keyPWD.toCharArray(), null);
			ks.store(new FileOutputStream(ksFile.toFile()), _storePWD.toCharArray());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void 		addCertificate(String _alias, Certificate _certs) {
		try {
			ks.setCertificateEntry(_alias, _certs);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Key  		getKey(String _alias, String _keyPWD) {
		try {
			return ks.getKey(_alias, _keyPWD.toCharArray());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Certificate  getCertificate(String _alias) {
		try {
			return ks.getCertificate(_alias);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void 		save(String _storePWD) {
		try {
			ks.store(new FileOutputStream(ksFile.toFile()), _storePWD.toCharArray());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
