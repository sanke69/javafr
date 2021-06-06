package fr.java.sdk.security.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import fr.java.sdk.security.AbstractCypher;
import fr.java.security.Cypher;

public class Cyphers {

	public static class RSA extends AbstractCypher implements Cypher {
		Cipher cipher;

		public RSA(int _keySize) {
			super(_keySize);

			try {
				cipher = Cipher.getInstance("RSA");
			} catch(NoSuchAlgorithmException | NoSuchPaddingException e) {}
		}

		@Override
		public Cipher getCipher() {
			return cipher;
		}
		
	}

}
