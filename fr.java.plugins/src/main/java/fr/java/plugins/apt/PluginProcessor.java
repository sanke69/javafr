package fr.java.plugins.apt;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fr.java.plugins.internal.di.PluginModel;
import fr.java.plugins.lang.Plugin;

public class PluginProcessor {

    public static <T> String 			getPluginName(T plugin) {
        return getPluginName(plugin.getClass());
    }

    public static <T> String 			getPluginName(Class<T> plugin) {
        final Plugin annotation = getPluginAnnotation(plugin);
        final String name       = annotation.name();
        return "".equalsIgnoreCase(name) ? null : name;
    }

    private static Plugin 				getPluginAnnotation(Class<?> pluginClass) {
        final Plugin annotation = pluginClass.getAnnotation(Plugin.class);
        if (annotation == null) {
            throw new IllegalArgumentException(String.format("Class %s is not a valid @Plugin class. Class: %s", pluginClass.getSimpleName(), pluginClass.getName()));
        }
        return annotation;
    }
   
    public boolean 						hasDependencies(Class<?> pluginClass) {
        getPluginAnnotation(pluginClass);

        final Constructor<?>[] constructors = pluginClass.getConstructors();

        if (constructors.length == 0) return false;

        for (Constructor<?> constructor : constructors) {
            final Inject annotation = constructor.getAnnotation(Inject.class);
            if (annotation != null)
                return true;
        }

        return false;
    }

    public Collection<PluginModel<?>> 	getDependencies(Class<?> pluginClass) {
        final Constructor<?>[] constructors = pluginClass.getConstructors();
        if (constructors.length == 0)
            throw new IllegalArgumentException(String.format("Class %s doesn't have a public constructor. Class: %s", pluginClass.getSimpleName(), pluginClass.getName()));

        for (Constructor<?> constructor : constructors) {
            final Inject annotation = constructor.getAnnotation(Inject.class);
            if (annotation == null)
                continue;

            final Collection<PluginModel<?>> dependencies = new ArrayList<>();
            final Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (final Class<?> param : parameterTypes)
                dependencies.add(new PluginModel<>(param));
            return dependencies;

        }
        return new ArrayList<>();
    }

}
