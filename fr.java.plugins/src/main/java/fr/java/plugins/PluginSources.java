package fr.java.plugins;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import fr.java.plugins.internal.cl.MasterClassLoader;
import fr.java.plugins.lang.PluginSource;

public class PluginSources {
	
    /**
     * Load plugins from JAR files located at the given path
     *
     * @param pluginDirectoryPath the path to the directory where the JAR files are located
     * @return a list of loaded {@link Class} objects, never null
     */
    public static PluginSource jarSource(final String pluginDirectoryPath) {
        return jarSource(URI.create(pluginDirectoryPath));
    }

    /**
     * Load plugins from JAR files located at the given {@link URI}
     *
     * @param pluginUri the {@link URI} to the directory where the JAR files are located
     * @return a list of loaded {@link Class} objects, never null
     */
    public static PluginSource jarSource(final URI pluginUri) {
        return new PluginSource() {
            @Override
            public Collection<Class<?>> load() throws IOException, ClassNotFoundException {
                final ArrayList<Class<?>> plugins = new ArrayList<>();
//                final Path path = Paths.get(pluginUri);
                final Path path = Paths.get(pluginUri.toString());

                if (!Files.exists(path)) {
                    throw new IllegalArgumentException("Path " + pluginUri + " does not exist");
                }

                if (!Files.isDirectory(path)) {
                    throw new IllegalArgumentException("Path " + pluginUri + " is not a directory");
                }
                final Map<Path, URL> jarUrls = new HashMap<>();
                for (Path filePath : Files.newDirectoryStream(path)) {
                    if (filePath.getFileName().toString().endsWith(".jar")) {
                        jarUrls.put(filePath, filePath.toUri().toURL());
                    }
                }
                final ClassLoader cl = MasterClassLoader.newClassLoader(jarUrls.values().toArray(new URL[]{}));
                for (Path jarPaht: jarUrls.keySet()) {
                    final File file = jarPaht.toAbsolutePath().toFile();
                    final JarFile jar = new JarFile(file);
                    for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
                        final JarEntry entry = entries.nextElement();
                        if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                            continue;
                        }
                        String className = entry.getName().substring(0, entry.getName().length() - 6);
                        className = className.replace('/', '.');

                        // FIXME:: Patch JDK9+
                        if(className.compareToIgnoreCase("module-info") != 0) {
                        	int nbAttempts = 0;

                        	while(nbAttempts < 1) {
	                        	try {
	                        		Class<?> clazz = Class.forName(className, true, cl);
	                                plugins.add(clazz);

	                                nbAttempts = 5;
	                        	} catch(ExceptionInInitializerError | IllegalStateException _ex) {
	                        		_ex.printStackTrace();
	                        		initJavaFX(); // TODO:: Find better solution...

	                        		nbAttempts++;
	                        	} catch(NoClassDefFoundError e) {
	                        		e.printStackTrace();

	                        	} catch(Exception e) {
	                        		e.printStackTrace();
	                        	}
                        	}

                            

                        }
                    }
                }
                return plugins;
            }
        };

    }

    /**
     * Load plugins from the given list of {@link Class}. Mostly useful for testing and debugging
     *
     * @param classes a list of classes to load
     * @return the same list given as input, never null
     */
    public static PluginSource classList(final Class<?>... classes) {
        return new PluginSource() {
            @Override
            public Collection<Class<?>> load() throws Exception {
                return new ArrayList<Class<?>>() {{
                    addAll(Arrays.asList(classes));
                }};
            }

        };
    }
    
    public static void initJavaFX() {
		final CountDownLatch latch  = new CountDownLatch(1);

		try {
			Lookup       lu      = MethodHandles.lookup();
			Class<?>     jfx     = lu.findClass("javafx.application.Platform");
			MethodHandle startup = lu.findStatic(jfx, "startup", MethodType.methodType(void.class, Runnable.class));
			startup.invokeExact((Runnable) () -> { latch.countDown(); });

	        try { latch.await(5000, TimeUnit.MILLISECONDS);
	        } catch (InterruptedException e) { System.err.println("Must not append"); }

		} catch (Throwable e) {
			e.printStackTrace();
		}

    }
    
}
