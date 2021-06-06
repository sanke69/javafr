package fr.java.graph.algorithms.traversal;

import fr.java.graph.GTGraph;
import fr.java.lang.properties.ID;

public interface GTGraphTraversal {

	static enum VisitReturn {
		CONTINUE, SKIP_NEIGHBORS, TERMINATE;
	}

	public void 		visit(GTGraph _graph, ID _nodeId);

	public VisitReturn 	visit(ID _nodeId);
	
}
