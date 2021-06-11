module javafr.graphs {
	requires java.desktop;
	requires java.logging;

	requires jdk.unsupported;

	requires transitive javafr;
	requires transitive javafr.beans;
	requires java.base;

	exports fr.java.graph;

	exports fr.java.graph.algorithms.traversal;
	exports fr.java.graph.algorithms.traversal.bfs;
	exports fr.java.graph.algorithms.traversal.dfs;

	exports fr.java.graph.tree;

	exports fr.java.graph.layouts;
	exports fr.java.graph.viewer;
	exports fr.java.graph.viewer.utils;

	exports fr.java.graph.adapters;
	exports fr.java.graph.utils;

}
