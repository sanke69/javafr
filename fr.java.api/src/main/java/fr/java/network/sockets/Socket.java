package fr.java.network.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.java.network.SocketFamily;

public interface Socket {

	SocketFamily 			getFamily();

	public InputStream 		getInputStream() throws IOException;
    public OutputStream 	getOutputStream() throws IOException;

}
