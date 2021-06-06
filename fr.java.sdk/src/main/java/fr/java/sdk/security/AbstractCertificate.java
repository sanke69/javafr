package fr.java.sdk.security;

import fr.java.security.SecurityCertificate;
import fr.java.security.enums.CertificateFormat;

public abstract class AbstractCertificate implements SecurityCertificate {

	CertificateFormat format;

	public AbstractCertificate(CertificateFormat _format) {
		super();
		format = _format;
	}


	protected final void 			setFormat(CertificateFormat _format) {
		format = _format;
	}
	@Override
	public final CertificateFormat 	getFormat() {
		return format;
	}

}
