package fr.java.user.session;

import java.util.Optional;

import fr.java.user.data.Identity;
import fr.java.user.data.User;

public interface SessionUser extends User {
	public static final SessionUser ANONYMOUS = new SessionUser() {
		@Override public String           getUsername()  { return "anonymous"; }
		@Override public String           getPassword()  { return null; }

		@Override public Identity         getIdentity()  { return Identity.ANONYMOUS; }
		@Override public Optional<String> getAvatarUrl() { return Optional.empty(); }
	};
	public static final SessionUser GUEST     = new SessionUser() {
		@Override public String           getUsername()  { return "guest"; }
		@Override public String           getPassword()  { return null; }

		@Override public Identity         getIdentity()  { return Identity.ANONYMOUS; }
		@Override public Optional<String> getAvatarUrl() { return Optional.empty(); }
	};

	public String 			getUsername();
	public String 			getPassword(); // TODO:: change to GuardedString
	public Optional<String> getAvatarUrl();

}
