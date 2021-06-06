package fr.java.sdk.security.encryption;

import fr.java.security.encryption.Encryptor;

public abstract class EncryptorFactory {

    // At some point we might make this pluggable, but for now, hard-code
    private static final String IMPL_NAME = "fr.java.sdk.security.encryption.impl.EncryptorFactoryImpl";

    private static EncryptorFactory _instance;

    /**
     * Get the singleton instance of the {@link EncryptorFactory}.
     */
    public static synchronized EncryptorFactory getInstance() {
        if (_instance == null) {
            try {
                Class<?> clazz = Class.forName(IMPL_NAME);
                Object object = clazz.newInstance();
                _instance = EncryptorFactory.class.cast(object);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return _instance;
    }

    /**
     * Default encryptor that encrypts/descrypts using a default key
     */
    public abstract Encryptor getDefaultEncryptor();
    
    /**
     * Creates a new encryptor initialized with a random encryption key
     */
    public abstract Encryptor newRandomEncryptor();

}