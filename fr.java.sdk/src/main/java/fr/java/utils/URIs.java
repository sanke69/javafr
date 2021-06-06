package fr.java.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class URIs {

	public static URI 		convert(String _path) {
		try {
			return new URI( "file://" + _path );
		} catch (URISyntaxException e) {
			return null;
		}
	}
	public static URI 		convert(Path _path) {
		return _path.toUri();
	}
	public static URI 		convert(URL _url) {
		try {
			return _url.toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static URI 		convert(String _domain, String _path) {
		try {
			return new URI( "http://" + _domain + _path );
		} catch (URISyntaxException e) {
			return null;
		}
	}
	public static URI 		convert(String _user, String _host, String _path, int _port) {
		try {
			return new URI( "ssh.unix://" + _user + "@" + _host + ":" + _port + _path );
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public static  Path 	asPath(URI _uri) {
		return Paths.get(_uri.toString().replace("file://", ""));
	}
	public static  Path 	asPath(Optional<URI> _uri) {
		if(_uri.isEmpty())
			return Paths.get("");

		return Paths.get(_uri.get().toString().replace("file://", ""));
	}

	public static  URL 		asURL(URI _uri) {
		try {
			return _uri.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static  URL	 	asURL(Optional<URI> _uri) {
		if(_uri.isEmpty())
			return null;

		try {
			return _uri.get().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean 	isExist(URI _uri) {
		return new File(_uri).exists();
	}

	public static long 		getResourceSize(URI _uri) {
		return getResourceSize(Optional.ofNullable(_uri));
	}
	public static long 		getResourceSize(Optional<URI> _uri) {
		if(_uri.isEmpty())
			return -1;

		long length = -1;
		try {
			length = _uri.get().toURL().openConnection().getContentLengthLong();
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

		return length;
	}

	public static String	getResourceName(URI _uri) {
		return getResourceName(Optional.ofNullable(_uri));
	}
	public static String	getResourceName(Optional<URI> _uri) {
		if(_uri.isEmpty())
			return "<undef>";

		try {
            URL url = _uri.get().toURL();
            return new File(url.getFile()).getName();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return null;
	}

}
