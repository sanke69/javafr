package fr.java.sdk.security.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import fr.java.security.SecurityKey;
import fr.java.utils.Streams;
import fr.java.utils.Bytes.HexaEncoder;

public class Signatures {

	static public class MD5 {

		public static byte[] of(InputStream _is) throws IOException {
			try {
				MessageDigest md = null;
				md = MessageDigest.getInstance("MD5");
				md.update(Streams.getBytes(_is));

				return md.digest();
			} catch(NoSuchAlgorithmException e) {
				throw new IOException();
			}
		}
		public static String asHexaString(InputStream _is) throws IOException {
			return new String(HexaEncoder.encodeHex(of(_is)));
		}

	}

	static public class SHA1 {

		public static byte[] of(InputStream _is) throws IOException {
			try {
				MessageDigest md = null;
				md = MessageDigest.getInstance("SHA");
				md.update(Streams.getBytes(_is));

				return md.digest();
			} catch(NoSuchAlgorithmException e) {
				throw new IOException();
			}
		}
		public static String asHexaString(InputStream _is) throws IOException {
			return new String(HexaEncoder.encodeHex(of(_is)));
		}

	}

	static public class SHA1RSA {

		public static byte[] 	getSignature(InputStream _is, SecurityKey _key) throws IOException, SignatureException, InvalidKeyException {
			if(_key == null || !(_key instanceof SecurityKey.Private))
				throw new NullPointerException("Key can't be null");

			return getSignature(_is, _key.asPrivate());
		}
		public static byte[] 	getSignature(InputStream _is, SecurityKey.Private _key) throws IOException, SignatureException, InvalidKeyException {
			if(_key == null)
				throw new NullPointerException("Key can't be null");

			try {
				Signature rsaSigner = Signature.getInstance("SHA1withRSA");

				rsaSigner.initSign(_key.getKey());
				rsaSigner.update(Streams.getBytes(_is));

				return rsaSigner.sign();
			} catch(NoSuchAlgorithmException e) {
			}

			return null;
		}

		public static boolean 	checkSignature(InputStream _is, byte[] _signature, SecurityKey _key) throws IOException, SignatureException, InvalidKeyException {
			if(_key == null || !(_key instanceof SecurityKey.Public))
				throw new NullPointerException("Key can't be null");

			return checkSignature(_is, _signature, _key.asPublic());
		}
		public static boolean 	checkSignature(InputStream _is, byte[] _signature, SecurityKey.Public _key) throws IOException, SignatureException, InvalidKeyException {
			if(_key == null)
				throw new NullPointerException("Key can't be null");

			try {
				Signature rsaSigner = Signature.getInstance("SHA1withRSA");

				rsaSigner.initVerify(_key.getKey());
				rsaSigner.update(Streams.getBytes(_is));

				return rsaSigner.verify(_signature);
			} catch(NoSuchAlgorithmException e) {
			}

			return false;
		}
	
	}

}
