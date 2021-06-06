package fr.java.sdk.network.sockets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import fr.java.network.sockets.SocketUDP;

public class SimpleSocketUDP implements SocketUDP {
	DatagramSocket	socket = null;
	InetAddress     addr   = null;
	int             port   = 0;

	protected SimpleSocketUDP() {
		try {
			addr = null;
			port = -1;

			socket = new DatagramSocket();
		} catch(SocketException e) {
			socket.close();
			socket = null;
		}
	}
	public SimpleSocketUDP(String _addr, int _port) {
		try {
			addr = InetAddress.getByName(_addr);
			port = _port;

			socket = new DatagramSocket(port, addr);
		} catch (UnknownHostException e) { 
			socket = null; 
		} catch(SocketException e) {
			socket.close();
			socket = null;
		}
	}

	public DatagramSocket sock() {
		return socket;
	}

	public void setSoTimeout(int _timeout) throws SocketException {
		socket.setSoTimeout(_timeout);
	}
	
	public void send(Object _o) {
		if(socket != null) {
			try {
				ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
				ObjectOutputStream    ooStream  = new ObjectOutputStream(baoStream);
				ooStream.writeObject(_o);
	
				socket.send(new DatagramPacket(baoStream.toByteArray(),
												 baoStream.toByteArray().length, 
												 addr, port));

			} catch(SocketException e) {
				;
			}  catch(IOException e) {
				;
			}
		}
		
		socket.close();
	}
	public void sendTo(byte[] _data, String _addr, int _port) {
		if(socket != null) {
			try {
				socket.send(new DatagramPacket( _data,
												_data.length, 
												InetAddress.getByName(_addr), _port));

			} catch(SocketException e) {
				;
			}  catch(IOException e) {
				;
			}
		}
		
		socket.close();
	}
	public void receive(DatagramPacket _packet) throws IOException {
		socket.receive(_packet);
	}
	public void sendTo(Object _o, String _addr, int _port) {
		if(socket != null) {
			try {
				ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
				ObjectOutputStream    ooStream  = new ObjectOutputStream(baoStream);
				ooStream.writeObject(_o);

				socket.send(new DatagramPacket(baoStream.toByteArray(),
												 baoStream.toByteArray().length, 
												 InetAddress.getByName(_addr), _port));

			} catch(SocketException e) {
				;
			}  catch(IOException e) {
				;
			}
		}
		
		socket.close();
	}

	public void close() {
		socket.close();
	}
}
