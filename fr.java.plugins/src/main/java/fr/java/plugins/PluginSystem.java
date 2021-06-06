package fr.java.plugins;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.nio.file.FileX;
import fr.java.plugins.apt.PluginProcessor;
import fr.java.plugins.impl.DefaultPluginContext;
import fr.java.plugins.impl.DefaultPluginManager;
import fr.java.plugins.internal.cl.MasterClassLoader;
import fr.java.plugins.internal.di.DependencyResolver;
import fr.java.plugins.lang.PluginManager;
import fr.java.plugins.lang.PluginRef;
import fr.java.plugins.lang.PluginSource;
import fr.java.sdk.nio.file.DirectoryObject;
import fr.java.sdk.nio.file.filters.samples.JarFileFilter;

public class PluginSystem implements PluginManager {
	PluginManager				manager;
	ClassLoader					libCL;

	public PluginSystem(String _libraryPath) {
		super();

		manager = initDefaultManager();

		libCL = initLibraryClassLoader(_libraryPath);
		MasterClassLoader.initDefaultClassLoader(libCL);
	}

	private ClassLoader initLibraryClassLoader(String _libraryPath) {
		if(_libraryPath == null)
			return MasterClassLoader.newClassLoader(ClassLoader.getSystemClassLoader());
			
		FileX.Directory libDir = new DirectoryObject(_libraryPath);

		Set<Path> jars = libDir.ls(true)
							   .stream()
							   .filter(f -> new JarFileFilter().accept(f.toFile()))
							   .map(FileX::toPath)
							   .collect(Collectors.toSet());

		for(Path jar : jars)
			System.out.println("Adding " + jar + " to library classpath");

		return MasterClassLoader.newClassLoader(ClassLoader.getSystemClassLoader(), jars.toArray(new Path[0]));
	}

	public PluginManager 				getPluginManager() {
		return manager;
	}

	public <T> void 					register(T plugin) {
		getPluginManager().register(plugin);
	}

	public <T> void 					register(String name, T plugin) {
		getPluginManager().register(name, plugin);
	}

	public <T> T 						getPlugin(String name) {
		return getPluginManager().getPlugin(name);
	}

	public <T> T 						getPlugin(Class<T> pluginClass) {
		return getPluginManager().getPlugin(pluginClass);
	}

	public <T> Set<T> 					getPlugins(Class<T> pluginClass) {
		return getPluginManager().getPlugins(pluginClass);
	}

	public Collection<PluginRef<?>> 	getAllPlugins() {
		return getPluginManager().getAllPlugins();
	}

	public <T> T 						removePlugin(String name) {
		return getPluginManager().removePlugin(name);
	}

	public <T> T 						removePlugin(T plugin) {
		return getPluginManager().removePlugin(plugin);
	}

	public Collection<Object> 			loadPlugins(PluginSource source) throws Exception {
		return getPluginManager().loadPlugins(source);
	}

	private PluginManager 				initDefaultManager() {
        final DefaultPluginContext context   = new DefaultPluginContext();
        final PluginProcessor      processor = new PluginProcessor();
        final DependencyResolver   resolver  = new DependencyResolver(processor);

        return new DefaultPluginManager(context, processor, resolver);
	}

}
