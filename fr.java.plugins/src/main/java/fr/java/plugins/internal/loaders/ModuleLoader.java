package fr.java.plugins.internal.loaders;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ModuleLoader {
	Path 			path;

	ModuleReference moduleRef;
	ModuleLayer 	moduleLayer;
	ClassLoader		parentClassLoader, moduleClassLoader;

	public ModuleLoader(Path _path) {
		this(_path, ClassLoader.getSystemClassLoader());
	}
	public ModuleLoader(Path _path, ClassLoader _parent) {
		super();
		path = _path;

		parentClassLoader = ClassLoader.getSystemClassLoader();

		initializeModule();
		initializeModuleLayer();
		initializeClassLoader();
	}

	public String 		getModuleName() {
		return moduleRef.descriptor().name();
	}
	public ClassLoader 	getModuleClassLoader() {
		return moduleClassLoader;
	}

	void initializeModule() {
		ModuleFinder    modFinder = ModuleFinder.of(path);
		ModuleReference modRef    = modFinder.findAll().toArray(new ModuleReference[0])[0];
		
		moduleRef = modRef;
	}
	void initializeModuleLayer() {
		ModuleLayer  	bootLayer  = ModuleLayer.boot();
		Configuration   bootConfig = bootLayer.configuration();
		ModuleFinder 	noFinder   = ModuleFinder.of(new Path[0]);

		ModuleFinder 	modFinder  = ModuleFinder.of(path);
		List<String>    modName    = Arrays.asList( getModuleName() );
		Configuration 	modConfig  = bootConfig.resolve(modFinder, noFinder, modName);
		ModuleLayer 	modLayer   = bootLayer.defineModulesWithManyLoaders(modConfig, parentClassLoader);

		moduleLayer = modLayer;
	}
	void initializeClassLoader() {
		ClassLoader classLoader = moduleLayer.findLoader(getModuleName());

		moduleClassLoader = classLoader;
	}

}
