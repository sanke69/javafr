package fr.java.sdk.network.sockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import fr.java.network.sockets.SocketTCP;

public /*abstract*/ class ServerSocketTCP implements SocketTCP.Server {

	public static interface SocketServerListener {
		public SocketTCP onIncomingConnection();
	}

	private ServerSocket	serverSocket	= null;
	private Thread			listeningThread	= null;
	private boolean			isRunning		= false;

	private int m_Port = 0;

	public ServerSocketTCP(int _port) {
		m_Port = _port;
	}

	public void start() {
		isRunning = true;
		try {
			serverSocket = new ServerSocket(m_Port);
/*
			while(isRunning) {
				final Socket socket = serverSocket.accept();

				listeningThread = new Thread(() -> {
					final SocketTCP incoming = new SocketTCP(socket);

					ByteArrayInputStream baiStream = null;
					ObjectInputStream    oiStream = null;

					try {
						final byte[] stream = new byte[incoming.getReceiveBufferSize()];

						incoming.getInputStream().read(stream);

						baiStream = new ByteArrayInputStream(stream);
						oiStream = new ObjectInputStream(baiStream);

						oiStream.readFully(stream);

						final Object o = oiStream.readObject();

						Thread traitment = new Thread(() -> onReceivedData(incoming, o));
						traitment.start();

					} catch(SocketException e1) {
						e1.printStackTrace();
					} catch(IOException e) {
						e.printStackTrace();
					} catch(InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch(ClassNotFoundException e) {
						e.printStackTrace();
						if(baiStream != null)
							onReceivedData(incoming, baiStream);
					}

				});
				listeningThread.start();

			}
*/
		} catch(SocketException _e) {
			;
		} catch(IOException _e1) {
			;
		}
	}
	public void stop() {
		isRunning = false;

		listeningThread.interrupt();

		if(serverSocket.isBound()) {
			try {
				serverSocket.wait();
				serverSocket.close();
			} catch(InterruptedException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void bind(String _ip, int _port) {
		InetSocketAddress bindAddr = new InetSocketAddress(_ip, _port);
		try {
			serverSocket.bind(bindAddr);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void listen(int _nbAccept) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SocketTCP accept() {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		listeningThread.interrupt();
		
		if(serverSocket.isBound()) {
			try {
				serverSocket.wait();
				serverSocket.close();
			} catch(InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
			
	}
}
