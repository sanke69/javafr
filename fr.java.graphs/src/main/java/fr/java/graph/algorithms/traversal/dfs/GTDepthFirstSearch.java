package fr.java.graph.algorithms.traversal.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.algorithms.traversal.GTGraphTraversal;
import fr.java.lang.properties.ID;

/**
 *  Depth First Search
 *  
 *  see. https://www.geeksforgeeks.org/applications-of-depth-first-search/
 *  
 *  @author sanke
 */
@FunctionalInterface
public interface GTDepthFirstSearch extends GTGraphTraversal {

	default VisitReturn recursiveVisit(GTGraph _graph, ID _currentId, List<ID> _visitedIds) {
		VisitReturn result = null;

		_visitedIds.add(_currentId);

    	if((result = visit(_currentId)) != VisitReturn.CONTINUE)
    		return result;

        for(GTNode next : _graph.getNeighbors(_currentId)) {
            if(!_visitedIds.contains(next.getId())) {
            	if((result = recursiveVisit(_graph, next.getId(), _visitedIds)) != VisitReturn.CONTINUE)
            		return result;
            } 
        }

        return VisitReturn.CONTINUE;
     }

	default void visit(GTGraph _graph, ID _nodeId) {
		visit(_graph, Arrays.asList(_nodeId));
    }
	default void visit(GTGraph _graph, Collection<ID> _nodeIds) {
        List<ID> visitedIds = new ArrayList<ID>();
        
        for(ID id : _nodeIds)
        	if(recursiveVisit(_graph, id, visitedIds) != VisitReturn.CONTINUE)
        		return ;
    }

	VisitReturn visit(ID _nodeId);

}
