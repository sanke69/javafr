package fr.java.utils;

import java.io.IOException;
import java.net.Authenticator;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.PasswordAuthentication;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Networks {

	public static List<String> getAllIPs() {
		List<String> ips = new ArrayList<String>();

		Enumeration<NetworkInterface> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();

			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while(ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					ips.add(i.getHostAddress());
				}
			}
		} catch(SocketException e1) {
			e1.printStackTrace();
		}

		return ips;
	}

	public static List<String> getLoopBackIPs() {
		List<String> loopback = new ArrayList<String>();

		Enumeration<NetworkInterface> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();

			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while(ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();

					if(i.getHostAddress().contains("127."))
						loopback.add(i.getHostAddress());
				}
			}
		} catch(SocketException e1) {
			e1.printStackTrace();
		}

		return loopback;
	}

	public static List<String> getLocalIPs() {
		List<String> local = new ArrayList<String>();

		Enumeration<NetworkInterface> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();

			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while(ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();

					if(i.getHostAddress().contains("192."))
						local.add(i.getHostAddress());
				}
			}
		} catch(SocketException e1) {
			e1.printStackTrace();
		}

		return local;
	}

	public static List<String> getRemoteIPs() {
		List<String> remote = new ArrayList<String>();

		Enumeration<NetworkInterface> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();

			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while(ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();

					if(!i.getHostAddress().contains("192.") && !i.getHostAddress().contains("127."))
						remote.add(i.getHostAddress());
				}
			}
		} catch(SocketException e1) {
			e1.printStackTrace();
		}

		return remote;
	}

	public void pingHost(String hostName) {
		try {
			InetAddress[] inet = InetAddress.getAllByName(hostName);

			String address = this.getIPv4Addresses(inet).getHostAddress();

			boolean status = this.getIPv6Addresses(inet).isReachable(5000);

			if(status) {
//				System.out.println(reverseDns(address) + " Host Reached\t" + this.getIPv6Addresses(inet).getHostAddress());
			} else {
				System.out.println(this.getIPv6Addresses(inet).getCanonicalHostName() + " Host Unreachable");
			}

		} catch(UnknownHostException e) {
			System.err.println("Host does not exists");
		} catch(IOException e) {
			System.err.println("Error in reaching the Host");
		}
	}

	public Inet6Address getIPv6Addresses(InetAddress[] addresses) {
		for(InetAddress addr : addresses) {
			if(addr instanceof Inet6Address) {
				return (Inet6Address) addr;
			}
		}
		return null;
	}

	public Inet4Address getIPv4Addresses(InetAddress[] addresses) {
		for(InetAddress addr : addresses) {
			if(addr instanceof Inet4Address) {
				return (Inet4Address) addr;
			}
		}
		return null;
	}

	public void setProxy(boolean _use_http_proxy, String _http_host, String _http_port, 
						 boolean _use_http_auth, String _http_user, String _http_pswd, 
						 boolean _use_socks_proxy, String _socks_host, String _socks_port, 
						 boolean _use_socks_auth, String _socks_user, String _socks_pswd) {
	    if (_use_http_proxy) {
	        // HTTP/HTTPS Proxy
	        System.setProperty("http.proxyHost", _http_host);
	        System.setProperty("http.proxyPort", _http_port);
	        System.setProperty("https.proxyHost", _http_host);
	        System.setProperty("https.proxyPort", _http_port);
	        if (_use_http_auth) {
//	            String encoded = new String(Base64.encodeBase64((_http_user + ":" + _http_pswd).getBytes()));
//	            con.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
	            Authenticator.setDefault(new ProxyAuth(_http_user, _http_pswd));
	        }
	    }
	    if (_use_socks_proxy) {
	        // SOCKS Proxy
	        System.setProperty("socksProxyHost", _socks_host);
	        System.setProperty("socksProxyPort", _socks_port);
	        if (_use_socks_auth) {
	            System.setProperty("java.net.socks.username", _socks_user);
	            System.setProperty("java.net.socks.password", _socks_pswd);
	            Authenticator.setDefault(new ProxyAuth(_socks_user, _socks_pswd));
	        }
	    }
	}

	public class ProxyAuth extends Authenticator {
	    private PasswordAuthentication auth;

	    private ProxyAuth(String user, String password) {
	        auth = new PasswordAuthentication(user, password == null ? new char[]{} : password.toCharArray());
	    }

	    protected PasswordAuthentication getPasswordAuthentication() {
	        return auth;
	    }
	}

}
