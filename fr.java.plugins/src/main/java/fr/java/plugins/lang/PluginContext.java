package fr.java.plugins.lang;

//import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Set;

public interface PluginContext {
	/**
	 * Return an instance of the plugin that is registered with the given name
	 *
	 * @param pluginName the name of the plugin
	 * @param <T>        the inferred type of the plugin
	 * @return the plugin object or <code>null</code> if none exists
	 */
//	@Nullable
	<T> T getPlugin(String pluginName);

	/**
	 * Return an instance of the plugin matching the given type given type
	 *
	 * @param pluginClass type the plugin must match; can be an interface or
	 *                    superclass. Cannot be <code>null</code>
	 * @param <T>         the inferred type of the plugin
	 * @return the plugin object or <code>null</code> if none exists
	 */
//	@Nullable
	<T> T 		getPlugin(Class<T> pluginClass);
	<T> Set<T> 	getPlugins(Class<T> pluginClass);

	/**
	 * Return the complete list of all the plugins currently registered
	 *
	 * @return a {@link Collection} of all the plugins
	 */
	Collection<PluginRef<?>> getAllPlugins();

	/**
	 * Register a plugin in the context
	 *
	 * @param plugin the plugin object to register
	 * @param <T>    the inferred type of the plugin
	 */
	<T> void addPlugin(T plugin);

	/**
	 * Register a plugin in the context with the given name
	 *
	 * @param name   the name to associate with the plugin
	 * @param plugin the plugin object to register
	 * @param <T>    the inferred type of the plugin
	 */
	<T> void addPlugin(String name, T plugin);

	/**
	 * Remove a plugin from the context
	 * 
	 * @param plugin the plugin instance to remove
	 * @param <T>    the inferred type of the plugin
	 * @return the removed plugin, or <code>null</code> if none is exists
	 */
//	@Nullable
	<T> T removePlugin(T plugin);

	/**
	 * Remove a plugin with the given name from the context
	 * 
	 * @param pluginName the name associated with the plugin
	 * @param <T>        the inferred type of the plugin
	 * @return the removed plugin, or <code>null</code> if none is exists
	 */
//	@Nullable
	<T> T removePlugin(String pluginName);

	/**
	 * Check if the context has a plugin with the given name
	 * 
	 * @param pluginName the name associated with the plugin
	 * @return <code>true</code> if a plugin is present, <code>false</code>
	 *         otherwise
	 */
	boolean hasPlugin(String pluginName);

	/**
	 * Check if the context has a plugin with the given type
	 * 
	 * @param pluginClass type the plugin must match; can be an interface or
	 *                    superclass.
	 * @return <code>true</code> if a plugin is present, <code>false</code>
	 *         otherwise
	 */
	<T> boolean hasPlugin(Class<T> pluginClass);

}
