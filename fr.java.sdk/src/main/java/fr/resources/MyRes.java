package fr.resources;

import java.net.MalformedURLException;
import java.net.URL;

@Deprecated
public final class MyRes {
	public static final URL earthMap = generateEarthMapURL();

	private static final URL generateEarthMapURL() {
		URL earthMap = null;

		try {
			earthMap = new URL("http://javafr.sp-web.fr/res/earth/Earth.png");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return earthMap;
	}
	
}
