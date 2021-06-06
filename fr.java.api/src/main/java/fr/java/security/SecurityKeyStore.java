package fr.java.security;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public interface SecurityKeyStore {

	public void 		addKey(String _alias, PrivateKey _key, String _keyPWD, Certificate _certs);
	public void 		addKey(String _alias, PrivateKey _key, String _keyPWD, Certificate[] _certs);
	public void 		addKey(String _storePWD, String _alias, PublicKey _key, String _keyPWD);

	public void 		addCertificate(String _alias, Certificate _certs);

	public Key  		getKey(String _alias, String _keyPWD);
	public Certificate  getCertificate(String _alias);

	public void 		save(String _storePWD);

}
