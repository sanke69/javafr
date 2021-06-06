package fr.java.user.session;

import fr.java.user.session.exceptions.InvalidSessionParameter;

public interface SessionAuthenticator {

	public String  getName();

	public Session openSession   (SessionUser _user) throws InvalidSessionParameter;
	public boolean closeSession  (Session _session);

	public boolean isValid       (Session _session);

}
