package fr.session;

import java.util.Optional;

import fr.java.sdk.security.encryption.GuardedString;
import fr.java.user.session.Session;
import fr.java.user.session.SessionAuthenticator;
import fr.java.user.session.SessionUser;
import fr.java.user.session.exceptions.InvalidSessionParameter;
import fr.session.SessionService;
import fr.session.SessionUserBean;
import javafx.beans.property.SimpleObjectProperty;

public class SessionProperty extends SimpleObjectProperty<Session> implements Session {

	private static final SessionAuthenticator authenticator;

	static {
		authenticator = new SessionService().getImplementation("MonoSessionAuthenticator");
	}

	public final Session 			loginPredicate(String _username, GuardedString _password) {
		_password.access(decoded -> {
			String      magic      = new String(decoded);
			SessionUser userBean   = new SessionUserBean(_username, magic, null);
			Session     newSession = null;
			try {
				newSession = authenticator.openSession(userBean);
			} catch (InvalidSessionParameter e) {
				e.printStackTrace();
			}
			set(newSession);
		});

		return get();
	}

	public final void 				unset() {
		set(null);
	}

	@Override
	public boolean 					isValid() {
		return getAuthenticator().isValid(this);
	}

	@Override
	public Type 					getType() {
		return get() != null ? get().getType() : Session.Type.UNKNOWN;
	}

	@Override
	public Optional<SessionUser> 	getUser() {
		return get() != null ? get().getUser() : Optional.empty();
	}

	@Override
	public SessionAuthenticator 	getAuthenticator() {
		return authenticator;
	}

}
