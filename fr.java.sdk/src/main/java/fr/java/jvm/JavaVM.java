package fr.java.jvm;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.security.Certificate;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.logging.Level;

import fr.java.sdk.log.LogInstance;

@SuppressWarnings("deprecation")
public class JavaVM {
	final static LogInstance log = LogInstance.getLogger("JavaVM");

	public static class ClassPath {
		/* see URLClassLoader SecureClassLoader */
		private static final Class<?>[] parameters = new Class[] { URL.class };

		private static String			rootPath;
		private static HashSet<Path>	classPathElements;
		private static String			classPath;

		static {
			rootPath = "";
			classPath = "";
			classPathElements = new HashSet<Path>();
		}

		public static void addClassPath(String[] _cps) {
			for(String cp : _cps)
				addClassPath(cp);
		}

		public static void addClassPath(String cp) {
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
					log.error("Skipping non-existent classpath element '" + fe + "' (" + thr + ").");
					continue;
				}

				if(bn != null)
					if(bn.length() != 0)
						fe = new File(fe, bn);

				if(classPathElements.contains(fe.getPath())) {
					log.error("Skipping duplicate classpath element '" + fe + "'.");
					continue;
				} else {
					classPathElements.add(fe.toPath());
				}

				if(bn != null) {
					if(bn.length() != 0)
						addJars(fe.getParentFile(), bn);
				} else if(!fe.exists()) {
					log.error("Could not find classpath element '" + fe + "'");
				} else if(fe.isDirectory()) {
					addURL(createUrl(fe));
				} else if(fe.getName().toLowerCase().endsWith(".zip") || fe.getName().toLowerCase().endsWith(".jar") || fe.getName().toLowerCase().endsWith(".war")) {
					addURL(createUrl(fe));
				} else {
					log.error("ClassPath element '" + fe + "' is not an existing directory and is not a file ending with '.zip', '.jar' or '.war'");
				}
			}
			log.info("Class loader is using classpath: \"" + classPath + "\".");
		}

		public static void addURL(URL aURL) {
			URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<?> sysclass = URLClassLoader.class;
			try {
				Method method = sysclass.getDeclaredMethod("addURL", parameters);
				method.setAccessible(true);
				method.invoke(sysloader, new Object[] { aURL });
			} catch(Throwable t) {
				t.printStackTrace();
				log.error("Error adding " + aURL + " to system classloader");
			}
		}

		public static void addJars(File dir, String nam) {
			String[] jars; // matching jar files

			if(nam.endsWith(".jar"))
				nam = nam.substring(0, (nam.length() - 4));

			if(!dir.exists()) {
				log.error("Could not find directory for Class Path element '" + dir + File.separator + nam + ".jar'");
				return;
			}
			if(!dir.canRead()) {
				log.error("Could not read directory for Class Path element '" + dir + File.separator + nam + ".jar'");
				return;
			}

			if((jars = getListOfFile(dir.getPath(), ".zip;.jar;.war")) == null) {
				log.error("Error accessing directory for Class Path element '" + dir + File.separator + nam + ".jar'");
			} else if(jars.length == 0) {
				log.error("No JAR files match specification '" + new File(dir, nam) + ".jar'");
			} else {
				log.error("Adding files matching specification '" + dir + File.separator + nam + ".jar'");
				Arrays.sort(jars, String.CASE_INSENSITIVE_ORDER);
				for(int xa = 0; xa < jars.length; xa++) {
					addURL(createUrl(new File(dir, jars[xa])));
				}
			}
		}

		private static URL createUrl(File fe) {
			try {
				URL url = fe.toURI().toURL();
				log.error("Added URL: '" + url.toString() + "'");
				if(classPath.length() > 0) {
					classPath += File.pathSeparator;
				}
				classPath += fe.getPath();
				return url;
			} catch(MalformedURLException thr) {
				log.error("Classpath element '" + fe + "' could not be used to create a valid file system URL");
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

	public static class ClassLoader extends URLClassLoader {
		// init0: bootstrap Loader, natif, <JAVA_HOME>/lib
		// init1: library Loader, ..., <JAVA_HOME>/lib/ext +  java.ext.dirs
		// init2: system Loader, ..., java.class.path

		public static void addLibDirectory(Path _libDir) {
			try {
				instance().addURL(_libDir.toUri().toURL());
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
		public static void addLibDirectory(Path _libDir, boolean _recursive) {

			for(File lib : _libDir.toFile().listFiles(new PluginFilter()))
				addJarFile(lib.toPath());

			if(_recursive)
				for(File lib : _libDir.toFile().listFiles(new DirectoryFilter()))
					addLibDirectory(lib.toPath(), _recursive);

		}
		public static void addJarFile(Path _jarFile) {

			try(JarFile jarFile = new JarFile(_jarFile.toFile())) {
				//			Manifest manifest        = jarFile.getManifest();
				//			String   moduleClassName = manifest.getMainAttributes().getValue("Module-Class");

				instance().addURL(_jarFile.toUri().toURL());
			} catch(IOException e) {
				log.error("Failed to load ZIP file: " + _jarFile);
				//			e.printStackTrace();
			}

		}
		public static void addClassesDirectory(Path _classesDir) {
			try {
				instance().addURL(_classesDir.toUri().toURL());
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}

		static private ClassLoader instance;

		private ClassLoader() {
			super(new URL[] {}, Thread.currentThread().getContextClassLoader());
		}

		public static ClassLoader instance() {
			if(instance == null)
				instance = new ClassLoader();
			return instance;
		}

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
					log.severe("Skipping non-existent classpath element '" + fe + "' (" + thr + ").");
					continue;
				}

				if(bn != null)
					if(bn.length() != 0)
						fe = new File(fe, bn);

				if(classPathElements.contains(fe.getPath())) {
					log.warning("Skipping duplicate classpath element '" + fe + "'.");
					continue;
				} else {
					classPathElements.add(fe.toPath());
				}

				if(bn != null) {
					if(bn.length() != 0)
						addJars(fe.getParentFile(), bn);
				} else if(!fe.exists())
					log.finer("Could not find classpath element '" + fe + "'");
				else if(fe.isDirectory())
					addURL_(createUrl(fe));
				else if(fe.getName().toLowerCase().endsWith(".zip") || fe.getName().toLowerCase().endsWith(".jar") || fe.getName().toLowerCase().endsWith(".war"))
					addURL_(createUrl(fe));
				else
					log.finer("ClassPath element '" + fe + "' is not an existing directory and is not a file ending with '.zip', '.jar' or '.war'");
			}
			log.finer("Class loader is using classpath: \"" + classPath + "\".");
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
				log.log(Level.SEVERE, "Error adding " + aURL + " to system classloader", t);
			}
		}

		public static void addJars(File dir, String nam) {
			String[] jars; // matching jar files

			if(nam.endsWith(".jar"))
				nam = nam.substring(0, (nam.length() - 4));

			if(!dir.exists()) {
				log.finer("Could not find directory for Class Path element '" + dir + File.separator + nam + ".jar'");
				return;
			}
			if(!dir.canRead()) {
				log.finer("Could not read directory for Class Path element '" + dir + File.separator + nam + ".jar'");
				return;
			}

			if((jars = getListOfFile(dir.getPath(), ".zip;.jar;.war")) == null) {
				log.finer("Error accessing directory for Class Path element '" + dir + File.separator + nam + ".jar'");
			} else if(jars.length == 0) {
				log.finer("No JAR files match specification '" + new File(dir, nam) + ".jar'");
			} else {
				log.finer("Adding files matching specification '" + dir + File.separator + nam + ".jar'");
				Arrays.sort(jars, String.CASE_INSENSITIVE_ORDER);
				for(int xa = 0; xa < jars.length; xa++) {
					addURL_(createUrl(new File(dir, jars[xa])));
				}
			}
		}

		private static URL createUrl(File fe) {
			try {
				URL url = fe.toURI().toURL();
				log.finer("Added URL: '" + url.toString() + "'");
				if(classPath.length() > 0) {
					classPath += File.pathSeparator;
				}
				classPath += fe.getPath();
				return url;
			} catch(MalformedURLException thr) {
				log.fine("Classpath element '" + fe + "' could not be used to create a valid file system URL");
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

	public static class ModuleLoader {
		protected static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
			ClassLoader classLoader = (ClassLoader) Thread.currentThread().getContextClassLoader();
			assert classLoader != null;
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = classLoader.getResources(path);
			List<File> dirs = new ArrayList<File>();
			while(resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
			for(File directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
			return classes.toArray(new Class[classes.size()]);
		}

		private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			if(!directory.exists()) {
				return classes;
			}
			File[] files = directory.listFiles();
			for(File file : files) {
				if(file.isDirectory()) {
					assert !file.getName().contains(".");
					classes.addAll(findClasses(file, packageName + "." + file.getName()));
				} else if(file.getName().endsWith(".class")) {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				}
			}
			return classes;
		}

		@SuppressWarnings("resource")
		public static Collection<Class<?>> getClassesForPackage(String packageName) throws Exception {
			String packagePath = packageName.replace(".", "/");
			ClassLoader classLoader = (ClassLoader) Thread.currentThread().getContextClassLoader();
			Set<URL> jarUrls = new HashSet<URL>();

			while(classLoader != null) {
				if(classLoader instanceof URLClassLoader)
					for(URL url : ((URLClassLoader) classLoader).getURLs())
						if(url.getFile().endsWith(".jar"))  // may want better way to detect jar files
							jarUrls.add(url);

				classLoader = (ClassLoader) classLoader.getParent();
			}

			Set<Class<?>> classes = new HashSet<Class<?>>();

			for(URL url : jarUrls) {
				JarInputStream stream = new JarInputStream(url.openStream()); // may want better way to open url connections
				JarEntry entry = stream.getNextJarEntry();

				while(entry != null) {
					String name = entry.getName();
					int i = name.lastIndexOf("/");

					if(i > 0 && name.endsWith(".class") && name.substring(0, i).equals(packagePath))
						classes.add(Class.forName(name.substring(0, name.length() - 6).replace("/", ".")));

					entry = stream.getNextJarEntry();
				}

				stream.close();
			}

			return classes;
		}
	}

	public static class Libraries {

		public static void addToLibraryPath(String pathToAdd) {
			try {
				Field usrPathsField = java.lang.ClassLoader.class.getDeclaredField("usr_paths");

			    usrPathsField.setAccessible(true);

			    //get array of paths
			    final String[] paths = (String[]) usrPathsField.get(null);

			    //check if the path to add is already present
			    for(String path : paths) {
			        if(path.equals(pathToAdd)) {
			            return;
			        }
			    }

			    final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
			    newPaths[newPaths.length-1] = pathToAdd;
			    usrPathsField.set(null, newPaths);
			 
			} catch(NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			} catch(IllegalArgumentException e) {
				e.printStackTrace();
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		public static void addToLibraryPathOld(String _libPath) {
			System.getProperties().setProperty("java.library.path", System.getProperty("java.library.path") + ":" + _libPath);
			
			try {
				Field sysPathsField = java.lang.ClassLoader.class.getDeclaredField("sys_paths");
			    sysPathsField.setAccessible(true);
			    sysPathsField.set(null, null);
			} catch(NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static void loadNativeLibrary(String _libPath) {
			try {
				System.load(_libPath);
			} catch(UnsatisfiedLinkError e) {
				System.err.println("Native code library [" + _libPath + "] failed to load.\n" + e);
				System.err.println("Error is:\n"
						+ e.getLocalizedMessage() + "\n"
						+ e.getMessage());
				System.exit(1);
			}
			log.info("Native library " + _libPath + " correctly loaded");

		}

	}

	public static class SystemProperties {

		public static void setDefault() {
			Properties defaultSet = new Properties();
			System.setProperties(defaultSet);
		}

		public static String getProperty(String _propName) {
			return System.getProperties().getProperty(_propName);
		}
		public static Properties getAll() {
			return System.getProperties();
		}

		public static void set(String _key, String _value) {
			System.getProperties().put(_key, _value);
		}

		public static void set(String[] _p) {
			Properties defaultSet = new Properties();
			for(int i = 0; i < _p.length / 2; ++i)
				defaultSet.setProperty(_p[2 * i], _p[2 * i + 1]);
			System.setProperties(defaultSet);
		}

		public static void set(Properties _p) {
			System.setProperties(_p);
		}

		public static void add(String _key, String _value) {
			System.getProperties().put(_key, _value);
		}

		public static void add(String[] _p) {
			for(int i = 0; i < _p.length / 2; ++i)
				System.getProperties().put(_p[2 * i], _p[2 * i + 1]);
		}

		public static void add(Properties _p) {
			Properties systemSet = System.getProperties();
			for(Object key : _p.keySet())
				systemSet.put(key, _p.getProperty((String) key));
			System.setProperties(systemSet);
		}

		public static void reload() {
			Field sysPathsField;
			try {
				sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			    sysPathsField.setAccessible(true);
				sysPathsField.set(null, null);
			    sysPathsField.setAccessible(false);
			} catch(NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static void display() {
			System.out.println("         =============================================================");
			System.out.println("    ======================= System information ============================");
			System.out.println("===============================================================================");
			for(Enumeration<?> e = System.getProperties().keys(); e.hasMoreElements();) {
				String key = (String) e.nextElement(), tab = "";
				int nTab = (int) ((32 - key.length()) / 8.0) + ((32 - key.length()) % 8 != 0 ? 1 : 0);
				for(int i = 0; i < nTab; ++i)
					tab += '\t';
				System.out.println(key + tab + System.getProperty(key));
			}
			System.out.println("===============================================================================");
			System.out.println("    =======================================================================");
			System.out.println("         =============================================================");
		}

	}

	public static class EnvironmentVariable {
		static Class<?> ProcessEnvironmentClass = null;

		static Class<?> ExternalDataClass = null;

		static Class<?>			VariableClass			= null;
		static Constructor<?>	Variable_Constructor	= null;
		static Method			Variable_toString		= null;
		static Class<?>			ValueClass				= null;
		static Method			Value_toString			= null;

		static Field					theEnvironmentField	= null;
		static HashMap<Object, Object>	theEnvironment		= null;

		static Field	theStrField		= null;
		static Field	theBytesField	= null;

		@SuppressWarnings("unchecked")
		protected static void configure() {
			if(theEnvironment == null)
				try {
					ProcessEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
					ExternalDataClass = Class.forName("java.lang.ProcessEnvironment$ExternalData");
					VariableClass = Class.forName("java.lang.ProcessEnvironment$Variable");
					ValueClass = Class.forName("java.lang.ProcessEnvironment$Value");

					Variable_Constructor = VariableClass.getDeclaredConstructor(String.class, byte[].class);
					Variable_Constructor.setAccessible(true);

					theEnvironmentField = ProcessEnvironmentClass.getDeclaredField("theEnvironment");
					theEnvironmentField.setAccessible(true);

					theStrField = ExternalDataClass.getDeclaredField("str");
					theStrField.setAccessible(true);
					theBytesField = ExternalDataClass.getDeclaredField("bytes");
					theBytesField.setAccessible(true);

					Variable_toString = VariableClass.getMethod("toString");
					Variable_toString.setAccessible(true);
					Value_toString = ValueClass.getMethod("getBytes");
					Value_toString.setAccessible(true);

					theEnvironment = (HashMap<Object, Object>) theEnvironmentField.get(null);

				} catch(ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException e) {
					e.printStackTrace();
				}
		}

		public static Map<String, String> getAll() {
			configure();
			HashMap<String, String> result = new HashMap<String, String>();

			for(Object envVar : theEnvironment.keySet())
				try {
					String key = (String) Variable_toString.invoke(envVar, (Object[]) null);
					String value = new String((byte[]) Value_toString.invoke(theEnvironment.get(envVar), (Object[]) null));

					result.put(key, value);
				} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}

			return result;
		}

		public static String get(String _envVar) {
			configure();

			try {
				Object variable = Variable_Constructor.newInstance(_envVar, _envVar.getBytes());

				Object value = theEnvironment.get(variable);

				Object strValue = theStrField.get(value);
				//Object bytesValue = theBytesField.get(value);

				return (String) strValue;
			} catch(IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				e.printStackTrace();
			}

			return null;
		}

		public static void set(String _envVar, String _value) {
			configure();

			try {
				Object variable = Variable_Constructor.newInstance(_envVar, _envVar.getBytes());

				Object value = theEnvironment.get(variable);
				if(value != null) {
								theStrField.set(value, _value);
								theBytesField.set(value, _value.getBytes());
				} else {
					theEnvironment.put(_envVar, _value);
				}
			} catch(IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unchecked")
		public static void setEnv(Map<String, String> newenv) {
			try {
				Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
				Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
				theEnvironmentField.setAccessible(true);
				Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
				env.putAll(newenv);
				Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
				theCaseInsensitiveEnvironmentField.setAccessible(true);
				Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
				cienv.putAll(newenv);
			} catch(NoSuchFieldException e) {
				try {
					Class<?>[] classes = Collections.class.getDeclaredClasses();
					Map<String, String> env = System.getenv();
					for(Class<?> cl : classes) {
						if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
							Field field = cl.getDeclaredField("m");
							field.setAccessible(true);
							Object obj = field.get(env);
							Map<String, String> map = (Map<String, String>) obj;
							map.clear();
							map.putAll(newenv);
						}
					}
				} catch(Exception e2) {
					e2.printStackTrace();
				}
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	public static class JarExecutor {

		public static final void launch(String _class) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			Class<? extends Runnable> theClass = Class.forName(_class).asSubclass(Runnable.class);
			Runnable instance = theClass.newInstance();
			new Thread(instance).start();
		}

		public static void start(final String classname, final String... params) throws Exception {
			final Class<?> clazz = Class.forName(classname);
			final Method main = clazz.getMethod("main", String[].class);

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						main.invoke(null, new Object[] { params });
					} catch(Exception e) {
						throw new AssertionError(e);
					}
				}
			}).start();
		}

	}

	public static class Invoker {

		public static void invoke(String[] _args) {
			if(_args.length < 1)
				return;

			Class<?>[] argTypes = new Class[1];
			argTypes[0] = String[].class;
			try {
				Method mainMethod = Class.forName(_args[0]).getDeclaredMethod("main", argTypes);
				Object[] argListForInvokedMain = new Object[1];
				argListForInvokedMain[0] = new String[0];
				mainMethod.invoke(null, argListForInvokedMain);
			} catch(ClassNotFoundException ex) {
				System.err.println("Class " + _args[0] + " not found in classpath.");
			} catch(NoSuchMethodException ex) {
				System.err.println("Class " + _args[0] + " does not define public static void main(String[])");
			} catch(InvocationTargetException ex) {
				System.err.println("Exception while executing " + _args[0] + ":" + ex.getTargetException());
			} catch(IllegalAccessException ex) {
				System.err.println("main(String[]) in class " + _args[0] + " is not public");
			}
		}

	}

	public static class KeyStore {

		/**
		 * Key format:
		 * openssl pkcs8 -topk8 -nocrypt -in YOUR.KEY -out YOUR.KEY.der -outform der
		 * 
		 * Format of the certificate:
		 * openssl x509 -in YOUR.CERT -out YOUR.CERT.der -outform der
		 * 
		 * Import key and certificate:
		 * java comu.ImportKey YOUR.KEY.der YOUR.CERT.der
		 * 
		 * Caution: the old keystore.ImportKey-file is deleted and replaced with a
		 * keystore only containing YOUR.KEY and YOUR.CERT. The keystore and the key has
		 * no password; they can be set by the keytool -keypasswd-command for setting
		 * the key password, and the keytool -storepasswd-command to set the keystore
		 * password.
		 * 
		 * The key and the certificate is stored under the alias importkey; to change
		 * this, use keytool -keyclone.
		 * 
		 * @author Joachim Karrer, Jens Carlberg
		 * @version 1.1
		 **/
		public static class ImportKey {

			private static InputStream fullStream(String fname) throws IOException {
				FileInputStream fis = new FileInputStream(fname);
				DataInputStream dis = new DataInputStream(fis);
				byte[] bytes = new byte[dis.available()];
				dis.readFully(bytes);
				dis.close();
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				return bais;
			}

			public static void main0(String args[]) {
				// change this if you want another password by default
				String keypass = "importkey";
				// change this if you want another alias by default
				String defaultalias = "importkey";
				// change this if you want another keystorefile by default
				String keystorename = System.getProperty("keystore");

				if(keystorename == null)
					keystorename = System.getProperty("user.home") + System.getProperty("file.separator") + "keystore.ImportKey"; // especially
																																	// this
																																	// ;-)

				// parsing command line input
				String keyfile = "";
				String certfile = "";
				if(args.length < 2 || args.length > 3) {
					System.out.println("Usage: java comu.ImportKey keyfile certfile [alias]");
					System.exit(0);
				} else {
					keyfile = args[0];
					certfile = args[1];
					if(args.length > 2)
						defaultalias = args[2];
				}

				try {
					// initializing and clearing keystore
					java.security.KeyStore ks = java.security.KeyStore.getInstance("JKS", "SUN");
					ks.load(null, keypass.toCharArray());
					System.out.println("Using keystore-file : " + keystorename);
					ks.store(new FileOutputStream(keystorename), keypass.toCharArray());
					ks.load(new FileInputStream(keystorename), keypass.toCharArray());

					// loading Key
					InputStream fl = fullStream(keyfile);
					byte[] key = new byte[fl.available()];
					KeyFactory kf = KeyFactory.getInstance("RSA");
					fl.read(key, 0, fl.available());
					fl.close();
					PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec(key);
					PrivateKey ff = kf.generatePrivate(keysp);

					// loading CertificateChain
					CertificateFactory cf = CertificateFactory.getInstance("X.509");
					InputStream certstream = fullStream(certfile);

					@SuppressWarnings("unchecked")
					Collection<? extends Certificate> c = (Collection<? extends Certificate>) cf.generateCertificates(certstream);
					Certificate[] certs = new Certificate[c.toArray().length];

					if(c.size() == 1) {
						certstream = fullStream(certfile);
						System.out.println("One certificate, no chain.");
						Certificate cert = (Certificate) cf.generateCertificate(certstream);
						certs[0] = cert;
					} else {
						System.out.println("Certificate chain length: " + c.size());
						certs = (Certificate[]) c.toArray();
					}

					// storing keystore
					ks.setKeyEntry(defaultalias, ff, keypass.toCharArray(), (java.security.cert.Certificate[]) certs);
					System.out.println("Key and certificate stored.");
					System.out.println("Alias:" + defaultalias + " Password:" + keypass);
					ks.store(new FileOutputStream(keystorename), keypass.toCharArray());
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}

		}

		/*
		  KeyStore ks2 = KeyStore.getInstance("jks");
		  ks2.load(null,"".toCharArray());
		  FileOutputStream out = new FileOutputStream("C:\\mykeytore.keystore");
		  ks2.store(out, "".toCharArray());
		*/

	}

	public static class Arguments {
		private static final RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

		public static void add(String _key, String _value) {
			runtimeMxBean.getInputArguments().add("-D" + _key + "=" + _value);
		}
		public static List<String> getAll() {
			RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
			List<String> arguments = runtimeMxBean.getInputArguments();

			return arguments;
		}
		
	}
	
	public static class Status {
		
		public static void memoryInfo() {

			System.out.println("total= " + Runtime.getRuntime().totalMemory());
			System.out.println("max= " + Runtime.getRuntime().maxMemory());
			System.out.println("free= " + Runtime.getRuntime().freeMemory());

		}

	}
	
}
