package fr.java.patterns.compatible;

@FunctionalInterface
public interface CompatibilityRules<T> {

	public CompatibilityResult apply(T _source, T _destination);

}
