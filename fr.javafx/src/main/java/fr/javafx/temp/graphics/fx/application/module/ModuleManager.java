package fr.javafx.temp.graphics.fx.application.module;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import fr.javafx.temp.graphics.fx.application.ApplicationInterface;

public class ModuleManager {
	private static Map<String, ModuleInterface> modules;
	private static ApplicationInterface 	 	application;
	
	static {
		modules = new LinkedHashMap<String, ModuleInterface>();
	}

	public static void registerApplication(ApplicationInterface _application) {
		application = _application;
	}

	public static Collection<ModuleInterface> getModules() {
		if(modules.isEmpty())
			return null;

		Collection<ModuleInterface> c = new LinkedList<ModuleInterface>();
		for(String mod : modules.keySet())
			c.add(modules.get(mod));
		return c;
	}

	public static boolean registerModule(String _name, ModuleInterface _module) {
		if(_module == null || modules.get(_name) != null)
			return false;

		if(!_module.isAvailable())
			return false;

		modules.put(_name, _module);

		if(application == null)
			return false;

		_module.configureRibbonPanes(application);
		return true;
	}
	public static ModuleInterface getModule(String _name) {
		if(modules.get(_name) == null)
			return null;

		return modules.get(_name);
	}
	public static boolean unregisterModule(String _name) {
		if(modules.get(_name) == null)
			return false;

		modules.remove(_name);
		return true;
	}

}
