package fr.java.sdk.network.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import fr.java.network.InternetProtocolVersion;
import fr.java.network.SocketFamily;
import fr.java.network.sockets.SocketTCP;

public class SimpleSocketTCP implements SocketTCP {
	public Socket			socket;
	private InputStream		sis;
	private OutputStream	sos;

	public SimpleSocketTCP(Socket _socket) {
		socket = _socket;
	}
	public SimpleSocketTCP(String _addr, int _port) {
		// Constructor used by the Client Application Part
		try {
			socket = new Socket(_addr, _port);
		} catch (UnknownHostException e) {
			System.out.println("Server not found");
		} catch (IOException e) {
			if(e.getLocalizedMessage().contains("Connection refused"))
				System.out.println("Connection has been refused");
		}
	}

	public SimpleSocketTCP(URL _url) throws UnknownHostException, IOException {
		String protocol = _url.getProtocol();
		String host     = _url.getHost();
		int    port     = _url.getPort();

		socket = new Socket(host, port);
		configure();
	}


	@Override 
	public InternetProtocolVersion 	getVersion() { return InternetProtocolVersion.V4; }
	@Override
	public SocketFamily 			getFamily() { return SocketFamily.IP; }

	@Override
	public InputStream 				getInputStream() throws IOException {
		return socket.getInputStream();
	}
	@Override
	public OutputStream 			getOutputStream() throws IOException {
		return socket.getOutputStream();
	}

	private void 					configure() throws IOException {
		sis = new DataInputStream(socket.getInputStream());
		sos = new DataOutputStream(socket.getOutputStream());
	}

	public boolean 					isConnected() {
		return socket != null;
	}

	public void 					close() throws IOException {
		socket.close();
	}

	public void 					send(byte[] _stream) {
		try {
			sos.write(_stream);
			sos.flush();
		} catch (IOException e) {
			;
		} catch(NullPointerException e) {
			;
		}
	}
	public byte[] 					receive() {
		byte[] stream = null;
		try {
			stream = new byte[sis.available()];
			sis.read(stream);
		} catch(java.net.SocketTimeoutException e) {
			;
		} catch (IOException e) {
			;
		} catch(NullPointerException e) {
			;
		}

		return stream;
	}

}
