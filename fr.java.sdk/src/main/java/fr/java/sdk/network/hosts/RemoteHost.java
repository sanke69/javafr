package fr.java.sdk.network.hosts;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class RemoteHost {

	public static final RemoteHost	localhost_v4	= new RemoteHost("localhost", new byte[] { 127, 0, 0, 1 });
	public static final RemoteHost	localhost_v6	= new RemoteHost("localhost", new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });

	String	name;
	byte[]	ip;

	InetAddress addr;

	public static RemoteHost newRemoteHost(String _name, String _url) {
		InetAddress address;
		try {
			address = InetAddress.getByName(_url);
		} catch(UnknownHostException e) {
			return null;
		}

			System.out.println(address.getAddress());
			System.out.println(address.getHostName());

		return new RemoteHost(_name, address.getAddress());
	}

	public RemoteHost(byte[] _ip) {
		super();
		name = "?";
		ip = _ip;

		addr = null;
	}
	public RemoteHost(String _name, byte[] _ip) {
		super();
		name = _name;
		ip = _ip;

		addr = null;
	}

	public String getName() {
		return name;
	}
	public InetAddress getAddress() {
		try {
			addr = InetAddress.getByAddress(ip);
		} catch(UnknownHostException e) {
			name = "Unknown";
			addr = null;
		}
		return addr;
	}

	public boolean isOnLine(int _timeOut) {
		try {
			if(addr == null)
				addr = InetAddress.getByAddress(ip);
			return addr.isReachable(_timeOut);
		} catch(IOException e) {
			return false;
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Name= " + name + "\n");

		// On affiche l'adresse sous forme textuelle
		sb.append("Adresse texte: " + addr.getHostAddress() + "\n");
		// On rappelle l'adresse octale	
		sb.append("Adresse octale: " + Arrays.toString(addr.getAddress()) + "\n");
		// Ensuite on affiche les informations associées
		sb.append("Adresse joker ? " + addr.isAnyLocalAddress() + "\n");
		sb.append("Adresse de lien local ? " + addr.isLinkLocalAddress() + "\n");
		sb.append("Adresse de boucle locale ? " + addr.isLoopbackAddress() + "\n");
		sb.append("Adresse de réseau privé ? " + addr.isSiteLocalAddress() + "\n");
		sb.append("Adresse de multicast ? " + addr.isMulticastAddress() + "\n");
		if(addr.isMulticastAddress()) {
			// Testons la portée du multicast
			sb.append("Portée globale ? " + addr.isMCGlobal() + "\n");
			sb.append("Portée organisationnelle ? " + addr.isMCOrgLocal() + "\n");
			sb.append("Portée site ? " + addr.isMCSiteLocal() + "\n");
			sb.append("Portée lien ? " + addr.isMCLinkLocal() + "\n");
			sb.append("Portée noeud ? " + addr.isMCNodeLocal() + "\n");
		}

		try {
			sb.append("On-Line ? " + addr.isReachable(500) + "\n");
		} catch(IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		RemoteHost devcube = RemoteHost.newRemoteHost("devcube", "Devcube-4480");

		System.out.println(devcube.isOnLine(500));
	}

}
