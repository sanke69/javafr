package fr.java.graph.algorithms.layouts;

import fr.java.graph.viewer.GTViewer;

@FunctionalInterface
public interface GTLayoutGenerator<GTV extends GTViewer/*.Graphics<?, ?, ?, ?>*/> {

	public void execute(GTV graph);

}
