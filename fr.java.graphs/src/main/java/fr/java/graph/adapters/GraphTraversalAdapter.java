package fr.java.graph.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.algorithms.traversal.GTGraphTraversal;
import fr.java.lang.properties.ID;

public abstract class GraphTraversalAdapter implements GTGraphTraversal {

    private GTGraph		 	graph;
    private Queue<ID> 		queue;
    private Set<ID> 		visited;
    private List<ID> 		postvisited;

    protected GraphTraversalAdapter(Queue<ID> _queue) {
    	super();
    	queue     = _queue;
    	visited   = new HashSet<ID>();
        postvisited = new ArrayList<ID>();
    }

    public abstract VisitReturn 	postVisit(ID _nodeId);

    public abstract boolean 		shouldProcessNeighbor(ID _nodeId, ID _neightborId);
    public abstract boolean 		shouldPostVisit(ID _nodeId);

    public void 					visit(GTGraph _graph, ID _nodeId) {
    	visit(_graph, Arrays.asList(_nodeId));
    }
    public void 					visit(GTGraph _graph, Collection<ID> _nodeIds) {
    	reset(_graph, _nodeIds);
    	
        while (!queue.isEmpty()) {
        	ID nodeId = queue.remove();
        	
            if(!isMarkedAsVisited(nodeId)) {
                if(visit(nodeId) != VisitReturn.CONTINUE)
                	return ;
                else
                	markAsVisited(nodeId);

                if(shouldPostVisit(nodeId))
                	addToQueue(nodeId);

                for(GTNode neightbor : _graph.getNeighbors(nodeId))
                    if(shouldProcessNeighbor(nodeId, neightbor.getId()))
                    	addToQueue(neightbor.getId());

            } else if(!isMarkedAsPostVisited(nodeId)) {
                if(isMarkedAsVisited(nodeId)) {
                	if(postVisit(nodeId) != VisitReturn.CONTINUE)
                    	return ;
                    markAsPostVisited(nodeId);
                } else
                	System.err.println("WTF why are we here ???");
            }
        }
    }

    protected GTGraph				getGraph() {
    	return graph;
    }

    private void 					reset(GTGraph _graph, Collection<ID> _nodeIds) {
    	graph = _graph;
    	queue.clear();
    	visited.clear();
        postvisited.clear();
        
        if(_nodeIds !=  null)
        	addToQueue(_nodeIds);
    }

    private void 					addToQueue(ID _nodeId) {
    	queue.add(_nodeId);
    }
    private void 					addToQueue(Collection<ID> _nodeIds) {
    	queue.addAll(_nodeIds);
    }

    private void 					markAsVisited(ID v) {
    	visited.add(v);
    }
    protected boolean 				isMarkedAsVisited(ID v) {
        return visited.contains(v);
    }

    private void 					markAsPostVisited(ID v) {
    	postvisited.add(v);
    }
    protected boolean 				isMarkedAsPostVisited(ID v) {
        return postvisited.contains(v);
    }

}
