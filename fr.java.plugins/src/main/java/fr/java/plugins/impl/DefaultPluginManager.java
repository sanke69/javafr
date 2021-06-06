package fr.java.plugins.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import fr.java.plugins.apt.PluginProcessor;
import fr.java.plugins.internal.di.DependencyResolver;
import fr.java.plugins.internal.di.PluginModel;
import fr.java.plugins.lang.Plugin;
import fr.java.plugins.lang.PluginContext;
import fr.java.plugins.lang.PluginManager;
import fr.java.plugins.lang.PluginRef;
import fr.java.plugins.lang.PluginSource;
import fr.java.plugins.lang.exceptions.MissingDependencyException;

public class DefaultPluginManager implements PluginManager {

    protected final PluginContext 		context;
    protected final PluginProcessor 	annotationProcessor;
    protected final DependencyResolver 	dependencyResolver;

    /**
     * Ideally not for public usage. Use {@link PluginManagers#defaultPluginManager()} instead for a fully
     * configured <code>DefaultPluginManager</code>
     *
     * @param context             the {@link PluginContext} to use
     * @param annotationProcessor the {@link AnnotationProcessor} to use
     * @param dependencyResolver  the {@link DependencyResolver} to use
     */
    public DefaultPluginManager(PluginContext _context, PluginProcessor _annotationProcessor, DependencyResolver _dependencyResolver) {
    	super();
        context             = _context;
        annotationProcessor = _annotationProcessor;
        dependencyResolver  = _dependencyResolver;
    }

    @Override
    public <T> void 					register(String name, T plugin) {
        context.addPlugin(name, plugin);
    }
    @Override
    public <T> void 					register(T plugin) {
        context.addPlugin(plugin);
    }

    @Override
    public <T> T 						getPlugin(String name) {
        return context.getPlugin(name);
    }
    @Override
    public <T> T 						getPlugin(Class<T> pluginClass) {
        return context.getPlugin(pluginClass);
    }
	@Override
	public <T> Set<T> 					getPlugins(Class<T> pluginClass) {
        return context.getPlugins(pluginClass);
	}

    @Override
    public Collection<PluginRef<?>> 	getAllPlugins() {
        return context.getAllPlugins();
    }

    @Override
    public <T> T 						removePlugin(String name) {
        return context.removePlugin(name);
    }
    @Override
    public <T> T 						removePlugin(T plugin) {
        return context.removePlugin(plugin);
    }

    @Override
    public Collection<Object> 			loadPlugins(PluginSource source) throws Exception {
        final Collection<Class<?>> pluginClasses = Objects.requireNonNull(source, "Plugin Source cannot be null").load();
        final Collection<Object>   loaded        = new ArrayList<>();

        if (pluginClasses.isEmpty())
            return loaded;

        final Iterator<Class<?>> itr = pluginClasses.iterator();
        while (itr.hasNext()) {
            Class<?> pluginClass = itr.next();

            if (pluginClass.getAnnotation(Plugin.class) == null) {
                itr.remove();
                continue;
            }
            if (context.hasPlugin(pluginClass))
                itr.remove();
        }

        Collection<PluginModel<?>> nodes = dependencyResolver.resolve(pluginClasses);

        createPlugins(nodes, loaded);
        return loaded;
    }

    protected Object 					create(Class<?> refClass) {
        final Constructor<?>[] constructors = refClass.getConstructors();
        for (Constructor<?> constructor : constructors) {

            try {
                final Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == 0) {
                    return refClass.newInstance();
                } else {
                    if (constructor.getAnnotation(Inject.class) != null) {
                        final Object[] deps = new Object[parameterTypes.length];
                        for (int i = 0; i < parameterTypes.length; i++) {
                            final Class<?> param = parameterTypes[i];
                            final Object dep = context.getPlugin(param);
                            if (dep != null) {
                                deps[i] = dep;
                            } else {
                                throw new MissingDependencyException("No plugin found for type %s while it is required by %s", param.getName(), refClass.getName());
                            }
                        }
                        return constructor.newInstance(deps);
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException ignored) {
            }
        }
        return null;
    }

    private void 						createPlugins(Collection<PluginModel<?>> nodes, Collection<Object> loaded) {
        for (PluginModel<?> node : nodes)
            createPlugin(node, loaded);
    }
    private void 						createPlugin(PluginModel<?> node, Collection<Object> loaded) {
        final Class<?> refClass = node.getRefClass();

        if (refClass.getAnnotation(Plugin.class) == null)
            return;

        if (!context.hasPlugin(refClass)) {
            final Object plugin = Objects.requireNonNull(create(refClass), "Could not create plugin of type " + refClass.getName());
            context.addPlugin(plugin);
            loaded.add(plugin);
        }
    }

}
