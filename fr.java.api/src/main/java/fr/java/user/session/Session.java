package fr.java.user.session;

import java.util.Optional;

public interface Session {

	public static enum Type {
		UNKNOWN, ANONYMOUS, GUEST, REGISTERED;
	}

	public Type    					getType();
	public SessionAuthenticator		getAuthenticator();

	public Optional<SessionUser> 	getUser();
	public boolean 					isValid();

}
