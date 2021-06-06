package fr.java.sdk.security.encryption;

import fr.java.security.encryption.Encryptor;

public final class GuardedString {

	public static GuardedString of(String text) {
		return new GuardedString(text.toCharArray());
	}

    public interface Accessor {
        public void access(char [] clearChars);
    }
    
    static Encryptor _encryptor;
    
    private boolean _readOnly;
    private boolean _disposed;
    private byte [] _encryptedBytes;
    private String  _base64SHA1Hash;

    public GuardedString() {
        this(new char[0]);
    }
    public GuardedString(char [] clearChars) {
        encryptChars(clearChars);
    }

    public void access(Accessor accessor) {
        checkNotDisposed();
        char [] clearChars = null; 
        try {
            clearChars = decryptChars();
            accessor.access(clearChars);
        } finally {
            SecurityUtil.clear(clearChars);
        }
    }

    public void appendChar(char c) {
        checkNotDisposed();
        checkWriteable();
        char [] clearChars = null;
        char [] clearChars2 = null;
        try {
            clearChars = decryptChars();
            clearChars2 = new char[clearChars.length+1];
            System.arraycopy(clearChars, 0, clearChars2, 0, clearChars.length);
            clearChars2[clearChars2.length-1] = c;
            encryptChars(clearChars2);
        }
        finally {
            SecurityUtil.clear(clearChars);
            SecurityUtil.clear(clearChars2);
        }
    }

    public void dispose() {
        SecurityUtil.clear(_encryptedBytes);
        _disposed = true;
    }

    public boolean isReadOnly() {
        checkNotDisposed();
        return _readOnly;
    }
    public void makeReadOnly() {
        checkNotDisposed();
        _readOnly = true;
    }

    public GuardedString copy() {
        checkNotDisposed();
        byte [] encryptedBytes2 = new byte[_encryptedBytes.length];
        System.arraycopy(_encryptedBytes, 0, encryptedBytes2, 0, _encryptedBytes.length);
        GuardedString rv = new GuardedString();
        rv._encryptedBytes = encryptedBytes2;
        return rv;
    }

    public boolean verifyBase64SHA1Hash(String hash) {
        checkNotDisposed();
        return _base64SHA1Hash.equals(hash);
    }
        
    private void checkWriteable() {
        if (_readOnly) {
            throw new IllegalStateException("String is read-only");
        }
    }

    private void checkNotDisposed() {
        if (_disposed) {
            throw new IllegalStateException("String is disposed");
        }
    }
    
    private char [] decryptChars() {
        byte [] clearBytes = null;
        try {
            clearBytes = decryptBytes();
            return SecurityUtil.bytesToChars(clearBytes);
        }
        finally {
            SecurityUtil.clear(clearBytes);
        }
    }
    
    private void encryptChars(char [] chars) {
        byte [] clearBytes = null;
        try {
            clearBytes = SecurityUtil.charsToBytes(chars);
            encryptBytes(clearBytes);
        }
        finally {
            SecurityUtil.clear(clearBytes);
        }
    }
    
    private static synchronized Encryptor getEncryptor() {
        if (_encryptor == null) {
            _encryptor = EncryptorFactory.getInstance().newRandomEncryptor();
        }
        return _encryptor;
    }
    
    static synchronized void setEncryptor(Encryptor encryptor) {
        _encryptor = encryptor;
    }
    
    private byte [] decryptBytes() {
        Encryptor encryptor = getEncryptor();
        return encryptor.decrypt(_encryptedBytes);
    }
    
    private void encryptBytes(byte [] bytes) {
        Encryptor encryptor = getEncryptor();
        byte [] newBytes = encryptor.encrypt(bytes);
        SecurityUtil.clear(_encryptedBytes);
        _encryptedBytes = newBytes;
        _base64SHA1Hash = SecurityUtil.computeBase64SHA1Hash(bytes);
    }

    @Override
    public boolean equals(Object o) {
        if ( o instanceof GuardedString ) {
            GuardedString other = (GuardedString) o;
            return _base64SHA1Hash.equals(other._base64SHA1Hash);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return _base64SHA1Hash.hashCode();
    }
  
}