package fr.java.plugins.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marking plugin classes that will be loaded at runtime.
 * This class must have either a no-args constructor or a constructor marked with {@link javax.inject.Inject} having
 * other dependent Plugins as parameters that will be resolved during instantiation.
 * @see Plugin#name()
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Plugin {

    /**
     * the name to associate with the plugin
     *
     * @return the plugin name
     */
    String name();

}
