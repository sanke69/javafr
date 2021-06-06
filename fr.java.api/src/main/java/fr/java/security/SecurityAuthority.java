package fr.java.security;

public interface SecurityAuthority {

	public SecurityCertificate 	deliverSignedCertificate(SecurityCertificate _certificate, SecurityCertificate _request);

	public int 					revokeCertificate(SecurityCertificate _certificate);

}
