package fr.java.sdk.network;

import fr.java.beans.impl.AbstractBean;
import fr.java.user.patterns.RemoteUser;

public class SimpleRemoteUser extends AbstractBean implements RemoteUser {
	private String username;
	private String password;
	
	// TODO:: Security Failure
	public SimpleRemoteUser() {
		username = "unknown";
		password = null;
	}
	public SimpleRemoteUser(String _username, String _password) {
		username = _username;
		password = _password;
	}

	public void setUsername(String newUsername) {
		String old = getUsername();
		username = newUsername;
		firePropertyChange("username", old, getUsername());
	}
	public String getUsername() {
		return username;
	}

	public void setPassword(String _password) {
		String old = getPassword();
		password = _password;
		firePropertyChange("password", old, getPassword());
	}
	public String getPassword() {
		return password;
	}

}
