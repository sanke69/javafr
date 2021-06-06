module javafr.plugins {
	requires java.logging;

	requires javafr;
	requires javafr.beans;
	requires javafr.graphs;
	requires javafr.sdk;

	exports javax.inject;

	exports fr.java.reflect.modules;

	exports fr.java.plugins;
	exports fr.java.plugins.apt;
//	exports fr.java.plugins.internal.loaders;
	exports fr.java.plugins.lang;
//	exports fr.java.plugins.impl;
	exports fr.java.plugins.lang.exceptions;

	exports fr.java.plugins.internal.cl;
//	exports fr.java.plugins.internal.cl.impl;
	exports fr.java.plugins.internal.loaders;

}
