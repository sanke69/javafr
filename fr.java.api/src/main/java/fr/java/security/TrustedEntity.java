package fr.java.security;

public interface TrustedEntity {
	public static final String DEFAULT_CERTIFICATE = "default";

	public SecurityIdentity		getIdentity();

	public SecurityCertificate 	getCertificate();
	public SecurityCertificate 	getCertificate(String _alias);

	public SecurityKey 			getPublicKey();
	public SecurityKey 			getPublicKey(String _alias);

}
