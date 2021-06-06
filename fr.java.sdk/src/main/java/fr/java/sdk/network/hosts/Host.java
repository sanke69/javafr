package fr.java.sdk.network.hosts;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

public class Host {

	public static final Host localhost_v4 = new Host("localhost", new byte[] { 127, 0, 0, 1 });
	public static final Host localhost_v6 = new Host("localhost", new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });

	public static final Host localhost = localhost_v6;

	String 		name;
	InetAddress addr;
	String 		user, pswd;
	
	public Host(String _name, byte[] _ip) {
		this(_name, _ip, null, null);
	}
	public Host(String _name, byte[] _ip, String _user, String _pswd) {
		super();
		name = _name;
		user = _user;
		pswd = _pswd;

		try {
			addr = InetAddress.getByAddress(_ip);
		} catch(UnknownHostException e) {
			name = "Unknown";
			addr = null;
		}
	}

	public String 		getName() {
		return name;
	}
	public InetAddress 	getAddress() {
		return addr;
	}
	public String 		getUserName() {
		return user;
	}
	public String 		getUserPassword() {
		return pswd;
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
		if (addr.isMulticastAddress()) {
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

	public static void main(String[] args) throws UnknownHostException, SocketException {
		System.out.println(localhost_v4.toString());
		
		InetAddress[] addrs = InetAddress.getAllByName("localhost");
		for (InetAddress addr: addrs)
			System.out.println(addr.getHostAddress());
		
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements())
		{
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration ee = n.getInetAddresses();
		    while (ee.hasMoreElements())
		    {
		        InetAddress i = (InetAddress) ee.nextElement();
		        System.out.println(i.getHostAddress());
		    }
		}
	}
}
