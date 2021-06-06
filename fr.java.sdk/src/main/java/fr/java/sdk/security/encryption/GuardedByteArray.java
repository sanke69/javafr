package fr.java.sdk.security.encryption;

import fr.java.security.encryption.Encryptor;

public final class GuardedByteArray {
    
    /**
     * Callback interface for those times that it is necessary to access the
     * clear text of the guarded bytes.
     */
    public interface Accessor {
        /**
         * This method will be called with the clear text of the bytes.
         * After the call the clearBytes array will be automatically zeroed
         * out, thus keeping the window of potential exposure to a bare-minimum.
         * @param clearBytes
         */
        public void access(byte[] clearBytes);
    }
    
    private static Encryptor _encryptor;
    
    private boolean _readOnly;
    private boolean _disposed;
    private byte [] _encryptedBytes;
    private String _base64SHA1Hash;
    
    /**
     * Creates an empty secure byte array.
     */
    public GuardedByteArray() {
        this(new byte[0]);
    }
    
    /**
     * Initializes the GuardedByteArray from the given clear text bytes.
     * Caller is responsible for zeroing out the array of bytes
     * after the call.
     * @param clearBytes The clear-text bytes
     */
    public GuardedByteArray(byte[] clearBytes) {
        encryptBytes(clearBytes);
    }
    
    /**
     * Provides access to the clear-text value of the byte array in a controlled fashion.
     * The clear-text bytes will only be available for the duration of the call
     * and automatically zeroed out following the call. 
     * 
     * <p>
     * <b>NOTE:</b> Callers are encouraged to use {@link #verifyBase64SHA1Hash(String)}
     * where possible if the intended use is merely to verify the contents of
     * the byte array match an expected hash value.
     * @param accessor Accessor callback.
     * @throws IllegalStateException If the byte array has been disposed
     */
    public void access(Accessor accessor) {
        checkNotDisposed();
        byte[] clearBytes = null; 
        try {
            clearBytes = decryptBytes();
            accessor.access(clearBytes);
        }
        finally {
            SecurityUtil.clear(clearBytes);
        }
    }
    
    /**
     * Appends a single clear-text byte to the secure byte array.
     * The in-memory data will be decrypted, the byte will be
     * appended, and then it will be re-encrypted.
     * @param b The byte to append.
     * @throws IllegalStateException If the byte array is read-only
     * @throws IllegalStateException If the byte array has been disposed
     */
    public void appendByte(byte b) {
        checkNotDisposed();
        checkWriteable();
        byte[] clearBytes = null;
        byte[] clearBytes2 = null;
        try {
            clearBytes = decryptBytes();
            clearBytes2 = new byte[clearBytes.length+1];
            System.arraycopy(clearBytes, 0, clearBytes2, 0, clearBytes.length);
            clearBytes2[clearBytes2.length-1] = b;
            encryptBytes(clearBytes2);
        }
        finally {
            SecurityUtil.clear(clearBytes);
            SecurityUtil.clear(clearBytes2);
        }
    }
    
    /**
     * Clears the in-memory representation of the byte array.
     */
    public void dispose() {
        SecurityUtil.clear(_encryptedBytes);
        _disposed = true;
    }
    
    /**
     * Returns true iff this byte array has been marked read-only
     * @return true iff this byte array has been marked read-only
     * @throws IllegalStateException If the byte array has been disposed
     */
    public boolean isReadOnly() {
        checkNotDisposed();
        return _readOnly;
    }
    
    /**
     * Mark this byte array as read-only.
     * @throws IllegalStateException If the byte array has been disposed
     */
    public void makeReadOnly() {
        checkNotDisposed();
        _readOnly = true;
    }
    
    /**
     * Create a copy of the byte array. If this instance is read-only,
     * the copy will not be read-only.
     * @return A copy of the byte array.
     * @throws IllegalStateException If the byte array has been disposed
     */
    public GuardedByteArray copy() {
        checkNotDisposed();
        byte [] encryptedBytes2 = new byte[_encryptedBytes.length];
        System.arraycopy(_encryptedBytes, 0, encryptedBytes2, 0, _encryptedBytes.length);
        GuardedByteArray rv = new GuardedByteArray();
        rv._encryptedBytes = encryptedBytes2;
        return rv;
    }
    
    /**
     * Verifies that this base-64 encoded SHA1 hash of this byte array
     * matches the given value.
     * @param hash The hash to verify against.
     * @return True if the hash matches the given parameter.
     * @throws IllegalStateException If the byte array has been disposed
     */
    public boolean verifyBase64SHA1Hash(String hash) {
        checkNotDisposed();
        return _base64SHA1Hash.equals(hash);
    }
    
    private void checkWriteable() {
        if (_readOnly) {
            throw new IllegalStateException("Byte array is read-only");
        }
    }

    private void checkNotDisposed() {
        if (_disposed) {
            throw new IllegalStateException("Byte array is disposed");
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
        if ( o instanceof GuardedByteArray ) {
            GuardedByteArray other = (GuardedByteArray)o;
            //not the true contract of equals. however,
            //due to the high mathematical improbability of
            //two unequal byte arrays having the same secure hash,
            //this approach feels good. the alternative,
            //decrypting for comparison, is simply too
            //performance intensive to be used for equals
            return _base64SHA1Hash.equals(other._base64SHA1Hash);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return _base64SHA1Hash.hashCode();
    }
}