package fr.java.network.sockets;

import java.io.DataInput;
import java.io.IOException;
import java.net.URL;

public interface SocketHTTP {

	public DataInput GET(String _request) throws IOException;
	public DataInput POST(URL _httpServer) throws IOException;

}
