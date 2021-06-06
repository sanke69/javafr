package fr.java.sdk.network.hosts;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalHost /*extends RemoteHost*/ {

	InetAddress theLocalhost;
	Map<String, NetworkInterface> inetInterfaces;
	Map<String, InterfaceAddress[]> inetAddresses;
	
	
	public LocalHost() {
		inetInterfaces = new HashMap<String, NetworkInterface>();
		inetAddresses  = new HashMap<String, InterfaceAddress[]>();

		try {
			theLocalhost = InetAddress.getLocalHost();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public String[] getInetInterfaceNames() {
		try {
			Enumeration<NetworkInterface> theIntfList = NetworkInterface.getNetworkInterfaces();
			while(theIntfList.hasMoreElements()) {
				NetworkInterface theIntf = theIntfList.nextElement();
				inetInterfaces.put(theIntf.getName(), theIntf);
			}
			return inetInterfaces.keySet().toArray(new String[inetInterfaces.size()]);
		} catch(SocketException e) {
			return new String[] { "Unable to get access to network interfaces" };
		}
	}
	
	/**
	 * METHODS ASSOCIATED TO INTERFACES
	 */
	public NetworkInterface getInetInterface(String _interface) {
		return inetInterfaces.get(_interface);
	}
	public byte[]  getMAC(String _interface) {
		try {
			return inetInterfaces.get(_interface).getHardwareAddress();
		} catch(SocketException e) {
			return null;
		}
	}
	public int     getMTU(String _interface) {
		try {
			return inetInterfaces.get(_interface).getMTU();
		} catch(SocketException e) {
			return -1;
		}
	}
	public boolean supportMultiCast(String _interface) {
		try {
			return inetInterfaces.get(_interface).supportsMulticast();
		} catch(SocketException e) {
			return false;
		}
	}
	public boolean isLoopBack(String _interface) {
		try {
			return inetInterfaces.get(_interface).isLoopback();
		} catch(SocketException e) {
			return false;
		}
	}
	public boolean isPointToPoint(String _interface) {
		try {
			return inetInterfaces.get(_interface).isPointToPoint();
		} catch(SocketException e) {
			return false;
		}
	}
	public boolean isVirtual(String _interface) {
		return inetInterfaces.get(_interface).isVirtual();
	}
	public boolean isUp(String _interface) {
		try {
			return inetInterfaces.get(_interface).isUp();
		} catch(SocketException e) {
			return false;
		}
	}

	/**
	 * METHODS ASSOCIATED TO ADDRESSES
	 */
	public InterfaceAddress[] getInetAddresses(String _interface) {
		return inetAddresses.get(_interface);
	}

	public String getHostName(String _interface, String _IP) {
		return inetAddresses.get(_interface)[0].getAddress().getHostName();
	}
	public String getClass(String _interface, String _IP) {
		return inetAddresses.get(_interface)[0].getAddress().getClass().getSimpleName();
	}
	public String getIP(String _interface, String _IP) {
		InetAddress addr = inetAddresses.get(_interface)[0].getAddress();
		return addr.getHostAddress() + "/" + inetAddresses.get(_interface)[0].getNetworkPrefixLength();
	}
	public String getNetMask(String _interface, String _IP) {
		int maskInt = Integer.MIN_VALUE >> (inetAddresses.get(_interface)[0].getNetworkPrefixLength() - 1);
		return toIPAddrString(maskInt);
	}
	public String getMAC(String _interface, String _IP) {
		InetAddress addr = inetAddresses.get(_interface)[0].getAddress();
		return toMACAddrString(addr.getAddress());
	}
	public String getBroadcast(String _interface, String _IP) {
		InterfaceAddress addr = inetAddresses.get(_interface)[0];
		return addr.getBroadcast().getHostAddress();
	}
	public String getCanonicalHostName(String _interface, String _IP) {
		InetAddress addr = inetAddresses.get(_interface)[0].getAddress();
		return addr.getCanonicalHostName();
	}
	public boolean isLocal(String _interface, String _IP) {
		InetAddress addr = inetAddresses.get(_interface)[0].getAddress();
		return addr.isSiteLocalAddress();
	}
	
	public void initialize() {
		try {
			Enumeration<NetworkInterface> theIntfList = NetworkInterface.getNetworkInterfaces();
			NetworkInterface theIntf = null;

			while(theIntfList.hasMoreElements()) {
				theIntf = theIntfList.nextElement();

				List<InterfaceAddress> theAddrList = theIntf.getInterfaceAddresses();
				InterfaceAddress[] addrs = new InterfaceAddress[theAddrList.size()];

				int index = 0;
				for(InterfaceAddress intAddr : theAddrList)
					addrs[index++] = intAddr;

				inetAddresses.put(theIntf.getName(), addrs);
			}
		} catch(SocketException e) {
			e.printStackTrace();
		}
	}
	

	public static String toMACAddrString(byte[] a) {
		if(a == null) {
			return "null";
		}
		int iMax = a.length - 1;

		if(iMax == -1) {
			return "[]";
		}

		StringBuilder b = new StringBuilder();
		b.append('[');
		for(int i = 0;; i++) {
			b.append(String.format("%1$02x", a[i]));

			if(i == iMax) {
				return b.append(']').toString();
			}
			b.append(":");
		}
	}

	public static String toIPAddrString(int ipa) {
		StringBuilder b = new StringBuilder();
		b.append(Integer.toString(0x000000ff & (ipa >> 24)));
		b.append(".");
		b.append(Integer.toString(0x000000ff & (ipa >> 16)));
		b.append(".");
		b.append(Integer.toString(0x000000ff & (ipa >> 8)));
		b.append(".");
		b.append(Integer.toString(0x000000ff & (ipa)));
		return b.toString();
	}

	
}
