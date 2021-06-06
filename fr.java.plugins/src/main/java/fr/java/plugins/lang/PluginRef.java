package fr.java.plugins.lang;

import java.util.Objects;

public final class PluginRef<T> implements PluginDescriptor {

    @SuppressWarnings("unchecked")
    public static <T> PluginRef<T> of(T ref, String name) {
        return new PluginRef<>(ref, name, (Class<T>) ref.getClass());
    }

    private final T 		ref;
    private final String 	name;
    private final Class<T> 	type;

    private PluginRef(T _ref, String _name, Class<T> _type) {
        ref  = Objects.requireNonNull(_ref,  "Plugin reference cannot be null");
        name = Objects.requireNonNull(_name, "Plugin name cannot be null");
        type = Objects.requireNonNull(_type, "Plugin type cannot be null");
    }

    public T 			get() {
        return ref;
    }
    public String 		getName() {
        return name;
    }
    public Class<T> 	getType() {
        return type;
    }

	public ClassLoader 	getClassLoader() {
		return this.getClass().getClassLoader();
	}

}
