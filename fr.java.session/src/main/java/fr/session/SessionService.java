package fr.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import fr.java.user.session.Session;
import fr.java.user.session.SessionAuthenticator;
import fr.java.user.session.SessionUser;
import fr.java.user.session.exceptions.InvalidSessionParameter;

public final class SessionService {
	Map<String, SessionAuthenticator> 	implementations;
	SessionAuthenticator 				defaultImplementation;

	private Collection<Session>       	sessions;

	public SessionService() {
		super();

		implementations = new HashMap<String, SessionAuthenticator>();
		reloadImplementations();
	}

	public Collection<SessionAuthenticator> getImplementations() {
		return implementations.values();
	}
	public SessionAuthenticator 			getImplementation(String _authenticatorName) {
		return implementations.get(_authenticatorName);
	}
	public SessionAuthenticator 			getDefaultImplementation() {
		return defaultImplementation;
	}

	public Session newSession(SessionUser _user) {
		if(defaultImplementation == null)
			return null;

		Session newSession = null;
		try {
			newSession = defaultImplementation.openSession(_user);
		} catch(InvalidSessionParameter e) {
			e.printStackTrace();
		}

		return newSession;
	}
	public Session newSession(SessionUser _user, String _authenticator) {
		if(!implementations.containsKey(_authenticator))
			return null;

		Session newSession = null;
		try {
			newSession = implementations.get(_authenticator).openSession(_user);
		} catch(InvalidSessionParameter e) {
			e.printStackTrace();
		}

		return newSession;
	}

	void reloadImplementations() {
		if(implementations.size() > 0) {
			System.err.println("Warning, all sessions will be closed");
			System.err.println("TBD");
			implementations.clear();
		}

		ServiceLoader.load(SessionAuthenticator.class)
					 .iterator()
					 .forEachRemaining(auth -> implementations.put(auth.getName(), auth));

		if(implementations.size() > 0)
			defaultImplementation = implementations.values().iterator().next();
		else
			System.err.println("Warning, no authenticator found");
	}

}
