package fr.java.graph.adapters;

import java.util.ArrayDeque;

import fr.java.lang.properties.ID;

public abstract class BreadthFirstTraversalAdapter extends GraphTraversalAdapter {

	public BreadthFirstTraversalAdapter() {
        super(new ArrayDeque<ID>());
    }

    public VisitReturn 	postVisit(ID _nodeId)								{ return VisitReturn.CONTINUE; }

    public boolean 		shouldProcessNeighbor(ID _nodeId, ID _neightborId) 	{ return !isMarkedAsVisited(_neightborId); }
    public boolean 		shouldPostVisit(ID _nodeId) 						{ return true; }

}
