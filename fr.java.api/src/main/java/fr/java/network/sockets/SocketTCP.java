package fr.java.network.sockets;

import java.io.IOException;

public interface SocketTCP extends SocketIP {

	public interface Server {
		public void 		bind(String _ip, int _nbAccept);
		public void 		listen(int _nbAccept);
		public SocketTCP 	accept();
	}

	public void close() throws IOException;

}
