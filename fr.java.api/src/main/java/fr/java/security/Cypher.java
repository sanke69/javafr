package fr.java.security;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public interface Cypher {

	public Cipher getCipher();

	public default byte[] encode(byte[] _data, Key _public) {
		try {
			getCipher().init(Cipher.ENCRYPT_MODE, _public);
			return getCipher().doFinal(_data);
		} catch(IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	public default byte[] decode(byte[] _data, Key _private) {
		try {
			getCipher().init(Cipher.DECRYPT_MODE, _private);
			return getCipher().doFinal(_data);
		} catch(IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

}
