package fr.java.plugins.internal.cl.impl;

import java.util.Map;

//import fr.java.sdk.log.LogInstance;

public abstract class ByteCodeClassLoader extends ClassLoader {
//	public static final LogInstance log = LogInstance.getLogger();

	Map<String, Class<?>> loadedClasses;

	protected abstract byte[] 		loadClassBytes(String className);
	
	@Override
	public synchronized Class<?> 	loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
		Class<?> result;

		result = (Class<?>) loadedClasses.get(className);
		if(result != null) {
//			log.trace(">> returning cached result.");
			return result;
		}

		try {
			result = super.findSystemClass(className);
//			log.trace(">> returning system class (in CLASSPATH).");
			return result;
		} catch(ClassNotFoundException e) {
//			log.trace(">> Not a system class.");
		}

		byte[] classBytes = loadClassBytes(className);
		if(classBytes == null)
			throw new ClassNotFoundException();

		result = defineClass(className, classBytes, 0, classBytes.length);
		if(result == null)
			throw new ClassFormatError();

		if(resolveIt)
			resolveClass(result);

		loadedClasses.put(className, result);

//		log.trace(">> Returning newly loaded class.");
		return result;
	}

	
	

/*
	@Override
	public Class<?> 				loadClass(String name) throws ClassNotFoundException {
		log.trace("ByteCodeClassLoader is loading " + name);
		return internal.loadClass(name);
	}

	@Override
	protected Class<?> 				findClass(String name) throws ClassNotFoundException {
		System.out.println("ByteCodeClassLoader findClass " + name);
		return null; //internal.findClass(name);
	}

	@Override
	protected Package 				getPackage(String name) {
		System.out.println("ByteCodeClassLoader getPackage " + name);
		return internal.getDefinedPackage(name); // TODO:: FIXME::
	}

	@Override
	protected URL 					findResource(String name) {
		System.out.println("ByteCodeClassLoader findResource " + name);
		return null; //internal.findResource(name);
	}

	@Override
	protected Enumeration<URL> 		findResources(String name) throws IOException {
		System.out.println("ByteCodeClassLoader findResources " + name);
		return null; //internal.findResources(name);
	}

	@Override
	public URL 						getResource(String name) {
		System.out.println("ByteCodeClassLoader getResource " + name);
		return internal.getResource(name);
	}

	@Override
	public InputStream 				getResourceAsStream(String name) {
		System.out.println("ByteCodeClassLoader getResourceAsStream " + name);
		return internal.getResourceAsStream(name);
	}

	@Override
	public Enumeration<URL> 		getResources(String name) throws IOException {
		System.out.println("ByteCodeClassLoader getResources " + name);
		return internal.getResources(name);
	}
*/
}
