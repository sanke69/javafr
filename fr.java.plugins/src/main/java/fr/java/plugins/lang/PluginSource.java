package fr.java.plugins.lang;

import java.util.Collection;

public interface PluginSource {

    /**
     * Load a list of classes that are either
     * plugins or related code
     *
     * @return a list of class
     * @throws Exception an exception that occurred during loading
     */
    Collection<Class<?>> load() throws Exception;

}
