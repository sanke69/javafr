package fr.java.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DistantFiles {

	static final URL urlTest;
	static {
        java.net.URL temp;
        try {
            temp = new URL("http://sp-web.fr/?section=perso&chapter=ecrits&page=/articles/Régionales2015.MD");
        } catch (java.net.MalformedURLException e) {
            temp = null;
        }
        urlTest = temp;
	}
	
	public static void main(String[] args) throws MalformedURLException {
		System.out.println(isExist(urlTest));
		System.out.println(getContentAsString(urlTest));
		System.out.println("DONE");
	}
	
	public static boolean 			isExist(URL _url) {
		URLConnection c = null;
		try {
			c = _url.openConnection();
			c.connect();
			c.getInputStream().close();

			return true;
		} catch(IOException e) {
			return false;
		} finally {
			c = null;
		}
		
	}

	public static InputStream 		getContent(URL _url) {
		InputStream is = null;
		try { 
			URLConnection c = _url.openConnection();
			c.setReadTimeout(15*1000);
			is = c.getInputStream();
		} catch(Exception e) { is = null; }
		return is;
	}
	public static byte[]      		getContentAsByteArrays(URL _url) {
		InputStream is = getContent(_url);
		if(is == null) return null;

		byte[] buffer;
		try {
			if(is.available() == 0) Thread.sleep(2500);
			
			buffer = new byte[is.available()];
			is.read(buffer, 0, buffer.length);
			return buffer;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String      		getContentAsString(URL _url) {
		byte[] data = getContentAsByteArrays(_url);
		if(data == null) return null;
		return new String(data);
	}

	public static InputStream 		download(URL _url) {
	    ByteBuffer            tmp = ByteBuffer.wrap(new byte[65635 * 1024]);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			ReadableByteChannel rbc = Channels.newChannel(_url.openStream());

			int tmpSize = 0;
			while((tmpSize = rbc.read(tmp)) > -1)
				out.write(tmp.array(), 0, tmpSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public static void 				download(URL _url, URL _dest) {
		try {
			ReadableByteChannel rbc = Channels.newChannel(_url.openStream());
			FileOutputStream fos = new FileOutputStream(_dest.getPath());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
