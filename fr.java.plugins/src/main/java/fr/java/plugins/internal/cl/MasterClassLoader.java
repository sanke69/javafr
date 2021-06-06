package fr.java.plugins.internal.cl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import fr.java.plugins.internal.loaders.ModuleLoader;
// import fr.java.sdk.log.LogInstance;

public class MasterClassLoader {
//	public static final LogInstance log = LogInstance.getLogger();

	// \ /
	//  |   init0: bootstrap Class Loader, natif, <JAVA_HOME>/lib
	//  |   init1: extension Class Loader, ..., <JAVA_HOME>/lib/ext +  java.ext.dirs
	//  |   init2: system    Class Loader, ..., java.class.path
	//  |   init3: User      Class Loader: THIS CLASS !!!
	// \|/

	private static final ClassLoader bootstrap = Object.class.getClassLoader();	// May not be exist...
	private static final ClassLoader extension = ClassLoader.getSystemClassLoader().getParent();
	private static final ClassLoader system    = ClassLoader.getSystemClassLoader();

	private static final ClassLoader context   = Thread.currentThread().getContextClassLoader();
	private static       ClassLoader defaultCL = null;

	public static final void initDefaultClassLoader(ClassLoader _classLoader) {
		if(defaultCL != null)
			throw new IllegalAccessError();
		defaultCL = _classLoader;
	}
	public static final ClassLoader defaultClassLoader() {
		return defaultCL != null ? defaultCL : context;
	}
	

	public static final ClassLoader bootstrap() {
		return bootstrap;
	}
	public static final ClassLoader system() {
		return system;
	}

	public static final ClassLoader newClassLoader() {
		return new URLClassLoader(new URL[] {}, defaultClassLoader());
	}
	public static final ClassLoader newClassLoader(ClassLoader _parent) {
		return new URLClassLoader(new URL[] {}, _parent);
	}

	public static final ClassLoader newClassLoader(Path... _paths) {
		try {
			return new URLClassLoader(toURLs(_paths), defaultClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final ClassLoader newClassLoader(Collection<Path> _paths) {
		try {
			return new URLClassLoader(toURLs(_paths), defaultClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final ClassLoader newClassLoader(ClassLoader _parent, Path... _paths) {
		try {
			return new URLClassLoader(toURLs(_paths), _parent);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final ClassLoader newClassLoader(ClassLoader _parent, Collection<Path> _paths) {
		try {
			URL[] urls = toURLs(_paths);
			return new URLClassLoader(toURLs(_paths), _parent);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final ClassLoader newClassLoader(URL... _urls) {
		return new URLClassLoader(_urls, defaultClassLoader());
	}
	public static final ClassLoader newClassLoader(ClassLoader _parent, URL... _urls) {
		return new URLClassLoader(_urls, _parent);
	}

	public static final ClassLoader newDirectoryClassLoader(Path _directory) {
		try {
			return new URLClassLoader(toURLs(_directory), defaultClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final ClassLoader newDirectoryClassLoader(ClassLoader _parent, Path _directory) {
		try {
			return new URLClassLoader(toURLs(_directory), _parent);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final ClassLoader newJarClassLoader(Path _jar) {
		try {
			return new URLClassLoader(toURLs(_jar), defaultClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final ClassLoader newJarClassLoader(ClassLoader _parent, Path _jar) {
		try {
			return new URLClassLoader(toURLs(_jar), _parent);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final ClassLoader newModuleClassLoader(Path _jmod) {
		ModuleLoader modLoader = new ModuleLoader(_jmod, defaultClassLoader());

		return modLoader.getModuleClassLoader();
	}
	public static final ClassLoader newModuleClassLoader(ClassLoader _parent, Path _jmod) {
		ModuleLoader modLoader = new ModuleLoader(_jmod, _parent);

		return modLoader.getModuleClassLoader();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static final URL[] toURLs(String _path) throws MalformedURLException {
		return new URL[] { new URL(_path) };
	}
	private static final URL[] toURLs(String... _path) throws MalformedURLException {
		URL[] urls = new URL[_path.length];
		
		for(int i = 0; i < _path.length; ++i)
			urls[i] = new URL(_path[i]);
		return urls;
	}
	private static final URL[] toURLs(String _path, String... _others) throws MalformedURLException {
		if(_others == null)
			return toURLs(_path);

		URL[] urls = new URL[_others.length + 1];

		urls[0] = new URL(_path);
		for(int i = 0; i < _others.length; ++i)
			urls[i+1] = new URL(_others[i]);
		return urls;
	}
	/*
	private static final URL[] toURLs(Collection<String> _pathes) throws MalformedURLException {
		return _pathes.stream()
						.map(p -> {
								try {
									return new URL(p);
								} catch (MalformedURLException e) {
									e.printStackTrace();
									return null;
								}
							})
						.filter(url -> url != null)
						.collect(Collectors.toList()).toArray(new URL[0]);
	}
	*/
	
	private static final URL[] toURLs(Path _path) throws MalformedURLException {
		return new URL[] { _path.toUri().toURL() };
	}
	private static final URL[] toURLs(Path... _path) throws MalformedURLException {
		URL[] urls = new URL[_path.length];
		
		for(int i = 0; i < _path.length; ++i)
			urls[i] = _path[i].toUri().toURL();
		return urls;
	}
	private static final URL[] toURLs(Path _path, Path... _others) throws MalformedURLException {
		if(_others == null)
			return toURLs(_path);

		URL[] urls = new URL[_others.length + 1];

		urls[0] = _path.toUri().toURL();
		for(int i = 0; i < _others.length; ++i)
			urls[i+1] = _others[i].toUri().toURL();
		return urls;
	}
	private static final URL[] toURLs(Collection<Path> _pathes) throws MalformedURLException {
		return _pathes.stream()
						.map(p -> {
							try {
								return p.toUri().toURL();
							} catch (MalformedURLException e) {
								e.printStackTrace();
								return null;
							}
						})
						.filter(url -> url != null)
						.collect(Collectors.toList()).toArray(new URL[0]);
	}

	
	

	/*
	public void addLibDirectory(Path _libDir, boolean _recursive) {
		for(File lib : _libDir.toFile().listFiles(new PluginFilter()))
			addJarFile(lib.toPath());

		if(_recursive)
			for(File lib : _libDir.toFile().listFiles(new DirectoryFilter()))
				addLibDirectory(lib.toPath(), _recursive);
	}
	public void addJarFile(Path _jarFile) {
		try(JarFile jarFile = new JarFile(_jarFile.toFile())) {
			addURL(_jarFile.toUri().toURL());
		} catch(IOException e) {
			log.error("Failed to load ZIP file: " + _jarFile);
		}
	}
	public void addClassesDirectory(Path _classesDir) {
		try {
			this.addURL(_classesDir.toUri().toURL());
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
	}*/

	/**/
	private static class DirectoryFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	}

	private static class PluginFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().toLowerCase().endsWith(".jar");
		}
	}
/*
	private static class ClassesFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().toLowerCase().endsWith(".class");
		}
	}
*/
	private static final Class<?>[] parameters = new Class[] { URL.class };

	private static String			rootPath;
	private static HashSet<Path>	classPathElements;
	private static String			classPath;

	public static void add(String cp) {
		String seps = File.pathSeparator;

		if(!File.pathSeparator.equals(";"))
			seps += ";";

		for(StringTokenizer st = new StringTokenizer(cp, seps, false); st.hasMoreTokens();) {
			String pe = st.nextToken();
			File fe;
			String bn = null;

			if(pe.length() == 0)
				continue;

			fe = new File(pe);

			if(fe.getName().indexOf('*') != -1) {
				bn = fe.getName();
				fe = fe.getParentFile();
			}

			if(!fe.isAbsolute() && pe.charAt(0) != '\\')
				fe = new File(rootPath, fe.getPath());

			try {
				fe = fe.getCanonicalFile();
			} catch(IOException thr) {
//				log.severe("Skipping non-existent classpath element '" + fe + "' (" + thr + ").");
				continue;
			}

			if(bn != null)
				if(bn.length() != 0)
					fe = new File(fe, bn);

			if(classPathElements.contains(fe.getPath())) {
//				log.warning("Skipping duplicate classpath element '" + fe + "'.");
				continue;
			} else {
				classPathElements.add(fe.toPath());
			}

			if(bn != null) {
				if(bn.length() != 0)
					addJars(fe.getParentFile(), bn);
			} else if(!fe.exists()) {
//				log.finer("Could not find classpath element '" + fe + "'");
			} else if(fe.isDirectory()) {
				addURL_(createUrl(fe));
			} else if(fe.getName().toLowerCase().endsWith(".zip") || fe.getName().toLowerCase().endsWith(".jar") || fe.getName().toLowerCase().endsWith(".war")) {
				addURL_(createUrl(fe));
			} else {
//				log.finer("ClassPath element '" + fe + "' is not an existing directory and is not a file ending with '.zip', '.jar' or '.war'");
			}
		}
//		log.finer("Class loader is using classpath: \"" + classPath + "\".");
	}

	public static void addURL_(URL aURL) {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { aURL });
			method.setAccessible(false);
		} catch(Throwable t) {
//			log.log(Level.SEVERE, "Error adding " + aURL + " to system classloader", t);
		}
	}

	public static void addJars(File dir, String nam) {
		String[] jars; // matching jar files

		if(nam.endsWith(".jar"))
			nam = nam.substring(0, (nam.length() - 4));

		if(!dir.exists()) {
//			log.finer("Could not find directory for Class Path element '" + dir + File.separator + nam + ".jar'");
			return;
		}
		if(!dir.canRead()) {
//			log.finer("Could not read directory for Class Path element '" + dir + File.separator + nam + ".jar'");
			return;
		}

		if((jars = getListOfFile(dir.getPath(), ".zip;.jar;.war")) == null) {
//			log.finer("Error accessing directory for Class Path element '" + dir + File.separator + nam + ".jar'");
		} else if(jars.length == 0) {
//			log.finer("No JAR files match specification '" + new File(dir, nam) + ".jar'");
		} else {
//			log.finer("Adding files matching specification '" + dir + File.separator + nam + ".jar'");
			Arrays.sort(jars, String.CASE_INSENSITIVE_ORDER);
			for(int xa = 0; xa < jars.length; xa++) {
				addURL_(createUrl(new File(dir, jars[xa])));
			}
		}
	}

	private static URL createUrl(File fe) {
		try {
			URL url = fe.toURI().toURL();
//			log.finer("Added URL: '" + url.toString() + "'");
			if(classPath.length() > 0) {
				classPath += File.pathSeparator;
			}
			classPath += fe.getPath();
			return url;
		} catch(MalformedURLException thr) {
//			log.fine("Classpath element '" + fe + "' could not be used to create a valid file system URL");
			return null;
		}
	}

	private static String[] getListOfFile(String _dir, String _ext) {
		ArrayList<String> ext_tic = new ArrayList<String>(); // Taken Into
															// Account
		ArrayList<String> files = new ArrayList<String>();

		for(StringTokenizer st = new StringTokenizer(_ext, ";", false); st.hasMoreTokens();)
			ext_tic.add(st.nextToken());

		File folder = new File(_dir);
		File[] listOfFiles = folder.listFiles();

		for(int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile()) {
				for(String ext : ext_tic)
					if(listOfFiles[i].getName().endsWith(ext)) {
						files.add(listOfFiles[i].getName());
					}
			} else if(listOfFiles[i].isDirectory()) {
				String[] subs = getListOfFile(listOfFiles[i].getPath(), _ext);
				if(subs == null)
					continue;
				for(String file : subs)
					files.add(file);
			}
		}

		if(files.size() != 0) {
			String[] returnStrings = new String[files.size()];
			int i = 0;
			for(Object o : files)
				returnStrings[i++] = (String) o;
			return returnStrings;
		}
		return null;
	}

}