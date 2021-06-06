package fr.java.sdk.security.encryption.impl;

import fr.java.sdk.security.encryption.EncryptorFactory;
import fr.java.security.encryption.Encryptor;

public class EncryptorFactoryImpl extends EncryptorFactory {

    private final Encryptor defaultEncryptor;

    public EncryptorFactoryImpl() {
        defaultEncryptor = new EncryptorImpl(true);
    }

    @Override
    public Encryptor getDefaultEncryptor() {
        return defaultEncryptor;
    }

    @Override
    public Encryptor newRandomEncryptor() {
        return new EncryptorImpl(false);
    }

}