package fr.java.plugins.internal.di;

import java.util.Objects;

public class PluginModel<T> {

    private final Class<T> refClass;

    public PluginModel(Class<T> refClass) {
        this.refClass = refClass;
    }

    public Class<T> getRefClass() {
        return refClass;
    }

    @Override
    public String toString() {
        return "PluginModel{" +
                "class=" + refClass.getSimpleName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PluginModel)) {
            return false;
        }
        PluginModel<?> node = (PluginModel<?>) o;
        return Objects.equals(refClass, node.refClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refClass);
    }
}
