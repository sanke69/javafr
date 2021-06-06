module javafr.beans {
	requires java.desktop;
	requires java.logging;

	requires jdk.unsupported;

	requires transitive javafr;

	exports fr.java.beans;
	exports fr.java.beans.annotations;
	exports fr.java.beans.properties;
	exports fr.java.beans.impl.events;
	exports fr.java.beans.properties.listeners;
	exports fr.java.beans.properties.listeners.vetoable;
	exports fr.java.beans.impl;

	exports fr.java.beans.reflect;
	exports fr.java.beans.reflect.types;
	exports fr.java.beans.reflect.utils;

	exports fr.java.beans.proxies;

	exports fr.java.beans.utils;

	exports fr.java.patterns;

}
