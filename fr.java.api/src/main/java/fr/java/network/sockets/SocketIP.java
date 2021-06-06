package fr.java.network.sockets;

import fr.java.network.InternetProtocolVersion;

public interface SocketIP extends Socket  {

	public InternetProtocolVersion getVersion();

}
