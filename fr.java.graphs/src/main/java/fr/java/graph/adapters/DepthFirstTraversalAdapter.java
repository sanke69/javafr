package fr.java.graph.adapters;

import java.util.ArrayDeque;
import java.util.Collections;

import fr.java.graph.adapters.GraphTraversalAdapter;
import fr.java.lang.properties.ID;

public abstract class DepthFirstTraversalAdapter extends GraphTraversalAdapter {

	public DepthFirstTraversalAdapter() {
        super(Collections.asLifoQueue(new ArrayDeque<ID>()));
    }

    @Override
    public VisitReturn 	postVisit(ID v) 									{ return VisitReturn.CONTINUE; }
    public boolean 		shouldProcessNeighbor(ID _nodeId, ID _neightborId) 	{ return !isMarkedAsVisited(_neightborId); }
    public boolean 		shouldPostVisit(ID _nodeId) 						{ return false; }

}
