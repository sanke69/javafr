package fr.java.plugins.internal.cl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;

/**
 *  Try to load a class first from provided URLs
 *  otherwise ask for parent...
 */
public class CustomClassLoader extends ClassLoader {

	private static class InternalClassLoader extends URLClassLoader {
		private DetectClass realParent;

		public InternalClassLoader(URL[] urls, DetectClass _parent) {
			super(urls, null);
			this.realParent = _parent;
		}

		@Override
		public Class<?> findClass(String name) throws ClassNotFoundException {
			try {
				Class<?> loaded = super.findLoadedClass(name);
				if (loaded != null)
					return loaded;
				return super.findClass(name);
			} catch (ClassNotFoundException e) {
				return realParent.loadClass(name);
			}
		}

	}

	private static class DetectClass extends ClassLoader {

		public DetectClass(ClassLoader parent) {
			super(parent);
		}

		@Override
		public Class<?> findClass(String name) throws ClassNotFoundException {
			return super.findClass(name);
		}

	}

	private InternalClassLoader delegated;

	public CustomClassLoader(List<URL> classpath) {
		super(Thread.currentThread().getContextClassLoader());
		URL[] urls = classpath.toArray(new URL[classpath.size()]);
		delegated  = new InternalClassLoader(urls, new DetectClass(this.getParent()));
	}

	
	
	

	/*
	 * 
	 */

	  @Override
	  public Class<?> loadClass(String name) throws ClassNotFoundException {
	    System.out.println("MyClassLoader is loading " + name);
	    return delegated.loadClass(name);
	  }//loadClass

	  @Override
	  public synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
	    System.out.println("MyClassLoader is loading " + name + " with resolve = " + resolve);
		try {
			return delegated.findClass(name);
		} catch (ClassNotFoundException e) {
			return super.loadClass(name, resolve);
		}
	  }

	  @Override
	  protected Class<?> findClass(String name) throws ClassNotFoundException {
	    System.out.println("MyClassLoader findClass " + name);
	    return delegated.findClass(name);
	  }

	  @Override
	  protected URL findResource(String name) {
	    System.out.println("MyClassLoader findResource " + name);
	    return delegated.findResource(name);
	  }

	  @Override
	  protected Enumeration<URL> findResources(String name) throws IOException {
	    System.out.println("MyClassLoader findResources " + name);
	    return delegated.findResources(name);
	  }

	  @Override
	  protected Package getPackage(String name) {
	    System.out.println("MyClassLoader getPackage " + name);
	    return delegated.getDefinedPackage(name);									// TODO:: FIXME::
	  }

	  @Override
	  public URL getResource(String name) {
	    System.out.println("MyClassLoadergetResource " + name);
	    return delegated.getResource(name);
	  }

	  @Override
	  public InputStream getResourceAsStream(String name) {
	    System.out.println("MyClassLoader getResourceAsStream " + name);
	    return delegated.getResourceAsStream(name);
	  }

	  @Override
	  public Enumeration<URL> getResources(String name) throws IOException {
	    System.out.println("MyClassLoader getResources " + name);
	    return delegated.getResources(name);
	  }
	
}