package fr.java.plugins.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import fr.java.plugins.apt.PluginProcessor;
import fr.java.plugins.lang.PluginContext;
import fr.java.plugins.lang.PluginRef;

@SuppressWarnings("unchecked")
public class DefaultPluginContext implements PluginContext {

	private final Map<String, Object>         nameRegistry;

	public DefaultPluginContext() {
		nameRegistry  = new HashMap<>();
	}

	public DefaultPluginContext(Map<String, Object> _nameRegistry) {
		nameRegistry  = Objects.requireNonNull(_nameRegistry, "Plugin Registry cannot be null");
	}

	@Override
//	@Nullable
	public <T> T getPlugin(String pluginName) {
		Objects.requireNonNull(pluginName, "Plugin name to lookup cannot be null");

		if (nameRegistry.containsKey(pluginName))
			return (T) nameRegistry.get(pluginName);

		return null;
	}

	@Override
//	@Nullable
	public <T> T getPlugin(Class<T> pluginClass) {
		Objects.requireNonNull(pluginClass, "Plugin class to lookup cannot be null");
		for(Object plugin : nameRegistry.values())
			if(isInstanceOf(plugin, pluginClass))
				return (T) plugin;

		return null;
	}
	public <T> Set<T> getPlugins(Class<T> pluginClass) {
		Objects.requireNonNull(pluginClass, "Plugin class to lookup cannot be null");
		
		Set<T> plugins = new HashSet<T>();

		nameRegistry.values().stream()
							 .filter(p -> isInstanceOf(p, pluginClass))
							 .forEach(p -> plugins.add((T) p));

		return plugins;
	}

	@Override
	public Collection<PluginRef<?>> getAllPlugins() {
		final ArrayList<PluginRef<?>> plugins = new ArrayList<>();
		for (Map.Entry<String, Object> entry : nameRegistry.entrySet()) {
			plugins.add(PluginRef.of(entry.getValue(), entry.getKey()));
		}
		return plugins;
	}

	@Override
	public <T> void addPlugin(T plugin) {
		Objects.requireNonNull(plugin, "Plugin to register cannot be null");
		final String name = PluginProcessor.getPluginName(plugin);
		addPlugin(name, plugin);
	}

	@Override
	public <T> void addPlugin(String name, T plugin) {
		Objects.requireNonNull(name, "Plugin name to register cannot be null");
		Objects.requireNonNull(plugin, "Plugin to register cannot be null");
		if (nameRegistry.containsKey(name) || nameRegistry.containsValue(plugin)) {
			throw new IllegalArgumentException("Plugin already registered");
		}
		nameRegistry.put(name, plugin);
	}

	@Override
//	@Nullable
	public <T> T removePlugin(T plugin) {
		Objects.requireNonNull(plugin, "Plugin to remove cannot be null");
		final String name = PluginProcessor.getPluginName(plugin);
		return removePlugin(name);
	}

	@Override
//	@Nullable
	public <T> T removePlugin(String pluginName) {
		Objects.requireNonNull(pluginName, "Plugin name to remove cannot be null");
		if (nameRegistry.containsKey(pluginName)) {
			return (T) nameRegistry.remove(pluginName);
		}
		return null;
	}

	@Override
	public boolean hasPlugin(String pluginName) {
		return nameRegistry.containsKey(pluginName);
	}

	@Override
	public <T> boolean hasPlugin(Class<T> pluginClass) {
		return getPlugin(pluginClass) != null;
	}

	private boolean isInstanceOf(Object _plugin, Class<?> _clazz) {
		final Class<?> clazz = _plugin.getClass();

		if (clazz.equals(_clazz))
			return true;

		for(Class<?> interfaze : clazz.getInterfaces()) {
			if(interfaze.equals(_clazz))
				return true;

			for(Class<?> superInterfaze : interfaze.getInterfaces())
				if(superInterfaze.equals(_clazz))
					return true;
		}

		Class<?> superClazz = clazz.getSuperclass();
		while(superClazz != Object.class) {
			if(superClazz.equals(_clazz))
				return true;

			superClazz = superClazz.getSuperclass();
		}

		return false; 
	}

}
