package fr.session;

import java.util.Optional;

import fr.java.user.data.Identity;
import fr.java.user.session.SessionUser;
import fr.user.UserBean;

public class SessionUserBean extends UserBean implements SessionUser {
	private final String username;
	private final String password;
	private final String avatar;

	public SessionUserBean(String _username, String _password, Identity _identity) {
		super(_identity);
		username = _username;
		password = _password;
		avatar   = null;
	}
	public SessionUserBean(String _username, String _password, Identity _identity, String _avatar) {
		super(_identity);
		username = _username;
		password = _password;
		avatar   = _avatar;
	}

	@Override
	public String 			getUsername() {
		return username;
	}
	@Override
	public String 			getPassword() {
		return password;
	}

	@Override
	public Optional<String> getAvatarUrl() {
		return Optional.ofNullable(avatar);
	}
	
}
