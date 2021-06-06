package fr.java.sdk.security.rsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.concurrent.ThreadLocalRandom;

import fr.java.lang.tuples.Pair;
import fr.java.sdk.lang.Tuples;
import fr.java.sdk.security.AbstractKey;
import fr.java.security.SecurityCertificate;
import fr.java.security.SecurityKey;
import fr.java.security.SecurityKey.Private;
import fr.java.security.SecurityKey.Public;
import fr.java.security.enums.KeyAlgorithm;
import fr.java.security.enums.KeyEncryption;
import fr.java.security.enums.KeyFormat;
import fr.java.security.exceptions.GenerationNotSupported;

public class Rsa implements SecurityKey.Generator {
	int 	keySize;
	byte[] 	keySeed;

	@Override
	public void 						setSeed(byte[] _bytes) {
		
	}

	@Override
	public Private 						generate(int _keySize, KeyAlgorithm _type, KeyFormat _format) throws GenerationNotSupported, NoSuchAlgorithmException {
		if(_type != KeyAlgorithm.RSA || _format != KeyFormat.PKCS_8_DER)
			throw new GenerationNotSupported();

		int    size = _keySize > 0 ? _keySize : 256;
		byte[] seed = keySeed != null ? keySeed : new byte[] { 'S', 'P', '-', 'W', 'E', 'B' };

//		KeyGenerator kGen = KeyGenerator.getInstance("RSA");
//		kGen.init(size, new SecureRandom(seed));

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(size, new SecureRandom(seed));

		KeyPair kp = keyGen.generateKeyPair();

		return new AbstractKey.Private(KeyAlgorithm.RSA, KeyFormat.PKCS_8_DER, KeyEncryption.NONE) {
						@Override public PrivateKey getKey() { return kp.getPrivate(); }  };
	}
	@Override
	public Private 						generate(int _keySize, KeyAlgorithm _type, KeyFormat _format, KeyEncryption _encryption, String _password) throws GenerationNotSupported {
		throw new GenerationNotSupported();
	}
	@Override
	public Public 						generate(Private _privateKey) throws GenerationNotSupported, NoSuchAlgorithmException {
		PrivateKey       myPrivateKey = _privateKey.getKey();
	    RSAPrivateCrtKey privk        = (RSAPrivateCrtKey) myPrivateKey;

//	    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privk.getModulus(), privk.getPrivateExponent());
	    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privk.getModulus(), privk.getPublicExponent());

	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    
	    try {
	        PublicKey myPublicKey = keyFactory.generatePublic(publicKeySpec);

			return new AbstractKey.Public(KeyAlgorithm.RSA, KeyFormat.PKCS_8_DER, KeyEncryption.NONE) {
							@Override public PublicKey getKey() { return myPublicKey; }  };
	    } catch(InvalidKeySpecException _e) {
	    	throw new GenerationNotSupported();
	    }
	}
	@Override
	public Pair<Private, Public> 		generatePair(int _keySize, KeyAlgorithm _type, KeyFormat _format) throws GenerationNotSupported, NoSuchAlgorithmException {
		if(_type != KeyAlgorithm.RSA || _format != KeyFormat.PKCS_8_DER)
			throw new GenerationNotSupported();

		byte[] seed = keySeed != null ? keySeed : new byte[] { 'S', 'P', '-', 'W', 'E', 'B' };

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(_keySize, new SecureRandom(seed));

		KeyPair kp = keyGen.generateKeyPair();

		return Tuples.of(
				new AbstractKey.Private(KeyAlgorithm.RSA, KeyFormat.PKCS_8_DER, KeyEncryption.NONE) {
					@Override public PrivateKey getKey() { return kp.getPrivate(); }  }, 
				new AbstractKey.Public(KeyAlgorithm.RSA, KeyFormat.PKCS_8_DER, KeyEncryption.NONE) { 
					@Override public PublicKey getKey() { return kp.getPublic(); } }
				);
	}

	public static boolean 				isValidPair(SecurityKey _private, SecurityKey _public) {
		if(_private.getType().compareTo(_public.getType()) != 0)
			return false;

		boolean keyPairMatches = false;

		try {
			// create a challenge
			byte[] challenge = new byte[10000];
			ThreadLocalRandom.current().nextBytes(challenge);
	
			// sign using the private key
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initSign(_private.asPrivate().getKey());
			sig.update(challenge);
			byte[] signature = sig.sign();
	
			// verify signature using the public key
			sig.initVerify(_public.asPublic().getKey());
			sig.update(challenge);
	
			keyPairMatches = sig.verify(signature);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return keyPairMatches;
	}

	public static boolean 				match(SecurityCertificate _certificate, SecurityKey _key) {
		return false;
	}

}
