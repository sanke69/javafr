package fr.java.network.sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public interface SocketUDP {

	public void sendTo(byte[] _data, String _addr, int _port);

	public void setSoTimeout(int _timeout) throws SocketException;

	public void receive(DatagramPacket packet) throws IOException;

	public void close();

}
