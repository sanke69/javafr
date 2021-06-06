package fr.java.graph.viewer.utils;

import fr.java.graph.GTEdge;
import fr.java.graph.GTGate;
import fr.java.graph.GTNode;
import fr.java.graph.viewer.GTLayout;

public interface GTLayoutFactory<NL extends GTLayout.Node, EL extends GTLayout.Edge, GL extends GTLayout.Gate>  {

	public NL createNodeLayout(GTNode _node);
	public EL createEdgeLayout(GTEdge _edge, NL _sourceLayout, NL _targetLayout);
	public GL createGateLayout(GTGate _gate, NL _ownerLayout);

}
