package fr.java.security;

import java.security.cert.Certificate;

import fr.java.security.enums.CertificateFormat;
import fr.java.security.exceptions.ConversionNotSupported;
import fr.java.security.exceptions.GenerationNotSupported;

public interface SecurityCertificate {
	
	public interface Generator {
		public SecurityCertificate generateRequest(SecurityIdentity _identity, SecurityKey.Private _privateKey) throws GenerationNotSupported;
		public SecurityCertificate generateSelfSigned(SecurityCertificate _request, SecurityKey.Private _privateKey, CertificateFormat _format, int _dayValidity) throws GenerationNotSupported;
	}

	public interface Converter {
		public SecurityCertificate convert(SecurityCertificate _key, CertificateFormat _newFormat) throws ConversionNotSupported;
	}

	public default boolean 		isRequest() { return getFormat() == CertificateFormat.REQUEST; }
	
	public CertificateFormat 	getFormat();
	public Certificate[]		getCertificates();

	public SecurityKey.Public	getAssociatedKey();

}
