package fr.java.sdk.security.encryption.impl;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import fr.java.security.encryption.Encryptor;

public class EncryptorImpl implements Encryptor {

    private static final String ALGORITHM = "AES";
    private static final String FULL_ALGORITHM = "AES/CBC/PKCS5Padding";

    private final static byte [] DEFAULT_KEY_BYTES = {
        (byte) 0x23,(byte) 0x65,(byte) 0x87,(byte) 0x22,
        (byte) 0x59,(byte) 0x78,(byte) 0x54,(byte) 0x43,
        (byte) 0x64,(byte) 0x05,(byte) 0x6A,(byte) 0xBD,
        (byte) 0x34,(byte) 0xA2,(byte) 0x34,(byte) 0x57,
    };

    private final static byte [] DEFAULT_IV_BYTES = {
        (byte) 0x51,(byte) 0x65,(byte) 0x22,(byte) 0x23,
        (byte) 0x64,(byte) 0x05,(byte) 0x6A,(byte) 0xBE,
        (byte) 0x51,(byte) 0x65,(byte) 0x22,(byte) 0x23,
        (byte) 0x64,(byte) 0x05,(byte) 0x6A,(byte) 0xBE,
    };

    private Key key;
    private IvParameterSpec iv;

    public EncryptorImpl(boolean defaultKey) {
        if (defaultKey) {
            key = new SecretKeySpec(DEFAULT_KEY_BYTES, ALGORITHM);
            iv = new IvParameterSpec(DEFAULT_IV_BYTES);
        } else {
            try {
                key = KeyGenerator.getInstance(ALGORITHM).generateKey();
                iv = new IvParameterSpec(DEFAULT_IV_BYTES);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public byte[] decrypt(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(FULL_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            return cipher.doFinal(bytes);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encrypt(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(FULL_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return cipher.doFinal(bytes);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}