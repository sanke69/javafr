package fr.java.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Resources {

	private static List<Path> searchPaths;
	static {
		searchPaths = new ArrayList<Path>();
/*
		final String dataPath;
		switch(OSHelper.get()) {
		case WINDOWS : dataPath = "H:/home/media/DATA"; break;
		case LINUX   : dataPath = "/media/sanke/home_usb/home/media/DATA"; break;
		case ANDROID : 
		case OSX     : 
		case SOLARIS : 
		case UNKNOWN : 
		default      : dataPath = null; break;
		}

		MediaHelper.addToSearchPath(dataPath);
		MediaHelper.addToSearchPath(dataPath + "/3D");
		MediaHelper.addToSearchPath(dataPath + "/3D/_3D_Universe");
*/
	}

	public static List<Path> searchPaths() { return searchPaths; }
	
	
	public static void addToSearchPath(Path _path) {
		if(!searchPaths.contains(_path))
			searchPaths.add(_path);
	}
	public static void removeFromSearchPath(String _path) {
		Path path = Paths.get(_path.codePointAt(_path.length() - 1) != '/' ? _path + "/" : _path);
		if(searchPaths.contains(path))
			searchPaths.remove(path);
	}

}
