package fr.java.sdk.network;

import fr.java.beans.impl.AbstractBean;
import fr.java.user.patterns.RemoteHost;

public class SimpleRemoteHost extends AbstractBean implements RemoteHost {
	private static final long serialVersionUID = 4061694064217154456L;

	private String hostname;
	private int port;

	public SimpleRemoteHost() {
		hostname = "0.0.0.0";
		port     = -1;
	}
	public SimpleRemoteHost(String _hostname, int _port) {
		hostname = _hostname;
		port     = _port;
	}

	public void setHostname(String _hostname) {
		String old = getHostname();
		hostname = _hostname;
		firePropertyChange("hostname", old, getHostname());
	}
	public String getHostname() {
		return hostname;
	}

	public void setPort(int _port) {
		int old = getPort();
		port = _port;
		firePropertyChange("port", old, getPort());
	}
	public int getPort() {
		return port;
	}

}
