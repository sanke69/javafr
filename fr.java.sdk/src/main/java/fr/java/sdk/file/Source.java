package fr.java.sdk.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public class Source {
	String 		string;
	Path   		path;
	URL    		url;
	InputStream istream;

	public Source(String _string) {
		super();
		string = _string;
	}
	public Source(Path _path) {
		super();
		path = _path;
	}
	public Source(URL _url) {
		super();
		url = _url;
	}
	public Source(InputStream _is) {
		super();
		istream = _is;
	}

	public InputStream getInputStream() {
		if(istream != null)
			return istream;

		if(string != null)
			return istream = __getFromString();
		else if(path != null)
			return istream = __getFromPath();
		else if(url != null)
			return istream = __getFromURL();
		return null;
	}
	
	public void finalize() {
		if(istream != null)
			try {
				istream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	InputStream __getFromString() {
		InputStream is = null;
		try {
			is = new FileInputStream(string);
		} catch (IOException e) {
			try {
				is = new URL(string).openConnection().getInputStream();
			} catch (IOException e2) {
				is = null;
				e.printStackTrace();
			}
		}
		
		return is;
	}
	InputStream __getFromPath() {
		InputStream is = null;
		try {
			is = new FileInputStream(path.toFile());
		} catch (IOException e) {
			is = null;
			e.printStackTrace();
		}
		
		return is;
	}
	InputStream __getFromURL() {
		InputStream is = null;
		try {
			is = url.openConnection().getInputStream();
		} catch (IOException e) {
			is = null;
			e.printStackTrace();
		}
		
		return is;
	}

}
