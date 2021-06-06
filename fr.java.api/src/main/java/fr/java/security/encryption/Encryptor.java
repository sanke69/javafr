package fr.java.security.encryption;

public interface Encryptor {

    public byte [] decrypt(byte [] bytes);
    public byte [] encrypt(byte [] bytes);

}
